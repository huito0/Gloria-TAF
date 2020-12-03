package Pages;

import BlocksFactory.HtmlBlockDecorator;
import DriverManager.GlobalDriverManager;
import Logger.Log;
import org.openqa.selenium.support.PageFactory;
import ru.yandex.qatools.htmlelements.loader.decorator.HtmlElementLocatorFactory;

public abstract class AbstractPage {
    public AbstractPage() {
        PageFactory.initElements(new HtmlBlockDecorator(new HtmlElementLocatorFactory(GlobalDriverManager.getDriver())), this);
    }

    public void waitForPageLoadedCompletely() {
        Log.warn("Wait method doesn't specified for that page");
    }

    public void refreshPage() {
        GlobalDriverManager.getDriver().navigate().refresh();
    }
}
