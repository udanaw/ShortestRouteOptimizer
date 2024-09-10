package core.utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import core.utilities.actions.ActionBase;

public class ReportUtils {

    public static ExtentReports setUpExtentReport(){
        // Report Path
        core.utilities.PropertiesUtils.loadPropertyFile(core.utilities.PropertiesUtils.PropertyFile.CONFIG);
        String reportLocation = System.getProperty("user.dir") +
                core.utilities.PropertiesUtils.getProperty("reports.location") + "/ExecutionReport_"+ ActionBase.getTimeStamp("MM-dd-yy HH-mm-ss") +".html";

        ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportLocation);

        ExtentReports extentReport = new ExtentReports();
        extentReport.attachReporter(sparkReporter);

        sparkReporter.config().setDocumentTitle(core.utilities.PropertiesUtils.getProperty("reports.title"));
        sparkReporter.config().setReportName(core.utilities.PropertiesUtils.getProperty("reports.name"));

        // Set report theme
        Theme theme;
        if(PropertiesUtils.getProperty("reports.theme.dark").equalsIgnoreCase("true"))
            theme = Theme.DARK;
        else theme = Theme.STANDARD;
        sparkReporter.config().setTheme(theme);

        return extentReport;
    }

}