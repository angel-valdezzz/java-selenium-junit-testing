package parabank.usecases;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.net.URI;
import java.time.Duration;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import parabank.data.Customer;
import parabank.pages.AccountsPage;

public final class CustomerAccounts {
  private final WebDriver driver;
  private final WebDriverWait wait;
  private final URI baseUrl;

  public CustomerAccounts(WebDriver driver, Duration timeout, URI baseUrl) {
    this.driver = driver;
    this.wait = new WebDriverWait(driver, timeout);
    this.baseUrl = baseUrl;
  }

  public void verifyOverviewFor(Customer customer) {
    wait.until(ExpectedConditions.textToBe(AccountsPage.HEADING, "Accounts Overview"));
    assertEquals(
        "Welcome " + customer.fullName(), driver.findElement(AccountsPage.WELCOME).getText());
    wait.until(d -> !d.findElements(AccountsPage.ACCOUNT_LINKS).isEmpty());
    assertTrue(
        driver.findElements(AccountsPage.ACCOUNT_LINKS).stream()
            .allMatch(link -> link.getText().matches("[0-9]+")));
  }

  public BigDecimal balance(String accountId) {
    if (!accountId.matches("[0-9]+")) {
      throw new IllegalArgumentException("Account ID must be numeric");
    }
    driver.get(baseUrl.resolve("activity.htm?id=" + accountId).toString());
    wait.until(ExpectedConditions.textToBe(AccountsPage.ACCOUNT_ID, accountId));
    wait.until(d -> !d.findElement(AccountsPage.BALANCE).getText().isBlank());
    return money(driver.findElement(AccountsPage.BALANCE).getText());
  }

  public void verifySavings(String accountId) {
    assertTrue(balance(accountId).signum() >= 0, "New savings balance cannot be negative");
    assertEquals("SAVINGS", driver.findElement(AccountsPage.ACCOUNT_TYPE).getText());
  }

  public void verifyBalanceAndMovement(String accountId, BigDecimal expected, BigDecimal amount) {
    BigDecimal actual = balance(accountId);
    assertEquals(0, expected.compareTo(actual), "Unexpected balance for account " + accountId);
    wait.until(
        ExpectedConditions.textToBePresentInElementLocated(
            AccountsPage.TRANSACTIONS, "$" + amount.toPlainString()));
  }

  private static BigDecimal money(String displayed) {
    return new BigDecimal(displayed.replace("$", "").replace(",", "").trim());
  }
}
