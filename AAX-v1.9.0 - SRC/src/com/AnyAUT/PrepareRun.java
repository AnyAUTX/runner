package com.AnyAUT;

import java.io.File;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import com.AnyAUT.Utilities;
import com.AnyAUT.arrayStructure;

import org.testng.annotations.BeforeTest;

public class PrepareRun extends Utilities {

	
	String xlInputPath, xlResultPath, xlFileLocation;
	static int modRowCount, modColumnCount, testCaseRowCount, testCaseColumnCount, testStepRowCount, testStepColumnCount, 
	xRows_HLK, HLKColumnCount, EIDRowCount, EIDColumnCount,testDataRowCount, testDataColumnCount, tempTSRowCount, tempTSColumnCount, executableTCRowCount,
	executableTCColumnCount;
	static String[] testDataSetID, strElementDetails;;
	static String[][] xlmainFileData, xlModuleData, xlTestCaseData, xlTestStepData, xlHighLevelKeysData, xlElementIDData, xlTestData, xlTempTestCases, xlExecutableTCs;
	static String strStepDetail, strCritical, strResult, strError, strScreenShot, strTimeTaken ;
	String strTestStepID, strKeyWord, strKeyWordType, strSeverity;
	static String strElementID, strTestData, strElementType, strElementBy, strTestDataType;
	String strTestStepResult, strTestCaseResult, strTestCaseModuleID, strTestCaseID, strTestCaseIExecute, strModuleID, strModuleExecute;
	static int runnerTSRowCount;
	arrayStructure testStepArray = new arrayStructure();
	
	static int demoCounterLimit = 20000;
	
	arrayStructure testCaseArray = new arrayStructure();
	
	static Logger logger = Logger.getLogger(PrepareRun.class); 
	
