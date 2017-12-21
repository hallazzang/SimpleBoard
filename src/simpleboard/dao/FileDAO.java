package simpleboard.dao;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import simpleboard.common.DatabaseException;
import simpleboard.dto.FileDTO;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class FileDAO extends BaseDAO {
    private static final String saveDirectory = "/usr/local/var/simpleboard/uploads";
    private static final int maxSize = 1024 * 1024 * 30;

    private FileDAO() {
    }

    public static ProcessResult processMultipartRequest(HttpServletRequest request)
            throws DatabaseException {
        ProcessResult result;
        MultipartRequest req;

        (new File(saveDirectory)).mkdirs();

        try {
            req = new MultipartRequest(request, saveDirectory, maxSize, "utf-8",
                    new DefaultFileRenamePolicy());
        } catch (IOException e) {
            return null;
        }

        result = new ProcessResult(req, null);

        File file = req.getFile("file");

        if (file == null) {
            return result;
        }

        Connection conn = getConnection();

        if (conn == null) {
            file.delete();
            throw new DatabaseException("Cannot connect to database");
        }

        result.setFile(new FileDTO());
        result.getFile().setName(req.getOriginalFileName("file"));
        result.getFile().setPath(file.getAbsolutePath());

        try {
            st = conn.prepareStatement("INSERT INTO files (name, path) VALUES (?, ?);");
            st.setString(1, req.getOriginalFileName("file"));
            st.setString(2, file.getAbsolutePath());
            st.executeUpdate();

            st.close();

            st = conn.prepareStatement("SELECT LAST_INSERT_ID();");
            rs = st.executeQuery();
            rs.next();
            result.getFile().setId(rs.getInt(1));
        } catch (SQLException e) {
            file.delete();
            throw new DatabaseException(e.getMessage());
        } finally {
            cleanup();
        }

        return result;
    }

    public static FileDTO getFile(int id) throws DatabaseException {
        Connection conn = getConnection();

        if (conn == null) {
            throw new DatabaseException("Cannot connect to database");
        }

        FileDTO result;

        try {
            st = conn.prepareStatement("SELECT * FROM files WHERE id = ?;");
            st.setInt(1, id);
            rs = st.executeQuery();
            if (!rs.next()) {
                return null;
            }
            result = new FileDTO(rs.getInt("id"), rs.getString("name"), rs.getString("path"));
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        } finally {
            cleanup();
        }

        return result;
    }

    public static void deleteFile(int id) throws DatabaseException {
        Connection conn = getConnection();

        if (conn == null) {
            throw new DatabaseException("Cannot connect to database");
        }

        try {
            st = conn.prepareStatement("DELETE FROM files WHERE id = ?;");
            st.setInt(1, id);
            st.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        } finally {
            cleanup();
        }
    }

    public static class ProcessResult {
        private MultipartRequest req;
        private FileDTO file;

        private ProcessResult() {
        }

        private ProcessResult(MultipartRequest req, FileDTO file) {
            this.req = req;
            this.file = file;
        }

        public MultipartRequest getReq() {
            return req;
        }

        public void setReq(MultipartRequest req) {
            this.req = req;
        }

        public FileDTO getFile() {
            return file;
        }

        public void setFile(FileDTO file) {
            this.file = file;
        }
    }
}
