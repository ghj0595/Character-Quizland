package quiz.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import solve.model.SolveDao;
import util.DBManager;

public class QuizDao {
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;

	private QuizDao() {
	}

	private static QuizDao instance = new QuizDao();

	public static QuizDao getInstance() {
		return instance;
	}

	public QuizResponseDto createQuiz(QuizRequestDto quizDto) {
		QuizResponseDto quiz = null;

		conn = DBManager.getConnection();

		if (conn != null) {
			try {
				String sql = "INSERT INTO quiz(type, content_id, people_id) VALUES(?, ?, ?)";

				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, quizDto.getType());
				pstmt.setInt(2, quizDto.getContentId());
				pstmt.setInt(3, quizDto.getPeopleId());
				pstmt.execute();

				quiz = findQuizByTypeContent(quizDto.getType(), quizDto.getContentId());
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				DBManager.close(conn, pstmt);
			}
		}
		return quiz;
	}

	public int getTotalSize() {
		int size = 0;

		conn = DBManager.getConnection();

		String sql = "SELECT COUNT(*) FROM quiz";

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

	public List<QuizResponseDto> findQuizAll(int page) {
		List<QuizResponseDto> list = new ArrayList<>();

		conn = DBManager.getConnection();

		String sql = "SELECT * FROM quiz LIMIT 10 OFFSET ?";

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, (page - 1) * 10);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				int code = rs.getInt(1);
				int type = rs.getInt(2);
				int contentId = rs.getInt(3);
				int peopleId = rs.getInt(4);
				String contentLink = "https://api.themoviedb.org/3/movie/" + contentId;
				String peopleLink = "https://api.themoviedb.org/3/person/" + peopleId;

				QuizResponseDto quizDto = new QuizResponseDto(code, type, contentId, peopleId);
				quizDto.setContentLink(contentLink);
				quizDto.setPeopleLink(peopleLink);
				
				list.add(quizDto);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt, rs);
		}
		return list;
	}

	public QuizResponseDto findQuizByCode(int code) {
		QuizResponseDto quiz = null;

		conn = DBManager.getConnection();

		String sql = "SELECT * FROM quiz WHERE code=?";

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, code);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				int type = rs.getInt(2);
				int contentId = rs.getInt(3);
				int peopleId = rs.getInt(4);

				quiz = new QuizResponseDto(code, type, contentId, peopleId);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt, rs);
		}
		return quiz;
	}

	public QuizResponseDto findQuizByTypeContent(int type, int contentId) {
		QuizResponseDto quiz = null;

		conn = DBManager.getConnection();

		String sql = "SELECT * FROM quiz WHERE type=? AND content_id=?";

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, type);
			pstmt.setInt(2, contentId);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				int code = rs.getInt(1);
				int peopleId = rs.getInt(4);

				quiz = new QuizResponseDto(code, type, contentId, peopleId);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt, rs);
		}
		return quiz;
	}

	public int findQuizcodeByIndex(int Index) {
		int code = 0;

		conn = DBManager.getConnection();

		String sql = "SELECT code FROM quiz LIMIT 1 OFFSET ?";

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, Index);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				code = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt, rs);
		}
		return code;
	}

	public QuizResponseDto updateQuiz(int code, QuizRequestDto quizDto) {
		QuizResponseDto quiz = null;

		conn = DBManager.getConnection();

		String sql = "UPDATE quiz SET type=?, content_id=?, people_id=? WHERE code=?";

		try {
			pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, quizDto.getType());
			pstmt.setInt(2, quizDto.getContentId());
			pstmt.setInt(3, quizDto.getPeopleId());
			pstmt.setInt(4, code);
			pstmt.execute();

			quiz = findQuizByCode(code);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt);
		}

		return quiz;
	}

	public void deleteQuizByCode(int code) {
		conn = DBManager.getConnection();

		String sql = "DELETE FROM quiz WHERE code=?";

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
