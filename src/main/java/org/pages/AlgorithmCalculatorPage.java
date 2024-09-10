package org.pages;

import core.driver.DriverManager;
import core.utilities.actions.web.WebUI;
import io.restassured.RestAssured;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.reusable.variables.GlobalVariables;
import org.testng.Assert;

import java.util.HashMap;
import java.util.Map;

public class AlgorithmCalculatorPage extends PageObject {

    @FindBy(xpath = "//div[contains(@class,'peer-checked:bg-color-secondary')]")
    WebElement randomModeButton;

    @FindBy(css = "button[class='flex re-calculate-random ml-[16px] cursor-pointer']")
    WebElement buttonRefresh;

    @FindBy(css = "div[id='fromNode'] div[class=' css-hlgwow']")
    WebElement fromNodeSelect;

    @FindBy(css = "div[id='toNode'] div[class=' css-hlgwow']")
    WebElement toNodeSelect;

    @FindBy(xpath = "//button[normalize-space()='Clear']")
    WebElement buttonClear;

    public void clickEnableRandomButton() {
        WebUI.waitForElementPresent(By.xpath("//div[contains(@class,'peer-checked:bg-color-secondary')]"));
        WebUI.click(randomModeButton);
    }

    public void clickButtonRefresh() {
        WebUI.waitForElementPresent(By.cssSelector("button[class='flex re-calculate-random ml-[16px] cursor-pointer']"));
        WebUI.click(buttonRefresh);
    }

    public void testUIDesignSpecification() {
        WebUI.verifyTextPresent("Dijkstra’s Algorithm Calculator");
        WebUI.verifyTextPresent("Discovering Optimal Routes Through Nodes Using Dijkstra's Method");

        WebElement fromNodeSelector = WebUI.findElement(By.cssSelector("div[id='fromNode'] div[class=' css-hlgwow']"));
        WebElement toNodeSelector = WebUI.findElement(By.cssSelector("div[id='toNode'] div[class=' css-hlgwow']"));
        WebElement calculateButton = WebUI.findElement(By.xpath("//button[normalize-space()='Calculate']"));
        WebElement clearButton = WebUI.findElement(By.xpath("//button[normalize-space()='Clear']"));

        Assert.assertTrue(fromNodeSelector.isDisplayed(), "FROM node selector not visible.");
        Assert.assertTrue(toNodeSelector.isDisplayed(), "TO node selector not visible.");
        Assert.assertTrue(calculateButton.isDisplayed(), "Calculate button not visible.");
        Assert.assertTrue(clearButton.isDisplayed(), "Clear button not visible.");

        WebUI.verifyElementPresent(By.xpath("//div[contains(@class,'peer-checked:bg-color-secondary')]"));
        WebUI.verifyTextPresent("Enable Random Mode");
        WebUI.click(randomModeButton);
        WebUI.verifyElementPresent(By.xpath("//img[contains(@src,'/refresh.svg')]"));

        System.out.println("UI design elements are visible and meet specifications.");
    }

    public void testRandomModeSelector() {
        String fromNode = WebUI.findElement(By.id("fromNode")).getAttribute("class");
        String toNode = WebUI.findElement(By.id("toNode")).getAttribute("class");

        clickEnableRandomButton();
        WebUI.delay(GlobalVariables.DELAY_MINI_SMALL);

        String fromNodeNew = WebUI.findElement(By.id("fromNode")).getAttribute("class");
        String toNodeNew = WebUI.findElement(By.id("toNode")).getAttribute("class");

        Assert.assertNotEquals(fromNode, fromNodeNew);
        Assert.assertNotEquals(toNode, toNodeNew);
    }

    public void testRefreshRandomNodes() {
        testRandomModeSelector();

        String initialFromNodeValue = WebUI.findElement(By.cssSelector("div[id='fromNode'] div[class=' css-olqui2-singleValue']")).getText();
        String initialToNodeValue = WebUI.findElement(By.cssSelector("div[id='toNode'] div[class=' css-olqui2-singleValue']")).getText();

        System.out.println("Initial FROM Node: " + initialFromNodeValue);
        System.out.println("Initial TO Node: " + initialToNodeValue);

        for (int i = 0; i < 3; i++) {
            clickButtonRefresh();
            WebUI.delay(GlobalVariables.DELAY_DEFAULT);

            String newFromNodeValue = WebUI.findElement(By.cssSelector("div[id='fromNode'] div[class=' css-olqui2-singleValue']")).getText();
            String newToNodeValue = WebUI.findElement(By.cssSelector("div[id='toNode'] div[class=' css-olqui2-singleValue']")).getText();

            System.out.println("New FROM Node after refresh " + (i + 1) + ": " + newFromNodeValue);
            System.out.println("New TO Node after refresh " + (i + 1) + ": " + newToNodeValue);

            int retryCount = 0;
            while (newToNodeValue.equals(initialToNodeValue) && retryCount < 2) {
                System.out.println("Retrying as TO node did not change...");
                clickButtonRefresh();
                WebUI.delay(GlobalVariables.DELAY_DEFAULT);
                newToNodeValue = WebUI.findElement(By.cssSelector("div[id='toNode'] div[class=' css-olqui2-singleValue']")).getText();
                retryCount++;
            }

            Assert.assertNotEquals(newFromNodeValue, initialFromNodeValue, "FROM Node value did not change after refresh " + (i + 1));
            Assert.assertNotEquals(newToNodeValue, initialToNodeValue, "TO Node value did not change after refresh " + (i + 1));

            initialFromNodeValue = newFromNodeValue;
            initialToNodeValue = newToNodeValue;
        }
    }

