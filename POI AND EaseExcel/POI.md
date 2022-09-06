# [POI使用详解](https://www.cnblogs.com/huajiezh/p/5467821.html)

# Apache POI使用详解

## 1.POI结构与常用类

**(1)POI介绍**

   Apache POI是Apache软件基金会的开源项目，POI提供API给Java程序对Microsoft Office格式档案读和写的功能。 .NET的开发人员则可以利用NPOI (POI for .NET) 来存取 Microsoft Office文档的功能。

**(2)POI结构说明**

 

**包名称说明**

HSSF提供读写Microsoft Excel XLS格式档案的功能。

XSSF提供读写Microsoft Excel OOXML XLSX格式档案的功能。

HWPF提供读写Microsoft Word DOC格式档案的功能。

HSLF提供读写Microsoft PowerPoint格式档案的功能。

HDGF提供读Microsoft Visio格式档案的功能。

HPBF提供读Microsoft Publisher格式档案的功能。

HSMF提供读Microsoft Outlook格式档案的功能。

**(3)POI常用类说明**

**类名**             **说明**

HSSFWorkbook       Excel的文档对象

HSSFSheet   

Excel的表单

HSSFRow         

Excel的行

HSSFCell   

Excel的格子单元

HSSFFont          Excel字体

 

HSSFDataFormat     格子单元的日期格式

HSSFHeader        Excel文档Sheet的页眉

HSSFFooter        Excel文档Sheet的页脚

HSSFCellStyle      格子单元样式

HSSFDateUtil       日期

HSSFPrintSetup     打印

 

HSSFErrorConstants  错误信息表

## 2.Excel的基本操作

**(1)创建Workbook和Sheet**

1. `public class Test00`
2. `{`
3. ` public static void main(String[] args) throws IOException`
4. ` {`
5. ` String filePath="d:\\users\\lizw\\桌面\\POI\\sample.xls";//文件路径`
6. ` HSSFWorkbook workbook = new HSSFWorkbook();//创建Excel文件(Workbook)`
7. ` HSSFSheet sheet = workbook.createSheet();//创建工作表(Sheet)`
8. `sheet = workbook.createSheet("Test");//创建工作表(Sheet)`
9. ` FileOutputStream out = new FileOutputStream(filePath);`
10. `workbook.write(out);//保存Excel文件`
11. `out.close();//关闭文件流`
12. ` System.out.println("OK!");`
13. ` }`
14. `}`

**(2)创建单元格**

1. `HSSFSheet sheet = workbook.createSheet("Test");// 创建工作表(Sheet)`
2. `HSSFRow row = sheet.createRow(0);// 创建行,从0开始`
3. `HSSFCell cell = row.createCell(0);// 创建行的单元格,也是从0开始`
4. `cell.setCellValue("李志伟");// 设置单元格内容`
5. `row.createCell(1).setCellValue(false);// 设置单元格内容,重载`
6. `row.createCell(2).setCellValue(new Date());// 设置单元格内容,重载`
7. `row.createCell(3).setCellValue(12.345);// 设置单元格内容,重载`

![img](image/JZjauuv.png!web)

**(3)创建文档摘要信息**

1. `workbook.createInformationProperties();//创建文档信息`
2. `DocumentSummaryInformation dsi=workbook.getDocumentSummaryInformation();//摘要信息`
3. `dsi.setCategory("类别:Excel文件");//类别`
4. `dsi.setManager("管理者:李志伟");//管理者`
5. `dsi.setCompany("公司:--");//公司`
6. `SummaryInformation si = workbook.getSummaryInformation();//摘要信息`
7. `si.setSubject("主题:--");//主题`
8. `si.setTitle("标题:测试文档");//标题`
9. `si.setAuthor("作者:李志伟");//作者`
10. `si.setComments("备注:POI测试文档");//备注`

![img](image/IRvMfm3.png!web)

**(4)创建批注**

1. `HSSFSheet sheet = workbook.createSheet("Test");// 创建工作表(Sheet)`
2. `HSSFPatriarch patr = sheet.createDrawingPatriarch();`
3. `HSSFClientAnchor anchor = patr.createAnchor(0, 0, 0, 0, 5, 1, 8,3);//创建批注位置`
4. `HSSFComment comment = patr.createCellComment(anchor);//创建批注`
5. `comment.setString(new HSSFRichTextString("这是一个批注段落！"));//设置批注内容`
6. `comment.setAuthor("李志伟");//设置批注作者`
7. `comment.setVisible(true);//设置批注默认显示`
8. `HSSFCell cell = sheet.createRow(2).createCell(1);`
9. `cell.setCellValue("测试");`
10. `cell.setCellComment(comment);//把批注赋值给单元格`

![img](image/BzAzE3.png!web)

   创建批注位置HSSFPatriarch.createAnchor(dx1, dy1, dx2, dy2, col1, row1, col2, row2)方法参数说明：

1. `dx1 第1个单元格中x轴的偏移量`
2. `dy1 第1个单元格中y轴的偏移量`
3. `dx2 第2个单元格中x轴的偏移量`
4. `dy2 第2个单元格中y轴的偏移量`
5. `col1 第1个单元格的列号`
6. `row1 第1个单元格的行号`
7. `col2 第2个单元格的列号`
8. `row2 第2个单元格的行号`

**(5)创建页眉和页脚**

1. `HSSFSheet sheet = workbook.createSheet("Test");// 创建工作表(Sheet)`
2. `HSSFHeader header =sheet.getHeader();//得到页眉`
3. `header.setLeft("页眉左边");`
4. `header.setRight("页眉右边");`
5. `header.setCenter("页眉中间");`
6. `HSSFFooter footer =sheet.getFooter();//得到页脚`
7. `footer.setLeft("页脚左边");`
8. `footer.setRight("页脚右边");`
9. `footer.setCenter("页脚中间");`

![img](image/nmQ3uaR.png!web)

   也可以使用Office自带的标签定义，你可以通过HSSFHeader或HSSFFooter访问到它们，都是静态属性，列表如下：

1. `HSSFHeader.tab &A 表名`
2. `HSSFHeader.file &F 文件名`
3. `HSSFHeader.startBold &B 粗体开始`
4. `HSSFHeader.endBold &B 粗体结束`
5. `HSSFHeader.startUnderline &U 下划线开始`
6. `HSSFHeader.endUnderline &U 下划线结束`
7. `HSSFHeader.startDoubleUnderline &E 双下划线开始`
8. `HSSFHeader.endDoubleUnderline &E 双下划线结束`
9. `HSSFHeader.time &T 时间`
10. `HSSFHeader.date &D 日期`
11. `HSSFHeader.numPages &N 总页面数`
12. `HSSFHeader.page &P 当前页号`

