package Tests;

import Utilities.BrowserFactory;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.time.Duration;

public class BaseTest {

    protected WebDriver driver;

    @BeforeMethod
    public void setup() {
        driver = BrowserFactory.browserSetup("chrome");
        driver.get("https://www.saucedemo.com/");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
    }

    @AfterMethod
    public void tearDown(){
        if (driver != null) {
            driver.quit();
        }
    }
}