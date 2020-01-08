package com.study.jsp.command;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.study.dto.NDto;
import com.study.dto.NPageInfo;
import com.study.jsp.BPageInfo;
import com.study.jsp.MemberDAO;
import com.study.jsp.MemberDTO;
import com.study.jsp.dao.copy.BDao;

public class AmemberCommand implements BCommand{

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
	    HttpSession session = null;
	    session = request.getSession();
		String option=(String) session.getAttribute("findoption");
		String text=(String) session.getAttribute("findtext");
		
		
		int nPage = 1;
		      try {
		         String sPage = request.getParameter("page");
		         System.out.println(sPage);
		         nPage = Integer.parseInt(sPage);
		      } catch(Exception e) {
		         
		      }
		      
		      BDao dao = BDao.getInstance();
		      
		      BPageInfo pinfo;
		      if( (option != null ) && ( text != null) && !(option.equals("검색옵션")) )
				{
					System.out.println("확인 : " + option + "  " );
					pinfo = dao.articlePage3(nPage);
				}
				else
				{
					pinfo = dao.articlePage3(nPage);
				}
		      
		      request.setAttribute("page", pinfo);
		      
		      nPage = pinfo.getCurPage();

//		      HttpSession session = null;
//		      session = request.getSession();
		      session.setAttribute("cpage", nPage);
			
		
		     

			MemberDAO mdao = MemberDAO.getInstance();
			
			
			ArrayList<MemberDTO> dtos;
			if( (option != null ) && ( text != null) && !(option.equals("검색옵션")) )
			{
				dtos = mdao.findMember(nPage, option, text);
			}
			else
			{
				dtos = mdao.getAllMember(nPage);
			}
			
			request.setAttribute("member", dtos);
		
	}

}
