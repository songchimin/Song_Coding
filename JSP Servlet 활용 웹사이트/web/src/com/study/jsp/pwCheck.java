package com.study.jsp;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class pwCheck  implements Service{

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		HttpSession session = null;
		session = request.getSession();
		
		String id = (String)session.getAttribute("id");
		String pw = request.getParameter("pw");
		
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter writer = response.getWriter();
		
		MemberDAO dao = MemberDAO.getInstance();
		
		if(dao.userCheck(id, pw) == MemberDAO.MEMBER_JOIN_SUCCESS) {
			writer.println("<html><head></head><body>");
			writer.println("<script language=\"javascript\">");
			writer.println("	document.location.href=\"/zzzz/login/modify.jsp\";");
			writer.println("</script>");
			writer.println("</body></html>");
			writer.close();
		}
		else
		{
			writer.println("<html><head></head><body>");
			writer.println("<script language=\"javascript\">");
			writer.println("	alert(\"비밀번호가 틀렸습니다.\");");
			writer.println("	history.back();");
			writer.println("</script>");
			writer.println("</body></html>");
			writer.close();
		}
	}


}
