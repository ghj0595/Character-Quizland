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


public class CreateQuizAction implements Action{

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String user = request.getHeader("Authorization");
		
		System.out.println("CreateQuizAction");
		System.out.println(user);
		
		StringBuilder builder = new StringBuilder(); 
		BufferedReader reader =  request.getReader();
		while(reader.ready()) {
			builder.append(reader.readLine());
		}
		
		JSONObject reqData = new JSONObject(builder.toString());
		JSONObject resData = new JSONObject();

		System.out.println(reqData);
		if(user==null || user.isEmpty() || !reqData.has("quiz_number") || !reqData.has("quiz_size") ) {
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
				SolveResponseDto solve = solveDao.findSolveByCode((Integer) i);
				if(solve!=null)
					quizCodes.add(solve.getQuizCode());
			}

			Integer code=0;
			
			
			while(true) {
				if(quizSize >= 10) {
					int reusePer = Math.min(quizSize, 1000);
					int rNum=ran.nextInt(1500);
					
					if(rNum<reusePer) {
						rNum=ran.nextInt(quizSize);
						code=quizDao.findQuizcodeByIndex(rNum);
						System.out.println("reUse : " + code);
					}
				}else {
					int type=ran.nextInt(2);
					
					int contentId = TMDBApiManager.getRandomPopularContent(type);
					
					QuizResponseDto resQuiz = quizDao.findQuizByTypeContent(type, contentId);
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
			
			SolveRequestDto reqSolve=new SolveRequestDto(user,code,0,20000);
			solveDao.createSolve(reqSolve);
			
			//퀴즈 생성 또는 추출후 부여
			SolveResponseDto resSolve= solveDao.findLatestSolveByUser(user);
			if(resSolve!=null)
				solves.add(resSolve.getCode());

			resData.put("status", HttpServletResponse.SC_CREATED);
			resData.put("message","퀴즈 생성이 완료되었습니다.");
			resData.put("quiz_code", code);
			resData.put("solve_codes", solves);

		}
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		
		
		PrintWriter out = response.getWriter();
		out.append(resData.toString());
		out.flush();
	}
}
