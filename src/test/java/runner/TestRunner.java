package runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
//        plugin = {"pretty", "html:target/cucumber", "json:target/cucumber.json"},
        features = "src/test/resources/Feature",
        glue = {"stepDefinition"},
        strict = true,
        monochrome = true,
        tags = "@Test001"
)
public class TestRunner {
}
