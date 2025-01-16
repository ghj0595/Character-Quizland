package solve.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

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

	public ArrayList<Solve> findMySolveAll(String userCode) {
		ArrayList<Solve> list = new ArrayList<>();

		conn = DBManager.getConnection();

		if (conn != null) {
			String sql = "SELECT * FROM solve WHERE=?";

			try {
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, userCode);
				rs = pstmt.executeQuery();

				while (rs.next()) {
					int code = rs.getInt(1);
					int quizCode = rs.getInt(3);
					int score = rs.getInt(4);
					int timer = rs.getInt(5);
					Timestamp regDate = rs.getTimestamp(6);
					Timestamp modDate = rs.getTimestamp(7);

					Solve solve = new Solve(code, userCode, quizCode, score, timer, regDate, modDate);

					list.add(solve);
				}

			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				DBManager.close(conn, pstmt, rs);
			}
		}
		return list;
	}

}
