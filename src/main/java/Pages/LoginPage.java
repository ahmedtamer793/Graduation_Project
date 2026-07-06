package Pages;

import Utilities.Utility;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage {

    final WebDriver driver;

    // Locators
    By userName = By.id("user-name");
    By password = By.id("password");
    By button = By.id("login-button");
    By title = By.className("login_logo");
    By errorMessage = By.cssSelector("[data-test=\"error\"]");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    // Methods
    public LoginPage enterUserName(String name) {
        Utility.sendKey(userName, name, driver);
        return this;
    }

    public LoginPage enterPassword(String name) {
        Utility.sendKey(password, name, driver);
        return this;
    }

    public void clickOnLoginButton() {
        Utility.click(button, driver);
    }

    public String getTitle() {
        return Utility.getText(title, driver);
    }

    public String getErrorMessage() {
        return Utility.getText(errorMessage, driver);
    }
}