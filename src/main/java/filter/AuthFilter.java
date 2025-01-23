package filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import notice.model.Notice;
import notice.model.NoticeDao;
import user.model.User;
import user.model.UserDao;
import java.io.IOException;
import java.util.ArrayList;

@WebFilter("/*")
public class AuthFilter extends HttpFilter implements Filter {
	private static final long serialVersionUID = 1L;

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;

		String uri = req.getRequestURI();
		HttpSession session = req.getSession(false);

		if (session == null) {
			session = req.getSession(true);
		}

		System.out.println("Requested URI: " + uri);

		UserDao userdao = UserDao.getInstance();
		ArrayList<User> rankList = userdao.findUserRank();
		session.setAttribute("rankList", rankList);

		NoticeDao noticeDao = NoticeDao.getInstance();
		ArrayList<Notice> noticeList = noticeDao.findActiveNotices();
		session.setAttribute("noticeList", noticeList);

		if (uri.endsWith(".css") || uri.endsWith(".js") || uri.endsWith(".png") || uri.endsWith(".jpg")
				|| uri.endsWith(".jpeg") || uri.endsWith(".gif") || uri.startsWith("/service")) {
			chain.doFilter(request, response);
			return;
		}

		if (uri.equals("/login") || uri.equals("/loginAdmin")) {
			if (session.getAttribute("log") != null) {
				res.sendRedirect("/");
				return;
			} else if (session.getAttribute("admin") != null) {
				res.sendRedirect("/manager");
				return;
			} else {
				chain.doFilter(request, response);
				return;
			}
		}

		if (uri.equals("/join")) {
			if (session.getAttribute("log") != null || session.getAttribute("admin") != null) {
				res.sendRedirect("/");
				return;
			} else {
				chain.doFilter(request, response);
				return;
			}
		}

		if (uri.equals("/list") || uri.equals("/user") || uri.equals("/manager") || uri.equals("/QuizListAction")) {
			if (session.getAttribute("admin") == null) {
				res.sendRedirect("/loginAdmin");
				return;
			} else {
				chain.doFilter(request, response);
				return;
			}
		}
		
		if (uri.equals("/quizzes")) {
				res.sendRedirect("/QuizListAction");
				return;
			} 

		if (uri.equals("/game") || uri.equals("/mypage") || uri.equals("/total") || uri.equals("/result")) {
			if (session.getAttribute("log") == null) {
				res.sendRedirect("/login");
				return;
			} else {
				chain.doFilter(request, response);
				return;
			}
		}

		if (uri.equals("/")) {
			if (session.getAttribute("admin") != null) {
				res.sendRedirect("/manager");
				return;
			} else {
				chain.doFilter(request, response);
				return;
			}
		}

		chain.doFilter(request, response);
	}
}