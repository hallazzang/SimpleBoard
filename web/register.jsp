<%@ page import="simpleboard.common.MessageFlasher" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <title>회원가입</title>
</head>
<body>
    <% MessageFlasher.renderFlashedMessage(request, response); %>
    <h2>회원가입</h2>
    <form method="post">
        <input type="text" name="userId" placeholder="아이디" required><br>
        <input type="text" name="userName" placeholder="이름" required><br>
        <input type="password" name="userPw" placeholder="비밀번호" required><br>
        <input type="password" id="pw-confirm" placeholder="비밀번호 확인" required><br>
        <input type="submit" value="회원가입">
    </form>
</body>
</html>