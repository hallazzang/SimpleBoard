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
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");

        String userId = request.getParameter("userId");
        String userPw = request.getParameter("userPw");

        if (userId == "" || userPw == "") {
            MessageFlasher.flash(request, "입력 필드가 비어 있습니다.", "error");
            response.sendRedirect("/login.jsp");
            return;
        }

        UserDTO user;

        try {
            user = UserDAO.getInstance().getUser(userId);
        } catch (DatabaseException e) {
            MessageFlasher.flash(request, e.getMessage(), "error");
            response.sendRedirect("/login.jsp");
            return;
        }

        if (user != null && user.checkPw(userPw)) {
            request.getSession().setAttribute("user", user);
            response.sendRedirect("/index.jsp");
            return;
        } else {
            MessageFlasher.flash(request, "아이디 혹은 비밀번호가 틀렸습니다.", "error");
            response.sendRedirect("/login.jsp");
            return;
        }
    }
}
