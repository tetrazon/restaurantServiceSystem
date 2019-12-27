<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@page isELIgnored="false"%>
<html>
<head>
    <title>managing</title>
</head>
<body>
<p>clients:</p>
<p></p>
<table>
    <tr><th>client name </th><th>mail </th><th>deposit</th><th></th><th></th></tr>
    <c:forEach var="employee" items="${clients}">

        <tr>
            <td>${employee.name}</td>
            <td>${employee.email}</td>
            <td>${employee.deposit}</td>
            <td>
                <form method="post" action='<c:url value="/clients_managing" />'style="display:inline;">
                    <input name="new_deposit" type="number">
                    <input type="hidden" name="clientId" value="${employee.id}">
                    <input type="submit" value="set new deposit">
                </form>
            </td>
            <td>
                <form method="post" action='<c:url value="/clients_managing" />' style="display:inline;">
                    <input type="hidden" name="clientId" value="${employee.id}">
                    <input type="hidden" name="delete" value="y">
                    <input type="submit" value="delete client">
                </form>
            </td>

        </tr>
    </c:forEach>
</table>
<button onclick="location.href='/manage_options.jsp'">go to manage options</button><br /><br>
<%@ include file="/WEB-INF/logout.html" %>
</body>
</html>
