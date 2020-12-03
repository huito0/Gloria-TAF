package Report;


import Utils.ScreenshotUtil;
import cucumber.api.Result;
import cucumber.api.event.ConcurrentEventListener;
import cucumber.api.event.EventPublisher;
import cucumber.api.event.TestStepFinished;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

//That class uses as plugin for cucumber
public class ScreenshotPublisher implements ConcurrentEventListener {
    @Override
    public void setEventPublisher(EventPublisher publisher) {
        publisher.registerHandlerFor(TestStepFinished.class, step -> {
            if (step.result.getStatus().equals(Result.Type.FAILED)) {
                ScreenshotUtil.makeScreenShot(ShootingStrategies.simple(), "Screenshot");
                ScreenshotUtil.makeScreenShot(ShootingStrategies.viewportPasting(100), "Full_page_screenshot");
            }
        });
    }
}