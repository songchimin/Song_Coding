package chat;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.study.jsp.dao.copy.BDao;


@WebServlet("/ChatListServlet")
public class ChatListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public ChatListServlet() {
        super();

    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		
		String fromID = request.getParameter("fromID");
		String toID = request.getParameter("toID");
		String listType = request.getParameter("listType");
		
		System.out.println(fromID + " " + toID + " " + listType);
		
		if(fromID == null || fromID.equals("") || toID == null || toID.equals("") || listType == null || listType.equals(""))
		{
			response.getWriter().write("");
		}
		else if(listType.equals("ten"))
		{	
			response.getWriter().write(getTen(URLDecoder.decode(fromID,"UTF-8"),URLDecoder.decode(toID,"UTF-8")));
		}
		else {
			try {
				response.getWriter().write(getID(URLDecoder.decode(fromID,"UTF-8"),URLDecoder.decode(toID,"UTF-8"), listType));
			} catch (Exception e) {
				response.getWriter().write("");
			}
		}
			
	}
	
	public String getTen(String fromID, String toID) {
		StringBuffer result = new StringBuffer("");
		result.append("{\"result\":[");
		BDao chatDAO = new BDao();
		
		ArrayList<ChatDTO> chatlist = chatDAO.getChatListByRecent(fromID, toID, 100);
		if(chatlist.size() == 0 ) return "";
		for(int i = 0 ; i < chatlist.size() ; i++) {
			result.append("[{\"value\": \"" + chatlist.get(i).getFromID() + "\"},");
			result.append("{\"value\": \"" + chatlist.get(i).getToID() + "\"},");
			result.append("{\"value\": \"" + chatlist.get(i).getChatContent() + "\"},");
			result.append("{\"value\": \"" + chatlist.get(i).getChatTime() + "\"}]");
			if(i != chatlist.size() - 1) result.append(",");
		}
		result.append("], \"last\":\"" + chatlist.get(chatlist.size() - 1).getChatID() + "\"}");
		chatDAO.readChat(fromID, toID);
		return result.toString();
	}

	public String getID(String fromID, String toID, String chatID) {
		StringBuffer result = new StringBuffer("");
		result.append("{\"result\":[");
		BDao chatDAO = new BDao();
		ArrayList<ChatDTO> chatlist = chatDAO.getChatListById(fromID, toID, chatID);
		if(chatlist.size() == 0 ) return "";
		for(int i = 0 ; i < chatlist.size() ; i++) {
			result.append("[{\"value\": \"" + chatlist.get(i).getFromID() + "\"},");
			result.append("{\"value\": \"" + chatlist.get(i).getToID() + "\"},");
			result.append("{\"value\": \"" + chatlist.get(i).getChatContent() + "\"},");
			result.append("{\"value\": \"" + chatlist.get(i).getChatTime() + "\"}]");
			if(i != chatlist.size() - 1) result.append(",");
		}
		result.append("], \"last\":\"" + chatlist.get(chatlist.size() - 1).getChatID() + "\"}");
		chatDAO.readChat(fromID, toID);
		return result.toString();
	}

}
