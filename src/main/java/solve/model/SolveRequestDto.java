package solve.model;

public class SolveRequestDto {
	private String userCode;
	private int quizCode;
	private int score;
	private int timer;
	
	public SolveRequestDto() {
	}

	public SolveRequestDto(String userCode, int quizCode, int score, int timer) {
		this.userCode = userCode;
		this.quizCode = quizCode;
		this.score = score;
		this.timer = timer;
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
}
