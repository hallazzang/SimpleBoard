package simpleboard.controller;

import simpleboard.common.DatabaseException;
import simpleboard.common.MessageFlasher;
import simpleboard.dao.ArticleDAO;
import simpleboard.dto.ArticleDTO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "ViewArticleController", urlPatterns = "/view")
public class ViewArticleController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ArticleDTO article;
        int articleId;

        try {
            String articleIdString = request.getParameter("articleId");

            articleId = Integer.parseInt(articleIdString);
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        try {
            article = ArticleDAO.getArticle(articleId);
        } catch (DatabaseException e) {
            MessageFlasher.flash(request, e.getMessage(), "exception");
            response.sendRedirect("/");
            return;
        }

        request.setAttribute("article", article);
        getServletContext().getRequestDispatcher("/WEB-INF/viewArticle.jsp").forward(request, response);
    }
}
