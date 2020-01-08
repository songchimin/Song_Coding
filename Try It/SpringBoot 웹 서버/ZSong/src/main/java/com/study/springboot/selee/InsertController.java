package com.study.springboot.selee;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.study.springboot.FcmUtil;
import com.study.springboot.dao.SeleeDao;
import com.study.springboot.dao.Web_ChallengeDao;
import com.study.springboot.dto.CertificateDto;
import com.study.springboot.dto.ChallengeDto;
import com.study.springboot.dto.CountDto;
import com.study.springboot.dto.MemberDto;

@Controller
@RequestMapping("/selee")
public class InsertController {
	
   @Autowired
   SeleeDao dao;
   
   @Autowired
   Web_ChallengeDao daoo;
   
   List<MemberDto> userlist;
   ArrayList<ChallengeDto> challengelist;
   
   // 피드추가
   @RequestMapping("/feed_insert") 
   public void feed_insert(HttpServletRequest request, 
         @RequestParam("member_id") String member_id, 
         @RequestParam("challenge_num") String challenge_num,
         @RequestParam("challenge_title") String challenge_title, 
         @RequestParam("feed_comment") String feed_comment,
         @RequestParam("feed_info") String feed_info) {
      
      System.out.println("feed_insert");
      String information = feed_info;
      
      //맵정보추가
      if(information.substring(0, 3).equals("map")) {
    	  information = "map";
      }
      
      System.out.println(member_id+"  "+ challenge_num+"  "+challenge_title+"  "+information+"  "+feed_comment);
      
      dao.feed_insert(member_id, challenge_num, challenge_title, information, feed_comment);
      dao.certificate_update(challenge_num, member_id);
      dao.record_update(challenge_num, member_id);
   } 
   
   // 맵좌표추가
   @RequestMapping("/map_insert") 
   public void map_insert(HttpServletRequest request, 
         @RequestParam("member_id") String member_id, 
         @RequestParam("challenge_num") String challenge_num,
         @RequestParam("feed_info") String inform) {
      
      System.out.println("map_insert");
	  
	  int idx;
	  String latitude;
	  String longitude;
		  
	  idx = inform.indexOf(",");
	  latitude = inform.substring(0,idx);
	  longitude = inform.substring(idx+1);
	  
	  double lat = Double.parseDouble(latitude);
	  double lon = Double.parseDouble(longitude);
	  
	  System.out.println("lat, lon : " + lat + "     " + lon);
	  System.out.println(member_id+"  "+ challenge_num+"   "+inform);
	  dao.map_insert(challenge_num, member_id, lat, lon);

   }
   
   // 맵좌표삭제
   @RequestMapping("/map_delete") 
   public void map_delete(HttpServletRequest request, 
         @RequestParam("member_id") String member_id, 
         @RequestParam("challenge_num") String challenge_num,
         @RequestParam("feed_info") String inform) {
      
      System.out.println("map_delete");
     
     dao.map_delete(challenge_num, member_id);

   } 
   
