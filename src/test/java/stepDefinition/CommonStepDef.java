package stepDefinition;

import Base.BaseClass;
import io.cucumber.java.en.When;

public class CommonStepDef {
    BaseClass baseClass=new BaseClass();

    @When("User launch {} on {}")
    public void userLaunchOn(String url,String browser) {
        baseClass.navigateToUrl(url,browser);
    }

    @When("User close browser")
    public void userCloseBrowser() {
        baseClass.quitDriver();
    }


}
