<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2021/2/10
  Time: 15:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>$Title$</title>
  </head>
  <body>
      <%--from表单向/hello提交数据，/hello处理数据并转发给test.jsp进行展示--%>
      <form action="/day01/hello" method="post">
        <input type="text" name="method">
        <input type="submit">
      </form>
  </body>
</html>
