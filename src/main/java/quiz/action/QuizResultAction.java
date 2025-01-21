package quiz.action;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import board.action.RequestDispatcher;
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
import user.model.UserDao;

public class QuizResultAction implements Action{

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
		
		if(userCode.isEmpty() || !reqData.has("quiz_number") || !reqData.has("quiz_size") || !reqData.has("score") || !reqData.has("timer") || !reqData.has("solve_codes") ) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			resData.put("status", HttpServletResponse.SC_BAD_REQUEST);
			resData.put("error", "BAD REQUEST");
			resData.put("message","잘못된 요청입니다. 필수 키 값이 누락되었습니다.");
			resData.put("timestamp",new Timestamp(System.currentTimeMillis()));
		}else {
			int number = reqData.getInt("quiz_number");
			int size = reqData.getInt("quiz_size");
			int curScore = reqData.getInt("score");
			double sec = reqData.getDouble("timer");
			int timer=(int) (sec * 1000);
			JSONArray solveCodes = reqData.getJSONArray("solve_codes");
			List<Object> solves=solveCodes.toList();
			int solveCode = (int) solves.get(solves.size()-1);
			
			SolveDao solveDao = SolveDao.getInstance();
			QuizDao quizDao = QuizDao.getInstance();
			SolveResponseDto solve = solveDao.findSolveByCode(solveCode);
			
			SolveRequestDto reqSolve = new SolveRequestDto(curScore,timer);
			solveDao.updateSolve(solveCode, reqSolve);
			
			QuizResponseDto quiz=quizDao.findQuizByCode(solve.getQuizCode());
			
			
			resData.put("status", HttpServletResponse.SC_OK);
			
			JSONObject content= TMDBApiManager.getContent(quiz.getType(), quiz.getContentId());
			content.put("type", quiz.getType());
			content.put("poster_path", "https://image.tmdb.org/t/p/w342"+content.get("poster_path"));
			content.put("content_path", "https://image.tmdb.org/t/p/w342"+content.get("poster_path"));

			JSONObject people= TMDBApiManager.getPeople(quiz.getPeopleId());
			people.put("profile_path", "https://image.tmdb.org/t/p/w342"+content.get("poster_path"));
			people.put("people_path", "https://image.tmdb.org/t/p/w342"+content.get("poster_path"));

			JSONObject score= new JSONObject();
			score.put("score", curScore);
			int totalScore = 0;
			for(Object s : solves) {
				SolveResponseDto curSolve = solveDao.findSolveByCode((int)s);
				totalScore +=curSolve.getScore();
			}
			score.put("total_score", totalScore);
			if(number==size) {
				UserDao userdao= UserDao.getInstance(); 
				int rank = userdao.getRankByScore(totalScore);
				double per = userdao.getPerByScore(totalScore);
				score.put("rank", rank);
				score.put("percentage", per);
			}
			
			resData.put("content", content);
			resData.put("people", people);
			resData.put("score", score);
			resData.put("solve_codes", solveCodes);
			
			session.setAttribute("result", resData.toString());
			System.out.println(resData);

			response.sendRedirect("/result");
		}
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		
		PrintWriter out = response.getWriter();
		out.append(resData.toString());
		out.flush();
		
	}
}
