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
					String managerCode = rs.getString(6);
					Timestamp regDate = rs.getTimestamp(7);
					Timestamp modDate = rs.getTimestamp(8);

					User user = new User(userCode, password, name, bestScore, status, managerCode, regDate,
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
	
	public ArrayList<User> findUserRank() {
	    ArrayList<User> list = new ArrayList<>();

	    conn = DBManager.getConnection();

	    if (conn != null) {
	    	String sql = "SELECT name, best_score, DENSE_RANK() OVER (ORDER BY best_score DESC, mod_date DESC) AS `rank` FROM users WHERE best_score > 0 AND status = 0 ORDER BY `rank` ASC LIMIT 10;";
	        
	        try {
	            pstmt = conn.prepareStatement(sql);
	            rs = pstmt.executeQuery();

	            while (rs.next()) {
	                String name = rs.getString("name");
	                int bestScore = rs.getInt("best_score");
	                int rank = rs.getInt("rank");

	                User user = new User(name, bestScore, rank);
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
				String managerCode = rs.getString(6);
				Timestamp regDate = rs.getTimestamp(7);
				Timestamp modDate = rs.getTimestamp(8);

				user = new User(userCode, password, name, bestScore, status, managerCode, regDate, modDate);
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
				String managerCode = rs.getString(6);
				Timestamp regDate = rs.getTimestamp(7);
				Timestamp modDate = rs.getTimestamp(8);

				user = new User(userCode, password, name, bestScore, status, managerCode, regDate, modDate);
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

	public void updateUserStatus(String userCode, int status, String adminCode) {
		conn = DBManager.getConnection();

		String sql = "UPDATE users SET status=?, manager_code=? WHERE code=?";

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, status);
			pstmt.setString(2, adminCode);
			pstmt.setString(3, userCode);

			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt);
		}
	}
	
	public int getUserBestScore(String userCode) {
		int bestScore = 0;

		conn = DBManager.getConnection();

		String sql = "SELECT best_score FROM users WHERE code=?";

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userCode);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				bestScore = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt, rs);
		}
		return bestScore;
	}
	
	public void updateUserBestScore(String userCode, int bestScore) {
		conn = DBManager.getConnection();

		String sql = "UPDATE users SET best_score=? WHERE code=?";

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, bestScore); 
			pstmt.setString(2, userCode);

			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt);
		}
	}

	public int getRankByScore(int Score) {
		int rank = 9999;
		conn = DBManager.getConnection();

		String sql = "SELECT COUNT(best_score) + 1 FROM users WHERE best_score > ?";

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, Score); 

			rs = pstmt.executeQuery();

			if (rs.next()) {
				rank = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt);
		}
		
		return rank;
	}
	
	public double getPerByScore(int Score) {
		double per = 100;
		conn = DBManager.getConnection();

		String sql = "SELECT (COUNT(CASE WHEN best_score > ? THEN 0 END) + 1) * 100.0 / COUNT(*) AS per FROM users";

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, Score); 

			rs = pstmt.executeQuery();

			if (rs.next()) {
				per = rs.getDouble(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt);
		}
		
		return per;
	}
}