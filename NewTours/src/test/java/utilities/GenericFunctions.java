package utilities;

import utilities.ObjectMap;

import utilities.ExtentReportManager;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
//import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.support.ui.Select;
import org.testng.ITestResult;

//import org.sikuli.script.FindFailed;
//import org.sikuli.script.Pattern;
//import org.sikuli.script.Screen;
//import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;

//import org.sikuli.script.Key;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;

public class GenericFunctions {
	WebDriver driver;
	TakesScreenshot ts;
	public ObjectMap objMap = new ObjectMap(".\\UI Map\\NewTours.properties");
	ExtentReports extentrep;
	ExtentTest Test;

	public void TakeScreenShot(String scrshotfilename, TakesScreenshot ts1) {
		File src = ts1.getScreenshotAs(OutputType.FILE);
		try {
			FileUtils.copyFile(src, new File("./test-output/Screenshots/" + scrshotfilename + ".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void Capture_Screenshot(ITestResult result, TakesScreenshot ts, ExtentTest report) throws Exception {

		// Call method to capture screenshot
		File source = ts.getScreenshotAs(OutputType.FILE);

		if (result.getStatus() == 1) {
			FileUtils.copyFile(source, new File("./Execution Reports/HTML Reports/Screenshots/"
					+ result.getInstanceName() + "_" + result.getName() + "_PASS.png"));
			// test.addScreenCaptureFromPath("../Screenshots/"+result.getInstanceName()+"_"+result.getName()+"_PASS.png");
			Thread.sleep(2000);
			report.addScreenCaptureFromPath(
					"./Screenshots/" + result.getInstanceName() + "_" + result.getName() + "_PASS.png");
		} else {
			FileUtils.copyFile(source, new File("./Execution Reports/HTML Reports/Screenshots/"
					+ result.getInstanceName() + "_" + result.getName() + "_FAIL.png"));
			report.fail("Test Failed - please refer log file & screnshot for the exact error details");
			Thread.sleep(2000);
			report.addScreenCaptureFromPath(
					"./Screenshots/" + result.getInstanceName() + "_" + result.getName() + "_FAIL.png");
			// test.addScreenCaptureFromPath("../Screenshots/"+result.getInstanceName()+"_"+result.getName()+"_FAIL.png");
		}

	
		System.out.println("Screenshot has been captured for the test" + result.getName());
	}


	public String getCurrentDateTime() {
		Date dt = new Date();
		// DateFormat dtf=new SimpleDateFormat("MM/dd/YYYY hh:mm:ss.S a z");
		DateFormat dtf = new SimpleDateFormat("MM_dd_YYYY_hh mm ss a z");
		return dtf.format(dt);
	}

	
	public ExtentReports extentReportInvoke() {

		extentrep = ExtentReportManager.GetExtent(objMap.getValue("extentReportFilePath"),
				objMap.getValue("extentReportDocumentTitle"), objMap.getValue("extentReportName"));
		// Test=extentrep.createTest(testName, description);
		return extentrep;

	}

	public Markup extentLabel(String text, ExtentColor color) {
		Markup m = MarkupHelper.createLabel(text, color);
		return m;
	}

	public Markup extentTable(String[][] data) {
		Markup m = MarkupHelper.createTable(data);
		return m;
	}
}
