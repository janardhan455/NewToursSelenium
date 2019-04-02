package tests;

import utilities.AppGenericFunctions;

import utilities.ExcelUtilities;
import utilities.GenericFunctions;
import utilities.ObjectMap;

import java.util.concurrent.TimeUnit;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import java.io.IOException;

public class VerifySignOff {

	public ObjectMap objMap;
	public GenericFunctions func;
	public AppGenericFunctions appFunc;
	ExcelUtilities objExcel;
	public WebDriver driver;
	WebDriverWait wait;
	public TakesScreenshot ts;
	String[][] testData;
	public int i, j, rowCount, colCount;
	public ITestResult result;
	ExtentReports extent;
	ExtentTest report;

	@BeforeClass
	public void Setup() throws IOException {
		objMap = new ObjectMap(".\\UI Map\\NewTours.properties");
		objExcel = new ExcelUtilities();
		func = new GenericFunctions();
		appFunc = new AppGenericFunctions();
		testData = objExcel.readExcel(".\\TestData", "TestDataFile.xlsx", "SignIn");
		System.setProperty("webdriver.chrome.driver", objMap.getValue("chromeDriverPath"));
		ChromeOptions options = new ChromeOptions();
		driver = new ChromeDriver(options);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		wait = new WebDriverWait(driver, 15);
		driver.navigate().to(objMap.getValue("baseUrl"));
		ts = (TakesScreenshot) driver;

		extent = func.extentReportInvoke();
		report = extent.createTest("New Tours - Verify Sign Off", "Verify SignOff");

		// System.out.println("Thread Name: "+Thread.currentThread().getName()+"Class
		// Name: "+driver.getClass().getName());

		rowCount = testData.length;
		colCount = testData[0].length;
	}

	@Test
	public void CreateAccount() throws Exception {
		appFunc.LogOff_NewTours(driver,report);
	}

	@AfterMethod(alwaysRun = true)
	public void Capture_Screenshot(ITestResult result) throws Exception {
		func.Capture_Screenshot(result, ts, report);		
	}

	@AfterClass(alwaysRun = true)
	public void teardown() throws Exception {
		extent.flush();
		driver.close();
		driver.quit();
		System.out.println("********************END of 'Sign Off Verification'*************************");
	}

}
