package simpleboard.dto;

import java.io.Serializable;
import java.sql.Timestamp;

public class ArticleDTO implements Serializable {
    private Integer id;
    private String title;
    private String content;
    private UserDTO author;
    private Timestamp modifiedAt;
    private String boardId;
    private FileDTO file;

    public ArticleDTO() {
        super();
    }

    public ArticleDTO(Integer id, String title, String content, UserDTO author, Timestamp modifiedAt, String boardId, FileDTO file) {
        super();
        this.id = id;
        this.title = title;
        this.content = content;
        this.author = author;
        this.modifiedAt = modifiedAt;
        this.boardId = boardId;
        this.file = file;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public UserDTO getAuthor() {
        return author;
    }

    public void setAuthor(UserDTO author) {
        this.author = author;
    }

    public Timestamp getModifiedAt() {
        return modifiedAt;
    }

    public void setModifiedAt(Timestamp modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    public String getBoardId() {
        return boardId;
    }

    public void setBoardId(String boardId) {
        this.boardId = boardId;
    }

    public FileDTO getFile() {
        return file;
    }

    public void setFile(FileDTO file) {
        this.file = file;
    }
}
