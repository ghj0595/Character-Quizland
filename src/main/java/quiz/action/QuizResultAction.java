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
import solve.model.SolveRequestDto;
import solve.model.SolveResponseDto;
import user.model.User;
import user.model.UserDao;

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
		
		int size = 10;
		if(session.getAttribute("quizSize")!=null) {
			try {
				size = (int)session.getAttribute("quizSize");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else {
			session.setAttribute("quizSize", size);
		}
		System.out.println("QuizResultAction");
		JSONArray solveCodes= new JSONArray();
		if(session.getAttribute("solveCodes")!=null) {
			solveCodes = new JSONArray(session.getAttribute("solveCodes").toString());
		}
		
		String userCode=user.getUserCode();
		
		StringBuilder builder = new StringBuilder(); 
		BufferedReader reader =  request.getReader();
		while(reader.ready()) {
			builder.append(reader.readLine());
		}
		
		JSONObject reqData = new JSONObject(builder.toString());
		JSONObject resData = new JSONObject();
		System.out.println("QuizResultAction");
		if(userCode.isEmpty() || !reqData.has("quiz_size") || !reqData.has("score") || !reqData.has("timer") || !reqData.has("solve_codes") ) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			resData.put("status", HttpServletResponse.SC_BAD_REQUEST);
			resData.put("error", "BAD REQUEST");
			resData.put("message","잘못된 요청입니다. 필수 키 값이 누락되었습니다.");
			resData.put("timestamp",new Timestamp(System.currentTimeMillis()));
		}else {
			int code = reqData.getInt("quiz_code");
			int curScore = reqData.getInt("score");
			double sec = reqData.getDouble("timer");
			int timer=(int) (sec * 1000);
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
			content.put("content_path", "https://image.tmdb.org/"+(quiz.getType()==0?"movie":"tv")+"/"+content.get("id"));

			JSONObject people= TMDBApiManager.getPeople(quiz.getPeopleId());
			people.put("profile_path", "https://image.tmdb.org/t/p/w185"+people.get("profile_path"));
			people.put("people_path", "https://image.tmdb.org/person/"+people.get("id"));

			JSONObject score= new JSONObject();
			score.put("score", curScore);
			int totalScore = 0;
			for(Object s : solves) {
				SolveResponseDto curSolve = solveDao.findSolveByCode((int)s);
				totalScore +=curSolve.getScore();
			}
			score.put("total_score", totalScore);
			if(solveSize==size) {
				UserDao userdao= UserDao.getInstance(); 
				int rank = userdao.getRankByScore(totalScore);
				double per = userdao.getPerByScore(totalScore);
				score.put("rank", rank);
				score.put("percentage", per);
			}

			resData.put("status", HttpServletResponse.SC_OK);
			resData.put("num", solveSize);
			resData.put("content", content);
			resData.put("people", people);
			resData.put("score", score);
			
			session.setAttribute("result", resData.toString().replace("\"", "&quot;"));
		}
		
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		
		PrintWriter out = response.getWriter();
		out.append(resData.toString());
		out.flush();
	}
}
