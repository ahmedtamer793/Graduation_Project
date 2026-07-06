package Utilities;

import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;

public class BrowserFactory {

    public static WebDriver browserSetup(String browser) {
        WebDriver driver = null;
        if (browser.equalsIgnoreCase("chrome")) {
            ChromeOptions options = new ChromeOptions();
            options.setPageLoadStrategy(PageLoadStrategy.EAGER);
            options.addArguments("--incognito");
            options.addArguments("start-maximized");
            driver = new ChromeDriver(options);
        } else if (browser.equalsIgnoreCase("edge")) {
            EdgeOptions options = new EdgeOptions();
            options.setPageLoadStrategy(PageLoadStrategy.NORMAL);
            options.addArguments("-inprivate");
            options.addArguments("start-maximized");
            driver = new EdgeDriver(options);
        }
        return driver;
    }
}