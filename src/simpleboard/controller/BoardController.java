package simpleboard.controller;

import simpleboard.common.DatabaseException;
import simpleboard.common.MessageFlasher;
import simpleboard.dao.ArticleDAO;
import simpleboard.dao.BoardDAO;
import simpleboard.dto.ArticleDTO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "BoardController", urlPatterns = {"/board"})
public class BoardController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String boardId = request.getParameter("boardId");
        int page;
        List<ArticleDTO> articles;

        try {
            String pageString = request.getParameter("page");

            if (pageString == null) {
                page = 1;
            } else {
                page = Integer.parseInt(pageString);

                if (page < 1) {
                    throw new NumberFormatException();
                }
            }
        } catch (NumberFormatException e) {
            MessageFlasher.flash(request, "존재하지 않는 페이지입니다.", "error");
            response.sendRedirect("/board?boardId=" + boardId);
            return;
        }

        try {
            if (!BoardDAO.exists(boardId)) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }

            articles = ArticleDAO.getArticles(boardId, page, ArticleDAO.SortType.DESCENDING);
        } catch (DatabaseException e) {
            MessageFlasher.flash(request, e.getMessage(), "exception");
            response.sendRedirect("/");
            return;
        }

        request.setAttribute("boardId", boardId);
        request.setAttribute("page", page);
        request.setAttribute("articles", articles);
        getServletContext().getRequestDispatcher("/WEB-INF/board.jsp").forward(request, response);
    }
}
