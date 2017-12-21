package simpleboard.controller;

import simpleboard.common.DatabaseException;
import simpleboard.common.MessageFlasher;
import simpleboard.dao.UserDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "RegisterController", urlPatterns = {"/register.do"})
public class RegisterController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");

        String userId = req.getParameter("userId");
        String userName = req.getParameter("userName");
        String userPw = req.getParameter("userPw");
        String role = "user";

        if (userId == "" || userName == "" || userPw == "") {
            MessageFlasher.flash(req, "입력 필드가 비어 있습니다.", "error");
            resp.sendRedirect("/register.do");
            return;
        }

        try {
            if (UserDAO.idExists(userId)) {
                MessageFlasher.flash(req, "이미 존재하는 아이디입니다.", "error");
                resp.sendRedirect("/register.do");
                return;
            }

            UserDAO.register(userId, userName, userPw, role);
        } catch (DatabaseException e) {
            MessageFlasher.flash(req, e.getMessage(), "error");
            resp.sendRedirect("/register.do");
            return;
        }

        MessageFlasher.flash(req, "회원가입이 완료되었습니다.", "success");
        resp.sendRedirect("/index.jsp");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/WEB-INF/register.jsp").forward(req, resp);
    }
}
