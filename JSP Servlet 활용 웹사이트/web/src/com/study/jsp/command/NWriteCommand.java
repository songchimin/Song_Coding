package com.study.jsp.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.study.jsp.dao.copy.NDao;


public class NWriteCommand implements NCommand{

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = null;
		session = request.getSession();
		
		String id = (String)session.getAttribute("id");
		String nName = (String)session.getAttribute("name");
		String nTitle = request.getParameter("nTitle");
		String nContent = request.getParameter("nContent");
		
		System.out.println(nName);
		System.out.println(nTitle);
		System.out.println(nContent);
		
		NDao dao = new NDao();
		dao.write(id, nName, nTitle, nContent);
		
	}
}
