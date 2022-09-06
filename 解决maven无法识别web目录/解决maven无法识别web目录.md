# 问题

在maven文件中web目录他没有被maven识别到，也就是web目录上没有小蓝点![image-20210219140521926](解决maven无法识别web目录.assets/image-20210219140521926.png)，正常情况是有的![image-20210219135723311](解决maven无法识别web目录.assets/image-20210219135723311.png)

![image-20210219135526040](解决maven无法识别web目录.assets/image-20210219135526040.png)

# 解决方法

1. 打开Project Structure	（File--->Project Structure）
2. 选择Modules
3. 选中你的项目并在上面点击`+`按钮，添加一个`web`
4. 之后再右侧的上方设置你的`web.xml`在下方设置你web目录的根路径

![image-20210219140349717](解决maven无法识别web目录.assets/image-20210219140349717.png)

