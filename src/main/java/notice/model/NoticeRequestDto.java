package notice.model;

import java.sql.Timestamp;

public class NoticeRequestDto {
    private String adminCode;
    private String title;
    private String content;
    private int status;
    private Timestamp resDate;
    private Timestamp closeDate;
    
    public NoticeRequestDto() {
    	
    }

    public NoticeRequestDto(String adminCode, String title, String content, int status, Timestamp resDate, Timestamp closeDate) {
        this.adminCode = adminCode;
        this.title = title;
        this.content = content;
        this.status = status;
        this.resDate = resDate;
        this.closeDate = closeDate;
    }        

    public NoticeRequestDto(String adminCode, String title, String content, int status) {
		this.adminCode = adminCode;
		this.title = title;
		this.content = content;
		this.status = status;
	}

	public String getAdminCode() {
        return adminCode;
    }

    public void setAdminCode(String adminCode) {
        this.adminCode = adminCode;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Timestamp getResDate() {
        return resDate;
    }

    public void setResDate(Timestamp resDate) {
        this.resDate = resDate;
    }

    public Timestamp getCloseDate() {
        return closeDate;
    }

    public void setCloseDate(Timestamp closeDate) {
        this.closeDate = closeDate;
    }
    
    public void setStartDate(String startDate) { 
    	this.resDate = (startDate != null && !startDate.isEmpty()) ? Timestamp.valueOf(startDate + " 00:00:00") : null; 
    } 
    
    public void setEndDate(String endDate) { 
    	this.closeDate = (endDate != null && !endDate.isEmpty()) ? Timestamp.valueOf(endDate + " 00:00:00") : null; 
    }
}
