# Spring 笔记

**作者：子墨**

spring使现由技术更加容易使用，本身是一个大杂烩，整合了现有技术框架  
SSH： Struct2 + Spring + Hibernate  
SSM: SpringMVC + Spring + Mybatis
Spring地址： https://spring.io/  
Spring中文文档： https://www.docs4dev.com/docs/zh/spring-framework/5.1.3.RELEASE/reference  
String官网文档： https://docs.spring.io/spring/docs/current/spring-framework-reference/core.html  

# 1. 搭建spring环境
## 1.1 maven构建项目

### 1.1.2 设置阿里云镜像

* 路径：.\apache-maven-3.6.3\conf\settings.xml  
```xml
    <!-- 设置阿里云镜像  -->
      <mirror>
        <id>alimaven</id>
        <mirrorOf>central</mirrorOf>
        <name>aliyun maven</name>
        <url>http://maven.aliyun.com/nexus/content/repositories/central/</url>
    </mirror>

      <!-- 设置jar包保存的位置 -->
      <localRepository>C:\Program Files\Java\mm</localRepository>
```
### 1.1.3 导包
* 文件名：pom.xml  
* 注意如果项目不能运行就**换一个库的位置**  
```xml
    
   
    <properties>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <maven.compiler.source>1.8</maven.compiler.source>
        <maven.compiler.target>1.8</maven.compiler.target>
    </properties>

    <!-- 获取spring -->
    <!-- 导入spring-webmvc会连同其他spring依赖一并导入 -->
    <dependencies>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-webmvc</artifactId>
            <version>5.2.0.RELEASE</version>
        </dependency>
<!--	如果没有导入就把这个注释打开
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-core</artifactId>
            <version>5.2.0.RELEASE</version>
        </dependency>

        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-context</artifactId>
            <version>5.2.0.RELEASE</version>
        </dependency>
 -->
        <!-- 单元测试 -->
        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <version>4.12</version>
        </dependency>
    </dependencies>

<!--插件    -->

    <build>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <version>3.2</version>
                <configuration>
                    <verbose>true</verbose>
                    <fork>true</fork>
                    <compilerVersion>1.8</compilerVersion>
                    <source>1.8</source>
                    <target>1.8</target>
                </configuration>
            </plugin>
        </plugins>
    </build>
```
## 1.2 创建spring
* xml配置文件模板  
    * xml文件名任意 （建议名称：applicationContext.xml）放在`resources`文件夹下  
```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xsi:schemaLocation="http://www.springframework.org/schema/beans
       	http://www.springframework.org/schema/beans/spring-beans.xsd">

</beans>
```


## 1.3 获取容器 ApplicationContext

```java
ApplicationContext context = new ClassPathXmlApplicationContext("配置文件.xml",..., "配置文件n.xml");
```
# 2. IOC
控制反转，对象的创建权交给spring创建    

## IOC概论

### 一、什么是IOC

   维基百科上说到：2004年Martin Fowler 提出了“控制反转的”概念，他得出的结论是：依赖对象的获得被反转了。后来为这个创造了一个更好的名字：依赖注入(IOC = Inversion of Control).简单的解释是：系统的运作是通过两个或多个类的合作来实现业务逻辑，这使得每个对象都需要与其合作的对象的引用（依赖关系），这个依赖对象以前是通过自身实现去获得，现在通过一个容器统一的管理这些依赖关系，从而获得这种依赖的这种实现方式，我们可以成为IOC。



###  二、为什么要用IOC

   我们知道任何事物的诞生，总会有其目的，这里我们先模拟几种场景，让我们更清楚的了解IOC是如何诞生的。

 

   例1: A类 需要用到业务B类的引用，这时候我们在A 里面进行 B b = new B();随着系统的庞大，我可能后面 C,F,G...N 都要用B类的引用，我们在每个类里面都进行类似A的操作吗？好吧，你这样做了！但是突然有一天需要变化，B类我们必须要一个带参构造public B(Stirng str){}.那么你要把所有的类的引用全部修改一次吗？如果可能继续扩展，继续变化呢？

   例2：同样很多类都需要B类的引用，B是A接口的实现，我现在不想用实现类B了，从新实现一个类C，全部替换过来，那么你需要从N 个类里面进行修改吗？



   例3：大部分情况下类的关系是：A需要B,B需要C，C需要D，D需要...!然后经常的变动是：B不满足了，需要改变，或者替换，然后可能D也需要替换等，然后我们就各个地方到处更改吗？



   说道这里，我们先看看按原始方式做的优劣： 

   **优点：**

   1.速度快，写得舒服！



   **缺点：**

   1.创建太多对象，占用内存空间。

   2.维护麻烦，改动可能影响太多的类



   当然可能大家会反驳，单例模式和工厂模式能解决那些缺点。当然熟悉工厂模式的朋友知道，即使是通过反射生成类的动态工厂，也需要提供路径参数，在在使用这些工厂的时候，我们依然会出现类似于硬编码的问题，不好管理。因此IOC诞生了。

   IOC 基本上也是动态工厂和单例等的结合体，并将类路径移植到配置文件，它带来的好处很多。

​    1.统一管理文件，一个接口多个实现，替换更改方便

   2.同时可以监控类的生命周期，和一些其他属性

   3.让我们程序解耦，代码量减少，无需关心具体实现，更多的去关注业务逻辑

   4.这种可拔插的模式，更符合OOP的那些原则。

   5.我们测试，也更加方便，类也能更好的复用了。

 

   当然也有一些坏处：

   1.让我们的生成对象的步骤变得麻烦，初学可能有点不习惯。

   2.反射效率稍微低点，但是现在的优化影响不大

## 2.1 spring创建对象

* spring容器初始化的时候，容器里的对象也会被初始化  
* spring 默认只创建一个对象，所以你无论获取多少次对象他们都是同一个对象



* springIOC容器的2种形式,    获取容器 ：

1. ​	xml配置文件：applicationContext.xml

```java
	ApplicationContext context= new ClassPathXmlApplicationContext("applicationContext.xml");
```

2. ​	注解：带有@Configuration注解的类（配置类）

 ```java
    ApplicationContext context  = new AnnotationConfigApplicationContext(Config.class) ;
 ```

-- 注意：两种形式获取的Ioc容器是 独立的

####  1.1 无参构造创建对象
> spring 创建对象默认需要无参构造否则会报错   

例：  
* applicationContext.xml  
```xml
        <bean name="hello" class="com.zimo.HelloSpring">
                <property name="str" value="HelloWorld"/>
        </bean>
```
* HelloSpring.java  
```java
   private String str;

    HelloSpring(){
        System.out.println("无参构造被执行。。。。。");
    }
    public void setStr(String str) {
        this.str = str;
    }

    public String getStr() {
        return str;
    }

    @Override
    public String toString() {
        return "Spring： "+getStr();
    }
```
* Test.java  
```java
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");//加载配置文件
        HelloSpring zi = (HelloSpring)context.getBean("hello");//获取com.zimo.HelloSpring对象，hello为这个对象的名字(注意强转)
		//HelloSpring zi = context.getBean("hello",com.zimo.Test.class);//不需要强转
        System.out.println(zi.toString());//打印com.zimo.HelloSpring对象的tostring方法
```
* 输出结果：  
```java
无参构造被执行。。。。。
Spring： HelloWorld
```


