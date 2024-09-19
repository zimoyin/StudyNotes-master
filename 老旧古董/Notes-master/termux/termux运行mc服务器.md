## 安装Ubuntu系统

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

## 安装Java
Ubuntu安装Java不然不能运行服务器
```
apt install openjdk-8-jdk
```


* 安装后Java所在路径在此路径下找
```
/data/data/com.termux/files/home/ubuntu-fs/usr/lib
```
所以java路径为 ：
```
/data/data/com.termux/files/home/ubuntu-fs/usr/lib/jvm/java-8-openjdk-arm64
```
jvm文件是存放jdk的，如果有jdk压缩包就可以在里面解压并且指定环境变量即可


## 下载CatServer服务器
地址：https://github.com/CatServer/CatServer/

* catserver.jar放在home/ubuntu-fs/root路径下
* 进入Ubuntu系统（./start-ubuntu.sh)
* 运行服务器`java -Xmx2G -jar catserver的名字`
  * 第一次运行会下载一下文件，第二次就不会了
  * 每次开启服务器都是用这指令
  * -Xmx：指定运行内存可改


