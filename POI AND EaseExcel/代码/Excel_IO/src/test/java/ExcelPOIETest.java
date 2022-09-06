import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFFormulaEvaluator;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFFormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.joda.time.DateTime;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class ExcelPOIETest {

    String PATH ="D:\\下载\\";
    @Test
    public void testWrite03() throws IOException {

        //工作簿
        Workbook book  = new HSSFWorkbook();//03    xls

        //工作表
        Sheet sheet = book.createSheet("sheet1");//默认为sheet1

        //行
        Row row = sheet.createRow(0);//行从0开始

        //列
        Cell cell11 = row.createCell(0);//列从0开始，当前坐标(0,0)
        cell11.setCellValue("今日天气");//输入值

        Cell cell12 = row.createCell(1);//，当前坐标(0,1)
        cell12.setCellValue("晴天");


        //行
        Row row2 = sheet.createRow(65537);//行从0开始

        //列
        Cell cell21 = row2.createCell(0);//列从0开始，当前坐标(1,0)
        cell21.setCellValue("统计时间");//输入值

        Cell cell22 = row2.createCell(1);//，当前坐标(1,1)
        String s = new DateTime().toString("yyyy-MM-dd HH:mm:ss");//创建日期并格式化日期
        cell22.setCellValue(s);

        //将工作簿写出
        FileOutputStream fileOutputStream = new FileOutputStream(PATH+"03Test.xls");
        book.write(fileOutputStream);

    }

    @Test
    public void testWrite07() throws IOException {

        //1. 获取工作簿  注意对象
        Workbook book  = new XSSFWorkbook();//07    xlsx

        //2. 获取工作簿
        Sheet sheet = book.createSheet("sheet1");//默认为sheet1

        //3. 获取行
        Row row = sheet.createRow(0);//行从0开始

        //4. 获取列
        Cell cell11 = row.createCell(0);//列从0开始，当前坐标(0,0)
        //5. 写入单元格数据
        cell11.setCellValue("今日天气");

        Cell cell12 = row.createCell(1);//，当前坐标(0,1)
        cell12.setCellValue("晴天");

        //行
        Row row2 = sheet.createRow(1);//行从0开始

        //列
        Cell cell21 = row2.createCell(0);//列从0开始，当前坐标(1,0)
        cell21.setCellValue("统计时间");//输入值

        Cell cell22 = row2.createCell(1);//，当前坐标(1,1)
        String s = new DateTime().toString("yyyy-MM-dd HH:mm:ss");//创建日期并格式化日期
        cell22.setCellValue(s);

        //6. 将工作簿输出到硬盘
        FileOutputStream fileOutputStream = new FileOutputStream(PATH+"07Test.xlsx");
        book.write(fileOutputStream);

    }

    @Test
    public void testWrite03Big() throws IOException {

        long start = System.currentTimeMillis();

        //工作簿
        Workbook book  = new HSSFWorkbook();//03    xls

        //工作表
        Sheet sheet = book.createSheet("sheet1");//默认为sheet1

        for (int i = 0; i < 65536; i++) {
            Row row = sheet.createRow(i);//创建行
            for (int j = 0; j < 36; j++) {
                Cell cell = row.createCell(j);//创建列

                cell.setCellValue("["+i+","+j+"]");//写入数据
            }
        }
        System.out.println("工作簿创建完成，用时： "+((System.currentTimeMillis()-start)/1000)+"s");

        //将工作簿写出
        FileOutputStream fileOutputStream = new FileOutputStream(PATH+"03TestBig.xls");
        book.write(fileOutputStream);

        long end = System.currentTimeMillis();
        System.out.println("文件写入完成");
        System.out.println("总用时： "+ ((end-start)/1000)+"s");
    }

    @Test
    public void testWrite07Big() throws IOException {

        long start = System.currentTimeMillis();

        //工作簿
        Workbook book  = new XSSFWorkbook();//07    xlsx

        //工作表
        Sheet sheet = book.createSheet("sheet1");//默认为sheet1

        for (int i = 0; i < 65536; i++) {
            Row row = sheet.createRow(i);//创建行
            for (int j = 0; j < 36; j++) {
                Cell cell = row.createCell(j);//创建列

                cell.setCellValue("["+i+","+j+"]");//写入数据
            }
        }
        System.out.println("工作簿创建完成，用时： "+((System.currentTimeMillis()-start)/1000)+"s");

        //将工作簿写出
        FileOutputStream fileOutputStream = new FileOutputStream(PATH+"07TestBig.xlsx");
        book.write(fileOutputStream);

        long end = System.currentTimeMillis();
        System.out.println("文件写入完成");
        System.out.println("总用时： "+ ((end-start)/1000)+"s");
    }

    @Test
    public void testWrite07SBig() throws IOException {

        long start = System.currentTimeMillis();

        //工作簿
        Workbook book  = new SXSSFWorkbook();//07    xlsx
        System.out.println(book.getClass().getName());

        //工作表
        Sheet sheet = book.createSheet("sheet1");//默认为sheet1

        for (int i = 0; i < 65536; i++) {
            Row row = sheet.createRow(i);//创建行
            for (int j = 0; j < 36; j++) {
                Cell cell = row.createCell(j);//创建列

                cell.setCellValue("["+i+","+j+"]");//写入数据
            }
        }
        System.out.println("工作簿创建完成，用时： "+((System.currentTimeMillis()-start)/1000)+"s");

        //将工作簿写出
        FileOutputStream fileOutputStream = new FileOutputStream(PATH+"07TestBigS.xlsx");
        book.write(fileOutputStream);

        //清理临时文件
        ((SXSSFWorkbook) book).dispose();
        long end = System.currentTimeMillis();
        System.out.println("文件写入完成");
        System.out.println("总用时： "+ ((end-start)/1000)+"s");
    }

    @Test
    public void testRead03() throws IOException {

        // 获取文件输入流
        FileInputStream fileInputStream = new FileInputStream(PATH + "03TestBig.xls");

        //工作簿
        Workbook book  = new HSSFWorkbook(fileInputStream);//03    xls

        //工作表
        Sheet sheet = book.getSheet("sheet1");//参数可以是工作表名字还可是工作表的索引（从0开始）

        //获取行
        Row row = sheet.getRow(666);
        //获取列
        Cell cell = row.getCell(23);
        //获取值
        String stringCellValue = cell.getStringCellValue();
        System.out.println(stringCellValue);

        fileInputStream.close();

    }

    @Test
    public void testReadType03() throws IOException {

        // 获取文件输入流
        FileInputStream fileInputStream = new FileInputStream(PATH + "03TestBig.xls");

        //工作簿
        Workbook book  = new HSSFWorkbook(fileInputStream);//03    xls

        //工作表
        Sheet sheet = book.getSheet("sheet1");//参数可以是工作表名字还可是工作表的索引（从0开始）

        //获取行
        Row row = sheet.getRow(666);
        //获取列
        Cell cell = row.getCell(23);

        isType(cell);

        fileInputStream.close();

    }

    @Test
    public void testReadFormula03() throws IOException {

        // 获取文件输入流
        FileInputStream fileInputStream = new FileInputStream(PATH + "gongshi.xlsx");

        //工作簿
        Workbook book  = new XSSFWorkbook(fileInputStream);//03    xls

        //工作表
        Sheet sheet = book.getSheet("sheet1");//参数可以是工作表名字还可是工作表的索引（从0开始）

        //获取行
        Row row = sheet.getRow(4);
        //获取列
        Cell cell = row.getCell(0);

        //获取公式
        //FormulaEvaluator接口有两个实现类 XSSFFormulaEvaluator 和 HSSFFormulaEvaluator
        XSSFFormulaEvaluator hssfFormulaEvaluator = new XSSFFormulaEvaluator((XSSFWorkbook) book);

        //输出公式
        System.out.println(cell.getCellFormula());

        CellValue evaluate = hssfFormulaEvaluator.evaluate(cell);
        //evaluate.formatAsString() 获取公式的值
        //输出
        System.out.println(evaluate.formatAsString());


        fileInputStream.close();

    }

    void isType(Cell cell){
        int cellType = cell.getCellType();

        switch (cellType){
            case Cell.CELL_TYPE_STRING  :
                System.out.println("是字符串型数据");
                break;
            case Cell.CELL_TYPE_BOOLEAN  :
                System.out.println("是布尔型数据");
                break;
            case Cell.CELL_TYPE_ERROR :
                System.out.println("是错误值");
                break;
            case Cell.CELL_TYPE_BLANK  :
                System.out.println("是空值");
                break;
            case Cell.CELL_TYPE_FORMULA  :
                System.out.println("是一个公式");
                break;
            case Cell.CELL_TYPE_NUMERIC  :
                if (HSSFDateUtil.isCellDateFormatted(cell)){
                    System.out.println("是日期型数据");
                }else {
                    System.out.println("是数值型数据");
                }
                break;
        }
    }
    
    
}
