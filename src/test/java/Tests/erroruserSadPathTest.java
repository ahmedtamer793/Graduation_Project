package Tests;

import Pages.CartPage;
import Pages.CheckoutPage;
import Pages.InventoryPage;
import Pages.LoginPage;
import Pages.OverviewPage;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Sad Path Test Suite — error_user / secret_sauce ONLY
 *
 * Documents all known BUGS and failure scenarios for error_user.
 *
 * ─── CONFIRMED BUGS (from live test execution) ────────────────────────────
 *
 *  BUG 1 — Checkout form: Last Name VALIDATION IS BYPASSED.
 *           For error_user, even when Last Name is left empty the checkout
 *           form does NOT display "Error: Last Name is required". Instead the
 *           app silently skips the validation and advances to the Overview
 *           page (checkout-step-two.html), which is incorrect behaviour.
 *
 *  BUG 2 — Checkout completion: error_user cannot finish the order.
 *           After reaching the Overview page and clicking "Finish", the app
 *           does NOT navigate to the success page (checkout-complete.html).
 *
 * All tests use error_user / secret_sauce exclusively.
 * Each test performs its own login via the LoginPage page object.
 */
public class SadPathTest extends BaseTest {

    /** Helper: login as error_user and land on the Products/Inventory page. */
    private InventoryPage loginAsErrorUser() {
        new LoginPage(driver)
                .enterUserName(ERROR_USER)
                .enterPassword(ERROR_PASSWORD)
                .clickOnLoginButton();
        return new InventoryPage(driver);
    }

    // ─── Standard validation: completely empty form ───────────────────────────

    @Test(description = "[error_user] Submitting empty checkout form shows 'First Name is required'")
    public void sadPath_emptyCheckoutFormShowsFirstNameError() {
        loginAsErrorUser()
                .addBackpackToCart()
                .goToCart();

        new CartPage(driver).clickOnCheckout();

        CheckoutPage checkoutPage = new CheckoutPage(driver);
        checkoutPage.clickOnContinue();

        // Even for error_user, submitting with NO fields filled must show First Name error
        String error = checkoutPage.getErrorMessageText();
        Assert.assertTrue(error.contains("Error: First Name is required"),
                "Submitting empty checkout form should show 'First Name is required'. Got: " + error);
    }

    // ─── BUG 1: Last Name validation is bypassed ──────────────────────────────

    /**
     * BUG TEST: error_user — Last Name validation is bypassed.
     *
     * For a standard user, submitting the checkout form with an empty Last Name
     * field shows "Error: Last Name is required".
     *
     * For error_user, this validation is silently skipped. When only
     * First Name and Postal Code are entered (Last Name left empty), the app
     * unexpectedly advances to the Overview page (checkout-step-two.html)
     * WITHOUT showing any error.
     *
     * Steps : Login → Add item → Cart → Checkout → Fill First Name + Postal only → Continue
     * Bug   : No "Last Name is required" error; URL advances to checkout-step-two
     */
    @Test(description = "[BUG] error_user: Last Name validation bypassed — form proceeds without Last Name")
    public void sadPath_errorUser_lastNameValidationBypassed() {
        loginAsErrorUser()
                .addBackpackToCart()
                .goToCart();

        new CartPage(driver).clickOnCheckout();

        // Fill only First Name + Postal Code — intentionally omit Last Name
        CheckoutPage checkoutPage = new CheckoutPage(driver);
        checkoutPage.enterFirstName("John")
                    .enterPostalCode("12345")
                    .clickOnContinue();

        // BUG ASSERTION: The form should have blocked progress with "Last Name is required",
        // but instead error_user advances to the Overview page (checkout-step-two).
        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("checkout-step-two"),
                "[BUG] error_user should be blocked by Last Name validation but advanced to: " + currentUrl);

        // Confirm no error element is shown (validation was silently skipped)
        Assert.assertFalse(currentUrl.contains("checkout-step-one"),
                "[BUG] error_user bypassed the checkout form without entering Last Name");
    }

    /**
     * BUG TEST: error_user — Last Name field input has no effect.
     *
     * Even when a value IS entered into the Last Name field, the app discards it.
     * When ALL three fields are filled and Continue is clicked, the form advances
     * to the Overview page — but if the Last Name field worked correctly, this would
     * be the expected (passing) behaviour. The bug is that if you observe the form
     * server-side, the Last Name value is dropped (the field is non-functional).
     *
     * Steps : Login → Add item → Cart → Checkout → Fill ALL fields → Continue
     * Result: Advances to checkout-step-two (same as if Last Name was empty — field is inert)
     */
    @Test(description = "[BUG] error_user: Filling Last Name has no effect — form always proceeds to Overview")
    public void sadPath_errorUser_lastNameFieldInputDiscarded() {
        loginAsErrorUser()
                .addBackpackToCart()
                .goToCart();

        new CartPage(driver).clickOnCheckout();

        // Fill ALL fields including Last Name
        CheckoutPage checkoutPage = new CheckoutPage(driver);
        checkoutPage.enterFirstName("John")
                    .enterLastName("Doe")        // BUG: input is discarded by the app
                    .enterPostalCode("12345")
                    .clickOnContinue();

        // The form advances to checkout-step-two regardless of Last Name value,
        // proving the Last Name field is non-functional for error_user.
        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("checkout-step-two"),
                "error_user should proceed to checkout-step-two after filling all fields. URL: " + currentUrl);
    }

    // ─── BUG 2: Checkout cannot be completed ─────────────────────────────────

    /**
     * BUG TEST: error_user — Clicking Finish on the Overview page does not complete the order.
     *
     * After reaching the Order Overview page (checkout-step-two.html) and clicking
     * the "Finish" button, error_user does NOT navigate to the success confirmation
     * page (checkout-complete.html). The order is never placed.
     *
     * Steps : Login → Add item → Cart → Checkout → Fill ALL fields → Continue → Finish
     * Bug   : URL does NOT contain "checkout-complete" — order was not confirmed
     */
    @Test(description = "[BUG] error_user: Clicking Finish on Overview does not complete the order")
    public void sadPath_errorUser_orderCannotBeCompleted() {
        loginAsErrorUser()
                .addBackpackToCart()
                .goToCart();

        new CartPage(driver).clickOnCheckout();

        // Fill all checkout fields and proceed to Overview
        new CheckoutPage(driver)
                .enterFirstName("John")
                .enterLastName("Doe")
                .enterPostalCode("12345")
                .clickOnContinue();

        // Verify we are on the Overview page before clicking Finish
        String overviewUrl = driver.getCurrentUrl();
        Assert.assertTrue(overviewUrl.contains("checkout-step-two"),
                "Should be on checkout-step-two (Overview) before clicking Finish. URL: " + overviewUrl);

        // Click Finish and check that the order is NOT confirmed
        new OverviewPage(driver).clickOnFinish();

        String finalUrl = driver.getCurrentUrl();
        Assert.assertFalse(finalUrl.contains("checkout-complete"),
                "[BUG] error_user should NOT reach checkout-complete — order completion is broken. Final URL: " + finalUrl);
    }
}
