package base;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import utilities.JsonUtil;
import utilities.PropertyFileUtil;


import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class BaseClass extends BrowserFactory {
    public WaitClass waitClass;
    public PropertyFileUtil propertyFileUtil;
    public JsonUtil jsonUtil;
    public VerifyClass verifyClass;
    public String suiteName;

    public BaseClass() {
        super();
        waitClass = new WaitClass();
        verifyClass = new VerifyClass();
        propertyFileUtil = new PropertyFileUtil();
        jsonUtil = new JsonUtil();
    }

    public WebDriver getDriver(String browserType) {
        try {
            driver = initBrowser(browserType);
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

    public WebElement getLocator(String element, String locatorType) {
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

    public void fillData(WebElement element, String data) {
        try {
            element.click();
            element.clear();
            element.sendKeys(data);
            logger.info("Entered text " + data + " successfully !!!!");
        } catch (ElementNotInteractableException e) {
            logger.error("Exception Occurred :" + e.getMessage());
        }
    }

    public void handleAlert(boolean action) {
        Alert alert = driver.switchTo().alert();
        if (action) {
            alert.accept();
            logger.info("Alert Accepted ");
        } else {
            alert.dismiss();
            logger.info("Alert rejected");
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

    public void clickElementUsingJavaScript(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", element);
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

    public void scrollToViewWebElement(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView();", element);
    }

    public void iterateElementsAndClick(By element) {
        List<WebElement> resultList = driver.findElements(element);
        for (WebElement eachResult : resultList) {
            eachResult.click();
        }
    }

    public String getScreenshot() {
        String path = System.getProperty("user.dir") + "/screenshots/" + "screenshot_" + System.currentTimeMillis() + ".png";
        try {
            File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            logger.info("Captured Screenshot of Failed scenario," +
                    "Navigate to following path. : " + System.getProperty("user.dir") + "/screenshots/");
            File destination = new File(path);
            FileUtils.copyFile(src, destination);
        } catch (IOException e) {
            logger.info("Capture Failed " + e.getMessage());
        }
        return path;
    }


    public static String getScenarioName(String scenarioName) {
        return scenarioName.replaceAll("\\s+", "");
    }

    public static String getFeatureName(String scenarioID) {
        String[] featureFolders = scenarioID.split(";");
        return featureFolders[0].replaceAll("-", "_");
    }

    public static String getStepName(String actualStepName) {
        return actualStepName.replace(' ', '_').replaceAll("\\s+", "_");
    }

    public String getCurrentDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy hh.mm.ss.sss");
        Date date = new Date();
        return dateFormat.format(date);
    }

    public void dateSelector(String exactDate, List<WebElement> dateToSelect) {
        String date = (exactDate.split("/"))[0];
        for (WebElement wb : dateToSelect) {
            if (wb.getText().equals(date)) {
                wb.click();
                break;
            } else {
                continue;
            }
        }
    }

    public void keyboardAction(CharSequence key, int keycode) {
        Actions action;
        Robot robot;
        try {
            //This block is handling Chrome as Action is not implemented in Geckodriver 0.13
            action = new Actions(driver);
            action.sendKeys(key).build().perform();
            action.release().perform();
            logger.info("Clicked on " + key + "key from chrome");
        } catch (Exception e) {
            //This block is for firefox, cannot work in headless mode
            try {
                robot = new Robot();
                robot.keyPress(keycode);
                robot.keyRelease(keycode);
                logger.info("Clicked on " + keycode + "key from firefox");
            } catch (AWTException E) {
                // TODO Auto-generated catch block
                logger.info(" Failed to perform action : " + e.getMessage());
            }
        }
    }
}
