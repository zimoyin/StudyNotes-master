IDEA运行Tomcat乱码：

解决方案一：

1. 选择菜单Run==> Edit Configurations...
2. 在Rum里面找到Tomcat配置，在配置里面找到 **VM options:**  输入值`-Dfile.encoding=UTF-8`
3. 清理缓存并重启IDEA：选择菜单的File，Invalidate-caches按钮，选择清除缓存并重启，



解决方案二（测试有效）：

1. 选择Help菜单===>Edit Custom VM Options...  
2. 在里面添加`-Dfile.encoding=UTF-8`强转设置编码为UTF-8
3. 清理缓存并重启IDEA：选择菜单的File，Invalidate-caches按钮，选择清除缓存并重启，



