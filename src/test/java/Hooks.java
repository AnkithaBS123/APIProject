import cucumber.api.java.After;
import cucumber.api.java.Before;

import static utils.DriverUtils.initDriver;
import static utils.DriverUtils.tearDown;

public class Hooks {
    @Before
    public void setUp(){
        if(!System.getenv("testType").toLowerCase().equalsIgnoreCase("API")) {
            initDriver();
        }
    }

    @After
    public void teardown(){
        tearDown();
    }
}

