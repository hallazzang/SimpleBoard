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

@WebServlet(name = "LoginController", urlPatterns = {"/login"})
public class LoginController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");

        String userId = request.getParameter("userId");
        String userPw = request.getParameter("userPw");

        if (userId.equals("") || userPw.equals("")) {
            MessageFlasher.flash(request, "입력 필드가 비어 있습니다.", "error");
            response.sendRedirect("/login");
            return;
        }

        UserDTO user;

        try {
            user = UserDAO.getUser(userId);
        } catch (DatabaseException e) {
            MessageFlasher.flash(request, e.getMessage(), "exception");
            response.sendRedirect("/login");
            return;
        }

        if (user != null && user.checkPw(userPw)) {
            request.getSession().setAttribute("user", user);
            response.sendRedirect("/");
        } else {
            MessageFlasher.flash(request, "아이디 혹은 비밀번호가 틀렸습니다.", "error");
            response.sendRedirect("/login");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getSession().getAttribute("user") != null) {
            MessageFlasher.flash(request, "이미 로그인 되어 있습니다.", "error");
            response.sendRedirect("/");
            return;
        }
        getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
    }
}
