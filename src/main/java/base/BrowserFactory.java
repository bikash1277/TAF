package base;

import common.CommonClass;
import org.openqa.selenium.WebDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.safari.SafariDriver;

public class BrowserFactory extends CommonClass {

    public WebDriver initBrowser(String browserType) {
        switch (getBrowserType(browserType).toLowerCase()) {
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
            case "safari":
                WebDriverManager.safaridriver().setup();
                driver = new SafariDriver();
                break;
            default:
                logger.error("Failed to Launch" + browserType);
        }
        return driver;
    }

    private String getBrowserType(String browserType) {
        browserType = browserType.toLowerCase().trim();
        if (browserType.equals("ff") || browserType.equals("firefox") || browserType.equals("mozilla") || browserType.equals("")) {
            return "firefox";
        } else if (browserType.equals("ie") || browserType.equals("explorer") || browserType.equals("internet explorer")) {
            return "ie";
        } else if (browserType.equals("chrome") || browserType.equals("google") || browserType.equals("google chrome")) {
            return "chrome";
        } else if (browserType.equals("edge") || browserType.equals("ee") || browserType.equals("microsoft edge")) {
            return "edge";
        } else if (browserType.equals("safari") || browserType.equals("sa") || browserType.equals("safari apple")) {
            return "safari";
        } else {
            return null;
        }
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
