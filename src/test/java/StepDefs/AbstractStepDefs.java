package StepDefs;

import Pages.ExamplePage.RamblerPage;

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
}
