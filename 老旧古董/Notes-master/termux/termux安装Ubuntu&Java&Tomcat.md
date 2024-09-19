# termux 安装Java

最新版的termux的pkg命令可以直接安装java openjdk 17 了

搜索可下载的jdk

```
pkg search openjdk
```

安装

```java
pkg install openjdk-17
```









# termux安装Ubuntu

* 推荐使用anLinux软件来获取下载命令
* 下载Ubuntu
```
pkg install wget openssl-tool proot -y && hash -r && wget https://raw.githubusercontent.com/EXALAB/AnLinux-Resources/master/Scripts/Installer/Ubuntu/ubuntu.sh && bash ubuntu.sh
```
卸载Ubuntu
```
wget https://raw.githubusercontent.com/EXALAB/AnLinux-Resources/master/Scripts/Uninstaller/Ubuntu/UNI-ubuntu.sh && bash UNI-ubuntu.sh
```
* 安装运行Ubuntu
```
./start-ubuntu.sh
```
运行后用户在root文件夹下，而root文件夹是Ubuntu是的根路径
```
/data/data/com.termux/files/home/ubuntu-fs/root
```



# Ubuntu安装Java
```
apt install openjdk-11-jdk
```


* 安装后Java所在路径在此路径下找
```
/data/data/com.termux/files/home/ubuntu-fs/usr/lib
```
所以java路径为 ：
```
/data/data/com.termux/files/home/ubuntu-fs/usr/lib/jvm/java-11-openjdk-arm64
```
jvm文件是存放jdk的，如果有jdk压缩包就可以在里面解压并且指定环境变量即可

# Ubuntu安装Tomcat
* 官网下载Tomcat： *.tag.gz
* 下载在后安装(termux的话把安装包拷贝到Ubuntu根路径root下）
```
tar zxvf  *.tar.gz
```

此步后就可以运行Tomcat了，如果不行的话就配置catalina.sh文件
* 注意运行不了时更改配置：
在catalina.sh中添加(在cygwin=false 
os400=false代码前添加）：
```
# JAVA_HOME=jdk路径
JAVA_HOME=usr/lib/jvm/java-11-openjdk-arm64

# JAVA_OPTS为配置服务器内存等。
JAVA_OPTS="-server -Xms512m 
-Xmx1024m -XX:PermSize=600M
-XX:MaxPermSize=600m
-Dcom.sun.management.jmxremote" 
```

* 运行./startup.sh
会出现以下内容
```
Using CATALINA_BASE:   /root/tomcat-8.5.51
Using CATALINA_HOME:   /root/tomcat-8.5.51
Using CATALINA_TMPDIR: /root/tomcat-8.5.51/temp
Using JRE_HOME:        /usr/lib/jvm/java-11-openjdk-arm64/
Using CLASSPATH:       /root/tomcat-8.5.51/bin/bootstrap.jar:/root/tomcat-8.5.51/bin/tomcat-juli.jar
Tomcat started.
```
然后可以在浏览器访问Tomcat：（地址：127.0.0.1：8080）