	@BeforeTest
	public void readSetUpData() throws Exception {
		
		File resultsDirectory, testSuiteDirectory;
		String TestRunName, TestRunLocation;
		
		TestRunName = new UICode().returnValues("TestRunName");
		TestRunLocation = new UICode().returnValues("TestRunLocation");
		
		PropertyConfigurator.configure("log4j.properties");
		
		xlInputPath = TestRunLocation;
		resultsDirectory = new File(fileAbsolutePath() + "/Results");
		
		if (!resultsDirectory.isDirectory()) {
			resultsDirectory.mkdir();
		}
		
		testSuiteDirectory = new File (resultsDirectory.getAbsolutePath() + "/" + "TestRun_" + TestRunName.trim());
		testSuiteDirectory.mkdir();
		xlResultPath = testSuiteDirectory.getAbsolutePath() + "/" + "TestRun_" + TestRunName.trim() + ".xls";
		
		logger.info("Input FIle Path: " + xlInputPath);
		logger.info("Output file path  :"+ xlResultPath);
		
		runnerTSRowCount = 0;
		
		try {
			
			xlModuleData = readXL(xlInputPath, "Modules");
			xlTestCaseData = readXL(xlInputPath, "TestCases");
			xlTestStepData = readXL(xlInputPath, "TestSteps");
			xlHighLevelKeysData = readXL(xlInputPath, "UserDefinedKeywords");
			xlElementIDData = readXL(xlInputPath, "EID");
			xlTestData = readXL(xlInputPath, "TestData");
			modRowCount = xlModuleData.length;
			modColumnCount = xlModuleData[0].length;
			testCaseRowCount = xlTestCaseData.length;
			testCaseColumnCount = xlTestCaseData[0].length;
			testStepRowCount = xlTestStepData.length;
			testStepColumnCount = xlTestStepData[0].length;
			xRows_HLK = xlHighLevelKeysData.length;
			HLKColumnCount = xlHighLevelKeysData[0].length;
			EIDRowCount = xlElementIDData.length;
			EIDColumnCount = xlElementIDData[0].length;
			testDataRowCount = xlTestData.length;
			testDataColumnCount = xlTestData[0].length;
		}catch(Exception ex) {
			logger.error("Error message:  "+ ex);
		}
		
	}
	@Test
	public void createRunSetUpData() throws Exception {
		
		String vTSTCID, vTSModID, vTDSets;
		int a;
		
		logger.info("Entered Main Test");
		//this method is used to identify the executable test cases
		getExecutableTestCases();
		
		//sets the header row of the test step output sheet
		setTopRow();
		
		a = 1;
		
		
		try{
			//loop through each of the executable test cases
			for (int i = 0; i < executableTCRowCount; i++) {
				if (runnerTSRowCount < demoCounterLimit) {
						strTestCaseID = xlExecutableTCs[i][1];
						strModuleID = xlExecutableTCs[i][0];
						vTDSets = xlExecutableTCs[i][5];
						strSeverity = xlExecutableTCs[i][6];
						//call fetchTestDataSets method to fetch the Test Data set IDs for each test case
						testDataSetID = fetchTestDataSets(vTDSets);
						//steps are repeated for the number of Test Data sets for each test case
						for (int r = 0; r < testDataSetID.length; r++) {
							//if a null value is in Test Data set then break out of the for loop
							if (testDataSetID[r] == null) { 
								break; 
							}
							runnerTSRowCount = 1;
							//steps are repeated for the number of Test Steps for each test case
							for (int j = 1;j < testStepRowCount;j++) {
								strElementDetails = new String[2];
								vTSTCID = xlTestStepData[j][1];
								vTSModID = xlTestStepData[j][0];
								//verify if the Module ID and Test Case at the test case level are same as test step
								if ((strModuleID.equalsIgnoreCase(vTSModID)==true) &&(strTestCaseID.equals(vTSTCID)==true)) {
									//capture elementID, Keyword type, Test Data from Test Data sheet
									strElementType = xlTestStepData[j][6];
									strElementBy = xlTestStepData[j][7];
									strElementID = xlTestStepData[j][8];
									strTestDataType = xlTestStepData[j][9];
									strTestData = xlTestStepData[j][10];
									strKeyWordType = xlTestStepData[j][4];
									//Added to include severity column in TestRun
									//strSeverity=xlTestStepData[j][16];
									//verify if the Keyword is HLK
									if (strKeyWordType.equalsIgnoreCase("UDK")==true) {
										//repeat steps for the number of number in Highlevel Keywords sheet
										for (int l = 1; l < xRows_HLK; l++) {
											//verify if the step description of a test step matches the step description in highlevel keywords sheet
											if (xlTestStepData[j][5].equals(xlHighLevelKeysData[l][0])==true) {
												strStepDetail = xlHighLevelKeysData[l][3];
												strKeyWord = xlHighLevelKeysData[l][4];	
												strElementType = xlHighLevelKeysData[l][5];
												strElementBy = xlHighLevelKeysData[l][6];
												strElementID = xlHighLevelKeysData[l][7];
												strTestDataType = xlHighLevelKeysData[l][8];
												strTestData = xlHighLevelKeysData[l][9];
												strCritical = xlHighLevelKeysData[l][10];
												//call this method to assign data in Output, Result, Error, 
												//ScreenShot and TimeTaken columns
												assignRemainingData(j);
												
												//call this method to fetch the value of the element
												if (strElementType.trim().equalsIgnoreCase("Reusable Element")) {//SR
													strElementDetails = getEID(strElementID);
												} else if (strElementType.trim().length() == 0){
													strElementDetails[0] = "-";
													strElementDetails[1] = "-";
													//strElementDetails[2] = "-";
												} else if ((strElementType.trim().length() > 0) || (strElementType.equals("-")) ){
													//strElementDetails[0] = strElementType;
													strElementDetails[0] = strElementBy;
													strElementDetails[1] = strElementID;
												} else if ((strElementType.trim().length() > 0) || (strElementType.equals("One Time Use")) ){
													//strElementDetails[0] = strElementType;
													strElementDetails[0] = strElementBy;
													strElementDetails[1] = strElementID;
												}/*
												} else if (strElementType.trim().length() == 0){
												strElementDetails[0] = "-";
												strElementDetails[1] = "-";
												}else if ((strElementType.trim().length() > 0) || (strElementType.equals("-")) ){
													strElementDetails[0] = strElementType;
													strElementDetails[1] = strElementID;
												}*/
													
												// Karthik : Changing to fix the @@@ change
												/*} else if ((strElementType.trim().length() == 0) || (strElementType.equals("-")){
													strElementDetails[0] = "-";
													strElementDetails[1] = "-";
												}else if ((strElementType.trim().length() > 0)){
													// This code gets executed only for One Time Use.
													String[] strElement;
													strElement = strElementID.split("@@@");
													strElementDetails[0] = strElement[0];
													strElementDetails[1] = strElement[1];
													
													// strElementDetails[0] = strElementType;
													// strElementDetails[1] = strElementID;
												}*/
												// End of Karthik's changes
												System.out.println("Test Data type : " + strTestDataType);
												//fetch test data
												if ((testDataSetID[r].trim().equals("-")) || (testDataSetID[r].trim().length() == 0)) {//SR
													strTestData = "-";
												}else if (strTestDataType.trim().equalsIgnoreCase("Reusable TestData")) {//SR
													//call this method to fetch the test data value
													strTestData = getTD(strTestData, testDataSetID[r].trim());
												}
												//call this method to add the o/p data to an array
												updateTestRunnerArray(a, j, testDataSetID[r].trim());
												a++;
											}//end of if	
										}//end of l for loop
									} else if (strKeyWordType.equalsIgnoreCase("BIK")) {
										strStepDetail = xlTestStepData[j][3];
										strKeyWord = xlTestStepData[j][5];
										strCritical = xlTestStepData[j][11];
										//call this method to assign data in Output, Result, Error, 
										//ScreenShot and TimeTaken columns
										assignRemainingData(j);
										//call this method to fetch the value of the element
										if (strElementType.equalsIgnoreCase("Reusable Element")) {//SR
											strElementDetails = getEID(strElementID);
										} else if (strElementType.trim().length() == 0){
											strElementDetails[0] = "-";
											strElementDetails[1] = "-";
											//strElementDetails[2] = "-";
										} else if ((strElementType.trim().length() > 0) || (strElementType.trim().equals("-")) ){
											//strElementDetails[0] = strElementType;
											strElementDetails[0] = strElementBy;
											strElementDetails[1] = strElementID;
										} else if ((strElementType.trim().length() > 0) || (strElementType.trim().equals("One Time Use")) ){
											//strElementDetails[0] = strElementType;
											strElementDetails[0] = strElementBy;
											strElementDetails[1] = strElementID;
										}
										/*
										} else if (strElementType.trim().length() == 0){
											strElementDetails[0] = "-";
											strElementDetails[1] = "-";
										}else if ((strElementType.trim().length() > 0) || (strElementType.equals("-")) ){
											strElementDetails[0] = strElementType;
											strElementDetails[1] = strElementID;
										}*/
											
										// Karthik : Changing to fix the @@@ change
										/*} else if ((strElementType.trim().length() == 0) || (strElementType.equals("-")){
											strElementDetails[0] = "-";
											strElementDetails[1] = "-";
										}else if ((strElementType.trim().length() > 0)){
											// This code gets executed only for One Time Use.
											String[] strElement;
											strElement = strElementID.split("@@@");
											strElementDetails[0] = strElement[0];
											strElementDetails[1] = strElement[1];
											
											// strElementDetails[0] = strElementType;
											// strElementDetails[1] = strElementID;
										}*/
										// End of Karthik's changes
										if ((testDataSetID[r].trim().equals("-")) || (testDataSetID[r].trim().length() == 0)) {
											strTestData = "-";
										}else if (strTestDataType.equalsIgnoreCase("Reusable TestData")) {//SR
											//call this method to fetch the test data value
											strTestData = getTD(strTestData, testDataSetID[r].trim());
										}
										//call this method to add the o/p data to an array
										updateTestRunnerArray(a, j, testDataSetID[r].trim());
										a++;
										}//end of if
								}//end if
							}//end j loop
						}//end of r loop
					} else {
						break;//end i for loop
					}
				
			}
		}catch (Exception e) {
			logger.error("Exception occured in main test");
			e.printStackTrace();
		}
		
		logger.info("Main test preparation is completed");
	
	}


