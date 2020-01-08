package com.study.jsp;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class logoutOk implements Service{

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();
		
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter writer = response.getWriter();
		

		writer.println("<html><head></head><body>");
		writer.println("<script language=\"javascript\">");
		writer.println("	alert(\"로그아웃\");");
		
		if(session.getAttribute("location").equals("home"))
		{
			writer.println("	document.location.href=\"/zzzz/home.jsp\";");
		}
		else if(session.getAttribute("location").equals("board"))
		{
			writer.println("	javascript:window.location=\"list.do\";");
		}
		else if(session.getAttribute("location").equals("mapboard"))
		{
			writer.println("	document.location.href=\"/zzzz/mapboard/mapboard_main.jsp\";");
		}
		else if(session.getAttribute("location").equals("mypage"))
		{
			writer.println("	document.location.href=\"/zzzz/home.jsp\";");
		}
		else if(session.getAttribute("location").equals("notify"))
		{
			writer.println("	document.location.href=\"/zzzz/notify.no\";");
		}
		else if(session.getAttribute("location").equals("member_administration"))
		{
			writer.println("	document.location.href=\"/zzzz/home.jsp\";");
		}
		
		writer.println("</script>");
		session.invalidate();
		
		 
		writer.println("<script language=\"javascript\">");
		writer.println("	document.location.href=location.href;");
		writer.println("</script>");
		
		writer.println("</body></html>");
		writer.close();
		
	}

}
