package com.huat.server;

import java.sql.*;

import com.huat.entity.File;
import com.huat.util.DBserver;
import com.mysql.jdbc.PreparedStatement;

public class Fileserver {
	public boolean upload(File file){
		Connection conn=DBserver.getconnection();
		String sql="insert into file (username,filename,filecontent)values(?,?,?)";
		try {
			PreparedStatement ps=(PreparedStatement)conn.prepareStatement(sql);
			ps.setString(1, file.getFilename());
			ps.setString(2, file.getFilename());
			ps.setBytes(3, file.getFstate());
			int n=ps.executeUpdate();//使用INSERT……等对数据库进行增删改操作
			if(n>0)	
				return true;
			ps.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

}
