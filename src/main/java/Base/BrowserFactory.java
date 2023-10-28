package Base;

import common.CommonClass;
import org.openqa.selenium.WebDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

public class BrowserFactory extends CommonClass {

    public WebDriver initBrowser(String browserType) {
        switch (browserType.toLowerCase()) {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
                break;
            case "ie":
                WebDriverManager.iedriver().setup();
                driver = new InternetExplorerDriver();
                break;
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver();
                break;
            case "edge":
                WebDriverManager.edgedriver().setup();
                driver = new EdgeDriver();
                break;
            default:
                logger.error("Failed to Launch" + browserType);
        }
        return driver;
    }

    public void quitDriver() {
        try {
            if (driver != null) {
                driver.quit();
            }
            logger.info("Driver Closed !!");
        } catch (Exception e) {
            logger.error("Exception Occurred :" + e.getMessage());
        }
    }
}
