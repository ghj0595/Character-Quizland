package admin.action;

import java.io.IOException;
import java.util.Set;

import org.json.JSONArray;

import controller.Action;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class GetDailyUsers  implements Action{

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Set<String> dailyUsers = DailySessionTrackingListener.getDailyUsers();
        response.setContentType("application/json");
        JSONArray usernames =new JSONArray(dailyUsers.toString());
        response.getWriter().write("{\"dailyUsers\": " + dailyUsers.size() + ", \"usernames\": " + usernames + "}");
	}

}
