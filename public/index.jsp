<%@ page import="simpleboard.common.MessageFlasher" %>
<%@ page import="simpleboard.dto.UserDTO" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <title></title>
</head>
<body>
    <% MessageFlasher.renderFlashedMessage(request, response); %>
    <%
        UserDTO user = (UserDTO)request.getSession().getAttribute("user");
        if (user != null) {
            out.println("<p>반갑습니다, " + user.getName() + "님!</p>");
        } else { %>
            <a href="/login.do">로그인</a>해주세요.
        <% }
    %>
</body>
</html>
