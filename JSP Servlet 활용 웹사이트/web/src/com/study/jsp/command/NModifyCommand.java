package com.study.jsp.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.study.jsp.dao.copy.NDao;

public class NModifyCommand implements NCommand{

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		String nId = request.getParameter("nId");
		String nName = request.getParameter("nName");
		String nTitle = request.getParameter("nTitle");
		String nContent = request.getParameter("nContent");
		
		System.out.println(nContent);
		
		NDao dao = new NDao();
		dao.modify(nId, nName, nTitle, nContent);
	}
}
