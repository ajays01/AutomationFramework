package org.automation.pages.login;

import org.apache.poi.ss.usermodel.charts.ScatterChartSeries;
import org.automation.pages.BasePage;
import org.automation.utility.PageFunctions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.io.IOException;
import java.util.Base64;

public class LoginPage extends BasePage {

    public LoginPage(WebDriver driver){
        PageFactory.initElements(driver,this);
    }

    @FindBy(xpath = "//input[@id='user-name']")
    private WebElement txt_userName;

    @FindBy(xpath="//input[@id='password']")
    private WebElement txt_password;

    @FindBy(xpath = "//input[@id='login-button']")
    private  WebElement btn_loginButton;


    public void login(String userName, String password,String caseId) throws IOException {
        wait.until(ExpectedConditions.visibilityOf(txt_userName));
        PageFunctions.enterText(txt_userName,userName);
        Add_log.info("User Name entered successfully");
        wait.until(ExpectedConditions.visibilityOf(txt_password));
        PageFunctions.enterText(txt_password,password);
        Add_log.info("Password entered successfully");
        wait.until(ExpectedConditions.visibilityOf(btn_loginButton));
        PageFunctions.takeSnapShotWithText(driver,caseId,"Entering credentials" );
        PageFunctions.clickElement(btn_loginButton);
        Add_log.info("Clicked on login button");
    }


    public void launchApplication(String caseId){
        Add_log.info("Launching URL - "+Param.getProperty("siteUrl"));
        driver.get(Param.getProperty("siteUrl"));
        driver.manage().window().maximize();
        Add_log.info(driver.getTitle());
//        wait.until(ExpectedConditions.titleIs("Swag Labs"));
    }

}
