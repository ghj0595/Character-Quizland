package quiz.action;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.json.JSONArray;
import org.json.JSONObject;

import controller.Action;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import quiz.model.QuizDao;
import quiz.model.QuizRequestDto;
import quiz.model.QuizResponseDto;
import solve.model.SolveDao;
import solve.model.SolveRequestDto;
import solve.model.SolveResponseDto;
import user.model.User;


public class CreateQuizAction implements Action{

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

		if(userCode.isEmpty() || !reqData.has("quiz_number") || !reqData.has("quiz_size") ) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			resData.put("status", HttpServletResponse.SC_BAD_REQUEST);
			resData.put("error", "BAD REQUEST");
			resData.put("message","잘못된 요청입니다. 필수 키 값이 누락되었습니다.");
			resData.put("timestamp",new Timestamp(System.currentTimeMillis()));
		}else {
			Random ran = new Random();

			int number = reqData.getInt("quiz_number");
			int size = reqData.getInt("quiz_size");
			JSONArray solveCodes = reqData.getJSONArray("solve_codes");
			
			QuizDao quizDao = QuizDao.getInstance();
			int quizSize=quizDao.getTotalSize();

			SolveDao solveDao = SolveDao.getInstance();
			List<Object> solves=solveCodes.toList();
			ArrayList<Integer> quizCodes= new ArrayList<>();
			
			for (Object i : solves) {
				SolveResponseDto solve = solveDao.findSolveByCode((int) i);
				if(solve!=null)
					quizCodes.add(solve.getQuizCode());
			}

			int code=0;
			QuizResponseDto resQuiz=null;
			
			while(true) {
				if(quizSize >= 10) {
					int reusePer = Math.min(quizSize, 1000);
					int rNum=ran.nextInt(1500);
					
					if(rNum<reusePer) {
						rNum=ran.nextInt(quizSize);
						code=quizDao.findQuizcodeByIndex(rNum);
						resQuiz=quizDao.findQuizByCode(code);
					}
				}
				if(resQuiz == null){
					int type=ran.nextInt(2);
					
					int contentId = TMDBApiManager.getRandomContentID(type);
					JSONArray castChk = TMDBApiManager.getCast(type, contentId);
					int profileNum = 0;

					for(int i=0;i<castChk.length();i++) {
						if(castChk.getJSONObject(i).get("profile_path")!=null)
							profileNum++;
					}
					
					resQuiz = quizDao.findQuizByTypeContent(type, contentId);
					if(profileNum>=4 && resQuiz == null) {
						int peopleId= TMDBApiManager.getPeopleID(type, contentId);
						
						QuizRequestDto reqQuiz= new QuizRequestDto(type,contentId,peopleId); 
						quizDao.createQuiz(reqQuiz);
						
						resQuiz = quizDao.findQuizByTypeContent(type, contentId);
					}
					if(resQuiz !=null)
						code=resQuiz.getCode();
				}
				if(code>0 && !quizCodes.contains(code)) {
					break;
				}
			}
			
			SolveRequestDto reqSolve=new SolveRequestDto(userCode,code,0,20000);
			solveDao.createSolve(reqSolve);
			SolveResponseDto resSolve= solveDao.findLatestSolveByUser(userCode);
			
			if(resSolve!=null)
				solves.add(resSolve.getCode());
			
			JSONArray cast = TMDBApiManager.getCast(resQuiz.getType(), resQuiz.getContentId());
			int castSize= cast.length();

			ArrayList<Integer> optIds =new ArrayList<>();
			optIds.add(resQuiz.getPeopleId());

			for(int i=0;i<3;i++) {
				int rNum = ran.nextInt(castSize);
				JSONObject people= cast.getJSONObject(rNum);

				int id= people.getInt("id");
				if(optIds.contains(id) || people.isNull("profile_path"))
					i--;
				else
					optIds.add(id);
			}

			for(int i=0;i<20;i++) {
				int rNum = ran.nextInt(optIds.size());
				int tmp =optIds.get(0);
				optIds.set(0, optIds.get(rNum));
				optIds.set(rNum, tmp);
			}
			
			ArrayList<String> optPath = new ArrayList<>();
			for(int id: optIds) {
				JSONObject people= TMDBApiManager.getPeople(id);
				String path="https://image.tmdb.org/t/p/w185"+people.get("profile_path");
				optPath.add(path);
			}
			JSONObject content = TMDBApiManager.getContent(resQuiz.getType(), resQuiz.getContentId());
			JSONObject firstCast= cast.getJSONObject(0);
			
			String posterPath = "https://image.tmdb.org/t/p/w342"+content.get("poster_path"); 
			resData.put("status", HttpServletResponse.SC_CREATED);
			resData.put("message","퀴즈 생성이 완료되었습니다.");
			resData.put("quiz_code", code);
			resData.put("solve_codes", solves);
			resData.put("poster_path", posterPath);
			resData.put("character_name", firstCast.get("character"));
			resData.put("answer_number", optIds.indexOf(resQuiz.getPeopleId()));
			resData.put("options", optPath);
		}
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		
		
		PrintWriter out = response.getWriter();
		out.append(resData.toString());
		out.flush();
	}
}
