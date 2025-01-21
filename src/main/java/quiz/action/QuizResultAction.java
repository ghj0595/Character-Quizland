package quiz.action;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
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
import solve.model.SolveResponseDto;
import user.model.User;

public class QuizResultAction implements Action{

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("QuizResultAction");
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("log");
		if(user == null) {
			response.sendRedirect("/login");
			return;
		}
		
		String userCode=user.getUserCode();
		
		StringBuilder builder = new StringBuilder(); 
		BufferedReader reader =  request.getReader();
		while(reader.ready()) {
			builder.append(reader.readLine());
		}
		
		JSONObject reqData = new JSONObject(builder.toString());
		JSONObject resData = new JSONObject();
		
		System.out.println(reqData);
		
		if(userCode.isEmpty() || !reqData.has("quiz_number") || !reqData.has("quiz_size") ) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			resData.put("status", HttpServletResponse.SC_BAD_REQUEST);
			resData.put("error", "BAD REQUEST");
			resData.put("message","잘못된 요청입니다. 필수 키 값이 누락되었습니다.");
			resData.put("timestamp",new Timestamp(System.currentTimeMillis()));
		}else {
			int number = reqData.getInt("quiz_number");
			int size = reqData.getInt("quiz_size");
			JSONArray solveCodes = reqData.getJSONArray("solve_codes");
			List<Object> solves=solveCodes.toList();
			int solveCode = (int) solves.get(solves.size()-1);
			
			SolveDao solveDao = SolveDao.getInstance();
			QuizDao quizDao = QuizDao.getInstance();
			SolveResponseDto solve = solveDao.findSolveByCode(solveCode);
			QuizResponseDto quiz=quizDao.findQuizByCode(solve.getQuizCode());
			
			JSONObject content= TMDBApiManager.getContent(quiz.getType(), quiz.getContentId());
			content.append("type", quiz.getType());
			content.append("poster_path", "https://image.tmdb.org/t/p/w342"+content.get("poster_path"));
			JSONObject people= TMDBApiManager.getPeople(quiz.getPeopleId());
			JSONObject score= new JSONObject();
			
			resData.put("status", HttpServletResponse.SC_OK);
			resData.put("content", content);
			resData.put("people", people);
			resData.put("score", score);
			resData.put("solve_codes", solveCodes);
		}
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		
		PrintWriter out = response.getWriter();
		out.append(resData.toString());
		out.flush();
	}
}
