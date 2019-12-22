<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>add menu item</title>
</head>
<body>
<p>new menu item:</p>
<form method="post" action="/add_menu_item">
    <label>Name</label><br>
    <input required="" name="dishName"/><br><br>
    <label>price</label><br>
    <input required="" name="dishPrice" type="number" step="0.01" min="0.1" max="1000"/><br><br>
    <label>description</label><br>
    <input required="" name="dishDescription" /><br><br>
    <label>food category</label><br>
    <input type="radio" name="foodCategory" value="FIRST_COURSE" checked>FIRST_COURSE<Br>
    <input type="radio" name="foodCategory" value="SECOND_COURSE">SECOND_COURSE<Br>
    <input type="radio" name="foodCategory" value="DRINK">DRINK<Br>
    <input type="radio" name="foodCategory" value="DESSERT">DESSERT<Br>
    <input type="submit" value="add menu item" />
</form>
<button onclick="location.href='/managing'">go to manage options</button><br /><br>
<%@ include file="/WEB-INF/logout.html" %>

</body>
</html>
