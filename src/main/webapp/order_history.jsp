<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<table>

        <tr><th>Date of order </th><th>invoice, $</th><th></th></tr>
        <c:forEach var="order" items="${orders}">

        <tr><td>
            <jsp:useBean id="myDate" class="java.util.Date"/>
            <c:set target="${myDate}" property="time" value="${order.timestamp}"/>
            ${myDate}
                </td>
            <td>${order.invoice}</td>
            <td>
                <form method="get" action='<c:url value="/order_details" />' >
                <input type="hidden" name="orderId" value="${order.id}">
                <input type="submit" value="details">
                </form>
            </td>
            <td>
            </td></tr>
        </c:forEach>

</table>
<button onclick="location.href='/create_order'">make new order</button><br /><br>
<%@ include file="/WEB-INF/logout.html" %>
</body>
</html>
