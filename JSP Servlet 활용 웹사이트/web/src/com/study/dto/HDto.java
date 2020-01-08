package com.study.dto;

import java.sql.Timestamp;

public class HDto {
	int pId;
	String id;
	String pName;
	String pTitle;
	String pContent;
	Timestamp pDate;
	int pHit;
	String pFile;
	String pRealFile;
	String like_id;
	int like_c;
	String path;
	
	public HDto() {
		
	}

	

	public HDto(int pId, String id, String pName, String pTitle, String pContent, Timestamp pDate, int pHit) {
		super();
		this.pId = pId;
		this.id = id;
		this.pName = pName;
		this.pTitle = pTitle;
		this.pContent = pContent;
		this.pDate = pDate;
		this.pHit = pHit;
	}



	public HDto(int pId, String id, String pName, String pTitle, String pContent, Timestamp pDate, int pHit,
			String pFile, String pRealFile) {
		super();
		this.pId = pId;
		this.id = id;
		this.pName = pName;
		this.pTitle = pTitle;
		this.pContent = pContent;
		this.pDate = pDate;
		this.pHit = pHit;
		this.pFile = pFile;
		this.pRealFile = pRealFile;
	}



	public int getpId() {
		return pId;
	}

	public void setpId(int pId) {
		this.pId = pId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getpName() {
		return pName;
	}

	public void setpName(String pName) {
		this.pName = pName;
	}

	public String getpTitle() {
		return pTitle;
	}

	public void setpTitle(String pTitle) {
		this.pTitle = pTitle;
	}

	public String getpContent() {
		return pContent;
	}

	public void setpContent(String pContent) {
		this.pContent = pContent;
	}

	public Timestamp getpDate() {
		return pDate;
	}

	public void setpDate(Timestamp pDate) {
		this.pDate = pDate;
	}

	public int getpHit() {
		return pHit;
	}

	public void setpHit(int pHit) {
		this.pHit = pHit;
	}

	public String getpFile() {
		return pFile;
	}

	public void setpFile(String pFile) {
		this.pFile = pFile;
	}

	public String getpRealFile() {
		return pRealFile;
	}

	public void setpRealFile(String pRealFile) {
		this.pRealFile = pRealFile;
	}

	public String getLike_id() {
		return like_id;
	}

	public void setLike_id(String like_id) {
		this.like_id = like_id;
	}

	public int getLike_c() {
		return like_c;
	}

	public void setLike_c(int like_c) {
		this.like_c = like_c;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	
	
}
