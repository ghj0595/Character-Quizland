package quiz.action;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import controller.Action;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import quiz.model.QuizDao;
import quiz.model.QuizResponseDto;
import solve.model.SolveDao;
import solve.model.SolveRequestDto;
import solve.model.SolveResponseDto;
import user.model.User;

public class QuizResultAction implements Action{

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

		JSONArray solveCodes = session.getAttribute("solveCodes") != null ? new JSONArray(session.getAttribute("solveCodes").toString()) : new JSONArray();
		
		StringBuilder builder = new StringBuilder(); 
		BufferedReader reader =  request.getReader();
		while(reader.ready()) {
			builder.append(reader.readLine());
		}
		
		JSONObject reqData = new JSONObject(builder.toString());
		if(!reqData.has("quiz_size") || !reqData.has("score") || !reqData.has("timer") || !reqData.has("solve_codes") ) {
		    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		    resData.put("status", HttpServletResponse.SC_BAD_REQUEST);
		    resData.put("error", "BAD REQUEST");
		    resData.put("message", "잘못된 요청입니다. 필수 키 값이 누락되었습니다.");
		    resData.put("timestamp", new Timestamp(System.currentTimeMillis()));
		    JsonUtil.sendJsonResponse(response, resData);
		    return;
		}
		
		int code = reqData.getInt("quiz_code");
		int curScore = reqData.getInt("score");
		double sec = reqData.getDouble("timer");
		int timer=(int) (sec * 1000);

		if (solveCodes.length() == 0) {
		    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		    resData.put("status", HttpServletResponse.SC_BAD_REQUEST);
		    resData.put("error", "BAD REQUEST");
		    resData.put("message", "solveCodes 배열이 비어 있습니다.");
		    resData.put("timestamp", new Timestamp(System.currentTimeMillis()));
		    JsonUtil.sendJsonResponse(response, resData);
		    return;
		}

		List<Object> solves=solveCodes.toList();
		int solveSize = solves.size();
		int solveCode = (int) solves.get(solveSize-1);
		
		SolveDao solveDao = SolveDao.getInstance();
		SolveRequestDto reqSolve = new SolveRequestDto(curScore,timer);
		solveDao.updateSolve(solveCode, reqSolve);
		
		QuizDao quizDao = QuizDao.getInstance();
		QuizResponseDto quiz=quizDao.findQuizByCode(code);

		JSONObject content= TMDBApiManager.getContent(quiz.getType(), quiz.getContentId());
		content.put("type", quiz.getType());
		content.put("poster_path", "https://image.tmdb.org/t/p/w342"+content.get("poster_path"));
		content.put("content_path", "https://www.themoviedb.org/"+(quiz.getType()==0?"movie":"tv")+"/"+content.get("id"));

		JSONObject people= TMDBApiManager.getPeople(quiz.getPeopleId());
		people.put("profile_path", "https://image.tmdb.org/t/p/w185"+people.get("profile_path"));
		people.put("people_path", "https://www.themoviedb.org/person/"+people.get("id"));

		JSONObject score= new JSONObject();
		score.put("score", curScore);

		int totalScore = 0;
		for(Object s : solves) {
			SolveResponseDto curSolve = solveDao.findSolveByCode((int)s);
			if(curSolve!=null)
				totalScore +=curSolve.getScore();
		}
		score.put("total_score", totalScore);

		resData.put("status", HttpServletResponse.SC_OK);
		resData.put("num", solveSize);
		resData.put("content", content);
		resData.put("people", people);
		resData.put("score", score);
		
		session.setAttribute("result", resData.toString().replace("\"", "&quot;"));
		
		JsonUtil.sendJsonResponse(response, resData);
	}
}
