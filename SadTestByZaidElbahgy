package Tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

public class Sad extends BaseTest {

    @Test
    public void usernameWithSpacesAroundTest() {

        driver.findElement(By.id("user-name")).sendKeys("   visual_user   ");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();

        Assert.assertTrue(driver.getCurrentUrl().contains("inventory"));
    }
    @Test
    public void emptyPasswordWithValidUserTest() {

        driver.findElement(By.id("user-name")).sendKeys("visual_user");
        driver.findElement(By.id("login-button")).click();

        String actual = driver.findElement(By.cssSelector("h3[data-test='error']")).getText();

        Assert.assertEquals(actual,
                "Epic sadface: Password is required");
    }
    @Test
    public void usernameWithTrailingSpacesTest() {

        driver.findElement(By.id("user-name")).sendKeys("visual_user    ");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();

        Assert.assertTrue(driver.getCurrentUrl().contains("inventory"));
    }
    @Test
    public void usernameWithLeadingSpacesTest() {

        driver.findElement(By.id("user-name")).sendKeys("    visual_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();

        Assert.assertTrue(driver.getCurrentUrl().contains("inventory"));
    }


    @Test
    public void verifyBackForwardSession() {

        driver.findElement(By.id("user-name")).sendKeys("visual_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();

        driver.navigate().back();
        driver.navigate().forward();

        Assert.assertTrue(driver.getCurrentUrl().contains("inventory"));
    }
    @Test
    public void verifyWhitespaceTrimming() {

        driver.findElement(By.id("user-name")).sendKeys("   visual_user   ");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();

        Assert.assertTrue(driver.getCurrentUrl().contains("inventory"));
    }
    @Test
    public void verifyCartIconAlignment() {

        driver.findElement(By.id("user-name")).sendKeys("visual_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();

        WebElement cart = driver.findElement(By.className("shopping_cart_link"));

        Assert.assertTrue(cart.isDisplayed());
    }
    @Test
    public void verifyBurgerMenuAlignment() {

        driver.findElement(By.id("user-name")).sendKeys("visual_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();

        WebElement menu = driver.findElement(By.id("react-burger-menu-btn"));

        Assert.assertTrue(menu.isDisplayed());
    }
    @Test
    public void verifyPriceConsistencyDetails() {

        driver.findElement(By.id("user-name")).sendKeys("visual_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();

        String inventoryPrice = driver.findElement(By.className("inventory_item_price")).getText();

        driver.findElement(By.className("inventory_item_name")).click();

        String detailsPrice = driver.findElement(By.className("inventory_details_price")).getText();

        Assert.assertEquals(detailsPrice, inventoryPrice);
    }
    }

