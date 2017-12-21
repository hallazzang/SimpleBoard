package simpleboard.common;

import org.apache.http.client.utils.URIBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URISyntaxException;

public class Redirecter {
    public static void redirect(String url, HttpServletRequest request, HttpServletResponse response) {
        redirect(url, request, response, false);
    }

    public static void forcedRedirect(String url, HttpServletRequest request, HttpServletResponse response) {
        redirect(url, request, response, true);
    }

    public static void refresh(HttpServletRequest request, HttpServletResponse response) {
        String target = request.getRequestURI();

        String queryString = request.getQueryString();
        if (queryString != null) {
            target += "?" + queryString;
        }

        redirect(target, request, response, true);
    }

    private static void redirect(String url, HttpServletRequest request, HttpServletResponse response, boolean forced) {
        if (!forced) {
            String nextUrl = request.getParameter("next");
            if (nextUrl != null) {
                url = nextUrl;
            }
        }

        try {
            response.sendRedirect(url);
        } catch (IOException e) {
        }
    }

    public static void comeBy(String url, HttpServletRequest request, HttpServletResponse response) {
        URIBuilder uriBuilder;

        try {
            uriBuilder = new URIBuilder(url);
        } catch (URISyntaxException e) {
            redirect(url, request, response, true);
            return;
        }

        uriBuilder.setParameter("next", request.getRequestURI() + "?" + request.getQueryString());
        try {
            url = uriBuilder.build().toString();
        } catch (URISyntaxException e) {
        }

        try {
            response.sendRedirect(url);
        } catch (IOException e) {
        }
    }
}
