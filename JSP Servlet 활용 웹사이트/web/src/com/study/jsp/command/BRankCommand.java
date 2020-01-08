package com.study.jsp.command;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.study.dto.RDto;
import com.study.jsp.dao.copy.BDao;

public class BRankCommand implements BCommand{

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
	      
		String start = request.getParameter("start");
		String end = request.getParameter("end");
		
		BDao dao = new BDao();
		
		ArrayList<RDto> rdto = dao.BRank(start, end);
		session.setAttribute("Brank", rdto);
		
		rdto = dao.PRank(start, end);
		session.setAttribute("Prank", rdto);
		
		rdto = dao.CRank(start, end);
		session.setAttribute("Crank", rdto);
			
	}
}
