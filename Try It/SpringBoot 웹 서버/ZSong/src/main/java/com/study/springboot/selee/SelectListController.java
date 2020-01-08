package com.study.springboot.selee;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.study.springboot.dao.SeleeDao;
import com.study.springboot.dto.CertificateDto;
import com.study.springboot.dto.CountDto;
import com.study.springboot.dto.FeedCommentDto;
import com.study.springboot.dto.FeedDto;
import com.study.springboot.dto.FeedLikeDto;
import com.study.springboot.dto.MapDto;
import com.study.springboot.dto.MemberDto;

@Controller
@RequestMapping("/selee")
public class SelectListController {
   
   @Autowired
   SeleeDao dao;
   
   ArrayList<CertificateDto> certificateList;
   ArrayList<CountDto> count;
   ArrayList<FeedDto> feedList;
   ArrayList<FeedDto> followfeedList;
   ArrayList<MapDto> locationList;
   ArrayList<FeedCommentDto> commentlist;
   ArrayList<FeedLikeDto> feedLikeList;
   
    // 인증 list
    @RequestMapping("/certificate_list")
    public String CertificateList(Model model, 
          @RequestParam("member_id") String member_id) {
      
       System.out.println("CertificateList");
      
       count  = new ArrayList<>();
       certificateList = new ArrayList<>();
      
       certificateList = dao.CertificateList(member_id);
      
       System.out.println(certificateList.size());
       for(int i = 0 ; i < certificateList.size() ; i++)
       {
          System.out.println("challenge_num : "+ certificateList.get(i).getChallenge_num());
         
          CountDto dto = new CountDto();
         
          dto.setAll_count(dao.CertificateList_count(certificateList.get(i).getChallenge_num() , member_id));
          dto.setCheck_count(dao.CertificateList_check_count(certificateList.get(i).getChallenge_num() , member_id));
         
          count.add(i, dto);
       }
      
       model.addAttribute("count", count);
       model.addAttribute("list", certificateList);
       return "/selee/certificate_list";
    }  
    
    // 피드 list
    @RequestMapping("/feed_list")
    public String FollowFeedList(Model model, 
         @RequestParam("member_id") String member_id,
          @RequestParam("type") String type) {
      
       System.out.println("FeedList");
       System.out.println(type);
       
       if(type.equals("follow")) {         
           feedList = dao.FollowFeedList(member_id);
       } else if (type.equals("all")) {
           feedList = dao.FeedList(member_id);
       }
       model.addAttribute("list", feedList);
       
      int[] likeCount = new int[feedList.size()];
      int[] likeExist = new int[feedList.size()];
      int[] comment_count = new int[feedList.size()];
      String[] feed_comment_userid = new String[feedList.size()];
      String[] feed_comment_content = new String[feedList.size()];
      
      ArrayList<MemberDto> memberList = new ArrayList<MemberDto>();
      MemberDto mDto = new MemberDto();
      for (int i = 0; i < feedList.size(); i++) {
        mDto = dao.MemberList(feedList.get(i).getMember_id());
        System.out.println("멤버아이디확인 "+ feedList.get(i).getMember_id());
        System.out.println("멤버디티오확인 " + mDto );
         memberList.add(mDto);
            
         likeCount[i] = dao.FeedLikeCount(feedList.get(i).getFeed_num());
         System.out.println(feedList.get(i).getFeed_num() + " : " + likeCount[i]);
         // 여기서 한번
         likeExist[i] = dao.FeedLikeExist(member_id, feedList.get(i).getFeed_num());
         System.out.println(feedList.get(i).getFeed_num() + " : " + likeExist[i]);
         System.out.println(feedList.get(i));
         
         comment_count[i] = dao.FeedCommentSelectCount(feedList.get(i).getFeed_num());
//         if(comment_count[i] != 0) {
//            feed_comment_userid[i] = dao.FeedCommentUser(feedList.get(i).getFeed_num());
//            feed_comment_content[i] = dao.FeedCommentContent(feedList.get(i).getFeed_num());      
//         } 시간남으면 출력하자 ㅠㅠㅠㅠㅠㅠㅠ
      }
      
      model.addAttribute("memberList", memberList);
      model.addAttribute("countlist", likeCount);
      model.addAttribute("existlist", likeExist);
      model.addAttribute("comment_count", comment_count);
      
      System.out.println(feedList+" /n "+likeCount+" /n "+likeExist+" /n "+comment_count);
      return "/selee/feed_list";
    } 
    
    
   // 피드 좋아요
   @RequestMapping("/feed_like")
   public String Public_Challenge_like(Model model, 
         @RequestParam("feed_num") int feed_num,
         @RequestParam("member_id") String member_id,
         @RequestParam("type") String type) {

      // 여기서 체크하는 메소드 만들어서 있으면 delete 없으면 insert
      if (dao.FeedLikeExist(member_id, feed_num) == 0) {
         dao.FeedLikeInsert(feed_num, member_id);
         System.out.println("FeedLikeInsert");
      } else {
         dao.FeedLikeDelete(member_id, feed_num);
         System.out.println("FeedLikeDelete");
      }
      
      System.out.println("feed_like");
      
          System.out.println("FeedList");
          System.out.println(type);
          
          if(type.equals("follow")) {         
              feedList = dao.FollowFeedList(member_id);
          } else if (type.equals("all")) {
              feedList = dao.FeedList(member_id);
          }
          model.addAttribute("list", feedList);
          
         int[] likeCount = new int[feedList.size()];
         int[] likeExist = new int[feedList.size()];
         int[] comment_count = new int[feedList.size()];
         String[] feed_comment_userid = new String[feedList.size()];
         String[] feed_comment_content = new String[feedList.size()];
         
         for (int i = 0; i < feedList.size(); i++) {
            likeCount[i] = dao.FeedLikeCount(feedList.get(i).getFeed_num());
            System.out.println(feedList.get(i).getFeed_num() + " : " + likeCount[i]);
            // 여기서 한번
            likeExist[i] = dao.FeedLikeExist(member_id, feedList.get(i).getFeed_num());
            System.out.println(feedList.get(i).getFeed_num() + " : " + likeExist[i]);
            System.out.println(feedList.get(i));
            
            comment_count[i] = dao.FeedCommentSelectCount(feedList.get(i).getFeed_num());
//            if(comment_count[i] != 0) {
//               feed_comment_userid[i] = dao.FeedCommentUser(feedList.get(i).getFeed_num());
//               feed_comment_content[i] = dao.FeedCommentContent(feedList.get(i).getFeed_num());      
//            } 시간남으면 출력하자 ㅠㅠㅠㅠㅠㅠㅠ
         }
         
         model.addAttribute("countlist", likeCount);
         model.addAttribute("existlist", likeExist);
         model.addAttribute("comment_count", comment_count);
         
         System.out.println(feedList+" /n "+likeCount+" /n "+likeExist+" /n "+comment_count);
         return "/selee/feed_list";
   }
   