## 3.Excel的单元格操作

**(1)设置格式**

1. `HSSFSheet sheet = workbook.createSheet("Test");// 创建工作表(Sheet)`
2. `HSSFRow row=sheet.createRow(0);`
3. `//设置日期格式--使用Excel内嵌的格式`
4. `HSSFCell cell=row.createCell(0);`
5. `cell.setCellValue(new Date());`
6. `HSSFCellStyle style=workbook.createCellStyle();`
7. `style.setDataFormat(HSSFDataFormat.getBuiltinFormat("m/d/yy h:mm"));`
8. `cell.setCellStyle(style);`
9. `//设置保留2位小数--使用Excel内嵌的格式`
10. `cell=row.createCell(1);`
11. `cell.setCellValue(12.3456789);`
12. `style=workbook.createCellStyle();`
13. `style.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00"));`
14. `cell.setCellStyle(style);`
15. `//设置货币格式--使用自定义的格式`
16. `cell=row.createCell(2);`
17. `cell.setCellValue(12345.6789);`
18. `style=workbook.createCellStyle();`
19. `style.setDataFormat(workbook.createDataFormat().getFormat("￥#,##0"));`
20. `cell.setCellStyle(style);`
21. `//设置百分比格式--使用自定义的格式`
22. `cell=row.createCell(3);`
23. `cell.setCellValue(0.123456789);`
24. `style=workbook.createCellStyle();`
25. `style.setDataFormat(workbook.createDataFormat().getFormat("0.00%"));`
26. `cell.setCellStyle(style);`
27. `//设置中文大写格式--使用自定义的格式`
28. `cell=row.createCell(4);`
29. `cell.setCellValue(12345);`
30. `style=workbook.createCellStyle();`
31. `style.setDataFormat(workbook.createDataFormat().getFormat("[DbNum2][$-804]0"));`
32. `cell.setCellStyle(style);`
33. `//设置科学计数法格式--使用自定义的格式`
34. `cell=row.createCell(5);`
35. `cell.setCellValue(12345);`
36. `style=workbook.createCellStyle();`
37. `style.setDataFormat(workbook.createDataFormat().getFormat("0.00E+00"));`
38. `cell.setCellStyle(style);`

![img](image/jIZ3Qbi.png!web)

   **HSSFDataFormat.getFormat和HSSFDataFormat.getBuiltinFormat的区别：** 当使用Excel内嵌的（或者说预定义）的格式时，直接用HSSFDataFormat.getBuiltinFormat静态方法即可。当使用自己定义的格式时，必须先调用HSSFWorkbook.createDataFormat()，因为这时在底层会先找有没有匹配的内嵌FormatRecord，如果没有就会新建一个FormatRecord，所以必须先调用这个方法，然后你就可以用获得的HSSFDataFormat实例的getFormat方法了，当然相对而言这种方式比较麻烦，所以内嵌格式还是用HSSFDataFormat.getBuiltinFormat静态方法更加直接一些。

**(2)合并单元格**

1. `HSSFSheet sheet = workbook.createSheet("Test");// 创建工作表(Sheet)`
2. `HSSFRow row=sheet.createRow(0);`
3. `//合并列`
4. `HSSFCell cell=row.createCell(0);`
5. `cell.setCellValue("合并列");`
6. `CellRangeAddress region=new CellRangeAddress(0, 0, 0, 5);`
7. `sheet.addMergedRegion(region);`
8. `//合并行`
9. `cell=row.createCell(6);`
10. `cell.setCellValue("合并行");`
11. `region=new CellRangeAddress(0, 5, 6, 6);`
12. `sheet.addMergedRegion(region);`

![img](image/7nquMbi.png!web)

   CellRangeAddress对象其实就是表示一个区域，其构造方法如下：CellRangeAddress(firstRow, lastRow, firstCol, lastCol)，参数的说明：

1. `firstRow 区域中第一个单元格的行号`
2. `lastRow 区域中最后一个单元格的行号`
3. `firstCol 区域中第一个单元格的列号`
4. `lastCol 区域中最后一个单元格的列号`

   **提示：** 即使你没有用CreateRow和CreateCell创建过行或单元格，也完全可以直接创建区域然后把这一区域合并，Excel的区域合并信息是单独存储的，和RowRecord、ColumnInfoRecord不存在直接关系。

**(3)单元格对齐**

1. `HSSFCell cell=row.createCell(0);`
2. `cell.setCellValue("单元格对齐");`
3. `HSSFCellStyle style=workbook.createCellStyle();`
4. `style.setAlignment(HSSFCellStyle.ALIGN_CENTER);//水平居中`
5. `style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直居中`
6. `style.setWrapText(true);//自动换行`
7. `style.setIndention((short)5);//缩进`
8. `style.setRotation((short)60);//文本旋转，这里的取值是从-90到90，而不是0-180度。`
9. `cell.setCellStyle(style);`

![img](image/iYbuIr6.png!web)

   水平对齐相关参数

1. `如果是左侧对齐就是 HSSFCellStyle.ALIGN_FILL;`
2. `如果是居中对齐就是 HSSFCellStyle.ALIGN_CENTER;`
3. `如果是右侧对齐就是 HSSFCellStyle.ALIGN_RIGHT;`
4. `如果是跨列举中就是 HSSFCellStyle.ALIGN_CENTER_SELECTION;`
5. `如果是两端对齐就是 HSSFCellStyle.ALIGN_JUSTIFY;`
6. `如果是填充就是 HSSFCellStyle.ALIGN_FILL;`

   垂直对齐相关参数

1. `如果是靠上就是 HSSFCellStyle.VERTICAL_TOP;`
2. `如果是居中就是 HSSFCellStyle.VERTICAL_CENTER;`
3. `如果是靠下就是 HSSFCellStyle.VERTICAL_BOTTOM;`
4. `如果是两端对齐就是 HSSFCellStyle.VERTICAL_JUSTIFY;`

**(4)使用边框**

   边框和其他单元格设置一样也是调用CellStyle接口，CellStyle有2种和边框相关的属性，分别是:

| 边框相关属性      | 说明     | 范例                                |
| ----------------- | -------- | ----------------------------------- |
| Border+ 方向      | 边框类型 | BorderLeft, BorderRight 等          |
| 方向 +BorderColor | 边框颜色 | TopBorderColor,BottomBorderColor 等 |

