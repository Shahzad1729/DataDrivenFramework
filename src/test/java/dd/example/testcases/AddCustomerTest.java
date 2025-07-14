package dd.example.testcases;

import dd.example.base.BaseClass;
import dd.example.utilities.TestUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Hashtable;

public class AddCustomerTest extends BaseClass {

    @Test(dataProviderClass = TestUtils.class,dataProvider = "getData")
    public void addCustomerTest(Hashtable<String,String> data) throws IOException, InterruptedException {

        type("fNameField_CSS",data.get("firstname"));
        type("lNameField_CSS",data.get("lastname"));
        type("postCodeField_CSS",data.get("postcode"));

        TestUtils.captureScreenshot("Filled Values");

        click("addBtn_CSS");

        Alert alert=wait.until(ExpectedConditions.alertIsPresent());

        extentTest.info("Verifying alert text");
        Assert.assertTrue(alert.getText().contains(data.get("alerttext")),"Incorrect alert text!!");

        extentTest.info("Click on Ok button on Success Popup");
        alert.accept();

        Thread.sleep(3000);
    }

}
