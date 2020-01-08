package com.study.springboot;


import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sound.sampled.ReverbType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.study.springboot.dao.ISimpleBbsDao;
import com.study.springboot.dao.SeleeDao;
import com.study.springboot.dao.Web_ChallengeDao;
import com.study.springboot.dto.ChallengeDto;
import com.study.springboot.dto.CommentDto;
import com.study.springboot.dto.FeedDto;
import com.study.springboot.dto.MemberDto;
import com.study.springboot.dto.PageInfo;
import com.study.springboot.dto.RecordAllDto;
import com.study.springboot.dto.RecordDto;

@Controller
public class ChallengeController {

	@Autowired
	Web_ChallengeDao dao;

	
	List<ChallengeDto> challenge;
	List<MemberDto> member;
	List<RecordDto> Record;
	List<RecordAllDto> AllRecord;
	MemberDto mdto;
	
	List<CommentDto> comment;
	List<FeedDto> feed;
	
	@RequestMapping("/register_challenge")
	public String register_challenge(HttpServletRequest request, Model model){
		
		
		HttpSession session = null;
		session = request.getSession();
		
		int nPage = 1;
		
		nPage = Integer.parseInt(request.getParameter("page"));
		String option = request.getParameter("findoption");
		String text = request.getParameter("findtext");
				
		
		int listCount = 15;	//한페이지당 보여줄 게시물 갯수
		int pageCount = 5; //하단에 보여줄 리스트 갯수
		
		int totalCount = dao.AllWaitchallengeCount();
				
		int totalPage = totalCount / listCount;
		if(totalCount % listCount > 0)
			totalPage++;
		
		int myCurPage = nPage;
		if(myCurPage > totalPage)
			myCurPage = totalPage;
		if(myCurPage < 1)
			myCurPage = 1;
		
		int startPage = ((myCurPage - 1) / pageCount) * pageCount + 1;
		
		int endPage = startPage + pageCount - 1;
		if(endPage > totalPage)
			endPage = totalPage;

		PageInfo pinfo = new PageInfo();
		
		pinfo.setTotalCount(totalCount);
		pinfo.setListCount(listCount);
		pinfo.setTotalPage(totalPage);
		pinfo.setCurPage(myCurPage);
		pinfo.setPageCount(pageCount);
		pinfo.setStartPage(startPage);
		pinfo.setEndPage(endPage);
		
		request.setAttribute("page", pinfo);
		nPage = pinfo.getCurPage();
		session.setAttribute("cpage", nPage);
		
		int start = (nPage-1)* listCount + 1;
		int end = (nPage-1)*listCount + listCount;

		
		if( option == null ) {
			challenge = dao.All_RegisterWait_challenge(end, start);
		}else if(option.equals("옵션")) {
			challenge = dao.All_RegisterWait_challenge(end, start);
		}else {
			if(option.equals("카테고리")) {
				challenge = dao.FindRegisterWait_challenge_title(end, start, text);
			}else if(option.equals("제목")) {
				challenge = dao.FindRegisterWait_challenge_name(end, start, text);
			}else if(option.equals("날짜")) {
				challenge = dao.FindRegisterWait_challenge_name(end, start, text);
				//수정
			}else if(option.equals("구분")) {
				option = "challenge_public";
				if(text.equals("공개"))
					text = "0";
				else
					text = "1";
				
				challenge = dao.FindRegisterWait_challenge_public(end, start, text);
			}
		}
		
		model.addAttribute("challenge", challenge);

		return "/challenge/register_challenge3";
	}
	
	
	@RequestMapping("/vote_public_challenge")
	public String vote_public_challenge(HttpServletRequest request, Model model){
		
		
		HttpSession session = null;
		session = request.getSession();
		
		int nPage = 1;
		
		nPage = Integer.parseInt(request.getParameter("page"));
		String option = request.getParameter("findoption");
		String text = request.getParameter("findtext");

		
		int listCount = 7;	//한페이지당 보여줄 게시물 갯수
		int pageCount = 5; //하단에 보여줄 리스트 갯수
		
		int totalCount = dao.public_challenge_count("1","0","0");
		 
		int totalPage = totalCount / listCount;
		if(totalCount % listCount > 0)
			totalPage++;
		
		int myCurPage = nPage;
		if(myCurPage > totalPage)
			myCurPage = totalPage;
		if(myCurPage < 1)
			myCurPage = 1;
		
		int startPage = ((myCurPage - 1) / pageCount) * pageCount + 1;
		
		int endPage = startPage + pageCount - 1;
		if(endPage > totalPage)
			endPage = totalPage;

		PageInfo pinfo = new PageInfo();
		
		pinfo.setTotalCount(totalCount);
		pinfo.setListCount(listCount);
		pinfo.setTotalPage(totalPage);
		pinfo.setCurPage(myCurPage);
		pinfo.setPageCount(pageCount);
		pinfo.setStartPage(startPage);
		pinfo.setEndPage(endPage);
		
		request.setAttribute("page", pinfo);
		nPage = pinfo.getCurPage();
		session.setAttribute("cpage", nPage);
		
		int start = (nPage-1)* listCount + 1;
		int end = (nPage-1)*listCount + listCount;

		
		if( option == null ) {
			challenge = dao.public_challenge(end, start);
		}else if(option.equals("옵션")) {
			challenge = dao.public_challenge(end, start);
		}else {
			if(option.equals("카테고리")) {
//				challenge = dao.FindRegisterWait_challenge_title(end, start, text);
			}else if(option.equals("제목")) {
//				challenge = dao.FindRegisterWait_challenge_name(end, start, text);
			}else if(option.equals("날짜")) {
//				challenge = dao.FindRegisterWait_challenge_name(end, start, text);
				//수정
			}else if(option.equals("구분")) {
				option = "challenge_public";
				if(text.equals("공개"))
					text = "0";
				else
					text = "1";
				
//				challenge = dao.FindRegisterWait_challenge_public(end, start, text);
			}
		}
		
		
		for(int i=0 ; i < challenge.size() ; i++) {
			challenge.get(i).setCount(dao.challenge_vote_count(challenge.get(i).getChallenge_num()));
		}
		
		model.addAttribute("challenge", challenge);

		return "/challenge/vote_public_challenge2";
	}
	
	
	