## 2.2 Spring 配置
### 2.2.1 别名 alias
为对象起一个别名  
```xml
<alias name="名称" alias="别名"/>
```
### 2.2.2 Bean配置（部分）
* id ： bean 的唯一标识符
* class:  bean 对象所对应的全限定名（包名+类型）
* name ： 别名，可取多个
```xml
<bean id="唯一标识(唯一)" class="全限定名" name="名称1(可用逗号，空格，分号来分割多个名称)" ></bean>>
```
### 2.2.3 import
> 用于将多个配置文件导入进一个配置文件
* applicationContext.xml
```xml
<import resource="./beans.xml">
```
* beans.xml
```xml
    ... ...
```
## 2.3 Bean的作用域 
Spring 3中为Bean定义了5中作用域，分别为 **singleton（单例）、prototype（原型）**、request、session和global session，5种作用域说明如下：  
* **singleton**：单例模式，Spring IoC容器中只会存在一个共享的Bean实例，无论有多少个Bean引用它，**始终指向同一对象**。Singleton作用域是Spring中的**缺省作用域** 

```xml
<bean id="student" class="com.zimo.Student" scope="singleton"/>
```
* **prototype**：原型模式，每次通过Spring容器获取prototype定义的bean时，容器都将创建一个新的Bean实例，**每个Bean实例都有自己的属性和状态（每个对象都不一样）**，而singleton全局只有一个对象。<u>根据经验，对有状态的bean使用prototype作用域，而对无状态的bean使用singleton作用域</u>。   
```xml
<bean id="student" class="com.zimo.Student" scope="prototype"/>
```



* request：在一次Http请求中，容器会返回该Bean的同一实例。而对不同的Http请求则会产生新的Bean，而且该bean仅在当前Http Request内有效。   针对每一次Http请求，Spring容器根据该bean的定义创建一个全新的实例，且该实例仅在当前Http请求内有效，而其它请求无法看到当前请求中状态的变化，当当前Http请求结束，该bean实例也将会被销毁。   
* session：在一次Http Session中，容器会返回该Bean的同一实例。而对不同的Session请求则会创建新的实例，该bean实例仅在当前Session内有效。   
,同Http请求相同，每一次session请求创建新的实例，而不同的实例之间不共享属性，且实例仅在自己的session请求内有效，请求结束，则实例将被销毁。   
* global Session：在一个全局的Http Session中，容器会返回该Bean的同一个实例，仅在使用portlet context时有效。 


# 3 DI：依赖注入
### 3.1 构造器注入
#### 3.1.1 构造注入： 无参构造创建对象
> spring 创建对象默认需要无参构造否则会报错   

例：  
* applicationContext.xml  
```xml
        <bean name="hello" class="com.zimo.HelloSpring">
                <property name="str" value="HelloWorld"/>
        </bean>
```
* HelloSpring.java  
```java
   private String str;

    HelloSpring(){
        System.out.println("无参构造被执行。。。。。");
    }
    public void setStr(String str) {
        this.str = str;
    }

    public String getStr() {
        return str;
    }

    @Override
    public String toString() {
        return "Spring： "+getStr();
    }
```
* Test.java  
```java
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");//加载配置文件
        HelloSpring zi = (HelloSpring)context.getBean("hello");//获取com.zimo.HelloSpring对象，hello为这个对象的名字(注意强转)
        System.out.println(zi.toString());//打印com.zimo.HelloSpring对象的tostring方法
```
* 输出结果：  
```java
无参构造被执行。。。。。
Spring： HelloWorld
```
####  3.1.2 构造注入： Spring 有参构造创建对象
###### 测试案例
* HelloSpring.java  
```java
   private String str;

    HelloSpring(String name){
        System.out.println(name);
    }
    public void setStr(String str) {
        this.str = str;
    }

    public String getStr() {
        return str;
    }

    @Override
    public String toString() {
        return "Spring： "+getStr();
    }
```
* Test.java  
```java
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");//加载配置文件
        HelloSpring zi = (HelloSpring)context.getBean("hello");//获取com.zimo.HelloSpring对象，hello为这个对象的名字
        System.out.println(zi.toString());//打印com.zimo.HelloSpring对象的tostring方法
```
#### 第一种： 通过下标赋值
```xml
    <bean name="hello" class="com.zimo.HelloSpring">
       <constructor-arg index="0" value="紫陌"/>
    </bean>
```
#### 第二种： 通过类型赋值
```xml
	<!--tyoe除了基本数据类型，引用数据类型必须用全限定名    -->
    <bean name="hello" class="com.zimo.HelloSpring">
        <constructor-arg type="java.lang.String" value="紫陌"/>
    </bean>
```
#### 第三种： 通过参数名赋值
```xml
    <bean name="hello" class="com.zimo.HelloSpring">
        <constructor-arg name="name" value="紫陌"/>
    </bean>
```

----



### 3.2 Set方法注入

* 依赖注入：Set注入
    * 依赖：bean对象的创建依赖于容器
    * 注入：bean对象中的所有属性，由容器注入

#### 3.2 测试案例代码
* Srudent
```java
    //set注入各种类型
    private String name;//名称
    private Address address;//地址
    private String [] books;//书
    private List<String> hobbys;//爱好
    private Map<String,String> card;//卡
    private Set<String> games;//游戏
    private String wife;//老婆
    private Properties info;//信息

    //省略get/set方法
       @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", address=" + address.toString() +
                ", books=" + java.util.Arrays.toString(books) +
                ", hobbys=" + hobbys +
                ", card=" + card +
                ", games=" + games +
                ", wife='" + wife + '\'' +
                ", info=" + info +
                '}';
    }
```
* Address
```java
   private  String address;
  //省略get/set toString方法
```
* MyTest
```java
public static void main(String[] args) {
        ApplicationContext app = new ClassPathXmlApplicationContext("beans.xml");
        Student student = (Student)app.getBean("student");
        System.out.println(student.toString());
}
```

