package Tests;

import Pages.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class StandardUserSadTest extends BaseTest {

    LoginPage loginPage;
    InventoryPage inventoryPage;
    CartPage cartPage;
    CheckoutPage checkoutPage;

    @BeforeMethod
    public void pageSetup() {
        loginPage = new LoginPage(driver);
        inventoryPage = new InventoryPage(driver);
        cartPage = new CartPage(driver);
        checkoutPage = new CheckoutPage(driver);
    }

    // 1. Missing Last Name
    @Test(priority = 1)
    public void testCheckoutWithMissingLastName() {
        loginPage.enterUserName("standard_user")
                .enterPassword("secret_sauce")
                .clickOnLoginButton();

        inventoryPage.addBackpackToCart().goToCart();
        cartPage.clickOnCheckout();

        checkoutPage.enterFirstName("Ahmed")
                .enterPostalCode("12345")
                .clickOnContinue();

        String actualError = checkoutPage.getErrorMessageText();
        Assert.assertTrue(actualError.contains("Last Name is required"),
                "Error message for missing Last Name didn't appear!");
    }

    // 2. Missing Postal Code
    @Test(priority = 2)
    public void testCheckoutWithMissingPostalCode() {
        loginPage.enterUserName("standard_user")
                .enterPassword("secret_sauce")
                .clickOnLoginButton();

        inventoryPage.addBackpackToCart().goToCart();
        cartPage.clickOnCheckout();

        checkoutPage.enterFirstName("Ahmed")
                .enterLastName("Tamer")
                .clickOnContinue();

        String actualError = checkoutPage.getErrorMessageText();
        Assert.assertTrue(actualError.contains("Postal Code is required"),
                "Error message for missing Postal Code didn't appear!");
    }

    // 3. Empty Credentials
    @Test(priority = 3)
    public void testLoginWithEmptyCredentials() {
        loginPage.clickOnLoginButton();

        String actualError = loginPage.getErrorMessage();
        Assert.assertTrue(actualError.contains("Username is required"),
                "Error message for empty credentials didn't appear!");
    }

    // 4. Empty Cart Checkout Bug
    @Test(priority = 4)
    public void testEmptyCartCheckoutBug() {
        loginPage.enterUserName("standard_user")
                .enterPassword("secret_sauce")
                .clickOnLoginButton();

        inventoryPage.goToCart();
        cartPage.clickOnCheckout();

        String currentUrl = driver.getCurrentUrl();
        Assert.assertFalse(currentUrl.contains("checkout-step-one"),
                "Bug Found: The system allows checkout with an empty cart!");
    }

    // 5. Browser Navigation Bug
    @Test(priority = 5)
    public void testBrowserNavigationBug() {
        loginPage.enterUserName("standard_user")
                .enterPassword("secret_sauce")
                .clickOnLoginButton();
        Assert.assertEquals(inventoryPage.getPageTitle(), "Products");

        driver.navigate().back();

        driver.navigate().forward();
        try {
            Assert.assertEquals(inventoryPage.getPageTitle(), "Products",
                    "Bug Found: Forward navigation broke the page state!");
        } catch (Exception e) {
            Assert.fail("Bug Found: Browser navigation caused an exception -> " + e.getMessage());
        }
    }

    // 6. Missing First Name
    @Test(priority = 6)
    public void testCheckoutWithMissingFirstName() {
        loginPage.enterUserName("standard_user")
                .enterPassword("secret_sauce")
                .clickOnLoginButton();

        inventoryPage.addBackpackToCart().goToCart();
        cartPage.clickOnCheckout();

        checkoutPage.enterLastName("Tamer")
                .enterPostalCode("12345")
                .clickOnContinue();

        String actualError = checkoutPage.getErrorMessageText();
        Assert.assertTrue(actualError.contains("First Name is required"),
                "Error message for missing First Name didn't appear!");
    }

    // 7. Invalid Password
    @Test(priority = 7)
    public void testLoginWithInvalidPassword() {
        loginPage.enterUserName("standard_user")
                .enterPassword("wrong_password")
                .clickOnLoginButton();

        String actualError = loginPage.getErrorMessage();
        Assert.assertTrue(actualError.contains("Username and password do not match"),
                "Error message for invalid credentials didn't appear!");
    }

    // 8. Security Bypass Bug
    @Test(priority = 8)
    public void testBypassLoginSecurity() {
        driver.get("https://www.saucedemo.com/inventory.html");
        String actualError = loginPage.getErrorMessage();
        Assert.assertTrue(actualError.contains("You can only access '/inventory.html' when you are logged in"),
                "Security Bug Found: System allowed direct access without login!");
    }
}