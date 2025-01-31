package admin.action;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;
import user.model.User;

@WebListener
public class DailySessionTrackingListener implements HttpSessionListener {
    private static final Set<String> activeSessions = Collections.synchronizedSet(new HashSet<>());
    private static final Set<String> dailyUsers = Collections.synchronizedSet(new HashSet<>());

    public DailySessionTrackingListener() {
        scheduleDailyReset();
    }

    @Override
    public void sessionCreated(HttpSessionEvent event) {
        HttpSession session = event.getSession();
        String sessionId = session.getId();
        activeSessions.add(sessionId);

        if (session.getAttribute("log") != null) {
        	User user = (User) session.getAttribute("log");
            dailyUsers.add(user.getUserCode());
            System.out.println("NdailyUsers add: " + user.getUserCode());
        } 
        System.out.println("New session created: " + sessionId);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent event) {
        HttpSession session = event.getSession();
        String sessionId = session.getId();
        activeSessions.remove(sessionId);
        System.out.println("Session destroyed: " + sessionId);
    }

    public static Set<String> getActiveSessions() {
        return activeSessions;
    }

    public static Set<String> getDailyUsers() {
        return dailyUsers;
    }

    // 매일 자정(00:00)에 초기화하는 스케줄러 설정
    private void scheduleDailyReset() {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        long initialDelay = computeInitialDelay();
        long period = 24 * 60 * 60; // 24시간 (초 단위)

        scheduler.scheduleAtFixedRate(() -> {
            dailyUsers.clear();
            System.out.println("Daily visitor data reset at midnight.");
        }, initialDelay, period, TimeUnit.SECONDS);
    }

    // 현재 시간에서 자정까지 남은 시간을 계산
    private long computeInitialDelay() {
        LocalDateTime now = LocalDateTime.now(ZoneId.of("Asia/Seoul")); // 한국 시간 기준
        LocalDateTime midnight = now.toLocalDate().plusDays(1).atStartOfDay(); // 내일 00:00

        Duration duration = Duration.between(now, midnight);
        return duration.getSeconds();
    }
}