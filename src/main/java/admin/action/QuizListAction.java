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
		int page = 1;
        int itemsPerPage = 10;
        
        if (request.getParameter("page") != null) {
            page = Integer.parseInt(request.getParameter("page"));
        }

        SolveDao solveDao = SolveDao.getInstance(); 
        JSONArray quizSolveData = solveDao.findQuizSolveAll(page, itemsPerPage); 
        request.setAttribute("quizList", quizSolveData.toList()); 
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", (int) Math.ceil(solveDao.getTotalSize() / (double) itemsPerPage));
        request.getRequestDispatcher("/quizzes").forward(request, response);
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
