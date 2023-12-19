package pages;

import base.BaseClass;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CityTempDetailsPage extends BaseClass {


    @FindBy(css = "div#weather-widget h2")
    public WebElement weatherWidgetHeader;

    @FindBy(css = "div#weather-widget h3")
    public WebElement weatherWidgetTempDetails;

    @FindBy(css = "div.weather-forecast-hourly-graphic h2")
    public WebElement foreCastHeader;

    public CityTempDetailsPage() {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
}
