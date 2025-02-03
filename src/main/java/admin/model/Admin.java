package admin.model;

import org.mindrot.jbcrypt.BCrypt;

public class Admin {
	private String code;
	private String password;
	
	public Admin(String code, String password) {
		this.code = code;
		this.password = password;
	}

	public String getCode() {
		return code;
	}
	
	public boolean checkPassword(String password) {
		boolean isChecked = false;
		try {
			isChecked = BCrypt.checkpw(password, this.password);
		} catch (Exception e) {
			System.err.println("암호화되지 않은 값이 저장되어 있습니다.");
		}
		return isChecked;
	}
	
	public String toString() {
		return String.format("%s", code);
	}
}
