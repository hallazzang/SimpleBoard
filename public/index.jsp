<%@ page import="simpleboard.common.MessageFlasher" %>
<%@ page import="simpleboard.dto.UserDTO" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <title></title>
</head>
<body>
    <% MessageFlasher.renderFlashedMessage(request, response); %>
    <c:choose>
        <c:when test="${user != null}">
            반갑습니다, ${user.name}님
        </c:when>
        <c:otherwise>
            <a href="/login">로그인</a> 해주세요.
        </c:otherwise>
    </c:choose>
</body>
</html>
