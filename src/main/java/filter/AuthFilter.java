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

import java.io.IOException;

public class AuthFilter extends HttpFilter implements Filter {
	private static final long serialVersionUID = 1L;

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;

		String uri = req.getRequestURI();
		HttpSession session = req.getSession(false);

		if (uri.endsWith(".css") || uri.endsWith(".js") || uri.endsWith(".png") || uri.endsWith(".jpg")
				|| uri.endsWith(".jpeg") || uri.endsWith(".gif") || uri.startsWith("/service")) {
			chain.doFilter(request, response);
			return;
		}

		if (uri.equals("/login") || uri.equals("/loginAdmin")) {
			if (session == null || (session.getAttribute("log") == null && session.getAttribute("admin") == null)) {
				chain.doFilter(request, response);
			} else if (session.getAttribute("log") != null) {
				res.sendRedirect("/");
			} else if (session.getAttribute("admin") != null) {
				res.sendRedirect("/manager");
			}
			return;
		}

		if (uri.equals("/join")) {
			if (session == null || session.getAttribute("log") == null) {
				chain.doFilter(request, response);
			} else {
				res.sendRedirect("/service/users?command=view");
			}
			return;
		}

		if (uri.equals("/mypage")) {
			if (session == null || session.getAttribute("log") == null) {
				res.sendRedirect("/login");
			} else {
				chain.doFilter(request, response);
			}
			return;
		}

		if (uri.equals("/") || uri.equals("/loginAdmin") || uri.equals("/manager")) {
			chain.doFilter(request, response);
			return;
		}

		if (session == null || session.getAttribute("log") == null) {
			res.sendRedirect("/login");
			return;
		}	

		chain.doFilter(request, response);
	}
}
