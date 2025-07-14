package dd.example.listeners;

import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import dd.example.base.BaseClass;
import dd.example.utilities.TestUtils;
import org.testng.*;

import java.io.IOException;

public class CustomListener extends BaseClass implements ITestListener {

    @Override
    public void onTestFailedWithTimeout(ITestResult result) {
        ITestListener.super.onTestFailedWithTimeout(result);
    }

    @Override
    public void onFinish(ITestContext context) {
        ITestListener.super.onFinish(context);
    }

    @Override
    public void onStart(ITestContext context) {

    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        ITestListener.super.onTestFailedButWithinSuccessPercentage(result);
    }

    @Override
    public void onTestFailure(ITestResult result) {
        System.setProperty("org.uncommons.reportng.escape-output","false"); // allows HTML

        Reporter.log("Capturing Screenshot");
        try {
            TestUtils.captureScreenshot("Failure Screen");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Reporter.log("<a target='_blank' href='"+TestUtils.screenshotName+"'>Screenshot</a>");
        Reporter.log("<a href='"+TestUtils.screenshotName+"'><img src='"+TestUtils.screenshotName+"' height=250 width=250 /></a>");

        Markup m=MarkupHelper.createLabel("Test Case - Failed", ExtentColor.RED);
        extentTest.fail(m);
        extentTest.log(Status.FAIL,result.getThrowable().getMessage());
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        Markup m=MarkupHelper.createLabel("Test Case - Skipped",ExtentColor.YELLOW);
        extentTest.skip(m);
    }

    @Override
    public void onTestStart(ITestResult result) {
        extentTest= extentReports.createTest(result.getMethod().getMethodName());

        // checking if Test is runnable or not
        if(!TestUtils.isTestRunnable(result.getName(),excel))
        {
            throw new SkipException("Skipping the Test: "+result.getName());
        }
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        Markup m=MarkupHelper.createLabel("Test Case - Passed",ExtentColor.GREEN);
        extentTest.pass(m);
    }

    @Override
    public boolean isEnabled() {
        return ITestListener.super.isEnabled();
    }
}
