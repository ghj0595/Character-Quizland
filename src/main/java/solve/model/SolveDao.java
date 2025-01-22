package solve.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import util.DBManager;

public class SolveDao {

	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;

	private SolveDao() {

	}

	private static SolveDao instance = new SolveDao();

	public static SolveDao getInstance() {
		return instance;
	}

	public SolveResponseDto createSolve(SolveRequestDto solveDto) {
		conn = DBManager.getConnection();

		if (conn != null) {
			try {
				String sql = "INSERT INTO solve(user_code, quiz_code, score, timer) VALUES(?, ?, ?, ?)";

				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, solveDto.getUserCode());
				pstmt.setInt(2, solveDto.getQuizCode());
				pstmt.setInt(3, solveDto.getScore());
				pstmt.setInt(4, solveDto.getTimer());
				pstmt.execute();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				DBManager.close(conn, pstmt);
			}
		}
		
		return findLatestSolveByUser(solveDto.getUserCode());
	}

	public int getTotalSize() {
		int size = 0;

		conn = DBManager.getConnection();

		String sql = "SELECT COUNT(*) FROM solve";

		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				size = rs.getInt(1);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt, rs);
		}

		return size;
	}

	public int getSizeByUser(String userCode) {
		conn = DBManager.getConnection();

		int size = 0;
		
		String sql = "SELECT COUNT(*) FROM solve WHERE user_code=? GROUP BY user_code";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userCode);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				size = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt, rs);
		}
		return size;
	}
	
	
	public int getTotalScoreByUser(String userCode) {
		conn = DBManager.getConnection();

		int score = 0;
		
		String sql = "SELECT SUM(score) FROM solve WHERE user_code=? GROUP BY user_code";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userCode);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				score = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt, rs);
		}
		return score;
	}
	
	public List<SolveResponseDto> findSolveAll(int page) {
		return findSolveAll(page, 10);
	}
	
	public List<SolveResponseDto> findSolveAll(int page, int line) {
		List<SolveResponseDto> list = new ArrayList<>();

		conn = DBManager.getConnection();

		String sql = "SELECT * FROM solve LIMIT ? OFFSET ?";

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, line);
			pstmt.setInt(2, (page - 1) * line);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				int code = rs.getInt(1);
				String userCode = rs.getString(2);
				int quizCode = rs.getInt(3);
				int score = rs.getInt(4);
				int timer = rs.getInt(5);
				Timestamp regDate = rs.getTimestamp(6);
				Timestamp modDate = rs.getTimestamp(7);

				SolveResponseDto solveDto = new SolveResponseDto(code, userCode, quizCode, score, timer, regDate, modDate);
				list.add(solveDto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt, rs);
		}
		return list;
	}
	
	public JSONArray findUserSolveAll(int page) {
		return findUserSolveAll(page, 10);
	}

	public JSONArray findUserSolveAll(int page, int line) {
		JSONArray list = new JSONArray();

		conn = DBManager.getConnection();
 
		String sql = "SELECT u.code,u.name,u.best_score, SUM(s.timer)/COUNT(*)/1000,COUNT(*),COUNT(CASE WHEN timer <= 1000 THEN 1 END),COUNT(CASE WHEN timer <= 1000 THEN 1 END) * 100.0 / COUNT(*) AS percentage FROM  users u JOIN solve s ON u.code=s.user_code GROUP BY u.code ORDER BY percentage DESC LIMIT ? OFFSET ?";

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, line);
			pstmt.setInt(2, (page - 1) * line);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				String code = rs.getString(1);
				String name = rs.getString(2);
				int bestScore = rs.getInt(3);
				double avgTimer = rs.getDouble(4);
				int totalCount = rs.getInt(5);
				int suspiciousCount = rs.getInt(6);
				double suspiciousRate= rs.getDouble(7);

				JSONObject info=new JSONObject();
				info.put("code", code);
				info.put("name", name);
				info.put("best_score", bestScore);
				info.put("avg_timer", avgTimer);
				info.put("total_count", totalCount);
				info.put("suspicious_count", suspiciousCount);
				info.put("suspicious_rate", suspiciousRate);
				list.put(info);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt, rs);
		}
		return list;
	}
	
	public JSONArray findQuizSolveAll(int page) {
		return findQuizSolveAll(page, 10);
	}
	
	public JSONArray findQuizSolveAll(int page, int line) {
		JSONArray list = new JSONArray();

		conn = DBManager.getConnection();
 
		String sql = "SELECT q.code,q.type,q.content_id,q.people_id,COUNT(*), SUM(s.score)/COUNT(*) as score, SUM(s.timer)/COUNT(*)/1000  FROM  quiz q JOIN solve s ON q.code=s.quiz_code GROUP BY q.code ORDER BY score ASC LIMIT ? OFFSET ?";

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, line);
			pstmt.setInt(2, (page - 1) * line);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				int code = rs.getInt(1);
				int type = rs.getInt(2);
				int contentId = rs.getInt(3);
				int peopleId = rs.getInt(4);
				int totalCount = rs.getInt(5);
				double avgScore = rs.getDouble(6);
				double avgTimer= rs.getDouble(7);

				JSONObject info=new JSONObject();
				info.put("code", code);
				info.put("type", type);
				info.put("content_id", contentId);
				info.put("people_id", peopleId);
				info.put("total_count", totalCount);
				info.put("average_score", avgScore);
				info.put("average_timer", avgTimer);
				list.put(info);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt, rs);
		}
		return list;
	}
	
	public SolveResponseDto findSolveByCode(int code) {
		SolveResponseDto solve = null;

		conn = DBManager.getConnection();

		String sql = "SELECT * FROM solve WHERE code=?";

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, code);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				String userCode = rs.getString(2);
				int quizCode = rs.getInt(3);
				int score = rs.getInt(4);
				int timer = rs.getInt(5);
				Timestamp regDate = rs.getTimestamp(6);
				Timestamp modDate = rs.getTimestamp(7);

				solve = new SolveResponseDto(code, userCode, quizCode, score, timer, regDate, modDate);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt, rs);
		}
		return solve;
	}
	
	public SolveResponseDto findLatestSolveByUser(String userCode) {
		SolveResponseDto solve = null;
		System.out.println("findLatestSolveByUser");
		conn = DBManager.getConnection();

		String sql = "SELECT * FROM solve WHERE user_code=? ORDER BY code DESC LIMIT 1";
		System.out.println(sql);
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userCode);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				int code = rs.getInt(1);
				int quizCode = rs.getInt(3);
				int score = rs.getInt(4);
				int timer = rs.getInt(5);
				Timestamp regDate = rs.getTimestamp(6);
				Timestamp modDate = rs.getTimestamp(7);
				System.out.println(code);
				solve = new SolveResponseDto(code, userCode, quizCode, score, timer, regDate, modDate);
				System.out.println(solve);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt, rs);
		}
		return solve;
	}
	
	public SolveResponseDto findLatestSolveByUserQuiz(String userCode, int quizCode) {
		SolveResponseDto solve = null;

		conn = DBManager.getConnection();

		String sql = "SELECT * FROM solve WHERE user_code=? AND quiz_code=? ORDER BY code DESC LIMIT 1";

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userCode);
			pstmt.setInt(2, quizCode);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				int code = rs.getInt(1);
				int score = rs.getInt(4);
				int timer = rs.getInt(5);
				Timestamp regDate = rs.getTimestamp(6);
				Timestamp modDate = rs.getTimestamp(7);

				solve = new SolveResponseDto(code, userCode, quizCode, score, timer, regDate, modDate);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt, rs);
		}
		return solve;
	}
	
	public SolveResponseDto updateSolve(int code, SolveRequestDto solveDto) {
		SolveResponseDto solve = null;

		conn = DBManager.getConnection();

		
		try {
			String sql = "UPDATE solve SET score=?, timer=? WHERE code=?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, solveDto.getScore());
			pstmt.setInt(2, solveDto.getTimer());
			pstmt.setInt(3, code);
			pstmt.execute();

			solve = findSolveByCode(code);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt);
		}

		return solve;
	}
	
	public void deleteSolveByCode(int code) {
		conn = DBManager.getConnection();

		String sql = "DELETE FROM solve WHERE code=?";

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, code);
			pstmt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt);
		}
	}
}