    private void calculateShortestPath(String fromNode, String toNode, Map<String, String> pathResults, Map<String, String> distanceResults) {
        WebUI.delay(GlobalVariables.DELAY_MINI_SMALL);
        WebDriver driver = DriverManager.getDriver();

        WebUI.click(fromNodeSelect);
        WebElement fromNodeElement = WebUI.findElement(By.xpath("//div[@id='react-select-2-listbox']//div[contains(text(),'" + fromNode + "')]"));
        fromNodeElement.click();

        WebUI.click(toNodeSelect);
        WebElement toNodeElement = WebUI.findElement(By.xpath("//div[@id='react-select-3-listbox']//div[contains(text(),'" + toNode + "')]"));
        toNodeElement.click();

        WebElement calculateButton = driver.findElement(By.xpath("//button[normalize-space()='Calculate']"));
        calculateButton.click();

        WebElement pathResult = driver.findElement(By.cssSelector("p[class='mb-[24px] text-sm text-[#5A5B5D]']"));
        String resultText = pathResult.getText();

        Assert.assertNotNull(resultText, "The result text (traversed nodes) is null.");
        Assert.assertFalse(resultText.isEmpty(), "The result text (traversed nodes) is empty.");

        String[] nodeNames = resultText.split(",\\s*");
        Assert.assertTrue(nodeNames.length > 0, "No nodes were traversed.");

        WebElement distanceDisplay = driver.findElement(By.cssSelector("strong"));
        String distanceText = distanceDisplay.getText();

        System.out.println("Captured Distance Text: " + distanceText);
        if (distanceText.equals("-1")) {
            System.err.println("Invalid Distance captured for path from " + fromNode + " to " + toNode);
            return;
        }

        int distance = Integer.parseInt(distanceText);
        System.out.println("Parsed Distance: " + distance);
        Assert.assertTrue(distance > 0, "Distance should be greater than 0, but found: " + distance);

        String nodePair = fromNode + " -> " + toNode;
        pathResults.put(nodePair, resultText);
        distanceResults.put(nodePair, distanceText);

        System.out.println("Traversed Nodes from " + fromNode + " to " + toNode + ": " + resultText);
        System.out.println("Total Distance from " + fromNode + " to " + toNode + ": " + distanceText);

        ShortestPathData dto = new ShortestPathData(nodeNames, distance);
        Assert.assertNotNull(dto, "DTO creation failed.");
        injectDTOIntoBrowserConsole(driver, dto);

        WebUI.click(buttonClear);
    }

    private void injectDTOIntoBrowserConsole(WebDriver driver, ShortestPathData dto) {
        String script = "console.log('ShortestPathData: " + dto.toString() + "');";
        ((JavascriptExecutor) driver).executeScript(script);
    }

    public void testAllShortestPaths() {
        String[] nodes = {"A", "B", "C", "D", "E", "F", "G", "I"};

        Map<String, String> pathResults = new HashMap<>();
        Map<String, String> distanceResults = new HashMap<>();

        for (String fromNode : nodes) {
            for (String toNode : nodes) {
                if (!fromNode.equals(toNode)) {
                    calculateShortestPath(fromNode, toNode, pathResults, distanceResults);
                    sendDtoToApiEcho(fromNode, toNode, pathResults.toString(), distanceResults.toString());
                }
            }

        }
        for (Map.Entry<String, String> entry : pathResults.entrySet()) {
            String nodePair = entry.getKey();
            String path = entry.getValue();
            String distance = distanceResults.get(nodePair);
            System.out.println("Shortest path from " + nodePair + ": " + path + " | Total Distance: " + distance);
        }

    }

    private void sendDtoToApiEcho(String fromNode, String toNode, String traversedNodes, String totalDistance) {
        String shortestPathDto = "{ \"fromNode\": \"" + fromNode + "\", \"toNode\": \"" + toNode + "\", " +
                "\"nodeNames\": \"" + traversedNodes + "\", \"distance\": " + totalDistance + " }";
        RestAssured.given()
                .header("Content-Type", "application/json")
                .body(shortestPathDto)
                .when()
                .post("https://echo.free.beeceptor.com/sample-request?author=beeceptor")
                .then()
                .statusCode(200)
                .log().all();
    }

