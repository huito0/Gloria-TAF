package StepDefs.ExampleStepDefs;

import DriverManager.GlobalDriverManager;
import Pages.ExamplePage.RamblerPage;
import StepDefs.AbstractStepDefs;
import cucumber.api.PendingException;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

import static Logger.Log.info;


public class RamblerStepDefs extends AbstractStepDefs {
    @Given("I open Rambler Page")
    public void givenIOpenRamblerPage() {
        GlobalDriverManager.getDriver().get(RamblerPage.URL);
        info("Open page with url: " + RamblerPage.URL);
        onRamblerPage().waitForPageLoadedCompletely();
    }

    @When("I simulate work process")
    public void whenISimulateWorkProcess() {
        info("Refresh home page");
        onRamblerPage().refreshPage();
        onRamblerPage().waitForPageLoadedCompletely();
    }

    @Then("I check that page is opened")
    public void thenICheckThatPageOpened() {
        info("check that Rambler logo is visible");
        Assert.assertTrue("Rambler logo should be displayed", onRamblerPage().isLogoVisible());
    }

    @When("^I fail scenario$")
    public void whenIFailScenario() {
        Assert.fail();
    }

    @When("^I mark step as pending$")
    public void whenIMarkStepAsPending() {
        throw new PendingException("Just mark scenario as pending");
    }
}
