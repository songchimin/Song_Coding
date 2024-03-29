package com.study.springboot;


import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.study.springboot.dao.QnADao;
import com.study.springboot.dto.ComplaintDto;
import com.study.springboot.dto.PageInfo;
import com.study.springboot.dto.QnADto;

@Controller
public class QnAController {

	@Autowired
	QnADao dao;
	
	List<QnADto> QnAList;
	List<ComplaintDto> Vote_ComplaintDto_List;

	
	@RequestMapping("/waitQnA")
	public String waitQnA(HttpServletRequest request, Model model) {
		
		HttpSession session = null;
		session = request.getSession();
		
		int nPage = 1;
		
		nPage = Integer.parseInt(request.getParameter("page"));
		String option = request.getParameter("findoption");
		String text = request.getParameter("findtext");
				
		
		int listCount = 7;	//한페이지당 보여줄 게시물 갯수
		int pageCount = 5; //하단에 보여줄 리스트 갯수
		
		int totalCount = dao.WaitQnACount();
				
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
		
//		if( (option != null ) && ( text != null) && !(option.equals("검색옵션")) )
//		{
//			if(option.equals("아이디"))
//				challenge = dao.FindMemberId(end, start, text);
//			else
//				challenge = dao.FindMemberName(end, start, text);
//		}
//		else
//		{
			QnAList = dao.All_WaitQnA(end, start);
//		}
		
	
		model.addAttribute("QnAList", QnAList);
		System.out.println("ㅁㄴㅇㅁㄴㅇㄴㅁㅇㅁㄴㅇㅁ");
		
		return "/QnA/waitQnA";
	}
	
	
	@RequestMapping("/QnAsubmit")	//QnA 답변
	public void QnAsubmit(HttpServletRequest request, HttpServletResponse response, Model model)
			throws ServletException, IOException{
		
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		int num = Integer.parseInt(request.getParameter("num"));
		String manager_id = request.getParameter("id");
		String content = request.getParameter("content");
		
		StringBuffer result = new StringBuffer("");

		System.out.println(content + "확인");
		
		if(content.length() <= 0)
		{
			result.append("0");
		}
		else
		{	
			result.append("1");
			dao.QnAsubmit(num, manager_id, content);
		}
		
		response.getWriter().write(result.toString());	
	}
	
	
	
	@RequestMapping("/EndQnA")
	public String EndQnA(HttpServletRequest request, Model model) {
		
		HttpSession session = null;
		session = request.getSession();
		
		int nPage = 1;
		
		nPage = Integer.parseInt(request.getParameter("page"));
		String option = request.getParameter("findoption");
		String text = request.getParameter("findtext");
				
		
		int listCount = 7;	//한페이지당 보여줄 게시물 갯수
		int pageCount = 5; //하단에 보여줄 리스트 갯수
		
		int totalCount = dao.EndQnACount();
				
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
		
//		if( (option != null ) && ( text != null) && !(option.equals("검색옵션")) )
//		{
//			if(option.equals("아이디"))
//				challenge = dao.FindMemberId(end, start, text);
//			else
//				challenge = dao.FindMemberName(end, start, text);
//		}
//		else
//		{
			QnAList = dao.EndQnA(end, start);
//		}
		
	
		model.addAttribute("QnAList", QnAList);
		
		
		return "/QnA/EndQnA";
	}
	
	
	
//	@RequestMapping("/addComment.do")
//    public String ajax_addComment(HttpServletRequest request){
//        
//        HttpSession session = request.getSession();
//        int question_num = Integer.parseInt(request.getParameter("num"));
//        String question_answer = request.getParameter("answer");
//        
//        dao.AddComment(question_num, (String)session.getAttribute("id"), question_answer);
//
//        return "success";
//    }
    

//    @RequestMapping("/commentList.do")
//    public ResponseEntity ajax_commentList(HttpServletRequest request){
//        
//        HttpHeaders responseHeaders = new HttpHeaders();
//        ArrayList<HashMap> hmlist = new ArrayList<HashMap>();
//        
//        int question_num = Integer.parseInt(request.getParameter("num"));
//        
//        // 해당 게시물 댓글
//        ArrayList<QnADto> comment = dao.SelectQnAComment(question_num);
//        
//        if(comment.size() > 0){
//            for(int i=0; i<comment.size(); i++){
//                HashMap hm = new HashMap();
//                
//                hm.put("c_code", comment.get(i).getQuestion_num());
//                hm.put("writer", comment.get(i).getMember_id());
//                hm.put("comment", comment.get(i).getQuestion_Content());
//                hm.put("manager", comment.get(i).getManager_id());
//                hm.put("answer", comment.get(i).getQuestion_answer());
//                
//                hmlist.add(hm);
//            }
//            
//        }
//        
//        JSONArray json = new JSONArray();
//        json.add(hmlist);
//        
//        return new ResponseEntity(json.toString(), responseHeaders, HttpStatus.CREATED);
//    }
	

		
	@RequestMapping("/vote_comment_report")
	public String vote_comment_report(HttpServletRequest request, Model model) {
		
		HttpSession session = null;
		session = request.getSession();
		
		int nPage = 1;
		
		nPage = Integer.parseInt(request.getParameter("page"));
		String option = request.getParameter("findoption");
		String text = request.getParameter("findtext");
				
		
		int listCount = 7;	//한페이지당 보여줄 게시물 갯수
		int pageCount = 5; //하단에 보여줄 리스트 갯수
		
		int totalCount = dao.Wait_vote_comment_reportCount();
				
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
		
//		if( (option != null ) && ( text != null) && !(option.equals("검색옵션")) )
//		{
//			if(option.equals("아이디"))
//				challenge = dao.FindMemberId(end, start, text);
//			else
//				challenge = dao.FindMemberName(end, start, text);
//		}
//		else
//		{
			Vote_ComplaintDto_List = dao.Wait_vote_comment_report(end, start);
//		}
			
			
			//댓글 내용 불러서 저장
			for(int i=0 ; i < Vote_ComplaintDto_List.size() ; i++) {
				Vote_ComplaintDto_List.get(i).setComment_content(dao.Get_Comment_Content(Vote_ComplaintDto_List.get(i).getComment_num()));
			}
			
			
		model.addAttribute("comment_report", Vote_ComplaintDto_List);
		
		return "/QnA/vote_comment_report";
	}
	
	
	@RequestMapping("/vote_comment_submit")	//QnA 답변
	public void vote_comment_submit(HttpServletRequest request, HttpServletResponse response, Model model)
			throws ServletException, IOException{
		
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		int num = Integer.parseInt(request.getParameter("num"));
		int num2 = Integer.parseInt(request.getParameter("num2"));
		int check = Integer.parseInt(request.getParameter("check"));
		String content = request.getParameter("content");
//		String manager_id = request.getParameter("id");
		
		System.out.println(num + " " +num2 + " " +check + " " +content);
		
		StringBuffer result = new StringBuffer("");

		if(content.length() <= 0)
		{
			result.append("0");
		}
		else
		{	
			result.append("1");
			//댓글지우고, 컴플레인 상태 1, 답변 내용,날짜 등록   complaint_reply  complaint_reply_date
			
			if(check == 0)
			{
				dao.vote_comment_report_submit(num, content);
				dao.delete_comment(num2);
			}
			else {
				dao.vote_comment_report_submit(num, content);
			}
			
		}
		
		response.getWriter().write(result.toString());	
	}
	
	
	
	
}
