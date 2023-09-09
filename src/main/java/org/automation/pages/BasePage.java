package org.automation.pages;

import org.automation.testsuitebase.SuiteBase;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BasePage extends SuiteBase {

    public static WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(120),Duration.ofSeconds(1));

    public Actions actions = new Actions(driver);
}
