package quiz.action;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
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

		if(userCode.isEmpty() || !reqData.has("quiz_size") || !reqData.has("solve_codes") || !reqData.has("timer") ) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			resData.put("status", HttpServletResponse.SC_BAD_REQUEST);
			resData.put("error", "BAD REQUEST");
			resData.put("message","잘못된 요청입니다. 필수 키 값이 누락되었습니다.");
			resData.put("timestamp",new Timestamp(System.currentTimeMillis()));
		}else {
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
			System.out.println("CreateQuiz- ready");
			int count =0;
			while(true) {
				count++;
				System.out.println(count);
				if(quizSize >= 10) {
					int reusePer = Math.min(quizSize, 1000);
					int rNum=ran.nextInt(1500);
					
					if(rNum<reusePer) {
						System.out.println("reuse");
						rNum=ran.nextInt(quizSize);
						code=quizDao.findQuizcodeByIndex(rNum);
						resQuiz=quizDao.findQuizByCode(code);
						System.out.println("reuse" + resQuiz);
					}
				}
				if(resQuiz == null){
					int type=ran.nextInt(2);
					
					JSONObject content = TMDBApiManager.getRandomContent(type);
					int contentId = content.getInt("id");
					
					JSONArray castChk = TMDBApiManager.getCast(type, contentId);
					int profileNum = 0;
					if(!castChk.isEmpty() && castChk.getJSONObject(0).get("character")!=""
							&& !castChk.getJSONObject(0).get("character").toString().contains("self")
							&& !castChk.getJSONObject(0).isNull("profile_path")) {
						for(int i=0;i<castChk.length();i++) {
							if(!castChk.getJSONObject(i).isNull("profile_path"))
								profileNum++;
						}
					}

					resQuiz = quizDao.findQuizByTypeContent(type, contentId);
					if(!content.isNull("poster_path") && profileNum>=4 && resQuiz == null) {
						System.out.println("create");

						int peopleId= castChk.getJSONObject(0).getInt("id");
						QuizRequestDto reqQuiz= new QuizRequestDto(type,contentId,peopleId);
						resQuiz = quizDao.createQuiz(reqQuiz);
						System.out.println("create" + resQuiz);
					}
					if(resQuiz !=null)
						code=resQuiz.getCode();
				}
				if(code>0 && !quizCodes.contains(code)) {
					System.out.println("result" + resQuiz);
					break;
				}
			}
			System.out.println("CreateQuiz - complete");
			SolveRequestDto reqSolve=new SolveRequestDto(userCode,code,0,20000);
			SolveResponseDto resSolve= solveDao.createSolve(reqSolve);

			if(resSolve!=null) {
				System.out.println("ADD");
				solves.add(resSolve.getCode());
			}else{
				System.out.println("NULL");
			}
			session.setAttribute("solveCodes", solves);

			System.out.println("CreateQuiz - Cast - get");
			JSONArray cast = TMDBApiManager.getCast(resQuiz.getType(), resQuiz.getContentId());
			int castSize= cast.length();
			System.out.println("CreateQuiz - Cast - complete");
			
			HashMap<Integer,String> castPath= new HashMap<>();
			for(int i=0;i<castSize;i++) {
				JSONObject people= cast.getJSONObject(i);
				if(!people.isNull("profile_path")) 
					castPath.put(people.getInt("id"), (String) people.get("profile_path"));
			}
			System.out.println("CreateQuiz - optIds - get");
			ArrayList<Integer> optIds =new ArrayList<>();
			optIds.add(resQuiz.getPeopleId());

			for(int i=0;i<3;i++) {
				int rNum = ran.nextInt(castSize);
				JSONObject people= cast.getJSONObject(rNum);
				System.out.println("CreateQuiz - optIds - complete"+people);
				int id= people.getInt("id");
				if(optIds.contains(id) || people.isNull("profile_path"))
					i--;
				else
					optIds.add(id);
			}
			System.out.println("CreateQuiz - optIds - complete");
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
			System.out.println("CreateQuiz - content - get");
			JSONObject content = TMDBApiManager.getContent(resQuiz.getType(), resQuiz.getContentId());
			System.out.println("CreateQuiz - content - complete");
			String posterPath = "https://image.tmdb.org/t/p/w342"+content.get("poster_path"); 
			resData.put("status", HttpServletResponse.SC_CREATED);
			resData.put("message","퀴즈 생성이 완료되었습니다.");
			resData.put("quiz_code", code);
			resData.put("solve_codes", solves);
			resData.put("poster_path", posterPath);
			resData.put("character_name", cast.getJSONObject(0).get("character"));
			resData.put("answer_number", optIds.indexOf(resQuiz.getPeopleId()));
			resData.put("options", optPath);
			System.out.println("CreateQuiz - sendout");
		}
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		
		PrintWriter out = response.getWriter();
		out.append(resData.toString());
		out.flush();
	}
}
