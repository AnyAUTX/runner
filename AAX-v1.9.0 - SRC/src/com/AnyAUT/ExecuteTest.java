package com.AnyAUT;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
//import org.testng.log4testng.Logger;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class ExecuteTest extends Utilities {


 	WebDriver myDriver;
	String fileInputPath, fileResultPath;
	int xlTestStepsRowCount, xlTestStepsColumnCount,xlTestCasesRowCount,xlTestCasesColumnCount;
	String[][] xlTestSteps,xlTestCases, xlmainFileData, xlPivot;	
	String testCase_Result, screenShotDirectoryName = null, severity;
	static Logger logger = Logger.getLogger(ExecuteTest.class);
	String filePivotPath;
	
	public static String verifyOutput;
	
	ExtentReports report;
	ExtentTest testCase_Report,testSteps_Report;
	Object TestStep_Output ;
	HashMap<String, String> hmapWindow;
	boolean set_Input_Flag=false;
	
	String testCase_Output,parentWinHandle,childWinHandle;
	HashMap<String, String> WinHandles;
	String TestRunName, tempPath;
	
	@BeforeTest
	public void readTestData()
	{   
	
		
		TestRunName = new UICode().returnValues("TestRunName");
		tempPath = Utilities.fileAbsolutePath()+"Results/TestRun_" + TestRunName + "/";
		
		fileInputPath = tempPath + "TestRun_" + TestRunName + ".xls";
		fileResultPath = tempPath + "RunResult.xls";
		filePivotPath=Utilities.fileAbsolutePath()+"Pivot_Template.xls";
		//filePivotPath = "C:\\XL\\Pivot.xls";
		
		PropertyConfigurator.configure("log4j.properties");
		
		report = new ExtentReports(tempPath + "Report.html",true);
		logger.info(fileInputPath);
		logger.info(fileResultPath);
		
		screenShotDirectoryName = tempPath + "Screenshots/";
		
		try{
		    xlTestCases=readXL(fileInputPath,"TestCases");
		    xlTestSteps = readXL(fileInputPath, "TestSteps");
		    xlPivot = readXL(filePivotPath, "TestCases");
		    System.out.println("XL Read");
		}
		catch(Exception e)
		{
			logger.error("Exception in readTestData method"+e.getClass().getSimpleName());
		}
	    xlTestStepsRowCount = xlTestSteps.length;
	    xlTestStepsColumnCount = xlTestSteps[0].length;
	    xlTestCasesRowCount=xlTestCases.length;
	    xlTestCasesColumnCount= xlTestCases[0].length;
		logger.info("Rows are " + xlTestStepsRowCount);
		logger.info("Cols are " + xlTestStepsColumnCount);	
	}
	// xlTestSteps[i][9] - is our Critical Y/N
	@Test
	public void executeTestSteps() throws IOException  {

		LowLevelKeywords lowLevelKeywords = new LowLevelKeywords(myDriver);
		DateFormat dateFormat;
		File screenShotDirectory;
		long vStartTime = 0;
		String screenShotFilePath = null, dateConversion;
		Date screenShotDate, stepDate; 
		String keyWord, elementType, elementBy, elementID, testData, criticalStep;
		screenShotDate = new Date();
		
		dateFormat = new SimpleDateFormat("MMddyyyy hh.mm.ss a");
			
		//converts screenShotDate in to the defined dateFormat
		dateConversion = dateFormat.format(screenShotDate);
		
		//fetch the absolute path of the workspace and append Screenshots folder
		screenShotDirectory = new File( screenShotDirectoryName + dateConversion);
		
		//create a directory with the file path. This ensures that there is a folder created for each execution
		screenShotDirectory.mkdir();
		screenShotFilePath = screenShotDirectory.getAbsolutePath();
		// int stopTest = 500; // Run upto 500 Test Steps. And then stop. 

			for ( int j=1; j < xlTestCasesRowCount; j++)
			{
				xlTestCases[j][4]="Pass";
				testCase_Report = report.startTest(xlTestCases[j][1]);
				// System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>         >>>>   stopTest Count : " + stopTest);
				
				/*
				if (stopTest <= 0) {
					break;
				}*/
					
				for (int i = 1; i < xlTestStepsRowCount; i++)
				{
					/*System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>         >>>>   stopTest Count : " + stopTest);
					if (stopTest <= 0) {
						break;
					}*/
					if((xlTestCases[j][1]).equals(xlTestSteps[i][1]))
					{
						testSteps_Report=report.startTest(xlTestSteps[i][2]+" : " + xlTestSteps[i][3]);
						testCase_Result = "Pass";
						//testCase_Output = "Nothing For this step";
						//testCase_Output = "N";
						severity=xlTestCases[j][6];
						
						keyWord =	xlTestSteps[i][4];
						elementType = xlTestSteps[i][5];
						elementBy =	xlTestSteps[i][6];
						elementID =	xlTestSteps[i][7];
						testData =	xlTestSteps[i][8];
						criticalStep =	xlTestSteps[i][9];
		
						logger.info("KW: " + keyWord +" ,Element Type: " + elementType +" ,Element By: " + elementBy +" ,Element ID: " + elementID +" ,Test Data: " + testData);
					
						try{
						/***********code begin for one TestStep output act as next step input********************************/
					
						if(set_Input_Flag)
						{
							logger.info("inside set input flag");
							logger.info(TestStep_Output);
							String prevTSoutput = setInput(TestStep_Output,testData);
							testData=prevTSoutput;
							set_Input_Flag=false;
							logger.info("new testData value "+testData);
							logger.info("inside set input flag clean flag "+set_Input_Flag);
						}
						/*******************************************/
						vStartTime = System.currentTimeMillis();
						
						/*
						if (stopTest > 0) {							
							executeKW(lowLevelKeywords, keyWord, elementBy, elementID, testData);
							stopTest--;
						} else {
							// break out of total execution
						}						/ 
						 */
						// stopTest--;
						executeKW(lowLevelKeywords, keyWord, elementBy, elementID, testData);
						
						//logger.info("Teststep status : "+testCase_Result);
						long vStopTime = System.currentTimeMillis();
					    long vElapsedTime = vStopTime - vStartTime;
					    vElapsedTime = vElapsedTime/1000;
					    String vExecutionTime = Long.toString(vElapsedTime); 
					    xlTestSteps[i][13] = vExecutionTime;
			    
					    testSteps_Report.log(LogStatus.INFO, "TSID : "+ xlTestSteps[i][2]);
					    testSteps_Report.log(LogStatus.INFO, "Keyword : "+ xlTestSteps[i][4]);
						testSteps_Report.log(LogStatus.INFO, "Element Id: "+ xlTestSteps[i][7]);
						testSteps_Report.log(LogStatus.INFO, "Test Data: "+ xlTestSteps[i][8]);
						
					    if(testCase_Result.equalsIgnoreCase("Pass")){
					    	lowLevelKeywords.highlightElement(elementBy, elementID, "green" );
					    	
					    	if(elementType.equals("One Time Use")) {
							    	testSteps_Report.log(LogStatus.PASS,xlTestSteps[i][10]);
					    		} else if(elementType.equals("Reusable Element")) {
					    				testSteps_Report.log(LogStatus.PASS,xlTestSteps[i][10]);
					    			} 
					    		else {
					    			testSteps_Report.log(LogStatus.PASS,xlTestSteps[i][10]);
					    		}
							
						    testSteps_Report.log(LogStatus.INFO, "Test Data Set: "+ xlTestSteps[i][14]); 
				    		testCase_Report.appendChild(testSteps_Report);
				    		
				    	//	lowLevelKeywords.unHighlightElement(elementBy, elementID );
			    		} 
					    else if(!testCase_Result.equals("Pass")){
								logger.info("test failed");
								xlTestSteps[i][11]  = "Verification Failed";
								stepDate = new Date();
								
								dateFormat = new SimpleDateFormat("yyyy-MM-dd hh.mm.ss a") ;
								// lowLevelKeywords.unHighlightElement(elementBy, elementID );
								lowLevelKeywords.highlightElement(elementBy, elementID, "red" );
								String testStepScreenShot = lowLevelKeywords.takePageScreenshot(screenShotFilePath+"/"+xlTestSteps[i][1]+"_"+xlTestSteps[i][3]+"_"+dateFormat.format(stepDate)+".png");
								// lowLevelKeywords.unHighlightElement(elementBy, elementID );
								xlTestSteps[i][12]="Look at Screenshot: "+screenShotFilePath; 
								
								if(elementType.equals("One Time Use")) {
									testSteps_Report.log(LogStatus.FAIL,xlTestSteps[i][10]);
									} else if(elementType.equals("Reusable Element")) {
									testSteps_Report.log(LogStatus.FAIL,xlTestSteps[i][10]);
									}  else {
									testSteps_Report.log(LogStatus.FAIL,xlTestSteps[i][10]);
								}
								
								testSteps_Report.log(LogStatus.INFO,testCase_Result);
								testSteps_Report.log(LogStatus.INFO, "Test Data Set: "+ xlTestSteps[i][14]);
								
								// KK Added below on 12th March 2020 to show actual vs expected test data
								testSteps_Report.log(LogStatus.INFO, "Expected :\""+ testData +"\". Actual is \"" + ExecuteTest.verifyOutput +"\""); 
								
								testSteps_Report.log(LogStatus.INFO, "Error Snapshot:" +testCase_Report.addScreenCapture(testStepScreenShot));
						    	testCase_Report.appendChild(testSteps_Report);

						    	// IF critical BREAK OUT after quit browser
						    	xlTestSteps[i][10] = testCase_Result;
								xlTestSteps[i][15] = severity;
								xlTestCases[j][4]="Fail";
								
						    	if (criticalStep.equalsIgnoreCase("Y")) {
						    		try {
						    			lowLevelKeywords.closeBrowser();
						    			testSteps_Report.log(LogStatus.ERROR, "Critical Step Failed. Closing browser and stopping TC execution.");
						    		}
						    		catch(Exception e) {
						    			System.out.println("Exception :"+e);
						    			testSteps_Report.log(LogStatus.ERROR, "Critical Step Failed. No browser found. Stopping TC execution.");
						    		}
						    		break;
						    	}
					    }
//						System.out.println("in try");
						}
						catch(Exception ex)
						{
							long vStopTime = System.currentTimeMillis();
						    long vElapsedTime = vStopTime - vStartTime;
						    vElapsedTime = vElapsedTime/1000;
						    String vExecutionTime = Long.toString(vElapsedTime);
							logger.error("Error : " + ex.getClass().getSimpleName());
							testCase_Result = "Fail";
							xlTestCases[j][4]="Fail";
							xlTestSteps[i][11] = "Error : " + ex;
							stepDate = new Date();
							String testStepScreenShot=lowLevelKeywords.takePageScreenshot(screenShotFilePath+"/"+xlTestSteps[i][1]+"_"+xlTestSteps[i][3]+"_"+dateFormat.format(stepDate)+".png");	
							xlTestSteps[i][12]="Look at Screenshot: " + screenShotFilePath; 
							
						    testSteps_Report.log(LogStatus.INFO, "TSID : "+ xlTestSteps[i][2]);
							testSteps_Report.log(LogStatus.INFO, "Keyword: "+ xlTestSteps[i][4]);
							testSteps_Report.log(LogStatus.INFO, "Element Id: "+ xlTestSteps[i][7]);
							testSteps_Report.log(LogStatus.INFO, "Test Data: "+ xlTestSteps[i][8]);
							
							// ??? Below 8 lines ??? 
							if(elementType.equals("One Time Use")) {
								testSteps_Report.log(LogStatus.FAIL,xlTestSteps[i][10]);
								} else if(elementType.equals("Reusable Element")) {
									testSteps_Report.log(LogStatus.FAIL,xlTestSteps[i][10]);
									} 
							else {
								testSteps_Report.log(LogStatus.FAIL,xlTestSteps[i][10]);						
							}
							// testSteps_Report.log(LogStatus.INFO,testCase_Result);
							testSteps_Report.log(LogStatus.INFO,xlTestSteps[i][11]);
							testSteps_Report.log(LogStatus.INFO, "Test Data Set: "+ xlTestSteps[i][14]); 
							testSteps_Report.log(LogStatus.INFO, "Error Snapshot:" +testCase_Report.addScreenCapture(testStepScreenShot));
					    	testCase_Report.appendChild(testSteps_Report);

							// IF critical BREAK OUT after quit browser
					    	xlTestSteps[i][10] = testCase_Result;
							xlTestSteps[i][15] = severity;
							
					    	if (criticalStep.equalsIgnoreCase("Y")) {
					    		try {
					    			lowLevelKeywords.closeBrowser();
					    			testSteps_Report.log(LogStatus.ERROR, "Critical Step Failed. Closing browser and stopping TC execution.");
					    		}
					    		catch(Exception e) {
					    			System.out.println("Exception :"+e);
					    			testSteps_Report.log(LogStatus.ERROR, "Critical Step Failed. No browser found. Stopping TC execution.");
					    		}
					    		break;
					    	}
						}//end of try-catch block
					//xlTestSteps[i][9] = testCase_Output;
					xlTestSteps[i][10] = testCase_Result;
					xlTestSteps[i][15]=severity;
					//for a failed test step, assigning 
					//if (xlTestSteps[i][12].equals("-")) {
					//	xlTestSteps[i][12] = "0";
					//}
				}
		
			}
			report.endTest(testCase_Report);
			report.flush();
		} 
		if (lowLevelKeywords.driver!=null) {
			lowLevelKeywords.driver.quit();
		}
		myDriver.quit();
	}
	
	@AfterTest
	public void getResults() {		

		try
		{
			
			writeXLSheets(fileResultPath, "TestCases", 0, xlTestCases);
			writeXLSheets(fileResultPath, "TestSteps", 1, xlTestSteps);
			System.out.println("Run Result.xls created");
			
			//copy pivot.xls and place in Results folder
			File source = new File(filePivotPath);
			File dest = new File(tempPath+"Pivot_Result.xls");
			try {
				FileUtils.copyFile(source, dest);
			    //FileUtils.copyDirectory(source, dest);
			} catch (IOException e) {
			    e.printStackTrace();
			}
			
			//writeXLSheets(fileResultPath, "Pivot", 2, xlPivot);
			writeXLSheets(tempPath+"Pivot_Result.xls", "TestCases", 0, xlTestCases);
			writeXLSheets(tempPath+"Pivot_Result.xls", "TestSteps", 1, xlTestSteps);
			System.out.println("Pivot_Result.xls created");
		}
		catch(Exception e)
		{
			logger.error("Exception in getResults method"+e.getClass().getSimpleName());
		}
		
		try 
		{
			Runtime.getRuntime().exec("taskkill /F /IM chromedriver.exe /T");
			//Runtime.getRuntime().exec("taskkill /F /IM chrome.exe /T");
		} 
		catch (IOException e) {
			logger.error("Exception in killing chrome process "+e);
		}
	}
	
	public void executeKW(LowLevelKeywords lowLevelKeywords, String strKeyWord, String ElementBy, String strElementID, String strTestData) throws Exception
	{
		String vReturn;
		
		switch(strKeyWord.trim()){
		
		case "openBrowser":
			//logger.info("Keyword is: "+ strKeyWord); // [KK] DO I NEED THIS 
			lowLevelKeywords.openBrowser(strTestData);
			break;
		
		case "closeBrowser":
			lowLevelKeywords.closeBrowser();
			break;
			
		case "quitBrowser": 
			lowLevelKeywords.quitBrowser();
			break;
			
		case "navigateBrowser":
			lowLevelKeywords.navigateBrowser(strTestData);
			break;
			
		case "maximizeBrowser":
			lowLevelKeywords.maximizeBrowser();
			break;
			
		case "typeText":
			lowLevelKeywords.typeText(ElementBy, strElementID, strTestData);
			break;
			
		case "sendKeys":
			lowLevelKeywords.typeText(ElementBy, strElementID, strTestData);
			break;
		
		case "selectList":
			lowLevelKeywords.selectList(ElementBy, strElementID, strTestData);
			break;
		
		case "clickButton":
		case "clickImage":
		case "clickLink":
		case "clickElement":
			lowLevelKeywords.clickElement(ElementBy, strElementID);
			break;
						
		case "verifyText":
			testCase_Result = lowLevelKeywords.verifyText(ElementBy, strElementID, strTestData);
			break;

		case "verifyMultilineText":
			testCase_Result = lowLevelKeywords.verifyMultilineText(ElementBy, strElementID, strTestData);
			break;

		case "verifyPartialText":
			testCase_Result = lowLevelKeywords.verifyPartialText(ElementBy, strElementID, strTestData);
			break;
			
		case "verifyValue":
			verifyOutput = "Starting";
			testCase_Result = lowLevelKeywords.verifyValue(ElementBy, strElementID, strTestData);
			break;
			
		case "enterKeyboard":
			lowLevelKeywords.enterKeyboard(ElementBy, strElementID);
			break;
	
		case "tabKeyboard": 
			lowLevelKeywords.tabKeyboard(ElementBy,strElementID);
			break;
		
		
		case "readText":
			testCase_Result = lowLevelKeywords.readText(ElementBy, strElementID);
			break;
		
		case "getAllLinks":
			TestStep_Output=lowLevelKeywords.getAllLinks(ElementBy, strElementID);
			logger.info(TestStep_Output);
			break;		
		
		case "getWebElements": 
			lowLevelKeywords.getWebElements(ElementBy, strElementID);
			break;	
			
		case "verify":
			lowLevelKeywords.scrollByElement(ElementBy, strElementID);
			break;
		
		case "openLinkInNewWindow":
			lowLevelKeywords.openLinkInNewWindow(ElementBy, strElementID);
			break;
		
		case "getWindowNames":
			WinHandles = new HashMap<String, String>();
			WinHandles = lowLevelKeywords.getWindowNames();
			parentWinHandle = WinHandles.get("parentWindow");
			if(parentWinHandle!=null) {
			System.out.println("parentWinHandle: "+parentWinHandle);
			}
			childWinHandle = WinHandles.get("childWindow");
			if(childWinHandle!=null) {
			System.out.println("childWinHandle: "+childWinHandle);
			}
			set_Input_Flag=true;
			logger.info("in switch case getwindownames:"+set_Input_Flag);
			break;
		
		case "switchToWindow":
			
			System.out.println("childWinHandle"+childWinHandle );
			if(childWinHandle!=null) {
			lowLevelKeywords.switchToWindow(childWinHandle);
			}
			break;
		
		case "switchToParentWindow":
			System.out.println(parentWinHandle);
			if(parentWinHandle!=null) {
			lowLevelKeywords.switchToParentWindow(parentWinHandle);
			}
			break;
			
		case "switchToPrintWindow":
			System.out.println("childWinHandle");
			if(childWinHandle!=null) {
			lowLevelKeywords.switchToPrintWindow(childWinHandle);
			}
			break;
			
		case "closepopupWindow":	
			lowLevelKeywords.closepopupWindow();
			break;
			
		case "closeLatestWindow":	
			lowLevelKeywords.closeLatestWindow();
			break;
		
		case "verifyTitle":	
			lowLevelKeywords.verifyTitle(strTestData);
			break;
		
		case "scrollByElement":	
			lowLevelKeywords.scrollByElement(ElementBy,strElementID);
			break;	
			
		case "selectFirstOptionInDropDownList":	
			lowLevelKeywords.selectFirstOptionInDropDownList(ElementBy,strElementID,strTestData);
			break;
		
		case "selectByValue":	
			lowLevelKeywords.selectByValue(ElementBy,strElementID,strTestData);
			break;
			
		case "selectDropDownList_selectByValue":	
			lowLevelKeywords.selectByValue(ElementBy,strElementID,strTestData);
			break;	
			
		case "selectByIndex":	
			lowLevelKeywords.selectByIndex(ElementBy,strElementID,strTestData);
			break;
		
		case "clickCheckbox":	
			lowLevelKeywords.clickCheckbox(ElementBy,strElementID,strTestData );
			break;
		
		case "isSelected":
			lowLevelKeywords.isSelected(ElementBy,strElementID);
			break;
			
		case "isNotSelected":
			lowLevelKeywords.isNotSelected(ElementBy,strElementID);
			break;

		case "scrollDown":	
			lowLevelKeywords.scrollDown();
			break;
		
		case "scroll_Horizontally":	
			lowLevelKeywords.scroll_Horizontally(ElementBy,strElementID,strTestData);
			break;
		
		case "scroll_Vertically":	
			lowLevelKeywords.scroll_Vertically(ElementBy,strElementID,strTestData);
			break;
		
		case "refresh":	
			lowLevelKeywords.refresh();
			break;	
		
		case "goBack":	
			lowLevelKeywords.goBack();
			break;
		
		case "goForward":	
			lowLevelKeywords.goForward();
			break;
		
		case "clearText":	
			lowLevelKeywords.clearText(ElementBy,strElementID);
			break;
		
		case "switchToFrame":
			lowLevelKeywords.switchToFrame(ElementBy,strElementID);
			break;
		
		case "defaultContent":
			lowLevelKeywords.defaultContent();
			break;
		
		case "getTitle": 
			String title = lowLevelKeywords.getTitle();
			System.out.println(title);
			break;
			
		case "verifyElementPresent": 
			lowLevelKeywords.scrollByElement(ElementBy, strElementID);
			break;

		case "verifyTextAnyWhere": 
			lowLevelKeywords.verifyTextAnyWhere(ElementBy, strElementID,strTestData);
			break;
			
		case "waitForSeconds": 
			lowLevelKeywords.waitForSeconds(strTestData);
			break;
			
		case "waitTillElementIsEnabled":
		case "waitTillElementIsClickable": 
			lowLevelKeywords.waitTillElementIsEnabled(ElementBy,strElementID,strTestData);
			break;
		
		case "waitTillElementIsVisible":
			lowLevelKeywords.waitTillElementIsVisible(ElementBy,strElementID,strTestData);
			break;
		
		case "waitTillElementTextPresent":
		case "waitforText":
			lowLevelKeywords.waitTillElementTextPresent(ElementBy,strElementID,strTestData);
			break;
		
		case "waitTillElementIsInvisible":
			lowLevelKeywords.waitTillElementIsInvisible(ElementBy,strElementID,strTestData);
			break;
		
		case "fluentWait": 
            lowLevelKeywords.fluentWait(ElementBy,strElementID);
            break;
            
		case "pageLoadTimeout": 
			lowLevelKeywords.pageLoadTimeout(strTestData);
			break;
			
		case "isEnabled":
			vReturn = lowLevelKeywords.isEnabled(ElementBy,strElementID);
			logger.info("isEnabled " + vReturn);
			System.out.println("isEnabled " + vReturn);
			break;
			
		case "isDisabled":
			vReturn = lowLevelKeywords.isDisabled(ElementBy,strElementID);
			logger.info("is disabled " + vReturn);
			System.out.println("isEnabled " + vReturn);
			break;
			
		case "isDisplayed":
			lowLevelKeywords.isDisplayed(ElementBy,strElementID);
			logger.info("is displayed "+lowLevelKeywords.isDisplayed(ElementBy,strElementID));
			break;
		
		case "selectRadio":
			lowLevelKeywords.selectRadio(ElementBy,strElementID,strTestData);
			break;
		
		case "getText":	
			String text = lowLevelKeywords.getText(ElementBy,strElementID);
			logger.info("Keyword getText: Text is "+ text); 
			//System.out.println(text);
			break;
		
		case "isAlertPresent":
		case "verifyAlertIsPresent":
			lowLevelKeywords.isAlertPresent();
			break;		
		
		case "verifyAlertText":
			String result = lowLevelKeywords.verifyAlertText(strTestData);
			logger.info("Keyword : verifyAlertText - Alert text verified! Result is "+ result);
			break;
		
		case "switchToAlert":
			lowLevelKeywords.switchToAlert();
			break;			
		
		case "dismissAlert":
			lowLevelKeywords.dismissAlert();
			break;			
		
		case "confirmAlert":
			lowLevelKeywords.confirmAlert();
			break;			
		
		case "getAlertText":
			String alertText = lowLevelKeywords.getAlertText();
			System.out.println(alertText);
			break;			
			
		case "sendTextToAlert":
			lowLevelKeywords.sendTextToAlert(strTestData);
			break;		
		
		case "verifyAttributeValue":
			String verifyAttribute = lowLevelKeywords.verifyAttributeValue(ElementBy,strElementID,strTestData);
			System.out.println("Keyword verifyAttributeValue "+ verifyAttribute);
			break;
		
		case "getAttributeValue":
			String attributeValue = lowLevelKeywords.getAttributeValue(ElementBy,strElementID,strTestData);
			System.out.println("Value of attribute '"+ strTestData + "' : "+ attributeValue);
			logger.info("Value of attribute '"+ strTestData +"' : "+ attributeValue);
			testCase_Output = attributeValue;
			break;
			
		case "getCssValue":
			String cssValue = lowLevelKeywords.getCssValue(ElementBy,strElementID,strTestData);
			logger.info("css Value is : "+ cssValue);
			break;
			
		case "dragAndDrop": 
            lowLevelKeywords.dragAndDrop(ElementBy,strElementID,strTestData);
            break;
			
		case "dragAndDropByOffset":
			lowLevelKeywords.dragAndDropByOffset(ElementBy,strElementID,strTestData);
			break;
			
		case "mouseHover":
			lowLevelKeywords.mouseHover(ElementBy,strElementID);
			break;
			
		case "contextClick": 
			lowLevelKeywords.contextClick(ElementBy,strElementID);
			break;
			
		case "doubleClick": 
			lowLevelKeywords.doubleClick(ElementBy,strElementID);
			break;
		
		case "takePageScreenshot":
			String screenShotPath = lowLevelKeywords.takePageScreenshot(strTestData);
			if (screenShotPath == "")  
			{
				testCase_Result="Fail";
				logger.info("Exception occured! Could not save screenshot!!!");
			}
			else
				logger.info("Location of screenshot : " + screenShotPath);
			break;
			
		case "toUpperCase": 
			lowLevelKeywords.toUppercase(strTestData);
			break;
			
		case "toLowerCase": 
			lowLevelKeywords.toLowercase(strTestData);
			break;
			
		case "verifyLink":
			lowLevelKeywords.verifyLink(ElementBy,strTestData);
			break;
						
		case "deleteAllCookies":
			lowLevelKeywords.deleteAllCookies();
			break;
					
		case "keyUpAndDown":
			lowLevelKeywords.keyUpAndDown();
			break;
			
		case "reverseString":
			String reverse = lowLevelKeywords.reverseString(strTestData);
			logger.info("The reversed string is "+ reverse);
			break;
			
		case "isElementPresent":
			lowLevelKeywords.isElementPresent(ElementBy,strElementID);
			break;
			
		case "isTextPresent":
			lowLevelKeywords.isTextPresent(strElementID);
			break;
			
		case "waitforAlert":
			lowLevelKeywords.waitforAlert();
			break;
			
		case "waitforTitle":
			lowLevelKeywords.waitforTitle(strTestData);
			break;
			
		case "scrollUpByPixel":
			lowLevelKeywords.scrollUpByPixel();
			break;
			
		case "scrollDownByPixel":
			lowLevelKeywords.scrollDownByPixel();
			break;
			
		case "zoomByPercentage":
			lowLevelKeywords.zoomByPercentage(strTestData);
			break;
		
			/**********************************************************************************/
		default : 
			
			testCase_Result="Fail";
			logger.info("Keyword is missing : " + strKeyWord);

		}
	}

	public String setInput(Object TSout , String strTestData)
	{

		logger.info("inside setInput method");
		String output="";
		// perform something 
		if(TSout instanceof String)
		{ //need to implement when this scenario comes up
			logger.info("string");
		}
		else if(TSout instanceof HashMap)
		{
			HashMap<String, String> hmap = (HashMap<String, String>) TSout;
			
			logger.info("inside hashmap if"+TSout);
			//loop through each entry in the map using Map.Entry
			for (Map.Entry<String, String> entry : hmap.entrySet())
			{
				String key=entry.getKey();
				String value=entry.getValue();
				logger.info("Key : " +key + " Value : " +value);
				if(value.equalsIgnoreCase(strTestData)) {output=key;}
			}
			logger.info("output is hashmap"+output);
		}
		else if(TSout instanceof List)
		{
			//need to implement when this scenario comes up
			logger.info("list");
		}
		return output;
	}
	
}