
# 前言

同时学习java和python或其他语言的小朋友，肯定发现了一个问题，java实体类有冗长的setter和getter，但是Python就不用，那么造成这个现象的原因是什么呢？   为什么java不像Python直接把属性设置为public就完事了？

不能只随波逐流别人写就跟着写要通过现象看本质。
# 一、面向对象的封装理念
这应该是最多人给你的答案，封装类的内部细节提供对应的方法，有时候可以对属性赋值的设置进行限制，如下通过setter方法，指定了年龄输入范围只在0-120
```java
class Person{
    private int age;
    public void setAge(int age){
        if(age <= 0 || age > 120){
            throw new RuntimeException("年龄范围不合法");
        }
        this.age = age;
    }
    public int getAge(){
        return this.age;
    }
}

``` 
或者部分属性读写权限分离 只提供读（getter）或写（setter）其中一种权限。如下情况外部对于Person类的age只有读的权限没有写的权限，如果直接把age设置成public无法做到这么精确的控制。
```
class Person{
    private int age;
    public int getAge(){
        return age;
    }
}
```
但这个理由其实并不太站得住脚，

绝大多数我们java实体类的setter、getter方法都是使用idea默认生成的，并没有对其做任何修改。所有起不到什么精确控制读写功能。
同为面向对象的Python、js就没听过有人写setter和getter。当然你要是想在python写setter也没人会拦着你，但是主流环境下没人这么做肯定是有他的原因。
阿里巴巴开发手册明确规定不能修改默认生成的setter、getter。那么我们通过修改这两个方法来精确控制属性值读写就属于不规范操作。
# 二、面向对象的多态
Java中方法是可以多态而属性是不能多态的，直接上代码
```Java
父类：

class Animal{
    public String name = "动物";

    public String getName(){
        return name;
    }
}
子类：

class Cat extends Animal{
    public String name = "猫咪";

    public String getName(){
        return name;
    }  
}
//父类引用指向子类对象
Animal animal = new Cat();
System.out.println(animal.name);
System.out.println(animal.getName());
```
```
输出结果为：

动物
猫咪
```
通过这个例子就不难知道为什么java需要setter、getter而Python就不需要了。

不仅仅是Python，几乎所有动态语言都没有setter和getter

其实有些人说这是动态类型语言优雅简洁的优点，实际上是动态类型没有什么“父类引用指向之类对象”的概念，他们就算想通过setter、getter来实现多态也做不到啊。索性就不要咯。

# 三、java的解决方案：Lombok
这么冗长的setter和getter其实并非无解，java有一个很实用的插件Lombok，使用方法如下

在pom.xml引入依赖
```
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <version>1.18.8</version>
</dependency>
/**
 *加入这两个注解自动生成setter、getter
*/
@Setter
@Getter
class Person{
    private String name;
}
```
# 总结
java的setter、getter设计初衷一小部分原因是为了封装内部细节，但这占比非常小。

真正导致实体类setter、getter不可或缺的根本原因 是java中属性不能多态，只有方法可以多态。

动态类型语言因为没有类型系统，没有‘完整’的多态特性，所以并不需要。

# [源文章地址](https://www.cnblogs.com/gxhunter/p/10847838.html)
# 作者树荫下的天空

