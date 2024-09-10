package core.utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import core.utilities.actions.ActionBase;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.io.FileHandler;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import core.driver.DriverManager;

import java.io.File;
import java.io.IOException;

public class TestListeners implements ITestListener {

    // Tip: All the Test related listeners are implemented here

    ExtentReports extent;
    ExtentTest test;

    String screenshotPath;

    public TestListeners(){
        screenshotPath = System.getProperty("user.dir") +
                PropertiesUtils.getPropertyQuick(PropertiesUtils.PropertyFile.CONFIG, "reports.screens.location") +
                "/Screenshot_" + ActionBase.getTimeStamp("MM-dd-yy HH-mm-ss") +".jpg";
    }

    public void onTestStart(ITestResult result) {
        test  = extent.createTest(result.getMethod().getMethodName());
    }

    public void onTestSuccess(ITestResult result) {
        test.log(Status.PASS, "Test Case: " + result.getMethod().getMethodName() + "is Passed.");
    }

    public void onTestFailure(ITestResult result) {
        test.log(Status.FAIL, "Test Case: " + result.getMethod().getMethodName() + "is Failed.");

        // Shows exception
        test.log(Status.FAIL, result.getThrowable());

        // Takes screenshot
        File src_screen = ((TakesScreenshot) DriverManager.getDriver()).getScreenshotAs(OutputType.FILE);
        File dest_screen = new File(screenshotPath);
        try{
            FileHandler.copy(src_screen, dest_screen);
            test.addScreenCaptureFromPath(screenshotPath, "Execution Failure Screen shot");
        }
        catch (IOException e){
            e.printStackTrace();
        }

    }

    public void onTestSkipped(ITestResult result) {}
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {}

    public void onTestFailedWithTimeout(ITestResult result) {}

    public void onStart(ITestContext context) {
        extent = ReportUtils.setUpExtentReport();
    }

    public void onFinish(ITestContext context) {
        extent.flush();
    }
}
