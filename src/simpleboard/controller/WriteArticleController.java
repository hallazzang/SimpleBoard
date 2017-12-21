package simpleboard.controller;

import simpleboard.common.DatabaseException;
import simpleboard.common.MessageFlasher;
import simpleboard.common.Redirecter;
import simpleboard.dao.ArticleDAO;
import simpleboard.dao.BoardDAO;
import simpleboard.dao.FileDAO;
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
            Redirecter.comeBy("/login", request, response);
            return;
        }

        String boardId = request.getParameter("boardId");

        try {
            if (!BoardDAO.exists(boardId)) {
                MessageFlasher.flash(request, "존재하지 않는 게시판입니다.", "error");
                Redirecter.forcedRedirect("/board", request, response);
                return;
            }
        } catch (DatabaseException e) {
            MessageFlasher.flash(request, e.getMessage(), "exception");
            Redirecter.refresh(request, response);
            return;
        }

        FileDAO.ProcessResult req;

        try {
            req = FileDAO.processMultipartRequest(request);
        } catch (DatabaseException e) {
            MessageFlasher.flash(request, e.getMessage(), "exception");
            Redirecter.refresh(request, response);
            return;
        }

        if (req == null) {
            MessageFlasher.flash(request, "잘못된 요청입니다.", "error");
            Redirecter.refresh(request, response);
            return;
        }

        String articleTitle = req.getReq().getParameter("articleTitle");
        String articleContent = req.getReq().getParameter("articleContent");
        Integer fileId = req.getFile() != null ? req.getFile().getId() : null;

        if (articleTitle.equals("") || articleContent.equals("")) {
            MessageFlasher.flash(request, "입력 필드가 비어 있습니다.", "error");
            Redirecter.refresh(request, response);
            return;
        }

        try {
            ArticleDAO.putArticle(boardId, articleTitle, articleContent, user.getId(), fileId);
        } catch (DatabaseException e) {
            MessageFlasher.flash(request, e.getMessage(), "exception");
            Redirecter.refresh(request, response);
            return;
        }

        Redirecter.redirect("/board?boardId=" + boardId, request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getSession().getAttribute("user") == null) {
            MessageFlasher.flash(request, "로그인이 필요합니다.", "error");
            Redirecter.comeBy("/login", request, response);
            return;
        }
        getServletContext().getRequestDispatcher("/WEB-INF/writeArticle.jsp").forward(request, response);
    }
}
