package com.study.springboot.dto;


import java.sql.Timestamp;

import lombok.Data;

@Data
public class RecordAllDto {
	   private int challenge_num;
	   private int member_num;
	   private int certificate_count;
	   private int all_count;
	   private int challenge_fee;
	   private int reward;
	   private Timestamp receipt;   
}