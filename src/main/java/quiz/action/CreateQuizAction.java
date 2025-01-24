package quiz.action;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
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
		
		JSONArray solveCodes = session.getAttribute("solveCodes") != null ? new JSONArray(session.getAttribute("solveCodes").toString()) : new JSONArray();
		
		StringBuilder builder = new StringBuilder(); 
		BufferedReader reader =  request.getReader();
		while(reader.ready()) {
			builder.append(reader.readLine());
		}
		
		JSONObject reqData = new JSONObject(builder.toString());
		if(!reqData.has("quiz_size") || !reqData.has("solve_codes") || !reqData.has("timer") ) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			resData.put("status", HttpServletResponse.SC_BAD_REQUEST);
			resData.put("error", "BAD REQUEST");
			resData.put("message","잘못된 요청입니다. 필수 키 값이 누락되었습니다.");
			resData.put("timestamp",new Timestamp(System.currentTimeMillis()));
			JsonUtil.sendJsonResponse(response, resData);
			return;
		}
		
		Random ran = new Random();
		
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
				
				JSONObject content = TMDBApiManager.getRandomContent(type);
				int contentId = content.getInt("id");
				
				JSONArray castChk = TMDBApiManager.getCast(type, contentId);
				int profileNum = 0;
				if(!castChk.isEmpty() && castChk.getJSONObject(0).get("character")!=""
						&& !castChk.getJSONObject(0).get("character").toString().toLowerCase().contains("self")
						&& !castChk.getJSONObject(0).isNull("profile_path")) {
					for(int i=0;i<castChk.length();i++) {
						if(!castChk.getJSONObject(i).isNull("profile_path"))
							profileNum++;
					}
				}

				resQuiz = quizDao.findQuizByTypeContent(type, contentId);
				if(!content.isNull("overview") && content.get("overview")!="" &&!content.isNull("poster_path") && profileNum>=4 && resQuiz == null) {
					int peopleId= castChk.getJSONObject(0).getInt("id");
					QuizRequestDto reqQuiz= new QuizRequestDto(type,contentId,peopleId);
					resQuiz = quizDao.createQuiz(reqQuiz);
				}
				if(resQuiz !=null)
					code=resQuiz.getCode();
			}
			if(code>0 && !quizCodes.contains(code)) {
				break;
			}
		}

		SolveRequestDto reqSolve=new SolveRequestDto(user.getUserCode(),code,0,20000);
		SolveResponseDto resSolve= solveDao.createSolve(reqSolve);

		solves.add(resSolve.getCode());
		session.setAttribute("solveCodes", solves);

		JSONArray cast = TMDBApiManager.getCast(resQuiz.getType(), resQuiz.getContentId());
		int castSize= cast.length();
		
		HashMap<Integer,String> castPath= new HashMap<>();
		for(int i=0;i<castSize;i++) {
			JSONObject people= cast.getJSONObject(i);
			if(!people.isNull("profile_path")) 
				castPath.put(people.getInt("id"), (String) people.get("profile_path"));
		}

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
			String path="https://image.tmdb.org/t/p/w185"+castPath.get(id);
			optPath.add(path);
		}

		JSONObject content = TMDBApiManager.getContent(resQuiz.getType(), resQuiz.getContentId());
		String posterPath = "https://image.tmdb.org/t/p/w342"+content.get("poster_path"); 
		resData.put("status", HttpServletResponse.SC_CREATED);
		resData.put("message","퀴즈 생성이 완료되었습니다.");
		resData.put("quiz_code", code);
		resData.put("solve_codes", solves);
		resData.put("poster_path", posterPath);
		resData.put("character_name", cast.getJSONObject(0).get("character"));
		resData.put("answer_number", optIds.indexOf(resQuiz.getPeopleId()));
		resData.put("options", optPath);

		JsonUtil.sendJsonResponse(response, resData);
	}
}
