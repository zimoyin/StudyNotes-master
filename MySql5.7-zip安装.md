

## 下载

Mysql 5.7 下载:https://cdn.mysql.com//archives/mysql-5.7/mysql-5.7.19-winx64.zip

## 安装

1. 解压
2. 配置环境变量。（配置bin目录的环境变量）
3. 在mysql安装目录新建配置文件my.ini

```ini
[mysqld]
#设置3306端口
port = 3306 
# 设置mysql的安装目录
basedir=C:\Program Files\Java\mysql-5.7.19-winx64
# 设置mysql数据库的数据的存放目录(data会自动生成)
datadir=C:\Program Files\Java\mysql-5.7.19-winx64\data
# 跳过登录验证
skip-grant-tables
```

4. 管理员运行cmd，进入MySQL的bin目录
5. 运行`mysqld -install`命令来安装MySQL
6. 运行`mysqld --initialize-insecure --user=mysql` 初始化数据文件
7. 启动mysql服务`net start mysql`
8. 登录mysql`mysql -u root -p`
9. 更改root用户密码

```sql
update mysql.user set anthentication_string=password('123456') where user='root' and Host = 'localhost';
```

如果上面的运行失败运行它

```mysql
update mysql.user set authentication_string=password('123') where user='root';
```

刷新权限

```mysql
flush privileges;
```

退出mysql

```mysql
exit
```

10. 删除my.ini中的`skip-grant-tables`

11. 重启mysql服务

```shell
net stop mysql
net start mysql
```

12. 登录mysql

```mysql
mysql -u root -p123
```

