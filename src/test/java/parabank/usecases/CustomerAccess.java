package parabank.usecases;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Duration;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import parabank.data.Credentials;
import parabank.data.RejectedAccess;
import parabank.pages.AccountsPage;
import parabank.pages.LoginPage;

public final class CustomerAccess {
  private final WebDriver driver;
  private final WebDriverWait wait;

  public CustomerAccess(WebDriver driver, Duration timeout) {
    this.driver = driver;
    this.wait = new WebDriverWait(driver, timeout);
  }

  public void login(Credentials credentials) {
    submit(credentials);
    wait.until(ExpectedConditions.textToBe(AccountsPage.HEADING, "Accounts Overview"));
    wait.until(ExpectedConditions.visibilityOfElementLocated(LoginPage.LOGOUT));
  }

  public void logout() {
    wait.until(ExpectedConditions.elementToBeClickable(LoginPage.LOGOUT)).click();
    wait.until(ExpectedConditions.visibilityOfElementLocated(LoginPage.USERNAME));
  }

  public void reject(RejectedAccess scenario) {
    submit(scenario.credentials());
    wait.until(ExpectedConditions.textToBe(LoginPage.ERROR, scenario.expectedMessage()));
    assertEquals(scenario.expectedMessage(), driver.findElement(LoginPage.ERROR).getText());
    wait.until(ExpectedConditions.visibilityOfElementLocated(LoginPage.USERNAME));
  }

  private void submit(Credentials credentials) {
    wait.until(ExpectedConditions.visibilityOfElementLocated(LoginPage.USERNAME))
        .sendKeys(credentials.username());
    driver.findElement(LoginPage.PASSWORD).sendKeys(credentials.password());
    driver.findElement(LoginPage.SUBMIT).click();
  }
}
