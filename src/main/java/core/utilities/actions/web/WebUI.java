package core.utilities.actions.web;

import core.utilities.actions.ActionBase;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import core.utilities.PropertiesUtils;
import java.time.Duration;
import java.util.List;

public class WebUI extends ActionBase {

    protected static final Duration timeout = Duration.ofDays(Integer.parseInt(PropertiesUtils.getPropertyQuick(PropertiesUtils.PropertyFile.WEB, "execution.TIME_OUT_DEFAULT")));
    protected static final Duration pageloadTimeout = Duration.ofDays(Integer.parseInt(PropertiesUtils.getPropertyQuick(PropertiesUtils.PropertyFile.WEB, "execution.TIME_OUT_PAGELOAD")));
    protected static final Duration sleepTimeout = Duration.ofDays(Integer.parseInt(PropertiesUtils.getPropertyQuick(PropertiesUtils.PropertyFile.WEB, "execution.TIME_OUT_SLEEP")));

    // Browser Actions
    public static void closeBrowser(){
        core.driver.DriverManager.getDriver().close();
    } // Close browser

    public static void delay(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    } // Delay script

    public static String getUrl() {
        String URL = core.driver.DriverManager.getDriver().getCurrentUrl();
        return URL;
    } // Get current URL

    public static void navigateToUrl(String URL) {
        core.driver.DriverManager.getDriver().manage().deleteAllCookies();
        core.driver.DriverManager.getDriver().get(URL);
    } //Navigate to url


    // Web Element Actions
    public static void clearText(WebElement element){
        element.clear();
    } // Click Clear Text

    public static void click(WebElement element){
        element.click();
    } // Click function

    public static String getText(WebElement element) {
        String text = element.getText();
        return text;
    } // Get Text

    public static void scrollToElement(WebElement element) {
        ((JavascriptExecutor) core.driver.DriverManager.getDriver()).executeScript("arguments[0].scrollIntoView(true);", element);
    } // Scroll To Element

    public static void sendKeys(WebElement element, String text) {
        element.sendKeys(text);
    } //Send Keys to an input field

    public static void setText(WebElement element, String text) {
        element.clear();
        element.sendKeys(text);
    } // Clear existing values and Set Text


    // Web Element Verification
    public static void verifyElementClickable(WebElement element) {
        WebDriverWait wait = new WebDriverWait(core.driver.DriverManager.getDriver(), timeout);
        wait.until(ExpectedConditions.elementToBeClickable(element));
    } //Verify Element Clickable

    public static void verifyElementEnabled(WebElement element) {
        boolean value = element.isEnabled();
        Assert.assertEquals(value, true, "Element is visible!!!");
    } //Verify Element enabled in the document

    public static void verifyElementDisabled(WebElement element) {
        boolean value = element.isEnabled();
        Assert.assertEquals(value, false, "Element is not visible!!!");
    } //Verify Element disabled in the document

    public static void verifyElementVisible(WebElement element) {
        boolean value = element.isDisplayed();
        Assert.assertEquals(value, true, "Element is not visible!!!");
    } //Verify Element Visible

    public static boolean verifyElementNotPresent(By element) {
        boolean value = verifyElementPresent(element);
        Assert.assertEquals(value, false, "Element is present in the document!!!");
        return value;
    } //Verify Element is not present in the document

    public static void verifyTextPresentInElement(WebElement element, String text) {
        byte[] textInElement  = element.getText().getBytes();
        boolean value;
        if((value = textInElement.length > 0)){
            Assert.assertEquals(value, true, "Element is not present in the document!!!");
        }
    } //Verify Text Present In Element

    public static void verifyTextPresent(String text) {
        Assert.assertTrue(core.driver.DriverManager.getDriver().getPageSource().contains(text));
    } // Verify Text Present in the current page

    public static void verifyTextNotPresent(String text) {
        Assert.assertFalse(core.driver.DriverManager.getDriver().getPageSource().contains(text));
    } // Verify Text Not Present in the current page

    public static boolean verifyElementPresent(By by){
        int results = findElements(by).size();
        if(results > 0)
            return true;
        else
            return false;
    } // Checks for element occurrences in the web page and returns the result

    public static void verifyElementPresent(WebElement element, int timeout){
        boolean value;
        WebUI.delay(timeout);
        if(value = element.isDisplayed()) {
            Assert.assertEquals(value, true, "Element is not present in the document!!!");
        }
    } // Verify element present in the document

    // Waits
    public static void waitForElementPresent(By element) {
        WebDriverWait wait = new WebDriverWait(core.driver.DriverManager.getDriver(), timeout);
        WebElement webElement = wait.until(ExpectedConditions.presenceOfElementLocated(element));
    } //Wait For Element Present in the Document

    public static void waitForElementVisible(WebElement webElement) {
        WebDriverWait wait = new WebDriverWait(core.driver.DriverManager.getDriver(), timeout);
        wait.until(ExpectedConditions.visibilityOf(webElement));
    } //Wait For Element Visible

    public static WebElement waitForElementClickable(WebElement element) {
        WebDriverWait wait = new WebDriverWait(core.driver.DriverManager.getDriver(), timeout);
        wait.until(ExpectedConditions.elementToBeClickable(element));
        return element;
    } // Wait For Element Clickable

    public static void waitForPageLoad() {
        ExpectedCondition<Boolean> expectation = new
                ExpectedCondition<Boolean>() {
                    public Boolean apply(WebDriver driver) {
                        return ((JavascriptExecutor) driver).executeScript("return document.readyState").toString().equals("complete");
                    }
                };
        try {
            Thread.sleep(sleepTimeout);
            WebDriverWait wait = new WebDriverWait(core.driver.DriverManager.getDriver(), pageloadTimeout);
            wait.until(expectation);
        } catch (Throwable error) {
            Assert.fail("Timeout waiting for Page Load Request to complete." + error.getMessage());
        }
    } //wait For Page Load

    public static WebElement findElement(By element){
        return core.driver.DriverManager.getDriver().findElement(element);
    } // find element

    public static List<WebElement> findElements(By element){
        return core.driver.DriverManager.getDriver().findElements(element);
    } // find elements




}