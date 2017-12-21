<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!doctype html>
<html>
<head>
  <jsp:include page="common/head.jsp"/>
  <title>로그인</title>
</head>
<body>
<jsp:include page="common/layoutTop.jsp"/>
<h2>로그인</h2>
<form method="post">
  <div class="form-group">
    <label for="userId">아이디</label>
    <input type="text" class="form-control" name="userId" id="userId" placeholder="아이디" required>
  </div>
  <div class="form-group">
    <label for="userPw">비밀번호</label>
    <input type="password" class="form-control" name="userPw" id="userPw" placeholder="비밀번호" required>
  </div>
  <button class="btn btn-primary" type="submit">로그인</button>
</form>
<jsp:include page="common/layoutBottom.jsp"/>
</body>
</html>