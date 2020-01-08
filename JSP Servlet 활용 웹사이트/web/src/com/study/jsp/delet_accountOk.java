package com.study.jsp;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class delet_accountOk implements Service {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		System.out.println("delete_accountOk");
		request.setCharacterEncoding("UTF-8");
		
		String id = request.getParameter("id");
		System.out.println(id);
		MemberDAO dao = MemberDAO.getInstance();
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter writer = response.getWriter();
		
		dao.deleteMember(id);
	
		writer.println("<html><head></head><body>");
		writer.println("<script language=\"javascript\">");
		writer.println("	alert(\"계정 삭제 완료\");");
		writer.println("	document.location.href=\"/zzzz/member.do\";");
		writer.println("</script>");
		writer.println("</body></html>");
		writer.close();

	}

}
