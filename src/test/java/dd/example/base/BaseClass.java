package dd.example.base;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import dd.example.utilities.ExcelReader;
import dd.example.utilities.ExtentManager;
import dd.example.utilities.TestUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

public class BaseClass {

    public static WebDriver driver;
    public static FileInputStream fileInputStream;
    public static Properties config=new Properties();
    public static Properties locators=new Properties();
    public static Logger log=Logger.getLogger("devpinoyLogger");
    public static ExcelReader excel=new ExcelReader(
            System.getProperty("user.dir")+"\\src\\test\\resources\\excel\\testdata.xlsx"
    );
    public static WebDriverWait wait;
    public static ExtentReports extentReports= ExtentManager.getInstance();
    public static ExtentTest extentTest;
    public static String browser;

    @BeforeSuite
    public void setup()
    {
        try {
            fileInputStream=new FileInputStream(System.getProperty("user.dir")+"\\src\\test\\resources\\properties\\config.properties");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        try {
            config.load(fileInputStream);
            log.debug("Config file loaded");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            fileInputStream=new FileInputStream(System.getProperty("user.dir")+"\\src\\test\\resources\\properties\\locators.properties");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        try {
            locators.load(fileInputStream);
            log.debug("Locators file loaded");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Jenkin Parameter
        if(System.getenv("browser")!=null && !System.getenv().isEmpty())
        {
            browser=System.getenv("browser");
        }
        // Or set from config file
        else{
            browser=config.getProperty("browser");
        }

        config.setProperty("browser",browser);

        // Driver
        if(config.getProperty("browser").equals("chrome"))
        {
            driver=new ChromeDriver();
            log.debug("Chrome driver is created");
        }
        else if(config.getProperty("browser").equals("firefox"))
        {
            driver=new FirefoxDriver();
            log.debug("Firefox driver is created");
        }
        else if(config.getProperty("browser").equals("edge"))
        {
            driver=new EdgeDriver();
            log.debug("Edge driver is created");
        }
        else if(config.getProperty("browser").equals("ie"))
        {
            driver=new InternetExplorerDriver();
            log.debug("IE driver is created");
        }
        else{
            System.out.println("Incorrect browser name!!!!!");
        }


        driver.get(config.getProperty("siteUrl"));
        log.debug("Navigated to: "+config.getProperty("siteUrl"));
        driver.manage().window().maximize();

        // Implicit wait
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(
                Integer.parseInt(config.getProperty("implicitWait"))));

        // Explicit wait
        wait=new WebDriverWait(driver,Duration.ofSeconds(5));

    }


    public boolean isElementPresent(By by)
    {
        try {
            driver.findElement(by);
            return true;
        }
        catch (NoSuchElementException e)
        {
            return false;
        }
    }


    @AfterMethod()
    public void afterMethod(ITestResult result)
    {
        extentTest.getModel().setDescription(result.getMethod().getDescription());
    }

    @AfterSuite
    public void tearDown()
    {
        if(driver!=null)
        {
            driver.quit();
        }
        log.debug("Test execution completed..");
        extentReports.flush();
        log.debug("Report flushed..");
    }


    public void click(String locator)
    {
        if(locator.endsWith("_CSS"))
        {
            driver.findElement(By.cssSelector(locators.getProperty(locator))).click();
        }
        else if(locator.endsWith("_XPath"))
        {
            driver.findElement(By.xpath(locators.getProperty(locator))).click();
        }
        else if(locator.endsWith("_ID"))
        {
            driver.findElement(By.id(locators.getProperty(locator))).click();
        }

        extentTest.info("Clicked on element: "+locator);
    }

    public void type(String locator,String value)
    {
        if(locator.endsWith("_CSS"))
        {
            driver.findElement(By.cssSelector(locators.getProperty(locator))).sendKeys(value);
        }
        else if(locator.endsWith("_XPath"))
        {
            driver.findElement(By.xpath(locators.getProperty(locator))).sendKeys(value);
        }
        else if(locator.endsWith("_ID"))
        {
            driver.findElement(By.id(locators.getProperty(locator))).sendKeys(value);
        }

        extentTest.info("Typing into - "+locator+", Value entered: "+value);
    }

    public void verifyEquals(String expected,String actual) throws IOException {
        try {
            Assert.assertEquals(actual,expected);
        }catch (Throwable t)
        {
            Markup m= MarkupHelper.createLabel("Verification failed with Exception: "+t.getMessage(), ExtentColor.RED);
            extentTest.log(Status.FAIL,m);
            TestUtils.captureScreenshot("Failure Screen");
        }
    }


    public static void select(String locator,String value){
        WebElement dropdown=null;

        if(locator.endsWith("_CSS"))
        {
            dropdown=driver.findElement(By.cssSelector(locators.getProperty(locator)));
        }
        else if(locator.endsWith("_XPath"))
        {
            dropdown=driver.findElement(By.xpath(locators.getProperty(locator)));
        }
        else if(locator.endsWith("_ID"))
        {
            dropdown=driver.findElement(By.id(locators.getProperty(locator)));
        }

        Assert.assertNotNull(dropdown);
        Select select=new Select(dropdown);

        select.selectByVisibleText(value);

        extentTest.info("Selecting from dropdown - "+locator+", Value selected: "+value);

    }

}
