package com.huat.server;

import java.sql.*;

import com.huat.entity.User;
import com.huat.util.DBserver;

public class Userserver {
	Connection conn=null;
	PreparedStatement ps=null;
	public boolean checklogin(User user){
		String sql="select * from user where username=? and password=?";
		conn=DBserver.getconnection();
		try {
			ps=conn.prepareStatement(sql);
			ps.setString(1, user.getuname());
			ps.setString(2, user.getpsw());
			ResultSet rs=ps.executeQuery();
			rs.last();
			int n=rs.getRow();
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
	public boolean register(User user){
		String sql="insert into user (username,password)values(?,?)";
		conn=DBserver.getconnection();
		try {
			ps=conn.prepareStatement(sql);
			ps.setString(1, user.getuname());
			ps.setString(2, user.getpsw());
			int n=ps.executeUpdate();
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
