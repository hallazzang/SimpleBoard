<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!doctype html>
<html>
<head>
  <jsp:include page="common/head.jsp"/>
  <style>
    th, td {
      word-break: keep-all;
      white-space: nowrap;
      text-align: center;
    }
  </style>
  <title>게시판</title>
</head>
<body>
<jsp:include page="common/layoutTop.jsp"/>
<div class="row">
  <div class="col-md-2">
    <ul class="nav nav-pills flex-column">
      <c:forEach var="board" items="${boards}">
        <li class="nav-item">
          <c:choose>
            <c:when test="${board.id == boardId}">
              <a class="nav-link active" href="<c:url value="/board?boardId=${board.id}"/>">
                  ${board.name}
              </a>
            </c:when>
            <c:otherwise>
              <a class="nav-link" href="<c:url value="/board?boardId=${board.id}"/>">
                  ${board.name}
              </a>
            </c:otherwise>
          </c:choose>
        </li>
      </c:forEach>
    </ul>
  </div>
  <div class="col-md-10">
    <a class="btn btn-primary mb-3 float-right" href="<c:url value="/write?boardId=${boardId}"/>">글 쓰기</a>
    <div class="table-responsive">
      <table class="table">
        <thead class="thead-light">
        <tr>
          <th class="w-25" scope="col">제목</th>
          <th class="w-25" scope="col">작성자</th>
          <th class="w-25" scope="col">첨부파일</th>
          <th class="w-25" scope="col">수정 일시</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="article" items="${articles}">
          <tr>
            <td>
              <a href="<c:url value="/view?articleId=${article.id}"/>">
                  ${article.title}
              </a>
            </td>
            <td>${article.author.name}</td>
            <td>
              <c:choose>
                <c:when test="${article.file == null}">
                  -
                </c:when>
                <c:otherwise>
                  <a href="<c:url value="/download?fileId=${article.file.id}"/>">
                    다운로드
                  </a>
                </c:otherwise>
              </c:choose>
            </td>
            <td><fmt:formatDate value="${article.modifiedAt}" pattern="YYYY-MM-dd HH:mm:ss"/></td>
          </tr>
        </c:forEach>
        </tbody>
      </table>
    </div>
    <jsp:include page="common/pagination.jsp"/>
  </div>
</div>
<jsp:include page="common/layoutBottom.jsp"/>
</body>
</html>
