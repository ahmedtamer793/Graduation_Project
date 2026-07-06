package Tests;

import Pages.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class StandardUserHappyTest extends BaseTest {

    LoginPage loginPage;
    InventoryPage inventoryPage;
    CartPage cartPage;
    CheckoutPage checkoutPage;
    OverviewPage overviewPage;

    @BeforeMethod
    public void pageSetup() {
        loginPage = new LoginPage(driver);
        inventoryPage = new InventoryPage(driver);
        cartPage = new CartPage(driver);
        checkoutPage = new CheckoutPage(driver);
        overviewPage = new OverviewPage(driver);
    }

    @Test
    public void testSuccessfulEndToEndOrder() {
        loginPage.enterUserName("standard_user")
                .enterPassword("secret_sauce")
                .clickOnLoginButton();

        Assert.assertEquals(inventoryPage.getPageTitle(), "Products");

        inventoryPage.addBackpackToCart().goToCart();
        Assert.assertEquals(cartPage.getPageTitle(), "Your Cart");

        cartPage.clickOnCheckout();

        checkoutPage.enterFirstName("Ahmed")
                .enterLastName("Tamer")
                .enterPostalCode("12345")
                .clickOnContinue();

        overviewPage.clickOnFinish();

        String actualMessage = overviewPage.getSuccessMessageText();
        Assert.assertEquals(actualMessage, "Thank you for your order!", "Order placement failed!");
    }
}