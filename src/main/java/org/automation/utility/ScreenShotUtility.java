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

    public void onStart(ITestContext tc){}

    public void onFinish(ITestContext tc){}

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


    public void captureScreenshot(ITestResult result, String status){
        String destDir = "";
        String passFailMethod = result.getName().getClass().getSimpleName()+"."+result.getMethod().getMethodName();
        //To capture screenshot
        File scrFile = ((TakesScreenshot)SuiteBase.driver).getScreenshotAs(OutputType.FILE);
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy__hh_mm__ssaa");
        String dt = dateFormat.format(new Date()).split("__")[0];
        if(status.equalsIgnoreCase("fail")){
            destDir = "screenshots/Failures/"+dt;
        }
        else if(status.equalsIgnoreCase("pass")){
            destDir = destDir = "screenshots/Success/"+dt;
        }

        new File(destDir).mkdirs();
        String destFile = passFailMethod+" - "+dateFormat.format(new Date()+".png");

        try {
            FileUtils.copyFile(scrFile,new File(destDir+"/"+destFile));
        } catch (IOException e){
            e.printStackTrace();
        }
    }

}
