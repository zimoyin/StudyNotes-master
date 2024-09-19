一道题要a在for循环中自增到7（只能定义两个变量），但在写是因为忽略a++和++a的区别所以错误百出  

>a++：先执行表达式再自增  
>++a：先自增再执行表达式  

代码：
```java
　　　　int a=1;
		for(int i=0;i<=5;i++){
			a=a++;
		}
		System.out.println(a);
		
```
输出：

```java
1
```  

造成这个结果的是a先赋值后再自增  
代码分解一下：
```java
　　　　int a=1;
		int l=1;
		for(int i=0;i<=5;i++){
			l=a++;
			a=l;
		}
		System.out.println(a);//1
		

```
在（`l=a++`）这代码中先执行l=a，在执行a++而此时l=1，a=2  
随后执行`a=l`，也就是a=1（如果将`a=l`注释掉的话a也就不会被赋值1）  

```java
　　　　int a=1;
		int l=1;
		for(int i=0;i<=5;i++){
			l=a++;
		}
		System.out.println(a);//7
		
```

所以在写代码时要注意是要先执行表达式再自增还是先自增再执行表达式。
所以正确代码为：  
```java
　　　　int a=1;
		for(int i=0;i<=5;i++){
			a=++a;
		}
		System.out.println(a);//7
		
```
