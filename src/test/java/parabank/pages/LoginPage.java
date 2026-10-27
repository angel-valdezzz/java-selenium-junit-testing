package parabank.pages;

import org.openqa.selenium.By;

/** Declarative locators only. Browser actions belong to customer access use cases. */
public final class LoginPage {
    public static final By USERNAME = By.name("username");
    public static final By PASSWORD = By.name("password");
    public static final By SUBMIT = By.cssSelector("#loginPanel input[type=submit]");
    public static final By ERROR = By.cssSelector("#rightPanel .error");
    public static final By LOGOUT = By.linkText("Log Out");

    private LoginPage() {}
}
