package parabank.pages;

import org.openqa.selenium.By;

public final class BankingPage {
  public static final By OPEN_ACCOUNT = By.linkText("Open New Account");
  public static final By TYPE = By.id("type");
  public static final By FUNDING = By.id("fromAccountId");
  public static final By FUNDING_OPTIONS = By.cssSelector("#fromAccountId option");
  public static final By OPEN_SUBMIT = By.cssSelector("#openAccountForm input.button");
  public static final By NEW_ACCOUNT = By.id("newAccountId");
  public static final By TRANSFER = By.linkText("Transfer Funds");
  public static final By AMOUNT = By.id("amount");
  public static final By FROM = By.id("fromAccountId");
  public static final By TO = By.id("toAccountId");
  public static final By TRANSFER_SUBMIT = By.cssSelector("#transferForm input.button");
  public static final By RESULT = By.cssSelector("#showResult h1");
  public static final By RESULT_FROM = By.id("fromAccountIdResult");
  public static final By RESULT_TO = By.id("toAccountIdResult");
  public static final By RESULT_AMOUNT = By.id("amountResult");

  private BankingPage() {}
}
