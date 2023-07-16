package org.automation.utility;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;


public class Read_XLS {
    public String fileLocation;
    public FileInputStream ipstr = null;
    public FileOutputStream opstr = null;
    private HSSFWorkbook wb = null;
    private HSSFSheet ws = null;

    public Read_XLS(String fileLocation) {
        this.fileLocation = fileLocation;
        try {
            ipstr = new FileInputStream(fileLocation);
            wb = new HSSFWorkbook(ipstr);
            ws = wb.getSheetAt(0);
            ipstr.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //To retrieve No of Rows from .xls file
    public int retrieveNoOfRows(String wsName) {
        int sheetIndex = wb.getSheetIndex(wsName);
        if (sheetIndex == -1) {
            return 0;
        } else {
            ws = wb.getSheetAt(sheetIndex);
            int rowCount = ws.getLastRowNum()+1;
            return rowCount;
        }
    }

    //To retrieve No of Columns from .xls file
    public int retrieveNoOfCols(String wsName) {
        int sheetIndex = wb.getSheetIndex(wsName);
        if (sheetIndex == -1) {
            return 0;
        } else {
            ws = wb.getSheetAt(sheetIndex);
            int colCount = ws.getRow(0).getLastCellNum();
            return colCount;
        }
    }

    public String retrieveToRunFlag(String wsName, String colName, String rowName) {
        int sheetIndex = wb.getSheetIndex(wsName);
        if (sheetIndex == -1) {
            return null;
        } else {
            int rowNum = retrieveNoOfRows(wsName);
            int colNum = retrieveNoOfCols(wsName);
            int colNumber = -1;
            int rowNumber = -1;

            HSSFRow Suiterow = ws.getRow(0);

            for (int i = 0; i < colNum; i++) {
                if (Suiterow.getCell(i).getStringCellValue().equals(colName.trim())) {
                    colNumber = i;
                }
            }
            if (colNumber == -1) {
                return "";
            }

            for (int j = 0; j < rowNum; j++) {
                HSSFRow Suitecol = ws.getRow(j);
                if (Suitecol.getCell(0).getStringCellValue().equals(rowName.trim())) {
                    rowNumber = j;
                }
            }

            if (rowNumber == -1) {
                return "";
            }

            HSSFRow row = ws.getRow(rowNumber);
            HSSFCell cell = row.getCell(colNumber);
            if (cell == null) {
                return "";
            }
            String value = cellToString(cell);
            return value;
        }
    }

    public String[] retrieveToRunFLagTestData(String wsName, String colName) {
        int sheetIndex = wb.getSheetIndex(wsName);
        if (sheetIndex == -1)
            return null;
        else {
            int rowNum = retrieveNoOfRows(wsName);
            int colNum = retrieveNoOfCols(wsName);
            int colNumber = -1;

            HSSFRow SuiteRow = ws.getRow(0);
            String data[] = new String[rowNum - 1];
            for (int i = 0; i < colNum; i++) {
                if (SuiteRow.getCell(i).getStringCellValue().equals(colName.trim())) {
                    colNumber = i;
                }
            }
            if (colNumber == 1) {
                return null;
            }
            for (int j = 0; j < rowNum - 1; j++) {
                HSSFRow Row = ws.getRow(j + 1);
                if (Row == null) {
                    data[j] = "";
                } else {
                    HSSFCell Cell = Row.getCell(colNumber);
                    if (Cell == null) {
                        data[j] = "";
                    } else {
                        String value = cellToString(Cell);
                        data[j] = value;
                    }
                }
            }
            return data;
        }
    }

    public Object[][] retrieveTestData(String wsName) {
        int SheetIndex = wb.getSheetIndex(wsName);
        if (SheetIndex == -1)
            return null;
        else {
            int rowNum = retrieveNoOfRows(wsName);
            int colNum = retrieveNoOfCols(wsName);

            Object data[][] = new Object[rowNum - 1][colNum - 2];

            for (int i = 0; i < rowNum-1; i++) {
                HSSFRow row = ws.getRow(i + 1);
                for (int j = 0; j < colNum - 2; j++) {
                    if (row == null) {
                        data[i][j] = "";
                    } else {
                        HSSFCell cell = row.getCell(j);

                        if (cell == null) {
                            data[i][j] = "";
                        } else {
                            cell.setCellType(Cell.CELL_TYPE_STRING);
                            String value = cellToString(cell);
                            data[i][j] = value;
                        }
                    }
                }
            }
            return data;
        }
    }

    public static String cellToString(HSSFCell cell) {
        int type;
        Object result;
        type = cell.getCellType();
        switch (type) {
            case 0:
                result = cell.getNumericCellValue();
                break;

            case 1:
                result = cell.getStringCellValue();
                break;

            default:
                throw new RuntimeException("Unsupported cell.");
        }
        return result.toString();
    }

    public boolean writeResult(String wsName, String colName, int rowNumber, String result) throws IOException {
        try {
            int sheetIndex = wb.getSheetIndex(wsName);
            if (sheetIndex == -1)
                return false;
            int colNum = retrieveNoOfCols(wsName);
            int colNumber = -1;

            HSSFRow Suiterow = ws.getRow(0);
            for (int i = 0; i < colNum; i++) {
                if (Suiterow.getCell(i).getStringCellValue().equals(colName.trim())) {
                    colNumber = i;
                }
            }
            if (colNumber == -1) {
                return false;
            }
            HSSFRow Row = ws.getRow(rowNumber);
            HSSFCell cell = Row.getCell(colNumber);
            if (cell == null)
                cell = Row.createCell(colNumber);

            cell.setCellValue(result);

            opstr = new FileOutputStream(fileLocation);
            wb.write(opstr);
            opstr.close();

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean writeResult(String wsName, String colName, String rowName, String result) throws IOException {
        try {
            int rowNum = retrieveNoOfRows(wsName);
            int rowNumber = -1;
            int sheetIndex = wb.getSheetIndex(wsName);
            if (sheetIndex == -1) {
                return false;
            }
            int colNum = retrieveNoOfCols(wsName);
            int colNumber = -1;

            HSSFRow Suiterow = ws.getRow(0);
            for (int i = 0; i < colNum; i++) {
                if (Suiterow.getCell(i).getStringCellValue().equals(colName.trim())) {
                    colNumber = i;
                }
            }

            if (colNumber == -1) {
                return false;
            }

            for (int i = 0; i < rowNum - 1; i++) {
                HSSFRow row = ws.getRow(i + 1);
                HSSFCell cell = row.getCell(0);
                cell.setCellType(Cell.CELL_TYPE_STRING);
                String value = cellToString(cell);
                if (value.equals(rowName)) {
                    rowNumber = i + 1;
                    break;
                }
            }
            HSSFRow Row = ws.getRow(rowNumber);
            HSSFCell cell = Row.getCell(colNumber);
            if (cell == null) {
                cell = Row.createCell(colNumber);
            }
            cell.setCellValue(result);

            opstr = new FileOutputStream(fileLocation);
            wb.write(opstr);
            opstr.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}

