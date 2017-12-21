package simpleboard.controller;

import simpleboard.common.DatabaseException;
import simpleboard.common.MessageFlasher;
import simpleboard.dao.UserDAO;
import simpleboard.dto.UserDTO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "LoginController", urlPatterns = {"/login.do"})
public class LoginController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");

        String userId = req.getParameter("userId");
        String userPw = req.getParameter("userPw");

        if (userId == "" || userPw == "") {
            MessageFlasher.flash(req, "입력 필드가 비어 있습니다.", "error");
            resp.sendRedirect("/login.do");
            return;
        }

        UserDTO user;

        try {
            user = UserDAO.getUser(userId);
        } catch (DatabaseException e) {
            MessageFlasher.flash(req, e.getMessage(), "error");
            resp.sendRedirect("/login.do");
            return;
        }

        if (user != null && user.checkPw(userPw)) {
            req.getSession().setAttribute("user", user);
            resp.sendRedirect("/index.jsp");
            return;
        } else {
            MessageFlasher.flash(req, "아이디 혹은 비밀번호가 틀렸습니다.", "error");
            resp.sendRedirect("/login.do");
            return;
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(req, resp);
    }
}