1. ```java
    HSSFCell cell=row.createCell(1);
   cell.setCellValue("设置边框");
    HSSFCellStyle style=workbook.createCellStyle();
   style.setBorderTop(HSSFCellStyle.BORDER_DOTTED);//上边框
   style.setBorderBottom(HSSFCellStyle.BORDER_THICK);//下边框
    style.setBorderLeft(HSSFCellStyle.BORDER_DOUBLE);//左边框
    style.setBorderRight(HSSFCellStyle.BORDER_SLANTED_DASH_DOT);//右边框
    style.setTopBorderColor(HSSFColor.RED.index);//上边框颜色
   style.setBottomBorderColor(HSSFColor.BLUE.index);//下边框颜色
    style.setLeftBorderColor(HSSFColor.GREEN.index);//左边框颜色
    style.setRightBorderColor(HSSFColor.PINK.index);//右边框颜色
    cell.setCellStyle(style);
   ```

   

![img](image/uYnyYrq.png!web)

   其中边框类型分为以下几种：

| 边框范例图                        | 对应的静态值                              |
| --------------------------------- | ----------------------------------------- |
| ![img](image/041657095397412.png) | HSSFCellStyle. BORDER_DOTTED              |
| ![img](image/041657101643055.png) | HSSFCellStyle. BORDER_HAIR                |
| ![img](image/041657109923870.png) | HSSFCellStyle. BORDER_DASH_DOT_DOT        |
| ![img](image/041657115559297.png) | HSSFCellStyle. BORDER_DASH_DOT            |
| ![img](image/041657121171427.png) | HSSFCellStyle. BORDER_DASHED              |
| ![img](image/041657130244283.png) | HSSFCellStyle. BORDER_THIN                |
| ![img](image/041657136647452.png) | HSSFCellStyle. BORDER_MEDIUM_DASH_DOT_DOT |
| ![img](image/041657144453282.png) | HSSFCellStyle. BORDER_SLANTED_DASH_DOT    |
| ![img](image/041657149455195.png) | HSSFCellStyle. BORDER_MEDIUM_DASH_DOT     |
| ![img](image/041657154457109.png) | HSSFCellStyle. BORDER_MEDIUM_DASHED       |
| ![img](image/041657163995951.png) | HSSFCellStyle. BORDER_MEDIUM              |
| ![img](image/041657169453851.png) | HSSFCellStyle. BORDER_THICK               |
| ![img](image/041657180557178.png) | HSSFCellStyle. BORDER_DOUBLE              |

**(5)设置字体**

1. ```java
   HSSFCell cell = row.createCell(1);
   cell.setCellValue("设置字体");
   HSSFCellStyle style = workbook.createCellStyle();
   HSSFFont font = workbook.createFont();
   font.setFontName("华文行楷");//设置字体名称
   font.setFontHeightInPoints((short)28);//设置字号
   font.setColor(HSSFColor.RED.index);//设置字体颜色
   font.setUnderline(FontFormatting.U_SINGLE);//设置下划线
   font.setTypeOffset(FontFormatting.SS_SUPER);//设置上标下标
   font.setStrikeout(true);//设置删除线
   style.setFont(font);
   cell.setCellStyle(style);
   ```

   

![img](image/F7VRzie.png!web)

下划线选项值：

单下划线 FontFormatting.U_SINGLE

双下划线 FontFormatting.U_DOUBLE

会计用单下划线 FontFormatting.U_SINGLE_ACCOUNTING

会计用双下划线 FontFormatting.U_DOUBLE_ACCOUNTING

无下划线 FontFormatting.U_NONE

   上标下标选项值：

上标 FontFormatting.SS_SUPER

下标 FontFormatting.SS_SUB

普通，默认值 FontFormatting.SS_NONE

**(6)背景和纹理**

1. `HSSFCellStyle style = workbook.createCellStyle();`
2. `style.setFillForegroundColor(HSSFColor.GREEN.index);//设置图案颜色`
3. `style.setFillBackgroundColor(HSSFColor.RED.index);//设置图案背景色`
4. `style.setFillPattern(HSSFCellStyle.SQUARES);//设置图案样式`
5. `cell.setCellStyle(style);`

![img](image/U3IVVb.png!web)

   图案样式及其对应的值：

| 图案样式                          | 常量                               |
| --------------------------------- | ---------------------------------- |
| ![img](image/041657239309416.png) | HSSFCellStyle. NO_FILL             |
| ![img](image/041657246496031.png) | HSSFCellStyle. ALT_BARS            |
| ![img](image/041657255395658.png) | HSSFCellStyle. FINE_DOTS           |
| ![img](image/041657265865471.png) | HSSFCellStyle. SPARSE_DOTS         |
| ![img](image/041657273527071.png) | HSSFCellStyle. LESS_DOTS           |
| ![img](image/041657283209142.png) | HSSFCellStyle. LEAST_DOTS          |
| ![img](image/041657289454785.png) | HSSFCellStyle. BRICKS              |
| ![img](image/041657296333941.png) | HSSFCellStyle. BIG_SPOTS           |
| ![img](image/041657300702342.png) | HSSFCellStyle. THICK_FORWARD_DIAG  |
| ![img](image/041657309309211.png) | HSSFCellStyle. THICK_BACKWARD_DIAG |
| ![img](image/041657313999368.png) | HSSFCellStyle. THICK_VERT_BANDS    |
| ![img](image/041657319923255.png) | HSSFCellStyle. THICK_HORZ_BANDS    |
| ![img](image/041657327114167.png) | HSSFCellStyle. THIN_HORZ_BANDS     |
| ![img](image/041657332279310.png) | HSSFCellStyle. THIN_VERT_BANDS     |
| ![img](image/041657348053794.png) | HSSFCellStyle. THIN_BACKWARD_DIAG  |
| ![img](image/041657355086179.png) | HSSFCellStyle. THIN_FORWARD_DIAG   |
| ![img](image/041657365555993.png) | HSSFCellStyle. SQUARES             |
| ![img](image/041657374451322.png) | HSSFCellStyle. DIAMONDS            |

**(7)设置宽度和高度**

1. `HSSFSheet sheet = workbook.createSheet("Test");// 创建工作表(Sheet)`
2. `HSSFRow row = sheet.createRow(1);`
3. `HSSFCell cell = row.createCell(1);`
4. `cell.setCellValue("123456789012345678901234567890");`
5. `sheet.setColumnWidth(1, 31 * 256);//设置第一列的宽度是31个字符宽度`
6. `row.setHeightInPoints(50);//设置行的高度是50个点`

