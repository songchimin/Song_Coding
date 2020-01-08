package com.study.jsp.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.study.dto.PDto;
import com.study.jsp.dao.copy.PDao;

public class PContentCommand implements PCommand {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
	    String id = (String)session.getAttribute("id");
	      
		String pId = request.getParameter("pId");
		PDao dao = new PDao();
		PDto dto = dao.contentView(pId, id);
		
		request.setAttribute("content_view", dto);
		
	}

}
