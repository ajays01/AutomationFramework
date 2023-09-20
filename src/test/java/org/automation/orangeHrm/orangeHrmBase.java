package org.automation.orangeHrm;

import com.github.dockerjava.core.dockerfile.DockerfileStatement;
import org.automation.testsuitebase.SuiteBase;
import org.automation.utility.Read_XLS;
import org.automation.utility.SuiteUtility;
import org.testng.SkipException;
import org.testng.annotations.BeforeSuite;

import java.io.IOException;

public class orangeHrmBase extends SuiteBase {
    public static Read_XLS TestCaseListExcelOne = null;

    Read_XLS FilePath = null;
    String SheetName = null;
    String SuiteName = null;
    String ToRunColumnName = null;

    @BeforeSuite
    public void checkSuiteToRun()throws IOException{
        //Called init() function from SuiteSBase class to initialize .xls files
        init();
        //Please change file's path strings below if you have stored them at location other than below
        //Initializing Test Suite One(suiteone.xls) File Path Using constructor of Read_XLS utility class
        Add_log.info("Excel file path = "+System.getProperty("user.dir")+"\\excelfiles\\orangeHrm.xls");
        TestCaseListExcelOne = new Read_XLS(System.getProperty("user.dir")+"\\excelfiles\\orangeHrm.xls");
        //Below given syntax will insert log in applog.log file
        Add_log.info("All excel files are initialized successfully");

        //Initialize Param.Properties file
        FilePath = TestSuiteListExcel;
        SheetName = "SuiteList";
        SuiteName = "orangeHrm";
        ToRunColumnName = "SuiteToRun";


        //If SuiteToRun != "Y" then SuiteOne will be skipped from execution.
        if(!SuiteUtility.checkToRunUtility(FilePath,SheetName,ToRunColumnName,SuiteName)){
            Add_log.info("SuiteToRun = N for "+SuiteName+" hence skipping the execution");
            //To report SuiteOne as skipped in SuiteList sheet of TestSuiteList.xls if SuiteToRun is N
            SuiteUtility.WriteResultUtility(FilePath,SheetName,"Skipped/Executed",SuiteName,"Skipped");
            //It will throw skipException to skip test suite's execution and suite will be marked as skipped in Report
            throw new SkipException(SuiteName+"'s SuiteToRun is 'N' or Blank. Hence skipping the execution of "+SuiteName);
        }
        //To report SuiteOne as 'Executed In SuiteList sheet of TestSuiteList.xls if SuiteToRun='Y'
        SuiteUtility.WriteResultUtility(FilePath,SheetName,"Skipped/Executed",SuiteName,"Executed");
    }


}
