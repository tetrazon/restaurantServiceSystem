<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Айвен
  Date: 17.12.2019
  Time: 20:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form method="post" action="/add_employee">
    <label>Name</label><br>
    <input required="" name="name"/><br><br>
    <label>Surname</label><br>
    <input required="" name="surname" /><br><br>
    <label>email</label><br>
    <input required="" type="email" name="email" /><br><br>
    <label>password</label><br>
    <input required="" type="password" name="password" /><br><br>
    <label>position</label><br>
    <input type="radio" name="position" value="COOK"> cook<Br>
    <input type="radio" name="position" value="WAITER"> waiter<Br>
    <input type="radio" name="position" value="MANAGER"> manager<Br>
    <input type="submit" value="add employee" />
</form>
<button onclick="location.href='/manage_options.jsp'">go to manage options</button><br /><br>
<%@ include file="/WEB-INF/logout.html" %>

</body>
</html>
