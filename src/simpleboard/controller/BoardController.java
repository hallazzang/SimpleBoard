package simpleboard.controller;

import simpleboard.common.DatabaseException;
import simpleboard.common.MessageFlasher;
import simpleboard.common.Redirecter;
import simpleboard.dao.ArticleDAO;
import simpleboard.dao.BoardDAO;
import simpleboard.dto.ArticleDTO;
import simpleboard.dto.BoardDTO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "BoardController", urlPatterns = {"/board"})
public class BoardController extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String boardId = request.getParameter("boardId");
        int page, totalCount;
        List<ArticleDTO> articles;
        List<BoardDTO> boards;

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
            Redirecter.forcedRedirect("/board?boardId=" + boardId, request, response);
            return;
        }

        try {
            boards = BoardDAO.getBoards();

            if (boards == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }

            if (boardId == null || boardId.equals("")) {
                Redirecter.forcedRedirect("/board?boardId=" + boards.get(0).getId(), request, response);
                return;
            }

            if (!BoardDAO.exists(boardId)) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
            articles = ArticleDAO.getArticles(boardId, page, ArticleDAO.SortType.DESCENDING);
            if (articles == null && page > 1) {
                MessageFlasher.flash(request, "존재하지 않는 페이지입니다.", "error");
                Redirecter.forcedRedirect("/board?boardId=" + boardId, request, response);
                return;
            }
            totalCount = ArticleDAO.getArticleCount(boardId);
        } catch (DatabaseException e) {
            MessageFlasher.flash(request, e.getMessage(), "exception");
            Redirecter.forcedRedirect("/board", request, response);
            return;
        }

        request.setAttribute("boardId", boardId);
        request.setAttribute("boards", boards);
        request.setAttribute("articles", articles);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalCount", totalCount);
        request.setAttribute("itemsPerPage", 10);
        request.setAttribute("pageNavCount", 5);
        getServletContext().getRequestDispatcher("/WEB-INF/board.jsp").forward(request, response);
    }
}
