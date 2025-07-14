package dd.example.testcases;

import dd.example.base.BaseClass;
import dd.example.utilities.TestUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Hashtable;

public class OpenAccountTest extends BaseClass {

    @Test(dataProviderClass = TestUtils.class,dataProvider="getData")
    public void openAccountTest(Hashtable<String,String> data) throws InterruptedException, IOException {

        click("openAccBtn_CSS");

        select("customer_CSS",data.get("customer"));
        select("currency_CSS",data.get("currency"));

        TestUtils.captureScreenshot("Filled Values");

        click("processBtn_CSS");

        Thread.sleep(2000);
        Alert alert=wait.until(ExpectedConditions.alertIsPresent());

        extentTest.info("Verifying alert text");
        Assert.assertTrue(alert.getText().contains(data.get("alerttext")),"Incorrect alert text!!");

        extentTest.info("Click on Ok button on Success Popup");
        alert.accept();

        Thread.sleep(2000);
    }
}
