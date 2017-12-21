<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
  int currentPage = (int) request.getAttribute("currentPage");
  int totalCount = (int) request.getAttribute("totalCount");
  int itemsPerPage = (int) request.getAttribute("itemsPerPage");
  int pageNavCount = (int) request.getAttribute("pageNavCount");

  int pages = (int) Math.ceil((double) totalCount / itemsPerPage);
  int left = ((int) Math.ceil((double) currentPage / pageNavCount) - 1) * pageNavCount + 1;
  int right = Math.min(left + pageNavCount - 1, pages);

  request.setAttribute("pages", pages);
  request.setAttribute("left", left);
  request.setAttribute("right", right);
%>
<nav aria-label="Page navigation example">
  <ul class="pagination justify-content-center">
    <c:choose>
      <c:when test="${left > 1}">
        <li class="page-item">
          <a class="page-link" href="<c:url value="/board?boardId=${boardId}&page=${left - 1}"/>" aria-label="Previous">
            <span aria-hidden="true">&laquo;</span>
            <span class="sr-only">Previous</span>
          </a>
        </li>
      </c:when>
      <c:otherwise>
        <li class="page-item disabled">
          <a class="page-link" href="#" aria-label="Previous">
            <span aria-hidden="true">&laquo;</span>
            <span class="sr-only">Previous</span>
          </a>
        </li>
      </c:otherwise>
    </c:choose>
    <c:forEach var="p" begin="${left}" end="${right}">
      <c:choose>
        <c:when test="${p == currentPage}">
          <li class="page-item"><a class="page-link">${p}</a></li>
        </c:when>
        <c:otherwise>
          <li class="page-item"><a class="page-link"
                                   href="<c:url value="/board?boardId=${boardId}&page=${p}"/>">${p}</a></li>
        </c:otherwise>
      </c:choose>
    </c:forEach>
    <c:choose>
      <c:when test="${right < pages}">
        <li class="page-item">
          <a class="page-link" href="<c:url value="/board?boardId=${boardId}&page=${right + 1}"/>" aria-label="Next">
            <span aria-hidden="true">&raquo;</span>
            <span class="sr-only">Next</span>
          </a>
        </li>
      </c:when>
      <c:otherwise>
        <li class="page-item disabled">
          <a class="page-link" href="#" aria-label="Next">
            <span aria-hidden="true">&raquo;</span>
            <span class="sr-only">Next</span>
          </a>
        </li>
      </c:otherwise>
    </c:choose>
  </ul>
</nav>