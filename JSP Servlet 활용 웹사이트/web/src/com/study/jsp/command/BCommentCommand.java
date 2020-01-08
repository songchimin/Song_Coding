package com.study.jsp.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.study.jsp.dao.copy.BDao;

public class BCommentCommand implements BCommand{

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = null;
		session = request.getSession();
	
		String bid = request.getParameter("bid");

		String id = (String)session.getAttribute("id");
		String Name = (String)session.getAttribute("name");
		String content = request.getParameter("content");

	
		System.out.println(bid);
		System.out.println(id);
		System.out.println(Name);
		System.out.println(content);

		BDao dao = new BDao();
		dao.writeComment(bid, id, Name, content);
		dao.downHit(bid);
	}

}
