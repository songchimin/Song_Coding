package com.study.dto;

import java.sql.Timestamp;

public class CDto {
	int bId;
	int cNo;
	String cId;
	String cName;
	String content;
	Timestamp cDate;
	int ss;
	
	public CDto() {
		
	}
	
	public CDto(int bId, int cNo, String cId, String cName, String content, Timestamp cDate, int ss) {
		super();
		this.bId = bId;
		this.cNo = cNo;
		this.cId = cId;
		this.cName = cName;
		this.content = content;
		this.cDate = cDate;
		this.ss = ss;
	}
	
	public int getbId() {
		return bId;
	}
	public void setbId(int bId) {
		this.bId = bId;
	}
	public int getcNo() {
		return cNo;
	}
	public void setcNo(int cNo) {
		this.cNo = cNo;
	}
	public String getcId() {
		return cId;
	}
	public void setcId(String cId) {
		this.cId = cId;
	}
	public String getcName() {
		return cName;
	}
	public void setcName(String cName) {
		this.cName = cName;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Timestamp getcDate() {
		return cDate;
	}
	public void setcDate(Timestamp cDate) {
		this.cDate = cDate;
	}
	public int getSs() {
		return ss;
	}
	public void setSs(int ss) {
		this.ss = ss;
	}
	
	
}
