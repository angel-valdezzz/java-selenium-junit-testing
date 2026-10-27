package parabank.usecases;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.math.BigDecimal;
import java.time.Duration;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import parabank.data.SavingsAccounts;
import parabank.pages.BankingPage;

public final class CustomerBanking {
    private final WebDriver driver;
    private final WebDriverWait wait;

    public CustomerBanking(WebDriver driver, Duration timeout) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, timeout);
    }

    public SavingsAccounts openSavingsAccount() {
        wait.until(ExpectedConditions.elementToBeClickable(BankingPage.OPEN_ACCOUNT)).click();
        wait.until(d -> !d.findElements(BankingPage.FUNDING_OPTIONS).isEmpty());
        String source = new Select(driver.findElement(BankingPage.FUNDING))
                .getFirstSelectedOption().getAttribute("value");
        new Select(driver.findElement(BankingPage.TYPE)).selectByValue("1");
        driver.findElement(BankingPage.OPEN_SUBMIT).click();
        wait.until(ExpectedConditions.textMatches(BankingPage.NEW_ACCOUNT,
                java.util.regex.Pattern.compile("^[0-9]+$")));
        String savings = driver.findElement(BankingPage.NEW_ACCOUNT).getText();
        assertNotEquals(source, savings, "Savings must be a separate account");
        return new SavingsAccounts(source, savings);
    }

    public void transfer(SavingsAccounts accounts, BigDecimal amount) {
        if (amount.signum() <= 0 || amount.scale() != 2) {
            throw new IllegalArgumentException("Transfer amount must be positive with two decimals");
        }
        wait.until(ExpectedConditions.elementToBeClickable(BankingPage.TRANSFER)).click();
        wait.until(d -> new Select(d.findElement(BankingPage.TO)).getOptions().stream()
                .anyMatch(option -> accounts.savingsId().equals(option.getAttribute("value"))));
        driver.findElement(BankingPage.AMOUNT).sendKeys(amount.toPlainString());
        new Select(driver.findElement(BankingPage.FROM)).selectByValue(accounts.sourceId());
        new Select(driver.findElement(BankingPage.TO)).selectByValue(accounts.savingsId());
        driver.findElement(BankingPage.TRANSFER_SUBMIT).click();
        wait.until(ExpectedConditions.textToBe(BankingPage.RESULT, "Transfer Complete!"));
        assertEquals(accounts.sourceId(), driver.findElement(BankingPage.RESULT_FROM).getText());
        assertEquals(accounts.savingsId(), driver.findElement(BankingPage.RESULT_TO).getText());
        assertEquals("$" + amount.toPlainString(),
                driver.findElement(BankingPage.RESULT_AMOUNT).getText());
    }
}
