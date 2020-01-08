package chat;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import com.study.jsp.MemberDAO;

@WebServlet("/ProfileServlet")
public class ProfileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		
		
		String savePath = request.getRealPath("/upload"); // upload는 폴더명 / 폴더의 경로를 구해옴
		
		MultipartRequest multi = null;
		
		try {
			multi = new MultipartRequest( // MultipartRequest 인스턴스 생성(cos.jar의 라이브러리)
					request, 
					savePath, // 파일을 저장할 디렉토리 지정
					10 * 1024 * 1024, // 첨부파일 최대 용량 설정(bite) / 10KB / 용량 초과 시 예외 발생
					"utf-8", // 인코딩 방식 지정
					new DefaultFileRenamePolicy() // 중복 파일 처리(동일한 파일명이 업로드되면 뒤에 숫자 등을 붙여 중복 회피)
			);
			
		} catch (Exception e) {
			e.getStackTrace();
		} // 업로드 종료
		
		String id = multi.getParameter("id");
		
		String fileName = "";
	
		File file = multi.getFile("profile");
		
		if(file != null) {
			String ext = file.getName().substring(file.getName().lastIndexOf(".") + 1);
			if(ext.equals("jsp") || ext.equals("png"))
			{
				String prev = new MemberDAO().getprofile(id);
				File prevFile = new File(savePath + "/" + prev);
				if(prevFile.exists()) {
					if(prev.equals("profile.png"))
					{
						
					}
					else
						prevFile.delete();
				}
				fileName = file.getName();
			}else {
				if(file.exists()) {
					file.delete();
				}
			}
			
		}
		
		new MemberDAO().profile(id, fileName);
		
		PrintWriter writer = response.getWriter();
		
		writer.println("<html><head></head><body>");
		writer.println("<script language=\"javascript\">");
//		writer.println("	alert(\"프로필 수정 완료.\");");
		writer.println("	document.location.href=\"/zzzz/login/modify.jsp\";");
		writer.println("</script>");
		writer.println("</body></html>");
		writer.close();
	}

}
