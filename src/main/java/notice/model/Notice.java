package notice.model;

import java.sql.Timestamp;

public class Notice {
		private int code;
		private String adminCode;
		private String title;
		private String content;
		private int status;
		private Timestamp resDate;
		private Timestamp closeDate;
		private Timestamp regDate;
		private Timestamp modDate;
		
		public Notice(int code, String adminCode, String title, String content, int status, Timestamp resDate,
				Timestamp closeDate, Timestamp regDate, Timestamp modDate) {
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

		public Notice(int code, String adminCode, String title, String content, int status, Timestamp regDate,
				Timestamp modDate) {
			this.code = code;
			this.adminCode = adminCode;
			this.title = title;
			this.content = content;
			this.status = status;
			this.regDate = regDate;
			this.modDate = modDate;
		}

		public int getCode() {
			return code;
		}

		public String getAdminCode() {
			return adminCode;
		}

		public String getTitle() {
			return title;
		}

		public String getContent() {
			return content;
		}

		public int getStatus() {
			return status;
		}

		public Timestamp getResDate() {
			return resDate;
		}

		public Timestamp getCloseDate() {
			return closeDate;
		}

		public Timestamp getRegDate() {
			return regDate;
		}

		public Timestamp getModDate() {
			return modDate;
		}		
}
