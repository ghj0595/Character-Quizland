package user.action;

import java.io.IOException;
import java.util.ArrayList;

import controller.Action;
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
        ArrayList<Solve> solvelist = solveDao.findMySolveAll(user.getUserCode());

        int listCount = solvelist.size();

        int totalScore = 0;
        for (Solve solve : solvelist) {
            totalScore += solve.getScore();
        }

        double avgScore = (listCount > 0) ? (double) totalScore / listCount : 0;

        session.setAttribute("total-game", listCount);
        session.setAttribute("total-score", totalScore);
        session.setAttribute("avg-score", avgScore);
        session.setAttribute("log", user);

        response.sendRedirect("/myPage");
    }
}
