package com.study.jsp.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.study.jsp.dao.copy.BDao;

public class BCommentDeleteCommand implements BCommand {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = null;
		session = request.getSession();
	
		String bid = request.getParameter("bid");
		String cNo = request.getParameter("cNo");
			
		System.out.println(bid +" "+cNo);
		
		BDao dao = new BDao();
		dao.deleteComment(cNo);
		dao.downHit(bid);
		
	}

}
