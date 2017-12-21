package simpleboard.dao;

import org.mindrot.jbcrypt.BCrypt;
import simpleboard.common.DatabaseException;
import simpleboard.dto.UserDTO;

import java.sql.*;

public class UserDAO {
    private static UserDAO instance = new UserDAO();

    private String url = "jdbc:mysql://mysql:3306/simpleboard?autoReconnect=true&useSSL=false";
    private String user = "simpleboard";
    private String password = "simpleboard";

    private Connection conn = null;
    private PreparedStatement st = null;
    private ResultSet rs = null;

    public static UserDAO getInstance() {
        return instance;
    }

    private UserDAO() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Cannot load database driver");
        }
    }

    private boolean connect() {
        if (conn != null) {
            return true;
        }

        try {
            conn = DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public UserDTO getUser(String userId) throws DatabaseException {
        if (!connect()) {
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

    public boolean canLogin(String userId, String userPw) throws DatabaseException {
        boolean result = false;

        UserDTO user = getUser(userId);

        if (user == null) {
            return false;
        }

        return BCrypt.checkpw(userPw, user.getPwHash());
    }

    public boolean idExists(String userId) throws DatabaseException {
        return getUser(userId) != null;
    }

    public void register(String userId, String userName, String userPw, String role) throws DatabaseException {
        if (!connect()) {
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

    private void cleanup() {
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
