package org.automation.googleHome;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.automation.utility.PageFunctions;
import org.automation.utility.Read_XLS;
import org.automation.utility.SuiteUtility;
import org.testng.Reporter;
import org.testng.SkipException;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;

import java.io.IOException;

public class TSL_Google extends GoogleBase {
    Read_XLS FilePath = null;
    String SheetName = null;
    String TestCaseName = null;
    String ToRunColumnNameTestCase = null;
    String ToRunColumnNameTestData = null;
    String TestDataToRun[] = null;
    String TestCaseDocName = null;
    static boolean TestCasePass = true;
    static boolean TestSkip = false;
    static boolean TestFail = true; //Initially considering all test cases are failed
    static int Dataset = -1;
    SoftAssert s_assert = null;

    //Initialize all required pages here


    //Do not change this section
    @BeforeTest
    public void checkCaseToRun() throws IOException {
        //called init() function from suitebase to initialize .xls files.
        init();
        //To set SuiteOne.xls file's path In filePath variable
        FilePath = TestCaseListExcelOne;
        TestCaseName = this.getClass().getSimpleName();
        //SheetName to check CaseToRun flag against test case
        SheetName = "TestCasesList";
        //Name of column in TestCaseList excel sheet
        ToRunColumnNameTestCase = "CaseToRun";
        //Name of column in Test case data sheet
        ToRunColumnNameTestData = "DataToRun";
        //Below given syntax will insert lin in applog.log file
        Add_log.info(TestCaseName + " : Execution Started");

        //To check test case's caseToRun = Y in related excel sheet
        //If CaseToRun = N or blank, Test case will skip execution.
        if (!SuiteUtility.checkToRunUtility(FilePath, SheetName, ToRunColumnNameTestCase, TestCaseName)) {
            Add_log.info(TestCaseName + " : CaseToRun = N hence skipping execution");
            //To report result as skip for test case in TestCaseList sheet.
            SuiteUtility.WriteResultUtility(FilePath, SheetName, "Pass/Fail/Skip", TestCaseName, "Skipped");
            //To throw skip exception for this test case.
            throw new SkipException((TestCaseName + "'s CaseToRun Flag is 'N' or Blank. So skipping execution of " + TestCaseName));
        }
        //To retrieve DataToRun flags of all data set lines from related test data sheet.
        TestDataToRun = SuiteUtility.checkRunUtilityOfData(FilePath, TestCaseName, ToRunColumnNameTestData);
        //For killing al open IEs
        Runtime.getRuntime().exec("taskkill /F /IM iexplorer.exe");
    }

    //Accepts 4 column's String of data in every iteration.

    @Test(dataProvider = "TSL_GoogleData")
    public void TSL_GoogleTest(String sINo, String caseId, String text ) throws IOException {
        //Dataset++;
        Dataset = (Integer.parseInt(sINo)) - 1;

        //created object for soft asserts
        s_assert = new SoftAssert();

        //If found DataToRun = 'N', set TestSkip = True.
        if (!TestDataToRun[Dataset].equalsIgnoreCase("Y")) {
            Add_log.info(TestCaseName + " :DataToRun = N for data set line " + (Dataset + 1) + " So skipping its execution.");
            //If DataToRun = N, set TestSkip = true;
            TestSkip = true;
            throw new SkipException("DataToRun for row number " + Dataset + " is NO or Blank. So skipping the execution");
        }

        TestCaseDocName = caseId;
        Add_log.info("Test case starts here");

        loadWebBrowser();
        driver.get("https://www.google.com");
        PageFunctions.takeSnapShotWithText(driver,caseId,driver.getTitle());


        //**************************************Test Execution Completed****************************************
        if (TestFail) {
            //At last, test data assertion failure will be reported in TestNg report and it will mark your test data, test case and test suite as fail.
            s_assert.assertAll();
            if (true) TestFail = false;
            Reporter.log("Passed " + TestCaseName, true);
        }
    }

    //@AfterMethod will be executed after execution of @Test method every time.
    @AfterMethod
    public void reporterDataResults() throws IOException, InvalidFormatException {
        if (TestSkip) {
            Add_log.info(TestCaseName + " : Reporting test data set line " + (Dataset + 1) + " as SKIP in excel.");
            //If found TestSkip = true, Result will be reported as SKIP against data set line in excel sheet.
            SuiteUtility.WriteResultUtility(FilePath, TestCaseName, "Pass/Fail/Skip", Dataset + 1, "SKIP");

        } else if (TestFail) {
            Add_log.info(TestCaseName + " :Reporting test data set line " + (Dataset + 1) + " as FAIL in excel.");
            //To make object reference null after reporting in report.
            s_assert = null;
            //Set TestCasesPass = false to report test case as fail in excel sheet.
            TestCasePass = false;
            //If found TestFail = true, Result will be reported as FAIL against dataset line in excel sheet.
            SuiteUtility.WriteResultUtility(FilePath, TestCaseName, "Pass/Fail/Skip", Dataset + 1, "FAIL");
            PageFunctions.failureCreateWordDoc(TestCaseDocName);
            closeWebBrowser();
        } else {
            Add_log.info(TestCaseName + " : Reporting test data set line " + (Dataset + 1) + " as PASS in excel");
            //If found TestSKip = false and TestFail = false, Result will be reported as PASS against data set line in excel sheet.
            SuiteUtility.WriteResultUtility(FilePath, TestCaseName, "Pass/Fail/Skip", Dataset + 1, "PASS");
            PageFunctions.createWordDoc(TestCaseDocName);
            closeWebBrowser();
        }
        //At last make both flags as false for next data set.
        TestSkip = false;
        TestFail = false;
    }

    //This data provider method will return 4 column's data one by one in every iteration.
    @DataProvider
    public Object[][] TSL_GoogleData() {
        //To retrieve data from data 1 column, data 2 column, data 3 column and expected result column of SuiteOneCaseOne data sheet.
        //Last two columns (DataToRun and Pass/Fail/Skip) are ignored programatically when reading test data.
        return SuiteUtility.GetTestDataUtility(FilePath, TestCaseName);
    }

    //To report result as pass or fail for test cases in TestCaseList sheet.
    @AfterTest
    public void closeBrowser() throws IOException {
        //To close the web browser at the end of test.
        if (TestCasePass) {
            SuiteUtility.WriteResultUtility(FilePath, SheetName, "Pass/Fail/Skip", TestCaseName, "Pass");
        } else {
            SuiteUtility.WriteResultUtility(FilePath, SheetName, "Pass/Fail/Skip", TestCaseName, "Fail");
        }
    }
}