   // 참가하기
   @RequestMapping("/record_insert") 
   public void record_insert(HttpServletRequest request, 
         @RequestParam("challenge_num") String challenge_num,
         @RequestParam("member_id") String member_id) {
      
      System.out.println("record_insert");
      
      ChallengeDto dto;
      
      dto = dao.selectChallenge(challenge_num);
      
      Timestamp challenge_start = dto.getChallenge_start();
      Timestamp challenge_end = dto.getChallenge_end();
      String challenge_frequency = dto.getChallenge_frequency();
      int challenge_fee = dto.getChallenge_fee();
      System.out.println("challenge_start, end : " + challenge_start+"  "+challenge_end);
      
      Date d_start = new Date(challenge_start.getTime());
      Date d_end = new Date(challenge_end.getTime());
      System.out.println("d_start, end : " + d_start+"  "+d_end);
      
      int term = doDiffOfDate(d_start, d_end);
      System.out.println("term : " + term);
      
      int all_count = countCheck(term, challenge_frequency);
      System.out.println("all_count : " + all_count);
      
      java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
      String start = sdf.format(challenge_start);
      String end = sdf.format(challenge_end);
      System.out.println("start, end : " + start +"  "+ end);
      
      dao.record_insert(challenge_num, member_id, all_count, challenge_fee);
      String certificate_date = null;
      try {
           if (challenge_frequency.equals("매일")) {
               certificate_date = start;
             for(int i=0; i < all_count; i++) {
            	System.out.println("매일_certificate_date : "+certificate_date);
                dao.certificate_insert(challenge_num, member_id, certificate_date);
                certificate_date = addDate(certificate_date,0,0,1);
             }
           } else if (challenge_frequency.equals("주중")) {
               certificate_date = start;
             for(int i=0; i < all_count; i++) {
                for(int j = 0; j < 5; j++) {
                	System.out.println("주중_certificate_date : "+certificate_date);
                    dao.certificate_insert(challenge_num, member_id, certificate_date);
                    certificate_date = addDate(certificate_date,0,0,1);
                    i++;
                }
               certificate_date = addDate(certificate_date,0,0,2);
             }
           } else if (challenge_frequency.equals("월,수,금")) {
               certificate_date = start;
             for(int i=0; i < all_count; i++) {
                for(int j = 0; j < 3; j++) {
                	System.out.println("월,수,금_certificate_date : "+certificate_date);
                    dao.certificate_insert(challenge_num, member_id, certificate_date);
                    certificate_date = addDate(certificate_date,0,0,2);
                    i++;
                }
               certificate_date = addDate(certificate_date,0,0,1);
             }
           } else if (challenge_frequency.equals("주말")) {
               certificate_date = start;
               certificate_date = addDate(certificate_date,0,0,5);
             for(int i=0; i < all_count; i++) {
                for(int j = 0; j < 2; j++) {
                	System.out.println("주말_certificate_date : "+certificate_date);
                    dao.certificate_insert(challenge_num, member_id, certificate_date);
                    certificate_date = addDate(certificate_date,0,0,1);
                    i++;
                }
               certificate_date = addDate(certificate_date,0,0,4);
             }
           }
      } catch (Exception e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
   }

   private static String addDate(String dt, int y, int m, int d) throws Exception  {
      SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

      Calendar cal = Calendar.getInstance();
      Date date = (Date) format.parse(dt);
      cal.setTime(date);
        cal.add(Calendar.YEAR, y);      //년 더하기
        cal.add(Calendar.MONTH, m);      //년 더하기
        cal.add(Calendar.DATE, d);      //년 더하기

      return format.format(cal.getTime());
   }
   
   // 두날짜의 차이 구하기
   public int doDiffOfDate(Date start, Date end){
       // 시간차이를 시간,분,초를 곱한 값으로 나누면 하루 단위가 나옴
       long diff = end.getTime() - start.getTime();
       int diffDays = (int) (diff / ((24 * 60 * 60 * 1000))+1)/7;
       
       System.out.println("diffDays : "+diffDays);
       return diffDays;
   }

   public int countCheck(int term, String i_frequency) {
       int fre = 0;
       if (i_frequency.equals("매일")) {
           fre = 7;
       } else if (i_frequency.equals("주중")) {
           fre = 5;
       } else if (i_frequency.equals("월,수,금")) {
           fre = 3;
       } else if (i_frequency.equals("주말")) {
           fre = 2;
       }
       return fre * term;
   }
   
   
// 비공개 승인
   @RequestMapping("/approval_private_content") 
   public void approval_private_content(HttpServletRequest request, HttpServletResponse response, Model model)
			throws ServletException, IOException{
	   
	   System.out.println("record_insert");
	   
	   System.out.println("들ㅇ오는 오");
	      
	   request.setCharacterEncoding("UTF-8");
	   response.setContentType("text/html; charset=UTF-8");
	   
	   int challenge_num = Integer.parseInt(request.getParameter("num"));
	   String member_id = request.getParameter("host");
	   String challenge_num2 = request.getParameter("num");
	   
	   System.out.println(challenge_num+member_id+challenge_num2);
	   
	   
	   
      
      
      ChallengeDto dto;
      
      dto = dao.selectChallenge(challenge_num2);
      
      Timestamp challenge_start = dto.getChallenge_start();
      Timestamp challenge_end = dto.getChallenge_end();
      String challenge_frequency = dto.getChallenge_frequency();
      int challenge_fee = dto.getChallenge_fee();
      System.out.println("challenge_start, end : " + challenge_start+"  "+challenge_end);
      
      Date d_start = new Date(challenge_start.getTime());
      Date d_end = new Date(challenge_end.getTime());
      System.out.println("d_start, end : " + d_start+"  "+d_end);
      
      int term = doDiffOfDate(d_start, d_end);
      System.out.println("term : " + term);
      
      int all_count = countCheck(term, challenge_frequency);
      System.out.println("all_count : " + all_count);
      
      java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
      String start = sdf.format(challenge_start);
      String end = sdf.format(challenge_end);
      System.out.println("start, end : " + start +"  "+ end);
      
      dao.record_insert(challenge_num2, member_id, all_count, challenge_fee);

      String certificate_date = null;
      try {
           if (challenge_frequency.equals("매일")) {
               certificate_date = start;
             for(int i=0; i < all_count; i++) {
            	System.out.println("매일_certificate_date : "+certificate_date);
                dao.certificate_insert(challenge_num2, member_id, certificate_date);
                certificate_date = addDate(certificate_date,0,0,1);
             }
           } else if (challenge_frequency.equals("주중")) {
               certificate_date = start;
             for(int i=0; i < all_count; i++) {
                for(int j = 0; j < 5; j++) {
                	System.out.println("주중_certificate_date : "+certificate_date);
                    dao.certificate_insert(challenge_num2, member_id, certificate_date);
                    certificate_date = addDate(certificate_date,0,0,1);
                    i++;
                }
               certificate_date = addDate(certificate_date,0,0,2);
             }
           } else if (challenge_frequency.equals("월,수,금")) {
               certificate_date = start;
             for(int i=0; i < all_count; i++) {
                for(int j = 0; j < 3; j++) {
                	System.out.println("월수금_certificate_date : "+certificate_date);
                    dao.certificate_insert(challenge_num2, member_id, certificate_date);
                    certificate_date = addDate(certificate_date,0,0,2);
                    i++;
                }
               certificate_date = addDate(certificate_date,0,0,2);
             }
           } else if (challenge_frequency.equals("주말")) {
               certificate_date = start;
             for(int i=0; i < all_count; i++) {
                for(int j = 0; j < 2; j++) {
                	System.out.println("주말_certificate_date : "+certificate_date);
                    dao.certificate_insert(challenge_num2, member_id, certificate_date);
                    certificate_date = addDate(certificate_date,0,0,1);
                    i++;
                }
               certificate_date = addDate(certificate_date,0,0,5);
             }
           }
      } catch (Exception e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
            
      
	        char[] charaters = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z','0','1','2','3','4','5','6','7','8','9'};

	        StringBuffer sb = new StringBuffer();
	        Random rn = new Random();

	        for( int i = 0 ; i < 10 ; i++ ){
	            sb.append( charaters[ rn.nextInt( charaters.length ) ] );
	        }

	        String code = "try-"+sb.toString();
		
		daoo.PrivateChallengeApproval(challenge_num, code);
         
		StringBuffer result = new StringBuffer("");
		result.append("1");

		
		FcmUtil FcmUtil = new FcmUtil();
	    FcmUtil.send_FCM(dto.getChallenge_host(), dto.getChallenge_title(), "챌린지가 승인 되었습니다.\n확인해보세요.", "");
		
		response.getWriter().write(result.toString());
   }
}