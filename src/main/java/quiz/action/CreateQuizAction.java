package quiz.action;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.List;
import java.util.Random;

import org.json.JSONArray;
import org.json.JSONObject;

import controller.Action;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import quiz.model.QuizDao;


public class CreateQuizAction implements Action{

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String user = request.getHeader("Authorization");

		StringBuilder builder = new StringBuilder(); 
		BufferedReader reader =  request.getReader();
		while(reader.ready()) {
			builder.append(reader.readLine());
		}
		
		JSONObject reqData = new JSONObject(builder.toString());
		JSONObject resData = new JSONObject();

		if(user.isEmpty() || !reqData.has("quiz_number") || !reqData.has("quiz_size") ) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			resData.put("status", HttpServletResponse.SC_BAD_REQUEST);
			resData.put("error", "BAD REQUEST");
			resData.put("message","잘못된 요청입니다. 필수 키 값이 누락되었습니다.");
			resData.put("timestamp",new Timestamp(System.currentTimeMillis()));
		}else {
//			Random ran = new Random();
//
//			int number = reqData.getInt("quiz_number");
//			int size = reqData.getInt("quiz_size");
//			JSONArray solveCodes = reqData.getJSONArray("solve_codes");
//			
//			
//			QuizDao quizDao = QuizDao.getInstance();
//			int quizSize=quizDao.getTotalSize();
//			
//			int code=0;
//			List<Object> solves=solveCodes.toList();
//			
//			while(true) {
//				// 생성된 퀴즈가 10개 이상 있는 경우 확률적으로 퀴즈중에 추출
//				if(quizSize >= 10) {
//					int reusePer = Math.min(quizSize, 1000);
//					int rNum=ran.nextInt(1500);
//					
//					if(rNum<reusePer) {
//						rNum=ran.nextInt(quizSize)+1;
//						code=0;
//					}
//				}
//				
//				if(!solves.equals(code)) {
//					break;
//				}
//			}
			
			//퀴즈 생성 또는 추출후 부여
//			solveCodes.put(code);
			resData.put("status", HttpServletResponse.SC_CREATED);
			resData.put("message","퀴즈 생성이 완료되었습니다.");
//			resData.put("quiz_code", code);
//			resData.put("solve_codes", solveCodes);
		}
		// 응답 준비
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		
		PrintWriter out = response.getWriter();
		out.append(resData.toString());
		out.flush();
	}
}