* beans.xml
```xml
<bean id="address" class="com.zimo.Address">
        <property name="address" value="花谷"></property>
    </bean>


    <bean id="student" class="com.zimo.Student">
        <!--   第一种,普通值注入，value     -->
        <property name="name" value="子墨"/>

        <!--    第二种,bean注入,ref-->
        <property name="address" ref="address"/>
        <!--   第三种，数组注入    -->
        <property name="books">
            <array>
                <value>红楼梦</value>
                <value>三国</value>
            </array>
        </property>
        <!--  第四种,List注入   -->
        <property name="hobbys">
            <list>
                <value>吃饭</value>
                <value>睡觉</value>
                <value>打豆豆</value>
            </list>
        </property>
        <!--第五种，map注入-->
        <property name="card">
            <map>
                <entry key="身份证" value="13098423337726"></entry>
            </map>
        </property>
        <!--第六种，set注入-->
        <property name="games">
            <set>
                <value>只狼：影逝二度</value>
            </set>
        </property>
        <!--第七种，Null注入-->
        <property name="wife">
            <null/>
        </property>
        <!--第八种，Properties(配置文件)注入-->
        <property name="info">
            <props>
                <prop key="学号">20211656</prop>
                <prop key="性别">女</prop>
            </props>
        </property>
    </bean>
```
#### 3.2.1 注入
* value : 基本类型，String  
* ref   : 引用类型  
* 除了普通值注入,bean注入,map注入,Null注入,Properties(配置文件)注入其他几个大致相同  
* 常用的注入方式：普通值注入，bean注入  
```xml
        <!--   第一种,普通值注入，value     -->
        <property name="name" value="子墨"/>

        <!--    第二种,bean注入,ref-->
        <property name="address" ref="address"/>
        <!--   第三种，数组注入    -->
        <property name="books">
            <array>
                <value>红楼梦</value>
                <value>三国</value>
            </array>
        </property>
        <!--  第四种,List注入   -->
        <property name="hobbys">
            <list>
                <value>吃饭</value>
                <value>睡觉</value>
                <value>打豆豆</value>
            </list>
        </property>
        <!--第五种，map注入-->
        <property name="card">
            <map>
                <entry key="身份证" value="13098423337726"></entry>
            </map>
        </property>
        <!--第六种，set注入-->
        <property name="games">
            <set>
                <value>只狼：影逝二度</value>
            </set>
        </property>
        <!--第七种，Null注入-->
        <property name="wife">
            <null/>
        </property>
        <!--第八种，Properties(配置文件)注入-->
        <property name="info">
            <props>
                <prop key="学号">20211656</prop>
                <prop key="性别">女</prop>
            </props>
        </property>
```
----



### 3.3 命名空间 注入

#### 3.3.1 C命名空间: 对构造器进行注入
* 引入C命名空间   
```xml
    xmlns:c="http://www.springframework.org/schema/c"
```

##### 测试

* beans2.xml
```xml
  <bean id="student" class="com.zimo.Student" c:name="zimoyin"/>
    <!--
        对构造器进行注入
        有两种注入方法：
        * 下标注入
        * 属性值注入
    -->
```
* MyTest.java  
```java
   @Test
    public void test1(){
        ApplicationContext app = new ClassPathXmlApplicationContext("beans2.xml");
        Student student = (Student)app.getBean("student");
        System.out.println(student.getName());
//        System.out.println(student.toString());
    }
```
#### 3.3.2 P命名空间:对set方法进行注入
* 引入P命名空间  
```xml
    xmlns:p="http://www.springframework.org/schema/p"
```
##### 测试  

* beans2.xml
```xml
   <bean id="address" class="com.zimo.Address" p:address="地球"/>
    <bean id="student" class="com.zimo.Student" p:name="紫陌" p:address-ref="address" p:books="三体，半仙" p:games="1,2,3" p:hobbys="吃饭" p:info="255," />
<!--
    P:(基本)属性名称=“属性值”
    P:(引用)属性名称-ref="另一个bean的name或id"
    p:(数组)属性名称="值1，值2，...,值n"
    p:(list,set)属性名称=“属性值” //只能传一个
    p:(配置文件Properties)属性名称="属性值=属性值"
 -->
```
* MyTest.java  
```java  
   @Test
    public void test1(){
        ApplicationContext app = new ClassPathXmlApplicationContext("beans2.xml");
        Student student = (Student)app.getBean("student");
        System.out.println(student.toString());
    }
```

----



### 3.4 自动装配

#### 3.4.1 测试用的代码：

首先，准备三个类，分别是User，Cat，Dog。其中User属性拥有Cat和Dog对象。

* User

```java
package com.zimo;

public class User {
    private Cat cat;
    private Dog dog;
    private String str;

    public Cat getCat() {
        return cat;
    }

    public void setCat(Cat cat) {
        this.cat = cat;
    }

    public Dog getDog() {
        return dog;
    }

    public void setDog(Dog dog) {
        this.dog = dog;
    }

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }
}

```

* Cat

```java
package com.zimo;

public class Dog {
    public void shout() {
        System.out.println("miao~");
    }
}
```

* Dog

```java
package com.zimo;

public class Dog {
    public void shout() {
        System.out.println("wang~");
    }
}
```

* Test.java
```java
import com.zimo.User;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MyTest {
    @Test
     public void testMethodAutowire() {
        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
           	 //getBean("user", com.hdu.autowire.User.class)这样写后代码就不用强转了
                User user = context.getBean("user", com.zimo.User.class);
                user.getCat().shout();
                 user.getDog().shout();
    }
}

```

#### 3.4.2  xml配置文件
这个没什么可说，前面所以的篇幅都是这种

#### 3.4.3 autowire byName (按名称自动装配)
由于在手动配置xml过程中，常常发生字母缺漏和大小写等错误，而无法对其进行检查，使得开发效率降低。采用自动装配将避免这些错误，并且使配置简单化。  
　　当一个bean节点带有 **autowire byName**的属性时。

```xml
<bean id="cat" class="com.zimo.Cat" autowire="byName"/>
```



1. 将查找其类中所有的set方法名，例如setCat，获得将set去掉并且首字母小写的字符串，即cat。

2. 去spring容器中寻找是否有此字符串名称id的对象。（查找有没有和cat一致的bean的id。**区分大小写**）

3. 如果有，就取出注入；如果没有，就报空指针异常。  

4. 注意：如果将` <bean id="cat" class="com.zimo.Cat autowire="byName""></bean>` 改成：  

   ```xml
   <bean id="catXXX" class="com.zimo.Cat autowire="byName""/>
   ```

   ​    执行时报空指针java.lang.NullPointerException。因为按byName规则找不对应set方法，真正的setCat就没执行，对象就没有初始化，所以调用时就会报空指针错误。  

##### 3.4.3 测试

* beans.xml 	正确配置

```xml
<bean id="cat" class="com.hdu.autowire.Cat"></bean>
<bean id="dog" class="com.hdu.autowire.Dog"></bean>
    
<bean id="user" 
      class="com.zimo.User"
      autowire="byName">
</bean>
```

可以正常运行并且属性已经被装配进去了，输出结果：

```
miao~
wang~
```

* beans.xml 非正常配置：bean的id与set后面的字符串（属性名）不一致

