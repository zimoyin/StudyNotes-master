# cpolar内网穿透

官网：https://www.cpolar.com/
原贴教程：https://www.cpolar.com/blog/how-to-install-cpolar-under-android-termux-hyper-terminal  

> 账号：tianxuanzimo@qq.com
> 密码：roo*********
>
服务器位置不确定：延迟低到中

1.安装cpolar
```shell
apt install dnsutils
```
它会创建一个DNS解析文件，路径在$PREFIX/etc/resolv.conf，里面有配置DNS解析服务器地址（默认已经加了8.8.8.8）

2.下载最新的cpolar客户端(ARM 版本）
``shell
curl -O -L https://cpolar.com/static/downloads/cpolar-stable-linux-arm.zip
```


3.解压缩
```shell
unzip cpolar-stable-linux-arm.zip
```

4.注册/选择套餐（免费），在cpolar后台复制你自己的token值
位置：后台/验证/Authtoken

5.运行
```shell
./cpolar authtoken xxxxxxxxxxtokenxxxxxxx

如 ： ./cpolar authtoken ZjczYzcyMWUtOTI1My00YWE0LWE5MGMtNzk5M2M1ZDJiNWY3
```

6.映射端口到外网（运行后他会给你url）
     *  ./cpolar 协议 端口
        *  ./cpolar http 8080
        *  ././cpolar tcp 25565
   
   
# ngrok
官网：http://ngrok.cc
因为免费的服务器在海外所以延迟高
1. 注册/登录
2. 开通/管理隧道
本地端口：ip:端口
3. 下载客户端（python版的）
在termux的home目录下解压运行`python sunny.py`
4. 安装运行
clientid 是隧道id


#樱花映射
官网：https://www.natfrp.com/?page=panel&module=proxies
免费好用
注：http（s）映射要选域外服务器否则要备案

1. 注册/登录

2. 开通通道

3. 下载frp
* 下载地址：https://github.com/fatedier/frp
* 根据自己系统选择版本
* 解压 tar -xzvf frp文件

4. 将通道的配置文件内容'覆盖'frpc.ini文件
* 通道的配置文件位于隧道列表，复制配置文件的”全部内容”

5. 运作./frpc -c ./frpc.ini

6. 后台运行nohup ./frpc -c ./frpc.ini

7. 报错：zsh: exec format error: ./frp
* 这是你下载的版本和系统支持的版本不同


# NATAPP
官网：https://natapp.cn/
没用过
