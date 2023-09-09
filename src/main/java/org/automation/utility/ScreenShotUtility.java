package org.automation.utility;

import org.apache.commons.io.FileUtils;
import org.automation.testsuitebase.SuiteBase;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScreenShotUtility implements ITestListener {

    //This method will execute before starting of Test Suite
    public void onStart(ITestContext tr){}

    //This method will execute once the TestSuite is finished
    public void onFinish(ITestContext tr){}

    public void onTestSuccess(ITestResult tr){
        if (SuiteBase.Param.getProperty("screenShotOnPass").equalsIgnoreCase("yes")){
            captureScreenshot(tr,"pass");
        }
    }

    public void onTestFailure(ITestResult tr){
        if (SuiteBase.Param.getProperty("screenShotOnFail").equalsIgnoreCase("yes")){
            captureScreenshot(tr,"fail");
        }
    }

    public void onTestStart(ITestResult tr){}


    public void onTestSkipped(ITestResult tr){}

    public void onTestFailedButWIthSuccessPercentage(ITestResult tr){}


    public void captureScreenshot(ITestResult result, String status){
        String destDir = "";
        String passfailMethod = result.getMethod().getRealClass().getSimpleName()+"."+result.getMethod().getMethodName();
        //To capture screenshot
        File scrFile = ((TakesScreenshot)SuiteBase.driver).getScreenshotAs(OutputType.FILE);
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy__hh_mm__ssaa");
//        String dt = dateFormat.format(new Date()).split("__")[0];
        if(status.equalsIgnoreCase("fail")){
            destDir = "screenshots/Failures";
        }
        else if(status.equalsIgnoreCase("pass")){
            destDir = "screenshots/Success";
        }

        new File(destDir).mkdirs();
        String destFile = passfailMethod+" - "+dateFormat.format(new Date()+".png");

        try {
            FileUtils.copyFile(scrFile,new File(destDir+"/"+destFile));
        } catch (IOException e){
            e.printStackTrace();
        }
    }

}
