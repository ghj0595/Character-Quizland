package admin.action;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import user.model.UserDao;

import java.io.IOException;

@WebServlet("/UpdateUserStatus")
public class UpdateUserStatus extends HttpServlet {
	private static final long serialVersionUID = 1L;       

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String userCode = request.getParameter("userCode");
		int status = Integer.parseInt(request.getParameter("status"));
		
		UserDao userDao = UserDao.getInstance();
		userDao.updateUserStatus(userCode, status);
		
		response.sendRedirect("/user");
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException { 
		doPost(request, response); 
	}

}
