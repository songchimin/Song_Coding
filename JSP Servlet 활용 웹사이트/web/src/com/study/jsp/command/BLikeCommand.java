package com.study.jsp.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.study.dto.BDto;
import com.study.jsp.dao.copy.BDao;

public class BLikeCommand implements BCommand{

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		
	      String bId = request.getParameter("bId");
	      BDao dao = new BDao();
	      
	      HttpSession session = request.getSession();
	      String id = (String)session.getAttribute("id");
	      
	      dao.downHit(bId);
	      
	      BDto dto = dao.likeCheck(bId, id);
	}

}
