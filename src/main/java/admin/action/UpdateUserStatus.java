package admin.action;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import user.model.UserDao;

import java.io.IOException;

import admin.model.Admin;

@WebServlet("/UpdateUserStatus")
public class UpdateUserStatus extends HttpServlet {
	private static final long serialVersionUID = 1L;       

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 HttpSession session = request.getSession();
	     Admin admin = (Admin) session.getAttribute("admin");
		
		String userCode = request.getParameter("userCode");
		int status = Integer.parseInt(request.getParameter("status"));
		
		UserDao userDao = UserDao.getInstance();
		userDao.updateUserStatus(userCode, status, admin.getCode());
		
		response.sendRedirect("/user");
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException { 
		doPost(request, response); 
	}

}