```xml
  <bean id="cat" class="com.hdu.autowire.Cat"></bean>
  <bean id="dog666" class="com.hdu.autowire.Dog"></bean>
      
  <bean id="user" 
        class="com.zimo.User"
        autowire="byName">
  </bean>
```

  输出结果:

```java
  miao~
  
  java.lang.NullPointerException
  	at MyTest.testMethodAutowire(MyTest.java:13)
```

  

#### 3.5.3 autowire byType (按类型自动装配)

* 使用autowire byType首先需要保证：
   *  同一类型的对象，在spring容器中唯一。
   *  如果不唯一，会报不唯一的异常。

```xml
<bean id="cat" class="com.zimo.Cat"></bean>
<bean id="dog" class="com.zimo.Dog"></bean>
    
<bean id="user" 
      class="com.zimo.User"
      autowire="byType">
</bean>
```

​    将`<bean id="cat" class="com.zimo.Cat"></bean> `改成

```xml
<bean id="catXXX" class="com.zimo.Cat"></bean>
```

```xml
<bean  class="com.zimo.Cat"></bean>
```

​    因为是按类型装配，所以并不会报异常，也不影响最后的结果。

​    甚至将id属性去掉，也不影响结果。

#####  3.5.3测试   

* beans.xml	正确配置

```xml
<bean id="cat233" class="com.zimo.Cat"></bean>
<bean class="com.zimo.Dog"></bean>
    
<bean id="user" 
      class="com.zimo.User"
      autowire="byType">
</bean>
```

输出结果：

```
miao~
wang~

```



* beans.xml 	非正常配置：出现类型重复

```xml
<bean id="cat233" class="com.zimo.Cat"></bean>
<bean id="dog" class="com.zimo.Dog"></bean>
<bean id="dog2" class="com.zimo.Dog"></bean>
<bean id="user"
      class="com.zimo.User"
      autowire="byType">
</bean>
```

输出结果(就算不运行IDEA也会爆红):

```java
警告: Exception encountered during context initialization - cancelling refresh attempt: org.springframework.beans.factory.UnsatisfiedDependencyException: Error creating bean with name 'user' defined in class path resource [beans.xml]: Unsatisfied dependency expressed through bean property 'dog'; nested exception is org.springframework.beans.factory.NoUniqueBeanDefinitionException: No qualifying bean of type 'com.zimo.Dog' available: expected single matching bean but found 2: dog,dog2

org.springframework.beans.factory.UnsatisfiedDependencyException: Error creating bean with name 'user' defined in class path resource [beans.xml]: Unsatisfied dependency expressed through bean property 'dog'; nested exception is org.springframework.beans.factory.NoUniqueBeanDefinitionException: No qualifying bean of type 'com.zimo.Dog' available: expected single matching bean but found 2: dog,dog2

```





#### 3.6 全局autowire

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:aop="http://www.springframework.org/schema/aop"
       xmlns:tx="http://www.springframework.org/schema/tx"
       xmlns:p="http://www.springframework.org/schema/p"
       xmlns:util="http://www.springframework.org/schema/util"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:mvc="http://www.springframework.org/schema/mvc"
       xsi:schemaLocation="
        http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/aop
        http://www.springframework.org/schema/aop/spring-aop.xsd
        http://www.springframework.org/schema/tx
        http://www.springframework.org/schema/tx/spring-tx.xsd
        http://www.springframework.org/schema/util
        http://www.springframework.org/schema/util/spring-util.xsd
        http://www.springframework.org/schema/context
        http://www.springframework.org/schema/context/spring-context.xsd
        http://www.springframework.org/schema/mvc
        http://www.springframework.org/schema/mvc/spring-mvc.xsd"  default-autowire="byName">

        <bean id="cat" class="com.zimo.Cat"></bean>
        <bean id="dog" class="com.zimo.Dog"></bean>

        <bean id="user" class="com.zimo.User" autowire="default"/>
        <bean id="user2" class="com.zimo.User" autowire="default"/>
        <bean id="user3" class="com.zimo.User" autowire="default"/>

</beans>
```



# 4 注解

配置注解的xml模板  

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:aop="http://www.springframework.org/schema/aop"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        https://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/context
        https://www.springframework.org/schema/context/spring-context.xsd
        http://www.springframework.org/schema/aop
        https://www.springframework.org/schema/context/spring-aop.xsd">

        <!-- 开启注解支持 -->
        <context:annotation-config/>


</beans>
```



## 4.1 注解自动装配

### 4.1 测试代码：

* beans.xml

```xml
		<bean id="cat" class="zimo.Cat"></bean>
        <bean id="dog" class="zimo.Dog"></bean>
        <bean id="user" class="zimo.User"/>
```

首先，准备三个类，分别是User，Cat，Dog。其中User属性拥有Cat和Dog对象。

* User

```java
package com.zimo;

public class User {
    private Cat cat;
    private Dog dog;
    private String str;
    
    
   public void setDog(Dog dog) {
        this.dog = dog;
    }
    
    public Cat getCat() {return cat;}
    public Dog getDog() { return dog;}
    public String getStr() { return str;}
    
    @Override
    public String toString() {
        return "User{" +
                "cat=" + cat +
                ", dog=" + dog +
                ", str='" + str + '\'' +
                '}';
    }
}

```

* Cat

```java
package com.zimo;

public class Dog {
    public void shout() {
        System.out.println("miao~");
    }
}
```

* Dog

```java
package com.zimo;

public class Dog {
    public void shout() {
        System.out.println("wang~");
    }
}
```

* Test.java

```java
 	@Test
    public  void main() {
        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        //getBean("user", com.hdu.autowire.User.class)这样写后代码就不用强转了
        User user = context.getBean("user", zimo.User.class);
        System.out.println(user);

    }
```



### 4.1.1 @Autowired

@Autowired

> 为属性自动注入(引用)值，前提是这个自动装配的属性在（Spring）IOC容器中存在，且符合名字byName，或类型符合byType
>
> @Autowired可以用在属性上方或者Set方法上方，当用在属性上方时则不需要定义Set方法了

**注意：**

>@Autowired自动装配会根据名称和类型进行自动装配，当其中一者不成立会使用令一者进行装配（类型不唯一，使用名称进行装配），当两者都不成立就会报错，也可以使用@Qualifier进行辅助避免@Autowired找不到bean报错

* User

```java
    @Autowired
    private Cat cat;
    private Dog dog;
    private String str;
    @Autowired
    public void setDog(Dog dog) {
        this.dog = dog;
    }
```

### 4.1.2 赋值Null

1. @Nullable

   这个对象的值可以为null

2. @Autowired

当`required = false`时就说明这个对象的值可以为null（beans.xml中不定义这个对象就为null），否则就报错

* 使用方法

```java
@Autowired(required = false)
```
* User
```java
//beans.xml中为定义cat但不会报错(<bean id="cat" class="zimo.Cat"/>)
@Autowired(required = false)
private Cat cat;
//beans.xml中为定义dog此时会报错
@Autowired
private Dog dog;
//str的值可以为Null
@Nullable
private String str;
```

