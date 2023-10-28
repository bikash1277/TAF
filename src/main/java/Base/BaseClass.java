package Base;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.Select;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class BaseClass extends BrowserFactory {
    public BaseClass() {
        super();
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
        } else {
            return null;
        }
    }

    public WebDriver getDriver(String browserType) {
        try {
            driver = initBrowser(getBrowserType(browserType));
            logger.info(browserType + "Driver Launched !!");
            return driver;
        } catch (Exception e) {
            logger.error("Exception Occurred :" + e.getMessage());
            return null;
        }
    }

    public void navigateToUrl(String url) {
        try {
            driver.get(url);
            driver.manage().window().maximize();
            driver.manage().deleteAllCookies();
            logger.info("Navigate to " + url + " successfully !!!!");
        } catch (Exception e) {
            logger.error("Exception Occurred :" + e.getMessage());
        }
    }

    public static WebElement getLocator(String element, String locatorType) {
        WebElement webElement = null;
        try {
            if (element.isEmpty()) {
                logger.error("Invalid Locator String");
            } else {
                switch (locatorType.toLowerCase()) {
                    case "id":
                        webElement = driver.findElement(By.id(element));
                        break;
                    case "Class":
                        webElement = driver.findElement(By.className(element));
                        break;
                    case "xPath":
                        webElement = driver.findElement(By.xpath(element));
                        break;
                    case "css":
                        webElement = driver.findElement(By.cssSelector(element));
                        break;
                    case "link":
                        webElement = driver.findElement(By.linkText(element));
                        break;
                    case "partialLink":
                        webElement = driver.findElement(By.partialLinkText(element));
                        break;
                    case "name":
                        webElement = driver.findElement(By.name(element));
                        break;
                    case "tagname":
                        webElement = driver.findElement(By.tagName(element));
                        break;
                    default:
                        webElement = null;
                }
            }
        } catch (Exception e) {
            logger.error("Exception Occurred :" + e.getMessage());

        }
        return webElement;
    }

    public void setText(WebElement element, String text) {
        try {
            element.sendKeys(text);
            logger.info("Entered text " + text + " successfully !!!!");
        } catch (Exception e) {
            logger.error("Exception Occurred :" + e.getMessage());
        }
    }

    public String getText(WebElement element) {
        try {
            String text;
            text = element.getText();
            logger.info("Fetched text :" + text);
            return text;
        } catch (Exception e) {
            logger.error("Exception Occurred :" + e.getMessage());
            return e.getMessage();
        }
    }

    public void clickElement(WebElement element) {
        try {
            element.click();
            logger.info("Clicked on the element : " + getText(element));

        } catch (Exception e) {
            logger.error("Exception Occurred :" + e.getMessage());
        }
    }

    public void selectItemFromListBox(WebElement element, String key, String value) {
        try {
            Select select = new Select(element);
            switch (key.toLowerCase()) {
                case "value":
                    select.selectByValue(value);
                    break;
                case "text":
                    select.selectByVisibleText(value);
                    break;
                case "id":
                    select.selectByIndex(Integer.parseInt(value));
                    break;
                default:
                    logger.info("Invalid key entered!!!");
            }
            logger.info("Selected element : " + value);
        } catch (Exception e) {
            logger.error("Exception Occurred :" + e.getMessage());
        }
    }

    public void switchToWindow(int x) {
        try {
            System.out.println(driver.getWindowHandles().size());
            String childWindow = driver.getWindowHandles().toArray()[x].toString();
            driver.switchTo().window(childWindow);
        } catch (Exception e) {
            logger.error("Exception Occurred :" + e.getMessage());
        }
    }

    public void scrollToElement(By element) {
        javascriptExecutor = (JavascriptExecutor) driver;
        int xPoint = driver.findElement(element).getLocation().x;
        int yPoint = driver.findElement(element).getLocation().y;
        String command = String.format("window.scrollTo(%d, %d)", xPoint, yPoint);
        javascriptExecutor.executeScript(command);
    }

    public void iterateElementsAndClick(By element) {
        List<WebElement> resultList = driver.findElements(element);
        for (WebElement eachResult : resultList) {
            eachResult.click();
        }
    }

    public String getScreenshot() {
        File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String path = System.getProperty("user.dir") + "/screenshots/" + System.currentTimeMillis() + ".png";
        logger.info("Captured Screenshot of Failed Sceanrio,Navigate to following path. : " + System.getProperty("user.dir") + "/screenshots/");
        try {
            File destination = new File(path);
            FileUtils.copyFile(src, destination);
        } catch (IOException e) {
            logger.info("Capture Failed " + e.getMessage());
        }
        return path;
    }

    public String savePageSnapshot(String sImagePath) {
        try {

            TakesScreenshot oCamera;
            File oTmpFile, oImageFile;
            oImageFile = new File(sImagePath);

            if (new File(sImagePath).exists()) {
                throw new Exception("Image File already Exists");
            }

            oCamera = (TakesScreenshot) driver;
            oTmpFile = oCamera.getScreenshotAs(OutputType.FILE);
            oCamera.getScreenshotAs(OutputType.FILE);

            FileUtils.copyFile(oTmpFile, oImageFile);

            return "File got saved";

        } catch (Exception e) {
            logger.error("Exception Occurred :" + e.getMessage());
            return "File already exists";
        }
    }
}