![img](image/eamiEff.png!web)

   这里你会发现一个有趣的现象，setColumnWidth的第二个参数要乘以256，这是怎么回事呢？其实，这个参数的单位是1/256个字符宽度，也就是说，这里是把B列的宽度设置为了31个字符。

   设置行高使用HSSFRow对象的setHeight和setHeightInPoints方法，这两个方法的区别在于setHeightInPoints的单位是点，而setHeight的单位是1/20个点，所以setHeight的值永远是setHeightInPoints的20倍。

   你也可以使用HSSFSheet.setDefaultColumnWidth、HSSFSheet.setDefaultRowHeight和HSSFSheet.setDefaultRowHeightInPoints方法设置默认的列宽或行高。

**(8)判断单元格是否为日期**

   判断单元格是否为日期类型，使用DateUtil.isCellDateFormatted(cell)方法，例如：

1. `HSSFCell cell = row.createCell(1);`
2. `cell.setCellValue(new Date());//设置日期数据`
3. `System.out.println(DateUtil.isCellDateFormatted(cell));//输出：false`
4. `HSSFCellStyle style =workbook.createCellStyle();`
5. `style.setDataFormat(HSSFDataFormat.getBuiltinFormat("m/d/yy h:mm"));`
6. `cell.setCellStyle(style);//设置日期样式`
7. `System.out.println(DateUtil.isCellDateFormatted(cell));//输出：true`

## 4.使用Excel公式

**(1)基本计算**

1. `HSSFSheet sheet = workbook.createSheet("Test");// 创建工作表(Sheet)`
2. `HSSFRow row = sheet.createRow(0);`
3. `HSSFCell cell = row.createCell(0);`
4. `cell.setCellFormula("2+3*4");//设置公式`
5. `cell = row.createCell(1);`
6. `cell.setCellValue(10);`
7. `cell = row.createCell(2);`
8. `cell.setCellFormula("A1*B1");//设置公式`

![img](image/YZR3Urv.png!web)

**(2)SUM函数**

1. `HSSFSheet sheet = workbook.createSheet("Test");// 创建工作表(Sheet)`
2. `HSSFRow row = sheet.createRow(0);`
3. `row.createCell(0).setCellValue(1);`
4. `row.createCell(1).setCellValue(2);`
5. `row.createCell(2).setCellValue(3);`
6. `row.createCell(3).setCellValue(4);`
7. `row.createCell(4).setCellValue(5);`
8. `row = sheet.createRow(1);`
9. `row.createCell(0).setCellFormula("sum(A1,C1)");//等价于"A1+C1"`
10. `row.createCell(1).setCellFormula("sum(B1:D1)");//等价于"B1+C1+D1"`

![img](image/BRZ7Ff.png!web)

**(3)日期函数**

1. `HSSFSheet sheet = workbook.createSheet("Test");// 创建工作表(Sheet)`
2. `HSSFCellStyle style=workbook.createCellStyle();`
3. `style.setDataFormat(workbook.createDataFormat().getFormat("yyyy-mm-dd"));`
4. `HSSFRow row = sheet.createRow(0);`
5. `Calendar date=Calendar.getInstance();//日历对象`
6. `HSSFCell cell=row.createCell(0);`
7. `date.set(2011,2, 7);`
8. `cell.setCellValue(date.getTime());`
9. `cell.setCellStyle(style);//第一个单元格开始时间设置完成`
10. `cell=row.createCell(1);`
11. `date.set(2014,4, 25);`
12. `cell.setCellValue(date.getTime());`
13. `cell.setCellStyle(style);//第一个单元格结束时间设置完成`
14. `cell=row.createCell(3);`
15. `cell.setCellFormula("CONCATENATE(DATEDIF(A1,B1,\"y\"),\"年\")");`
16. `cell=row.createCell(4);`
17. `cell.setCellFormula("CONCATENATE(DATEDIF(A1,B1,\"m\"),\"月\")");`
18. `cell=row.createCell(5);`
19. `cell.setCellFormula("CONCATENATE(DATEDIF(A1,B1,\"d\"),\"日\")");`

![img](image/6ZjuQj.png!web)

  

