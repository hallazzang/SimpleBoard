package simpleboard.controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

@WebServlet(name = "FileDownloadHandler")
public class FileDownloadHandler extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        final String saveDirectory = "/usr/local/var/simpleboard/uploads/";

        String fileName = request.getParameter("fileName");
        File file = new File(saveDirectory + fileName);

        String mimeType = getServletContext().getMimeType(file.toString());
        if (mimeType != null) {
            response.setContentType(mimeType);
        } else {
            response.setContentType("application/octet-stream");
        }


    }
}
