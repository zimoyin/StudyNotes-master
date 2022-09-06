# POI

依赖pom.xml

```xml
        <!--  xls(03) -->
        <dependency>
            <groupId>org.apache.poi</groupId>
            <artifactId>poi</artifactId>
            <version>3.9</version>
        </dependency>

        <!--  xlsx(07) -->
        <dependency>
            <groupId>org.apache.poi</groupId>
            <artifactId>poi-ooxml</artifactId>
            <version>3.9</version>
        </dependency>

        <!--  日期格式化工具 -->
        <dependency>
            <groupId>joda-time</groupId>
            <artifactId>joda-time</artifactId>
            <version>2.10.1</version>
        </dependency>

        <!-- 单元测试 -->
        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <version>4.12</version>
        </dependency>
```



##  一、POI概述

　　Apache POI是Apache软件基金会的开放源码函式库，POI提供API给Java程序对Microsoft Office格式档案读和写的功能。

　　结构：

- 
  - HSSF － 提供读写**Microsoft Excel**格式档案的功能。(03版。***.xls**。最多有65536行数据)
  - XSSF － 提供读写**Microsoft Excel OOXML**格式档案的功能。(07版。***.xlsx**。无限制)
  - HWPF － 提供读写**Microsoft Word**格式档案的功能。
  - HSLF － 提供读写**Microsoft PowerPoint**格式档案的功能。
  - HDGF － 提供读写**Microsoft Visio**格式档案的功能。
  
  教程：https://www.cnblogs.com/huajiezh/p/5467821.html
  
  

## 二、Excel

### 1. Excel

- HSSF － 提供读写**Microsoft Excel**格式档案的功能。(03版。***.xls**。最多有65536行数据)
- XSSF － 提供读写**Microsoft Excel OOXML**格式档案的功能。(07版。***.xlsx**。无限制)



### 2 Excel基本对象

* 工作簿

```java
//工作簿
Workbook book  = new HSSFWorkbook();//(03)xls
Workbook book2  = new SXSSFWorkbook();//更加快速的处理(07)xlsx
Workbook book3  = new XSSFWorkbook();//(07)	xlsx
```

* 行

```java
Row row = sheet.createRow(0);//行从0开始
```

* 列（一行一列确定一个**单元格**,所以这个一个单元格）

```java
//列
Cell cell11 = row.createCell(0);//列从0开始，当前坐标(0,0)
```

### 3 基本写入Excel

##### 3.1 基本步骤

1. 创建工作簿
2. 创建工作簿
3. 创建行
4. 创建列
5. 写入单元格数据
6. 将工作簿输出到硬盘
7. 关闭流

##### 3.2（03）xls文件

```java
String PATH ="D:\\下载\\";
    @Test
    public void testWrite03() throws IOException {
		//POIFSFileSystem fs=newPOIFSFileSystem(new FileInputStream("d:/test.xls"));    
        //Workbook book  = new HSSFWorkbook(fs);//03    xls
        //1. 获取工作簿
        Workbook book  = new HSSFWorkbook();//03    xls

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
        FileOutputStream fileOutputStream = new FileOutputStream(PATH+"03Test.xls");
        book.write(fileOutputStream);
        //关闭流
        fileOutputStream.close();

    }
```

##### 3.3 （07）xlsx文件

```java
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
        //关闭流
        fileOutputStream.close();

    }
```



##### 注意事项：

1. 03 (HSSFWorkbook) 和 07(XSSFWorkbook) 的**对象**不同注意不要弄错
2. 03(xls)和07(xlsx)的**拓展名**不同注意不要弄错
3. 03(xls) 最多写入**65536行**数据，如果超出这个值会报异常

```java
java.lang.IllegalArgumentException: Invalid row number (65537) outside allowable range (0..65535)
```



### 4. 大文件写入

基本步骤和上面一样

##### 4.1（HSSF）xls大文件

```java
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

        //关闭流
        fileOutputStream.close();
        long end = System.currentTimeMillis();
        System.out.println("文件写入完成");
        System.out.println("总用时： "+ ((end-start)/1000)+"s");
    }
```

结果:

```java
工作簿创建完成，用时： 4s
文件写入完成
总用时： 8s
```

##### 4.2（XSSF）xlsx大文件

