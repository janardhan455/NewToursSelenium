package utilities;

import java.util.Random;

import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentTest;

//import tests.func;
import utilities.ObjectMap;
import utilities.ExcelUtilities;

public class AppGenericFunctions {
	ObjectMap objMap = new ObjectMap(".\\UI Map\\NewTours.properties");	
	WebDriverWait wait;
	ExcelUtilities objExcel;
	public GenericFunctions func = new GenericFunctions();
	public TakesScreenshot ts;
	public ITestResult result;	
	int i;

// *********************************************************************************************************************************//
	public boolean Login_NewTours(WebDriver driver, ExtentTest report) throws Exception {		
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		driver.manage().window().maximize();
		driver.findElement(objMap.getLocator("userName")).clear();
		try {
			driver.findElement(objMap.getLocator("userName")).sendKeys(objMap.getValue("userNameValue"));
			report.pass("Successfully entered UserName:" + objMap.getValue("userNameValue"));

			driver.findElement(objMap.getLocator("passWord")).sendKeys(objMap.getValue("passWordValue"));
			report.pass("Successfully entered Password:" + objMap.getValue("passWordValue"));
			driver.findElement(objMap.getLocator("btnsignin")).click();
//			Thread.sleep(10000);
			wait = new WebDriverWait(driver,30);
			wait.until(ExpectedConditions.visibilityOf(driver.findElement(objMap.getLocator("lnkSIGNOFF"))));
			report.pass("Sign Off link is displayed in the UI, hence Sign-In is Successful");

			if (driver.findElement(objMap.getLocator("lnkSIGNOFF")).isDisplayed()) {
				report.pass("Sign-In is successful");
				return true;
			} else {
				report.fail("Sign-In is not successful");
				return false;
			}

		} catch (Exception e) {
			report.fail(e);
			return false;
		}
	}
	// *********************************************************************************************************************************//

	public boolean EnrollRegistration(WebDriver driver, String[][] testData, ExtentTest report) throws Exception {
		try {
			int rowCount=testData.length;
			for (i = 1; i <rowCount; i++) {
				driver.findElement(objMap.getLocator("lnkregister")).click();
				wait = new WebDriverWait(driver,30);
				wait.until(ExpectedConditions.visibilityOf(driver.findElement(objMap.getLocator("txtfirstName"))));

				String FN = testData[i][0];

				if (FN.equalsIgnoreCase("VALIDCHAR")) {
					FN = this.GenerateRandomString();
				}

				driver.findElement(objMap.getLocator("txtfirstName")).sendKeys(FN);
				report.pass("Successfully entered first name:" + FN);

				String LN = testData[i][1];

				if (LN.equalsIgnoreCase("VALIDCHAR")) {
					LN = this.GenerateRandomString();
				}

				driver.findElement(objMap.getLocator("txtlastName")).sendKeys(LN);
				report.pass("Successfully entered last name:" + LN);

				String PH = testData[i][2];
				driver.findElement(objMap.getLocator("txtphone")).sendKeys(PH);
				report.pass("Successfully entered phone:" + PH);

				String EMAIL = testData[i][3];

				if (EMAIL.equalsIgnoreCase("VALIDCHAR")) {
					EMAIL = this.GenerateRandomString();
				}

				driver.findElement(objMap.getLocator("txtemail")).sendKeys(EMAIL);
				report.pass("Successfully entered phone:" + EMAIL);

				String ADD = testData[i][7];

				if (ADD.equalsIgnoreCase("VALIDCHAR")) {
					ADD = this.GenerateRandomString();
				}

				driver.findElement(objMap.getLocator("txtaddress1")).sendKeys(ADD);
				report.pass("Successfully entered address1:" + ADD);

				String CITY = testData[i][9];

				if (CITY.equalsIgnoreCase("VALIDCHAR")) {
					CITY = this.GenerateRandomString();
				}

				driver.findElement(objMap.getLocator("txtcity")).sendKeys(CITY);
				report.pass("Successfully entered city:" + CITY);

				String STATE = testData[i][10];

				if (STATE.equalsIgnoreCase("VALIDCHAR")) {
					STATE = this.GenerateRandomString();
				}

				driver.findElement(objMap.getLocator("txtstate")).sendKeys(STATE);
				report.pass("Successfully entered state:" + STATE);

				String postalcode = testData[i][11];
				driver.findElement(objMap.getLocator("txtpostalcode")).sendKeys(postalcode);
				report.pass("Successfully entered postalcode:" + postalcode);

				String username = testData[i][4];

				if (username.equalsIgnoreCase("VALIDCHAR")) {
					username = this.GenerateRandomString();
				}

				driver.findElement(objMap.getLocator("txtusername")).sendKeys(username);
				report.pass("Successfully entered postalcode:" + username);

				String password = testData[i][5];
				driver.findElement(objMap.getLocator("txtpassword")).sendKeys(password);
				report.pass("Successfully entered postalcode:" + password);

				String confirmpassword = testData[i][6];
				driver.findElement(objMap.getLocator("txtconfirmpassword")).sendKeys(confirmpassword);
				report.pass("Successfully entered postalcode:" + confirmpassword);

				driver.findElement(objMap.getLocator("btnsubmit")).click();
				
				wait = new WebDriverWait(driver,50);
				wait.until(ExpectedConditions.visibilityOf(driver.findElement(objMap.getLocator("eleRegisterConfmsg"))));
				
				String confmsg = driver.findElement(objMap.getLocator("eleRegisterConfmsg")).getText();
				ts = (TakesScreenshot) driver;
				
				if (confmsg.indexOf("Your user name is") > 0) {
					report.pass("Registration is successful: " + confmsg);
				} else {
					report.fail("Registration is unsuccessful");
				}
			}
			return true;
		} catch (Exception e) {
			report.fail(e);
			return false;
		}
	}
//*********************************************************************************************************************************//

