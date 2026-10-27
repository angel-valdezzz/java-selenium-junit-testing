package parabank.pages;

import org.openqa.selenium.By;

public final class AccountsPage {
  public static final By HEADING = By.cssSelector("#showOverview h1.title");
  public static final By WELCOME = By.cssSelector("#leftPanel p.smallText");
  public static final By ACCOUNT_LINKS = By.cssSelector("#accountTable tbody a");
  public static final By ACCOUNT_ID = By.id("accountId");
  public static final By ACCOUNT_TYPE = By.id("accountType");
  public static final By BALANCE = By.id("balance");
  public static final By TRANSACTIONS = By.cssSelector("#transactionTable tbody");

  private AccountsPage() {}
}
