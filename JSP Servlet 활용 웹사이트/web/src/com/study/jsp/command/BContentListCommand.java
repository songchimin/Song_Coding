package com.study.jsp.command;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.study.dto.NDto;
import com.study.dto.NPageInfo;
import com.study.jsp.dao.copy.BDao;
import com.study.jsp.dao.copy.NDao;

public class BContentListCommand implements BCommand{

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
//		HttpSession session = null;
//		session = request.getSession();
//
//		
//		BDao dao = BDao.getInstance();	
//
//
//		ArrayList<NDto> dtos = dao.commentlist(bid);
//
//
//		request.setAttribute("nlist", dtos);
	}

}
