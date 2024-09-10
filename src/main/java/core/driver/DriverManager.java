package core.driver;

import org.openqa.selenium.WebDriver;

public class DriverManager {


    public static WebDriver getDriver() {
        return DriverFactory.getInstance().getDriver();
    }

    public static final void closeBrowser() {
        System.out.println("Closing active browser tab.....");
        DriverFactory.getInstance().closeBrowser();
    } // Closes current browser tab


    public static final void quitBrowser() {
        System.out.println("Quitting web browser .....");
        DriverFactory.getInstance().quitBrowser();
    } // Closes the driver instances


}

