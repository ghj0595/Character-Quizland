package quiz.action;

import java.io.IOException;
import java.sql.Timestamp;

import org.json.JSONObject;

import controller.Action;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import user.model.User;
import user.model.UserDao;


public class TotalResultAction  implements Action{

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("log");
		
		JSONObject resData = new JSONObject();
		if(user == null || user.getUserCode().isEmpty()) {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			resData.put("status", HttpServletResponse.SC_UNAUTHORIZED);
			resData.put("error", "SC_UNAUTHORIZED");
			resData.put("message","로그인되어 있지 않습니다.");
			resData.put("timestamp",new Timestamp(System.currentTimeMillis()));
			JsonUtil.sendJsonResponse(response, resData);
			return;
		}
		
		if(session.getAttribute("result") == null) {
		    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
		    resData.put("status", HttpServletResponse.SC_FORBIDDEN);
		    resData.put("error", "BAD SC_FORBIDDEN");
		    resData.put("message", "잘못된 접근입니다.");
		    resData.put("timestamp", new Timestamp(System.currentTimeMillis()));
		    JsonUtil.sendJsonResponse(response, resData);
		    return;
		}
		
		JSONObject reqData = new JSONObject(session.getAttribute("result").toString().replace("&quot;","\""));
		
		int totalScore = reqData.getJSONObject("score").getInt("total_score");

		UserDao userDao = UserDao.getInstance();

	    boolean isBestScore=false;
	    if(user.getBestScore()<totalScore) {
	    	userDao.updateUserBestScore(user.getUserCode(),totalScore);
	    	isBestScore=true;
	    }
	    
	    resData.put("status", HttpServletResponse.SC_OK);
		resData.put("score", totalScore);
		resData.put("rank", userDao.getRankByScore(totalScore));
		resData.put("percentage", userDao.getPerByScore(totalScore));
		resData.put("is_best_Score", isBestScore);
		
		if(session.getAttribute("solveCodes") != null)
			session.removeAttribute("solveCodes");
		session.removeAttribute("result");
		
		JsonUtil.sendJsonResponse(response, resData);
	}
}
