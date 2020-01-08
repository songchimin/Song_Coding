package com.study.jsp.command;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.study.dto.BDto;
import com.study.dto.CDto;
import com.study.jsp.dao.copy.BDao;

public class BContentCommand implements BCommand{

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
	    String id = (String)session.getAttribute("id");
	      
		String bId = request.getParameter("bId");
		BDao dao = new BDao();
		BDto dto = dao.contentView(bId, id);
		
		request.setAttribute("content_view", dto);
		
		ArrayList<CDto> cdto = dao.commentlist(bId);
		request.setAttribute("clist", cdto);
		
	}
}
