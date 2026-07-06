package Pages;

import Utilities.Utility;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CartPage {

    final WebDriver driver;

    // Locators
    By checkoutButton = By.id("checkout");
    By continueShoppingBtn = By.id("continue-shopping");
    By pageTitle = By.className("title");

    public CartPage(WebDriver driver) {
        this.driver = driver;
    }

    // Methods
    public void clickOnCheckout() {
        Utility.click(checkoutButton, driver);
    }

    public void clickContinueShopping() {
        Utility.click(continueShoppingBtn, driver);
    }

    public String getPageTitle() {
        return Utility.getText(pageTitle, driver);
    }
}