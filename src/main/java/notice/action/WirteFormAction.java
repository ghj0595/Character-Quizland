package notice.action;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import notice.model.NoticeDao;
import notice.model.NoticeRequestDto;

import java.io.IOException;
import java.sql.Timestamp;

@WebServlet("/notice/write")
public class WirteFormAction extends HttpServlet {
	private static final long serialVersionUID = 1L;

	    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	        	    	
	    	String adminCode = request.getParameter("adminCode");
	        String title = request.getParameter("title");
	        String content = request.getParameter("content");
	        
	        int status = 1; 
	        
	        String resDateParam = request.getParameter("startDate"); 
	        String closeDateParam = request.getParameter("endDate"); 
	        
	        Timestamp resDate = null; 
	        Timestamp closeDate = null; 
	        
	        if (resDateParam != null && !resDateParam.isEmpty()) { 
	        	resDate = Timestamp.valueOf(resDateParam + " 00:00:00"); 
	        	status = 0;
	        	} 
	        if (closeDateParam != null && !closeDateParam.isEmpty()) { 
	        	closeDate = Timestamp.valueOf(closeDateParam + " 23:59:59");	        	
	        }
	        
	        NoticeRequestDto noticeDto = new NoticeRequestDto(adminCode, title, content, status, resDate, closeDate);
	        
	        NoticeDao noticeDao = NoticeDao.getInstance();
	        noticeDao.createNotice(noticeDto);	        
	        
	        response.sendRedirect("/list");
	    }
}
