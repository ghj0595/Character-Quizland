package user.action;

import java.io.IOException;
import java.util.Random;

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
		User user = userDao.findUserByCode(code);

		if (user != null) {
			isValid = false;
			System.err.println("회원 아이디 중복 발생");
		}

		User duplUser = userDao.findUserByName(name);
		if (duplUser != null) {
			String newName = randomName(name, userDao);
			userDto.setName(newName);
			System.out.println("닉네임 중복 발생. 새로운 닉네임: " + newName);
		}

		HttpSession session = request.getSession();

		if (!isValid) {
			response.sendRedirect("/join");
		} else {
			session.removeAttribute("userData");
			userDao.createUser(userDto);
			response.sendRedirect("/login");
		}
	}

	private String randomName(String baseName, UserDao userDao) {
		String newName = baseName + "#" + randomNumber();
		while (userDao.findUserByName(newName) != null) {
			newName = baseName + "#" + randomNumber();
		}
		return newName;
	}

	private String randomNumber() {
		Random ran = new Random();
		int randomNum = ran.nextInt(1000);
		return Integer.toString(randomNum);
	}
}