	public String GenerateRandomString() {

		int leftLimit = 97; // letter 'a'
		int rightLimit = 122; // letter 'z'
		int targetStringLength = 8;
		Random random = new Random();
		StringBuilder buffer = new StringBuilder(targetStringLength);
		for (int i = 0; i < targetStringLength; i++) {
			int randomLimitedInt = leftLimit + (int) (random.nextFloat() * (rightLimit - leftLimit + 1));
			buffer.append((char) randomLimitedInt);
		}
		String generatedString = buffer.toString();

		return generatedString;
	}

//*********************************************************************************************************************************//
		public boolean LogOff_NewTours(WebDriver driver, ExtentTest report) throws Exception {		
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			driver.manage().window().maximize();
			driver.findElement(objMap.getLocator("userName")).clear();
			try {
				driver.findElement(objMap.getLocator("userName")).sendKeys(objMap.getValue("userNameValue"));
				report.pass("Successfully entered UserName:" + objMap.getValue("userNameValue"));

				driver.findElement(objMap.getLocator("passWord")).sendKeys(objMap.getValue("passWordValue"));
				report.pass("Successfully entered Password:" + objMap.getValue("passWordValue"));
				driver.findElement(objMap.getLocator("btnsignin")).click();
				
				wait = new WebDriverWait(driver,30);
				wait.until(ExpectedConditions.visibilityOf(driver.findElement(objMap.getLocator("lnkSIGNOFF"))));				
				driver.findElement(objMap.getLocator("lnkSIGNOFF")).click();
				report.pass("Successfully clicked on the link Sign Off");
				wait.until(ExpectedConditions.visibilityOf(driver.findElement(objMap.getLocator("lnkSIGNON"))));
				report.pass("Sign-In link is displayed in the UI, hence Sign-Off is Successful");
				
				if (driver.findElement(objMap.getLocator("lnkSIGNON")).isDisplayed()) {
					report.pass("Sign Off is successful");
					return true;
				} else {
					report.fail("Sign Off is not successful");
					return false;
				}

			} catch (Exception e) {
				report.fail(e);
				return false;
			}
		}
//*********************************************************************************************************************************//
	public void DashboardMenu(WebDriver driver, String[][] testData, ExtentTest report) throws Exception {		
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		int rowCount=testData.length;
		int colCount = testData[0].length;
		int j=0;
		String str = null;
		
		for (i=1;i<=rowCount-1;i++) {
			for (j=0;j<colCount-1;j++) {
				str=testData[i][j];
				if (!str.equals("")) {
					if (driver.findElement(objMap.getLocator(str)).isDisplayed()) {
						report.pass(str+" is displayed successfully");
					} else {
						report.fail("Failed to display the link "+str);
					}
				}
			}
		}
	}
	// *********************************************************************************************************************************//

}
