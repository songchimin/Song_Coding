package com.study.dto;

import java.sql.Date;

public class RDto {
	String bid;
	String name;
	int bcount;
	String start;
	String end;
	
	public RDto() {
		
	}

	public RDto(String bid, String name, int bcount) {
		super();
		this.bid = bid;
		this.name = name;
		this.bcount = bcount;
	}

	
	


	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}

	public String getBid() {
		return bid;
	}

	public void setBid(String bid) {
		this.bid = bid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getBcount() {
		return bcount;
	}

	public void setBcount(int bcount) {
		this.bcount = bcount;
	}

	
	
}
