package com.study.jsp;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class banOk implements Service{

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String id = request.getParameter("id");
		
		System.out.println(id);
		
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter writer = response.getWriter();
		MemberDAO dao = MemberDAO.getInstance();
		MemberDTO dto = dao.getMember(id);
		
		dto.setId(id);
		dto.setBan("1");
		
		int ri = dao.banMember(dto);
			
		if(ri == 1) {
			writer.println("<html><head></head><body>");
			writer.println("<script language=\"javascript\">");
//			writer.println("	alert(\"벤 완료\");");
			writer.println("	document.location.href=\"/zzzz/member.do\";");
			writer.println("</script>");
			writer.println("</body></html>");
			writer.close();
		}
		
	}
}
