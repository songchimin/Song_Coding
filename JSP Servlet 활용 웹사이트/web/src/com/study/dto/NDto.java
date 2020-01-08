package com.study.dto;

import java.sql.Timestamp;

public class NDto {
	int nId;
	String id;
	String nName;
	String nTitle;
	String nContent;
	Timestamp nDate;
	int nHit;
	int nGroup;
	int nStep;
	int nIndent;
	
	public NDto() {
		
	}
	
	public NDto(int nId, String id, String nName, String nTitle, String nContent, Timestamp nDate, int nHit, int nGroup,
			int nStep, int nIndent) {
		super();
		this.nId = nId;
		this.id = id;
		this.nName = nName;
		this.nTitle = nTitle;
		this.nContent = nContent;
		this.nDate = nDate;
		this.nHit = nHit;
		this.nGroup = nGroup;
		this.nStep = nStep;
		this.nIndent = nIndent;
	}



	public int getnId() {
		return nId;
	}

	public void setnId(int nId) {
		this.nId = nId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getnName() {
		return nName;
	}

	public void setnName(String nName) {
		this.nName = nName;
	}

	public String getnTitle() {
		return nTitle;
	}

	public void setnTitle(String nTitle) {
		this.nTitle = nTitle;
	}

	public String getnContent() {
		return nContent;
	}

	public void setnContent(String nContent) {
		this.nContent = nContent;
	}

	public Timestamp getnDate() {
		return nDate;
	}

	public void setnDate(Timestamp nDate) {
		this.nDate = nDate;
	}

	public int getnHit() {
		return nHit;
	}

	public void setnHit(int nHit) {
		this.nHit = nHit;
	}

	public int getnGroup() {
		return nGroup;
	}

	public void setnGroup(int nGroup) {
		this.nGroup = nGroup;
	}

	public int getnStep() {
		return nStep;
	}

	public void setnStep(int nStep) {
		this.nStep = nStep;
	}

	public int getnIndent() {
		return nIndent;
	}

	public void setnIndent(int nIndent) {
		this.nIndent = nIndent;
	}
	
	
}
