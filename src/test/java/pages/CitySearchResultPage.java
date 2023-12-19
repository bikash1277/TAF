package pages;

import base.BaseClass;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CitySearchResultPage extends BaseClass {

    @FindBy(xpath = "//div[@class='alert alert-warning' and contains(text(),'Not found')]")
    public WebElement cityNotFoundError;

    @FindBy(xpath = "//div[@id='forecast_list_ul']//td[2]//b/a")
    public WebElement actualCityFound;

    public CitySearchResultPage() {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
}
