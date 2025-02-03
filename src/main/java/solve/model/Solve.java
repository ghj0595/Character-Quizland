package solve.model;

import java.sql.Timestamp;

public class Solve {
	private int code;
	private String userCode;
	private int quizCode;
	private int score;
	private int timer;
	private Timestamp regDate;
	private Timestamp modDate;
	
	public Solve(int code, String userCode, int quizCode, int score, int timer, Timestamp regDate, Timestamp modDate) {
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

	public String getUserCode() {
		return userCode;
	}

	public int getQuizCode() {
		return quizCode;
	}

	public int getScore() {
		return score;
	}

	public int getTimer() {
		return timer;
	}

	public Timestamp getRegDate() {
		return regDate;
	}

	public Timestamp getModDate() {
		return modDate;
	}
}
