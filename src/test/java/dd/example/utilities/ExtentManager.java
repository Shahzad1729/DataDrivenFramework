package dd.example.utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

public class ExtentManager{

    public static ExtentHtmlReporter extentHtmlReporter;
    public static ExtentReports extentRep;

    public static ExtentReports getInstance()
    {
        if(extentRep ==null)
        {
            extentHtmlReporter =new ExtentHtmlReporter("./target/surefire-reports/html/extent.html");

            extentHtmlReporter.config().setEncoding("utf-8");
            extentHtmlReporter.config().setDocumentTitle("Data Driven Framework");
            extentHtmlReporter.config().setReportName("Automation Report");

            extentRep =new ExtentReports();
            extentRep.attachReporter(extentHtmlReporter);

            extentRep.setSystemInfo("Username","Shez");
            extentRep.setSystemInfo("Environment","Sit");
            extentRep.setSystemInfo("Build Version","283802");
            extentRep.setSystemInfo("System","Windows 11");

        }

        return extentRep;
    }
}
