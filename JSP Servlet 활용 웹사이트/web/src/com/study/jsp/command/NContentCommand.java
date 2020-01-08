package com.study.jsp.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.study.dto.NDto;
import com.study.jsp.dao.copy.NDao;

public class NContentCommand implements NCommand{

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		
		String nId = request.getParameter("nId");
		NDao dao = new NDao();
		NDto dto = dao.contentView(nId);
		
		request.setAttribute("Ncontent_view", dto);
		
	}

}
