package com.study.springboot.dto;

import lombok.Data;

@Data
public class FollowDto {
	
	private String folloer_id;
	private String following_id;
	
	private int followExist;	
	
}
