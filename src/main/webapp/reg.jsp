
<%--
  Created by IntelliJ IDEA.
  User: Айвен
  Date: 03.12.2019
  Time: 8:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>add client</title>
</head>
<body>
<h3>New client</h3>

<form method="post" action="/register">
    <label>Name</label><br>
    <input name="name"/><br><br>
    <label>Surname</label><br>
    <input name="surname" /><br><br>
    <label>email</label><br>
    <input name="email" /><br><br>
    <label>password</label><br>
    <input name="password" /><br><br>
    <input type="submit" value="register" />
</form>
</body>
</html>
