<%@ page import="simpleboard.common.MessageFlasher" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <title>회원가입</title>
</head>
<body>
    <%
        if (request.getSession().getAttribute("user") != null) {
            MessageFlasher.flash(request, "이미 로그인 되어 있습니다.", "error");
            response.sendRedirect("/index.jsp");
            return;
        }
    %>
    <% MessageFlasher.renderFlashedMessage(request, response); %>
    <h2>회원가입</h2>
    <form action="register.do" method="post">
        <input type="text" name="userId" placeholder="아이디"><br>
        <input type="text" name="userName" placeholder="이름"><br>
        <input type="password" name="userPw" placeholder="비밀번호"><br>
        <input type="password" id="pw-confirm" placeholder="비밀번호 확인"><br>
        <input type="submit" value="회원가입">
    </form>
</body>
</html>