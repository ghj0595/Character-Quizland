package user.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.mindrot.jbcrypt.BCrypt;

import util.DBManager;

public class UserDao {

	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;

	private UserDao() {

	}

	private static UserDao instance = new UserDao();

	public static UserDao getInstance() {
		return instance;
	}

	public void createUser(UserRequestDto userDto) {
		conn = DBManager.getConnection();

		String sql = "INSERT INTO users(code, `password`, `name`) VALUES(?, ?, ?)";

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userDto.getUserCode());

			String rawPassword = userDto.getPassword();
			String hashedPassword = BCrypt.hashpw(rawPassword, BCrypt.gensalt());

			pstmt.setString(2, hashedPassword);
			pstmt.setString(3, userDto.getName());

			pstmt.execute();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt);
		}
	}

	public List<User> findUserAll() {
		ArrayList<User> list = new ArrayList<>();

		conn = DBManager.getConnection();

		if (conn != null) {
			String sql = "SELECT * FROM users";

			try {
				pstmt = conn.prepareStatement(sql);
				rs = pstmt.executeQuery();

				while (rs.next()) {
					String userCode = rs.getString(1);
					String password = rs.getString(2);
					String name = rs.getString(3);
					int bestScore = rs.getInt(4);
					int status = rs.getInt(5);
					Timestamp closeDate = rs.getTimestamp(6);
					String managerCode = rs.getString(7);
					Timestamp regDate = rs.getTimestamp(8);
					Timestamp modDate = rs.getTimestamp(9);

					User user = new User(userCode, password, name, bestScore, status, closeDate, managerCode, regDate,
							modDate);

					list.add(user);
				}

			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				DBManager.close(conn, pstmt, rs);
			}
		}
		return list;
	}

	public User findUserByCode(String userCode) {
		User user = null;

		conn = DBManager.getConnection();

		String sql = "SELECT * FROM users WHERE code=?";

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userCode);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				String password = rs.getString(2);
				String name = rs.getString(3);
				int bestScore = rs.getInt(4);
				int status = rs.getInt(5);
				Timestamp closeDate = rs.getTimestamp(6);
				String managerCode = rs.getString(7);
				Timestamp regDate = rs.getTimestamp(8);
				Timestamp modDate = rs.getTimestamp(9);

				user = new User(userCode, password, name, bestScore, status, closeDate, managerCode, regDate, modDate);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt, rs);
		}
		return user;
	}

	public User findUserByName(String name) {
		User user = null;

		conn = DBManager.getConnection();

		String sql = "SELECT * FROM users WHERE name=?";

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, name);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				String userCode = rs.getString(1);
				String password = rs.getString(2);
				int bestScore = rs.getInt(4);
				int status = rs.getInt(5);
				Timestamp closeDate = rs.getTimestamp(6);
				String managerCode = rs.getString(7);
				Timestamp regDate = rs.getTimestamp(8);
				Timestamp modDate = rs.getTimestamp(9);

				user = new User(userCode, password, name, bestScore, status, closeDate, managerCode, regDate, modDate);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt, rs);
		}
		return user;
	}

	public User updateUser(UserRequestDto userDto) {
		conn = DBManager.getConnection();

		if (conn != null) {
			String sql = "UPDATE users SET name=?";
			if (userDto.getPassword() != null)
				sql += ", password=?";
			sql += " WHERE code=?";

			try {
				int cnt = 1;

				pstmt = conn.prepareStatement(sql);

				pstmt.setString(1, userDto.getName());

				if (userDto.getPassword() != null) {
					String hashedPassword = BCrypt.hashpw(userDto.getPassword(), BCrypt.gensalt());
					pstmt.setString(++cnt, hashedPassword);
				}

				pstmt.setString(++cnt, userDto.getUserCode());
				pstmt.execute();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				DBManager.close(conn, pstmt);
			}
		}

		return findUserByCode(userDto.getUserCode());
	}

	public void deleteUserByCode(String userCode) {
		conn = DBManager.getConnection();

		if (conn != null) {
			String sql = "DELETE FROM users WHERE code=?";

			try {
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, userCode);

				pstmt.execute();

			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				DBManager.close(conn, pstmt);
			}
		}
	}

	public double calculateAverageScore(String userCode) {
		double averageScore = 0;
		conn = DBManager.getConnection();

		String sql = "SELECT AVG(score) AS average_score FROM solve WHERE user_code=?";

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userCode);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				averageScore = rs.getDouble("average_score");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt, rs);
		}
		return averageScore;
	}

	public int calculateGameCount(String userCode) {
		int gameCount = 0;
		conn = DBManager.getConnection();

		String sql = "SELECT COUNT(*) AS game_count FROM solve WHERE user_code=?";

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userCode);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				gameCount = rs.getInt("game_count");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt, rs);
		}
		return gameCount;
	}
	
	public void updateUserStatus(String userCode, int status) {
	    conn = DBManager.getConnection();

	    String sql = "UPDATE users SET status=? WHERE code=?";

	    try {
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setInt(1, status);
	        pstmt.setString(2, userCode);

	        pstmt.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        DBManager.close(conn, pstmt);
	    }
	}

}