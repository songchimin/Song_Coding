package com.study.jsp.command;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.study.dto.BDto;
import com.study.dto.PDto;
import com.study.jsp.BPageInfo;
import com.study.jsp.dao.copy.BDao;
import com.study.jsp.dao.copy.PDao;

public class PListCommand implements PCommand {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		int nPage = 1;
		HttpSession session = null;
		session = request.getSession();
//		String option=(String) session.getAttribute("findoption");
//		String text=(String) session.getAttribute("findtext");

		
		try {
			String sPage = request.getParameter("page");
			nPage = Integer.parseInt(sPage);
		}catch (Exception e) {
		}
		
		PDao dao = PDao.getInstance();

		BPageInfo pinfo;
		pinfo = dao.articlePage(nPage);	

		request.setAttribute("page", pinfo);
		
		nPage = pinfo.getCurPage();		
		
		session.setAttribute("cpage", nPage);	
		
		ArrayList<PDto> dtos;
		dtos = dao.list(nPage);


		request.setAttribute("plist", dtos);
		
	}

}
