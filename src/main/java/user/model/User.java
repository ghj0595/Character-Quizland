package user.model;

import java.sql.Timestamp;

import org.mindrot.jbcrypt.BCrypt;

public class User {

	private String userCode;
	private String password;
	private String name;
	private int bestScore;
	private int status;
	private String managerCode;
	private Timestamp regDate;
	private Timestamp modDate;
	private int rank;

	public User(String userCode, String password, String name, int bestScore, int status,
			String managerCode, Timestamp regDate, Timestamp modDate) {
		this.userCode = userCode;
		this.password = password;
		this.name = name;
		this.bestScore = bestScore;
		this.status = status;
		this.managerCode = managerCode;
		this.regDate = regDate;
		this.modDate = modDate;
	}

	public User(String userCode, String password, String name) {
		this.userCode = userCode;
		this.password = password;
		this.name = name;
	}

	public User(String name, int bestScore, int rank) {
		this.name = name;
		this.bestScore = bestScore;
		this.rank = rank;
	}

	public int getRank() {
		return rank;
	}

	public String getUserCode() {
		return userCode;
	}

	public boolean checkPassword(String password) {
		boolean isChecked = false;
		try {
			isChecked = BCrypt.checkpw(password, this.password);
		} catch (Exception e) {
			System.err.println("암호화되지 않은 값이 저장되어 있습니다.");
		}
		return isChecked;
	}

	public String getName() {
		return name;
	}

	public int getBestScore() {
		return bestScore;
	}

	public int getStatus() {
		return status;
	}

	public String getManagerCode() {
		return managerCode;
	}

	public Timestamp getRegDate() {
		return regDate;
	}

	public Timestamp getModDate() {
		return modDate;
	}

	public String toString() {
		return String.format("%s \n%s \n%d \n%d \n%s \n%s \n%s \n%s", userCode, name, bestScore, status, managerCode,
				regDate, modDate);
	}
}