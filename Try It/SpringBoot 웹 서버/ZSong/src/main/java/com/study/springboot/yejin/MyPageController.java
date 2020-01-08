package com.study.springboot.yejin;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.study.springboot.dao.MyPageDao;
import com.study.springboot.dto.ChallengeDto;
import com.study.springboot.dto.CommentDto;
import com.study.springboot.dto.ComplaintDto;
import com.study.springboot.dto.MemberDto;
import com.study.springboot.dto.MyPageCdateDto;
import com.study.springboot.dto.MypageMemoDto;
import com.study.springboot.dto.QnADto;

@Controller
@RequestMapping("/yejin")
public class MyPageController {
	
	@Autowired
	MyPageDao dao;
	
	ArrayList<MemberDto> mypagelist;
	ArrayList<MyPageCdateDto> calendarlist;
	ArrayList<MypageMemoDto> memolist;
	ArrayList<QnADto> qnalist;
	ArrayList<ComplaintDto> complaintlist;
	ArrayList<ChallengeDto> challengelist;
	ArrayList<CommentDto> commentlist;
	
	
	@RequestMapping("/mypage_main_list")
	public String Mypage_Main_List(Model model, @RequestParam("member_id") String member_id) {
		
		mypagelist = dao.MyPageMainList(member_id);

		model.addAttribute("list", mypagelist);

		return "/mypage/member";
	}
	
	@RequestMapping("/mypage_challenge_list")
	public String Mypage_Challenge_List(Model model, 
			@RequestParam("member_id") String member_id) {
		
		challengelist = dao.MyPageChallengeList(member_id);

		model.addAttribute("list", challengelist);

		return "/Andchallenge/private_list";
	}
		
	@RequestMapping("/mypage_challege_date")
	public String MyPageCertificateDate(Model model, @RequestParam("member_num") int member_num) {
		
		calendarlist = dao.MyPageCertificateDate(member_num);
		
		model.addAttribute("list", calendarlist);
		
		return "/mypage/certificate";
	}
	
	@RequestMapping("/mypage_memo_select")
	public String MyPageMemoList(Model model,
			@RequestParam("member_id") String member_id) {
		
		memolist = dao.MyPageMemo(member_id);
		
		model.addAttribute("list", memolist);
		
		return "/mypage/memo";
	}
	
	@RequestMapping("/mypage_memo_insert")
	public void MyPageMemoInsert(Model model, 
			@RequestParam("member_id") String member_id,
			@RequestParam("memo_title") String memo_title,
			@RequestParam("memo_content") String memo_content, 
			@RequestParam("memo_date") String memo_date) {
		
		dao.MyPageMemoInsert(memo_title, memo_content, member_id , memo_date);
				
	}
	
	@RequestMapping("/mypage_modify_password")
	public void MyPagePwModify(Model model, 
			@RequestParam("id") String id,
			@RequestParam("pw") String pw) {
		
		System.out.println();
		dao.PasswordModify(id, pw);
				
	}
	
	@RequestMapping("/mypage_question_list")
	public String MyPageQnAList(Model model, 
			@RequestParam("id") String id) {
		
		System.out.println();
		qnalist = dao.MyPageQnAList(id);

		model.addAttribute("list", qnalist);
		
		return "/mypage/qnalist";
	}
	
	@RequestMapping("/mypage_question_insert")
	public void MyPageQnAInsert(Model model, 
			@RequestParam("id") String id,
			@RequestParam("title") String title,
			@RequestParam("content") String content,
			@RequestParam("image") String image) {
		
		System.out.println();
		dao.MyPageQnAInsert(id, title, content, image);
		
	}
	
	@RequestMapping("/mypage_complaint_list")
	public String MyPageComplaintList(Model model, 
			@RequestParam("id") String id) {
		
		System.out.println();
		complaintlist = dao.MyPageComplaintList(id);

		model.addAttribute("list", complaintlist);
		
		return "/mypage/complaint";
	}
	
	@RequestMapping("/mypage_complaint_challenge")
	public void MyPageComplaintChallenge(Model model, 
			@RequestParam("challenge_num") int challenge_num, 
			@RequestParam("id") String id, 
			@RequestParam("content") String complaint_content) {
		
		dao.MyPageComplaintChallenge(challenge_num, id, complaint_content);
		
		System.out.println();
	}
	
	@RequestMapping("/mypage_complaint_comment")
	public void MyPageComplaintComment(Model model, 
			@RequestParam("comment_num") int comment_num, 
			@RequestParam("id") String id, 
			@RequestParam("content") String complaint_content) {
		
		dao.MyPageComplaintComment(comment_num, id, complaint_content);
		
		System.out.println();
	}
	
	@RequestMapping("/mypage_profile_modify")
	public String MyPageProfileModify(Model model, 
			@RequestParam("id") String id, 
			@RequestParam("nickname") String nickname, 
			@RequestParam("introduce") String introduce) {
		
		dao.ProfileModify(id, nickname, introduce);
		
		mypagelist = dao.MyPageMainList(id);

		model.addAttribute("list", mypagelist);

		return "/mypage/member";
	}
	
	@RequestMapping("/mypage_comment_complaint")
	public String MyPageComment(Model model, 
			@RequestParam("commentNum") int comment_num) {
		
		commentlist = dao.MyPageComment(comment_num);
		
		model.addAttribute("list", commentlist);
			
		return "/mypage/comment";
	}
	
	@RequestMapping("/profile_Image")
	public String ProfileImage(Model model, 
			@RequestParam("id") String id) {
		
		System.out.println(id);
		model.addAttribute("profileImg",dao.ProfileImg(id) );		

		return "/mypage/profileImg";
	}
	
	@RequestMapping("/nickName")
	public String NickName(Model model, 
			@RequestParam("id") String id) {
		
		System.out.println(id);
		model.addAttribute("Nickname",dao.NickName(id) );		

		return "/mypage/nickname";
	}
	
}
