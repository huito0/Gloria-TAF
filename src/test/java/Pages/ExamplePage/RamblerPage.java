package Pages.ExamplePage;

import CLIOptions.GlobalConfig;
import DriverManager.GlobalDriverManager;
import Pages.AbstractPage;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.yandex.qatools.htmlelements.element.HtmlElement;
import ru.yandex.qatools.htmlelements.loader.decorator.HtmlElementDecorator;
import ru.yandex.qatools.htmlelements.loader.decorator.HtmlElementLocatorFactory;

public class RamblerPage extends AbstractPage {
    public static final String URL = "https://www.rambler.ru";

    @FindBy(xpath = "//*[contains(@data-logo, 'header::logo_rambler')]")
    private HtmlElement logo;

    public RamblerPage() {
        PageFactory.initElements(new HtmlElementDecorator(new HtmlElementLocatorFactory(GlobalDriverManager.getDriver())), this);
    }

    @Override
    public void waitForPageLoadedCompletely() {
        new WebDriverWait(GlobalDriverManager.getDriver(), GlobalConfig.getTimeout())
                .until((ExpectedCondition<Boolean>) d -> logo.isDisplayed());
    }

    public boolean isLogoVisible() {
        return logo.isDisplayed();
    }
}
