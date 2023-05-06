package org.automation.utility;

import org.apache.xpath.objects.XObject;

import java.io.IOException;

public class SuiteUtility {
    public static boolean checkToRunUtility(Read_XLS xls,String sheetName,String ToRun,String testSuite){
        boolean Flag = false;
        if(xls.retrieveToRunFlag(sheetName,ToRun,testSuite).equalsIgnoreCase("y")){
            Flag = true;
        }else{
            Flag = false;
        }
        return Flag;
    }

    public static String[] checkRunUtilityOfData(Read_XLS xls, String sheetName,String colName){
        return xls.retrieveToRunFLagTestData(sheetName,colName);
    }

    public static Object[][] GetTestDataUtility(Read_XLS xls, String sheetName){
        return xls.retrieveTestData(sheetName);
    }

    public static boolean WriteResultUtility(Read_XLS xls, String sheetName, String colName, int rowNum, String result) throws IOException {
        return xls.writeResult(sheetName,colName,rowNum,result);
    }

    public static boolean WriteResultUtility(Read_XLS xls, String sheetName, String colName, String rowName, String result) throws IOException {
        return xls.writeResult(sheetName,colName,rowName,result);
    }
}