以上代码中的公式说明：

   DATEDIF(A1,B1,\"y\") ：取得 A1 单元格的日期与 B1 单元格的日期的时间间隔。 ( “ y ” : 表示以年为单位 , ” m ”表示以月为单位 ; ” d ”表示以天为单位 ) 。

 

​      CONCATENATE( str1,str2, … ) ：连接字符串。

​      更多 Excel 的日期函数可参考：http://tonyqus.sinaapp.com/archives/286

**(4)字符串相关函数**

1. `HSSFSheet sheet = workbook.createSheet("Test");// 创建工作表(Sheet)`
2. `HSSFRow row = sheet.createRow(0);`
3. `row.createCell(0).setCellValue("abcdefg");`
4. `row.createCell(1).setCellValue("aa bb cc dd ee fF GG");`
5. `row.createCell(3).setCellFormula("UPPER(A1)");`
6. `row.createCell(4).setCellFormula("PROPER(B1)");`

![img](image/muYB73n.png!web)

  

以上代码中的公式说明：

   UPPER( String ) ：将文本转换成大写形式。

 

​      PROPER( String ) ：将文字串的首字母及任何非字母字符之后的首字母转换成大写。将其余的字母转换成小写。

​      更多 Excel 的字符串函数可参考：http://tonyqus.sinaapp.com/archives/289

**(5)IF函数**

1. `HSSFSheet sheet = workbook.createSheet("Test");// 创建工作表(Sheet)`
2. `HSSFRow row = sheet.createRow(0);`
3. `row.createCell(0).setCellValue(12);`
4. `row.createCell(1).setCellValue(23);`
5. `row.createCell(3).setCellFormula("IF(A1>B1,\"A1大于B1\",\"A1小于等于B1\")");`

![img](image/eeQbQv.png!web)

   以上代码中的公式说明：

​      IF(logical_test,value_if_true,value_if_false)用来用作逻辑判断。其中Logical_test表示计算结果为 TRUE 或 FALSE 的任意值或表达式 ; value_if_true表示当表达式Logical_test的值为TRUE时的返回值；value_if_false表示当表达式Logical_test的值为FALSE时的返回值。

**(6)CountIf和SumIf函数**

1. `HSSFSheet sheet = workbook.createSheet("Test");// 创建工作表(Sheet)`
2. `HSSFRow row = sheet.createRow(0);`
3. `row.createCell(0).setCellValue(57);`
4. `row.createCell(1).setCellValue(89);`
5. `row.createCell(2).setCellValue(56);`
6. `row.createCell(3).setCellValue(67);`
7. `row.createCell(4).setCellValue(60);`
8. `row.createCell(5).setCellValue(73);`
9. `row.createCell(7).setCellFormula("COUNTIF(A1:F1,\">=60\")");`
10. `row.createCell(8).setCellFormula("SUMIF(A1:F1,\">=60\",A1:F1)");`

![img](image/AzM7FfZ.png!web)

   以上代码中的公式说明：

​      COUNTIF(range,criteria)：满足某条件的计数的函数。参数range：需要进行读数的计数；参数criteria：条件表达式，只有当满足此条件时才进行计数。

​      SumIF(criteria_range, criteria,sum_range)：用于统计某区域内满足某条件的值的求和。参数criteria_range：条件测试区域，第二个参数Criteria中的条件将与此区域中的值进行比较；参数criteria：条件测试值，满足条件的对应的sum_range项将进行求和计算；参数sum_range：汇总数据所在区域，求和时会排除掉不满足Criteria条件的对应的项。

**(7)Lookup函数**

1. `HSSFSheet sheet = workbook.createSheet("Test");// 创建工作表(Sheet)`
2. `HSSFRow row = sheet.createRow(0);`
3. `row.createCell(0).setCellValue(0);`
4. `row.createCell(1).setCellValue(59);`
5. `row.createCell(2).setCellValue("不及格");`
6. `row = sheet.createRow(1);`
7. `row.createCell(0).setCellValue(60);`
8. `row.createCell(1).setCellValue(69);`
9. `row.createCell(2).setCellValue("及格");`
10. `row = sheet.createRow(2);`
11. `row.createCell(0).setCellValue(70);`
12. `row.createCell(1).setCellValue(79);`
13. `row.createCell(2).setCellValue("良好");`
14. `row = sheet.createRow(3);`
15. `row.createCell(0).setCellValue(80);`
16. `row.createCell(1).setCellValue(100);`
17. `row.createCell(2).setCellValue("优秀");`
18. `row = sheet.createRow(4);`
19. `row.createCell(0).setCellValue(75);`
20. `row.createCell(1).setCellFormula("LOOKUP(A5,$A$1:$A$4,$C$1:$C$4)");`
21. `row.createCell(2).setCellFormula("VLOOKUP(A5,$A$1:$C$4,3,true)");`

![img](image/e6Zvyy.png!web)

  

以上代码中的公式说明：

   LOOKUP(lookup_value,lookup_vector,result_vector) ，第一个参数：需要查找的内容，本例中指向 A5 单元格，也就是 75 ；第二个参数：比较对象区域，本例中的成绩需要与 $A$1:$A$4 中的各单元格中的值进行比较；第三个参数：查找结果区域，如果匹配到会将此区域中对应的数据返回。如本例中返回$C$1:$C$4 中对应的值。

 

 

可能有人会问，字典中没有 75 对应的成绩啊，那么 Excel 中怎么匹配的呢？答案是模糊匹配，并且 LOOKUP 函数只支持模糊匹配。 Excel 会在 $A$1:$A$4 中找小于 75 的最大值，也就是 A3 对应的 70 ，然后将对应的 $C$1:$C$4 区域中的 C3 中的值返回，这就是最终结果“良好”的由来。

   VLOOKUP(lookup_value,lookup_area,result_col,is_fuzzy ) ，第一个参数：需要查找的内容，这里是 A5 单元格；第二个参数：需要比较的表，这里是 $A$1:$C$4 ，注意 VLOOKUP 匹配时只与表中的第一列进行匹配。第三个参数：匹配结果对应的列序号。这里要对应的是成绩列，所以为 3 。第四个参数：指明是否模糊匹配。例子中的 TRUE 表示模糊匹配，与上例中一样。匹配到的是第三行。如果将此参数改为 FALSE ，因为在表中的第 1 列中找不到 75 ，所以会报“#N/A ”的计算错误。

 

另外，还有与 VLOKUP 类似的 HLOOKUP 。不同的是 VLOOKUP 用于在表格或数值数组的首列查找指定的数值，并由此返回表格或数组当前行中指定列处的数值。而HLOOKUP 用于在表格或数值数组的首行查找指定的数值，并由此返回表格或数组当前列中指定行处的数值。读者可以自已去尝试。

**(8)随机数函数**

1. `HSSFSheet sheet = workbook.createSheet("Test");// 创建工作表(Sheet)`
2. `HSSFRow row = sheet.createRow(0);`
3. `row.createCell(0).setCellFormula("RAND()");//取0-1之间的随机数`
4. `row.createCell(1).setCellFormula("int(RAND()*100)");//取0-100之间的随机整数`
5. `row.createCell(2).setCellFormula("rand()*10+10");//取10-20之间的随机实数`
6. `row.createCell(3).setCellFormula("CHAR(INT(RAND()*26)+97)");//随机小写字母`
7. `row.createCell(4).setCellFormula("CHAR(INT(RAND()*26)+65)");//随机大写字母`
8. `//随机大小写字母`
9. `row.createCell(5).setCellFormula("CHAR(INT(RAND()*26)+if(INT(RAND()*2)=0,97,65))");`

![img](image/Ef6zaen.png!web)

   以上代码中的公式说明：

​      上面几例中除了用到RAND函数以外，还用到了CHAR函数用来将ASCII码换为字母，INT函数用来取整。值得注意的是INT函数不会四舍五入，无论小数点后是多少都会被舍去。

**(9)获得公式的返回值**

1. `HSSFSheet sheet = workbook.createSheet("Test");// 创建工作表(Sheet)`
2. `HSSFRow row = sheet.createRow(0);`
3. `row.createCell(0).setCellValue(7);//A1`
4. `row.createCell(1).setCellValue(8);//B1`
5. `HSSFCell cell=row.createCell(2);`
6. `cell.setCellFormula("A1*B1+14");`
7. `HSSFFormulaEvaluator e = newHSSFFormulaEvaluator(workbook);`
8. `cell = e.evaluateInCell(cell);//若Excel文件不是POI创建的，则不必调用此方法`
9. `System.out.println("公式计算结果："+cell.getNumericCellValue());`

![img](image/7RzeAj.png!web)

## 5.使用图形

**(1)画线**

1. `HSSFSheet sheet = workbook.createSheet("Test");// 创建工作表(Sheet)`
2. `HSSFPatriarch patriarch=sheet.createDrawingPatriarch();`
3. `HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0, 0, 0,(short)1, 0,(short)4, 4);`
4. `HSSFSimpleShape line = patriarch.createSimpleShape(anchor);`
5. `line.setShapeType(HSSFSimpleShape.OBJECT_TYPE_LINE);//设置图形类型`
6. `line.setLineStyle(HSSFShape.LINESTYLE_SOLID);//设置图形样式`
7. `line.setLineWidth(6350);//在POI中线的宽度12700表示1pt,所以这里是0.5pt粗的线条。`

![img](image/QJneuq2.png!web)

   通常，利用POI画图主要有以下几个步骤：

​      \1. 创建一个Patriarch（注意，一个sheet中通常只创建一个Patriarch对象）；

​      \2. 创建一个Anchor，以确定图形的位置；

​      \3. 调用Patriarch创建图形；

​      \4. 设置图形类型(直线，矩形，圆形等)及样式（颜色，粗细等）。

   关于HSSFClientAnchor(dx1,dy1,dx2,dy2,col1,row1,col2,row2)的参数，有必要在这里说明一下：

​      dx1：起始单元格的x偏移量，如例子中的0表示直线起始位置距B1单元格左侧的距离；

​      dy1：起始单元格的y偏移量，如例子中的0表示直线起始位置距B1单元格上侧的距离；

​      dx2：终止单元格的x偏移量，如例子中的0表示直线起始位置距E5单元格左侧的距离；

​      dy2：终止单元格的y偏移量，如例子中的0表示直线起始位置距E5单元格上侧的距离；

​      col1：起始单元格列序号，从0开始计算；

​      row1：起始单元格行序号，从0开始计算，如例子中col1=1,row1=0就表示起始单元格为B1；

​      col2：终止单元格列序号，从0开始计算；

​      row2：终止单元格行序号，从0开始计算，如例子中col2=4,row2=4就表示起始单元格为E5；

   最后，关于LineStyle属性，有如下一些可选值，对应的效果分别如图所示：

![img](image/fuau6rB.png!web)

**(2)画矩形**

1. `HSSFSheet sheet = workbook.createSheet("Test");// 创建工作表(Sheet)`
2. `HSSFPatriarch patriarch=sheet.createDrawingPatriarch();`
3. `HSSFClientAnchor anchor = new HSSFClientAnchor(255,122,255, 122, (short)1, 0,(short)4, 3);`
4. `HSSFSimpleShape rec = patriarch.createSimpleShape(anchor);`
5. `rec.setShapeType(HSSFSimpleShape.OBJECT_TYPE_RECTANGLE);`
6. `rec.setLineStyle(HSSFShape.LINESTYLE_DASHGEL);//设置边框样式`
7. `rec.setFillColor(255, 0, 0);//设置填充色`
8. `rec.setLineWidth(25400);//设置边框宽度`
9. `rec.setLineStyleColor(0, 0, 255);//设置边框颜色`

![img](image/FVfAnu.png!web)

**(3)画圆形**

   更改上例的代码如下：

​     rec.setShapeType(HSSFSimpleShape.OBJECT_TYPE_OVAL);//设置图片类型

![img](image/Y3m22aa.png!web)

**(4)画Grid**

   在POI中，本身没有画Grid(网格)的方法。但我们知道Grid其实就是由横线和竖线构成的，所在我们可以通过画线的方式来模拟画Grid。代码如下：

1. `HSSFSheet sheet = workbook.createSheet("Test");// 创建工作表(Sheet)`
2. `HSSFRow row = sheet.createRow(2);`
3. `row.createCell(1);`
4. `row.setHeightInPoints(240);`
5. `sheet.setColumnWidth(2, 9000);`
6. `int linesCount = 20;`
7. `HSSFPatriarch patriarch = sheet.createDrawingPatriarch();`
8. `//因为HSSFClientAnchor中dx只能在0-1023之间,dy只能在0-255之间，这里采用比例的方式`
9. `double xRatio = 1023.0 / (linesCount * 10);`
10. `double yRatio = 255.0 / (linesCount * 10);`
11. `// 画竖线`
12. `int x1 = 0;`
13. `int y1 = 0;`
14. `int x2 = 0;`
15. `int y2 = 200;`
16. `for (int i = 0; i < linesCount; i++)`
17. `{`
18. ` HSSFClientAnchor a2 = new HSSFClientAnchor();`
19. `a2.setAnchor((short) 2, 2, (int) (x1 * xRatio),`
20. ` (int) (y1 * yRatio), (short) 2, 2, (int) (x2 * xRatio),`
21. ` (int) (y2 * yRatio));`
22. ` HSSFSimpleShape shape2 = patriarch.createSimpleShape(a2);`
23. `shape2.setShapeType(HSSFSimpleShape.OBJECT_TYPE_LINE);`
24. `x1 += 10;`
25. `x2 += 10;`
26. `}`
27. `// 画横线`
28. `x1 = 0;`
29. `y1 = 0;`
30. `x2 = 200;`
31. `y2 = 0;`
32. `for (int i = 0; i < linesCount; i++)`
33. `{`
34. ` HSSFClientAnchor a2 = new HSSFClientAnchor();`
35. `a2.setAnchor((short) 2, 2, (int) (x1 * xRatio),`
36. ` (int) (y1 * yRatio), (short) 2, 2, (int) (x2 * xRatio),`
37. ` (int) (y2 * yRatio));`
38. ` HSSFSimpleShape shape2 = patriarch.createSimpleShape(a2);`
39. `shape2.setShapeType(HSSFSimpleShape.OBJECT_TYPE_LINE);`
40. `y1 += 10;`
41. `y2 += 10;`
42. `}`

![img](image/7fiU3m.png!web) 

**(5)插入图片**

1. `HSSFSheet sheet = workbook.createSheet("Test");// 创建工作表(Sheet)`
2. `FileInputStream stream=newFileInputStream("d:\\POI\\Apache.gif");`
3. `byte[] bytes=new byte[(int)stream.getChannel().size()];`
4. `stream.read(bytes);//读取图片到二进制数组`
5. `int pictureIdx = workbook.addPicture(bytes,HSSFWorkbook.PICTURE_TYPE_JPEG);`
6. `HSSFPatriarch patriarch = sheet.createDrawingPatriarch();`
7. `HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0, 0, 0,(short)0, 0, (short)5, 5);`
8. `HSSFPicture pict = patriarch.createPicture(anchor,pictureIdx);`
9. `//pict.resize();//自动调节图片大小,图片位置信息可能丢失`

![img](image/naAvmu.png!web)

**(6)从Excel文件提取图片**

1. `InputStream inp = new FileInputStream(filePath);`
2. `HSSFWorkbook workbook = new HSSFWorkbook(inp);//读取现有的Excel文件`
3. `List<HSSFPictureData> pictures = workbook.getAllPictures();`
4. `for(int i=0;i<pictures.size();i++)`
5. `{`
6. ` HSSFPictureData pic=pictures.get(i);`
7. ` String ext = pic.suggestFileExtension();`
8. ` if (ext.equals("png"))//判断文件格式`
9. ` {`
10. ` FileOutputStream png=newFileOutputStream("d:\\POI\\Apache.png");`
11. `png.write(pic.getData());`
12. `png.close();//保存图片`
13. ` }`
14. `}`

## 6.Excel表操作

**(1)设置默认工作表**

1. `HSSFWorkbook workbook = new HSSFWorkbook();// 创建Excel文件(Workbook)`
2. `workbook.createSheet("Test0");// 创建工作表(Sheet)`
3. `workbook.createSheet("Test1");// 创建工作表(Sheet)`
4. `workbook.createSheet("Test2");// 创建工作表(Sheet)`
5. `workbook.createSheet("Test3");// 创建工作表(Sheet)`
6. `workbook.setActiveSheet(2);//设置默认工作表`

**(2)重命名工作表**

1. `HSSFWorkbook workbook = new HSSFWorkbook();// 创建Excel文件(Workbook)`
2. `workbook.createSheet("Test0");// 创建工作表(Sheet)`
3. `workbook.createSheet("Test1");// 创建工作表(Sheet)`
4. `workbook.createSheet("Test2");// 创建工作表(Sheet)`
5. `workbook.createSheet("Test3");// 创建工作表(Sheet)`
6. `workbook.setSheetName(2, "1234");//重命名工作表`

**(3)调整表单显示比例**

1. `HSSFWorkbook workbook = new HSSFWorkbook();// 创建Excel文件(Workbook)`
2. `HSSFSheet sheet1= workbook.createSheet("Test0");// 创建工作表(Sheet)`
3. `HSSFSheet sheet2=workbook.createSheet("Test1");// 创建工作表(Sheet)`
4. `HSSFSheet sheet3=workbook.createSheet("Test2");// 创建工作表(Sheet)`
5. `sheet1.setZoom(1,2);//50%显示比例`
6. `sheet2.setZoom(2,1);//200%显示比例`
7. `sheet3.setZoom(1,10);//10%显示比例`

![img](image/aQfQ7fZ.png!web)![img](image/73Mjeue.png!web)![img](image/2EvEZr.png!web)

**(4)显示/隐藏网格线**

1. `HSSFWorkbook workbook = new HSSFWorkbook();// 创建Excel文件(Workbook)`
2. `HSSFSheet sheet1= workbook.createSheet("Test0");// 创建工作表(Sheet)`
3. `HSSFSheet sheet2=workbook.createSheet("Test1");// 创建工作表(Sheet)`
4. `sheet1.setDisplayGridlines(false);//隐藏Excel网格线,默认值为true`
5. `sheet2.setGridsPrinted(true);//打印时显示网格线,默认值为false`

![img](image/3AFvyyB.png!web)

**(5)遍历Sheet**

1. `String filePath = "d:\\users\\lizw\\桌面\\POI\\sample.xls";`
2. `FileInputStream stream = new FileInputStream(filePath);`
3. `HSSFWorkbook workbook = new HSSFWorkbook(stream);//读取现有的Excel`
4. `HSSFSheet sheet= workbook.getSheet("Test0");//得到指定名称的Sheet`
5. `for (Row row : sheet)`
6. `{`
7. ` for (Cell cell : row)`
8. ` {`
9. ` System.out.print(cell + "\t");`
10. ` }`
11. ` System.out.println();`
12. `}`

![img](image/IfQJ7f.png!web)

![img](image/uEJ7Nfv.png!web)

## 7.Excel行列操作

**(1)组合行、列**

1. `HSSFSheet sheet= workbook.createSheet("Test0");// 创建工作表(Sheet)`
2. `sheet.groupRow(1, 3);//组合行`
3. `sheet.groupRow(2, 4);//组合行`
4. `sheet.groupColumn(2, 7);//组合列`

![img](image/muQ3Ev.png!web)

   这里简单的介绍一下什么叫做组合：组合分为行组合和列组合，所谓行组合，就是让n行组合成一个集合，能够进行展开和合拢操作。

   使用POI也可以取消组合，例如：sheet.ungroupColumn(1, 3);//取消列组合

**(2)锁定列**

   在Excel中，有时可能会出现列数太多或是行数太多的情况，这时可以通过锁定列来冻结部分列，不随滚动条滑动，方便查看。

1. `HSSFSheet sheet= workbook.createSheet("Test0");// 创建工作表(Sheet)`
2. `sheet.createFreezePane(2, 3, 15, 25);//冻结行列`

![img](image/2QvMzu.png!web)

   下面对CreateFreezePane的参数作一下说明：

​      第一个参数表示要冻结的列数；

​      第二个参数表示要冻结的行数，这里只冻结列所以为0；

​      第三个参数表示右边区域可见的首列序号，从1开始计算；

​      第四个参数表示下边区域可见的首行序号，也是从1开始计算，这里是冻结列，所以为0；

**(3)上下移动行**

1. `FileInputStream stream = new FileInputStream(filePath);`
2. `HSSFWorkbook workbook = new HSSFWorkbook(stream);`
3. `HSSFSheet sheet = workbook.getSheet("Test0");`
4. `sheet.shiftRows(2, 4, 2);//把第3行到第4行向下移动两行`

![img](image/2MFVBz.png!web)![img](image/emEZBnQ.png!web)

   HSSFSheet.shiftRows(startRow, endRow, n)参数说明

​      startRow：需要移动的起始行；

​      endRow：需要移动的结束行；

​      n：移动的位置，正数表示向下移动，负数表示向上移动；

 

## 8.Excel的其他功能

**(1)设置密码**

1. `HSSFSheet sheet= workbook.createSheet("Test0");// 创建工作表(Sheet)`
2. `HSSFRow row=sheet.createRow(1);`
3. `HSSFCell cell=row.createCell(1);`
4. `cell.setCellValue("已锁定");`
5. `HSSFCellStyle locked = workbook.createCellStyle();`
6. `locked.setLocked(true);//设置锁定`
7. `cell.setCellStyle(locked);`
8. `cell=row.createCell(2);`
9. `cell.setCellValue("未锁定");`
10. `HSSFCellStyle unlocked = workbook.createCellStyle();`
11. `unlocked.setLocked(false);//设置不锁定`
12. `cell.setCellStyle(unlocked);`
13. `sheet.protectSheet("password");//设置保护密码`

![img](image/MVraArE.png!web)

**(2)数据有效性**

1. `HSSFSheet sheet= workbook.createSheet("Test0");// 创建工作表(Sheet)`
2. `HSSFRow row=sheet.createRow(0);`
3. `HSSFCell cell=row.createCell(0);`
4. `cell.setCellValue("日期列");`
5. `CellRangeAddressList regions = new CellRangeAddressList(1, 65535,0, 0);//选定一个区域`
6. `DVConstraint constraint = DVConstraint.createDateConstraint(`DVConstraint . OperatorType . BETWEEN , "1993-01-01" ,"2014-12-31" , "yyyy-MM-dd" );
7. `HSSFDataValidation dataValidate = new HSSFDataValidation(regions,constraint);`
8. `dataValidate.createErrorBox("错误", "你必须输入一个时间！");`
9. `sheet.addValidationData(dataValidate);`

