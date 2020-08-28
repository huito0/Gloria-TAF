package Pages;

import DriverManager.GlobalDriverManager;

public abstract class AbstractPage {

    public abstract void waitForPageLoadedCompletely();

    public void refreshPage() {
        GlobalDriverManager.getDriver().navigate().refresh();
    }
}