	@RequestMapping("/challenge_content")
	public String challenge_content(HttpServletRequest request, Model model){
		
		int num = Integer.parseInt(request.getParameter("num"));
		
		ChallengeDto dto = dao.challenge_content(num);
		model.addAttribute("challenge", dto);
		
		if(dto.getChallenge_public().equals("0"))
		{
			model.addAttribute("vote_comment", dao.challenge_content_vote_comment(num));
		}else if(dto.getChallenge_public().equals("1")) {
			
			return "/challenge/challenge_private_content";
		}
		return "/challenge/challenge_content";
	}
	
	
	@RequestMapping("/challenge_vote_content")
	public String challenge_vote_content(HttpServletRequest request, Model model){
		
		int num = Integer.parseInt(request.getParameter("num"));
		
		ChallengeDto dto = dao.public_challenge_content(num);
		
		comment = dao.challenge_content_vote_comment(num);
				
		
		for(int i =0 ; i < comment.size() ; i++) {
			mdto = dao.getMemberInfo(comment.get(i).getMember_id());
			
			comment.get(i).setNickname(mdto.getMember_nickname());
			comment.get(i).setProfile(mdto.getMember_profile_image());
			comment.get(i).setCount(dao.like_count(comment.get(i).getComment_num()));
		}
		
		
		model.addAttribute("challenge", dto);
		model.addAttribute("vote_comment", comment);
		

		return "/challenge/challenge_vote_content";
	}
	
	
	
