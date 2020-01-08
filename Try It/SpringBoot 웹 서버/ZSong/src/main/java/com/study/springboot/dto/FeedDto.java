package com.study.springboot.dto;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class FeedDto {
   private int feed_num;
   private String member_id;
   private int challenge_num;
   private String challenge_title;
   private Timestamp feed_update_time;
   private String feed_info;
   private String feed_comment;
   
   private int count;
   private int exit;
   
   private int comment_count;
   private String comment_userid;
   private String comment_content;
}