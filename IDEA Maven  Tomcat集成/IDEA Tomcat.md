# 一、创建Maven项目

1. 利用maven创建web项目，首先创建项目中选择maven，然后在maven中勾选`Create from archetype`选项，然后选择`org.apache.maven.archetypes:maven-archetype-webapp` 然后Next

![1](.\image\1.PNG)

2. 对项目命名，并且选择项目保存地址，然后next

![2](.\image\2.PNG)

3. 这三个地方必须注意，第一个选择你的maven，第二个选择你已经配置好的maven的**setting.xml**(==注意你的setting.xm要使用国内源否则你会崩溃)==，第三个是选择你的类保存的位置

![3](.\image\3.PNG)

4. 单机finish后IDEA会自动配置你的环境，时间可能有点长耐心等待

二、配置Tomcat

1. 配置Tomcat，选择Add Configuration。也可以从Run菜单中选择 Edit Configurations... 他们打开后是一样的

![4](.\image\4.PNG)

2. 选择`+` 后选择Tomcat Service 中的Local

![5](.\image\5.PNG)



3. 配置Tomcat

![image-20210119123622824](.\image\image-20210119123622824.png)

# 二、配置Tomcat的地址

![image-20210119124152486](.\image\6.png)

4. 配置项目:单击Fix选择 xxx:war exploded	如果这个没有这个选项则看下图2

![image-20210119124310926](.\image\7.png)

图2：

![image-20210119143704022](.\image\9png)



5. 单击appl和ok后就可以了

6. 选择file菜单下的project structure.选择则下图的右侧那个小按钮效果一样

![image-20210119124531641](.\image\8.png)

7. 

![image-20210119125143696](.\image\9.png)



8. 添加Tomcat依赖:没有他就无法创建Servlet

![image-20210119143919623](.\image\10.png)



9. 

![image-20210119144120136](.\image\56.png)

![image-20210119144219800](.\image\image-20210119144219800.png)