package com.study.jsp.command;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.study.dto.BDto;
import com.study.jsp.BPageInfo;
import com.study.jsp.dao.copy.BDao;

public class BFindCommand implements BCommand{

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		
		int nPage = 1;
		String option=null;
		String text=null;

		option = request.getParameter("findoption");
		text = request.getParameter("findtext");
		
		try {
			String sPage = request.getParameter("page");
			nPage = Integer.parseInt(sPage);
			
		}catch (Exception e) {
		}
		
		BDao dao = BDao.getInstance();
		BPageInfo pinfo = dao.articlePage2(nPage, option, text);
		request.setAttribute("page", pinfo);
		
		nPage = pinfo.getCurPage();
		//저장된 현재 페이지 세션에 올려놓기
		HttpSession session = null;
		session = request.getSession();
		session.setAttribute("cpage", nPage);
		
		ArrayList<BDto> dtos = dao.findlist(nPage, option, text);
		request.setAttribute("list", dtos);

		
		
//		BDao dao = new BDao();
//		BDto dto = dao.findcontent(bId, option, text);
//		
//		request.setAttribute("content_view", dto);
		
	}

}

