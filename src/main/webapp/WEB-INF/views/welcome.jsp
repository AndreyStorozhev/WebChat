<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Welcome</title>
</head>
<body>
    <h2>Привет, ${SPRING_SECURITY_LAST_USERNAME}</h2>
    <p>Список всех пользовтелей</p>
    <c:forEach items="${loginInUsers}" var="user">
        <tr>
            <td>${user.id}</td>
            <td>${user.login}</td>
            <td>${user.name}</td>
            <td>${user.lastName}</td>
            <td>${user.email}</td>
        </tr>
    </c:forEach>
<a href="<c:url value="/chat"/>">go to chat</a>
</body>
</html>
