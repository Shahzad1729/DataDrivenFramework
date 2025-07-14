package dd.example.utilities;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import dd.example.base.BaseClass;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.annotations.DataProvider;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Hashtable;

public class TestUtils extends BaseClass {
    public static String screenshotName;

    public static void captureScreenshot(String details) throws IOException {
        Date d=new Date();
        screenshotName=d.toString().replace(":","_").replace(" ","_")+".jpg";
        File scrFile=((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(scrFile,new File(System.getProperty("user.dir")+"\\target\\surefire-reports\\html\\"+screenshotName));

        extentTest.log(Status.INFO,details, MediaEntityBuilder.createScreenCaptureFromPath(TestUtils.screenshotName).build());

    }


    @DataProvider(name="getData")
    public Object[][] getData(Method m)
    {
        String sheetName=m.getName();

        extentTest.info("Providing data from the sheet: "+sheetName);

        int rows=excel.getRowCount(sheetName);
        int cols=excel.getColumnCount(sheetName);

        Object[][] data=new Object[rows-1][1];

        Hashtable<String,String> table;

        for(int rowNum=2;rowNum<=rows;rowNum++)
        {
            table=new Hashtable<>();

            for(int colNum=0;colNum<cols;colNum++)
            {
                // data[0][0]
                table.put(excel.getCellData(sheetName,colNum,1),excel.getCellData(sheetName,colNum,rowNum));
                data[rowNum-2][0]=table;
            }
        }
        return data;
    }


    public static boolean isTestRunnable(String testName,ExcelReader excel)
    {
        String sheetName="Test_Suite";

        int rows=excel.getRowCount(sheetName);

        for(int rowNum=2;rowNum<=rows;rowNum++)
        {
            String testcase=excel.getCellData(sheetName,"TCID",rowNum);
            if(testcase.equalsIgnoreCase(testName))
            {
                String runMode=excel.getCellData(sheetName,"Runmode",rowNum);
                return runMode.equalsIgnoreCase("Y");
            }
        }

        return false;
    }

}
