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
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");

        String userId = request.getParameter("userId");
        String userName = request.getParameter("userName");
        String userPw = request.getParameter("userPw");
        String role = "user";

        if (userId == "" || userName == "" || userPw == "") {
            MessageFlasher.flash(request, "입력 필드가 비어 있습니다.", "error");
            response.sendRedirect("/register.jsp");
            return;
        }

        try {
            if (UserDAO.getInstance().idExists(userId)) {
                MessageFlasher.flash(request, "이미 존재하는 아이디입니다.", "error");
                response.sendRedirect("/register.jsp");
                return;
            }

            UserDAO.getInstance().register(userId, userName, userPw, role);
        } catch (DatabaseException e) {
            MessageFlasher.flash(request, e.getMessage(), "error");
            response.sendRedirect("/register.jsp");
            return;
        }

        MessageFlasher.flash(request, "회원가입이 완료되었습니다.", "success");
        response.sendRedirect("/index.jsp");
    }
}
