package com.study.jsp.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.study.jsp.dao.copy.NDao;


public class NDeleteCommand implements NCommand{

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		String nId = request.getParameter("nId");
		NDao dao = new NDao();
		dao.delete(nId);
		
	}
	
}