	@RequestMapping("/approval_public_challenge")
	public void approval_public_challenge(HttpServletRequest request, HttpServletResponse response, Model model)
			throws ServletException, IOException{
		
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		int num = Integer.parseInt(request.getParameter("num"));
		String host = request.getParameter("host");
		
		dao.approve_public_challenge(num, 1);

		FcmUtil FcmUtil = new FcmUtil();
	    FcmUtil.send_FCM(dao.get_token(host), "공개 트라잇 승인", "공개 트라잇이 승인 되었습니다.\n투표를 확인해보세요.", "");
		
		StringBuffer result = new StringBuffer("");
		result.append("1");
		response.getWriter().write(result.toString());
	}
	

	
	@RequestMapping("/challenge_content_popup")
	public String challenge_content_popup(HttpServletRequest request, Model model){
			
		int num = Integer.parseInt(request.getParameter("num"));
		
		model.addAttribute("challenge", dao.challenge_vote_content(num));
		
		return "/challenge/challenge_content_popup";
	}
	
	
	@RequestMapping("/approval_content")		// 라이트박스 페이지
	public void approval_content(HttpServletRequest request, HttpServletResponse response, Model model)
			throws ServletException, IOException{
		
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		int num = Integer.parseInt(request.getParameter("num"));
		String name = request.getParameter("name");
		String type = request.getParameter("check");
		String title = request.getParameter("title");
		String frequency = request.getParameter("frequency");
		String date = request.getParameter("date");
		String arrDate[] = date.split(" - ");
		int fee = Integer.parseInt(request.getParameter("money"));
		fee = fee*10000;
		String time = request.getParameter("time");
		String detail = request.getParameter("detail");
		String expStr = request.getParameter("exp");
		int exp = Integer.parseInt(expStr.replace("exp", ""));
		
		
		arrDate[0]=arrDate[0].replace("/", "-");
		arrDate[1]=arrDate[1].replace("/", "-");
		
		arrDate[0]=arrDate[0]+" 00:00:00.0";
		arrDate[1]=arrDate[1]+" 00:00:00.0";
		
		
		Timestamp start=null;
		Timestamp end=null;	
		
        start = java.sql.Timestamp.valueOf(arrDate[0]);
        end = java.sql.Timestamp.valueOf(arrDate[1]);
		
	
	    System.out.println(num+" "+ name+" "+ title+" "+ type+" "+ frequency+" "+ start+" "+ end+" "+ fee+" "+ time+" "+ detail +" "+ exp);
	    
	    dao.ChallengeApproval(num, name, title, type, frequency, start, end, fee, time, detail, exp);
	    
		StringBuffer result = new StringBuffer("");

		result.append("1");
		
		FcmUtil FcmUtil = new FcmUtil();
	    FcmUtil.send_FCM("", title, "챌린지가 승인 되었습니다.\n확인해보세요.", request.getParameter("num"));
		
		response.getWriter().write(result.toString());

	    
//		return "/account/stop_content";
	}
	
	
	
//	@RequestMapping("/approval_private_content")		// 라이트박스 페이지
//	public void approval_private_content(HttpServletRequest request, HttpServletResponse response, Model model)
//			throws ServletException, IOException{
//		
//		request.setCharacterEncoding("UTF-8");
//		response.setContentType("text/html; charset=UTF-8");
//		
//		int num = Integer.parseInt(request.getParameter("num"));
//				
//	        char[] charaters = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z','0','1','2','3','4','5','6','7','8','9'};
//
//	        StringBuffer sb = new StringBuffer();
//	        Random rn = new Random();
//
//	        for( int i = 0 ; i < 10 ; i++ ){
//	            sb.append( charaters[ rn.nextInt( charaters.length ) ] );
//	        }
//
//	        String code = "try-"+sb.toString();
//		
//		dao.PrivateChallengeApproval(num, code);
//		
//		
//		
//	
//	    
//		StringBuffer result = new StringBuffer("");
//		result.append("1");
//		
//		response.getWriter().write(result.toString());
//	}
	
	
	@RequestMapping("/ongoing_challenge")
	public String ongoing_challenge(HttpServletRequest request, Model model){
			
		HttpSession session = null;
		session = request.getSession();
		
		int nPage = 1;
		
		nPage = Integer.parseInt(request.getParameter("page"));
		String option = request.getParameter("findoption");
		String text = request.getParameter("findtext");
				
		
		int listCount = 10;	//한페이지당 보여줄 게시물 갯수
		int pageCount = 5; //하단에 보여줄 리스트 갯수
		
		int totalCount = dao.AllOngoingchallengeCount();
				
		int totalPage = totalCount / listCount;
		if(totalCount % listCount > 0)
			totalPage++;
		
		int myCurPage = nPage;
		if(myCurPage > totalPage)
			myCurPage = totalPage;
		if(myCurPage < 1)
			myCurPage = 1;
		
		int startPage = ((myCurPage - 1) / pageCount) * pageCount + 1;
		
		int endPage = startPage + pageCount - 1;
		if(endPage > totalPage)
			endPage = totalPage;

		PageInfo pinfo = new PageInfo();
		
		pinfo.setTotalCount(totalCount);
		pinfo.setListCount(listCount);
		pinfo.setTotalPage(totalPage);
		pinfo.setCurPage(myCurPage);
		pinfo.setPageCount(pageCount);
		pinfo.setStartPage(startPage);
		pinfo.setEndPage(endPage);
		
		request.setAttribute("page", pinfo);
		nPage = pinfo.getCurPage();
		session.setAttribute("cpage", nPage);
		
		int start = (nPage-1)* listCount + 1;
		int end = (nPage-1)*listCount + listCount;
		
		if( option == null ) {
			challenge = dao.All_Ongoing_challenge(end, start);
		}else if(option.equals("옵션")) {
			challenge = dao.All_Ongoing_challenge(end, start);
		}else {
			if(option.equals("카테고리")) {
				challenge = dao.FindOngoing_challenge_title(end, start, text);
			}else if(option.equals("제목")) {
				challenge = dao.FindOngoing_challenge_name(end, start, text);
			}else if(option.equals("날짜")) {
				challenge = dao.FindOngoing_challenge_name(end, start, text);
				//수정
			}else if(option.equals("구분")) {
				option = "challenge_public";
				if(text.equals("공개"))
					text = "0";
				else
					text = "1";
				
				challenge = dao.FindOngoing_challenge_public(end, start, text);
			}
		}
		
		
		model.addAttribute("challenge", challenge);
		
		return "/challenge/ongoing_challenge2";
	}
	
	
	
