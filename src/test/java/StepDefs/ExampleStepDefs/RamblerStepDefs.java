package StepDefs.ExampleStepDefs;

import DriverManager.GlobalDriverManager;
import Pages.ExamplePage.RamblerPage;
import StepDefs.AbstractStepDefs;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

public class RamblerStepDefs extends AbstractStepDefs {
    @Given("I open Rambler Page")
    public void givenIOpenRamblerPage() {
        GlobalDriverManager.getDriver().get(RamblerPage.URL);
        onRamblerPage().waitForPageLoadedCompletely();
    }

    @When("I simulate work process")
    public void whenISimulateWorkProcess() {
        onRamblerPage().refreshPage();
        onRamblerPage().waitForPageLoadedCompletely();
    }

    @Then("I check that page is opened")
    public void thenICheckThatPageOpened() {
        Assert.assertTrue("Rambler logo should be displayed", onRamblerPage().isLogoVisible());;
    }
}
