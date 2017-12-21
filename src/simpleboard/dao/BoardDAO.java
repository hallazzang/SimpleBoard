package simpleboard.dao;

import simpleboard.common.DatabaseException;

import java.sql.Connection;
import java.sql.SQLException;

public class BoardDAO extends BaseDAO {
    private BoardDAO() { }

    public static boolean exists(String boardId) throws DatabaseException {
        Connection conn = getConnection();
        boolean result;

        if (conn == null) {
            throw new DatabaseException("Cannot connect to database");
        }

        try {
            st = conn.prepareStatement("SELECT * FROM boards WHERE id = ?;");
            st.setString(1, boardId);

            rs = st.executeQuery();
            result = rs.next();
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        } finally {
            cleanup();
        }

        return result;
    }
}
