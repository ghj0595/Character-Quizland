package admin.action;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import user.model.UserDao;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Calendar;

@WebServlet("/UpdateUserCloseDate")
public class UpdateUserCloseDate extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String userCode = request.getParameter("userCode");
		
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_YEAR, 7);
		
		Timestamp closeDate = new Timestamp(calendar.getTimeInMillis());
			
		UserDao userDao = UserDao.getInstance();
		userDao.updateUserCloseDate(userCode, closeDate);
		
		response.sendRedirect("/user");
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException { 
		doPost(request, response); 
	}

}