![img](image/miyei2.png!web)

CellRangeAddressList类表示一个区域，构造函数中的四个参数分别表示起始行序号，终止行序号，起始列序号，终止列序号。65535是一个Sheet的最大行数。另外，CreateDateConstraint的第一个参数除了设置成DVConstraint.OperatorType.BETWEEN外，还可以设置成如下一些值，大家可以自己一个个去试看看效果：

![img](image/6BVZvua.png!web)

验证的数据类型也有几种选择，如下：

![img](image/nENr6fZ.png!web)

**(3)生成下拉式菜单**

1. `CellRangeAddressList regions = new CellRangeAddressList(0, 65535,0, 0);`
2. `DVConstraint constraint =DVConstraint.createExplicitListConstraint(new String[] { "C++","Java", "C#" });`
3. `HSSFDataValidation dataValidate = new HSSFDataValidation(regions,constraint);`
4. `sheet.addValidationData(dataValidate);`

![img](image/VFJbE3j.png!web)

**(4)打印基本设置**

1. `HSSFSheet sheet= workbook.createSheet("Test0");// 创建工作表(Sheet)`
2. `HSSFPrintSetup print = sheet.getPrintSetup();//得到打印对象`
3. `print.setLandscape(false);//true，则表示页面方向为横向；否则为纵向`
4. `print.setScale((short)80);//缩放比例80%(设置为0-100之间的值)`
5. `print.setFitWidth((short)2);//设置页宽`
6. `print.setFitHeight((short)4);//设置页高`
7. `print.setPaperSize(HSSFPrintSetup.A4_PAPERSIZE);//纸张设置`
8. `print.setUsePage(true);//设置打印起始页码不使用"自动"`
9. `print.setPageStart((short)6);//设置打印起始页码`
10. `sheet.setPrintGridlines(true);//设置打印网格线`
11. `print.setNoColor(true);//值为true时，表示单色打印`
12. `print.setDraft(true);//值为true时，表示用草稿品质打印`
13. `print.setLeftToRight(true);//true表示“先行后列”；false表示“先列后行”`
14. `print.setNotes(true);//设置打印批注`
15. `sheet.setAutobreaks(false);//Sheet页自适应页面大小`

