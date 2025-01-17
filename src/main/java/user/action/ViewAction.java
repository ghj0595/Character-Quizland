package user.action;

import java.io.IOException;
import java.util.ArrayList;

import controller.Action;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import solve.model.Solve;
import solve.model.SolveDao;
import user.model.User;

public class ViewAction implements Action {

    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("log");

        if (user == null) {
            response.sendRedirect("/login");
            return;
        }

        SolveDao solveDao = SolveDao.getInstance();

        
        int listCount = solveDao.getSizeByUser(user.getUserCode());

        int totalScore = solveDao.getTotalScoreByUser(user.getUserCode());

        double avgScore = (listCount > 0) ? (double) totalScore / listCount : 0;
        
        session.setAttribute("totalGame", listCount);
        session.setAttribute("totalScore", totalScore);
        session.setAttribute("avgScore", avgScore);
        session.setAttribute("log", user);
        
        RequestDispatcher dispatcher = request.getRequestDispatcher("/mypage");
        dispatcher.forward(request, response);
    }
}
