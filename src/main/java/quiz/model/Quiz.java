package quiz.model;

public class Quiz {
	private int code;
	private int type;
	private int contentId;
	private int peopleId;
	
	public Quiz(int code, int type, int contentId, int peopleId) {
		this.code = code;
		this.type = type;
		this.contentId = contentId;
		this.peopleId = peopleId;
	}

	public int getCode() {
		return code;
	}

	public int getType() {
		return type;
	}

	public int getContentId() {
		return contentId;
	}

	public int getPeopleId() {
		return peopleId;
	}
	
	
}
