package quiz.model;

public class QuizRequestDto {
	private int type;
	private int contentId;
	private int peopleId;
	
	public QuizRequestDto() {
	}
	
	public QuizRequestDto(int type, int contentId, int peopleId) {
		this.type = type;
		this.contentId = contentId;
		this.peopleId = peopleId;
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
