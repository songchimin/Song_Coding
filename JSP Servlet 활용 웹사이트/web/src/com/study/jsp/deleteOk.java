package com.study.jsp;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class deleteOk implements Service{

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		System.out.println("deleteOk");
		request.setCharacterEncoding("UTF-8");
		
		HttpSession session = request.getSession();
		String id = (String)session.getAttribute("id");
		String pw = request.getParameter("pw");

		MemberDAO dao = MemberDAO.getInstance();
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter writer = response.getWriter();
		
		
		if(dao.userCheck(id, pw) == MemberDAO.MEMBER_JOIN_SUCCESS)
		{
			dao.deleteMember(id);
			session.invalidate();
			
			writer.println("<html><head></head><body>");
			writer.println("<script language=\"javascript\">");
			writer.println("	alert(\"계정 삭제 완료\");");
			writer.println("	document.location.href=\"../home.jsp\";");
			writer.println("</script>");
			writer.println("</body></html>");
			writer.close();
		}
		else
		{
			writer.println("<html><head></head><body>");
			writer.println("<script language=\"javascript\">");
			writer.println("	alert(\"비밀번호 틀림!\");");
			writer.println("	document.location.href=\"modify.jsp\";");
			writer.println("</script>");
			writer.println("</body></html>");
			writer.close();
		}
	}

}
