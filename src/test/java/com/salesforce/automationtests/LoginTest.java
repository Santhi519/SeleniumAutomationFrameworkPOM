package com.salesforce.automationtests;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.salesforce.pages.home.HomePage;
import com.salesforce.pages.login.LoginPage;
import com.salesforce.utilities.Constants;
import com.salesforce.utilities.ExtentReportsUtility;
import com.salesforce.utilities.PropertiesUtility;
import com.salesforce.base.BaseSalesforceTest;

public class LoginTest extends BaseSalesforceTest {
	protected Logger LoginTestlog=LogManager.getLogger();
	protected static ExtentReportsUtility extentReport=ExtentReportsUtility.getInstance();
	@Test
	public void LoginErrorMessage_TC1() {
		LoginTestlog.info("*************LoginErrorMessage_TC1 Started**************");
		LoginPage loginpage=new LoginPage(driver);
		loginpage.enterUsername("santhik@salesforce.com");
		loginpage.enterPassword("");
		extentReport.logTestInfo("Username and Password entered");
		driver=loginpage.clickLoginButton();
		String actual=loginpage.geterrorMessage();
		String expected="Please enter your password.";
		Assert.assertEquals(actual, expected);
	}
	@Test
	public void LoginToSalesForce_TC2() {
		LoginTestlog.info("*************LoginToSalesForce_TC2 Started**************");
		LoginPage loginpage=new LoginPage(driver);
		String username=PropertiesUtility.readDataFromPropertyFile(Constants.APPLICATION_PROPERTIES, "username");
		String password=PropertiesUtility.readDataFromPropertyFile(Constants.APPLICATION_PROPERTIES, "password");
		extentReport.logTestInfo("Username and Password extracted from properties file");
		loginpage.enterUsername(username);
		loginpage.enterPassword(password);
		driver=loginpage.clickLoginButton();
		HomePage homepage=new HomePage(driver);
		String actual=homepage.getTitleofthehome();
		String expected="Home Page ~ Salesforce - Developer Edition";
		Assert.assertEquals(actual, expected);
	}
	@Test
	public void CheckRemeberMe_TC3() throws InterruptedException {
		LoginTestlog.info("*************CheckRemeberMe_TC3 Started**************");
		LoginPage loginpage=new LoginPage(driver);
		String username=PropertiesUtility.readDataFromPropertyFile(Constants.APPLICATION_PROPERTIES, "username");
		String password=PropertiesUtility.readDataFromPropertyFile(Constants.APPLICATION_PROPERTIES, "password");
		extentReport.logTestInfo("Username and Password extracted from properties file");
		loginpage.enterUsername(username);
		loginpage.enterPassword(password);
		loginpage.clickRememberMe();
		extentReport.logTestInfo("Remember Me checbox is clicked");
		driver=loginpage.clickLoginButton();
		HomePage homepage=new HomePage(driver);
		String actual=homepage.getTitleofthehome();
		String expected="Home Page ~ Salesforce - Developer Edition";
		Assert.assertEquals(actual, expected);
		homepage.clickusermenu();
		driver=homepage.clicklogout();
		Thread.sleep(2000);
		String actual1=loginpage.getTitleofthelogin();
		String expected1="Login | Salesforce";
		Assert.assertEquals(actual1, expected1);
		String actual2=loginpage.getusername();
		String expected2="santhik@salesforce.com";
		Assert.assertEquals(actual2, expected2);
		
	}
	@Test
	public void ForgotPassword_TC4A() {
		LoginTestlog.info("*************ForgotPassword_TC4A Started**************");
		LoginPage loginpage=new LoginPage(driver);
		loginpage.clickforgotpassword();
		String expected="Forgot Your Password | Salesforce";
		String actual=loginpage.getTitleofthelogin();
		Assert.assertEquals(actual, expected);
		loginpage.enterforgotusername("santhik@salesforce.com");
		extentReport.logTestInfo("Username is entered");
		loginpage.clicksubmit();
		String actual1=loginpage.getpasswordresetMessage();
		extentReport.logTestInfo("The Forgot password message displayed is: \n"+actual1);
		
		
	}
	@Test
	public void ValidateLoginErrorMessage_TC4B() {
		LoginTestlog.info("*************ValidateLoginErrorMessage_TC4B Started**************");
		LoginPage loginpage=new LoginPage(driver);
		loginpage.enterUsername("123");
		loginpage.enterPassword("22131");
		extentReport.logTestInfo("Username and Password entered");
		driver=loginpage.clickLoginButton();
		String actual=loginpage.geterrorMessage();
		String expected="Please check your username and password. If you still can't log in, contact your Salesforce administrator.";
		Assert.assertEquals(actual, expected);
	}
}
