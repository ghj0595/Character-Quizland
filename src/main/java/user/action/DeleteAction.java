package user.action;

import java.io.IOException;

import controller.Action;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import user.model.User;
import user.model.UserDao;

public class DeleteAction implements Action {

	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("log");
		String password = request.getParameter("password");

		if(user == null) {
			response.sendRedirect("/login");
			return;
		}
		
		String url = "";

		if(user.checkPassword(password)) {
			UserDao userDao = UserDao.getInstance();
			userDao.deleteUserByCode(user.getUserCode());
			
			session.removeAttribute("log");
			session.invalidate();
			
			url = "/";
			
		} else {
			url = "/delete";
		}
		response.sendRedirect(url);
	}

}