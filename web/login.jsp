<%@ page import="simpleboard.common.MessageFlasher" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <title>로그인</title>
</head>
<body>
<% MessageFlasher.renderFlashedMessage(request, response); %>
<h2>로그인</h2>
<form method="post">
    <input type="text" name="userId" placeholder="아이디" required><br>
    <input type="password" name="userPw" placeholder="비밀번호" required><br>
    <input type="submit" value="로그인"><br>
</form>
</body>
</html>