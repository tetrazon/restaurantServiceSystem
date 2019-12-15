<%--
  Created by IntelliJ IDEA.
  User: Айвен
  Date: 09.12.2019
  Time: 17:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Welcome!</title>
</head>
<body>
<%--${requestScope.get("name")}--%>
Welcome, ${name}. Now try to login.
<br> To replenish the deposit, contact the manager.
<button onclick="location.href='/home?action=login'">login</button><br /><br>


</body>
</html>
