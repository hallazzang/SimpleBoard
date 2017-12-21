package simpleboard.dto;

import org.mindrot.jbcrypt.BCrypt;

import java.io.Serializable;

public class UserDTO implements Serializable {
    private String id;
    private String name;
    private String pwHash;
    private String role;

    public UserDTO() {
        super();
    }

    public UserDTO(String id, String name, String pwHash, String role) {
        super();
        this.id = id;
        this.name = name;
        this.pwHash = pwHash;
        this.role = role;
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

    public String getPwHash() {
        return pwHash;
    }

    public void setPwHash(String pwHash) {
        this.pwHash = pwHash;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean checkPw(String pw) {
        return BCrypt.checkpw(pw, pwHash);
    }
}
