package com.study.jsp.command;

import java.io.File;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import com.study.jsp.dao.copy.BDao;


public class BWriteCommand implements BCommand{

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = null;
		session = request.getSession();
		response.setContentType("text/html; charset=UTF-8");
		
		
		String savePath = request.getRealPath("upload"); // upload는 폴더명 / 폴더의 경로를 구해옴

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
		
		String id = (String)session.getAttribute("id");
		String bName = (String)session.getAttribute("name");
		String bTitle = multi.getParameter("bTitle");
		String bContent = multi.getParameter("bContent");
		
		
		String bFile = "";
		String bRealFile = "";
	
		File file = multi.getFile("boardFile");
		
		if(file != null) {
			bFile = multi.getOriginalFileName("boardFile");
			bRealFile = file.getName();
		}
		
		System.out.println("파일" + bFile);
		System.out.println("r파일" + bRealFile);

		
		BDao dao = new BDao();
		dao.write(id, bName, bTitle, bContent, bFile, bRealFile);
		
	}
}
