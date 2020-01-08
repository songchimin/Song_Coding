package com.study.springboot.dao;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

import com.study.springboot.dto.CertificateDto;
import com.study.springboot.dto.ChallengeDto;
import com.study.springboot.dto.FeedCommentDto;
import com.study.springboot.dto.FeedDto;
import com.study.springboot.dto.FeedLikeDto;
import com.study.springboot.dto.MapDto;
import com.study.springboot.dto.MemberDto;

@Mapper
public interface SeleeDao {

   public void feed_insert(String member_id, String challenge_num, String challenge_title, String feed_info, String feed_comment);
   public void map_insert(String challenge_num, String member_id, double lat, double lon);
   public void map_delete(String challenge_num, String memebr_id);
   
   public void certificate_update(String challenge_num, String member_id);
   public void record_update(String challenge_num, String member_id);
   
   public ArrayList<CertificateDto> CertificateList(String member_id);   
   public int CertificateList_count(int cnum, String id);
   public int CertificateList_check_count(int cnum, String id);
   
   public void record_insert(String challenge_num, String member_id, int all_count, int challenge_fee);
   public void certificate_insert(String Challenge_num, String member_id, String certificate_date);
   
   public ChallengeDto selectChallenge(String challenge_num);
   
   public ArrayList<FeedDto> FollowFeedList(String member_id); 
   public ArrayList<FeedDto> FeedList(String member_id);
   public MemberDto MemberList(String member_id);
   
   public ArrayList<FeedLikeDto> FeedLikeSelect(int feed_num);
   public int followExist(String follower_id, String following_id);
   public void followInsert(String my_id, String member_id);
   public void followDelete(String my_id, String member_id);
   
   public ArrayList<MapDto> LocationList(String challenge_num, String member_id, String certificate_date);
   public void FeedLikeInsert(int feed_num, String member_id);
   public void FeedLikeDelete(String member_id, int feed_num);
   public int FeedLikeExist(String member_id, int feed_num);
   public int FeedLikeCount(int feed_num);
   
   public void CommentInsert(int feed_num, String member_id, String content);
	public ArrayList<FeedCommentDto> CommentSelect();
	public int FeedCommentSelectCount(int feed_num);
	public void CommentLikeInsert(int feed_num, int comment_num, String member_id);
	public void CommentLikeDelete(String member_id, int challenge_num, int comment_num);
	public int CommentLikeExist(String member_id, int challenge_num, int comment_num);
	public int CommentLikeCount(int feed_num, int comment_num);
 
}