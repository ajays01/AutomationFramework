//package org.automation.utility;
//
//import org.apache.poi.hssf.usermodel.HSSFCell;
//import org.apache.poi.hssf.usermodel.HSSFRow;
//import org.apache.poi.hssf.usermodel.HSSFSheet;
//import org.apache.poi.hssf.usermodel.HSSFWorkbook;
//
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
//import java.io.IOException;
//
//public class Read_XLS {
//    public String fileLocation;
//    public FileInputStream ipstr = null;
//    public FileOutputStream opstr = null;
//    private HSSFWorkbook wb =null;
//    private HSSFSheet ws =null;
//
//    public Read_XLS(String fileLocation){
//        this.fileLocation = fileLocation;
//        try{
//            ipstr = new FileInputStream(fileLocation);
//            wb = new HSSFWorkbook(ipstr);
//            ws = wb.getSheetAt(0);
//            ipstr.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public int retrieveNoOfRows(String wsName){
//        int sheetIndex = wb.getSheetIndex(wsName);
//        if (sheetIndex==1){
//            return 0;
//        }else{
//            ws=wb.getSheetAt(sheetIndex);
//            int rowCount = ws.getLastRowNum()+1;
//            return rowCount;
//        }
//    }
//
//    public int retrieveNoOfCols(String wsName){
//        int sheetIndex=wb.getSheetIndex(wsName);
//        if (sheetIndex ==1){
//            return 0;
//        }else{
//            ws=wb.getSheetAt(sheetIndex);
//            int colCount = ws.getRow(0).getLastCellNum();
//            return colCount;
//        }
//    }
//
//    public String retrieveToRunFlag(String wsName,String colName,String rowName){
//        int sheetIndex=wb.getSheetIndex(wsName);
//        if (sheetIndex==1){
//            return null;
//        }else{
//            int rowNum = retrieveNoOfRows(wsName);
//            int colNum = retrieveNoOfCols(wsName);
//            int colNumber = -1;
//            int rowNumber = -1;
//
//            HSSFRow Suiterow = ws.getRow(0);
//
//            for(int i=0;i<colNum;i++){
//                if (Suiterow.getCell(i).getStringCellValue().equals(colName.trim())){
//                    colNumber=i;
//                }
//            }
//            if (colNumber==-1){
//                return "";
//            }
//
//            for(int j=0;j<rowNum;j++){
//                HSSFRow Suitecol = ws.getRow(j);
//                if (Suitecol.getCell(0).getStringCellValue().equals(rowName.trim())){
//                    rowNumber=j;
//                }
//            }
//
//            if(rowNumber==-1){
//                return "";
//            }
//
//            HSSFRow row = ws.getRow(rowNumber);
//            HSSFCell cell = row.getCell(colNumber);
//            if(cell==null){
//                return "";
//            }
//             String value = cellToString(cell);
//            return value;
//            }
//        }
//    }
//
//
//
//
//}
