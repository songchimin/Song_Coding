package com.study.springboot;


import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.tomcat.jni.Mmap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.study.springboot.dao.AndroidChatDao;
import com.study.springboot.dto.AndroidChatListDto;
import com.study.springboot.dto.ChallengeDto;
import com.study.springboot.dto.MemberDto;
import com.study.springboot.dto.SimpleBbsDto;


@Controller
public class AndroidController {

	@Autowired
	AndroidChatDao dao;
	
	List<SimpleBbsDto> list;
	List<MemberDto> member;
	List<AndroidChatListDto> AndroidChatList;

	
	@RequestMapping("/chat_room_list")
	public String test(Model model, HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException
	{
		String id = request.getParameter("id");
		
		AndroidChatList = dao.chat_room_list(id);
		
		System.out.println("ssss");
		
		for(int i = 0 ; i < AndroidChatList.size() ; i++) {
			AndroidChatList.get(i).setChat_room_participant_count(dao.participant_count(AndroidChatList.get(i).getChallenge_num()));
		}
		
		model.addAttribute("AndroidChatList", AndroidChatList);
		return "/Android_Chat/chat_list";
	}
	
	
	@RequestMapping("/member_nickname")
	public String member_nickname(Model model, HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException
	{
		String id = request.getParameter("id");
		
		String nickname = dao.member_nickname(id);
		
		System.out.println(nickname + "출력");
		model.addAttribute("nickname", nickname);
		
		return "/Android_Chat/chat_member_nickname";
	}
	
}
