package com.study.jsp.dao.copy;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.study.dto.HDto;
import com.study.dto.PDto;
import com.study.jsp.BPageInfo;
import com.study.jsp.MemberDAO;

public class PDao {
	private static PDao instance= new PDao();
	DataSource dataSource;
	
	int listCount = 6;
	int pageCount = 5;
	
	public PDao() {
		try {
			Context context = new InitialContext();
			dataSource = (DataSource)context.lookup("java:comp/env/jdbc/Oracle11g");
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public static PDao getInstance() {
		return instance;
	}
	
	
	public void write(String id, String bName, String bTitle, String bContent, String boardFile, String boardRealFile) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = dataSource.getConnection();
			String query = "insert into mvc_pictureboard " +
						   " (pId, id, pName, pTitle, pContent, pHit, pFile, pRealFile) " +
						   " values " +
						   " (mvc_pictureboard_seq.nextval, ?, ?, ?, ?, 0, ?, ?)";
			
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, id);
			pstmt.setString(2, bName);
			pstmt.setString(3, bTitle);
			pstmt.setString(4, bContent);
			pstmt.setString(5, boardFile);
			pstmt.setString(6, boardRealFile);
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
	
	
	public ArrayList<PDto> list(int curPage){
		ArrayList<PDto> dtos = new ArrayList<PDto>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet resultSet = null;
		PreparedStatement pstmt2 = null;
		ResultSet resultSet2 = null;
		
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
							"				from mvc_pictureboard " +
							"			order by pDate desc ) A " +
							"	where rownum <= ? ) B " + 
							"where B.num >= ?";
			
			
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, nEnd);
			pstmt.setInt(2, nStart);
			resultSet = pstmt.executeQuery();
			
			while(resultSet.next())
			{
				System.out.println("검사");
				int bId = resultSet.getInt("pId");
				String id = resultSet.getString("id");
				String bName = resultSet.getString("pName");
				String bTitle = resultSet.getString("pTitle");
				String bContent = resultSet.getString("pContent");
				Timestamp bDate = resultSet.getTimestamp("pDate");
				int bHit = resultSet.getInt("pHit");
				String path = "http://localhost:8081/zzzz/upload/" + resultSet.getString("pFile");
				
				
//				String query2 = "select count(*) from good where bId = ? ";
//		        pstmt2 = con.prepareStatement(query2);
//		        pstmt2.setInt(1, bId);
//		        resultSet2 = pstmt2.executeQuery();
		        
//		        int like_c=0;
//		        
//		        if (resultSet2.next()) {
//		           like_c = resultSet2.getInt(1);
//		        }
//		        String profile = new MemberDAO().getprofilePath(id);
//		        
				PDto dto = new PDto(bId, id, bName, bTitle, bContent, bDate, bHit);
				dto.setPath(path);
				
//				dto.setProfile(profile);
//				dto.setLike_c(like_c);
				
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
	
	public PDto contentView(String strID, String id) {
		upHit(strID);
		
		PDto dto = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet resultSet = null;
//		String like_id = "";
		
		try {
		con = dataSource.getConnection();
			
//		String query = "select count(*) from good where bId = ? ";
//        pstmt = con.prepareStatement(query);
//        pstmt.setInt(1, Integer.parseInt(strID));
//        resultSet = pstmt.executeQuery();
//        int like_c=0;
//        
//        if (resultSet.next()) {
//           like_c = resultSet.getInt(1);         
//        }
        
        
//        query = "select * from good where bId = ? and like_user = ? " ;
//        pstmt = con.prepareStatement(query);
//        pstmt.setInt(1, Integer.parseInt(strID));
//        pstmt.setString(2, id);
//        resultSet = pstmt.executeQuery();
//        
//        if (resultSet.next()) {
//           like_id = resultSet.getString(2);
//        }

        
			String query = "select * from mvc_pictureboard where pId = ?";
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, Integer.parseInt(strID));
			resultSet = pstmt.executeQuery();
			
			if(resultSet.next())
			{
				int bId = resultSet.getInt("pId");
				String uid = resultSet.getString("id");
				String bName = resultSet.getString("pName");
				String bTitle = resultSet.getString("pTitle");
				String bContent = resultSet.getString("pContent");
				Timestamp bDate = resultSet.getTimestamp("pDate");
				int bHit = resultSet.getInt("pHit");
				String bFile = resultSet.getString("pFile");
				String bRealFile = resultSet.getString("pRealFile");
				
				dto = new PDto(bId, uid, bName, bTitle, bContent, bDate, bHit, 
					bFile ,bRealFile);
				
				String path = "http://localhost:8081/zzzz/upload/" + resultSet.getString("pFile");
				dto.setPath(path);
				
//				dto = new PDto(bId, uid, bName, bTitle, bContent, bDate, bHit, 
//						bFile ,bRealFile, like_id, like_c);
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
	
	private void upHit(String bId) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = dataSource.getConnection();	
			String query = "update mvc_pictureboard set pHit = pHit + 1 where pId = ?";
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, bId);
			
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
	
public BPageInfo articlePage(int curPage) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet resultSet = null;
		
		int listCount = 6;	//한페이지당 보여줄 게시물 갯수
		int pageCount = 5; //하단에 보여줄 리스트 갯수
		
		// 총 게시물 갯수
		int totalCount = 0;
		try {
			con = dataSource.getConnection();
			
			String query = "select count(*) as total from mvc_pictureboard";
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
		
		BPageInfo pinfo = new BPageInfo();
		pinfo.setTotalCount(totalCount);
		pinfo.setListCount(listCount);
		pinfo.setTotalPage(totalPage);
		pinfo.setCurPage(myCurPage);
		pinfo.setPageCount(pageCount);
		pinfo.setStartPage(startPage);
		pinfo.setEndPage(endPage);
		
		return pinfo;
	}

	public String getFile(String boardId){
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet resultSet = null;
	
		try {
			con = dataSource.getConnection();
			
			String query = "select pFile from mvc_pictureboard where pId = ?";
			
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, boardId);
			resultSet = pstmt.executeQuery();
			
			while(resultSet.next())
			{
				return resultSet.getString("pFile");
			}
			return "";
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
		return "";
	}
	
	public PDto rankpicture(int num){
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet resultSet = null;	
		PDto dto = null;
		
		try {
			con = dataSource.getConnection();
			
			String query = "select * from mvc_pictureboard order by pHit desc";	
			
			pstmt = con.prepareStatement(query);
			resultSet = pstmt.executeQuery();
			
			if(num == 1)
			{
				if(resultSet.next())
				{	
					System.out.println("검사");
					int bId = resultSet.getInt("pId");
					String id = resultSet.getString("id");
					String bName = resultSet.getString("pName");
					String bTitle = resultSet.getString("pTitle");
					String bContent = resultSet.getString("pContent");
					Timestamp bDate = resultSet.getTimestamp("pDate");
					int bHit = resultSet.getInt("pHit");
					String path = "http://localhost:8081/zzzz/upload/" + resultSet.getString("pFile");
					dto = new PDto(bId, id, bName, bTitle, bContent, bDate, bHit);
					dto.setPath(path);
					
					return dto;
				}
			}
			else if(num == 2)
			{
				if(resultSet.next())
				{	
					resultSet.next();
					System.out.println("검사");
					int bId = resultSet.getInt("pId");
					String id = resultSet.getString("id");
					String bName = resultSet.getString("pName");
					String bTitle = resultSet.getString("pTitle");
					String bContent = resultSet.getString("pContent");
					Timestamp bDate = resultSet.getTimestamp("pDate");
					int bHit = resultSet.getInt("pHit");
					String path = "http://localhost:8081/zzzz/upload/" + resultSet.getString("pFile");
					dto = new PDto(bId, id, bName, bTitle, bContent, bDate, bHit);
					dto.setPath(path);
					return dto;
				}
			}
			else if(num == 3)
			{
				if(resultSet.next())
				{	
					resultSet.next();
					resultSet.next();
					
					System.out.println("검사");
					int bId = resultSet.getInt("pId");
					String id = resultSet.getString("id");
					String bName = resultSet.getString("pName");
					String bTitle = resultSet.getString("pTitle");
					String bContent = resultSet.getString("pContent");
					Timestamp bDate = resultSet.getTimestamp("pDate");
					int bHit = resultSet.getInt("pHit");
					String path = "http://localhost:8081/zzzz/upload/" + resultSet.getString("pFile");
					dto = new PDto(bId, id, bName, bTitle, bContent, bDate, bHit);
					dto.setPath(path);
					return dto;
				}
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
	
}
