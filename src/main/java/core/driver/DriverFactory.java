package core.driver;

import org.openqa.selenium.WebDriver;

public class DriverFactory {


    private DriverFactory(){
    }

    private static DriverFactory driverManger = new DriverFactory();

    public static DriverFactory getInstance(){
        return driverManger;
    }

    ThreadLocal<WebDriver> driver = new ThreadLocal<WebDriver>();

    public WebDriver getDriver(){
        return driver.get();
    }

    public void setDriver(WebDriver driverElement){
        driver.set(driverElement);
    }

    public void closeBrowser(){
        driver.get().close();
        driver.remove();
    }

    public void quitBrowser(){
        driver.get().quit();
        driver.remove();
    }
}
