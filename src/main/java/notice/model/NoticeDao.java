package notice.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import util.DBManager;

public class NoticeDao {

	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;

	private NoticeDao() {

	}

	private static NoticeDao instance = new NoticeDao();

	public static NoticeDao getInstance() {
		return instance;
	}

	public void createNotice(NoticeRequestDto noticeDto) {

		conn = DBManager.getConnection();

		if (conn != null) {
			try {
				String sql = "INSERT INTO notice (admin_code, title, content, status, res_date, close_date) VALUES (?, ?, ?, ?, ?, ?)";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, noticeDto.getAdminCode());
				pstmt.setString(2, noticeDto.getTitle());
				pstmt.setString(3, noticeDto.getContent());					
				
				if(noticeDto.getResDate() != null) {					
					pstmt.setInt(4, 0);
				} else {
					pstmt.setInt(4, 1);					
				}

				pstmt.setTimestamp(5, noticeDto.getResDate());
				pstmt.setTimestamp(6, noticeDto.getCloseDate());
				pstmt.executeUpdate();

				System.out.println("데이터 베이스 연동 성공!\n" + pstmt);
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				DBManager.close(conn, pstmt);
			}
		} else {
			System.out.println("데이터 베이스 연동 실패...");
		}
	}

	public NoticeResponseDto readNoticeByCode(int code) {
		NoticeResponseDto notice = null;

		conn = DBManager.getConnection();

		String sql = "SELECT * FROM notice WHERE code = ?";

		try {

			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, code);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				notice = new NoticeResponseDto(rs.getInt("code"), rs.getString("admin_code"), rs.getString("title"),
						rs.getString("content"), rs.getInt("status"), rs.getTimestamp("res_date"),
						rs.getTimestamp("close_date"), rs.getTimestamp("reg_date"), rs.getTimestamp("mod_date"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt, rs);
		}
		return notice;
	}

	public List<NoticeResponseDto> readAllNotices() {
		List<NoticeResponseDto> notices = new ArrayList<>();

		conn = DBManager.getConnection();

		String sql = "SELECT * FROM notice";

		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				NoticeResponseDto notice = new NoticeResponseDto(rs.getInt("code"), rs.getString("admin_code"),
						rs.getString("title"), rs.getString("content"), rs.getInt("status"),
						rs.getTimestamp("res_date"), rs.getTimestamp("close_date"), rs.getTimestamp("reg_date"),
						rs.getTimestamp("mod_date"));
				notices.add(notice);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt, rs);
		}
		return notices;
	}

	public void updateNotice(NoticeResponseDto notice) {

		conn = DBManager.getConnection();

		String sql = "UPDATE notice SET admin_code = ?, title = ?, content = ?, status = ?, res_date = ?, close_date = ?, reg_date = ?, mod_date = ? WHERE code = ?";

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, notice.getAdminCode());
			pstmt.setString(2, notice.getTitle());
			pstmt.setString(3, notice.getContent());
			
			Timestamp currentDate = new Timestamp(System.currentTimeMillis());

			if (notice.getResDate() != null && notice.getResDate().before(currentDate)) {
				pstmt.setInt(4, 1);
			} else {
				pstmt.setInt(4, 0);
			}
			
			pstmt.setTimestamp(5, notice.getResDate());
			pstmt.setTimestamp(6, notice.getCloseDate());
			pstmt.setTimestamp(7, notice.getRegDate());
			pstmt.setTimestamp(8, notice.getModDate());
			pstmt.setInt(9, notice.getCode());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt);
		}
	}

	public void deleteNoticeByCode(int code) {

		conn = DBManager.getConnection();

		String sql = "DELETE FROM notice WHERE code = ?";

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, code);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt);
		}
	}

	public ArrayList<Notice> findActiveNotices() {
		ArrayList<Notice> notices = new ArrayList<>();

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		String sql = "SELECT code, title FROM notice WHERE status=?";

		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, 1);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				Notice notice = new Notice(rs.getInt("code"), rs.getString("title"));
				notices.add(notice);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt, rs);
		}

		return notices;
	}

}