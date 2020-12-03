package StepDefs;

import CLIOptions.GlobalConfig;
import DriverManager.GlobalDriverManager;
import Pages.ExamplePage.RamblerPage;

import static Logger.Log.info;

public abstract class AbstractStepDefs {
    //>> example
    private ThreadLocal<RamblerPage> ramblerPage = new ThreadLocal<>();

    public RamblerPage onRamblerPage() {
        if (ramblerPage.get() == null) {
            ramblerPage.set(new RamblerPage());
        }
        return ramblerPage.get();
    }
    //<< example

    protected void openPage(String path) {
        String url = GlobalConfig.getEnvironment().getHostAddress() + path;
        GlobalDriverManager.getDriver().get(url);
        info("Open page with path: " + url);
    }
}
