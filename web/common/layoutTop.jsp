<%@ page import="simpleboard.common.MessageFlasher" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<nav class="navbar navbar-expand-md navbar-light bg-light">
  <div class="container">
    <a class="navbar-brand" href="<c:url value="/"/>">MAL</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
            aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
      <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarSupportedContent">
      <ul class="navbar-nav mr-auto">
        <li class="nav-item">
          <a class="nav-link" href="<c:url value="/"/>">홈</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="<c:url value="/intro"/>">연구실 소개</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="<c:url value="/board"/>">게시판</a>
        </li>
      </ul>
      <c:choose>
        <c:when test="${sessionScope.user == null}">
          <form action="<c:url value="/login?next=/board"/>" method="post" class="form-inline">
            <input class="form-control mr-sm-2" type="text" name="userId" size="14" placeholder="아이디" aria-label="ID"
                   required>
            <input class="form-control mr-sm-2" type="text" name="userPw" size="14" placeholder="비밀번호"
                   aria-label="Password" required>
            <button class="btn btn-outline-primary my-2 my-sm-0 mr-2" type="submit">로그인</button>
            <a class="btn btn-outline-success my-2 my-sm-0" href="<c:url value="/register"/>">회원가입</a>
          </form>
        </c:when>
        <c:otherwise>
          ${user.name}
          (<a href="<c:url value="/logout"/>">로그아웃</a>)
        </c:otherwise>
      </c:choose>
    </div>
  </div>
</nav>
<div class="container mt-3">
<%= MessageFlasher.renderFlashedMessage(request) %>