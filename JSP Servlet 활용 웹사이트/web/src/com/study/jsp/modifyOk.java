package com.study.jsp;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class modifyOk implements Service {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();

		String id = (String)session.getAttribute("id");
		String pw = request.getParameter("pw");
		String pw_check = request.getParameter("pw_check");
		String eMail = request.getParameter("eMail");
		String address = request.getParameter("address");
		
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter writer = response.getWriter();
		MemberDAO dao = MemberDAO.getInstance();
		MemberDTO dto = dao.getMember(id);

		if(pw.equals(pw_check)) {
			dto.setPw(pw);
			dto.seteMail(eMail);
			dto.setAddress(address);
			
			int ri = dao.updateMember(dto);
			
			if(ri == 1) {
				writer.println("<html><head></head><body>");
				writer.println("<script language=\"javascript\">");
				writer.println("	alert(\"정보수정 완료\");");
				writer.println("	document.location.href=\"/zzzz/login/mypage.jsp\";");
				writer.println("</script>");
				writer.println("</body></html>");
				writer.close();
			}else {
					writer.println("<html><head></head><body>");
					writer.println("<script language=\"javascript\">");
					writer.println("	alert(\"에러\");");
					writer.println("	document.location.href=\"/zzzz/home.jsp\";");
					writer.println("</script>");
					writer.println("</body></html>");
					writer.close();
			}
		
		}else {
				writer.println("<html><head></head><body>");
				writer.println("<script language=\"javascript\">");
				writer.println("	alert(\"비밀번호가 서로 맞지 않습니다.\");");
				writer.println("</script>");
				writer.println("</body></html>");
				writer.close();
		}	
		
	}

}
