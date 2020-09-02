package BaseTest;

import Logger.Log;
import Report.CucumberReport;
import cucumber.api.event.ConcurrentEventListener;
import io.cucumber.core.api.Scenario;
import io.cucumber.java.Before;
import  cucumber.api.event.EventPublisher;
import  cucumber.api.event.TestRunFinished;

public class BaseTest implements ConcurrentEventListener {
    @Override
    public void setEventPublisher(EventPublisher eventPublisher) {
        eventPublisher.registerHandlerFor(TestRunFinished.class, e -> {
            DriverManager.GlobalDriverManager.destroyDriver();
            CucumberReport.build();
        });
    }

    @Before
    public void startUp(Scenario scenario) {
        Log.setScenario(scenario);
    }
}
