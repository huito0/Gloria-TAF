package Pages.ExamplePage;

import CLIOptions.GlobalConfig;
import DriverManager.GlobalDriverManager;
import Pages.AbstractPage;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.yandex.qatools.htmlelements.element.HtmlElement;

import static Logger.Log.info;

public class RamblerPage extends AbstractPage {
    public static final String URL = "https://www.rambler.ru";

    @FindBy(xpath = "//*[contains(@data-logo, 'header')]")
    private HtmlElement logo;

    @Override
    public void waitForPageLoadedCompletely() {
        info("Wait for home page loaded completely");
        new WebDriverWait(GlobalDriverManager.getDriver(), GlobalConfig.getTimeout())
                .until((ExpectedCondition<Boolean>) d -> logo.isDisplayed());
    }

    public boolean isLogoVisible() {
        return logo.isDisplayed();
    }
}
