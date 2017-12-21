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
    private static final int pageSize = 3;

    private ArticleDAO() {
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
                            "ORDER BY a.id " + (sortType.equals(SortType.ASCENDING) ? "ASC" : "DESC") + " LIMIT ?, ?;"
            );
            st.setString(1, boardId);
            st.setInt(2, (page - 1) * pageSize);
            st.setInt(3, pageSize);
            rs = st.executeQuery();

            if (!rs.next()) {
                return null;
            } else {
                rs.previous();
                result = new ArrayList<>();
            }
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

    public static int getArticleCount(String boardId) throws DatabaseException {
        Connection conn = getConnection();

        if (conn == null) {
            throw new DatabaseException("Cannot connect to database");
        }

        int result;

        try {
            st = conn.prepareStatement("SELECT COUNT(*) FROM articles WHERE boardId = ?;");
            st.setString(1, boardId);
            rs = st.executeQuery();
            rs.next();
            result = rs.getInt(1);
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        } finally {
            cleanup();
        }

        return result;
    }

    public enum SortType {
        ASCENDING,
        DESCENDING,
    }
}
