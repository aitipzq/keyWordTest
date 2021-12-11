package cn.gloryroad.util;


import cn.gloryroad.configuration.Constants;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


import java.io.FileInputStream;
import java.io.FileOutputStream;


//主要实现扩展名为“.xlsx”的Excel文件操作
public class ExcelUtil {
    public static boolean testResult;
    private static XSSFSheet ExcelSheet;
    private static XSSFWorkbook ExcelWBook;
    private static XSSFCell Cell;
    private static XSSFRow Row;



    //设定要操作的文件路径和Excel文件中的Sheet
    public static void setExcelFile(String path, String SheetName) throws Exception {
        FileInputStream ExcelFile;
        try {
            ExcelFile = new FileInputStream(path);
            ExcelWBook = new XSSFWorkbook(ExcelFile);
            ExcelSheet = ExcelWBook.getSheet(SheetName);
        } catch (Exception e) {
            throw (e);
        }

    }

    //读取Excel文件指定单元格的函数，此函数只支持扩展名为“.xlsx"的 excel文件
    public static String getCellData(int RowNum, int ColNum) {
        try {
            Cell = ExcelSheet.getRow(RowNum).getCell(ColNum);
            String CellData = Cell.getCellType() == XSSFCell.CELL_TYPE_STRING ? Cell.getStringCellValue() + "" : String.valueOf(Math.round(Cell.getNumericCellValue()));
            return CellData;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }

    }

    //获取Excel文件最后一行的行号
    public static int getLastRowNum() {
        return ExcelSheet.getLastRowNum();
    }

    //读取要操作的Excel文件路径
    public static void setExcelFile(String path) {
        FileInputStream ExcelFile;
        try {
            ExcelFile = new FileInputStream(path);
            ExcelWBook = new XSSFWorkbook(ExcelFile);
        } catch (Exception e) {
            System.out.println("Excel路径设置失败");
            e.printStackTrace();
        }
    }

    //读取sheet文件指定单元格的函数，此函数只支持扩展名为“.xlsx"的 excel文件
    public static String getCellData(String SheetName, int RowNum, int ColNum) {
        try {
            ExcelSheet = ExcelWBook.getSheet(SheetName);
            Cell = ExcelSheet.getRow(RowNum).getCell(ColNum);
            if(Cell == null){
                return "";
            }
            String CellData = Cell.getCellType() == XSSFCell.CELL_TYPE_STRING ? Cell.getStringCellValue() + "" : String.valueOf(Math.round(Cell.getNumericCellValue()));
            return CellData;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }

    }

    //获取置顶sheet中的数据总行数
    public static int getRowCount(String SheetName) {
        ExcelSheet = ExcelWBook.getSheet(SheetName);
        int number = ExcelSheet.getLastRowNum();
        return number;
    }

    //在Excel文件的置顶sheet中，获取第一次包含指定测试用例序号的行号
    public static int getFirstRowContainsTestCaseID(String sheetName, String testCaseName, int colNum) {
        int i;
        ExcelSheet = ExcelWBook.getSheet(sheetName);
        int rowCount = ExcelUtil.getRowCount(sheetName);
        for (i = 0; i < rowCount; i++) {
            if (ExcelUtil.getCellData(sheetName, i, colNum).equalsIgnoreCase(testCaseName)) {
                break;
            }

        }
        return i;
    }

    //获取置顶sheet中某个测试用例步骤的个数
    public static int getTestCaseLastStepRow(String SheetName, String testCaseID, int testCaseStartRowNumber) {
        ExcelSheet = ExcelWBook.getSheet(SheetName);
        for (int i = testCaseStartRowNumber; i <= ExcelUtil.getRowCount(SheetName) - 1; i++) {
            if (!testCaseID.equals(ExcelUtil.getCellData(SheetName, i, Constants.Col_TestCaseID))) {
                int number = i;
                return number;
            }
        }
        int number = ExcelSheet.getLastRowNum() + 1;
        return number;
    }


    //在Excel文件的执行单元格中写入数据
    public static void setCellData(String Sheetname, int RowNum, int ColNum,String Result) {
        ExcelSheet = ExcelWBook.getSheet(Sheetname);
        try {
            Row= ExcelSheet.getRow(RowNum);
            Cell = Row.getCell(ColNum,Row.RETURN_BLANK_AS_NULL);
            if (Cell == null){
                Cell = Row.createCell(ColNum);
                Cell.setCellValue(Result);
            }else {
                Cell.setCellValue(Result);
            }
            FileOutputStream fileOutputStream = new FileOutputStream(Constants.Path_ExcelFile);
            ExcelWBook.write(fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
        }catch (Exception e){

            testResult= false;
            e.printStackTrace();
        }

    }



}
