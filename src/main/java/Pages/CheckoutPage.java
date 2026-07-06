package Pages;

import Utilities.Utility;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CheckoutPage {

    final WebDriver driver;

    // Locators
    By firstNameField = By.id("first-name");
    By lastNameField = By.id("last-name");
    By postalCodeField = By.id("postal-code");
    By continueButton = By.id("continue");

    public CheckoutPage(WebDriver driver) {
        this.driver = driver;
    }

    // Methods
    public CheckoutPage enterFirstName(String firstName) {
        Utility.sendKey(firstNameField, firstName, driver);
        return this;
    }

    public CheckoutPage enterLastName(String lastName) {
        Utility.sendKey(lastNameField, lastName, driver);
        return this;
    }

    public CheckoutPage enterPostalCode(String postalCode) {
        Utility.sendKey(postalCodeField, postalCode, driver);
        return this;
    }

    public void clickOnContinue() {
        Utility.click(continueButton, driver);
    }

    By errorMessage = By.cssSelector("[data-test=\"error\"]");

    public String getErrorMessageText() {
        return Utility.getText(errorMessage, driver);
    }
}