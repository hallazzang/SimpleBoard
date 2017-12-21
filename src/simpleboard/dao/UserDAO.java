package simpleboard.dao;

import org.mindrot.jbcrypt.BCrypt;
import simpleboard.common.Database;
import simpleboard.common.DatabaseException;
import simpleboard.dto.UserDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {
    private static PreparedStatement st = null;
    private static ResultSet rs = null;

    private UserDAO() { }

    public static UserDTO getUser(String userId) throws DatabaseException {
        Connection conn = Database.getConnection();

        if (conn == null) {
            throw new DatabaseException("Cannot connect to database");
        }

        UserDTO result = null;

        try {
            st = conn.prepareStatement("SELECT userName, pwHash, role FROM users WHERE userId=?");
            st.setString(1, userId);

            rs = st.executeQuery();
            if (rs.next()) {
                String userName = rs.getString(1);
                String pwHash = rs.getString(2);
                String role = rs.getString(3);

                result = new UserDTO(userId, userName, pwHash, role);
            }
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        } finally {
            cleanup();
        }

        return result;
    }

    public static boolean canLogin(String userId, String userPw) throws DatabaseException {
        boolean result = false;

        UserDTO user = getUser(userId);

        if (user == null) {
            return false;
        }

        return BCrypt.checkpw(userPw, user.getPwHash());
    }

    public static boolean idExists(String userId) throws DatabaseException {
        return getUser(userId) != null;
    }

    public static void register(String userId, String userName, String userPw, String role) throws DatabaseException {
        Connection conn = Database.getConnection();

        if (conn == null) {
            throw new DatabaseException("Cannot connect to database");
        }

        try {
            st = conn.prepareStatement("INSERT INTO users (userId, userName, pwHash, role) VALUES (?, ?, ?, ?);");
            st.setString(1, userId);
            st.setString(2, userName);
            st.setString(3, BCrypt.hashpw(userPw, BCrypt.gensalt()));
            st.setString(4, role);

            st.execute();
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        } finally {
            cleanup();
        }
    }

    private static void cleanup() {
        try {
            if (rs != null) {
                rs.close();
                rs = null;
            }
            if (st != null) {
                st.close();
                st = null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
