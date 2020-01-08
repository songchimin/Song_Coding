package com.study.jsp.frontcontroller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.study.jsp.command.BContentCommand;
import com.study.jsp.command.PCommand;
import com.study.jsp.command.PContentCommand;
import com.study.jsp.command.PListCommand;
import com.study.jsp.command.PWriteCommand;

@WebServlet("*.po")
public class PFrontController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	public PFrontController(){
		super();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		actionDo(request,response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		actionDo(request,response);
		
	}
	
	private void actionDo(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException{
		
		request.setCharacterEncoding("UTF-8");
		
		String viewPage = null;
		PCommand command = null;
		
		String url = request.getRequestURI();
		String conPath = request.getContextPath();
		String com = url.substring(conPath.length());
		
		System.out.println("uri : " + url);
		System.out.println("conPath : " + conPath);
		System.out.println("com : " + com);
		
		HttpSession session = null;
		session = request.getSession();
		
		
		if(com.equals("/picture_board_write_view.po")) {
			viewPage = "board/picture_board_write_view.jsp";
		}else if(com.equals("/write.po")) {
			command = new PWriteCommand();
			command.execute(request, response);
			viewPage = "list.po";
			
		}else if(com.equals("/list.po")) {			
			command = new PListCommand();
			command.execute(request, response);
			viewPage = "board/picture_board.jsp";
			
		}else if(com.equals("/content_view.po")) {
			command = new PContentCommand();
			command.execute(request, response);
			viewPage = "board/picture_board_content_view.jsp";
		}
		
		RequestDispatcher dispatcher = request.getRequestDispatcher(viewPage);
		dispatcher.forward(request, response);
	}

}
