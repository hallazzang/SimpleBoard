<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!doctype html>
<html>
<head>
  <jsp:include page="common/head.jsp"/>
  <title>첨부파일 다운로드</title>
</head>
<body>
<jsp:include page="common/layoutTop.jsp"/>
<form method="post" class="mt-3">
  <input type="hidden" name="fileId" value="${fileId}">
  <div class="form-group">
    <label for="password">다운로드 암호</label>
    <input type="password" class="form-control" name="password" id="password" aria-describedby="password"
           placeholder="비밀번호" required>
    <small id="passwordHelp" class="form-text text-muted">첨부파일 다운로드 시 필요한 암호입니다.</small>
  </div>
  <button type="submit" class="btn btn-primary">다운로드</button>
</form>
<jsp:include page="common/layoutBottom.jsp"/>
</body>
</html>
