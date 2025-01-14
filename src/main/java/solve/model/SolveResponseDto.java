package solve.model;

import java.sql.Timestamp;

public class SolveResponseDto {
	private int code;
	private String userCode;
	private int quizCode;
	private int score;
	private int timer;
	private Timestamp regDate;
	private Timestamp modDate;
	
	public SolveResponseDto() {
	}

	public SolveResponseDto(int code, String userCode, int quizCode, int score, int timer, Timestamp regDate,
			Timestamp modDate) {
		this.code = code;
		this.userCode = userCode;
		this.quizCode = quizCode;
		this.score = score;
		this.timer = timer;
		this.regDate = regDate;
		this.modDate = modDate;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public int getQuizCode() {
		return quizCode;
	}

	public void setQuizCode(int quizCode) {
		this.quizCode = quizCode;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getTimer() {
		return timer;
	}

	public void setTimer(int timer) {
		this.timer = timer;
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
