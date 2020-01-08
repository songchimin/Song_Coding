package com.study.jsp;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sun.corba.se.spi.orbutil.fsm.Action;


@WebServlet("*.doo")
public class FrontCon extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public FrontCon() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		System.out.println("doGet");
		actionDo(request,response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		System.out.println("doPost");
		actionDo(request,response);
	}
	
	private void actionDo(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException{
		System.out.println("actinoDo");
		
		request.setCharacterEncoding("UTF-8");
		HttpSession session = null;
		session = request.getSession();
		
		String uri = request.getRequestURI();
		System.out.println("uri : " + uri);
		String conPath = request.getContextPath();
		System.out.println("conPath : " + conPath);
		String command = uri.substring(conPath.length());
		System.out.println("command : " + command);
		
		
		int curPage = 1;
		if(session.getAttribute("cpage") != null) {
			curPage = (int)session.getAttribute("cpage");
		}
		
		if(command.equals("/login/joinOk.doo")) {
			Service service = new joinOk();
			service.execute(request, response);
			
		}else if(command.equals("/login/loginOk.doo")) {
			Service service = new loginOk();
			service.execute(request, response);	

		}else if(command.equals("/login/modifyOk.doo")) {
			Service service = new modifyOk();
			service.execute(request, response);	
					
		}else if(command.contains("/logoutOk.doo")) {
			Service service = new logoutOk();
			service.execute(request, response);	
		}else if(command.equals("/login/deleteOk.doo")) {
			Service service = new deleteOk();
			service.execute(request, response);	
		}else if(command.equals("/ban.doo")) {
			Service service = new banOk();
			service.execute(request, response);	
		}else if(command.equals("/unban.doo")) {
			Service service = new unbanOk();
			service.execute(request, response);	
		}else if(command.equals("/delet_account.doo")) {
			Service service = new delet_accountOk();
			service.execute(request, response);	
		}else if(command.equals("/pwcheck.doo")) {
			Service service = new pwCheck();
			service.execute(request, response);	
		}else if(command.equals("/login/kakao.doo")) {
			Service service = new KakaoLogin();
			service.execute(request, response);	
		}
			
//		else if(command.equals("/board/logoutOk.doo")) {
//			Service service = new logoutOk();
//			service.execute(request, response);	
//		}
	}
}

