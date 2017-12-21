<%@ page import="simpleboard.common.MessageFlasher" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <title>로그인</title>
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
    <h2>로그인</h2>
    <form action="login.do" method="post">
        <input type="text" name="userId" placeholder="아이디"><br>
        <input type="password" name="userPw" placeholder="비밀번호"><br>
        <input type="submit" value="로그인"><br>
    </form>
</body>
</html>