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

		String sql = "INSERT INTO users(id, `password`, `name`, contact) VALUES(?, ?, ?, ?)";

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userDto.getId());

			String rawPassword = userDto.getPassword();
			String hashedPassword = BCrypt.hashpw(rawPassword, BCrypt.gensalt());

			pstmt.setString(2, hashedPassword);
			pstmt.setString(3, userDto.getName());
			pstmt.setString(4, userDto.getContact());

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
					int userCode = rs.getInt(1);
					String id = rs.getString(2);
					String password = rs.getString(3);
					String name = rs.getString(4);
					String contact = rs.getString(5);
					Timestamp regDate = rs.getTimestamp(6);
					Timestamp modDate = rs.getTimestamp(7);

					User user = new User(userCode, id, password, name, contact, regDate, modDate);

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

	public User findUserById(String id) {
		User user = null;

		conn = DBManager.getConnection();

		String sql = "SELECT * FROM users WHERE id=?";

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				int userCode = rs.getInt(1);
				String password = rs.getString(3);
				String name = rs.getString(4);
				String contact = rs.getString(5);
				Timestamp regDate = rs.getTimestamp(6);
				Timestamp modDate = rs.getTimestamp(7);

				user = new User(userCode, id, password, name, contact, regDate, modDate);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt, rs);
		}
		return user;
	}

	public User findUserByContact(String contact) {
		User user = null;

		conn = DBManager.getConnection();

		String sql = "SELECT * FROM users WHERE contact=?";

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, contact);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				int userCode = rs.getInt(1);
				String id = rs.getString(2);
				String password = rs.getString(3);
				String name = rs.getString(4);
				Timestamp regDate = rs.getTimestamp(6);
				Timestamp modDate = rs.getTimestamp(7);

				user = new User(userCode, id, password, name, contact, regDate, modDate);
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
			String sql = "UPDATE users SET contact=?";
			if (userDto.getPassword() != null)
				sql += ", password=?";
			sql += " WHERE id=?";

			try {
				int cnt = 1;

				pstmt = conn.prepareStatement(sql);

				pstmt.setString(1, userDto.getContact());

				if (userDto.getPassword() != null) {
					String hashedPassword = BCrypt.hashpw(userDto.getPassword(), BCrypt.gensalt());
					pstmt.setString(++cnt, hashedPassword);
				}

				pstmt.setString(++cnt, userDto.getId());
				pstmt.execute();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				DBManager.close(conn, pstmt);
			}
		}

		return findUserById(userDto.getId());
	}

	public void deleteUserById(String id) {
		conn = DBManager.getConnection();

		if (conn != null) {
			String sql = "DELETE FROM users WHERE id=?";

			try {
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, id);

				pstmt.execute();

			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				DBManager.close(conn, pstmt);
			}
		}
	}
}