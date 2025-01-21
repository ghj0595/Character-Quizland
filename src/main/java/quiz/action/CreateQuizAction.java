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

import com.mysql.cj.xdevapi.JsonArray;

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
		String userCode=user.getUserCode();
		
		
		System.out.println("CreateQuizAction");
		System.out.println(user);
		System.out.println(session.getAttribute("quizNumber"));

		StringBuilder builder = new StringBuilder(); 
		BufferedReader reader =  request.getReader();
		while(reader.ready()) {
			builder.append(reader.readLine());
		}
		
		JSONObject reqData = new JSONObject(builder.toString());
		
		System.out.println(reqData);
		
		JSONObject resData = new JSONObject();

		System.out.println(reqData);
		if(userCode==null || userCode.isEmpty() || !reqData.has("quiz_number") || !reqData.has("quiz_size") ) {
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
			System.out.println("no"+number);
			System.out.println("size"+size);
			System.out.println("DAOSIZE"+quizSize);

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
				}else {
					int type=ran.nextInt(2);
					
					int contentId = TMDBApiManager.getRandomContentID(type);
					
					resQuiz = quizDao.findQuizByTypeContent(type, contentId);
					if(resQuiz ==null) {
						int peopleId= TMDBApiManager.getPeopleID(type, contentId);
						
						QuizRequestDto reqQuiz= new QuizRequestDto(type,contentId,peopleId); 
						quizDao.createQuiz(reqQuiz);
						
						resQuiz = quizDao.findQuizByTypeContent(type, contentId);
						System.out.println(resQuiz);
					}
					if(resQuiz !=null)
						code=resQuiz.getCode();
					System.out.println("make : " + code);
				}
				if(code>0 && !quizCodes.contains(code)) {
					System.out.println("result " + code);
					break;
				}
			}

			System.out.println(code);
			System.out.println(quizCodes);
			System.out.println(quizCodes.contains(code));
			
			SolveRequestDto reqSolve=new SolveRequestDto(userCode,code,0,20000);
			solveDao.createSolve(reqSolve);
			System.out.println(reqSolve);
			
			//퀴즈 생성 또는 추출후 부여
			SolveResponseDto resSolve= solveDao.findLatestSolveByUser(userCode);
			System.out.println(resSolve);
			
			if(resSolve!=null)
				solves.add(resSolve.getCode());
			
			JSONArray cast = TMDBApiManager.getCast(resQuiz.getType(), resQuiz.getContentId());
			System.out.println("result");
			System.out.println(resQuiz.getType());
			System.out.println(resQuiz.getContentId());
			System.out.println(cast);
			int castSize= cast.length();
			System.out.println(castSize);
			if(castSize>3) {
				ArrayList<Integer> optIds =new ArrayList<>();
				optIds.add(resQuiz.getPeopleId());
				System.out.println(optIds);

				for(int i=0;i<3;i++) {
					int rNum = ran.nextInt(castSize);
					JSONObject people= cast.getJSONObject(rNum);
					System.out.println(people);

					int id= people.getInt("id");
					System.out.println(id);
					if(optIds.contains(id))
						i--;
					else
						optIds.add(id);
				}
				System.out.println(optIds);
				for(int i=0;i<20;i++) {
					int rNum = ran.nextInt(optIds.size());
					int tmp =optIds.get(0);
					System.out.println("랜덤" + rNum);
					System.out.println(optIds.get(0));
					optIds.set(0, optIds.get(rNum));
					System.out.println(optIds.get(rNum));
					optIds.set(rNum, tmp);
				}
				System.out.println(optIds);
				
				ArrayList<String> optPath = new ArrayList<>();
				for(int id: optIds) {
					JSONObject people= TMDBApiManager.getPeople(id);
					String path="https://image.tmdb.org/t/p/w185"+people.get("profile_path");
					optPath.add(path);
				}
				System.out.println(optPath);
				JSONObject content = TMDBApiManager.getContent(resQuiz.getType(), resQuiz.getContentId());
				JSONObject firstCast= cast.getJSONObject(0);
				
				String posterPath = "https://image.tmdb.org/t/p/w342"+content.get("poster_path"); 
				System.out.println(posterPath);
				resData.put("status", HttpServletResponse.SC_CREATED);
				resData.put("message","퀴즈 생성이 완료되었습니다.");
				resData.put("quiz_code", code);
				resData.put("solve_codes", solves);
				resData.put("poster_path", posterPath);
				resData.put("character_name", firstCast.get("character"));
				resData.put("answer_number", optIds.indexOf(resQuiz.getPeopleId()));
				resData.put("options", optPath);
			}else {
				response.setStatus(HttpServletResponse.SC_CONFLICT);
				resData.put("status", HttpServletResponse.SC_CONFLICT);
				resData.put("error", "CONFLICT");
				resData.put("message","캐스팅인원이 모자름");
				resData.put("timestamp",new Timestamp(System.currentTimeMillis()));
			}
		}
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		
		
		PrintWriter out = response.getWriter();
		out.append(resData.toString());
		out.flush();
	}
}
