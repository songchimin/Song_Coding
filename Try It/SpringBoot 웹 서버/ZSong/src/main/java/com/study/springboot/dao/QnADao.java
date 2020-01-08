package com.study.springboot.dao;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

import com.study.springboot.dto.ComplaintDto;
import com.study.springboot.dto.QnADto;


@Mapper
public interface QnADao {
	
//	public ArrayList<ChallengeDto> All_RegisterWait_challenge(int end, int start);
//	public ChallengeDto challenge_content(int num);
//	public int AllWaitchallengeCount();
	
	
	public ArrayList<QnADto> All_WaitQnA(int end, int start);
	public int WaitQnACount();
	public void QnAsubmit(int num, String manager_id, String content);
	
	
	public ArrayList<QnADto> EndQnA(int end, int start);
	public int EndQnACount();
	
	
	public void AddComment(int question_num, String manager_id, String question_answer);
	public ArrayList<QnADto> SelectQnAComment(int num);
	
	
	//댓글 신고내역
	public ArrayList<ComplaintDto> Wait_vote_comment_report(int end, int start);
	public int Wait_vote_comment_reportCount();
	
	public String Get_Comment_Content(int comment_num);	
	
	public int vote_comment_report_submit(int complaint_num, String complaint_reply);
	public void delete_comment(int comment_num);
	
}
