package quiz.model;

public class QuizResponseDto {
	private int code;
	private int type;
	private int contentId;
	private int peopleId;
	
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
}