更详细的打印设置请参考： http://tonyqus.sinaapp.com/archives/271

**(5)超链接**

1. `HSSFSheet sheet = workbook.createSheet("Test0");`
2. `CreationHelper createHelper = workbook.getCreationHelper();`
3. `// 关联到网站`
4. `Hyperlink link =createHelper.createHyperlink(Hyperlink.LINK_URL);`
5. `link.setAddress("http://poi.apache.org/");`
6. `sheet.createRow(0).createCell(0).setHyperlink(link);`
7. `// 关联到当前目录的文件`
8. `link = createHelper.createHyperlink(Hyperlink.LINK_FILE);`
9. `link.setAddress("sample.xls");`
10. `sheet.createRow(0).createCell(1).setHyperlink(link);`
11. `// e-mail 关联`
12. `link = createHelper.createHyperlink(Hyperlink.LINK_EMAIL);`
13. `link.setAddress("mailto:poi@apache.org?subject=Hyperlinks");`
14. `sheet.createRow(0).createCell(2).setHyperlink(link);`
15. `//关联到工作簿中的位置`
16. `link = createHelper.createHyperlink(Hyperlink.LINK_DOCUMENT);`
17. `link.setAddress("'Test0'!C3");//Sheet名为Test0的C3位置`
18. `sheet.createRow(0).createCell(3).setHyperlink(link);`

