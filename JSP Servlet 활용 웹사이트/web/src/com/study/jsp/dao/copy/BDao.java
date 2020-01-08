package com.study.jsp.dao.copy;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import javax.websocket.Session;

import com.study.dto.BDto;
import com.study.dto.CDto;
import com.study.dto.RDto;
import com.study.jsp.BPageInfo;
import com.study.jsp.MemberDAO;

import chat.ChatDTO;

public class BDao {
	
	private static BDao instance= new BDao();
	DataSource dataSource;
	
	int listCount = 10;
	int pageCount = 10;
	
	public BDao() {
		try {
			Context context = new InitialContext();
			dataSource = (DataSource)context.lookup("java:comp/env/jdbc/Oracle11g");
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static BDao getInstance() {
		return instance;
	}
	
	public void write(String id, String bName, String bTitle, String bContent, String boardFile, String boardRealFile) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = dataSource.getConnection();
			String query = "insert into mvc_board " +
						   " (bId, id, bName, bTitle, bContent, bHit, bFile, bRealFile, bGroup, bStep, bIndent) " +
						   " values " +
						   " (mvc_board_seq.nextval, ?, ?, ?, ?, 0, ?, ?, mvc_board_seq.currval, 0, 0 )";
			
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
	
	public void writeComment(String bwid, String cid, String cname, String content) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = dataSource.getConnection();
			
			String query = "insert into commentDB " +
						   " (bwid, cno, cid, cname, content) " +
						   " values " +
						   " (?, commentDB_seq.nextval, ?, ?, ?)";
			
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, bwid);
			pstmt.setString(2, cid);
			pstmt.setString(3, cname);
			pstmt.setString(4, content);
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
	
	public void deleteComment(String cid) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = dataSource.getConnection();
			
			String query = "delete from commentDB where cno = ? ";
			
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, cid);
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
	
	
	public ArrayList<BDto> list(int curPage){
		ArrayList<BDto> dtos = new ArrayList<BDto>();
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
							"				from mvc_board " +
							"			order by bGroup desc, bStep asc ) A " +
							"	where rownum <= ? ) B " + 
							"where B.num >= ?";
			
			
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, nEnd);
			pstmt.setInt(2, nStart);
			resultSet = pstmt.executeQuery();
			