```java
@Test
    public void testWrite07Big() throws IOException {

        long start = System.currentTimeMillis();

        //工作簿
        Workbook book  = new XSSFWorkbook();//03    xls

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
        
        //关闭流
        fileOutputStream.close();

        long end = System.currentTimeMillis();
        System.out.println("文件写入完成");
        System.out.println("总用时： "+ ((end-start)/1000)+"s");
    }
```

结果：

```java
java.lang.OutOfMemoryError: Java heap space
```

程序跑了7m 26s 632ms最后因为OutOfMemoryError 程序而终止了



##### 4.3  (SXSS） xlsx大文件

```java
@Test
public void testWrite07SBig() throws IOException {

    long start = System.currentTimeMillis();

    //工作簿
    Workbook book  = new SXSSFWorkbook();//07    xlsx

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

    //清理临时文件(dispose()是sxff特有的方法所以要向下转行)
    ((SXSSFWorkbook) book).dispose();

    //关闭流
    fileOutputStream.close();

    long end = System.currentTimeMillis();
    System.out.println("文件写入完成");
    System.out.println("总用时： "+ ((end-start)/1000)+"s");
}
```

结果:

```java
工作簿创建完成，用时： 1s
文件写入完成
总用时： 3s
```

可以看出sxff比xssf快多了



##### 总结：

1. **第一种：HSSFWorkbook**

poi导出excel最常用的方式；但是此种方式的局限就是导出的行数至多为65535行，超出65536条后系统就会报错。此方式因为行数不足七万行所以一般不会发生内存不足的情况（OOM）。



2. **第二种：XSSFWorkbook**

这种形式的出现是为了突破HSSFWorkbook的65535行局限。其对应的是excel2007(1048576行，16384列)扩展名为“.xlsx”，最多可以导出104万行，不过这样就伴随着一个问题---OOM内存溢出，原因是你所创建的book sheet row cell等此时是存在内存的并没有持久化。



3. **第三种：SXSSFWorkbook**

从POI 3.8版本开始，提供了一种基于XSSF的低内存占用的SXSSF方式。对于大型excel文件的创建，一个关键问题就是，要确保不会内存溢出。其实，就算生成很小的excel（比如几Mb），它用掉的内存是远大于excel文件实际的size的。如果单元格还有各种格式（比如，加粗，背景标红之类的），那它占用的内存就更多了。对于大型excel的创建且不会内存溢出的，就只有SXSSFWorkbook了。**SXSSF它的原理很简单，用硬盘空间换内存（就像hash map用空间换时间一样），他生成100条记录的时候会保存在内存中，超出后会将前面的数据保存在临时文件中，当然这个数量可以改变的`new SXSSFWorkbook(数量)` 。**



SXSSFWorkbook是streaming版本的XSSFWorkbook,它只会保存最新的excel rows在内存里供查看，在此之前的excel rows都会被写入到硬盘里（Windows电脑的话，是写入到C盘根目录下的temp文件夹）。被写入到硬盘里的rows是不可见的/不可访问的。只有还保存在内存里的才可以被访问到。

SXSSF与XSSF的对比：

a. 在一个时间点上，只可以访问一定数量的数据

b. 不再支持Sheet.clone()

c. 不再支持公式的求值

d. 在使用Excel模板下载数据时将不能动态改变表头，因为这种方式已经提前把excel写到硬盘的了就不能再改了



当数据量超出65536条后，在使用HSSFWorkbook或XSSFWorkbook，程序会报OutOfMemoryError：Javaheap space;内存溢出错误。这时应该用SXSSFworkbook。



### 5 读取文件

##### 5.1 基本操作

1.  获取文件输入流
2. 获取工作簿
3. 获取表
4. 获取行
5. 获取列
6. 判断该单元格是否为空
7. 判断单元格数据类型
8. 获取单元格数据(根据单元格的数据类型使用不同的方法获取)
9. 关闭流

##### 5.2 读取文件

