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

public class JoinAction implements Action {

	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String code = request.getParameter("code");
		String password = request.getParameter("password");
		String name = request.getParameter("name");

		UserRequestDto userDto = new UserRequestDto(code, password, name);

		UserDao userDao = UserDao.getInstance();

		boolean isValid = true;

		User user = null;
		user = userDao.findUserByCode(code);

		if(user != null) {
			isValid = false;
			System.err.println("회원 아이디 중복 발생");	
		}
		
		HttpSession session = request.getSession();
		
		if(!isValid) {
			session.setAttribute("userData", userDto);
			response.sendRedirect("/join");
			
		} else {
			session.removeAttribute("userData");
			
			userDao.createUser(userDto);
			response.sendRedirect("/login");
		}
	}

}