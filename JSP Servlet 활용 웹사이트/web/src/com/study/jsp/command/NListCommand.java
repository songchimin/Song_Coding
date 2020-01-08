 package com.study.jsp.command;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.study.dto.BDto;
import com.study.dto.NDto;
import com.study.dto.NPageInfo;
import com.study.jsp.BPageInfo;
import com.study.jsp.dao.copy.BDao;
import com.study.jsp.dao.copy.NDao;

import jdk.nashorn.internal.runtime.regexp.joni.Regex;

public class NListCommand implements NCommand{

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) 
	{
		int nPage = 1;
		HttpSession session = null;
		session = request.getSession();
		String option=(String) session.getAttribute("findoption");
		String text=(String) session.getAttribute("findtext");
		
		
		try {
			String sPage = request.getParameter("page");
			nPage = Integer.parseInt(sPage);
		}catch (Exception e) {
		}
		
		NDao dao = NDao.getInstance();

		NPageInfo pinfo;
		if( (option != null ) && ( text != null) && !(option.equals("검색옵션")) )
		{
			System.out.println("확인 : " + option + "  " );
			pinfo = dao.articlePage2(nPage, option, text);
		}
		else
		{
			pinfo = dao.articlePage(nPage);	
		}

		request.setAttribute("page", pinfo);
		
		nPage = pinfo.getCurPage();
		//저장된 현재 페이지 세션에 올려놓기
		
		
		session.setAttribute("cpage", nPage);	

		ArrayList<NDto> dtos;
		if( (option != null ) && ( text != null) && !(option.equals("검색옵션")) )
		{
			dtos = dao.findlist(nPage, option, text);
		}
		else
		{
			dtos = dao.list(nPage);
		}


		request.setAttribute("nlist", dtos);
		
	}

}
