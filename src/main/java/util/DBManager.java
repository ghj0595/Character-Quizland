package util;

import java.sql.Connection;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class DBManager {
		public static Connection getConnection() {
			Connection conn = null;
			
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				
				Context init = new InitialContext();
				Context ctx = (Context) init.lookup("java:comp/env");
				
				DataSource dataSource = (DataSource) ctx.lookup("jdbc/QuizlandDB");
				conn = dataSource.getConnection();
				
				System.out.println("Database 연동 성공!");
			} catch (Exception e) {
				e.printStackTrace();
				System.err.println("Database 연동 실패...");
			}
			return conn;
		}		

}
