package com.study.jsp;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class loginOk implements Service {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		String id = request.getParameter("id");
		String pw = request.getParameter("pw");

		response.setContentType("text/html; charset=UTF-8");
		PrintWriter writer = response.getWriter();
		
		MemberDAO dao = MemberDAO.getInstance();
		System.out.println(id + pw);
		if(dao.confirmId(id) == MemberDAO.MEMBER_NONEXISTENT) {
			writer.println("<html><head></head><body>");
			writer.println("<script language=\"javascript\">");
			writer.println("	alert(\"존재하지 않은 아이디입니다.\");");
			writer.println("	history.back();");
			writer.println("</script>");
			writer.println("</body></html>");
			writer.close();
		}else {
			int ri = dao.userCheck(id,pw);
			
			if(ri == MemberDAO.MEMBER_JOIN_SUCCESS){
				
				int r = dao.banCheck(id);
				
				if(r == MemberDAO.MEMBER_JOIN_BAN) {
					writer.println("<html><head></head><body>");
					writer.println("<script language=\"javascript\">");
					writer.println("	alert(\"차단된 아이디 입니다.\");");
					writer.println("	document.location.href=\"login.jsp\";");
					writer.println("</script>");
					writer.println("</body></html>");
					writer.close();
				}else {
					MemberDTO dto = dao.getMember(id);
					
					HttpSession session = request.getSession();
					session.setAttribute("id", id);
					session.setAttribute("name", dto.getName());
					
					if(session.getAttribute("location").equals("home"))
					{
						writer.println("<html><head></head><body>");
						writer.println("<script language=\"javascript\">");
						writer.println("	alert(\"로그인 성공\");");
						writer.println("	document.location.href=\"/zzzz/home.jsp\";");
						writer.println("</script>");
						writer.println("</body></html>");
						writer.close();
					}
					else if(session.getAttribute("location").equals("board"))
					{
						writer.println("<html><head></head><body>");
						writer.println("<script language=\"javascript\">");
						writer.println("	alert(\"로그인 성공\");");
						writer.println("	javascript:window.location=\"../list.do\";");
						writer.println("</script>");
						writer.println("</body></html>");
						writer.close();
					}else if(session.getAttribute("location").equals("notify"))
					{
						writer.println("<html><head></head><body>");
						writer.println("<script language=\"javascript\">");
						writer.println("	alert(\"로그인 성공\");");
						writer.println("	javascript:window.location=\"/zzzz/notify.no\";");
						writer.println("</script>");
						writer.println("</body></html>");
						writer.close();
					}
					else if(session.getAttribute("location").equals("mapboard"))
					{
						writer.println("<html><head></head><body>");
						writer.println("<script language=\"javascript\">");
						writer.println("	alert(\"로그인 성공\");");
						writer.println("	javascript:window.location=\"/zzzz/mapboard/mapboard_main.jsp\";");
						writer.println("</script>");
						writer.println("</body></html>");
						writer.close();
					}	
					else
					{
						writer.println("<html><head></head><body>");
						writer.println("<script language=\"javascript\">");
						writer.println("	alert(\"로그인 성공\");");
						writer.println("	javascript:window.location=\"/zzzz/home.jsp\";");
						writer.println("</script>");
						writer.println("</body></html>");
						writer.close();
					}
					
				}
			}
			else if(ri == MemberDAO.MEMBER_LOGIN_PW_NO_GOOD){
				writer.println("<html><head></head><body>");
				writer.println("<script language=\"javascript\">");
				writer.println("	alert(\"비밀번호가 틀렸습니다.\");");
				writer.println("	document.location.href=\"login.jsp\";");
				writer.println("</script>");
				writer.println("</body></html>");
				writer.close();
			}
			else {
				writer.println("<html><head></head><body>");
				writer.println("<script language=\"javascript\">");
				writer.println("	alert(\"로그인 실패\");");
				writer.println("	document.location.href=\"login.jsp\";");
				writer.println("</script>");
				writer.println("</body></html>");
				writer.close();
			}
		}	
	}

}
