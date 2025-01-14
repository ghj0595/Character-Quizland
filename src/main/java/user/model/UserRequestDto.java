package user.model;

import java.sql.Timestamp;

public class UserRequestDto {

	private String userCode;
	private String password;
	private String name;
	private int bestScore;
	private int status;
	private Timestamp closeDate;
	private String managerCode;
	private Timestamp regDate;
	private Timestamp modDate;

	public UserRequestDto() {

	}

	public UserRequestDto(String userCode, String password, String name, int bestScore, int status, Timestamp closeDate,
			String managerCode, Timestamp regDate, Timestamp modDate) {
		this.userCode = userCode;
		this.password = password;
		this.name = name;
		this.bestScore = bestScore;
		this.status = status;
		this.closeDate = closeDate;
		this.managerCode = managerCode;
		this.regDate = regDate;
		this.modDate = modDate;
	}

	public UserRequestDto(String userCode, String password, String name) {
		this.userCode = userCode;
		this.password = password;
		this.name = name;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getBestScore() {
		return bestScore;
	}

	public void setBestScore(int bestScore) {
		this.bestScore = bestScore;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Timestamp getCloseDate() {
		return closeDate;
	}

	public void setCloseDate(Timestamp closeDate) {
		this.closeDate = closeDate;
	}

	public String getManagerCode() {
		return managerCode;
	}

	public void setManagerCode(String managerCode) {
		this.managerCode = managerCode;
	}

	public Timestamp getRegDate() {
		return regDate;
	}

	public void setRegDate(Timestamp regDate) {
		this.regDate = regDate;
	}

	public Timestamp getModDate() {
		return modDate;
	}

	public void setModDate(Timestamp modDate) {
		this.modDate = modDate;
	}
	
}