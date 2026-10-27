package parabank.pages;

import org.openqa.selenium.By;

public final class RegistrationPage {
    public static final By OPEN = By.linkText("Register");
    public static final By FIRST_NAME = By.id("customer.firstName");
    public static final By LAST_NAME = By.id("customer.lastName");
    public static final By STREET = By.id("customer.address.street");
    public static final By CITY = By.id("customer.address.city");
    public static final By STATE = By.id("customer.address.state");
    public static final By ZIP_CODE = By.id("customer.address.zipCode");
    public static final By PHONE = By.id("customer.phoneNumber");
    public static final By SSN = By.id("customer.ssn");
    public static final By USERNAME = By.id("customer.username");
    public static final By PASSWORD = By.id("customer.password");
    public static final By CONFIRM_PASSWORD = By.id("repeatedPassword");
    public static final By SUBMIT = By.cssSelector("#customerForm input[type=submit]");
    public static final By CONFIRMATION = By.cssSelector("#rightPanel h1.title");
    public static final By MESSAGE = By.cssSelector("#rightPanel p");

    private RegistrationPage() {}
}
