package admin.action;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import solve.model.SolveDao;

import java.io.IOException;

import org.json.JSONArray;

@WebServlet("/QuizListAction")
public class QuizListAction extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		SolveDao solveDao = SolveDao.getInstance(); 
		JSONArray quizSolveData = solveDao.findQuizSolveAll(1, 10); 
		request.setAttribute("quizList", quizSolveData.toList()); 
		request.getRequestDispatcher("/quizzes").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