### 4.1.3 @Qualifier

如果@Autowired自动装配的环境比较复杂，@Autowired无法通过类型和名称进行装配的时候可以使用**@Qualifier(value="IDName")**协助@Autowired

* beans.xml

```xml
<bean id="cat0" class="zimo.Cat"></bean>
<bean id="dog0" class="zimo.Dog"></bean>
<bean id="cat1" class="zimo.Cat"></bean>
<bean id="dog1" class="zimo.Dog"></bean>
<bean id="cat2" class="zimo.Cat"></bean>
<bean id="dog2" class="zimo.Dog"></bean>
<bean id="user" class="zimo.User"/>
```

* User

```java
    @Autowired
    @Qualifier(value = "cat1")
    private Cat cat;
    @Autowired
    private Dog dog;
    @Nullable
    private String str;
```



### 4.1.4 @Resource

**java的原生注解**，使用它也能进行自动装配，他和@Autowired一样，会**自动进行名称与类型进行查找装配**，同样的当名称与类型都不符合的时候会报错(名称与类型的要求见3.4.3和3.4.5)  

它有个属性**name**和@Qualifier一样可以辅助它进行查找自动装配

* beans.xml

  见4.1.3的beans.xml

* User

  ```java
      @Resource(name="cat1")
      private Cat cat;
      @Resource(name="dog1")
      private Dog dog;
  
      private String str;
  ```

### **4.1.5 总结**

@Resource(name="bean的名字")

* 为属性自动注入(引用)值，前提是这个自动装配的属性在（Spring）IOC容器中存在，且符合名字byName，或类型符合byType
* 当不符合时候可以使用这个注解的name属性进行辅助

@Autowired

* 为属性自动注入(引用)值，前提是这个自动装配的属性在（Spring）IOC容器中存在，且符合名字byName，或类型符合byType

@Qualifier(value="bean的名字")

* 可以辅助@Autowired进行自动装配（当@Autowired无法独立进行装配的时候）

@Nullable

* 允许属性为Null

## 4.2 Spring注解开发

在Spring4之后，要使用注解开发，必须保证aop的包导入了(没有导入刷新一下maven)。使用context约束，增加注解的支持！

* xml模板

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:aop="http://www.springframework.org/schema/aop"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        https://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/context
        https://www.springframework.org/schema/context/spring-context.xsd
        http://www.springframework.org/schema/aop
        https://www.springframework.org/schema/context/spring-aop.xsd">

        <!-- 开启注解支持 -->
    <context:annotation-config/>
    <!--指定要扫描的包，这个包下的注解就会生效-->
    <context:component-scan base-package="包路径"/>

</beans>
```



* com.zimo.pojo.User

```java
//等价于相当于配置文件中 <bean id="user" class="当前注解的类"/>
//组件  @Component
@Component
//设置对象作用域
@Scope("prototype")
public class User {
    //注入值
    @Value("子墨")
    public String name;
}
```



* Test

```java

@Test
public void test(){   
    ApplicationContext applicationContext =   new ClassPathXmlApplicationContext("beans.xml");
    User user = (User) applicationContext.getBean("user");
    System.out.println(user.name);
}
```



### 4.2 测试代码

### 1.bean用注解实现

我们之前都是使用 bean 的标签进行bean注入，但是实际开发中，我们一般都会使用注解！
1、配置扫描哪些包下的注解(只有被扫描到的注解才会生效)

```xml
    <!-- 开启注解支持 -->
    <context:annotation-config/>
    <!--指定要扫描的包，这个包下的注解就会生效-->
    <context:component-scan base-package="com.zimo.pojo"/>
```
2、在指定包下编写类，增加注解@Component("user")

```java
	@Component("user")
  // 相当于配置文件中 <bean id="user" class="当前注解的类"/>
    public class User {   
        public String name = "子墨";
    }
```
### 2.属性如何注入

```java
//组件
//此注解等价于 bean
@Component
public class User 
{    
    //Value注解相当于xml中bean的赋值操作    
    @Value("子墨")    
    public String name;
}
```

### 3.衍生的注解

@Component 有几个衍生注解，我们会按照mvc三层架构分层

- dao【@Repository】
- sevice【@Service】
- controller【@Controller】

这四个注解的功能是一样的，都只是代表将某个类注册到Sprng中，装配Bean。至于用对应的注解注册的话，应该是要生活更有仪式感（滑稽）

### 4.自动装配

上面写了

### 5.作用域

```java
@Component
//设置为单例注解等等
@Scope("singleton")
public class User {    
    //Value注解相当于xml中bean的赋值操作    
    @Value("子墨")    
    public String name;
}
```



### 6.小结

xml与注解

- xml 更加万能，适用于任何场合！维护方便！但配置稍显麻烦
- 注解 不是自己类用不了，维护相对复杂！

xml 与 注解 的最佳实践

- xml用来管理bean
- 注解只负责完成属性的注入
- 在使用过程中，只需要注意一个问题：必须让注解生效，需要开启注解的支持

* 注解

>```java
>//将对象注册到spring容器
>@Component、@Repository、@Service、@Controller
>//为属性注入值，放在属性上或set方法上
>@Value("值")  
>//设置作用域
>@Scope("singleton")
>```

# 5. SpringConfig

完全使用注解，不适用xml进行配置,进行开发



### 5. 测试代码

* com.zimo.config.ZimoConfig2	配置类

与下同

* com.zimo.config.ZimoConfig 	配置类

```java
//注册到Spring容器中，因为他本身就是一个@Component.
//@Configuration 代表他是一个配置类
@Configuration
//要被扫描的包
@ComponentScan("com.zimo.pojo")
//引入另一个config类
@Import(ZimoConfig2.class)
public class ZimoConfig {
    //注册一个Bean
    //这个方法名字相当于bean标签中的id
    //返回值相当于，bean标签中的class属性
    @Bean
    public User getUser(){
        return  new User();
    }
}
```

* com.zimo.pojo.User

```java
//注册类到Spring容器中
@Component
public class User {
    private String name;

    public String getName() {
        return name;
    }

    @Value("子墨")//注入值
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                '}';
    }
}
```



* Test

```java
public class MyTest {
    @Test
    public void test(){
        //加载配置类
        AnnotationConfigApplicationContext config = new AnnotationConfigApplicationContext(ZimoConfig.class);
        User user = (User) config.getBean("user");
        User getUser = (User) config.getBean("getUser");
        System.out.println(getUser);
        System.out.println(user);


    }
}
```



### 5. 小结

* 配置类

```java
用在类上面
//注册到Spring容器中，因为他本身就是一个@Component.
//@Configuration 代表他是一个配置类
@Configuration
//要被扫描的包
@ComponentScan("com.zimo.pojo")
//引入另一个config类	
@Import(ZimoConfig2.class)

