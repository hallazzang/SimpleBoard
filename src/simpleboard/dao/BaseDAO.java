package simpleboard.dao;

import simpleboard.common.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

class BaseDAO {
    static PreparedStatement st = null;
    static ResultSet rs = null;

    BaseDAO() { }

    static Connection getConnection() {
        return Database.getConnection();
    }

    static void cleanup() {
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
