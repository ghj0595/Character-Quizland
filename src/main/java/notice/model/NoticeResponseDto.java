package notice.model;

import java.sql.Timestamp;

public class NoticeResponseDto {
    private int code;
    private String adminCode;
    private String title;
    private String content;
    private int status;
    private Timestamp resDate;
    private Timestamp closeDate;
    private Timestamp regDate;
    private Timestamp modDate;

    public NoticeResponseDto(int code, String adminCode, String title, String content, int status, Timestamp resDate, Timestamp closeDate, Timestamp regDate, Timestamp modDate) {
        this.code = code;
        this.adminCode = adminCode;
        this.title = title;
        this.content = content;
        this.status = status;
        this.resDate = resDate;
        this.closeDate = closeDate;
        this.regDate = regDate;
        this.modDate = modDate;
    }    

    public NoticeResponseDto(int code, String adminCode, String title, String content, Timestamp resDate,
			Timestamp closeDate, Timestamp regDate, Timestamp modDate) {
		this.code = code;
		this.adminCode = adminCode;
		this.title = title;
		this.content = content;
		this.resDate = resDate;
		this.closeDate = closeDate;
		this.regDate = regDate;
		this.modDate = modDate;
	}

	public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
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
    
    public String getFormattedResDate() { 
    	return (resDate != null) ? new java.text.SimpleDateFormat("yyyy-MM-dd").format(resDate) : ""; 
    } 
    
    public String getFormattedCloseDate() { 
    	return (closeDate != null) ? new java.text.SimpleDateFormat("yyyy-MM-dd").format(closeDate) : ""; 
    }
}
