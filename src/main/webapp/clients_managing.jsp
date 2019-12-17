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
    <c:forEach var="client" items="${clients}">

        <tr>
            <td>${client.name}</td>
            <td>${client.email}</td>
            <td>${client.deposit}</td>
            <td>
                <form method="post" action='<c:url value="/clients_managing" />'style="display:inline;">
                    <input name="new_deposit" type="number">
                    <input type="hidden" name="clientId" value="${client.id}">
                    <input type="submit" value="set new deposit">
                </form>
            </td>
            <td>
                <form method="post" action='<c:url value="/clients_managing" />' style="display:inline;">
                    <input type="hidden" name="clientId" value="${client.id}">
                    <input type="hidden" name="delete" value="y">
                    <input type="submit" value="delete client">
                </form>
            </td>

        </tr>
    </c:forEach>
</table>

</body>
</html>