用在方法上
//注册一个Bean
//这个方法名字相当于bean标签中的id
//返回值相当于，bean标签中的class属性
@Bean
```



* bean

```java
用在类上面
// 相当于配置文件中 <bean id="user" class="当前注解的类"/>
@Component("user")
//设置作用域
@Scope("singleton")
  
用在属性或set方法上
//Value注解相当于xml中bean的赋值操作    
@Value("子墨")  
```



# 6 注解总结

注解:
 ## 配置类注解

```java
用在类上面
//注册到Spring容器中，因为他本身就是一个@Component.
//@Configuration 代表他是一个配置类
@Configuration
//要被扫描的包
@ComponentScan("com.zimo.pojo")
//引入另一个config类	
@Import(ZimoConfig2.class)

用在方法上
//注册一个Bean
//这个方法名字相当于bean标签中的id
//返回值相当于，bean标签中的class属性
@Bean
```



 ## bean类

```java
/*
		用在类上面
*/
// 相当于配置文件中 <bean id="user" class="当前注解的类"/>
@Component("user")
//设置作用域
@Scope("singleton")



/*
		用在属性或set方法上
*/
//Value注解相当于xml中bean的赋值操作    
@Value("子墨")  
    
//为属性自动注入(引用)值，前提是这个自动装配的属性在（Spring）IOC容器中存在，且符合名字byName，或类型符合byType当不符合时候可以使用这个注解的name属性进行辅助
@Resource(name="bean的名字")
    
//为属性自动注入(引用)值，前提是这个自动装配的属性在（Spring）IOC容器中存在，且符合名字byName，或类型符合byType
@Autowired
    
//可以辅助@Autowired进行自动装配（当@Autowired无法独立进行装配的时候）
@Qualifier(value="bean的名字")
    
//允许属性为Null
@Nullable

```







# 7 AOP

AOP：面向切面编程，他的底层基本使用(动态)代理来实现的

![avatar](图片\1.png)

### 7 导包

```xml
        <dependency>
            <groupId>org.aspectj</groupId>
            <artifactId>aspectjweaver</artifactId>
            <version>1.9.4</version>
        </dependency>
```



### 7 测试代码

* beans.xml	(注意引入AOP约束)

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:aop="http://www.springframework.org/schema/aop"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans.xsd
       http://www.springframework.org/schema/aop
       http://www.springframework.org/schema/aop/spring-aop.xsd">


    <!-- 注册bean -->
    <bean id="userService" class="com.zimo.service.UserServerImp"/>
    <bean id="log" class="com.zimo.log.Log"/>
    <bean id="afterLog" class="com.zimo.log.AfterLog"/>


</beans>
```

* com.zimo.service.UserService 	接口

```java
public interface UserService {
    public void add();
    public void delete();
    public void update();
    public void select();

}
```

* com.zimo.service.UserServiceImp	实现类

```java
public class UserServerImp implements UserService {
    @Override
    public void add() {
        System.out.println("增加了一个用户");
    }

    @Override
    public void delete() {
        System.out.println("删除了一个用户");
    }

    @Override
    public void update() {
        System.out.println("更新了一个用户");
    }

    @Override
    public void select() {
        System.out.println("查询了一个用户");
    }
}
```

* com.zimo.log.log		前置(切入)日志类

```java
    /**
     *
     * method:  要执行的目标对象的方法
     * objects: 方法参数
     * o：       目标对象
     *
     */
    @Override
    public void before(Method method, Object[] objects, Object o) throws Throwable {
        System.out.println(o.getClass().getName()+"类下"+method.getName()+"方法被执行了");
    }
```



* com.zimo.log.AfterLog	后置(切入)日志类

```java
public class AfterLog implements AfterReturningAdvice {
     /**
     *
     * method:  要执行的目标对象的方法
     * objects: 方法参数
     * o：       目标对象
     *
     */
    @Override
    public void afterReturning(Object o, Method method, Object[] objects, Object o1) throws Throwable {
        System.out.println(o1+"类执行了"+method.getName()+"方法，方法返回了："+o);
    }
}
```



* Test

```java
@Test
    public void test(){
        ClassPathXmlApplicationContext c = new ClassPathXmlApplicationContext("beans.xml");
        //注意要用接口接受
        UserService userService = c.getBean("userService", UserService.class);
        userService.add();
    }
```





### 7.1 方法一: 	使用Spring API接口

* beans.xml

```xml
    
    <aop:config>
        <!--  切入点：experience：表达式，execution（* 要执行的位置 全限定名.方法名字 *(参数[..为任意参数])） -->
        <aop:pointcut id="pointcut" expression="execution(* com.zimo.service.UserServerImp.*(..))"/>

        <!-- 执行环绕增加 -->
        <!-- 要看方法在切入点之前执行还是切入点之后执行要看log类实现的是谁的接口 -->
        <aop:advisor advice-ref="log" pointcut-ref="pointcut"/>
        <aop:advisor advice-ref="afterLog" pointcut-ref="pointcut"/>
    </aop:config>
```

* 执行测试方法

结果：

```java
com.zimo.service.UserServerImp类下add方法被执行了
增加了一个用户
com.zimo.service.UserServerImp@2473d930类执行了add方法，方法返回了：null
```



### 7.2 方法二： 自定义来实现AOP

* beans.xml

```xml
   
	<!-- 注册bean -->
	<bean id="diy" class="com.zimo.diy.Diy"/>
	<!-- 配置aop -->
    <aop:config>
        <!-- 自定义切面，ref 要引用的类 -->
        <aop:aspect ref="diy">
            <!-- 切入点 -->
            <aop:pointcut id="pointcut" expression="execution(* com.zimo.service.UserServerImp.*(..))"/>

            <!-- 通知 -->
            <!-- 在 pointcut 切入点之前执行 diy类的before -->
            <aop:before method="before" pointcut-ref="pointcut"/>
            <!-- 在 pointcut 切入点之后执行 diy类的after -->
            <aop:after method="after" pointcut-ref="pointcut"/>

        </aop:aspect>
    </aop:config>
```

* com.zimo.diy.Diy

```java
public class Diy {
        public void before(){
            System.out.println("========方法执行前========");
        }

        public void after(){
            System.out.println("========方法执行后========");
        }
}
```



测试结果

```java
========方法执行前========
增加了一个用户
========方法执行后========
```





### 方法三：AOP注解

* beans.xml 	开启注解支持。注册log bean

```xml
<bean id="aoonLog" class="com.zimo.log.AoonLog"/>

<!-- 开启AOP注解支持 -->
<!--
proxy-target-class="false"
使用JDK实现(默认false)还是cglib实现(true)
-->
    <aop:aspectj-autoproxy proxy-target-class="false"></aop:aspectj-autoproxy>
```

* com.zimo.log.AoonLog

