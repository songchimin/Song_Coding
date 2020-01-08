package com.study.jsp.frontcontroller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.study.jsp.command.NCommand;
import com.study.jsp.command.NContentCommand;
import com.study.jsp.command.NDeleteCommand;
import com.study.jsp.command.NListCommand;
import com.study.jsp.command.NModifyCommand;
import com.study.jsp.command.NWriteCommand;

@WebServlet("*.no")
public class NFrontController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public NFrontController() {
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
		request.setCharacterEncoding("UTF-8");
		
		String viewPage = null;
		NCommand command = null;
		
		String url = request.getRequestURI();
		String conPath = request.getContextPath();
		String com = url.substring(conPath.length());
		
		System.out.println("uri : " + url);
		System.out.println("conPath : " + conPath);
		System.out.println("com : " + com);
		
		String option=null;
		String text=null;

		HttpSession session = null;
		session = request.getSession();
		
		option = request.getParameter("findoption");
		text = request.getParameter("findtext");
		
		if(option!=null && text!=null && com.equals("/find.do") )
		{
			session.setAttribute("findoption", option);
			session.setAttribute("findtext", text);
		}
			
		System.out.println(" 2313"+ session.getAttribute("findoption"));
		System.out.println(" 2313"+ session.getAttribute("findtext"));
		
		int curPage = 1;
		if(session.getAttribute("cpage") != null) {
			curPage = (int)session.getAttribute("cpage");
		}
		
		if(com.equals("/Nwrite_view.no")) {
			viewPage = "board/Nwrite_view.jsp";
		}else if(com.equals("/write.no")) {
			command = new NWriteCommand();
			command.execute(request, response);
			viewPage = "notify.no";
			
		}else if(com.equals("/notify.no")) {
			session.removeAttribute("findoption");
			session.removeAttribute("findtext");
			
			command = new NListCommand();
			command.execute(request, response);
			viewPage = "board/notify.jsp";
			
		}else if(com.equals("/Ncontent_view.no")) {
			command = new NContentCommand();
			command.execute(request, response);
			viewPage = "board/Ncontent_view.jsp";
			
		}else if(com.equals("/Nmodify_view.no")) {
			command = new NContentCommand();
			command.execute(request, response);
			viewPage = "board/Nmodify_view.jsp";
		}else if(com.equals("/Nmodify.no")) {
			command = new NModifyCommand();
			command.execute(request, response);
			
			command = new NContentCommand();
			command.execute(request, response);
			viewPage = "board/Ncontent_view.jsp";
			
		}else if(com.equals("/Ndelete.no")) {
			command = new NDeleteCommand();
			command.execute(request, response);
			viewPage = "notify.no?page="+curPage;
		}else if(com.equals("/find.no")) {
			command = new NListCommand();
			command.execute(request, response);
			viewPage = "board/notify.jsp";
		}
		
		
		
		RequestDispatcher dispatcher = request.getRequestDispatcher(viewPage);
		dispatcher.forward(request, response);
	}


}
