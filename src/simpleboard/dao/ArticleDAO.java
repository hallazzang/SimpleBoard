package simpleboard.dao;

import simpleboard.common.DatabaseException;
import simpleboard.dto.ArticleDTO;
import simpleboard.dto.FileDTO;
import simpleboard.dto.UserDTO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ArticleDAO extends BaseDAO {
    private ArticleDAO() {
    }

    public enum SortType {
        ASCENDING,
        DESCENDING,
    }

    public static void putArticle(String boardId, String title, String content, String authorId, Integer fileId)
            throws DatabaseException {
        Connection conn = getConnection();

        if (conn == null) {
            throw new DatabaseException("Cannot connect to database");
        }

        try {
            st = conn.prepareStatement(
                    "INSERT INTO articles " +
                    "(boardId, title, content, authorId, fileId) VALUES (?, ?, ?, ?, ?);");
            st.setString(1, boardId);
            st.setString(2, title);
            st.setString(3, content);
            st.setString(4, authorId);
            st.setObject(5, fileId);
            st.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        } finally {
            cleanup();
        }
    }

    public static ArticleDTO getArticle(int articleId) throws DatabaseException {
        Connection conn = getConnection();

        if (conn == null) {
            throw new DatabaseException("Cannot connect to database");
        }

        ArticleDTO result = null;

        try {
            st = conn.prepareStatement(
                    "SELECT * " +
                    "FROM articles AS a " +
                    "INNER JOIN users AS u ON a.id = ? " +
                    "LEFT JOIN files AS f ON a.fileId = f.id;");
            st.setInt(1, articleId);
            rs = st.executeQuery();
            if (rs.next()) {
                UserDTO author = new UserDTO(rs.getString("u.id"), rs.getString("u.name"), null, rs.getString("u.role"));

                FileDTO file = null;
                if (rs.getInt("f.id") != 0) {
                    file = new FileDTO(rs.getInt("f.id"), rs.getString("f.name"), rs.getString("f.path"));
                }

                result = new ArticleDTO(rs.getInt("a.id"), rs.getString("a.title"), rs.getString("a.content"),
                        author, rs.getTimestamp("a.modifiedAt"), rs.getString("a.boardId"), file
                );
            }
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        } finally {
            cleanup();
        }

        return result;
    }

    public static List<ArticleDTO> getArticles(String boardId, int page, SortType sortType) throws DatabaseException {
        Connection conn = getConnection();

        if (conn == null) {
            throw new DatabaseException("Cannot connect to database");
        }

        List<ArticleDTO> result;

        try {
            st = conn.prepareStatement(
                    "SELECT * " +
                    "FROM articles AS a " +
                    "INNER JOIN users AS u ON a.boardId = ? AND a.authorId = u.id " +
                    "LEFT JOIN files AS f ON a.fileId = f.id " +
                    "ORDER BY a.id " + (sortType.equals(SortType.ASCENDING) ? "ASC" : "DESC") + " LIMIT ?, 15;"
            );
            st.setString(1, boardId);
            st.setInt(2, (page - 1) * 15);
            rs = st.executeQuery();

            result = new ArrayList<>();
            while (rs.next()) {
                UserDTO author = new UserDTO(rs.getString("u.id"), rs.getString("u.name"), null, rs.getString("u.role"));

                FileDTO file = null;
                if (rs.getInt("f.id") != 0) {
                    file = new FileDTO(rs.getInt("f.id"), rs.getString("f.name"), rs.getString("f.path"));
                }

                ArticleDTO article = new ArticleDTO(
                        rs.getInt("a.id"), rs.getString("a.title"), rs.getString("a.content"),
                        author, rs.getTimestamp("a.modifiedAt"), rs.getString("a.boardId"), file
                );

                result.add(article);
            }
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        } finally {
            cleanup();
        }

        return result;

    }
}
