package Utilities;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class Utility {

    private static WebDriverWait getWait(WebDriver driver) {
        return new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public static void click(By locator, WebDriver driver) {
        getWait(driver).until(ExpectedConditions.elementToBeClickable(locator)).click();
    }

    public static void sendKey(By locator, String text, WebDriver driver) {
        getWait(driver).until(ExpectedConditions.visibilityOfElementLocated(locator)).clear();
        driver.findElement(locator).sendKeys(text);
    }

    public static String getText(By locator, WebDriver driver) {
        return getWait(driver).until(ExpectedConditions.visibilityOfElementLocated(locator)).getText();
    }
}