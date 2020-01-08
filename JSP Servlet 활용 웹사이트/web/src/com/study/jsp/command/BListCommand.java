 package com.study.jsp.command;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.study.dto.BDto;
import com.study.jsp.BPageInfo;
import com.study.jsp.dao.copy.BDao;

import jdk.nashorn.internal.runtime.regexp.joni.Regex;

public class BListCommand implements BCommand{

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) 
	{
		int nPage = 1;
		HttpSession session = null;
		session = request.getSession();
		String option=(String) session.getAttribute("findoption");
		String text=(String) session.getAttribute("findtext");
		
//		String option=null;
//		String text=null;
//
//		option = request.getParameter("findoption");
//		text = request.getParameter("findtext");
		
		
		try {
			String sPage = request.getParameter("page");
			nPage = Integer.parseInt(sPage);
		}catch (Exception e) {
		}
		
		BDao dao = BDao.getInstance();
//		BPageInfo pinfo = dao.articlePage(nPage);
		BPageInfo pinfo;
		if( (option != null ) && ( text != null) && !(option.equals("검색옵션")) )
		{
			System.out.println("확인 : " + option + "  " );
			pinfo = dao.articlePage2(nPage, option, text);
		}
		else
		{
			pinfo = dao.articlePage(nPage);	
		}
//		BPageInfo pinfo = dao.articlePage2(nPage, option, text);
		request.setAttribute("page", pinfo);
		
		nPage = pinfo.getCurPage();
		//저장된 현재 페이지 세션에 올려놓기
		
		
		session.setAttribute("cpage", nPage);	
		
//		if(option!=null && text!=null)
//		{
//			session.setAttribute("findoption", option);
//			session.setAttribute("findtext", text);
//		}
			
//		System.out.println(session.getAttribute("findoption"));
//		System.out.println(session.getAttribute("findtext"));
		
//		ArrayList<BDto> dtos = dao.list(nPage);
		ArrayList<BDto> dtos;
		if( (option != null ) && ( text != null) && !(option.equals("검색옵션")) )
		{
			dtos = dao.findlist(nPage, option, text);
		}
		else
		{
			dtos = dao.list(nPage);
		}
//		ArrayList<BDto> dtos = dao.findlist(nPage, option, text);

		request.setAttribute("list", dtos);
		
	}

}
