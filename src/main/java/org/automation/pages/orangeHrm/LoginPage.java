package org.automation.pages.orangeHrm;

import com.github.dockerjava.core.dockerfile.DockerfileStatement;
import org.automation.pages.BasePage;
import org.automation.utility.PageFunctions;
import org.checkerframework.checker.units.qual.A;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.io.IOException;

public class LoginPage extends BasePage {

    @FindBy(xpath = "//input[@name='username']")
    private WebElement txt_userName;

    @FindBy(xpath = "//input[@name='password']")
    private WebElement txt_password;

    @FindBy(xpath = "//button[@class='oxd-button oxd-button--medium oxd-button--main orangehrm-login-button']")
    private WebElement btn_login;

    public LoginPage(WebDriver driver){
        PageFactory.initElements(driver,this);
    }


    public void launchApplication(String url) {
        driver.get(url);
        Add_log.info(url+ "Loaded successfully");
        wait.until(ExpectedConditions.titleIs("OrangeHRM"));
        driver.manage().window().maximize();
    }

    public void login(String userName, String password, String caseId) {
        PageFunctions.enterText(txt_userName,userName);
        Add_log.info("Username entered as :- "+userName);
        PageFunctions.enterText(txt_password,password);
        Add_log.info("Password entered successfully");
        PageFunctions.clickElement(btn_login);
        Add_log.info("Login button clicked successfully");

    }




}
