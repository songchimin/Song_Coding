package com.study.springboot.dao;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

import com.study.springboot.dto.ChallengeDto;
import com.study.springboot.dto.CommentDto;
import com.study.springboot.dto.ComplaintDto;
import com.study.springboot.dto.MemberDto;
import com.study.springboot.dto.MyPageCdateDto;
import com.study.springboot.dto.MypageMemoDto;
import com.study.springboot.dto.QnADto;

@Mapper	
public interface MyPageDao {
	
	public ArrayList<MemberDto> MyPageMainList(String member_id);
	public ArrayList<MyPageCdateDto> MyPageCertificateDate(int member_num);
	
	public void MyPageMemoInsert(String memo_title, String memo_content, String member_id, String memo_date);
	public ArrayList<MypageMemoDto> MyPageMemo(String userid);
	public void PasswordModify(String id, String pw);

	public ArrayList<QnADto> MyPageQnAList(String id);
	public void MyPageQnAInsert(String id, String title, String content, String image);

	public ArrayList<ComplaintDto> MyPageComplaintList(String userid);
	public void MyPageComplaintChallenge(int challenge_num, String member_id, String complaint_content);
	public void MyPageComplaintComment(int comment_num, String member_id, String complaint_content);

	public void ProfileModify(String id, String nickname, String introduce);
	
	public ArrayList<ChallengeDto> MyPageChallengeList(String id);
	public String ProfileImg(String id);
	
	public String NickName(String id);	
	
	public ArrayList<CommentDto> MyPageComment(int num);

}