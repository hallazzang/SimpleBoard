package simpleboard.controller;

import simpleboard.common.DatabaseException;
import simpleboard.common.MessageFlasher;
import simpleboard.common.Redirecter;
import simpleboard.dao.FileDAO;
import simpleboard.dto.FileDTO;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@WebServlet(name = "FileDownloadController", urlPatterns = "/download")
public class FileDownloadController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String password = request.getParameter("password");

        if (password == null || !password.equals("2101")) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        int fileId;

        try {
            fileId = Integer.parseInt(request.getParameter("fileId"));
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        FileDTO file;

        try {
            file = FileDAO.getFile(fileId);
        } catch (DatabaseException e) {
            MessageFlasher.flash(request, e.getMessage(), "exception");
            Redirecter.forcedRedirect("/board", request, response);
            return;
        }

        if (file == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        String mimeType = getServletContext().getMimeType(file.getPath());
        if (mimeType != null) {
            response.setContentType(mimeType);
        } else {
            response.setContentType("application/octet-stream");
        }

        response.setHeader("Content-Disposition", "attachment; filename=\"" + file.getName() + "\";");

        File rawFile = new File(file.getPath());
        FileInputStream inputStream = new FileInputStream(rawFile);
        ServletOutputStream outputStream = response.getOutputStream();

        org.apache.commons.io.IOUtils.copy(inputStream, outputStream);

        outputStream.flush();
        outputStream.close();
        inputStream.close();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int fileId;

        try {
            fileId = Integer.parseInt(request.getParameter("fileId"));
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        request.setAttribute("fileId", fileId);
        getServletContext().getRequestDispatcher("/WEB-INF/download.jsp").forward(request, response);
    }
}
