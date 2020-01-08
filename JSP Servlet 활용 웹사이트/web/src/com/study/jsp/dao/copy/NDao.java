package com.study.jsp.dao.copy;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.study.dto.NDto;
import com.study.dto.NPageInfo;


public class NDao {
	
	private static NDao instance= new NDao();
	DataSource dataSource;
	
	int listCount = 10;
	int pageCount = 10;
	
	public NDao() {
		try {
			Context context = new InitialContext();
			dataSource = (DataSource)context.lookup("java:comp/env/jdbc/Oracle11g");
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static NDao getInstance() {
		return instance;
	}
	
	public void write(String id, String nName, String nTitle, String nContent) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = dataSource.getConnection();
			String query = "insert into mvc_notifyboard " +
						   " (nId, id, nName, nTitle, nContent, nHit, nGroup, nStep, nIndent) " +
						   " values " +
						   " (mvc_notifyboard_seq.nextval, ?, ?, ?, ?, 0, mvc_notifyboard_seq.currval, 0, 0 )";
			
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, id);
			pstmt.setString(2, nName);
			pstmt.setString(3, nTitle);
			pstmt.setString(4, nContent);
			int rn = pstmt.executeUpdate();
					
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(pstmt != null) pstmt.close();
				if(con != null) con.close();
			}catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}
	
	public ArrayList<NDto> list(int curPage){
		ArrayList<NDto> dtos = new ArrayList<NDto>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet resultSet = null;
		
		//1~10 11~20 21~30 start,end
		int nStart = (curPage - 1) * listCount + 1;
		int nEnd = (curPage - 1) * listCount + listCount;
		
		try {
			con = dataSource.getConnection();
			
			String query = "select * " +
							" from ( " + 
							" 	select rownum num, A.* " + 
							"		from ( " + 
							"			select * " + 
							"				from mvc_notifyboard " +
							"			order by nGroup desc, nStep asc ) A " +
							"	where rownum <= ? ) B " + 
							"where B.num >= ?";
			
			
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, nEnd);
			pstmt.setInt(2, nStart);
			resultSet = pstmt.executeQuery();
			
			while(resultSet.next())
			{
				int nId = resultSet.getInt("nId");
				String id = resultSet.getString("id");
				String nName = resultSet.getString("nName");
				String nTitle = resultSet.getString("nTitle");
				String nContent = resultSet.getString("nContent");
				Timestamp nDate = resultSet.getTimestamp("nDate");
				int nHit = resultSet.getInt("nHit");
				int nGroup = resultSet.getInt("nGroup");
				int nStep = resultSet.getInt("nStep");
				int nIndent = resultSet.getInt("nIndent");
				
				NDto dto = new NDto(nId, id, nName, nTitle, nContent, nDate, nHit, nGroup, nStep, nIndent);
				dtos.add(dto);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(resultSet != null) resultSet.close();
				if(pstmt != null) pstmt.close();
				if(con != null) con.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		
		return dtos;
	}
	
	public NDto contentView(String strID) {
		upHit(strID);
		
		NDto dto = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet resultSet = null;
		
		try {
			con = dataSource.getConnection();
			
			String query = "select * from mvc_notifyboard where nId = ?";
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, Integer.parseInt(strID));
			resultSet = pstmt.executeQuery();
			
			while(resultSet.next())
			{
				int nId = resultSet.getInt("nId");
				String id = resultSet.getString("id");
				String nName = resultSet.getString("nName");
				String nTitle = resultSet.getString("nTitle");
				String nContent = resultSet.getString("nContent");
				Timestamp nDate = resultSet.getTimestamp("nDate");
				int nHit = resultSet.getInt("nHit");
				int nGroup = resultSet.getInt("nGroup");
				int nStep = resultSet.getInt("nStep");
				int nIndent = resultSet.getInt("nIndent");

				dto = new NDto(nId, id, nName, nTitle, nContent, nDate, nHit,
					nGroup, nStep, nIndent);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(resultSet != null) resultSet.close();
				if(pstmt != null) pstmt.close();
				if(con != null) con.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		
		return dto;
	}
	

	
	
	public void modify(String nId, String nName, String nTitle, String nContent) {
		Connection con = null;
		PreparedStatement pstmt = null;

		String query = "update mvc_notifyboard " +
				  		"	set nName = ?, " +
				  		"		nTitle = ?, " +
				  		"		nContent = ? " +
				  		"where nId = ?";
		
		try {
			con = dataSource.getConnection();			
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, nName);
			pstmt.setString(2, nTitle);
			pstmt.setString(3, nContent);
			pstmt.setString(4, nId);
			int rn = pstmt.executeUpdate();
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(pstmt != null) pstmt.close();
				if(con != null) con.close();
			}catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}
	
	private void upHit(String nId) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = dataSource.getConnection();	
			String query = "update mvc_notifyboard set nHit = nHit + 1 where nId = ?";
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, nId);
			
			int rn = pstmt.executeUpdate();
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(pstmt != null) pstmt.close();
				if(con != null) con.close();
			}catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}
	
	public void delete(String nId) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = dataSource.getConnection();	
			String query = "delete from mvc_notifyboard where nId = ?";
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, Integer.parseInt(nId));
			
			int rn = pstmt.executeUpdate();
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(pstmt != null) pstmt.close();
				if(con != null) con.close();
			}catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}
	
	public NPageInfo articlePage(int curPage) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet resultSet = null;
		
		int listCount = 10;	//한페이지당 보여줄 게시물 갯수
		int pageCount = 10; //하단에 보여줄 리스트 갯수
		
		// 총 게시물 갯수
		int totalCount = 0;
		try {
			con = dataSource.getConnection();
			
			String query = "select count(*) as total from mvc_notifyboard";
			pstmt = con.prepareStatement(query);
			resultSet = pstmt.executeQuery();
			
			if(resultSet.next()) {			
				totalCount = resultSet.getInt("total");
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(resultSet != null) resultSet.close();
				if(pstmt != null) pstmt.close();
				if(con != null) con.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		
		int totalPage = totalCount / listCount;
		if(totalCount % listCount > 0)
			totalPage++;
		
		int myCurPage = curPage;
		if(myCurPage > totalPage)
			myCurPage = totalPage;
		if(myCurPage < 1)
			myCurPage = 1;
		
		int startPage = ((myCurPage - 1) / pageCount) * pageCount + 1;
		
		int endPage = startPage + pageCount - 1;
		if(endPage > totalPage)
			endPage = totalPage;
		
		NPageInfo pinfo = new NPageInfo();
		pinfo.setTotalCount(totalCount);
		pinfo.setListCount(listCount);
		pinfo.setTotalPage(totalPage);
		pinfo.setCurPage(myCurPage);
		pinfo.setPageCount(pageCount);
		pinfo.setStartPage(startPage);
		pinfo.setEndPage(endPage);
		
		return pinfo;
	}
	
	
	
	public ArrayList<NDto> findlist(int curPage, String option, String text){
		ArrayList<NDto> dtos = new ArrayList<NDto>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet resultSet = null;
		
		//1~10 11~20 21~30 start,end
		int nStart = (curPage - 1) * listCount + 1;
		int nEnd = (curPage - 1) * listCount + listCount;
		
		System.out.println("검색" + option +"  "+ text);
		
		try {
			con = dataSource.getConnection();
			
			if((option!=null) && (text!=null)) {
			String query="";
			
			if(option.equals("제목")) {
				query = "select * " +
						" from ( " + 
						" 	select rownum num, A.* " + 
						"		from ( " + 
						"			select * " + 
						"				from mvc_notifyboard " +
						"				where nTitle like '%' ||? ||'%' " +
						"			order by nGroup desc, nStep asc ) A " +
						"	where rownum <= ? ) B " + 
						"where B.num >= ?";
			}else if(option.equals("내용")) {
				query = "select * " +
						" from ( " + 
						" 	select rownum num, A.* " + 
						"		from ( " + 
						"			select * " + 
						"				from mvc_notifyboard " +
						"				where nContent like '%' ||? ||'%' " +
						"			order by nGroup desc, nStep asc ) A " +
						"	where rownum <= ? ) B " + 
						"where B.num >= ?";
			}else if(option.equals("작성자")) {
				query = "select * " +
						" from ( " + 
						" 	select rownum num, A.* " + 
						"		from ( " + 
						"			select * " + 
						"				from mvc_notifyboard " +
						"				where nName like '%' ||? ||'%' " +
						"			order by nGroup desc, nStep asc ) A " +
						"	where rownum <= ? ) B " + 
						"where B.num >= ?";
			}
			
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, text);
			pstmt.setInt(2, nEnd);
			pstmt.setInt(3, nStart);
			resultSet = pstmt.executeQuery();
			
			}else {
				
				String query = "select * " +
						" from ( " + 
						" 	select rownum num, A.* " + 
						"		from ( " + 
						"			select * " + 
						"				from mvc_notifyboard " +
						"				where nTitle like '%' ||? ||'%' " +
						"			order by nGroup desc, nStep asc ) A " +
						"	where rownum <= ? ) B " + 
						"where B.num >= ?";
		
		
						pstmt = con.prepareStatement(query);
						pstmt.setString(1, text);
						pstmt.setInt(2, nEnd);
						pstmt.setInt(3, nStart);
						resultSet = pstmt.executeQuery();
						
			}
			
			while(resultSet.next())
			{
				int nId = resultSet.getInt("nId");
				String id = resultSet.getString("id");
				String nName = resultSet.getString("nName");
				String nTitle = resultSet.getString("nTitle");
				String nContent = resultSet.getString("nContent");
				Timestamp nDate = resultSet.getTimestamp("nDate");
				int nHit = resultSet.getInt("nHit");
				int nGroup = resultSet.getInt("nGroup");
				int nStep = resultSet.getInt("nStep");
				int nIndent = resultSet.getInt("nIndent");
						
				NDto dto = new NDto(nId, id, nName, nTitle, nContent, nDate, nHit,
											nGroup, nStep, nIndent);
				dtos.add(dto);
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(resultSet != null) resultSet.close();
				if(pstmt != null) pstmt.close();
				if(con != null) con.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		
		return dtos;
	}
	
	
	public NPageInfo articlePage2(int curPage, String findoption, String text) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet resultSet = null;
		
		int listCount = 10;	//한페이지당 보여줄 게시물 갯수
		int pageCount = 10; //하단에 보여줄 리스트 갯수
		
		

		// 총 게시물 갯수
		int totalCount = 0;
		try {
			con = dataSource.getConnection();
			String query="";
			
			if(!(findoption.equals(null)) || !(text.equals(null)) )
			{
				if(findoption.equals("제목")) {
					query = "select count(*) as total from mvc_notifyboard where nTitle like '%' ||? ||'%'";
				}else if(findoption.equals("내용")) {
					query = "select count(*) as total from mvc_notifyboard where nContent like '%' ||? ||'%'";
				}else if(findoption.equals("작성자")) {
					query = "select count(*) as total from mvc_notifyboard where nName like '%' ||? ||'%'";
				}
				pstmt = con.prepareStatement(query);
				pstmt.setString(1, text);
			}
			else {
				query = "select count(*) as total from mvc_notifyboard";
				pstmt = con.prepareStatement(query);
			}
			
			resultSet = pstmt.executeQuery();
			
			if(resultSet.next()) {			
				totalCount = resultSet.getInt("total");
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(resultSet != null) resultSet.close();
				if(pstmt != null) pstmt.close();
				if(con != null) con.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		
		int totalPage = totalCount / listCount;
		if(totalCount % listCount > 0)
			totalPage++;
		
		int myCurPage = curPage;
		if(myCurPage > totalPage)
			myCurPage = totalPage;
		if(myCurPage < 1)
			myCurPage = 1;
		
		int startPage = ((myCurPage - 1) / pageCount) * pageCount + 1;
		
		int endPage = startPage + pageCount - 1;
		if(endPage > totalPage)
			endPage = totalPage;
		
		NPageInfo pinfo = new NPageInfo();
		pinfo.setTotalCount(totalCount);
		pinfo.setListCount(listCount);
		pinfo.setTotalPage(totalPage);
		pinfo.setCurPage(myCurPage);
		pinfo.setPageCount(pageCount);
		pinfo.setStartPage(startPage);
		pinfo.setEndPage(endPage);
		
		return pinfo;
	}
	

}
