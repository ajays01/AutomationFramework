package org.automation.pages;

import org.automation.testsuitebase.SuiteBase;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BasePage extends SuiteBase {

    public static WebDriverWait wait = new WebDriverWait(driver,120,100);

    public Actions actions = new Actions(driver);
}
