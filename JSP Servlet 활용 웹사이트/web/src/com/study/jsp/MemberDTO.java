package com.study.jsp;

import java.sql.Timestamp;

public class MemberDTO {
	private String id;
	private String pw;
	private String name;
	private String eMail;
	private Timestamp rDate;
	private String address;
	private String ban;
	private String state;
	private String profile;
	
	public MemberDTO() {
		
	}

	public MemberDTO(String id, String pw, String name, String eMail, Timestamp rDate, String address, String ban,
			String state) {
		super();
		this.id = id;
		this.pw = pw;
		this.name = name;
		this.eMail = eMail;
		this.rDate = rDate;
		this.address = address;
		this.ban = ban;
		this.state = state;
	}

	
	
	public String getProfile() {
		return profile;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}

	public String getBan() {
		return ban;
	}
	
	public void setBan(String ban) {
		this.ban = ban;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPw() {
		return pw;
	}
	public void setPw(String pw) {
		this.pw = pw;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String geteMail() {
		return eMail;
	}
	public void seteMail(String eMail) {
		this.eMail = eMail;
	}
	public Timestamp getrDate() {
		return rDate;
	}
	public void setrDate(Timestamp rDate) {
		this.rDate = rDate;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}

		
}
