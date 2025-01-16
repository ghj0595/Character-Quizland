package solve.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

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
		SolveResponseDto solve = null;

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
				
				solve = findLatestSolveByUser(solveDto.getUserCode());
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				DBManager.close(conn, pstmt);
			}
		}
		return solve;
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

	public List<SolveResponseDto> findSolveAll(int page) {
		List<SolveResponseDto> list = new ArrayList<>();

		conn = DBManager.getConnection();

		String sql = "SELECT * FROM solve LIMIT 10 OFFSET ?";

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, (page - 1) * 10);

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

		conn = DBManager.getConnection();

		String sql = "SELECT * FROM solve WHERE user_code=? ORDER BY code DESC LIMIT 1";

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

				solve = new SolveResponseDto(code, userCode, quizCode, score, timer, regDate, modDate);
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

		String sql = "UPDATE solve SET user_code=?, quiz_code=?, score=?, timer=? WHERE code=?";

		try {
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, solveDto.getUserCode());
			pstmt.setInt(2, solveDto.getQuizCode());
			pstmt.setInt(3, solveDto.getScore());
			pstmt.setInt(4, solveDto.getTimer());
			pstmt.setInt(5, code);
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
