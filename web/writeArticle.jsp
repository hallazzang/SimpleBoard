<%@ page import="simpleboard.common.MessageFlasher" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!doctype html>
<html>
<head>
  <meta charset="utf-8">
  <title></title>
</head>
<body>
<% MessageFlasher.renderFlashedMessage(request, response); %>
<form method="post" enctype="multipart/form-data">
  <input type="text" name="articleTitle" placeholder="제목" required><br>
  <textarea name="articleContent" placeholder="내용" required></textarea><br>
  <input type="file" name="file">
  <input type="submit" value="작성">
</form>
</body>
</html>
