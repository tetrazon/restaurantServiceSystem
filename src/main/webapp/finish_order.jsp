<%@ page import="entity.food.DishesInOrder" %>
<%@ page import="java.util.List" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Айвен
  Date: 15.12.2019
  Time: 9:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<p>your order:</p>
<table>

        <tr><th>Name</th><th>price</th><th>quantity</th></tr>
        <c:forEach var="item" items="${dishesInOrderList}">
        <tr><td>${item.dish.name}</td>
            <td>${item.dish.price}</td>
            <td>${item.quantity}</td>
            <td></td>
        </tr>
        </c:forEach>
</table>
<p> total sum: ${invoice} $</p>
<button onclick="location.href='/create_order'">make new order</button><br /><br>
<button onclick="location.href='/order_history'">order history</button><br /><br>
<%@ include file="/WEB-INF/logout.html" %>
</body>
</html>
