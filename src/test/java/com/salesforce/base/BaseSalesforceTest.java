package com.salesforce.base;

import java.io.File;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import com.google.common.io.Files;
import com.salesforce.utilities.Constants;
import com.salesforce.utilities.ExtentReportsUtility;
import com.salesforce.utilities.PropertiesUtility;

import io.github.bonigarcia.wdm.WebDriverManager;
@Listeners(com.salesforce.utilities.SalesforceListenerUtility.class)
public class BaseSalesforceTest {
	protected static WebDriver driver = null;
	protected Logger baseTestlog=LogManager.getLogger();
	protected static ExtentReportsUtility extentReport=ExtentReportsUtility.getInstance();
	
	@BeforeMethod
	@Parameters("browserName")
	public void setUpBeforeMethod(@Optional("chrome") String name) {
		baseTestlog.info(".........BeforeMethod setUpBeforeMethod executed---------------");
		launchBrowser("chrome");
		String url=PropertiesUtility.readDataFromPropertyFile(Constants.APPLICATION_PROPERTIES, "url");
		goToUrl(url);
	}
	
	@AfterMethod
	public void tearDownAfterTestMethod() {
		closeBrowser();
		baseTestlog.info("******tearDownAfterTestMethod executed***********");
	}
   //Launching the browser
	public void launchBrowser(String browserName) {

		switch (browserName.toLowerCase()) {
		case "chrome":
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
			baseTestlog.info("browser instance chrome created.");
			driver.manage().window().maximize();
			baseTestlog.info("window is maximized");
			break;

		case "firefox":
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
			driver.manage().window().maximize();
			break;

		case "edge":
			WebDriverManager.edgedriver().setup();
			driver = new EdgeDriver();
			driver.manage().window().maximize();
			break;

		case "opera":
			WebDriverManager.operadriver().setup();
			driver = new OperaDriver();
			driver.manage().window().maximize();
			break;

		case "safari":
			// Safari does not require a separate driver setup, as it is included with macOS
			driver = new SafariDriver();
			break;

		default:
			baseTestlog.info("Unsupported browser: " + browserName);
		}

	}
   //Navigating to the URL
	public void goToUrl(String url) {
		driver.get(url);
		baseTestlog.info(url + " is entered");

	}
	 
	   //To close the Browser
		public void closeBrowser() {
			driver.close();
			baseTestlog.info("Browser instance closed");
			driver=null;
		}
		//To close all the open windows
		public void quitBrowser() {
			driver.quit();
			baseTestlog.info("all browser closed");
			driver=null;
			
		}
		//Screen shot for the webpage at a time
				public void takescreenshot(String filepath) {
					 TakesScreenshot screenCapture=(TakesScreenshot)driver;
					 File src=screenCapture.getScreenshotAs(OutputType.FILE);
					 File destination=new File(filepath);
					 try {
						Files.copy(src, destination);
						baseTestlog.info("captured the screen");
						
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						baseTestlog.info("Went wrong when capturing the screen");
						
					}
				}
				//screenshot for single webelement
				public void takescreenshot(WebElement element,String filepath) {
					 File src=element.getScreenshotAs(OutputType.FILE);
					 File destination=new File(filepath);
					 try {
						Files.copy(src, destination);
						baseTestlog.info("Captured Element Screenshot");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						baseTestlog.info("Went wrong when capturing the screen");
						
					}
				}
}
