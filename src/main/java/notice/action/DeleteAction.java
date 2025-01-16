package notice.action;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import notice.model.NoticeDao;

import java.io.IOException;

@WebServlet("/notice/delete")
public class DeleteAction extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int code = Integer.parseInt(request.getParameter("code"));
		
		NoticeDao noticeDao = NoticeDao.getInstance();
		noticeDao.deleteNoticeByCode(code);
		
		response.sendRedirect("/list");
	}
}
