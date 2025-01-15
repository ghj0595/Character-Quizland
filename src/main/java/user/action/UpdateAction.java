package user.action;

import java.io.IOException;

import controller.Action;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import user.model.User;
import user.model.UserDao;
import user.model.UserRequestDto;

public class UpdateAction implements Action {

	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("log");
		
		if(user == null) {
			response.sendRedirect("/login");
			return;
		}
		
		String code = user.getUserCode();
		String password = request.getParameter("password");
		String newPassword = request.getParameter("new-password");

		UserDao userDao = UserDao.getInstance();

		UserRequestDto userDto = new UserRequestDto();

		userDto.setUserCode(code);

		if(!newPassword.equals("") && !password.equals(newPassword)) {
			if(user.checkPassword(password))
				userDto.setPassword(newPassword);
		}
		
		user = userDao.updateUser(userDto);
		session.setAttribute("log", user);

		response.sendRedirect("/myPage");
	}

}