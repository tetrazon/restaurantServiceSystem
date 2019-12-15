<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="entity.food.Dish" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: Айвен
  Date: 07.12.2019
  Time: 14:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@page isELIgnored="false"%>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%--create order, change, finish, process--%>
<h2>Dishes list</h2>

<table>
    <form method="post" action='<c:url value="/create_order" />' >
    <tr><th>Name</th><th>desc</th><th>cat</th><th>price $</th><th></th></tr>
    <c:forEach var="dish" items="${dishes}">

        <tr><td>${dish.name}</td>
            <td>${dish.description}</td>
            <td>${dish.foodCategory}</td>
            <td>${dish.price}</td>
            <td>
                <label>quantity</label><br>
                <input name="quantity"  type="number" min="1" max="10"/><br>
                <input type="hidden" name="dishId" value="${dish.id}">
                <input type="hidden" name="dishPrice" value="${dish.price}">
            </td>
            <td>
            </td></tr>
    </c:forEach>

</table>
<p></p>
<p> choose table:</p>
<c:forEach var="table" items="${tables}">

    <p><input name="tableId" type="radio" value="${table.id}"> Table ${table.id}, ${table.seats} seats</p>
</c:forEach>
<input type="submit" value="create order">
</form>
<% if(request.getParameter("error")!= null && request.getParameter("error").equals("money")){ %>
<a>sorry, you have not enough money.Try to order <%=request.getParameter("need")%>$ less</a>
<% }%>



<%if(request.getParameter("error")!= null && request.getParameter("error").equals("table")){%>
<a>It seems you have not chosen table or at that moment all tables are reserved. </a>
<a>Try to reserve available table or wait some time and make order again </a>
<% }%>
<%if(request.getParameter("error")!= null && request.getParameter("error").equals("order")){%>
<a>sorry, some db error has been occurred. try make order again</a>
<% }%>

</body>
</html>