	private void getExecutableTestCases() {
		int vMod_Rows, p;
		
		p = 0;
		vMod_Rows = xlModuleData.length;
		xlExecutableTCs = new String[xlTestCaseData.length][xlTestCaseData[0].length];
		
		//loops the number modules in TestSetup
		for (int i = 1; i< vMod_Rows; i++) {
			//verify if Execute column has All
			if (xlModuleData[i][3].equalsIgnoreCase("All")) {
				for (int j = 1; j < xlTestCaseData.length; j++) {
					if (xlModuleData[i][1].equals(xlTestCaseData[j][0])==true) {
						xlExecutableTCs[p][0] = xlTestCaseData[j][0];
						xlExecutableTCs[p][1] = xlTestCaseData[j][1];
						xlExecutableTCs[p][2] = xlTestCaseData[j][2];
						xlExecutableTCs[p][3] = xlTestCaseData[j][3];
						xlExecutableTCs[p][4] = xlTestCaseData[j][4];
						xlExecutableTCs[p][5] = xlTestCaseData[j][5];
						xlExecutableTCs[p][6] = xlTestCaseData[j][6];
						//Added to include Result in TestRun Sheet
						//xlExecutableTCs[p][7] = xlTestCaseData[j][7];
						p++;
						executableTCRowCount = p;
					}
				}
				//verify if Execute column has N or Y
			} else if (xlModuleData[i][3].equalsIgnoreCase("Y")) {
				//loop for the number of test cases in TestSetup
				for (int j = 1; j < xlTestCaseData.length; j++) {
					//verify if module ID from Modules sheet matches with the one in Test Cases sheet
					if (xlModuleData[i][1].equals(xlTestCaseData[j][0])==true) {	
						//verify if execute is Y in test cases sheet
						if (xlTestCaseData[j][3].equalsIgnoreCase("Y")) {
							//loop for the number test steps in TestSteps sheet
							for (int m = 1; m < testStepRowCount; m++) {
								//verify if there is atleast one Test Step for the test case
								if (xlTestCaseData[j][1].equalsIgnoreCase(xlTestStepData[m][1]) == true)  
								{
									xlExecutableTCs[p][0] = xlTestCaseData[j][0];
									xlExecutableTCs[p][1] = xlTestCaseData[j][1];
									xlExecutableTCs[p][2] = xlTestCaseData[j][2];
									xlExecutableTCs[p][3] = xlTestCaseData[j][3];
									xlExecutableTCs[p][4] = xlTestCaseData[j][4];
									xlExecutableTCs[p][5] = xlTestCaseData[j][5];
									//Added to include severity column in TestRun Sheet
									xlExecutableTCs[p][6] = xlTestCaseData[j][6];
									//Added to include Result in TestRun Sheet
									//xlExecutableTCs[p][7] = xlTestCaseData[j][7];
									p++;
									executableTCRowCount = p;
									break;
								}//end if
							}//end for loop
						}//end of if
					}//end of if
				}//end of j for loop
			}//end of if
		}//end of i for loop	
		logger.info("Completed fetching the test cases ready for execution");
	}

