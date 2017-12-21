<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!doctype html>
<html>
<head>
  <jsp:include page="common/head.jsp"/>
  <title>게시글 작성</title>
</head>
<body>
<jsp:include page="common/layoutTop.jsp"/>
<form method="post" enctype="multipart/form-data">
  <div class="form-group">
    <label for="articleTitle">제목</label>
    <input type="text" class="form-control" name="articleTitle" id="articleTitle" placeholder="제목" required>
  </div>
  <div class="form-group">
    <label for="articleContent">내용</label>
    <textarea class="form-control" name="articleContent" id="articleContent" placeholder="내용" required></textarea>
  </div>
  <div class="form-group">
    <label for="file">첨부파일</label>
    <input type="file" class="form-control-file" name="file" id="file">
  </div>
  <button class="btn btn-primary" type="submit">작성하기</button>
</form>
<jsp:include page="common/layoutBottom.jsp"/>
</body>
</html>
