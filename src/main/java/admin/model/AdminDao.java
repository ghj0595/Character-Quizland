package admin.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import util.DBManager;

public class AdminDao {
	
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;

	private AdminDao() {
		
	}

	private static AdminDao instance = new AdminDao();

	public static AdminDao getInstance() {
		return instance;
	}
	
	public Admin findAdminByCode(String code) {
		Admin admin = null;

		conn = DBManager.getConnection();

		String sql = "SELECT * FROM `admin` WHERE code=?";

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, code);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				String password = rs.getString(2);

				admin = new Admin(code, password);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt, rs);
		}
		return admin;
	}
	
}