	@RequestMapping("/reward_challenge")
	public String reward_challenge(HttpServletRequest request, Model model){
			
		HttpSession session = null;
		session = request.getSession();
		
		int nPage = 1;
		
		nPage = Integer.parseInt(request.getParameter("page"));
		String option = request.getParameter("findoption");
		String text = request.getParameter("findtext");
				
		
		int listCount = 7;	//한페이지당 보여줄 게시물 갯수
		int pageCount = 5; //하단에 보여줄 리스트 갯수
		
		int totalCount = dao.reward_challenge_count();
				
		int totalPage = totalCount / listCount;
		if(totalCount % listCount > 0)
			totalPage++;
		
		int myCurPage = nPage;
		if(myCurPage > totalPage)
			myCurPage = totalPage;
		if(myCurPage < 1)
			myCurPage = 1;
		
		int startPage = ((myCurPage - 1) / pageCount) * pageCount + 1;
		
		int endPage = startPage + pageCount - 1;
		if(endPage > totalPage)
			endPage = totalPage;

		PageInfo pinfo = new PageInfo();
		
		pinfo.setTotalCount(totalCount);
		pinfo.setListCount(listCount);
		pinfo.setTotalPage(totalPage);
		pinfo.setCurPage(myCurPage);
		pinfo.setPageCount(pageCount);
		pinfo.setStartPage(startPage);
		pinfo.setEndPage(endPage);
		
		request.setAttribute("page", pinfo);
		nPage = pinfo.getCurPage();
		session.setAttribute("cpage", nPage);
		
		int start = (nPage-1)* listCount + 1;
		int end = (nPage-1)*listCount + listCount;
		
		if( option == null ) {
			challenge = dao.reward_challenge(end, start);
		}else if(option.equals("옵션")) {
			challenge = dao.reward_challenge(end, start);
		}else {
//			if(option.equals("카테고리")) {
//				challenge = dao.FindOngoing_challenge_title(end, start, text);
//			}else if(option.equals("제목")) {
//				challenge = dao.FindOngoing_challenge_name(end, start, text);
//			}else if(option.equals("날짜")) {
//				challenge = dao.FindOngoing_challenge_name(end, start, text);
//				//수정
//			}else if(option.equals("구분")) {
//				option = "challenge_public";
//				if(text.equals("공개"))
//					text = "0";
//				else
//					text = "1";
//				
//				challenge = dao.FindOngoing_challenge_public(end, start, text);
//			}
		}
		
		
		model.addAttribute("challenge", challenge);
		
		return "/challenge/reward_challenge";
	}
	
	
	
	
	@RequestMapping("/ongoing_challenge_content")
	public String ongoing_challenge_content(HttpServletRequest request, Model model){
		
		int num = Integer.parseInt(request.getParameter("num"));
		
		ChallengeDto dto = dao.ongoing_challenge_content(num);
		model.addAttribute("challenge", dto);
		
		
		return "/challenge/ongoing_challenge_content";
	}
	
	
	@RequestMapping("/read_participant")
	public void read_participant(HttpServletRequest request, HttpServletResponse response, Model model)
			throws ServletException, IOException{
		
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		StringBuffer result = new StringBuffer("");
		
		int num = Integer.parseInt(request.getParameter("num"));
		
		System.out.println(num);
		member = dao.read_participant(num);

		result.append("{\"result\":[");
		
		for(int i = 0 ; i < member.size() ; i++) {
//			result.append("[{\"value\": \"" + member.get(i).getMember_nickname() + "\"},");
//			result.append("{\"value\": \"" + member.get(i).getMember_profile_image() + "\"}]");
			result.append("[{\"value\": \"" + member.get(i).getMember_nickname() + "\"},");
			result.append("{\"value\": \"" + member.get(i).getMember_profile_image() + "\"}]");
			if(i != member.size() - 1) 
				result.append(",");
		}
//		result.append("], \"last\":\"" + "\"}");
//		result.append("" + "\"}");
		result.append("]}");
		
		response.getWriter().write(result.toString());
		
		System.out.println(result);
		System.out.println("결과 확인");
	}
	

