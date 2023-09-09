package org.automation.test;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

public class Test {

    public static void main(String[] args) {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
//        options.addArguments("--headless");
        WebDriver driver = new ChromeDriver(options);
        System.out.println("Chrome luanched in headless mode");
        driver.navigate().to("https://www.wikipedia.org/");
        driver.manage().window().maximize();
        driver.findElement(By.xpath("//*[text()='Google Play Store']")).click();
        ArrayList<String> newTb = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(newTb.get(1));
        System.out.println(driver.getTitle());
    }

}
