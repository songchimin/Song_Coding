package com.study.jsp;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class KakaoLogin implements Service{

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String id = request.getParameter("id");
		String name = request.getParameter("name");

		response.setContentType("text/html; charset=UTF-8");
		PrintWriter writer = response.getWriter();
		
		HttpSession session = request.getSession();
		session.setAttribute("id", id);
		session.setAttribute("name", name);
		
		writer.println("<html><head></head><body>");
		writer.println("<script language=\"javascript\">");
		writer.println("	alert(\"로그인 성공\");");
		writer.println("	document.location.href=\"/zzzz/home.jsp\";");
		writer.println("</script>");
		writer.println("</body></html>");
		writer.close();
		
	}

}