   // 댓글 추가
   @RequestMapping("/comment_insert")
   public String comment_insert(HttpServletRequest request, Model model,
         @RequestParam("feed_num") int feed_num, 
         @RequestParam("member_id") String member_id,
         @RequestParam("content") String content) {

      System.out.println("comment_insert");

      dao.CommentInsert(feed_num, member_id, content);

      commentlist = dao.CommentSelect();
      model.addAttribute("list", commentlist);

      int[] array = new int[commentlist.size()];
      int[] likeExist = new int[commentlist.size()];

      for (int i = 0; i < commentlist.size(); i++) {
         array[i] = dao.CommentLikeCount(commentlist.get(i).getFeed_num(), commentlist.get(i).getComment_num());
         System.out.println(commentlist.get(i).getFeed_num());

         likeExist[i] = dao.CommentLikeExist(commentlist.get(i).getMember_id(),
               commentlist.get(i).getFeed_num(), commentlist.get(i).getComment_num());
         System.out.println(commentlist.get(i).getFeed_num() + " : " + likeExist[i]);
      }

      model.addAttribute("countlist", array);
      model.addAttribute("existlist", likeExist);

      return "/selee/feed_comment_list";
   }

   // 댓글 출력
   @RequestMapping("/comment_list")
   public String Feed_Comment_list(Model model) {

      System.out.println("Feed_Comment_list");

      commentlist = dao.CommentSelect();

      model.addAttribute("list", commentlist);

      // 댓글 좋아요 숫자 출력
      int[] likeCount = new int[commentlist.size()];
      int[] likeExist = new int[commentlist.size()];

      for (int i = 0; i < commentlist.size(); i++) {
         likeCount[i] = dao.CommentLikeCount(commentlist.get(i).getFeed_num(),
               commentlist.get(i).getComment_num());
         System.out.println(commentlist.get(i).getFeed_num() + " : " + likeCount[i]);

         // 여기서 한번
         likeExist[i] = dao.CommentLikeExist(commentlist.get(i).getMember_id(),
               commentlist.get(i).getFeed_num(), commentlist.get(i).getComment_num());
         System.out.println(commentlist.get(i).getFeed_num() + " : " + likeExist[i]);
      }
      model.addAttribute("countlist", likeCount);
      model.addAttribute("existlist", likeExist);

      return "/selee/feed_comment_list";
   }

