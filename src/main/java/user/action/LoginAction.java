package user.action;

import java.io.IOException;

import controller.Action;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import user.model.User;
import user.model.UserDao;

public class LoginAction implements Action {

    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String code = request.getParameter("code");
        String password = request.getParameter("password");

        UserDao userDao = UserDao.getInstance();
        User user = userDao.findUserByCode(code);

        if (user != null && user.checkPassword(password)) {
        	if(user.getStatus()==0) {
        		HttpSession session = request.getSession();
                session.setAttribute("log", user);
                response.sendRedirect("/");
        	} else {
        		request.setAttribute("loginError", "정지된 아이디입니다.");
                request.getRequestDispatcher("/login").forward(request, response);
        	}
        } else {
            request.setAttribute("loginError", "아이디 또는 비밀번호가 틀렸습니다.");
            request.getRequestDispatcher("/login").forward(request, response);
        }
    }
}