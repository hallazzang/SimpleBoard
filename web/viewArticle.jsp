<%@ page import="simpleboard.common.MessageFlasher" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!doctype html>
<html>
<head>
  <meta charset="utf-8">
  <title></title>
</head>
<body>
<% MessageFlasher.renderFlashedMessage(request, response); %>
<table>
  <tbody>
  <tr>
    <th>제목</th>
    <td colspan="3">${article.title}</td>
  </tr>
  <tr>
    <th>작성자</th>
    <td>${article.author.name}</td>
    <th>작성 일시</th>
    <td><fmt:formatDate value="${article.modifiedAt}" pattern="YYYY-MM-dd HH:mm:ss"/></td>
  </tr>
  </tbody>
</table>
</body>
</html>
