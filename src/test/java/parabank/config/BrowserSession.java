package parabank.config;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolver;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import parabank.usecases.CustomerAccess;
import parabank.usecases.CustomerAccounts;
import parabank.usecases.CustomerBanking;
import parabank.usecases.CustomerRegistration;

/** Composition and per-case browser lifecycle. No business assertions live here. */
public final class BrowserSession
    implements BeforeEachCallback, AfterEachCallback, ParameterResolver {
  private static final ExtensionContext.Namespace NAMESPACE =
      ExtensionContext.Namespace.create(BrowserSession.class);
  private static final Set<Class<?>> SUPPORTED =
      Set.of(
          CustomerRegistration.class,
          CustomerAccess.class,
          CustomerAccounts.class,
          CustomerBanking.class);

  @Override
  public void beforeEach(ExtensionContext context) {
    BrowserSettings settings = BrowserSettings.fromEnvironment();
    ChromeOptions options = new ChromeOptions();
    options.addArguments("--window-size=1440,900", "--disable-dev-shm-usage");
    if (settings.headless()) {
      options.addArguments("--headless=new");
    }
    if (Boolean.getBoolean("browser.noSandbox")) {
      options.addArguments("--no-sandbox");
    }
    WebDriver driver = new ChromeDriver(options);
    context.getStore(NAMESPACE).put("driver", driver);
    context.getStore(NAMESPACE).put("settings", settings);
    try {
      driver.get(settings.baseUrl().resolve("index.htm").toString());
    } catch (RuntimeException failure) {
      driver.quit();
      context.getStore(NAMESPACE).remove("driver");
      throw failure;
    }
  }

  @Override
  public boolean supportsParameter(ParameterContext parameter, ExtensionContext context) {
    return SUPPORTED.contains(parameter.getParameter().getType());
  }

  @Override
  public Object resolveParameter(ParameterContext parameter, ExtensionContext context) {
    WebDriver driver = context.getStore(NAMESPACE).get("driver", WebDriver.class);
    BrowserSettings settings = context.getStore(NAMESPACE).get("settings", BrowserSettings.class);
    Class<?> requested = parameter.getParameter().getType();
    if (requested == CustomerRegistration.class) {
      return new CustomerRegistration(driver, settings.timeout());
    }
    if (requested == CustomerAccess.class) {
      return new CustomerAccess(driver, settings.timeout());
    }
    if (requested == CustomerAccounts.class) {
      return new CustomerAccounts(driver, settings.timeout(), settings.baseUrl());
    }
    return new CustomerBanking(driver, settings.timeout());
  }

  @Override
  public void afterEach(ExtensionContext context) {
    WebDriver driver = context.getStore(NAMESPACE).remove("driver", WebDriver.class);
    if (driver == null) {
      return;
    }
    try {
      if (context.getExecutionException().isPresent()) {
        try {
          Path directory = Path.of("target", "evidence", java.util.UUID.randomUUID().toString());
          Files.createDirectories(directory);
          Files.writeString(
              directory.resolve("case.txt"), context.getUniqueId() + "\n" + context.getDisplayName());
          System.err.println("Failure evidence: " + directory);
          Files.write(
              directory.resolve("failure.png"),
              ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES));
        } catch (IOException | RuntimeException captureFailure) {
          System.err.println(
              "Could not capture failure screenshot: " + captureFailure.getClass().getSimpleName());
        }
      }
    } finally {
      driver.quit();
    }
  }
}
