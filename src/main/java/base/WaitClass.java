package base;

import common.CommonClass;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class WaitClass extends CommonClass {
    WebDriverWait wait;

    public WaitClass() {
        super();
    }

    public void waitTillElementIsClickable(WebElement element, int timeoutSeconds) {
        try {
            logger.info("Waiting for " + timeoutSeconds + " the Element to Clickable !!!!");
            wait = new WebDriverWait(driver, Duration.ofMinutes(timeoutSeconds));
            wait.until(ExpectedConditions.elementToBeClickable(element));
        } catch (Exception e) {
            logger.error("Exception Occurred :" + e.getMessage());
        }
    }

    public void waitTillElementIsSelected(WebElement element, int timeoutSeconds) {
        try {
            logger.info("Waiting for " + timeoutSeconds + " the Element to Selected !!!!");
            wait = new WebDriverWait(driver, Duration.ofMinutes(timeoutSeconds));
            wait.until(ExpectedConditions.elementToBeSelected(element));
        } catch (Exception e) {
            logger.error("Exception Occurred :" + e.getMessage());
        }
    }

    public void waitTillElementToVisible(WebElement element, long timeoutSeconds) {
        try {
            logger.info("Waiting for " + timeoutSeconds + " Element to Visible !!!!");
            wait = new WebDriverWait(driver, Duration.ofMinutes(timeoutSeconds));
            wait.until(ExpectedConditions.visibilityOf(element));
        } catch (Exception e) {
            logger.error("Exception Occurred :" + e.getMessage());
        }
    }

    public void sleepForTime(int waitTime, String timeUnit) {
        try {
            logger.info("Waiting for " + waitTime + " " + timeUnit + " !!!!");
            int time = 0;
            switch (timeUnit.toLowerCase()) {
                case "seconds":
                    time = waitTime * 1000;
                    break;
                case "mintues":
                    time = waitTime * 60 * 1000;
                    break;
                case "milliseconds":
                    time = waitTime;
                    break;
                default:
                    time = 5;
            }
            Thread.sleep(time);
        } catch (Exception e) {
            logger.error("Exception Occurred :" + e.getMessage());
        }
    }

    public void waitImplicitly(int timeoutSeconds) {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(timeoutSeconds));
    }

    public void setPageLoadTimeOut(int lngPageLoadTimeOut) {
        this.lngPageLoadTimeOut = lngPageLoadTimeOut;
    }

    public void setElementDetectionTimeOut(int lngElementDetectionTimeOut) {
        this.lngElementDetectionTimeOut = lngElementDetectionTimeOut;
    }

    public void fluentWaitForElementToVisible(WebElement element, int timeInSec, int pollingInterval) {
        FluentWait<WebDriver> wait = new FluentWait<WebDriver>(driver)
                .withTimeout(Duration.ofSeconds(timeInSec))
                .pollingEvery(Duration.ofSeconds(pollingInterval))
                .ignoring(NoSuchElementException.class);
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    public void fluentWaitForElementToClickable(WebElement element, int timeInSec, int pollingInterval) {
        FluentWait<WebDriver> wait = new FluentWait<WebDriver>(driver)
                .withTimeout(Duration.ofSeconds(timeInSec))
                .pollingEvery(Duration.ofSeconds(pollingInterval))
                .ignoring(NoSuchElementException.class);
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }
}
