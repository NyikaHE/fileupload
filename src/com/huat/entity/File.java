package com.huat.entity;

import java.io.Serializable;

public class File implements Serializable{
	private static final long serialVersionUID = 1L;
	private String filename;
	private String username;
	private byte[] fstate;
	
	public File(String name,String user,byte[] ss){
		super();
		filename=name;
		username=user;
		fstate=ss;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public byte[] getFstate() {
		return fstate;
	}
	public void setFstate(byte[] fstate) {
		this.fstate = fstate;
	}
}