```java
  @Test
    public void testRead03() throws IOException {

        // 获取文件输入流
        FileInputStream fileInputStream = new FileInputStream(PATH + "03TestBig.xls");

        //工作簿
        Workbook book  = new HSSFWorkbook(fileInputStream);//03    xls
        // XSSFWorkbook workbook = (XSSFWorkbook) WorkbookFactory.create(new File("d:/test1.xlsx"));

        //工作表
        Sheet sheet = book.getSheet("sheet1");//参数可以是工作表名字还可是工作表的索引（从0开始）

        //获取行
        Row row = sheet.getRow(666);
        //获取列
        Cell cell = row.getCell(23);
        //获取值之前要先进行非空判断
        //获取值，这样是字符串类型所以用getStringCellValue()方法
        String stringCellValue = cell.getStringCellValue();
        System.out.println(stringCellValue);

        fileInputStream.close();

    }
```

注意： 

- 获取值时要注意值得类型
- 获取值之前要先进行非空判断

##### 5.3 判断数据类型以及常用方法

###### 5.3.1 常用方法

- 获取一行当中有多少个列

```java
//获取行
Row row = sheet.getRow(666);
//获取一行当中有多少个列
int count=row.getPhysicalNumberOfCells();
```

- 获取这个工作表中有多少个行

```java
//获取工作表
Sheet sheet = book.getSheet("sheet1");//参数可以是工作表名字还可是工作表的索引（从0开始）
int count= sheet.getPhysicalNumberOfRows();
```

- 获取工作簿当中有多少个工作表

```java
//工作簿
Workbook book  = new HSSFWorkbook(fileInputStream);//03    xls
int count=book.getNumberOfSheets()
```



- 获取数据类型

```java

//获取数据类型
int c = cell.getCellType();
//判断数据是数字还是日期
boolean b= HSSFDateUtil.isCellDateFormatted(cell);
```

- 数据类型值所对应的数据类型

```java
int CELL_TYPE_NUMERIC = 0;//数字(数字、日期)
int CELL_TYPE_STRING = 1;//字符串
int CELL_TYPE_FORMULA = 2;//单元格类型公式
int CELL_TYPE_BLANK = 3;//空值
int CELL_TYPE_BOOLEAN = 4;//布尔值
int CELL_TYPE_ERROR = 5;//错误值
```



###### 5.3.2 判断数据类型

```java
//判断单元格类型
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


```



###### 5.3.3 读取/计算公式

```java
    @Test
    public void testReadFormula03() throws IOException {

        // 获取文件输入流
        FileInputStream fileInputStream = new FileInputStream(PATH + "gongshi.xlsx");

        //工作簿
        Workbook book  = new XSSFWorkbook(fileInputStream);//03    xls

        //工作表
        Sheet sheet = book.getSheet("sheet1");//参数可以是工作表名字还可是工作表的索引（从0开始）

        //(4,0)是一个sum()求和公式
        //获取行
        Row row = sheet.getRow(4);
        //获取列
        Cell cell = row.getCell(0);

        //获取公式
        //FormulaEvaluator接口有两个实现类 XSSFFormulaEvaluator 和 HSSFFormulaEvaluator
        //这里使用的是HSSFFormulaEvaluator
        XSSFFormulaEvaluator hssfFormulaEvaluator = new XSSFFormulaEvaluator((XSSFWorkbook) book);

        //输出公式
        System.out.println(cell.getCellFormula());

        CellValue evaluate = hssfFormulaEvaluator.evaluate(cell);
        //evaluate.formatAsString() 获取公式的值
        //输出
        System.out.println(evaluate.formatAsString());


        fileInputStream.close();

    }
```



##### 5.4 读取工作簿

###### 5.4.1 工作簿对象

> XSSFWorkbook
>
> HSSFWorkbook
>
> SXSSFWorkbook
>
> Workbook

###### 5.4.2  通过 WorkbookFactory 类创建

工厂创建工作簿对象的几种方法，当然也可以仿照工厂类里面的方法来自己创建工作簿对象。创建后的对象可以根据需要向下向上转型

