package BaseTest;

import Logger.Log;
import Report.CucumberReport;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.plugin.ConcurrentEventListener;
import io.cucumber.plugin.event.EventPublisher;
import io.cucumber.plugin.event.TestRunFinished;

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