			while(resultSet.next())
			{
				System.out.println("검사");
				int bId = resultSet.getInt("bId");
				String id = resultSet.getString("id");
				String bName = resultSet.getString("bName");
				String bTitle = resultSet.getString("bTitle");
				String bContent = resultSet.getString("bContent");
				Timestamp bDate = resultSet.getTimestamp("bDate");
				int bHit = resultSet.getInt("bHit");
				int bGroup = resultSet.getInt("bGroup");
				int bStep = resultSet.getInt("bStep");
				int bIndent = resultSet.getInt("bIndent");
				
				
				String query2 = "select count(*) from good where bId = ? ";
		        pstmt2 = con.prepareStatement(query2);
		        pstmt2.setInt(1, bId);
		        resultSet2 = pstmt2.executeQuery();
		        
		        int like_c=0;
		        
		        if (resultSet2.next()) {
		           like_c = resultSet2.getInt(1);
		        }
		        
		        String profile = new MemberDAO().getprofilePath(id);
		        
				BDto dto = new BDto(bId, id, bName, bTitle, bContent, bDate, bHit,
									bGroup, bStep, bIndent);
				
				dto.setProfile(profile);
				dto.setLike_c(like_c);
				
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
	
	
	//댓글 리스트 불러오기
	public ArrayList<CDto> commentlist(String bid){
		ArrayList<CDto> dtos = new ArrayList<CDto>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet resultSet = null;
		
		try {
			con = dataSource.getConnection();
			
			String query = "select * from commentDB where bwid = ? order by cno asc";
			
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, bid);
			resultSet = pstmt.executeQuery();
			
			while(resultSet.next())
			{
				System.out.println("검사");
				int bWid = resultSet.getInt("bWid");
				int cNo = resultSet.getInt("cNo");
				String cId = resultSet.getString("cId");
				String cName = resultSet.getString("cName");
				String content = resultSet.getString("content");
				Timestamp cDate = resultSet.getTimestamp("cDate");
				int ss = resultSet.getInt("ss");
				
				CDto dto = new CDto(bWid, cNo, cId, cName, content, cDate, ss);
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
	
	
	public BDto contentView(String strID, String id) {
		upHit(strID);
		
		BDto dto = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet resultSet = null;
		String like_id = "";
		
		try {
		con = dataSource.getConnection();
			
		String query = "select count(*) from good where bId = ? ";
        pstmt = con.prepareStatement(query);
        pstmt.setInt(1, Integer.parseInt(strID));
        resultSet = pstmt.executeQuery();
        int like_c=0;
        
        if (resultSet.next()) {
           like_c = resultSet.getInt(1);         
        }
        
        
        query = "select * from good where bId = ? and like_user = ? " ;
        pstmt = con.prepareStatement(query);
        pstmt.setInt(1, Integer.parseInt(strID));
        pstmt.setString(2, id);
        resultSet = pstmt.executeQuery();
        
        if (resultSet.next()) {
           like_id = resultSet.getString(2);
        }

        
			query = "select * from mvc_board where bId = ?";
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, Integer.parseInt(strID));
			resultSet = pstmt.executeQuery();
			
			if(resultSet.next())
			{
				int bId = resultSet.getInt("bId");
				String uid = resultSet.getString("id");
				String bName = resultSet.getString("bName");
				String bTitle = resultSet.getString("bTitle");
				String bContent = resultSet.getString("bContent");
				Timestamp bDate = resultSet.getTimestamp("bDate");
				int bHit = resultSet.getInt("bHit");
				String bFile = resultSet.getString("bFile");
				String bRealFile = resultSet.getString("bRealFile");
				int bGroup = resultSet.getInt("bGroup");
				int bStep = resultSet.getInt("bStep");
				int bIndent = resultSet.getInt("bIndent");

				dto = new BDto(bId, uid, bName, bTitle, bContent, bDate, bHit, 
					bFile ,bRealFile, bGroup, bStep, bIndent,like_id, like_c);
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
	

	
	
	public void modify(String bId, String bName, String bTitle, String bContent) {
		Connection con = null;
		PreparedStatement pstmt = null;

		String query = "update mvc_board " +
				  		"	set bName = ?, " +
				  		"		bTitle = ?, " +
				  		"		bContent = ? " +
				  		"where bId = ?";
		
		try {
			con = dataSource.getConnection();			
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, bName);
			pstmt.setString(2, bTitle);
			pstmt.setString(3, bContent);
			pstmt.setString(4, bId);
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
	
	private void upHit(String bId) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = dataSource.getConnection();	
			String query = "update mvc_board set bHit = bHit + 1 where bId = ?";
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
	
	public void downHit(String bId) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = dataSource.getConnection();	
			String query = "update mvc_board set bHit = bHit - 1 where bId = ?";
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
	
	public void heart(String bId, String id) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = dataSource.getConnection();
			String query = "insert into favoriteDB " +
						   " (bid, id) " +
						   " values (?, ?)";

			pstmt = con.prepareStatement(query);
			pstmt.setString(1, bId);
			pstmt.setString(2, id);
			
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
	
	public BDto likeCheck (String bId,String id){

	      BDto dto = null;
	      Connection con = null;
	      PreparedStatement pstmt = null;
	      ResultSet resultSet = null;
	      
	      try {
	         
	         con = dataSource.getConnection();
	         
	         String query = "select * from good where bId = ? and like_user = ?  " ;
	         pstmt = con.prepareStatement(query);
	         pstmt.setString(1,bId);
	         pstmt.setString(2,id);
	         resultSet = pstmt.executeQuery();
	         
	         if(resultSet.next()) {
	            
	            query = "delete from good where bId = ? and like_user = ? ";
	            pstmt = con.prepareStatement(query);
	            pstmt.setString(1,bId);
	            pstmt.setString(2,id);
	            int rn = pstmt.executeUpdate();
	            
	         } else {
	            
	         query = "insert into good (bId,like_user) values (?,?) ";
	         
	         pstmt = con.prepareStatement(query);
	         pstmt.setString(1,bId);
	         pstmt.setString(2, id);
	         int rn = pstmt.executeUpdate();
	         
	         }
	         
	         
	      } catch (Exception e) {
	         e.printStackTrace();
	      } finally {

	         try {
	            if (pstmt != null)
	               pstmt.close();
	            if (con != null)
	               con.close();
	         } catch (Exception e2) {
	            e2.printStackTrace();
	         }

	      }
	      return dto;

	   }
	
	
	public void delete(String bId) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = dataSource.getConnection();	
			String query = "delete from mvc_board where bId = ?";
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, Integer.parseInt(bId));
			
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
	
	
	public BDto reply_view(String str) {
		BDto dto = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet resultSet = null;
		
		try {
			con = dataSource.getConnection();
			
			String query = "select * from mvc_board where bId = ?";
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, Integer.parseInt(str));
			resultSet = pstmt.executeQuery();
			
			while(resultSet.next())
			{
				int bId = resultSet.getInt("bId");
				String id = resultSet.getString("id");
				String bName = resultSet.getString("bName");
				String bTitle = resultSet.getString("bTitle");
				String bContent = resultSet.getString("bContent");
				Timestamp bDate = resultSet.getTimestamp("bDate");
				int bHit = resultSet.getInt("bHit");
				int bGroup = resultSet.getInt("bGroup");
				int bStep = resultSet.getInt("bStep");
				int bIndent = resultSet.getInt("bIndent");
				
				dto = new BDto(bId, id, bName, bTitle, bContent, bDate, bHit,
									bGroup, bStep, bIndent);
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
	
	
	public void reply(String bId, String bName, String bTitle, String bContent,
					String bGroup, String bStep, String bIndent) 
	{
		replyShape(bGroup, bStep);
		
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = dataSource.getConnection();
			String query = "insert into mvc_board " +
						   " (bId, bName, bTitle, bContent, bGroup, bStep, bIndent) " +
						   " values (mvc_board_seq.nextval, ?, ?, ?, ?, ?, ? )";
			pstmt = con.prepareStatement(query);
			
			pstmt.setString(1, bName);
			pstmt.setString(2, bTitle);
			pstmt.setString(3, bContent);
			pstmt.setInt(4, Integer.parseInt(bGroup));
			pstmt.setInt(5, Integer.parseInt(bStep)+1);
			pstmt.setInt(6, Integer.parseInt(bIndent)+1);
			
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
	
	private void replyShape(String strGroup, String strStep) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = dataSource.getConnection();
			String query = "update mvc_board " +
						   " set bStep = bStep + 1 " +
						   " where bGroup = ? and bStep > ?";
			
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, Integer.parseInt(strGroup));
			pstmt.setInt(2, Integer.parseInt(strStep));
			
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
		
		int listCount = 10;	//한페이지당 보여줄 게시물 갯수
		int pageCount = 10; //하단에 보여줄 리스트 갯수
		
		// 총 게시물 갯수
		int totalCount = 0;
		try {
			con = dataSource.getConnection();
			
			String query = "select count(*) as total from mvc_board";
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
	
	public BPageInfo articlePage3(int curPage) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet resultSet = null;
		
		int listCount = 5;	//한페이지당 보여줄 게시물 갯수
		int pageCount = 5; //하단에 보여줄 리스트 갯수
		
		// 총 게시물 갯수
		int totalCount = 0;
		try {
			con = dataSource.getConnection();
			
			String query = "select count(*) as total from members";
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
	
	public ArrayList<BDto> findlist(int curPage, String option, String text){
		ArrayList<BDto> dtos = new ArrayList<BDto>();
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
						"				from mvc_board " +
						"				where bTitle like '%' ||? ||'%' " +
						"			order by bGroup desc, bStep asc ) A " +
						"	where rownum <= ? ) B " + 
						"where B.num >= ?";
			}else if(option.equals("내용")) {
				query = "select * " +
						" from ( " + 
						" 	select rownum num, A.* " + 
						"		from ( " + 
						"			select * " + 
						"				from mvc_board " +
						"				where bContent like '%' ||? ||'%' " +
						"			order by bGroup desc, bStep asc ) A " +
						"	where rownum <= ? ) B " + 
						"where B.num >= ?";
			}else if(option.equals("작성자")) {
				query = "select * " +
						" from ( " + 
						" 	select rownum num, A.* " + 
						"		from ( " + 
						"			select * " + 
						"				from mvc_board " +
						"				where bName like '%' ||? ||'%' " +
						"			order by bGroup desc, bStep asc ) A " +
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
						"				from mvc_board " +
						"				where bTitle like '%' ||? ||'%' " +
						"			order by bGroup desc, bStep asc ) A " +
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
				int bId = resultSet.getInt("bId");
				String id = resultSet.getString("id");
				String bName = resultSet.getString("bName");
				String bTitle = resultSet.getString("bTitle");
				String bContent = resultSet.getString("bContent");
				Timestamp bDate = resultSet.getTimestamp("bDate");
				int bHit = resultSet.getInt("bHit");
				int bGroup = resultSet.getInt("bGroup");
				int bStep = resultSet.getInt("bStep");
				int bIndent = resultSet.getInt("bIndent");
						
				String profile = new MemberDAO().getprofilePath(id);
					
				BDto dto = new BDto(bId, id, bName, bTitle, bContent, bDate, bHit,
											bGroup, bStep, bIndent);
				dto.setProfile(profile);
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
	
	
	public BPageInfo articlePage2(int curPage, String findoption, String text) {
		
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
					query = "select count(*) as total from mvc_board where bTitle like '%' ||? ||'%'";
				}else if(findoption.equals("내용")) {
					query = "select count(*) as total from mvc_board where bContent like '%' ||? ||'%'";
				}else if(findoption.equals("작성자")) {
					query = "select count(*) as total from mvc_board where bName like '%' ||? ||'%'";
				}
				pstmt = con.prepareStatement(query);
				pstmt.setString(1, text);
			}
			else {
				query = "select count(*) as total from mvc_board";
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
	
//	public BDto findcontent(String strID, String option, String text) {
//		
//		BDto dto = null;
//		Connection con = null;
//		PreparedStatement pstmt = null;
//		ResultSet resultSet = null;
//		String query=null;
//		
//		try {
//			con = dataSource.getConnection();
//			
//			query = "select * from mvc_board where bId = ?";
//			pstmt = con.prepareStatement(query);
//			pstmt.setInt(1, Integer.parseInt(strID));
//			resultSet = pstmt.executeQuery();
//			
//			while(resultSet.next())
//			{
//				if(option.equals("제목"))
//				{
//					if(resultSet.getString("bTitle").contains(text))
//					{
//						int bId = resultSet.getInt("bId");
//						String id = resultSet.getString("id");
//						String bName = resultSet.getString("bName");
//						String bTitle = resultSet.getString("bTitle");
//						String bContent = resultSet.getString("bContent");
//						Timestamp bDate = resultSet.getTimestamp("bDate");
//						int bHit = resultSet.getInt("bHit");
//						int bGroup = resultSet.getInt("bGroup");
//						int bStep = resultSet.getInt("bStep");
//						int bIndent = resultSet.getInt("bIndent");
//
//						dto = new BDto(bId, id, bName, bTitle, bContent, bDate, bHit,
//							bGroup, bStep, bIndent);	
//					}
//				}
//
//			}
//
//
//		}catch (Exception e) {
//			e.printStackTrace();
//		}finally {
//			try {
//				if(resultSet != null) resultSet.close();
//				if(pstmt != null) pstmt.close();
//				if(con != null) con.close();
//			} catch (Exception e2) {
//				e2.printStackTrace();
//			}
//		}
//		
//		return dto;
//	}
	
	public ArrayList<ChatDTO> getChatListByRecent(String fromID, String toID, int number){
		ArrayList<ChatDTO> ChatList = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet resultSet = null;
		
		String query = "select * from chat where ((fromID = ? and toID = ?) or ( fromID = ? and toID = ? )) and chatID > (select max(chatID) - ? from chat where (fromID = ? and toID = ?) or ( fromID = ? and toID = ? )) order by chatTime";
		
		try {
			con = dataSource.getConnection();
			pstmt = con.prepareStatement(query);
			
			pstmt.setString(1, fromID);
			pstmt.setString(2, toID);
			pstmt.setString(3, toID);
			pstmt.setString(4, fromID);
			pstmt.setInt(5, number);
			pstmt.setString(6, fromID);
			pstmt.setString(7, toID);
			pstmt.setString(8, toID);
			pstmt.setString(9, fromID);
			resultSet = pstmt.executeQuery();
			ChatList = new ArrayList<ChatDTO>();
			
			while(resultSet.next()) {
				
				ChatDTO chat = new ChatDTO();
				chat.setChatID(resultSet.getInt("chatID"));
				chat.setFromID(resultSet.getString("fromID").replaceAll(" ", "&nbsp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>;"));
				chat.setToID(resultSet.getString("toID").replaceAll(" ", "&nbsp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>;"));
				chat.setChatContent(resultSet.getString("ChatContent").replaceAll(" ", "&nbsp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>;"));
				chat.setChatTime(resultSet.getTimestamp("chatTime"));
				
				ChatList.add(chat);
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
		
		return ChatList;
	}
	
	public ArrayList<ChatDTO> getChatListById(String fromID, String toID, String chatID){
		ArrayList<ChatDTO> ChatList = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet resultSet = null;
		
		String query = "select * from chat where ((fromID = ? and toID = ?) or (fromID = ? and toID = ?)) and chatID > ? order by chatTime";
		
		try {
			con = dataSource.getConnection();
			pstmt = con.prepareStatement(query);
			
			pstmt.setString(1, fromID);
			pstmt.setString(2, toID);
			pstmt.setString(3, toID);
			pstmt.setString(4, fromID);
			pstmt.setInt(5, Integer.parseInt(chatID));
			
			resultSet = pstmt.executeQuery();
			ChatList = new ArrayList<ChatDTO>();
			
			while(resultSet.next()) {
				
				ChatDTO chat = new ChatDTO();
				chat.setChatID(resultSet.getInt("chatID"));
				chat.setFromID(resultSet.getString("fromID").replaceAll(" ", "&nbsp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>;"));
				chat.setToID(resultSet.getString("toID").replaceAll(" ", "&nbsp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>;"));
				chat.setChatContent(resultSet.getString("ChatContent").replaceAll(" ", "&nbsp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>;"));
				chat.setChatTime(resultSet.getTimestamp("chatTime"));
				
				ChatList.add(chat);
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
		
		return ChatList;
	}
	
	public int submit(String fromID, String toID, String chatContent){
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet resultSet = null;
		
		String query = "insert into chat(chatID, fromID, toID, chatContent, chatRead) values(mvc_chat_seq.nextval, ?, ?, ?, 0)";
		
		try {
			con = dataSource.getConnection();
			pstmt = con.prepareStatement(query);
			
			pstmt.setString(1, fromID);
			pstmt.setString(2, toID);
			pstmt.setString(3, chatContent);
			
			return pstmt.executeUpdate();
			
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
		
		return -1;
	}
	
	public int readChat(String fromID, String toID) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet resultSet = null;
		String query = "update chat set chatRead = 1 where (fromID = ? and toID = ?)";
		
		try {
			con = dataSource.getConnection();
			pstmt = con.prepareStatement(query);	
			pstmt.setString(1, toID);
			pstmt.setString(2, fromID);
			
			return pstmt.executeUpdate();			
		} catch (Exception e) {
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
		return -1;
	}
	
	
	public int getAllUnreadChat(String userID) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet resultSet = null;
		String query = "select count(chatID) from chat where toID = ? and chatRead = 0";
		
		try {
			con = dataSource.getConnection();
			pstmt = con.prepareStatement(query);	
			pstmt.setString(1, userID);
			resultSet = pstmt.executeQuery();
			
			if(resultSet.next()) {
				return resultSet.getInt("count(chatID)");
			}
			return 0 ;
		} catch (Exception e) {
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
		return -1;
	}
	
	
	public int getUnreadChat(String fromID, String toID) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet resultSet = null;
		String query = "select count(chatID) from chat where fromID = ? and toID = ? and chatRead = 0";
		
		try {
			con = dataSource.getConnection();
			pstmt = con.prepareStatement(query);	
			pstmt.setString(1, fromID);
			pstmt.setString(2, toID);
			resultSet = pstmt.executeQuery();
			
			if(resultSet.next()) {
				return resultSet.getInt("count(chatID)");
			}
			return 0 ;
		} catch (Exception e) {
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
		return -1;
	}
	
	
	
	public ArrayList<ChatDTO> getBox(String userID){
		ArrayList<ChatDTO> ChatList = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet resultSet = null;
		
		String query = "select * from chat where chatID IN(select MAX(chatID) from chat where toID = ? or fromID = ? group by fromID,  toID)";
		
		try {
			con = dataSource.getConnection();
			pstmt = con.prepareStatement(query);
			
			pstmt.setString(1, userID);
			pstmt.setString(2, userID);
			
			resultSet = pstmt.executeQuery();
			ChatList = new ArrayList<ChatDTO>();
			
			while(resultSet.next()) {
				
				ChatDTO chat = new ChatDTO();
				chat.setChatID(resultSet.getInt("chatID"));
				chat.setFromID(resultSet.getString("fromID").replaceAll(" ", "&nbsp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>;"));
				chat.setToID(resultSet.getString("toID").replaceAll(" ", "&nbsp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>;"));
				chat.setChatContent(resultSet.getString("ChatContent").replaceAll(" ", "&nbsp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>;"));
				chat.setChatTime(resultSet.getTimestamp("chatTime"));
				
				ChatList.add(chat);
			}
			for(int i = 0 ; i < ChatList.size(); i++ ) {
				ChatDTO x = ChatList.get(i);
				for(int j = 0 ; j < ChatList.size(); j++) {
					ChatDTO y = ChatList.get(j);
					if(x.getFromID().equals(y.getToID()) && x.getToID().equals(y.getFromID())) {
						if(x.getChatID() < y.getChatID())
						{
							ChatList.remove(x);
							i--;
							break;
						}else {
							ChatList.remove(y);
						}
					}
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
		
		return ChatList;
	}
	
	
	
	public String getFile(String boardId){
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet resultSet = null;

		try {
			con = dataSource.getConnection();
			
			String query = "select bFile from mvc_board where bId = ?";
			
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, boardId);
			resultSet = pstmt.executeQuery();
			
			while(resultSet.next())
			{
				return resultSet.getString("bFile");
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
	
	public ArrayList<RDto> BRank(String start, String end) {
		ArrayList<RDto> rdto = new ArrayList<RDto>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet resultSet = null;
//		PreparedStatement pstmt2 = null;
//		ResultSet resultSet2 = null;
		RDto dto = null;
		
		try {
			con = dataSource.getConnection();	
			String query = "select id, bname, count(*) from mvc_board where bdate between TO_DATE( ? ,'YYYY-MM-DD') and TO_DATE( ? ,'YYYY-MM-DD') + 0.99999 group by id, bname order by count(*) desc";
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, start);
			pstmt.setString(2, end);
			resultSet = pstmt.executeQuery();
			
			while(resultSet.next()) {
				String id = resultSet.getString(1);
				String name = resultSet.getString(2);
				int count = resultSet.getInt(3);
				
				dto = new RDto(id, name, count);
				dto.setStart(start);
				dto.setEnd(end);
				rdto.add(dto);
			}

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
		return rdto;
	}
	
	public ArrayList<RDto> CRank(String start, String end) {
		ArrayList<RDto> rdto = new ArrayList<RDto>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet resultSet = null;
//		PreparedStatement pstmt2 = null;
//		ResultSet resultSet2 = null;
		RDto dto = null;
		
		try {
			con = dataSource.getConnection();	
			String query = "select cid, cname, count(*) from commentDB where cdate between TO_DATE( ? ,'YYYY-MM-DD') and TO_DATE( ? ,'YYYY-MM-DD') + 0.99999 group by cid, cname order by count(*) desc";
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, start);
			pstmt.setString(2, end);
			resultSet = pstmt.executeQuery();
			
			while(resultSet.next()) {
				String id = resultSet.getString(1);
				String name = resultSet.getString(2);
				int count = resultSet.getInt(3);
				
				dto = new RDto(id, name, count);
				dto.setStart(start);
				dto.setEnd(end);
				rdto.add(dto);
			}

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
		return rdto;
	}
	
	public ArrayList<RDto> PRank(String start, String end) {
		ArrayList<RDto> rdto = new ArrayList<RDto>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet resultSet = null;
//		PreparedStatement pstmt2 = null;
//		ResultSet resultSet2 = null;
		RDto dto = null;
		
		try {
			con = dataSource.getConnection();
			String query = "select id, pname, count(*) from mvc_pictureboard where pdate between TO_DATE(?,'YYYY-MM-DD') and TO_DATE(?,'YYYY-MM-DD') + 0.99999 group by id, pname order by count(*) desc";
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, start);
			pstmt.setString(2, end);
			resultSet = pstmt.executeQuery();
			
			while(resultSet.next()) {
				String id = resultSet.getString(1);
				String name = resultSet.getString(2);
				int count = resultSet.getInt(3);
				
				dto = new RDto(id, name, count);
				dto.setStart(start);
				dto.setEnd(end);
				rdto.add(dto);
			}

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
		return rdto;
	}
	
	
	public String getRealFile(String boardId){
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet resultSet = null;

		try {
			con = dataSource.getConnection();
			
			String query = "select bRealFile from mvc_board where bId = ?";
			
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, boardId);
			resultSet = pstmt.executeQuery();
			
			while(resultSet.next())
			{
				return resultSet.getString("bRealFile");
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
}
