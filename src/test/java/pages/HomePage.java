package pages;

import base.BaseClass;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage extends BaseClass {
    @FindBy(xpath = "//img[@src='/themes/openweathermap/assets/vendor/owm/img/logo_OpenWeatherMap_orange.svg']")
    public WebElement openWeatherMapImg;

    @FindBy(css = "input#q:nth-child(2)")
    public WebElement searchBoxForCityEntry;

    @FindBy(css = "button[class='btn btn-orange']")
    public WebElement searchButtonForCity;

    public HomePage() {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
}
