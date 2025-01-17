package admin.action;

import java.io.IOException;

import admin.model.Admin;
import admin.model.AdminDao;
import controller.Action;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class LoginAdminAction implements Action {

	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String code = request.getParameter("code");
		String password = request.getParameter("password");

		AdminDao adminDao = AdminDao.getInstance();
		Admin admin = adminDao.findAdminByCode(code);

		String url = "";

		if(admin != null && admin.checkPassword(password)) {
			HttpSession session = request.getSession();
			session.setAttribute("admin", admin);
			url = "/manager";
		} else {
			url = "/loginAdmin";
		}

		response.sendRedirect(url);
	}

}