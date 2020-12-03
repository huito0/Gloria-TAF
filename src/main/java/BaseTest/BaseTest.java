package BaseTest;

import DriverManager.GlobalDriverManager;
import Logger.Log;
import Report.CucumberReport;
import Utils.ScreenshotUtil;
import cucumber.api.event.ConcurrentEventListener;
import cucumber.api.event.EventPublisher;
import cucumber.api.event.TestRunFinished;
import io.cucumber.core.api.Scenario;
import io.cucumber.java.Before;

public class BaseTest implements ConcurrentEventListener {
    @Override
    public void setEventPublisher(EventPublisher eventPublisher) {
        eventPublisher.registerHandlerFor(TestRunFinished.class, e -> {
            GlobalDriverManager.destroyDriver();
            CucumberReport.build();
        });
    }

    @Before
    public void startUp(Scenario scenario) {
        Log.setScenario(scenario);
        ScreenshotUtil.setScenario(scenario);
    }
}
