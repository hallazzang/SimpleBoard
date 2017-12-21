package simpleboard.dto;

import java.io.Serializable;

public class FileDTO implements Serializable {
    private Integer id;
    private String name;
    private String path;

    public FileDTO() { super(); }

    public FileDTO(Integer id, String name, String path) {
        super();
        this.id = id;
        this.name = name;
        this.path = path;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
