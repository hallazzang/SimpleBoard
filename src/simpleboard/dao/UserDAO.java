package simpleboard.dao;

import org.mindrot.jbcrypt.BCrypt;
import simpleboard.common.DatabaseException;
import simpleboard.dto.UserDTO;

import java.sql.Connection;
import java.sql.SQLException;

public class UserDAO extends BaseDAO {
    private UserDAO() { }

    public static UserDTO getUser(String userId) throws DatabaseException {
        Connection conn = getConnection();

        if (conn == null) {
            throw new DatabaseException("Cannot connect to database");
        }

        UserDTO result = null;

        try {
            st = conn.prepareStatement("SELECT * FROM users WHERE id = ?");
            st.setString(1, userId);
            rs = st.executeQuery();
            if (rs.next()) {
                String userName = rs.getString("name");
                String pwHash = rs.getString("pwHash");
                String role = rs.getString("role");

                result = new UserDTO(userId, userName, pwHash, role);
            }
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        } finally {
            cleanup();
        }

        return result;
    }

    public static boolean idExists(String userId) throws DatabaseException {
        return getUser(userId) != null;
    }

    public static void register(String userId, String userName, String userPw, String role) throws DatabaseException {
        Connection conn = getConnection();

        if (conn == null) {
            throw new DatabaseException("Cannot connect to database");
        }

        try {
            st = conn.prepareStatement("INSERT INTO users (id, name, pwHash, role) VALUES (?, ?, ?, ?);");
            st.setString(1, userId);
            st.setString(2, userName);
            st.setString(3, BCrypt.hashpw(userPw, BCrypt.gensalt()));
            st.setString(4, role);
            st.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        } finally {
            cleanup();
        }
    }
}