	@RequestMapping("/read_imagelist")
	public void read_imagelist(HttpServletRequest request, HttpServletResponse response, Model model)
			throws ServletException, IOException{
		
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		StringBuffer result = new StringBuffer("");
		
		int num = Integer.parseInt(request.getParameter("num"));
		
		System.out.println(num);
//		member = dao.read_participant(num);
		
		feed = dao.get_feed_list(num);
				
		result.append("{\"result\":[");
		
		for(int i = 0 ; i < feed.size() ; i++) {
			result.append("[{\"value\": \"" + feed.get(i).getChallenge_title() + "\"},");
			result.append("{\"value\": \"" + feed.get(i).getFeed_comment() + "\"},");
			result.append("{\"value\": \"" + feed.get(i).getFeed_info() +".jpg"+ "\"},");
			result.append("{\"value\": \"" + feed.get(i).getFeed_update_time() + "\"},");
			result.append("{\"value\": \"" + dao.get_nickname(feed.get(i).getMember_id()) + "\"}]");
			
			if(i != feed.size() - 1) 
				result.append(",");
		}
				
//		for(int i = 0 ; i < member.size() ; i++) {
//			result.append("[{\"value\": \"" + "치민" + "\"},");
//			result.append("{\"value\": \"" + "ㅇㅇㅇㅇㅇㅇㅇㅇ" + "\"},");
//			result.append("{\"value\": \"" + "/img/aaa.jpg" + "\"},");
//			result.append("{\"value\": \"" + "12:00" + "\"}]");
//			if(i != member.size() - 1) 
//				result.append(",");
//				result.append("[{\"value\": \"" + "송" + "\"},");
//				result.append("{\"value\": \"" + "ㅅㅅㅅㅅㅅㅅㅅㅅ" + "\"},");
//				result.append("{\"value\": \"" + "/img/aaa.jpg" + "\"},");
//				result.append("{\"value\": \"" + "15:00" + "\"}]");
//		}		
				
				
				
		result.append("]}");
		
		response.getWriter().write(result.toString());

	}
	
	
	
	@RequestMapping(value = "/fcmtest")
	public void fcmtest(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		
		String id = request.getParameter("id");
//		String title = request.getParameter("title");
		String content = request.getParameter("content");
		
	    String tokenId = dao.get_token(id);
	    String title="FCM 테스트";
//	    String content="호예... 된당  잔다..";
	  
	    FcmUtil FcmUtil = new FcmUtil();
	    FcmUtil.send_FCM(tokenId, title, content, "");
	  
//	    return "test";
	}
	
	
	@RequestMapping(value = "/fcmall")
	public void fcmall(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		
//		String title = request.getParameter("title");
		String content = request.getParameter("content");		
		
//	    String tokenId = dao.get_token(id);
	    String title="FCM 테스트";
//	    String content="호예... 된당  잔다..";
	  
	    FcmUtil FcmUtil = new FcmUtil();
	    FcmUtil.send_FCM("", title, content, "all");
	  
//	    return "test";
	}
	
	@RequestMapping(value = "/fcmChallenge")
	public void fcmChallenge(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		
//		String title = request.getParameter("title");
		String content = request.getParameter("content");
		String no = request.getParameter("no");
		
	    String title="FCM 테스트";
	  
	    FcmUtil FcmUtil = new FcmUtil();
	    FcmUtil.send_FCM("", title, content, no);
	  
//	    return "test";
	}
	
	
	@RequestMapping("/token_save")
	public void token_save(HttpServletRequest request, HttpServletResponse response, Model model,
			@RequestParam("id") String id, @RequestParam("token") String token) throws Exception {
	
		System.out.println("들어옴");
		dao.save_token(id, token);
	}
	
	
	
