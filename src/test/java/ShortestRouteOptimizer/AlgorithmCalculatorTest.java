package ShortestRouteOptimizer;

import org.base.TestBaseWeb;
import org.pages.AlgorithmCalculatorPage;
import org.testng.annotations.Test;

public class AlgorithmCalculatorTest extends TestBaseWeb {

    AlgorithmCalculatorPage algorithmCalculatorPage;

    @Test(priority = 1, description = "Verifying Adherence to the V1 UI Design Specification" , enabled = true)
    public void testUIDesignSpecification(){
        algorithmCalculatorPage = new AlgorithmCalculatorPage();
        algorithmCalculatorPage.testUIDesignSpecification();
    }

    @Test(priority = 2, description = "Verify the Random Option for FROM and TO Nodes selections After Each Click" , enabled = true)
    public void testRandomModeSelector(){
        algorithmCalculatorPage = new AlgorithmCalculatorPage();
        algorithmCalculatorPage.testRandomModeSelector();
    }

    @Test(priority = 3, description = "Verify the Random Option after Refreshing the random nodes." , enabled = true)
    public void testRefreshRandomNodes(){
        algorithmCalculatorPage = new AlgorithmCalculatorPage();
        algorithmCalculatorPage.testRefreshRandomNodes();
    }

    @Test(priority = 4, description = "Verifying FROM and TO nodes selection, All path calculation including node clearing, API echo response OK, Browser console output of result." , enabled = true)
    public void testCalculatePathBetweenNodes(){
        algorithmCalculatorPage = new AlgorithmCalculatorPage();
        algorithmCalculatorPage.testAllShortestPaths();
    }

    @Test(priority = 5, description = "Verifying Viewport Scaling Responsiveness of UI" , enabled = true)
    public void testViewportScaling(){
        algorithmCalculatorPage = new AlgorithmCalculatorPage();
        algorithmCalculatorPage.testViewportScaling();
    }

    @Test(priority = 6, description = "Verify that the application validates user inputs correctly." , enabled = true)
    public void testInputValidations(){
        algorithmCalculatorPage = new AlgorithmCalculatorPage();
        algorithmCalculatorPage.testInputValidations();
    }

    @Test(priority = 7, description = "Verify that the application either retains or resets the state node selections and calculated path) after the browser is refreshed" , enabled = true)
    public void testDataPersistenceAfterRefresh(){
        algorithmCalculatorPage = new AlgorithmCalculatorPage();
        algorithmCalculatorPage.testDataPersistenceAfterRefresh();
    }



}
