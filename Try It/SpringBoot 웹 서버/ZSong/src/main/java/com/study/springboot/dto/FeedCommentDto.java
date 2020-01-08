package com.study.springboot.dto;

import java.sql.Timestamp;
import java.util.Date;

import lombok.Data;

@Data
public class FeedCommentDto {
	private int comment_num;
	private int feed_num;
	private String member_id;
	private String comment_content;
	private Timestamp commment_date;	
}