	   @RequestMapping("/reward_challenge_content")
	   public String reward_challenge_content(HttpServletRequest request, Model model){
	      
	      int num = Integer.parseInt(request.getParameter("num"));
	      ChallengeDto dto = dao.ongoing_challenge_content(num);
	      
	      Record = dao.GetRecord(num);
	      

	      int total = Record.size() * Record.get(0).getChallenge_fee();
	      int low = 0;
	      int count = 0;
	      
	      for(int i = 0 ; i < Record.size() ; i++) {
	         
	         System.out.println(Record.get(i).getCertificate_count() + " " + Record.get(i).getAll_count());
	         double per = (double)Record.get(i).getCertificate_count() / (double)Record.get(i).getAll_count() ;
	         double perint = ((Math.round(per*1000)/1000.0));
	         
	         System.out.println("퍼센트 : " + perint);
	         
	         int money = (int) Math.round(perint*Record.get(i).getChallenge_fee());
	         low += money;
	      
	         if(perint==1)
	         {
	            count++;
	            Record.get(i).setReward(1);
	         }
	         
	         Record.get(i).setChallenge_fee(money);
	      }
	      
	      int result = total - low;
	      System.out.println("먹은돈 : " + result);
	      
	      
	      System.out.println("달성자 수 : "+count);
	      
	      int reward = (int) ((result*0.9) / count);
	      System.out.println("1인당 상금 : "+reward);
	      System.out.println("때먹은 수수료 : " + (result-reward*count));
	      
	      for(int i = 0 ; i < Record.size() ; i++) {
	         if(Record.get(i).getReward()==1) {
	            Record.get(i).setReward(reward);
	         }
	      }
	      
	      model.addAttribute("total", total);
	      model.addAttribute("result", result);
	      model.addAttribute("totalreward", reward*count);
	      model.addAttribute("ss", (result-reward*count));
	      model.addAttribute("count", count);
	      
	      model.addAttribute("challenge", dto);
	      model.addAttribute("Record", Record);
	      return "/challenge/reward_challenge_content";
	   }
	   
	   
	   @RequestMapping("/submit_reward")
	   public void submit_reward(HttpServletResponse response, HttpServletRequest request, Model model) throws Exception{
			response.setContentType("text/html; charset=UTF-8");
			StringBuffer result = new StringBuffer("");
			
	      int num = Integer.parseInt(request.getParameter("num"));
	      ChallengeDto dto = dao.ongoing_challenge_content(num);
	      	
	      for(int i = 0 ; i < Record.size() ; i++) {
//	    	 System.out.println(Record.get(i).getChallenge_fee());
//	    	 System.out.println(Record.get(i).getReward());
	    	 dao.setRecord(Record.get(i).getMember_num(), num, Record.get(i).getReward());
	    	 dao.setMoney(Record.get(i).getMember_id(), Record.get(i).getChallenge_fee());
	    	 dao.setReward(Record.get(i).getMember_id(), Record.get(i).getReward());
	    	 
	    	 mdto = dao.getMemberInfo(Record.get(i).getMember_id());
	    	 dao.setExp(dto.getChallenge_exp()+mdto.getMember_exp(), Record.get(i).getMember_id());  
	    	 
		    	 //6개 넣어야함
		    	 if(dto.getChallenge_category().equals("취미")) {
		    		 dao.hobby(mdto.getMember_hobby()+1, Record.get(i).getMember_id());
		    	 }else if(dto.getChallenge_category().equals("역량")) {
		    		 dao.capability(mdto.getMember_capability()+1, Record.get(i).getMember_id());
		    	 }else if(dto.getChallenge_category().equals("관계")) {
		    		 dao.relationship(mdto.getMember_relationship()+1,  Record.get(i).getMember_id());
		    	 }else if(dto.getChallenge_category().equals("자산")) {
		    		 dao.assets(mdto.getMember_assets()+1,  Record.get(i).getMember_id());
		    	 }else if(dto.getChallenge_category().equals("생활")) {
		    		 dao.life(mdto.getMember_life()+1,  Record.get(i).getMember_id());
		    	 }else if(dto.getChallenge_category().equals("건강")) {
		    		 dao.health(mdto.getMember_health()+1,  Record.get(i).getMember_id());
		    	 }	    	 	    	 
	      }

		   //챌린지 완료로 바꿔...
	      dao.approve_public_challenge(num, 3);
	      //삭제
	      dao.certificate_delete(num);
	      
	      
	      result.append(1);
	      response.getWriter().write(result.toString());
	   }
	   
	   
	   @RequestMapping("/end_challenge")
		public String end_challenge(HttpServletRequest request, Model model){
			
			HttpSession session = null;
			session = request.getSession();
			
			int nPage = 1;
			
			nPage = Integer.parseInt(request.getParameter("page"));
			String option = request.getParameter("findoption");
			String text = request.getParameter("findtext");

			
			int listCount = 7;	//한페이지당 보여줄 게시물 갯수
			int pageCount = 5; //하단에 보여줄 리스트 갯수
			
			int totalCount = dao.public_challenge_count("3","0","1");
			 
			int totalPage = totalCount / listCount;
			if(totalCount % listCount > 0)
				totalPage++;
			
			int myCurPage = nPage;
			if(myCurPage > totalPage)
				myCurPage = totalPage;
			if(myCurPage < 1)
				myCurPage = 1;
			
			int startPage = ((myCurPage - 1) / pageCount) * pageCount + 1;
			
			int endPage = startPage + pageCount - 1;
			if(endPage > totalPage)
				endPage = totalPage;

			PageInfo pinfo = new PageInfo();
			
			pinfo.setTotalCount(totalCount);
			pinfo.setListCount(listCount);
			pinfo.setTotalPage(totalPage);
			pinfo.setCurPage(myCurPage);
			pinfo.setPageCount(pageCount);
			pinfo.setStartPage(startPage);
			pinfo.setEndPage(endPage);
			
			request.setAttribute("page", pinfo);
			nPage = pinfo.getCurPage();
			session.setAttribute("cpage", nPage);
			
			int start = (nPage-1)* listCount + 1;
			int end = (nPage-1)*listCount + listCount;

			
			if( option == null ) {
				challenge = dao.end_challenge(end, start);
			}else if(option.equals("옵션")) {
				challenge = dao.end_challenge(end, start);
			}else {
				if(option.equals("카테고리")) {
//					challenge = dao.FindRegisterWait_challenge_title(end, start, text);
				}else if(option.equals("제목")) {
//					challenge = dao.FindRegisterWait_challenge_name(end, start, text);
				}else if(option.equals("날짜")) {
//					challenge = dao.FindRegisterWait_challenge_name(end, start, text);
					//수정
				}else if(option.equals("구분")) {
					option = "challenge_public";
					if(text.equals("공개"))
						text = "0";
					else
						text = "1";
					
//					challenge = dao.FindRegisterWait_challenge_public(end, start, text);
				}
			}
		

			//평균 달성률 계산
			for(int i=0 ; i < challenge.size() ; i++) {
				Record = dao.GetRecord(challenge.get(i).getChallenge_num());
							
			    int count = 0;
			    double sum=0;
			    
			      for(int j = 0 ; j < Record.size() ; j++) {
			         
			    	  System.out.println("Dasdsadsad3");
			         System.out.println(Record.get(j).getCertificate_count() + " " + Record.get(j).getAll_count());
			         double per = (double)Record.get(j).getCertificate_count() / (double)Record.get(j).getAll_count() ;
			         double perint = ((Math.round(per*1000)/1000.0));
			         
			         sum += perint;
			         
			         if(perint==1)
			         {
			            count++;
			         }
			         
			      }

			      String aa = String.valueOf(((Math.round((sum/Record.size())*1000)/1000.0)));
			      challenge.get(i).setAvg(aa);
			}
			
			model.addAttribute("challenge", challenge);

			return "/challenge/end_challenge";
		}
	   
	   
	
