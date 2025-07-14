package dd.example.testcases;

import dd.example.base.BaseClass;
import org.testng.Assert;
import org.openqa.selenium.By;
import org.testng.annotations.Test;

public class BankManagerLoginTest extends BaseClass {

    @Test(groups = {"Sanity"},description = "001: Login as Bank manager and click on Add Customer button")
    public void bankManagerLoginTest() throws InterruptedException{
        log.debug("Inside Test Method");

        click("bankManagerLoginBtn_CSS");

        Assert.assertTrue(isElementPresent(By.cssSelector(locators.getProperty("addCustBtn_CSS"))),
                "Login test failed");

        click("addCustBtn_CSS");

        Thread.sleep(2000);
     }
}
