package com.study.jsp.command;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.study.dto.RDto;
import com.study.jsp.dao.copy.BDao;

public class CRankCommand implements BCommand{

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
	      
		String start = request.getParameter("start");
		String end = request.getParameter("end");
		
		BDao dao = new BDao();
		
		ArrayList<RDto> rdto = dao.CRank(start, end);
		
		session.setAttribute("Crank", rdto);
}
}
