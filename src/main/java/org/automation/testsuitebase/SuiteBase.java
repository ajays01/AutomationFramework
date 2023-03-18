package org.automation.testsuitebase;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;

import java.util.Properties;

public class SuiteBase {

    public static Logger Add_log = null;
    public static Properties Param = null;
    public static WebDriver driver = null;
    public static  WebDriver ExistingChromeBrowser;
    public static  WebDriver ExistingEdgeBrowser;
//    public static Read_XLS TestSuiteListExcel = null;

    public void init(){
        //To initialize logger service
        System.setProperty("log4j.configurationFile",System.getProperty("user.dir")+"/src/main/java/org/automation/logreporting/log4j2.xml");
        Add_log = LogManager.getLogger();

        //To initialize Param.properties file


    }

}
