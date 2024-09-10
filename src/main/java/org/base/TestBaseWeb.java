package org.base;

import core.driver.BrowserFactory;
import core.driver.DriverFactory;
import core.driver.DriverManager;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.reusable.variables.GlobalVariables;
import java.util.concurrent.TimeUnit;

public class TestBaseWeb {

    BrowserFactory browserFactory = new BrowserFactory();

    @BeforeMethod
    public void initialization() {
        DriverFactory.getInstance().setDriver(browserFactory.createBrowserInstance());
        WebDriver driver = DriverFactory.getInstance().getDriver();

        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();

        driver.manage().timeouts().pageLoadTimeout(GlobalVariables.TIME_OUT_PAGELOAD, TimeUnit.MILLISECONDS);
        driver.manage().timeouts().implicitlyWait(GlobalVariables.TIME_OUT_DEFAULT, TimeUnit.MILLISECONDS);
        /*Dimension dimension = new Dimension(1555, 883);
        driver.manage().window().setSize(dimension);*/

        launchBrowser();
        System.out.println("Browser resolution : " + driver.manage().window().getSize());
    }

    public void launchBrowser() {
        DriverManager.getDriver().get(GlobalVariables.URL);
        System.out.println("Starting web driver...");
    }

    @AfterMethod
    public void tearDown() {
        DriverManager.quitBrowser();

    }

}