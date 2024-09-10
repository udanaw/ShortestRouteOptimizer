package core.driver;

import core.utilities.PropertiesUtils;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.reusable.variables.GlobalVariables;

import java.util.HashMap;
import java.util.Map;

public class BrowserFactory {

    private static String browserType;
    private static String browserVersion;
    private static boolean isHeadless;
    private static boolean isPrivate;

    public WebDriver createBrowserInstance() {
        WebDriver driver = null;

        if (browserType == null || browserVersion == null) {
            core.utilities.PropertiesUtils.loadPropertyFile(core.utilities.PropertiesUtils.PropertyFile.WEB);
            browserType = core.utilities.PropertiesUtils.getProperty("browser.type");
            browserVersion = core.utilities.PropertiesUtils.getProperty("browser.version");
            isHeadless = Boolean.parseBoolean(core.utilities.PropertiesUtils.getProperty("browser.mode.headless").toLowerCase());
            isPrivate = Boolean.parseBoolean(PropertiesUtils.getProperty("browser.mode.private").toLowerCase());
        }

        if (browserType.equalsIgnoreCase("chrome")) {
            if (browserVersion.startsWith("auto"))
                WebDriverManager.chromedriver().setup();
            else
                WebDriverManager.chromedriver().browserVersion(browserVersion).setup();

            // Chrome options
            ChromeOptions cOptions = new ChromeOptions();

            if (isHeadless) {
                cOptions.setHeadless(true);
            }
            if (isPrivate) {
                cOptions.addArguments("--incognito");
            }


            Map<String, Object> prefs = new HashMap<String, Object>();
            prefs.put("download.default_directory", GlobalVariables.CSVFileDownloadedLocation);
            cOptions.setExperimentalOption("prefs", prefs);
            driver = new ChromeDriver(cOptions);


        } else if (browserType.equalsIgnoreCase("firefox")) {
            if (browserVersion.startsWith("auto"))
                WebDriverManager.firefoxdriver().setup();
            else
                WebDriverManager.firefoxdriver().browserVersion(browserVersion).setup();

            // Firefox options
            FirefoxOptions fOptions = new FirefoxOptions();
            if (isHeadless) {
                fOptions.setHeadless(true);
            }
            if (isPrivate) {
                fOptions.addArguments("-private");
            }

            driver = new FirefoxDriver(fOptions);
        } else if (browserType.toLowerCase().startsWith("ie") || browserType.equalsIgnoreCase("internet explorer")) {
            if (browserVersion.startsWith("auto"))
                WebDriverManager.iedriver().setup();
            else
                WebDriverManager.iedriver().browserVersion(browserVersion).setup();

            // IE options
            InternetExplorerOptions ieOptions = new InternetExplorerOptions();
            if (isPrivate) {
                ieOptions.addCommandSwitches("-private");
            }

            driver = new InternetExplorerDriver(ieOptions);
        } else if (browserType.equalsIgnoreCase("edge")) {
            if (browserVersion.startsWith("auto"))
                WebDriverManager.edgedriver().setup();
            else
                WebDriverManager.edgedriver().browserVersion(browserVersion).setup();

            driver = new EdgeDriver();
        }

        return driver;
    }

}
