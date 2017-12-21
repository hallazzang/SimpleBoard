package simpleboard.common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private static Connection conn = null;

    private static final String url = "jdbc:mysql://mysql:3306/simpleboard?autoReconnect=true&useSSL=false";
    private static final String user = "simpleboard";
    private static final String password = "simpleboard";

    private Database() { }

    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Cannot load database driver");
        }
    }

    public static Connection getConnection() {
        if (conn == null) {
            try {
                conn = DriverManager.getConnection(url, user, password);
            } catch (SQLException e) {
                return null;
            }
        }

        return conn;
    }

}
