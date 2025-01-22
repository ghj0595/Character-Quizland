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

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String uri = req.getRequestURI();
        HttpSession session = req.getSession(false);

        if (session == null) {
            session = req.getSession(true);
        }

        // Logging for debugging
        System.out.println("Requested URI: " + uri);

        // Always update session attributes
        updateSessionAttributes(session);

        // Static resources
        if (isStaticResource(uri)) {
            chain.doFilter(request, response);
            return;
        }

        // Authentication and Authorization logic
        if (isLoginPage(uri)) {
            handleLoginRedirect(session, res, chain, request, response);
            return;
        }

        if (isJoinPage(uri)) {
            handleJoinRedirect(session, res, chain, request, response);
            return;
        }

        if (isRestrictedForUsers(uri)) {
            if (session.getAttribute("log") == null) {
                res.sendRedirect("/login");
            } else {
                chain.doFilter(request, response);
            }
            return;
        }

        if (isRestrictedForAdmins(uri)) {
            if (session.getAttribute("admin") == null) {
                res.sendRedirect("/loginAdmin");
            } else {
                chain.doFilter(request, response);
            }
            return;
        }

        if (uri.equals("/")) {
            if (session.getAttribute("admin") != null) {
                res.sendRedirect("/manager");
            } else {
                chain.doFilter(request, response);
            }
            return;
        }

        // Default case
        chain.doFilter(request, response);
    }

    private void updateSessionAttributes(HttpSession session) {
        UserDao userDao = UserDao.getInstance();
        ArrayList<User> rankList = userDao.findUserRank();
        session.setAttribute("rankList", rankList);

        NoticeDao noticeDao = NoticeDao.getInstance();
        ArrayList<Notice> noticeList = noticeDao.findActiveNotices();
        session.setAttribute("noticeList", noticeList);
    }

    private boolean isStaticResource(String uri) {
        return uri.endsWith(".css") || uri.endsWith(".js") || uri.endsWith(".png") ||
               uri.endsWith(".jpg") || uri.endsWith(".jpeg") || uri.endsWith(".gif") ||
               uri.startsWith("/service");
    }

    private boolean isLoginPage(String uri) {
        return uri.equals("/login") || uri.equals("/loginAdmin");
    }

    private boolean isJoinPage(String uri) {
        return uri.equals("/join");
    }

    private boolean isRestrictedForUsers(String uri) {
        return uri.equals("/mypage") || uri.equals("/game");
    }

    private boolean isRestrictedForAdmins(String uri) {
        return uri.equals("/manager") || uri.equals("/QuizListAction") ||
               uri.equals("/quizzes") || uri.equals("/list") || uri.equals("/user");
    }

    private void handleLoginRedirect(HttpSession session, HttpServletResponse res,
                                     FilterChain chain, ServletRequest request,
                                     ServletResponse response) throws IOException, ServletException {
        if (session.getAttribute("log") != null) {
            res.sendRedirect("/");
        } else if (session.getAttribute("admin") != null) {
            res.sendRedirect("/manager");
        } else {
            chain.doFilter(request, response);
        }
    }

    private void handleJoinRedirect(HttpSession session, HttpServletResponse res,
                                    FilterChain chain, ServletRequest request,
                                    ServletResponse response) throws IOException, ServletException {
        if (session.getAttribute("log") != null) {
            res.sendRedirect("/service/users?command=view");
        } else if (session.getAttribute("admin") != null) {
            res.sendRedirect("/manager");
        } else {
            chain.doFilter(request, response);
        }
    }
}
