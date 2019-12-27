<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@page isELIgnored="false"%>
<html>
<head>
    <title>managing</title>
</head>
<body>
<p>employees:</p>
<p></p>
<table>
    <tr><th>employee name </th><th>mail </th><th>position</th><th></th><th></th></tr>
    <c:forEach var="employee" items="${employees}">

        <tr>
            <td>${employee.name}</td>
            <td>${employee.email}</td>
            <td>${employee.position}</td>
            <td>
                <form method="post" action='<c:url value="/employees_managing" />' style="display:inline;">
                    <input type="hidden" name="employeeId" value="${employee.id}">
                    <input type="hidden" name="delete" value="y">
                    <input type="submit" value="delete employee">
                </form>
            </td>

        </tr>
    </c:forEach>
</table>

<button onclick="location.href='/add_employee'">add new employee</button><br /><br>
<button onclick="location.href='/manage_options.jsp'">go to manage options</button><br /><br>
<%@ include file="/WEB-INF/logout.html" %>
</body>
</html>
