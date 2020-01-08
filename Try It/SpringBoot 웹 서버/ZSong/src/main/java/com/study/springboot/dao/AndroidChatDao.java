package com.study.springboot.dao;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

import com.study.springboot.dto.AndroidChatListDto;


@Mapper	
public interface AndroidChatDao {
	
	public ArrayList<AndroidChatListDto> chat_room_list(String id);
	public int participant_count(int num);
	
	public String member_nickname(String id);
	
}
