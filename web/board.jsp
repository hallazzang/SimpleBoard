<%@ page import="simpleboard.common.MessageFlasher" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <title>게시판</title>
</head>
<body>
    <% MessageFlasher.renderFlashedMessage(request, response); %>
    <h2>게시판</h2>
    <a href="${"/write?boardId=".concat(boardId)}">글 쓰기</a>
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
                    <td><a href="${"/view?articleId=".concat(article.id)}">${article.title}</a></td>
                    <td>${article.author.name}</td>
                    <td>
                        <c:choose>
                            <c:when test="${article.file == null}">
                                -
                            </c:when>
                            <c:otherwise>
                                <a href="${"/download?fileId=".concat(article.file.id)}">
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
