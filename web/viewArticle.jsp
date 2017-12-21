<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!doctype html>
<html>
<head>
  <jsp:include page="common/head.jsp"/>
  <style>
    th, td {
      word-break: keep-all;
      white-space: nowrap;
    }

    .multiline {
      white-space: pre-wrap;
    }
  </style>
  <title>게시글 보기</title>
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
    <%--<a class="btn btn-primary mb-3 float-right" href="<c:url value="/write?boardId=${boardId}"/>">글 쓰기</a>--%>
    <div class="table-responsive">
      <table class="table table-bordered">
        <tbody class="thead-light">
        <tr>
          <th scope="col">제목</th>
          <td colspan="3">${article.title}</td>
        </tr>
        <tr>
          <th scope="col">작성자</th>
          <td>${article.author.name}</td>
          <th scope="col">작성 일시</th>
          <td><fmt:formatDate value="${article.modifiedAt}" pattern="YYYY-MM-dd HH:mm:ss"/></td>
        </tr>
        <tr>
          <th scope="col">첨부파일</th>
          <td colspan="3">
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
        </tr>
        <tr>
          <th scope="col">내용</th>
          <td class="multiline" colspan="3">${fn:escapeXml(article.content)}</td>
        </tr>
        </tbody>
      </table>
    </div>
  </div>
</div>
<jsp:include page="common/layoutBottom.jsp"/>
</body>
</html>
