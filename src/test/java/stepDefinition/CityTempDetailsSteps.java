package stepDefinition;

import base.BaseClass;
import io.cucumber.java.en.Then;
import junit.framework.Assert;
import pages.CitySearchResultPage;

public class CityTempDetailsSteps extends BaseClass {
    CitySearchResultPage citySearchResultPage = new CitySearchResultPage();

    @Then("^Weather details should be updated$")
    public void weather_details_should_be_updated() throws Throwable {
        waitClass.fluentWaitForElementToClickable(citySearchResultPage.actualCityFound, 5, 1);
        clickElementUsingJavaScript(citySearchResultPage.actualCityFound);




        /*Assert.assertTrue(wutil.validateElement(cityTempDetailspage.get_weatherWidgetHeader()));
        Assert.assertTrue(wutil.validateElement(cityTempDetailspage.get_weatherWidgetTempDetails()));
        Assert.assertTrue(wutil.validateElement(cityTempDetailspage.get_foreCastHeader()));*/

    }
}