	private static void assignRemainingData(int j) 
	{		
		//strCritical = xlTestStepData[j][11];
		strResult = xlTestStepData[j][12];
		strError = xlTestStepData[j][13];
		strScreenShot = xlTestStepData[j][14];
		strTimeTaken = xlTestStepData[j][15];
		demoCounterLimit--;
		System.out.println("Prepared " + demoCounterLimit + "out of 20000");
	}
	
	@SuppressWarnings("null")
	private static String[] fetchTestDataSets(String vTDSets) {
		
		String[] vTDSetIDs = null;

		try {
			if (vTDSets.equalsIgnoreCase("All")) {
				int m = 0;
				vTDSetIDs = new String[testDataColumnCount];
				for (int x = 1; x < testDataColumnCount; x++) {
					vTDSetIDs[m] = xlTestData[0][x];
					m++;				
				}
			} else if ((vTDSets != "-") || (!vTDSets.equals("N/A"))) {
				vTDSetIDs = vTDSets.split(",");
			} else {
				vTDSetIDs[0] = "N/A";
			}
		}catch (Exception ex) {
			logger.error("Error occured in fetchTestDataSets method:   "+ ex);
		}
		logger.info("Completed fetching test data sets for test case");
		return vTDSetIDs;
	}
	
	@AfterTest
	public void writeRunSetupData() throws Exception {
		// 8 Publish results back to an Excel
		xlTempTestCases = testStepArray.toArray();
		writeXLSheets(xlResultPath, "TestCases", 0, TCSheet());
		writeXLSheets(xlResultPath, "TestSteps", 1, xlTempTestCases);
	}

	public void updateTestRunnerArray(int fA, int fJ, String vTDID) 
	{
		
		testStepArray.add(fA, 0, strModuleID);
		testStepArray.add(fA, 1, strTestCaseID);
		/*if (vTDID.trim().equals("-")|| vTDID.trim().equals("N/A")) {
			testStepArray.add(fA, 2, xlTestStepData[fJ][1]+"_"+runnerTSRowCount);
		} else {
			testStepArray.add(fA, 2, xlTestStepData[fJ][1]+"_"+vTDID+"_"+runnerTSRowCount);
		}
		if (vTDID.trim().equals("-")|| vTDID.trim().equals("N/A")) {
			testStepArray.add(fA, 2, "#-" +runnerTSRowCount);
		} else {
			testStepArray.add(fA, 2, "#_"+vTDID+"-"+runnerTSRowCount);
		}*/
		testStepArray.add(fA, 2, "#-" +runnerTSRowCount);
		testStepArray.add(fA, 3, strStepDetail);
		testStepArray.add(fA, 4, strKeyWord);

		testStepArray.add(fA, 5, strElementType);
		testStepArray.add(fA, 6, strElementDetails[0]);
		testStepArray.add(fA, 7, strElementDetails[1]);
		testStepArray.add(fA, 8, strTestData);
		testStepArray.add(fA, 9, strCritical);
		testStepArray.add(fA, 10, strResult);
		testStepArray.add(fA, 11, strError);
		testStepArray.add(fA, 12, strScreenShot);
		testStepArray.add(fA, 13, strTimeTaken);
		testStepArray.add(fA, 14, vTDID);
		testStepArray.add(fA, 15, strSeverity);
		
		runnerTSRowCount++;
		
	}
	
