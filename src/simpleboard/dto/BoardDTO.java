package simpleboard.dto;

import java.io.Serializable;

public class BoardDTO implements Serializable {
    private String id;
    private String name;

    public BoardDTO() { super(); }

    public BoardDTO(String id, String name) {
        super();
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
