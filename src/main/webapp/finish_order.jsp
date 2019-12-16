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
enjoy your meal!


<table>
    ordered items:
        <tr><th>Name</th><th>price</th><th>quantity</th></tr>
        <c:forEach var="item" items="${dishesInOrderList}">
        <tr><td>${item.dish.name}</td>
            <td>${item.dish.price}</td>
            <td>${item.quantity}</td>
            <td></td>
        </tr>
        </c:forEach>
</table>

</body>
</html>
