# 无法创建

8. 添加Tomcat依赖:没有他就无法创建Servlet

![image-20210119143919623](.\image\10.png)



9. 

![image-20210119144120136](.\image\56.png)

![image-20210119144219800](.\image\image-20210119144219800.png)





# 无法访问



这是IDEA自动生成的

```java
@WebServlet(name = "sub")
public class Sub extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println(56);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println(56);
    }
}
```



将**@WebServlet(name = "sub")**改一下改成@WebServlet("/sub")

```java
@WebServlet("/sub")
public class Sub extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println(56);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println(56);
    }
}
```

