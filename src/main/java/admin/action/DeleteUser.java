package admin.action;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import user.model.User;
import user.model.UserDao;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;


@WebServlet("/DeleteUser")
public class DeleteUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String code = request.getParameter("userCode");
		
		UserDao userDao = UserDao.getInstance();
		userDao.deleteUserByCode(code);
		
		response.sendRedirect("/user");

	}


}
