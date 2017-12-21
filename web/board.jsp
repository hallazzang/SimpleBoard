<%@ page import="simpleboard.common.MessageFlasher" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/normalize.css">
    <style>

    </style>
    <title>게시판</title>
</head>
<body>
<% MessageFlasher.renderFlashedMessage(request, response); %>
<h2>게시판</h2>
<a href="${"/write?boardId=".concat(boardId)}">글 쓰기</a>
<h4>게시판 목록</h4>
<ul>
    <c:forEach var="board" items="${boards}">
        <li>${board.name}</li>
    </c:forEach>
</ul>
<h4>글 목록</h4>
<table>
    <thead>
    <tr>
        <th>제목</th>
        <th>작성자</th>
        <th>첨부파일</th>
        <th>수정 일시</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="article" items="${articles}">
        <tr>
            <c:url var="articleURL" value="/view">
                <c:param name="articleId" value="${article.id}"/>
            </c:url>
            <td><a href="${articleURL}">${article.title}</a></td>
            <td>${article.author.name}</td>
            <td>
                <c:choose>
                    <c:when test="${article.file == null}">
                        -
                    </c:when>
                    <c:otherwise>
                        <a href="${pageContext.request.contextPath}/download?fileId=${article.file.id}">
                                ${article.file.name}
                        </a>
                    </c:otherwise>
                </c:choose>
            </td>
            <td><fmt:formatDate value="${article.modifiedAt}" pattern="YYYY-MM-dd HH:mm:ss"/></td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>
