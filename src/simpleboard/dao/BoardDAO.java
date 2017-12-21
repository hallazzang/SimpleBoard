package simpleboard.dao;

import simpleboard.common.DatabaseException;
import simpleboard.dto.BoardDTO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BoardDAO extends BaseDAO {
    private BoardDAO() {
    }

    public static boolean exists(String boardId) throws DatabaseException {
        Connection conn = getConnection();

        if (conn == null) {
            throw new DatabaseException("Cannot connect to database");
        }

        boolean result;

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

    public static List<BoardDTO> getBoards() throws DatabaseException {
        Connection conn = getConnection();

        if (conn == null) {
            throw new DatabaseException("Cannot connect to database");
        }

        List<BoardDTO> result;

        try {
            st = conn.prepareStatement("SELECT * FROM boards;");
            rs = st.executeQuery();
            if (!rs.next()) {
                return null;
            } else {
                rs.previous();
                result = new ArrayList<>();
            }
            while (rs.next()) {
                result.add(new BoardDTO(rs.getString("id"), rs.getString("name")));
            }
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        } finally {
            cleanup();
        }

        return result;
    }
}
