package stepDefinition;

import base.BaseClass;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import junit.framework.Assert;
import pages.HomePage;


public class HomeSteps extends BaseClass {
    HomePage homePage = new HomePage();


    @When("User provides a input as {}")
    public void user_provides_a_valid_city_input(String input) throws Exception {
        waitClass.waitTillElementIsClickable(homePage.searchBoxForCityEntry, 5);
        setText(homePage.searchBoxForCityEntry, jsonUtil.getDataFromJson(suiteName, input));
    }

    @When("User clicks on search button")
    public void user_clicks_on_search_button() throws Exception {
        clickElement(homePage.searchButtonForCity);
    }


}

