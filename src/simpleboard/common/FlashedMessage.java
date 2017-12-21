package simpleboard.common;

import java.io.Serializable;

public class FlashedMessage implements Serializable {
    private String message, category;

    public FlashedMessage(String message, String category) {
        super();
        this.message = message;
        this.category = category;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
