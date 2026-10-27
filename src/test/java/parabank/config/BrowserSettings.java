package parabank.config;

import java.net.URI;
import java.time.Duration;

public record BrowserSettings(URI baseUrl, Duration timeout, boolean headless) {
  public static BrowserSettings fromEnvironment() {
    String url =
        System.getProperty(
            "parabank.url",
            System.getenv()
                .getOrDefault("PARABANK_URL", "https://parabank.parasoft.com/parabank/"));
    if (!url.endsWith("/")) {
      url += "/";
    }
    URI base = URI.create(url);
    if (!"http".equals(base.getScheme()) && !"https".equals(base.getScheme())) {
      throw new IllegalArgumentException("ParaBank URL must use HTTP or HTTPS");
    }
    return new BrowserSettings(
        base,
        Duration.ofSeconds(Long.getLong("browser.timeout", 20L)),
        Boolean.parseBoolean(System.getProperty("browser.headless", "true")));
  }
}
