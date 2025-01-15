package notice.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import notice.model.NoticeDao;
import notice.model.NoticeRequestDto;

import java.io.IOException;
import java.sql.Timestamp;

public class WirteFormAction extends HttpServlet {
	private static final long serialVersionUID = 1L;

	    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	        	    	
	    	String adminCode = request.getParameter("adminCode");
	        String title = request.getParameter("title");
	        String content = request.getParameter("content");
	        int status = Integer.parseInt(request.getParameter("status"));
	        Timestamp resDate = Timestamp.valueOf(request.getParameter("resDate"));
	        Timestamp closeDate = Timestamp.valueOf(request.getParameter("closeDate"));
	        
	        NoticeRequestDto noticeDto = new NoticeRequestDto(adminCode, title, content, status, resDate, closeDate);
	        
	        NoticeDao noticeDao = NoticeDao.getInstance();
	        noticeDao.createNotice(noticeDto);
	        
	        response.sendRedirect("/list");
	    }
}
