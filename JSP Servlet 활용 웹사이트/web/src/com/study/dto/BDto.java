package com.study.dto;

import java.sql.Timestamp;

public class BDto {
	int bId;
	String id;
	String bName;
	String bTitle;
	String bContent;
	Timestamp bDate;
	int bHit;
	String bFile;
	String bRealFile;
	int bGroup;
	int bStep;
	int bIndent;
	String like_id;
	int like_c;
	String profile;
	
	public BDto() {
		
	}

	public BDto(int bId, String id, String bName, String bTitle, String bContent, Timestamp bDate, int bHit, int bGroup,
			int bStep, int bIndent) {
		this.bId = bId;
		this.id = id;
		this.bName = bName;
		this.bTitle = bTitle;
		this.bContent = bContent;
		this.bDate = bDate;
		this.bHit = bHit;
		this.bGroup = bGroup;
		this.bStep = bStep;
		this.bIndent = bIndent;
	}

	public BDto(int bId, String id, String bName, String bTitle, String bContent, Timestamp bDate, int bHit,
			String bFile, String bRealFile, int bGroup, int bStep, int bIndent) {
		super();
		this.bId = bId;
		this.id = id;
		this.bName = bName;
		this.bTitle = bTitle;
		this.bContent = bContent;
		this.bDate = bDate;
		this.bHit = bHit;
		this.bFile = bFile;
		this.bRealFile = bRealFile;
		this.bGroup = bGroup;
		this.bStep = bStep;
		this.bIndent = bIndent;
	}
	
	


	public BDto(int bId, String id, String bName, String bTitle, String bContent, Timestamp bDate, int bHit,
			String bFile, String bRealFile, int bGroup, int bStep, int bIndent, String like_id, int like_c) {
		super();
		this.bId = bId;
		this.id = id;
		this.bName = bName;
		this.bTitle = bTitle;
		this.bContent = bContent;
		this.bDate = bDate;
		this.bHit = bHit;
		this.bFile = bFile;
		this.bRealFile = bRealFile;
		this.bGroup = bGroup;
		this.bStep = bStep;
		this.bIndent = bIndent;
		this.like_id = like_id;
		this.like_c = like_c;
	}

	
	
	
	
	
	public String getProfile() {
		return profile;
	}

	public void setProfile(String profile) {
		this.profile = profile;
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

	public String getbFile() {
		return bFile;
	}

	public void setbFile(String bFile) {
		this.bFile = bFile;
	}

	public String getbRealFile() {
		return bRealFile;
	}

	public void setbRealFile(String bRealFile) {
		this.bRealFile = bRealFile;
	}

	public int getbId() {
		return bId;
	}

	public void setbId(int bId) {
		this.bId = bId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String getbName() {
		return bName;
	}

	public void setbName(String bName) {
		this.bName = bName;
	}

	public String getbTitle() {
		return bTitle;
	}

	public void setbTitle(String bTitle) {
		this.bTitle = bTitle;
	}

	public String getbContent() {
		return bContent;
	}

	public void setbContent(String bContent) {
		this.bContent = bContent;
	}

	public Timestamp getbDate() {
		return bDate;
	}

	public void setbDate(Timestamp bDate) {
		this.bDate = bDate;
	}

	public int getbHit() {
		return bHit;
	}

	public void setbHit(int bHit) {
		this.bHit = bHit;
	}

	public int getbGroup() {
		return bGroup;
	}

	public void setbGroup(int bGroup) {
		this.bGroup = bGroup;
	}

	public int getbStep() {
		return bStep;
	}

	public void setbStep(int bStep) {
		this.bStep = bStep;
	}

	public int getbIndent() {
		return bIndent;
	}

	public void setbIndent(int bIndent) {
		this.bIndent = bIndent;
	}
	
}