```java
public class WorkbookFactory {
    public WorkbookFactory() {
    }
	//通过 POIFSFileSystem 来创建一个 HSSF 模型的工作簿对象 
    public static Workbook create(POIFSFileSystem fs) throws IOException {
        return new HSSFWorkbook(fs);
    }
	//通过 NPOIFSFileSystem 来创建一个 HSSF 模型的工作簿对象 
    public static Workbook create(NPOIFSFileSystem fs) throws IOException {
        return new HSSFWorkbook(fs.getRoot(), true);
    }
	//通过 OPCPackage 来创建一个 XSSF 模型的对象 
    public static Workbook create(OPCPackage pkg) throws IOException {
        return new XSSFWorkbook(pkg);
    }
	//通过 InputStream 流来创建一个 HSSF 模型或 XSSF 模型的对象
    public static Workbook create(InputStream inp) throws IOException, InvalidFormatException {
        if (!((InputStream)inp).markSupported()) {
            inp = new PushbackInputStream((InputStream)inp, 8);
        }

        if (POIFSFileSystem.hasPOIFSHeader((InputStream)inp)) {
            return new HSSFWorkbook((InputStream)inp);
        } else if (POIXMLDocument.hasOOXMLHeader((InputStream)inp)) {
            return new XSSFWorkbook(OPCPackage.open((InputStream)inp));
        } else {
            throw new IllegalArgumentException("Your InputStream was neither an OLE2 stream, nor an OOXML stream");
        }
    }
	//通过 File 来创建一个 HSSF 模型或 XSSF 模型的对象
    public static Workbook create(File file) throws IOException, InvalidFormatException {
        if (!file.exists()) {
            throw new FileNotFoundException(file.toString());
        } else {
            try {
                NPOIFSFileSystem fs = new NPOIFSFileSystem(file);
                return new HSSFWorkbook(fs.getRoot(), true);
            } catch (OfficeXmlFileException var3) {
                OPCPackage pkg = OPCPackage.open(file);
                return new XSSFWorkbook(pkg);
            }
        }
    }
}
```





## 三、Word

## 四、PPT

## END

# EaseExcel

github:https://github.com/alibaba/easyexcel

文档:https://www.yuque.com/easyexcel/doc/easyexcel

**JAVA解析Excel工具EasyExcel**

Java解析、生成Excel比较有名的框架有Apache poi、jxl。但他们都存在一个严重的问题就是非常的耗内存，poi有一套SAX模式的API可以一定程度的解决一些内存溢出的问题，但POI还是有一些缺陷，比如07版Excel解压缩以及解压后存储都是在内存中完成的，内存消耗依然很大。easyexcel重写了poi对07版Excel的解析，能够原本一个3M的excel用POI sax依然需要100M左右内存降低到几M，并且再大的excel不会出现内存溢出，03版依赖POI的sax模式。在上层做了模型转换的封装，让使用者更加简单方便

**64M内存1分钟内读取75M(46W行25列)的Excel**

当然还有急速模式能更快，但是内存占用会在100M多一点



## 1. 依赖

```xml
		<!--  easyexcel -->
		<!--  点开easyexcel后会发现他也导入了一些包，要注意不要重复导包导致冲突 -->
        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>easyexcel</artifactId>
            <version>2.2.7</version>
        </dependency>

        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <version>1.18.12</version>
        </dependency>


        <!--  日期格式化工具 -->
        <dependency>
            <groupId>joda-time</groupId>
            <artifactId>joda-time</artifactId>
            <version>2.10.1</version>
        </dependency>

        <!-- 单元测试 -->
        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <version>4.12</version>
        </dependency>
```



## 2. 写

- eTest

```java
public class eTest {
    private List<DemoData> data() {
        List<DemoData> list = new ArrayList<DemoData>();
        for (int i = 0; i < 10; i++) {
            DemoData data = new DemoData();
            data.setString("字符串" + i);
            data.setDate(new Date());
            data.setDoubleData(0.56);
            list.add(data);
        }
        return list;
    }

    /**
     * 最简单的写
     * <p>1. 创建excel对应的实体对象 参照{@link DemoData}
     * <p>2. 直接写即可
     */
    @Test
    public void simpleWrite() {
        // 写法1
        String fileName = "D:\\下载\\eTest.xlsx";
        // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        // write(文件名，格式类)
        //sheet(表名称)
        //deWrite(数据)
        EasyExcel.write(fileName, DemoData.class).sheet("模板").doWrite(data());


    }

}
```



- DemoData	模板类

```java
@Data
public class DemoData {
    @ExcelProperty("字符串标题")
    private String string;
    @ExcelProperty("日期标题")
    private Date date;
    @ExcelProperty("数字标题")
    private Double doubleData;
    /**
     * 忽略这个字段
     */
    @ExcelIgnore
    private String ignore;


}
```

## 3. 读

不行学了，以后自己看文档吧，吐了

读：https://www.yuque.com/easyexcel/doc/read

## END