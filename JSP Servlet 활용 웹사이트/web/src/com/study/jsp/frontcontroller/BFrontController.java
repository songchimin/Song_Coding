package com.study.jsp.frontcontroller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.study.jsp.command.AmemberCommand;
import com.study.jsp.command.BCommand;
import com.study.jsp.command.BCommentCommand;
import com.study.jsp.command.BCommentDeleteCommand;
import com.study.jsp.command.BContentCommand;
import com.study.jsp.command.BDeleteCommand;
import com.study.jsp.command.BFindCommand;
import com.study.jsp.command.BHeartCommand;
import com.study.jsp.command.BLikeCommand;
import com.study.jsp.command.BListCommand;
import com.study.jsp.command.BModifyCommand;
import com.study.jsp.command.BRankCommand;
import com.study.jsp.command.BReplyCommand;
import com.study.jsp.command.BReplyViewCommand;
import com.study.jsp.command.BWriteCommand;
import com.study.jsp.command.CRankCommand;
import com.study.jsp.command.NListCommand;
import com.study.jsp.command.PRankCommand;

@WebServlet("*.do")
public class BFrontController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public BFrontController() {
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
		BCommand command = null;
		
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
		
		if(option!=null && text!=null && (com.equals("/find.do") || com.equals("/member.do")) )
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
		
		if(com.equals("/write_view.do")) {
			viewPage = "board/write_view.jsp";
		}else if(com.equals("/write.do")) {
			command = new BWriteCommand();
			command.execute(request, response);
			viewPage = "list.do";
			
		}else if(com.equals("/list.do")) {
			session.removeAttribute("findoption");
			session.removeAttribute("findtext");
			
			command = new BListCommand();
			command.execute(request, response);
			viewPage = "board/boardmain.jsp";
			
		}else if(com.equals("/content_view.do")) {
			command = new BContentCommand();
			command.execute(request, response);
			viewPage = "board/content_view.jsp";
			
		}else if(com.equals("/modify_view.do")) {
			command = new BContentCommand();
			command.execute(request, response);
			viewPage = "board/modify_view.jsp";
		}else if(com.equals("/modify.do")) {
			command = new BModifyCommand();
			command.execute(request, response);
			
			command = new BContentCommand();
			command.execute(request, response);
			viewPage = "board/content_view.jsp";
			
		}else if(com.equals("/delete.do")) {
			command = new BDeleteCommand();
			command.execute(request, response);
			viewPage = "list.do?page="+curPage;
		}else if(com.equals("/reply_view.do")) {
			command = new BReplyViewCommand();
			command.execute(request, response);
			viewPage = "board/reply_view.jsp";
			
		}else if(com.equals("/reply.do")) {
			command = new BReplyCommand();
			command.execute(request, response);
			viewPage = "list.do?page="+curPage;
		}else if(com.equals("/find.do")) {
//			command = new BFindCommand();
			command = new BListCommand();
			command.execute(request, response);
			viewPage = "board/boardmain.jsp";
		}else if(com.equals("/member.do")) {
			command = new AmemberCommand();
			command.execute(request, response);
			viewPage = "admin/member_administration.jsp";
			session.removeAttribute("findoption");
			session.removeAttribute("findtext");
		}else if(com.equals("/comment.do")) {
			command = new BCommentCommand();
			command.execute(request, response);
			viewPage = "content_view.do?bId="+request.getParameter("bid");
		}else if (com.equals("/like.do")) {
	         command = new BLikeCommand();
	         command.execute(request, response);
	         viewPage = "/content_view.do";
	    }else if (com.equals("/rank.do")) {
		     command = new BRankCommand();
		     command.execute(request, response);
		     viewPage = "admin/boradManagement.jsp";
		}else if (com.equals("/crank.do")) {
		     command = new CRankCommand();
		     command.execute(request, response);
		     viewPage = "admin/boradManagement.jsp";
		}else if (com.equals("/prank.do")) {
		     command = new PRankCommand();
		     command.execute(request, response);
		     viewPage = "admin/boradManagement.jsp";
		}else if (com.equals("/cdelete.do")) {
			command = new BCommentDeleteCommand();
			command.execute(request, response);
			viewPage = "content_view.do?bId="+request.getParameter("bid");
		}

//		else if(com.equals("/heart.do")) {
//			command = new BHeartCommand();
//			command.execute(request, response);
//			viewPage = "content_view.do?bId="+request.getParameter("bid");
//		}
		
		
		RequestDispatcher dispatcher = request.getRequestDispatcher(viewPage);
		dispatcher.forward(request, response);
	}


}
