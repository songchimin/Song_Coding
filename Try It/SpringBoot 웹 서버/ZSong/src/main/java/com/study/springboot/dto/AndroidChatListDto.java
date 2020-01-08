package com.study.springboot.dto;

import lombok.Data;

@Data
public class AndroidChatListDto {
	private int challenge_num;
	private String challenge_title;
	private String challenge_first_image;
	
	private int chat_room_participant_count;
}
