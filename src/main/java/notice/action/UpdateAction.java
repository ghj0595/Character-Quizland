package notice.action;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import notice.model.NoticeDao;
import notice.model.NoticeResponseDto;

import java.io.IOException;
import java.sql.Timestamp;

@WebServlet("/notice/update")
public class UpdateAction extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		        int code = Integer.parseInt(request.getParameter("code"));
		        String adminCode = request.getParameter("adminCode");  
		        String title = request.getParameter("title");
		        String content = request.getParameter("content");
		        String regDateParam = request.getParameter("regDate");		        		
		        String resDateParam = request.getParameter("startDate");
		        String closeDateParam = request.getParameter("endDate");		 

		        Timestamp resDate = null;
		        Timestamp closeDate = null;
		        Timestamp regDate = Timestamp.valueOf(regDateParam);
		        Timestamp modDate = new Timestamp(System.currentTimeMillis());

		        if (resDateParam != null && !resDateParam.isEmpty()) {
		            resDate = Timestamp.valueOf(resDateParam + " 00:00:00");
		        } else {
		        	resDate = new Timestamp(System.currentTimeMillis());
		        }
		        if (closeDateParam != null && !closeDateParam.isEmpty()) {
		            closeDate = Timestamp.valueOf(closeDateParam + " 23:59:59");
		        }
		        
		        System.out.println(resDate);
		        
		        Timestamp currentDate = new Timestamp(System.currentTimeMillis());
		        if (resDate != null && resDate.before(currentDate)) {
		            request.setAttribute("errorMessage", "게시일을 다시 확인해주세요.");
		            request.getRequestDispatcher("/notice").forward(request, response);
		            return;
		        }
		        
		        if (closeDate != null) {
		            if (resDate != null && closeDate.before(resDate)) {
		                request.setAttribute("errorMessage", "만료일을 다시 확인해주세요.");
		                request.getRequestDispatcher("/notice").forward(request, response);
		                return;
		            } else if (closeDate.before(currentDate)) {
		                request.setAttribute("errorMessage", "만료일을 다시 확인해주세요.");
		                request.getRequestDispatcher("/notice").forward(request, response);
		                return;
		            }
		        }

		        NoticeDao noticeDao = NoticeDao.getInstance();
		        NoticeResponseDto notice = new NoticeResponseDto(code, adminCode, title, content, resDate, closeDate, regDate, modDate);
		        noticeDao.updateNotice(notice);

		        response.sendRedirect("/list");
		    }
		

	

}
