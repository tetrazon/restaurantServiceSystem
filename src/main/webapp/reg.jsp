
<%--
  Created by IntelliJ IDEA.
  User: Айвен
  Date: 03.12.2019
  Time: 8:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page isELIgnored="false"%>
<html>
<head>
    <title>registration, singing in</title>
</head>
<body>
<h3>New client? pls register</h3>

<form method="post" action="/register">
    <label>Name</label><br>
    <input required="" name="name"/><br><br>
    <label>Surname</label><br>
    <input required="" name="surname" /><br><br>
    <label>email</label><br>
    <input required="" type="email" name="email" /><br><br>
    <label>password</label><br>
    <input required="" type="password" name="password" /><br><br>
    <input type="submit" value="register" />
</form>
<h3>Already registered? log in:</h3>

<p></p>
<% if(request.getParameter("login")!= null && request.getParameter("login").equals("y")){ %>
<a>Welcome, <%=request.getParameter("name")%>. Now try to login.</a>
<br> To replenish the deposit, please contact the manager.
<p></p>
<% } %>
<p></p>
<form method="post" action="/login">

    <label>email</label><br>
    <input required="" type="email" name="email" /><br><br>
    <label>password</label><br>
    <input required="" type="password" name="password" /><br><br>
    <input type="submit" value="log in" />
</form>

<% if(request.getParameter("loginError")!= null && request.getParameter("loginError").equals("y")){ %>
<a>login or password is incorrect</a>
<% } %>

</body>
</html>
