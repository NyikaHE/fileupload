package com.huat.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBserver {
	private static final String driver="com.mysql.jdbc.Driver";
	private static final String url="jdbc:mysql://localhost:3306/fileupload?useUnicode=true&charcterEncoding=UTF-8";
	private static final String username="root";
	private static final String password="123456";
	private static Connection con=null;
	static{
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static Connection getconnection(){
		if(con==null){
			try {
				con=DriverManager.getConnection(url,username,password);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return con;
	}

}
