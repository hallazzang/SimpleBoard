package simpleboard.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MessageFlasher {
    public static void flash(HttpServletRequest request, String message, String category) {
        request.getSession().setAttribute("flashedMessage", new FlashedMessage(message, category));
    }

    public static FlashedMessage getFlashedMessage(HttpServletRequest request) {
        FlashedMessage flashedMessage = (FlashedMessage)request.getSession().getAttribute("flashedMessage");
        if (flashedMessage != null) {
            request.getSession().removeAttribute("flashedMessage");
        }
        return flashedMessage;
    }

    public static void renderFlashedMessage(HttpServletRequest request, HttpServletResponse response) {
        try {
            FlashedMessage flashedMessage = getFlashedMessage(request);

            if (flashedMessage != null) {
                response.getWriter().println("<p>" + flashedMessage.getMessage() + "</p>");
            }
        } catch (IOException e) {
        }
    }
}
