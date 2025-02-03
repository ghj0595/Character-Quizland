package quiz.action;

import java.io.IOException;
import java.io.PrintWriter;

import org.json.JSONObject;

import jakarta.servlet.http.HttpServletResponse;

public class JsonUtil {
	public static void sendJsonResponse(HttpServletResponse response, JSONObject resData) throws IOException {
	    response.setContentType("application/json");
	    response.setCharacterEncoding("UTF-8");
	    try (PrintWriter out = response.getWriter()) {
	        out.append(resData.toString());
	        out.flush();
	    }
	}
}
