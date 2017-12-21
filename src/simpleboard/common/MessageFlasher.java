package simpleboard.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MessageFlasher {
    public static void flash(HttpServletRequest request, String message, String category) {
        request.getSession().setAttribute("flashedMessage", new FlashedMessage(message, category));
    }

    public static FlashedMessage getFlashedMessage(HttpServletRequest request) {
        FlashedMessage flashedMessage = (FlashedMessage) request.getSession().getAttribute("flashedMessage");
        if (flashedMessage != null) {
            request.getSession().removeAttribute("flashedMessage");
        }
        return flashedMessage;
    }

    public static String renderFlashedMessage(HttpServletRequest request) {
        String result = "";

        FlashedMessage flashedMessage = getFlashedMessage(request);

        if (flashedMessage != null) {
            String color;

            switch (flashedMessage.getCategory()) {
                case "success":
                    color = "success";
                    break;
                case "error":
                    color = "warning";
                    break;
                case "exception":
                    color = "danger";
                    break;
                case "info":
                    color = "info";
                    break;
                default:
                    color = "primary";
            }
            result += "<div class=\"alert alert-" + color + " alert-dismissable fade show\" role=\"alert\">";
            result += flashedMessage.getMessage();
            result += "<button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-label=\"Close\">\n" +
                    "    <span aria-hidden=\"true\">&times;</span>\n" +
                    "  </button></div>";
        }

        return result;
    }
}
