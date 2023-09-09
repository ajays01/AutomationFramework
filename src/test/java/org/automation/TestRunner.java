package org.automation;

import org.testng.TestNG;

import java.util.ArrayList;
import java.util.List;

public class TestRunner {

    public static void main(String[] args) {
        //This method is developed to run xml files using main method
        //create object for testng
        TestNG testNG = new TestNG();

        //create a list to add xml files for execution
        List<String> suiteFiles = new ArrayList<>();

        //add xml files to the arraylist
        suiteFiles.add(System.getProperty("user.dir")+"\\xmls\\testng.xml");

        //now set xml file for execution
        testNG.setTestSuites(suiteFiles);

        //finally execute the runner using run method
        testNG.run();
    }

}