		@RequestMapping("/state_challenge")
		public String state_challenge(HttpServletRequest request, Model model){
				
			challenge = dao.All_challenge();
			
			int a = 0; int b = 0; int c = 0;
			int d = 0; int e = 0; int f = 0;
			int g = 0; int h = 0; int i = 0;
			int j = 0; int k = 0; int l = 0;
			
			for(int y = 0 ; y < challenge.size() ; y++)
			{
				if(challenge.get(y).getChallenge_category().equals("역량")) {
					a++;
				}else if(challenge.get(y).getChallenge_category().equals("건강")) {
					b++;
				}else if(challenge.get(y).getChallenge_category().equals("관계")) {
					c++;
				}else if(challenge.get(y).getChallenge_category().equals("자산")) {
					d++;
				}else if(challenge.get(y).getChallenge_category().equals("생활")) {
					e++;
				}else{
					f++;
				}
				
				if(challenge.get(y).getChallenge_state().equals("2")) {
					if(challenge.get(y).getChallenge_category().equals("역량")) {
						g++;
					}else if(challenge.get(y).getChallenge_category().equals("건강")) {
						h++;
					}else if(challenge.get(y).getChallenge_category().equals("관계")) {
						i++;
					}else if(challenge.get(y).getChallenge_category().equals("자산")) {
						j++;
					}else if(challenge.get(y).getChallenge_category().equals("생활")) {
						k++;
					}else{
						l++;
					}
				}
			}
			
			model.addAttribute("a", a);
			model.addAttribute("b", b);
			model.addAttribute("c", c);
			model.addAttribute("d", d);
			model.addAttribute("e", e);
			model.addAttribute("f", f);
			
			model.addAttribute("g", g);
			model.addAttribute("h", h);
			model.addAttribute("i", i);
			model.addAttribute("j", j);
			model.addAttribute("k", k);
			model.addAttribute("l", l);
			
			
			
			AllRecord = dao.GetAllRecord();
			
			//평균 달성률 계산				
			    int count = 0;
			    int totalfee = 0;
			    int totalcount = 0;
			    double sum=0;
			    int totalchallenge = 0;
			    
			    
			    totalchallenge = challenge.size();
			    //총 챌린지 개설 수
			    
			    totalcount = AllRecord.size();
			    //총 챌린지 참여 횟수
			    
			      for(int y = 0 ; y < AllRecord.size() ; y++) {

			    	 
			    	 totalfee += AllRecord.get(i).getChallenge_fee();
			    	 //총 참가비
			    	 
			         System.out.println(AllRecord.get(y).getCertificate_count() + " " + AllRecord.get(y).getAll_count());
			         double per = (double)AllRecord.get(y).getCertificate_count() / (double)AllRecord.get(y).getAll_count() ;
			         double perint = ((Math.round(per*1000)/1000.0));
			         
			         sum += perint;
			         
			         if(perint==1)
			         {
			            count++;
			            //챌린지 100% 성공 한사람 
			         }
			         
			      }

			      //aa는 전체 사람들 전체 퍼센트 더함
			      String aa = String.valueOf(((Math.round((sum/AllRecord.size())*1000)/1000.0)));
			      
			      
			    model.addAttribute("count", count);
				model.addAttribute("totalfee", totalfee);
				model.addAttribute("totalcount", totalcount);
				model.addAttribute("sum", aa);
				model.addAttribute("totalchallenge", totalchallenge);
			
			return "/challenge/state_challenge";
		}
		
		
		
		
		   @RequestMapping("/end_challenge_content")
		   public String end_challenge_content(HttpServletRequest request, Model model){
		      
		      int num = Integer.parseInt(request.getParameter("num"));
		      ChallengeDto dto = dao.end_challenge_content(num);
		      
		      Record = dao.GetRecord(num);
		      

		      int total = Record.size() * Record.get(0).getChallenge_fee();
		      int low = 0;
		      int count = 0;
		      
		      for(int i = 0 ; i < Record.size() ; i++) {
		         
		         System.out.println(Record.get(i).getCertificate_count() + " " + Record.get(i).getAll_count());
		         double per = (double)Record.get(i).getCertificate_count() / (double)Record.get(i).getAll_count() ;
		         double perint = ((Math.round(per*1000)/1000.0));
		         
		         System.out.println("퍼센트 : " + perint);
		         
		         int money = (int) Math.round(perint*Record.get(i).getChallenge_fee());
		         low += money;
		      
		         if(perint==1)
		         {
		            count++;
		            Record.get(i).setReward(1);
		         }
		         
		         Record.get(i).setChallenge_fee(money);
		      }
		      
		      int result = total - low;
		      System.out.println("먹은돈 : " + result);
		      
		      
		      System.out.println("달성자 수 : "+count);
		      
		      int reward = (int) ((result*0.9) / count);
		      System.out.println("1인당 상금 : "+reward);
		      System.out.println("때먹은 수수료 : " + (result-reward*count));
		      
		      for(int i = 0 ; i < Record.size() ; i++) {
		         if(Record.get(i).getReward()==1) {
		            Record.get(i).setReward(reward);
		         }
		      }
		      
		      model.addAttribute("total", total);
		      model.addAttribute("result", result);
		      model.addAttribute("totalreward", reward*count);
		      model.addAttribute("ss", (result-reward*count));
		      model.addAttribute("count", count);
		      
		      model.addAttribute("challenge", dto);
		      model.addAttribute("Record", Record);
		      return "/challenge/end_challenge_content";
		   }
		
}
