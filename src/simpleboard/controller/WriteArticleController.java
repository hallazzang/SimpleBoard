package simpleboard.controller;

import simpleboard.common.DatabaseException;
import simpleboard.common.MessageFlasher;
import simpleboard.dao.ArticleDAO;
import simpleboard.dto.UserDTO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "WriteArticleController", urlPatterns = {"/write"})
public class WriteArticleController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");

        UserDTO user = (UserDTO)request.getSession().getAttribute("user");

        if (user == null) {
            MessageFlasher.flash(request, "로그인이 필요합니다", "error");
            response.sendRedirect("/login");
            return;
        }

        String boardId = (String)request.getParameter("boardId");
        String articleTitle = (String)request.getParameter("articleTitle");
        String articleContent = (String)request.getParameter("articleContent");

        if (articleTitle == "" || articleContent == "") {
            MessageFlasher.flash(request, "입력 필드가 비어 있습니다.", "error");
            response.sendRedirect("/write");
            return;
        }

        try {
            ArticleDAO.putArticle("test", articleTitle, articleContent, user.getId());
        } catch (DatabaseException e) {
            MessageFlasher.flash(request, e.getMessage(), "exception");
            response.sendRedirect("/write");
            return;
        }

        response.sendRedirect("/board?boardId=" + boardId);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getSession().getAttribute("user") == null) {
            MessageFlasher.flash(request, "로그인이 필요합니다.", "error");
            response.sendRedirect("/login");
            return;
        }
        getServletContext().getRequestDispatcher("/WEB-INF/writeArticle.jsp").forward(request, response);
    }
}
