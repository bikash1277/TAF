package stepDefinition;

import base.BaseClass;
import io.cucumber.java.en.Then;
import pages.CitySearchResultPage;

public class CitySearchResultSteps extends BaseClass {

    CitySearchResultPage citySearchResultPage = new CitySearchResultPage();

    @Then("^No Weather should be found$")
    public void no_Weather_should_be_found() throws Exception {
        waitClass.waitTillElementToVisible(citySearchResultPage.cityNotFoundError, 5);
    }


    @Then("^Proper city details should be found$")
    public void proper_weather_details_should_be_found() throws Throwable {
        waitClass.waitTillElementToVisible(citySearchResultPage.actualCityFound, 5);

    }
}
