# 安装数据库

##安装
```
pkg install mariadb
```

* 安装失败的话就输入

```
mkdir /data/data/com.termux/files/usr/etc/my.cnf.d
```
原因是在：`/data/data/com.termux/files/usr/etc/`下没有`my.cnf.d`文件夹所以要创建一个

* 配置数据库
```
mysql_install_db
```
mysql_install_db会初始化MariaDB数据目录，并且在数据库中创建mysql（如果不存在）。MarinaDB使用这些表来管理特权，角色和差距

## 启动数据库（服务）

```mysql
mysqld
```
或者
```mysql
mysqld_safe -u root &
```
mysql_sade 与root用户一起运行。&代表在后台运行

## 登录数据库
* 新建一个窗口
* 登录数据库
	* mysql -u 用户名 -p 密码
		* 数据库一开始有两个用户root和u0用户
			* u0：用`whoami`查看用户名
		* 一开始是登录不了roo用户的所以登录第二个用户

```
mysql -u root -p
```
或者
```
mysql
```
输入后他可能会让你输入密码，因为没有密码所以回车即可。

## 区分

### mysql与mysqld的区别：

* mysqld 是服务端程序（即MySQL的服务器）要想使用客户端程序，该程序必须运行，因为客户端通过连接服务器来访问数据库；mysql 是命令行客户端程序。

### mysqld_safe 与 mysqld 区别：
* 直接运行 mysqld 程序来启动 MySQL 服务的方法很少见。mysqld_safe 脚本会在启动 MySQL 服务器后继续监控其运行情况，并在其死机时重新启动它。mysql.server 脚本其实也是调用 mysqld_safe 脚本去启动 MySQL 服务器的。
mysqld_safe 相当于多了一个守护进程，当 mysqld 挂了会自动把 mysqld 进程拉起来。


## 配置 MariaDB 远程登录


### 创建用户
* 创建一个可远程登录的用户：
```mysql
CREATE USER 'username'@'%' IDENTIFIED BY 'password';
eg: CREATE USER 'zimo'@'%' IDENTIFIED BY 'root';
```
% 通配符表示创建外网可访问的用户。

### 用户授权：

* 用户授权 只授权部分
```mysql
GRANT ALL privileges ON databasename.tablename TO 'username'@'%';
eg: GRANT ALL privileges ON databasename.tablename TO 'zimo'@'%';
```
* 如果要授予该用户对所有数据库和表的相应操作权限则可用 * 表示，如 *.*
```mysql
GRANT ALL privileges ON *.* TO 'username'@'%';
eg: GRANT ALL privileges ON *.* TO 'zimo'@'%';
```
* 刷新授权：
```mysql
flush privileges;
```
通过以上配置即可通过局域网内其它设备登录手机里的数据库进行操作。

注：由于 my.cnf.d 目录下并未找到 mysqld.cnf 配置文件，所以并未修改注释 bind-address 这个选项也可进行远程登录。

# 停止 MariaDB 服务器

要停止 MariaDB 服务器，请输入：
```shell
pkill mysql
```
或者，使用单词“MySQL”取其进程 ID ，使用命令 grep，然后杀死他们kill -9 [ID]，-9 是发送终止信号。

要查找执行的进程ID：

```shell
ps aux | grep mysql
```
拥有ID后，请杀死它们：
```shell
kill -9 15423
```

# 安全的 MariaDB

MariaDB 的安装已经可以正常工作，并且如果我们只打算使用它，那么我们就不必在意安全性。但是，相反，如果您想通过良好的习惯来确保它，可以这样做。为此，执行：
```shell
mysql_secure_installation
```
这将为您提供一个向导，该向导将帮助您确保安装MySQL：输入密码，删除测试特权等。

# mysql_secure_installation

安装完mysql-server 会提示可以运行mysql_secure_installation。运行mysql_secure_installation会执行几个设置：

  a)为root用户设置密码
  b)删除匿名账号
  c)取消root用户远程登录
  d)删除test库和对test库的访问权限

  e)刷新授权表使修改生效

  ```
 [root@server1 ~]# mysql_secure_installation
NOTE: RUNNING ALL PARTS OF THIS SCRIPT IS RECOMMENDED FOR ALL MySQL
SERVERS IN PRODUCTION USE! PLEASE READ EACH STEP CAREFULLY!
In order to log into MySQL to secure it, we'll need the current
password for the root user. If you've just installed MySQL, and
you haven't set the root password yet, the password will be blank,
so you should just press enter here.
Enter current password for root (enter for none):<–初次运行直接回车
OK, successfully used password, moving on…
Setting the root password ensures that nobody can log into the MySQL
root user without the proper authorisation.
Set root password? [Y/n] <– 是否设置root用户密码，输入y并回车或直接回车
New password: <– 设置root用户的密码
Re-enter new password: <– 再输入一次你设置的密码
Password updated successfully!
Reloading privilege tables..
… Success!
By default, a MySQL installation has an anonymous user, allowing anyone
to log into MySQL without having to have a user account created for
them. This is intended only for testing, and to make the installation
go a bit smoother. You should remove them before moving into a
production environment.
Remove anonymous users? [Y/n] <– 是否删除匿名用户,生产环境建议删除，所以直接回车
… Success!
Normally, root should only be allowed to connect from 'localhost'. This
ensures that someone cannot guess at the root password from the network.
Disallow root login remotely? [Y/n] <–是否禁止root远程登录,根据自己的需求选择Y/n并回车,建议禁止
… Success!
By default, MySQL comes with a database named 'test' that anyone can
access. This is also intended only for testing, and should be removed
before moving into a production environment.
Remove test database and access to it? [Y/n] <– 是否删除test数据库,直接回车
- Dropping test database…
… Success!
- Removing privileges on test database…
… Success!
Reloading the privilege tables will ensure that all changes made so far
will take effect immediately.
Reload privilege tables now? [Y/n] <– 是否重新加载权限表，直接回车
… Success!
Cleaning up…
All done! If you've completed all of the above steps, your MySQL
installation should now be secure.
Thanks for using MySQL!

  ```

# 修改密码

```mysql
set password for root@localhost =password("newpassword");
```

## 报错
```mysql
mysql> set password for root@localhost = password('123456');
ERROR 1064 (42000): You have an error in your SQL syntax; check the manual that
corresponds to your MySQL server version for the right syntax to use near 'passw
ord('123456')' at line 1
```
* 解决方法
```mysql
SET PASSWORD FOR root@localhost = '123456';
```

```mysql
mysql> SET PASSWORD FOR root@localhost = '123456';
Query OK, 0 rows affected (0.02 sec)
```

问题描述
修改MySQL密码提示：ERROR 1372 (HY000): Password hash should be a 41-digit hexadecimal number



问题原因
输入的密码是明文，要求输入十六进制数字。

解决方案
使用mysql命令行：select password('密码'); ，查询密码对应的十六进制码，然后使用十六进制码进行修改，问题即可解决。(不要丢了*号)

```
select password('密码');
```

