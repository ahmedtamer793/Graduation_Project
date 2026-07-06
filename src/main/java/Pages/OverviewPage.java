package Pages;

import Utilities.Utility;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class OverviewPage {

    final WebDriver driver;

    // Locators
    By finishButton = By.id("finish");
    By successMessage = By.className("complete-header");

    public OverviewPage(WebDriver driver) {
        this.driver = driver;
    }

    // Methods
    public void clickOnFinish() {
        Utility.click(finishButton, driver);
    }

    public String getSuccessMessageText() {
        return Utility.getText(successMessage, driver);
    }
}