    public void testViewportScaling() {
        WebDriver driver = DriverManager.getDriver();
        driver.manage().window().setSize(new org.openqa.selenium.Dimension(1920, 1080));
        WebElement formContainer = driver.findElement(By.cssSelector("div[class='calculator-card-left py-[32px] pl-[32px] pr-[24px]']"));
        Assert.assertTrue(formContainer.isDisplayed(), "Form not displayed on desktop viewport.");

        driver.manage().window().setSize(new org.openqa.selenium.Dimension(768, 1024));
        Assert.assertTrue(formContainer.isDisplayed(), "Form not displayed on tablet viewport.");

        driver.manage().window().setSize(new org.openqa.selenium.Dimension(375, 667));
        Assert.assertTrue(formContainer.isDisplayed(), "Form not displayed on mobile viewport.");
    }

    public void testInputValidations() {
        WebDriver driver = DriverManager.getDriver();
        WebUI.verifyTextPresent("Dijkstra’s Algorithm Calculator");
        WebUI.verifyTextPresent("Discovering Optimal Routes Through Nodes Using Dijkstra's Method");

        WebElement calculateButton = driver.findElement(By.xpath("//button[normalize-space()='Calculate']"));
        calculateButton.click();
        WebUI.verifyElementPresent(By.xpath("p[class='text-[13px] text-center text-red-800']"));
        WebUI.verifyTextPresent("Please select valid FROM and TO nodes.");
        String invalidError = WebUI.findElement(By.cssSelector("p[class='text-[13px] text-center text-red-800']")).getText();
        Assert.assertEquals(invalidError, "Please select valid FROM and TO nodes.");
        WebUI.click(buttonClear);

        String[] nodes = {"A", "B", "C", "D", "E", "F", "G", "I"};
        for (String fromNode : nodes) {
            WebUI.click(fromNodeSelect);
            WebElement fromNodeElement = WebUI.findElement(By.xpath("//div[@id='react-select-2-listbox']//div[contains(text(),'" + fromNode + "')]"));
            if (fromNodeElement != null) {
                fromNodeElement.click();
                break;
            }
        }
        calculateButton.click();
        WebUI.verifyTextPresent("Please select valid FROM and TO nodes.");
        WebUI.click(buttonClear);
        for (String toNode : nodes) {
            WebUI.click(toNodeSelect);
            WebElement toNodeElement = WebUI.findElement(By.xpath("//div[@id='react-select-3-listbox']//div[contains(text(),'" + toNode + "')]"));
            if (toNodeElement != null) {
                toNodeElement.click();
                break;
            }
        }
        calculateButton.click();
        WebUI.verifyTextPresent("Please select valid FROM and TO nodes.");
    }

    public void testDataPersistenceAfterRefresh() {
        WebDriver driver = DriverManager.getDriver();

        WebElement fromNodeSelect = driver.findElement(By.id("fromNode"));
        fromNodeSelect.click();
        WebElement fromNodeOption = driver.findElement(By.xpath("//div[contains(text(),'A')]"));
        fromNodeOption.click();

        WebElement toNodeSelect = driver.findElement(By.id("toNode"));
        toNodeSelect.click();
        WebElement toNodeOption = driver.findElement(By.xpath("//div[contains(text(),'D')]"));
        toNodeOption.click();

        WebElement calculateButton = driver.findElement(By.xpath("//button[normalize-space()='Calculate']"));
        calculateButton.click();

        WebElement pathResult = driver.findElement(By.cssSelector("p[class='mb-[24px] text-sm text-[#5A5B5D]']"));
        String calculatedPath = pathResult.getText();
        WebElement distanceResult = driver.findElement(By.cssSelector("strong"));
        String calculatedDistance = distanceResult.getText();

        Assert.assertNotNull(calculatedPath, "Calculated path should not be null.");
        Assert.assertNotNull(calculatedDistance, "Calculated distance should not be null.");

        driver.navigate().refresh();

        WebElement fromNodeSelectAfterRefresh = driver.findElement(By.id("fromNode"));
        String fromNodeAfterRefresh = fromNodeSelectAfterRefresh.getText();

        WebElement toNodeSelectAfterRefresh = driver.findElement(By.id("toNode"));
        String toNodeAfterRefresh = toNodeSelectAfterRefresh.getText();

        Assert.assertEquals(fromNodeAfterRefresh, "Select", "FROM node should reset after refresh.");
        Assert.assertEquals(toNodeAfterRefresh, "Select", "TO node should reset after refresh.");
    }


}