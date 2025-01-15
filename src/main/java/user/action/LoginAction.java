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

		String url = "";

		if(user != null && user.checkPassword(password)) {
			HttpSession session = request.getSession();
			session.setAttribute("log", user);
			url = "/";
		} else {
			url = "/login";
		}

		response.sendRedirect(url);
	}

}