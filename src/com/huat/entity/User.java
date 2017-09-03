package com.huat.entity;

import java.io.Serializable;

public class User implements Serializable{
	private static final long serialVersionUID = 4015036439904829095L;
	private String username;//ÓÃ»§Ãû
	private String password;//ÃÜÂë
	public User(){
		super();			//?
	}
	public User(String name,String pass){
		super();
		username=name;
		password=pass;
	}
	public void setuname(String name){
		username=name;
	}
	public void setpsw(String pass){
		password=pass;
	}
	public String getuname(){
		return username;
	}
	public String getpsw(){
		return password;
	}

}