```java
@Aspect
public class AoonLog {
    final  String execution="execution(* com.zimo.service.UserServerImp.*(..))";
    //目标方法执行前执行
    //注解里面的表达式必须是常亮
    @Before(execution)
    public void before(){
        System.out.println("【LOG】   Before执行");
    }

    //目标方法执行后执行
    //注解里面的表达式必须是常亮
    @After("execution(* com.zimo.service.UserServerImp.*(..))")
    public void after(){
        System.out.println("【LOG】   after执行");
    }

    //环绕
    @Around(execution)
    public void Around(ProceedingJoinPoint jp) throws Throwable {
        System.out.println("【LOG】   环绕前");

        //执行方法,使用这个注解之后必须执行写这条语句，否则目标方法不会执行
        Object proceed = jp.proceed();

        System.out.println("【LOG】   环绕后");
    }
}
```

* ProceedingJoinPoint的方法

```java
jp.getSignature();//获得签名(这个方法的信息)
jp.getSourceLocation();//获得资源的位置
jp.getTarget();//获得目标对象
jp.getThis();//获取代理对象本身
jp.toLongString();//获得这个方法的信息
jp.getArgs();//获取连接点方法运行时的入参列表
```

* 测试结果：

```java
【LOG】   环绕前
【LOG】   Before执行
删除了一个用户
【LOG】   环绕后
【LOG】   after执行
```





注解：  

>@Aspect:作用是把当前类标识为一个切面供容器读取
>
>@Pointcut：Pointcut是植入Advice的触发条件。每个Pointcut的定义包括2部分，一是表达式，二是方法签名。方法签名必须是 public及void型。可以将Pointcut中的方法看作是一个被Advice引用的助记符，因为表达式不直观，因此我们可以通过方法签名的方式为 此表达式命名。因此Pointcut中的方法只需要方法签名，而不需要在方法体内编写实际代码。
>
>@Around：环绕增强，相当于MethodInterceptor
>
>@AfterReturning：后置增强，相当于AfterReturningAdvice，方法正常退出时执行
>
>@Before：标识一个前置增强方法，相当于BeforeAdvice的功能，相似功能的还有
>
>@AfterThrowing：异常抛出增强，相当于ThrowsAdvice
>
>@After: final增强，不管是抛出异常或者正常退出都会执行





### execution切点函数(看懂上面的execution公式就行)

execution函数用于匹配方法执行的连接点，语法为：

execution(方法修饰符(可选)  返回类型  方法名  参数  异常模式(可选)) 



参数部分允许使用通配符：

\*  匹配任意字符，但只能匹配一个元素

.. 匹配任意字符，可以匹配任意多个元素，表示类时，必须和*联合使用

\+  必须跟在类名后面，如Horseman+，表示类本身和继承或扩展指定类的所有类



示例：`"execution(* chop(..))"`解读为：

方法修饰符  无

返回类型    *匹配任意数量字符，表示返回类型不限

方法名      chop表示匹配名称为chop的方法

参数        (..)表示匹配任意数量和类型的输入参数

异常模式    不限



示例：`"execution(void com.zimo.service.UserServerImp.*(..))"`

返回类型    void

方法名      com.zimo.service.UserServerImp下的所以方法

参数        (..)表示匹配任意数量和类型的输入参数





- 更多示例：

void chop(String,int)

匹配目标类任意修饰符方法、返回void、方法名chop、带有一个String和一个int型参数的方法



public void chop(*)

匹配目标类public修饰、返回void、方法名chop、带有一个任意类型参数的方法



public String *o*(..)

 匹配目标类public修饰、返回String类型、方法名中带有一个o字符、带有任意数量任意类型参数的方法



public void *o*(String,..)

 匹配目标类public修饰、返回void、方法名中带有一个o字符、带有任意数量任意类型参数，但第一个参数必须有且为String型的方法



也可以指定类：

public void examples.chap03.Horseman.*(..)



匹配Horseman的public修饰、返回void、不限方法名、带有任意数量任意类型参数的方法

public void examples.chap03.*man.*(..)



匹配以man结尾的类中public修饰、返回void、不限方法名、带有任意数量任意类型参数的方法

指定包：

public void examples.chap03.*.chop(..)



匹配examples.chap03包下所有类中public修饰、返回void、方法名chop、带有任意数量任意类型参数的方法

public void examples..*.chop(..)



匹配examples.包下和所有子包中的类中public修饰、返回void、方法名chop、带有任意数量任意类型参数的方法
可以用这些表达式替换StorageAdvisor中的代码并观察效果



####  更多切点函数

除了execution()，Spring中还支持其他多个函数，这里列出名称和简单介绍，以方便根据需要进行更详细的查询



-  @annotation()

表示标注了指定注解的目标类方法

例如 @annotation(org.springframework.transaction.annotation.Transactional) 表示标注了@Transactional的方法



-  args()

通过目标类方法的参数类型指定切点

例如 args(String) 表示有且仅有一个String型参数的方法



-  @args()

通过目标类参数的对象类型是否标注了指定注解指定切点

如 @args(org.springframework.stereotype.Service) 表示有且仅有一个标注了@Service的类参数的方法



-  within()

通过类名指定点

如 with(examples.chap03.Horseman) 表示Horseman的所有方法



-  target()

通过类名指定，同时包含所有子类

如 target(examples.chap03.Horseman)  且Elephantman extends Horseman，则两个类的所有方法都匹配



-  @within()

匹配标注了指定注解的类及其所有子类

如 @within(org.springframework.stereotype.Service) 给Horseman加上@Service标注，则Horseman和Elephantman 的所有方法都匹配



-  @target()

所有标注了指定注解的类

如 @target(org.springframework.stereotype.Service) 表示所有标注了@Service的类的所有方法



- this()

大部分时候和target()相同，区别是this是在运行时生成代理类后，才判断代理类与指定的对象类型是否匹配



####  逻辑运算符



表达式可由多个切点函数通过逻辑运算组成



- &&

与操作，求交集，也可以写成and

例如 execution(* chop(..)) && target(Horseman)  表示Horseman及其子类的chop方法



- ||

或操作，求并集，也可以写成or

例如 execution(* chop(..)) || args(String)  表示名称为chop的方法或者有一个String型参数的方法



-  !

非操作，求反集，也可以写成not

例如 execution(* chop(..)) and !args(String) 表示名称为chop的方法但是不能是只有一个String型参数的方法



# 8 Spring整合Mybatis

MyBatis-Spring： http://mybatis.org/spring/zh/index.html



### 1. 导包

- pom.xml

```xml
<dependency>
  <groupId>org.mybatis</groupId>
  <artifactId>mybatis-spring</artifactId>
  <version>2.0.6</version>
</dependency>
```



```xml
<!--spring的数据库驱动，没有他就无法操作数据库-->
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-jdbc</artifactId>
    <version>5.1.9.RELEASE</version>
</dependency>
```



### 2. 测试代码

#### resource

