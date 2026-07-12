package Tests;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

public class Sad extends BaseTest {

    @Test
    public void invalidLoginTest() {

        driver.findElement(By.id("user-name"))
                .sendKeys("performance_glitch_user");

        driver.findElement(By.id("password"))
                .sendKeys("invalid_password");

        driver.findElement(By.id("login-button"))
                .click();

        String actualMessage = driver.findElement(By.cssSelector("h3[data-test='error']")).getText();

        String expectedMessage =
                "Epic sadface: Username and password do not match any user in this service";

        Assert.assertEquals(actualMessage, expectedMessage);
    }
    @Test
    public void invalidPasswordTest() {

        driver.findElement(By.id("user-name")).sendKeys("performance_glitch_user");
        driver.findElement(By.id("password")).sendKeys("wrong_password");
        driver.findElement(By.id("login-button")).click();

        String actual = driver.findElement(By.cssSelector("h3[data-test='error']")).getText();
        String expected = "Epic sadface: Username and password do not match any user in this service";

        Assert.assertEquals(actual, expected);
    }
    @Test
    public void emptyUsernameTest() {

        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();

        String actual = driver.findElement(By.cssSelector("h3[data-test='error']")).getText();
        String expected = "Epic sadface: Username is required";

        Assert.assertEquals(actual, expected);
    }
    @Test
    public void emptyPasswordTest() {

        driver.findElement(By.id("user-name")).sendKeys("performance_glitch_user");
        driver.findElement(By.id("login-button")).click();

        String actual = driver.findElement(By.cssSelector("h3[data-test='error']")).getText();
        String expected = "Epic sadface: Password is required";

        Assert.assertEquals(actual, expected);
    }
    @Test
    public void emptyCredentialsTest() {

        driver.findElement(By.id("login-button")).click();

        String actual = driver.findElement(By.cssSelector("h3[data-test='error']")).getText();
        String expected = "Epic sadface: Username is required";

        Assert.assertEquals(actual, expected);
    }
    @Test
    public void usernameWithSpacesTest() {

        driver.findElement(By.id("user-name")).sendKeys("   ");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();

        String actual = driver.findElement(By.cssSelector("h3[data-test='error']")).getText();
        Assert.assertEquals(actual,
                "Epic sadface: Username is required");
    }
    @Test
    public void sqlInjectionTest() {

        driver.findElement(By.id("user-name")).sendKeys("' OR '1'='1");
        driver.findElement(By.id("password")).sendKeys("' OR '1'='1");
        driver.findElement(By.id("login-button")).click();

        String actual = driver.findElement(By.cssSelector("h3[data-test='error']")).getText();
        Assert.assertEquals(actual,
                "Epic sadface: Username and password do not match any user in this service");
    }
    @Test
    public void lockedOutUserTest() {

        driver.findElement(By.id("user-name")).sendKeys("locked_out_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();

        String actual = driver.findElement(By.cssSelector("h3[data-test='error']")).getText();
        Assert.assertEquals(actual,
                "Epic sadface: Sorry, this user has been locked out.");
    }
}