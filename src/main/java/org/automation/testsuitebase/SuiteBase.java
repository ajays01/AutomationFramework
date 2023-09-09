package org.automation.testsuitebase;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.automation.utility.PageFunctions;
import org.automation.utility.Read_XLS;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.Properties;

public class SuiteBase {

    public static Logger Add_log = null;
    public static Properties Param = null;
    public static WebDriver driver = null;
    public static WebDriver ExistingChromeBrowser;
    public static WebDriver ExistingEdgeBrowser;
    public static Read_XLS TestSuiteListExcel = null;

    public void init() throws IOException {
        //To initialize logger service
        System.setProperty("log4j.configurationFile", System.getProperty("user.dir") + "/src/main/java/org/automation/logreporting/log4j2.xml");
        Add_log = LogManager.getLogger();

        //To initialize Param.properties file
        Param = new Properties();
        FileInputStream fip = new FileInputStream(System.getProperty("user.dir") + "//src//main//java//org//automation//property//Param.properties");
        Param.load(fip);
        Add_log.info("Param.properties file loaded successfully");

        //Initialize SuiteList
        TestSuiteListExcel = new Read_XLS(System.getProperty("user.dir") + "\\src\\main\\java\\org\\automation\\excelfiles\\TestSuiteList.xls");
    }

    public void loadWebBrowser() {
        if(Param.getProperty("testBrowser").equalsIgnoreCase("Chrome")) {
//            System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "//browserdrivers//chromedriver.exe");
            WebDriverManager.chromedriver().setup();
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--no-sandbox--");
            options.addArguments("--disable-gpu--");
            options.addArguments("--start-maximized--");
            options.addArguments("--disable-infobars--");
            options.addArguments("--disable-extensions--");
            driver = new ChromeDriver(options);
            ExistingChromeBrowser = driver;
            Add_log.info("Chrome driver instance loaded successfully");
        }else if(Param.getProperty("testBrowser").equalsIgnoreCase("Edge")){
            WebDriverManager.edgedriver().setup();
            driver = new EdgeDriver();
            ExistingEdgeBrowser = driver;
        }


    }

    public void closeWebBrowser() {
        try {
            driver.close();
            PageFunctions.simpleWait(1000);
            driver.quit();
        } catch (NullPointerException e) {
            Add_log.info("NullPointerException");
        }
        ExistingChromeBrowser = null;
    }

    public static void updateTestResult(String index,  String CaseName, String result) throws IOException {
        String startTime = new SimpleDateFormat("dd-MM-yyyy_HH-ss").format(new GregorianCalendar().getTime());
        Add_log.info("Report Started at - "+startTime);

        String reportFile = System.getProperty("user.dir")+"//target//customizedReport//ExecutionReport.html";

        File file = new File(reportFile);
        Add_log.info(file.exists());

        if(!file.exists()){
            FileWriter fw = new FileWriter(file.getAbsoluteFile(),true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write("<html>" + "\n");
            bw.write("<head><title>" + "Test Execution Report" + "</title>" + "\n");
            bw.write("</head>" + "\n");
            bw.write("<body>");
            bw.write("<font faces 'Tahoma'sizes '2'›" + "\n");
            bw.write("<u><h1 align='center>" + "Test execution report" + "</hi>‹/u›" + "\n");
            bw. flush();
            bw.close();
        }
        BufferedWriter bw1 = new BufferedWriter(new FileWriter(file,true));


    }


}
