package quiz.model;

public class QuizResponseDto {
	private int code;
	private int type;
	private int contentId;
	private int peopleId;
	private String contentLink;
	private String peopleLink;
	private int count;
	private double averageScore;
	private double averageTimer;
	
	public QuizResponseDto() {
	}

	public QuizResponseDto(int code, int type, int contentId, int peopleId) {
		this.code = code;
		this.type = type;
		this.contentId = contentId;
		this.peopleId = peopleId;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getContentId() {
		return contentId;
	}

	public void setContentId(int contentId) {
		this.contentId = contentId;
	}

	public int getPeopleId() {
		return peopleId;
	}

	public void setPeopleId(int peopleId) {
		this.peopleId = peopleId;
	}

	public String getContentLink() {
		return contentLink;
	}

	public void setContentLink(String contentLink) {
		this.contentLink = contentLink;
	}

	public String getPeopleLink() {
		return peopleLink;
	}

	public void setPeopleLink(String peopleLink) {
		this.peopleLink = peopleLink;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public double getAverageScore() {
		return averageScore;
	}

	public void setAverageScore(double averageScore) {
		this.averageScore = averageScore;
	}

	public double getAverageTimer() {
		return averageTimer;
	}

	public void setAverageTimer(double correctRate) {
		this.averageTimer = correctRate;
	}		
}
