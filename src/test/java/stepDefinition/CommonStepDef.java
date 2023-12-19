package stepDefinition;

import base.BaseClass;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import pages.HomePage;

public class CommonStepDef extends BaseClass {
    //    BaseClass baseClass = new BaseClass();
    HomePage homePage = new HomePage();


    @Before
    public void setUp(Scenario scenario) throws Exception {
        suiteName = getFeatureName(scenario.getId());
    }

    @Given("User launches {} website in desired {}")
    public void user_launches_OpenWeatherMap_website_in_desired(String url, String browser) throws Exception {
        getDriver(browser);
        navigateToUrl(propertyFileUtil.getProperty(url.toLowerCase() + ".url"));
        waitClass.waitTillElementToVisible(homePage.openWeatherMapImg, 5);
    }

    @After
    public void tearDown(Scenario scenario) {
        if (driver != null) {
            driver.manage().deleteAllCookies();
            driver.quit();
            driver = null;
        }
    }

}