	public void setTopRow()
	{
		int x = 0;
		for (x = 0; x < 4; x++)
		{  
			testStepArray.add(0, x, xlTestStepData[0][x]);  
		}
		for (x = 4; x < 8; x++)
		{  
			System.out.println("Value in the test step  " + xlTestStepData[0][x+1]);
			testStepArray.add(0, x, xlTestStepData[0][x+1]);  
		}
		for (x = 8 ; x < testStepColumnCount-2; x++) {
			System.out.println("Value in the test step  " + xlTestStepData[0][x+2]);
			testStepArray.add(0, x, xlTestStepData[0][x+2]);
		}
		testStepArray.add(0, x, "Test Data Set");
		int y=x+1;
		testStepArray.add(0, y, "Severity");
		logger.info("Completed assigning the top row");
	}

	public static String[] getEID(String fEID) {
		// Go to each row in PageObjects
		String[] elementDetails = null;
		elementDetails = new String[2];
		try{
			if ((fEID.equalsIgnoreCase("-")) || (fEID.equals(null))) {
				//return fEID;
				elementDetails[0] = "-";
				elementDetails[1] = "-";
				return elementDetails;
			} else {
				for (int m = 1; m < EIDRowCount; m++) {
					// Check if the EID's match
					if (fEID.equals(xlElementIDData[m][1])) {
						// Return the xPath
						elementDetails[0] = xlElementIDData[m][2];
						elementDetails[1] = xlElementIDData[m][3];
						return elementDetails;
					}
				}
			}//end of if
		}catch(Exception e) {
			logger.error("Exception occured while fetching elements  "+ e);
		}
		elementDetails[0] = "-";
		elementDetails[1] = "-";
		return elementDetails;
	}

	public static String getTD(String fTD, String vTDSID) {
		// Go to each row in TestData
		System.out.println(fTD + "  " + vTDSID );
		for (int n = 2; n < testDataRowCount; n++) {
			// Check if the TDID's match
			if (fTD.equalsIgnoreCase(xlTestData[n][0])) {
				for (int x = 0; x < testDataColumnCount; x++) {
					if (xlTestData[0][x].equalsIgnoreCase(vTDSID)) {
						return xlTestData[n][x];
					}
				}	
			}
		}
		// If TDID's do not match any one, then return TDID's as is
		return fTD;
	}
	

	public String [][] TCSheet()
	{
		int nrow = 1;
		executableTCColumnCount = xlExecutableTCs[0].length;
		
		for (int tcrow = 0 ; tcrow< executableTCRowCount; tcrow++)
		{	
			testCaseArray.add(nrow, 0, xlExecutableTCs[tcrow][0]);
			testCaseArray.add(nrow, 1, xlExecutableTCs[tcrow][1]);
			testCaseArray.add(nrow, 2, xlExecutableTCs[tcrow][2]);
			testCaseArray.add(nrow, 3, xlExecutableTCs[tcrow][3]);
			testCaseArray.add(nrow, 4, "-");
			testCaseArray.add(nrow, 5, xlExecutableTCs[tcrow][5]);
			//Added to include severity column in TestRun Sheet
			testCaseArray.add(nrow, 6, xlExecutableTCs[tcrow][6]);
			//Added to include Result in TestRun Sheet
			//testCaseArray.add(nrow, 7, xlExecutableTCs[tcrow][7]);
			nrow++;	
		}
		//assign the header for the Test Cases output sheet
		for (int tccol=0 ; tccol< executableTCColumnCount; tccol++) 
		{
			testCaseArray.add(0, tccol, xlTestCaseData[0][tccol]);
		}
		
		String xltc_updated[][] =testCaseArray.toArray();
		logger.info("Test Cases are added to the array");
		return xltc_updated;
	}
}