![img](image/rIFV3q.png!web)

## 9.POI对Word的基本操作

**(1)POI操作Word简介**

POI读写Excel功能强大、操作简单。但是POI操作时，一般只用它读取word文档，POI只能能够创建简单的word文档，相对而言POI操作时的功能太少。

**(2)POI创建Word文档的简单示例**

1. `XWPFDocument doc = new XWPFDocument();// 创建Word文件`
2. `XWPFParagraph p = doc.createParagraph();// 新建一个段落`
3. `p.setAlignment(ParagraphAlignment.CENTER);// 设置段落的对齐方式`
4. `p.setBorderBottom(Borders.DOUBLE);//设置下边框`
5. `p.setBorderTop(Borders.DOUBLE);//设置上边框`
6. `p.setBorderRight(Borders.DOUBLE);//设置右边框`
7. `p.setBorderLeft(Borders.DOUBLE);//设置左边框`
8. `XWPFRun r = p.createRun();//创建段落文本`
9. `r.setText("POI创建的Word段落文本");`
10. `r.setBold(true);//设置为粗体`
11. `r.setColor("FF0000");//设置颜色`
12. `p = doc.createParagraph();// 新建一个段落`
13. `r = p.createRun();`
14. `r.setText("POI读写Excel功能强大、操作简单。");`
15. `XWPFTable table= doc.createTable(3, 3);//创建一个表格`
16. `table.getRow(0).getCell(0).setText("表格1");`
17. `table.getRow(1).getCell(1).setText("表格2");`
18. `table.getRow(2).getCell(2).setText("表格3");`
19. `FileOutputStream out = newFileOutputStream("d:\\POI\\sample.doc");`
20. `doc.write(out);`
21. `out.close();`

![img](image/RveaQbV.png!web)

**(3)POI读取Word文档里的文字**

1. `FileInputStream stream = newFileInputStream("d:\\POI\\sample.doc");`
2. `XWPFDocument doc = new XWPFDocument(stream);// 创建Word文件`
3. `for(XWPFParagraph p : doc.getParagraphs())//遍历段落`
4. `{`
5. ` System.out.print(p.getParagraphText());`
6. `}`
7. `for(XWPFTable table : doc.getTables())//遍历表格`
8. `{`
9. ` for(XWPFTableRow row : table.getRows())`
10. ` {`
11. ` for(XWPFTableCell cell : row.getTableCells())`
12. ` {`
13. ` System.out.print(cell.getText());`
14. ` }`
15. ` }`
16. `}`

![img](image/R7BfMjy.png!web)

 

\-----------------------------------------------------------------------------------------------------------

