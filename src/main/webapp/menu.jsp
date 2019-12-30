<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@page isELIgnored="false"%>
<html>
<head>
    <title>managing</title>
</head>
<body>
<p>menu:</p>
<p></p>
<table>
    <tr><th>food name </th><th>price$</th><th> description </th> <th>food category</th><th>new price & description            </th><th></th><th></th></tr>
    <c:forEach var="dish" items="${dishes}">

        <tr>
            <td>${dish.name}</td>
            <td>${dish.price}</td>
            <td>${dish.description}</td>
            <td>${dish.foodCategory}</td>
            <td>
                <form method="post" action='<c:url value="/menu" />'style="display:inline;">
                    <input required="" name="newPrice" type="number" step="0.01" min="0.1" max="1000" />
                    <input required="" name="newDescription" />
                    <input type="hidden" name="dishId" value="${dish.id}">
<%--                    dish instead or order!!--%>
                    <input type="submit" value="change price & descr">
                </form>
            </td>
            <td>
                <form method="post" action='<c:url value="/menu" />' style="display:inline;">
                    <input type="hidden" name="dishId" value="${dish.id}">
                    <input type="hidden" name="delete" value="y">
                    <input type="submit" value="delete item">
                </form>
            </td>

        </tr>
    </c:forEach>
</table>
<button onclick="location.href='/add_menu_item.jsp'">add new menu item</button><br /><br>
<button onclick="location.href='/manage_options.jsp'">go to manage options</button><br /><br>
<%@ include file="/WEB-INF/logout.html" %>
</body>
</html>
