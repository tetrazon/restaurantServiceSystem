<%--
  Created by IntelliJ IDEA.
  User: Айвен
  Date: 09.12.2019
  Time: 12:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>managing</title>
</head>
<body>
<h3>manager log in:</h3>
<form method="post" action="/manager_login">

    <label>email</label><br>
    <input required="" type="email" name="email" /><br><br>
    <label>password</label><br>
    <input required="" type="password" name="password" /><br><br>
    <input type="submit" value="log in" />
</form>
<button onclick="location.href='/index.jsp'">home page</button><br /><br>
</body>
</html>
