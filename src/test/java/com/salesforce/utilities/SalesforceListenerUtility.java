package com.salesforce.utilities;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import com.salesforce.base.BaseSalesforceTest;


public class SalesforceListenerUtility extends BaseSalesforceTest implements ITestListener {
	protected Logger Listenerlog=LogManager.getLogger();
	private static ExtentReportsUtility extentReport=ExtentReportsUtility.getInstance();
	@Override
	public void onTestStart(ITestResult result) {
		Listenerlog.info(result.getMethod().getMethodName()+".......test execution started........");
		extentReport.startSingleTestReport(result.getMethod().getMethodName());
		
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		Listenerlog.info(result.getMethod().getMethodName()+".......test execution completed with success........");
		extentReport.logTestpassed("test execution completed with success");
	}

	@Override
	public void onTestFailure(ITestResult result) {
		Listenerlog.error(result.getMethod().getMethodName()+".......test execution completed with failure........");
		extentReport.logTestFailed(result.getMethod().getMethodName()+"test is failed");
		String filename=new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss").format(new Date());
		System.out.println("filename="+filename);
		String path=Constants.SCREENSHOTS_DIRECTORY_PATH+filename+".png"; // 2024_02_13_02_11_23.png
		//WebDriver driver=getDriverInstance();
		takescreenshot(path);
		extentReport.logTestWithscreenshot(System.getProperty("user.dir")+"/reports/screenshots/"+filename+".png");
		extentReport.logTestFailedWithException(result.getThrowable());
	}

	@Override
	public void onStart(ITestContext context) {
		Listenerlog.info(context.getName()+" has started....................");
		extentReport.startExtentReport();
		
	}

	@Override
	public void onFinish(ITestContext context) {
		Listenerlog.info(context.getName()+" has ended....................");
		extentReport.endReport();
	}
	


}
