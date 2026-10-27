package parabank.usecases;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import parabank.data.Customer;
import parabank.pages.LoginPage;
import parabank.pages.RegistrationPage;

public final class CustomerRegistration {
    private final WebDriver driver;
    private final WebDriverWait wait;

    public CustomerRegistration(WebDriver driver, Duration timeout) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, timeout);
    }

    /** Complete registration and verify the identity and enabled session. */
    public void register(Customer customer) {
        wait.until(ExpectedConditions.elementToBeClickable(RegistrationPage.OPEN)).click();
        fill(RegistrationPage.FIRST_NAME, customer.firstName());
        fill(RegistrationPage.LAST_NAME, customer.lastName());
        fill(RegistrationPage.STREET, customer.street());
        fill(RegistrationPage.CITY, customer.city());
        fill(RegistrationPage.STATE, customer.state());
        fill(RegistrationPage.ZIP_CODE, customer.zipCode());
        fill(RegistrationPage.PHONE, customer.phone());
        fill(RegistrationPage.SSN, customer.ssn());
        fill(RegistrationPage.USERNAME, customer.credentials().username());
        fill(RegistrationPage.PASSWORD, customer.credentials().password());
        fill(RegistrationPage.CONFIRM_PASSWORD, customer.credentials().password());
        driver.findElement(RegistrationPage.SUBMIT).click();
        String confirmation = "Welcome " + customer.credentials().username();
        wait.until(ExpectedConditions.textToBe(RegistrationPage.CONFIRMATION, confirmation));
        assertEquals(confirmation, driver.findElement(RegistrationPage.CONFIRMATION).getText());
        assertTrue(driver.findElement(RegistrationPage.MESSAGE).getText()
                .contains("Your account was created successfully."));
        wait.until(ExpectedConditions.visibilityOfElementLocated(LoginPage.LOGOUT));
    }

    private void fill(By locator, String value) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).sendKeys(value);
    }
}
