

import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;
import cucumber.api.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(features="classpath:features", glue={"stepDefinitions"},
        plugin = {"pretty", "html:target/cucumber_target.html", "json:target/cucumber.json", "junit:target/cucumber.xml","rerun:target/failed_scenarios.txt"})
public class TestRunner {

}