package Pages;

import Utilities.Utility;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class InventoryPage {

    final WebDriver driver;

    // Locators
    By backpackAddToCartBtn = By.id("add-to-cart-sauce-labs-backpack");
    By cartIcon = By.className("shopping_cart_link");
    By pageTitle = By.className("title");

    public InventoryPage(WebDriver driver) {
        this.driver = driver;
    }

    // Methods
    public InventoryPage addBackpackToCart() {
        Utility.click(backpackAddToCartBtn, driver);
        return this;
    }

    public void goToCart() {
        Utility.click(cartIcon, driver);
    }

    public String getPageTitle() {
        return Utility.getText(pageTitle, driver);
    }
}