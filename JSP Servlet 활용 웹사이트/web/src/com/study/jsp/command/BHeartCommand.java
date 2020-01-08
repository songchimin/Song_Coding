package com.study.jsp.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.study.jsp.dao.copy.BDao;

public class BHeartCommand implements BCommand{

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		
		HttpSession session = null;
		session = request.getSession();
		
		String bId = request.getParameter("bId");
		String id = (String)session.getAttribute("id");
		
		BDao dao = new BDao();
		dao.heart(bId, id);
		
		
	}

}
