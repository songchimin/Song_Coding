package chat;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.study.jsp.MemberDAO;
import com.study.jsp.dao.copy.BDao;


@WebServlet("/ChatBoxServlet")
public class ChatBoxServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		
		String userID = request.getParameter("userID");

		if(userID == null || userID.equals("") )
		{
			response.getWriter().write("0");
		}else {
			try {
				userID = URLDecoder.decode(userID, "UTF-8");
				response.getWriter().write(getBox(userID));
			} catch (Exception e) {
				response.getWriter().write("");
			}

		}
	}
	
	public String getBox(String userID) {
		StringBuffer result = new StringBuffer("");
		result.append("{\"result\":[");
		BDao chatDAO = new BDao();
		ArrayList<ChatDTO> chatlist = chatDAO.getBox(userID);
		if(chatlist.size() == 0 ) return "";
		for(int i = chatlist.size() - 1 ; i >= 0 ; i--) {
			String unread = "";
			String profile = "";
			if(userID.equals(chatlist.get(i).getToID())) {
				unread = chatDAO.getUnreadChat(chatlist.get(i).getFromID(), userID) + "";
				if(unread.equals("0")) unread = "";
			}
			if(userID.equals(chatlist.get(i).getToID())){
				profile = new MemberDAO().getprofilePath(chatlist.get(i).getFromID());
			}else {
				profile = new MemberDAO().getprofilePath(chatlist.get(i).getToID());
			}
			result.append("[{\"value\": \"" + chatlist.get(i).getFromID() + "\"},");
			result.append("{\"value\": \"" + chatlist.get(i).getToID() + "\"},");
			result.append("{\"value\": \"" + chatlist.get(i).getChatContent() + "\"},");
			result.append("{\"value\": \"" + chatlist.get(i).getChatTime() + "\"},");
			result.append("{\"value\": \"" + unread + "\"},");
			result.append("{\"value\": \"" + profile + "\"}]");
			if(i != 0) result.append(",");
		}
		result.append("], \"last\":\"" + chatlist.get(chatlist.size() - 1).getChatID() + "\"}");
		return result.toString();
	}

}
