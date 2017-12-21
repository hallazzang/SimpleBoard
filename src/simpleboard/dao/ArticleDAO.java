package simpleboard.dao;

import simpleboard.common.DatabaseException;
import simpleboard.dto.ArticleDTO;
import simpleboard.dto.UserDTO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ArticleDAO extends BaseDAO {
    private ArticleDAO() { }

    public enum SortType {
        ASCENDING,
        DESCENDING,
    };

    public static void putArticle(String boardId, String title, String content, String authorId) throws DatabaseException {
        Connection conn = getConnection();

        if (conn == null) {
            throw new DatabaseException("Cannot connect to database");
        }

        try {
            st = conn.prepareStatement("INSERT INTO articles (boardId, title, content, authorId)" +
                                            "VALUES (?, ?, ?, ?);");
            st.setString(1, boardId);
            st.setString(2, title);
            st.setString(3, content);
            st.setString(4, authorId);
            st.execute();
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
            st = conn.prepareStatement("SELECT articles.title, articles.content, " +
                    "users.id, users.name, users.role, articles.modifiedAt, articles.boardId " +
                    "FROM articles INNER JOIN users ON articles.id = ?;");
            st.setInt(1, articleId);
            rs = st.executeQuery();
            if (rs.next()) {
                UserDTO author = new UserDTO();
                author.setId(rs.getString(3));
                author.setName(rs.getString(4));
                author.setRole(rs.getString(5));

                result = new ArticleDTO(articleId, rs.getString(1), rs.getString(2),
                        author, rs.getTimestamp(6), rs.getString(7)
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
            st = conn.prepareStatement("SELECT articles.id, articles.title, articles.content, " +
                    "users.id, users.name, users.role, articles.modifiedAt, articles.boardId " +
                    "FROM articles INNER JOIN users ON articles.boardId = ? AND articles.authorId = users.id " +
                    "ORDER BY articles.id " + (sortType.equals(SortType.ASCENDING) ? "ASC" : "DESC") +
                    " LIMIT ?, 15;"
            );
            st.setString(1, boardId);
            st.setInt(2, (page - 1) * 15);
            rs = st.executeQuery();

            result = new ArrayList<>();
            while (rs.next()) {
                UserDTO author = new UserDTO();
                author.setId(rs.getString(4));
                author.setName(rs.getString(5));
                author.setRole(rs.getString(6));

                ArticleDTO article = new ArticleDTO(
                        rs.getInt(1), rs.getString(2), rs.getString(3),
                        author, rs.getTimestamp(7), rs.getString(8)
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