- spring-dao.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:aop="http://www.springframework.org/schema/aop"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans.xsd
       http://www.springframework.org/schema/aop
       http://www.springframework.org/schema/aop/spring-aop.xsd">

        <!--配置数据源-->
        <!--DataSource:使用spring的数据源来替换mybatis的数据源配置 -->
    <bean id="dataSource" class="org.springframework.jdbc.datasource.DriverManagerDataSource">
        <!--  驱动  -->
        <property name="driverClassName" value="com.mysql.jdbc.Driver"/>
        <!--  数据库地址  -->
        <property name="url" value="jdbc:mysql://localhost:3306/mybatis?useSSL=true&amp;useUnicode=true&amp;characterEncoding=UTF-8"/>
        <!--  用户名  -->
        <property name="username" value="root"/>
        <!--  密码  -->
        <property name="password" value="123"/>
    </bean>


    <!--sqlSessionFactory-->
    <bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
        <property name="dataSource" ref="dataSource" />
        <!--绑定mybatis的配置文件-->
        <!--绑定mybatis的核心配置文件，这样一些设置就可以在mybatis的核心配置文件里面进行(简洁)，当然在这里也可以。-->
        <property name="configLocation" value="classpath:mybatis-config.xml"/>
        <!--绑定Mapper.xml-->
        <property name="mapperLocations" value="classpath:com/zimo/mapper/*.xml"/>
    </bean>


    <!--SqlSessionTemplate就是我们使用的sqlSession-->
    <bean id="sqlSession" class="org.mybatis.spring.SqlSessionTemplate">
        <!--在SqlSessionTemplate 中没有set方法所以只能用构造器注入sqlSessionFactory-->
        <constructor-arg index="0" ref="sqlSessionFactory"/>
    </bean>


    <!--  userMapper：这个可以放在applicationContext.xml 里面，applicationContext.xml通过<import> 来引用spring-dao.xml  -->
    <bean id="userMapper" class="com.zimo.mapper.UserMapperImpl">
        <property name="sqlSession" ref="sqlSession"/>
    </bean>
</beans>
```



- mybatis-config.xml

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">

<!--核心配置文件-->
<configuration>

<!--  设置  -->
<settings>
    <!--&lt;!&ndash;设置日志工厂并设置其值&ndash;&gt;-->
    <!--<setting name="logImpl" value="LOG4J"/>-->
    <!--是否开启驼峰命名自动映射，即从经典数据库列名 A_COLUMN 映射到经典 Java 属性名 aColumn。-->
    <setting name="mapUnderscoreToCamelCase" value="true"/>
</settings>

<!--设置别名 -->
<typeAliases>
    <package name="com.zimo.pojo"/>
</typeAliases>

</configuration>
```



#### com.zimo.mapper

- UserMapper.xml

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.zimo.mapper.UserMapper">
    <select id="getUserList" resultType="user">
        select  * from mybatis.user;
    </select>
</mapper>
```



- UserMapper.class

```java
public interface UserMapper {
    List<User> getUserList();
}
```



- UserMapperImpl.class

```java
//我们多了一个实现类来实现UserMapper里的方法，这样更符合面向对象。
// 这个实现类去操作数据库，去做以前mybatis做的事情
public class UserMapperImpl  implements  UserMapper{
    //在原来我们所以的操作，都使用sqlSession来执行，现在动用SqlSessionTemplate
    private SqlSessionTemplate sqlSession;

    public void setSqlSession(SqlSessionTemplate sqlSession) {
        this.sqlSession = sqlSession;
    }

    @Override
    public List<User> getUserList() {
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        return mapper.getUserList();
    }
}
```



#### com.zimo.pojo

- User.class

```java
 //这里的属性对应数据库表中文档每一个字段
    private int id;
    private String name;
    private String pwd;
// 省略set get等方法
```



#### Test

```java
@Test
public void test(){
    ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring-dao.xml");
    UserMapper userMapper = applicationContext.getBean("userMapper", UserMapper.class);

    List<User> userList = userMapper.getUserList();
    for (User user : userList) {
        System.out.println(user);
    }
}
```



### 3. 步骤总结

1. 导包
2. 配置数据源
3. bean注册sqlSessionFactory
4. 通过注入sqlSessionFactory来注册SqlSessionTemplate
5. SqlSessionTemplate和mybatis的SqlSession一毛一样
6. 创建mybatis的核心配置文件mybatis-config.xml
7. 创建User，UserMapper接口，UserMapper.xml配置文件
8. UserMapperImpl实现UserMapper接口 ，并留下一个set方法方便被注入SqlSessionTemplate
9. bean注册UserMapperImpl
10. 测试：
    1. 创建applicationContext
    2. getBean
    3. 调用方法
    4. 遍历结果





# 9 Spring 事务



### 编程式事务

### 声明式事务

把他们丢入spring-dao.xml里面

```xml
 <!--配置声明式事务-->
    <!--要开启 Spring 的事务处理功能，在 Spring 的配置文件中创建一个 DataSourceTransactionManager 对象：-->
    <bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
       <property name="dataSource" ref="dataSource"/>
    </bean>


    <!--结合AOP实现事务的织入-->
    <!--配置事务通知-->
    <tx:advice id="txAdvice" transaction-manager="transactionManager">
    <!--  给那些方法配置事务  -->
    <!--   配置事务的传播特性  propagation="REQUIRED"（默认）-->
        <tx:attributes>
            <tx:method name="add" />
            <tx:method name="delete"/>
            <tx:method name="*" propagation="REQUIRED"/>
        </tx:attributes>
    </tx:advice>

    <!--配置事务的切入-->
    <aop:config>
        <aop:pointcut id="txPointcut" expression="execution(* com.zimo.mapper.*.*(..))"/>
        <aop:advisor advice-ref="txAdvice" pointcut-ref="txPointcut"/>
    </aop:config>
```



- com.zimo.mapper.UserMapper.xml

```xml
<!--  插入一个用户  -->
<!--  对象中的属性可以直接取出来  (#{id},#{name},#{pwd})-->
<insert id="add"    parameterType="com.zimo.pojo.User">
    insert into mybatis.user (id,name,pwd) value (#{id},#{name},#{pwd})
</insert>
```



- com.zimo.mapper.UserMapper

```java
//mapper
public interface UserMapper {
    List<User> getUserList();

    int add(User user);
}
```



- com.zimo.mapper.UserMapperImpl

```java
    @Override
    public int add(User user) {
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        mapper.add(user);
        int a=1/0;
        return 0;
    }
```





- Test

```java
@Test
public void test2(){
    ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring-dao.xml");
    UserMapper userMapper = applicationContext.getBean("userMapper", UserMapper.class);

    User user = new User(9, "uur", "pwdafad7a5");
    int add = userMapper.add(user);
    System.out.println(add);

}
```

测试结果：

开启事务插入失败，关闭事务插入成功





