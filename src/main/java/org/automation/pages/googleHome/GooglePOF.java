package org.automation.pages.googleHome;

import org.automation.pages.BasePage;
import org.automation.utility.PageFunctions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class GooglePOF extends BasePage {

    @FindBy(id="asas")
    public WebElement txt_search;


    public GooglePOF(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void setSearchText(String str) {
        PageFunctions.enterText(txt_search,str);

        }
}