   // 댓글 좋아요
   @RequestMapping("/feed_comment_like")
   public String Feed_Comment_like(Model model, 
         @RequestParam("feed_num") int feed_num,
         @RequestParam("member_id") String member_id, 
         @RequestParam("comment_num") int comment_num) {

      // 여기서 체크하는 메소드 만들어서 있으면 delete 없으면 insert
      if (dao.CommentLikeExist(member_id, feed_num, comment_num) == 0) {
         dao.CommentLikeInsert(feed_num, comment_num, member_id);
         System.out.println("CommentLikeInsert");

      } else {
         dao.CommentLikeDelete(member_id, feed_num, comment_num);
         System.out.println("CommentLikeDelete");
      }

      System.out.println("Public_Comment_like");

      commentlist = dao.CommentSelect();

      model.addAttribute("list", commentlist);

      // 댓글 좋아요 출력
      int[] likeCount = new int[commentlist.size()];
      int[] likeExist = new int[commentlist.size()];

      for (int i = 0; i < commentlist.size(); i++) {
         likeCount[i] = dao.CommentLikeCount(commentlist.get(i).getFeed_num(),
               commentlist.get(i).getComment_num());
         System.out.println(commentlist.get(i).getFeed_num() + " : " + likeCount[i]);

         // 여기서 한번
         likeExist[i] = dao.CommentLikeExist(commentlist.get(i).getMember_id(),
               commentlist.get(i).getFeed_num(), commentlist.get(i).getComment_num());
         System.out.println(commentlist.get(i).getFeed_num() + " : " + likeExist[i]);
      }
      model.addAttribute("countlist", likeCount);
      model.addAttribute("existlist", likeExist);

      return "/selee/feed_comment_list";
   }
   
   // 피드 좋아요 유저 출력
   @RequestMapping("/feed_like_list")
   public String Feed_Like_list(Model model,
         @RequestParam("feed_num") int feed_num,
         @RequestParam("user_id") String user_id) {

      System.out.println("Feed_Like_list");

      feedLikeList = dao.FeedLikeSelect(feed_num);

      model.addAttribute("list", feedLikeList);

      //팔로우 체크
      int[] followExist = new int[feedLikeList.size()];

      for (int i = 0; i < feedLikeList.size(); i++) {
         followExist[i] = dao.followExist(user_id, feedLikeList.get(i).getMember_id());
         System.out.println(feedLikeList.get(i).getMember_id() + " : " + followExist[i]);
         
      }
      model.addAttribute("existlist", followExist);

      return "/selee/feed_like_list";
   }
   
   // 팔로우
   @RequestMapping("/follow")
   public String Follow(Model model, 
         @RequestParam("my_id") String my_id,
         @RequestParam("member_id") String member_id,
         @RequestParam("feed_num") int feed_num) {

      // 여기서 체크하는 메소드 만들어서 있으면 delete 없으면 insert
      if (dao.followExist(my_id, member_id) == 0) {
         dao.followInsert(my_id, member_id);
         System.out.println("FollowInsert");

      } else {
         dao.followDelete(my_id, member_id);
         System.out.println("FollowDelete");
      }

      System.out.println("Follow");

      feedLikeList = dao.FeedLikeSelect(feed_num);

      model.addAttribute("list", feedLikeList);

      //팔로우 체크
      int[] followExist = new int[feedLikeList.size()];

      for (int i = 0; i < feedLikeList.size(); i++) {
         followExist[i] = dao.followExist(my_id, feedLikeList.get(i).getMember_id());
         System.out.println(feedLikeList.get(i).getMember_id() + " : " + followExist[i]);
         
      }
      model.addAttribute("existlist", followExist);

      return "/selee/feed_like_list";
   }
    
    // Map location list
    @RequestMapping("/location_list")
    public String LocationList(Model model, 
          @RequestParam("challenge_num") String challenge_num,
          @RequestParam("member_id") String member_id,
          @RequestParam("certificate_date") String certificate_date) {
      
       System.out.println("LocationList");
       String date = certificate_date.substring(2, 10);
       date = date.replace(".", "/");
       
       System.out.println(challenge_num +"  "+ member_id +"  "+ date);
       locationList = new ArrayList<>();
       locationList = dao.LocationList(challenge_num, member_id, date);
       
       System.out.println(locationList);
       
       model.addAttribute("location_list", locationList);
       return "/selee/location_list";
    }  
}