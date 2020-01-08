package com.study.jsp;

import java.sql.*;
import java.util.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.study.dto.BDto;


public class MemberDAO {
   
	public static final int MEMBER_NONEXISTENT = 0;
	public static final int MEMBER_EXISTENT = 1;
	public static final int MEMBER_JOIN_FAIL = 0;
	public static final int MEMBER_JOIN_SUCCESS = 1;
	public static final int MEMBER_JOIN_BAN = 0;
	public static final int MEMBER_JOIN_UNBAN = 1;
	public static final int MEMBER_LOGIN_PW_NO_GOOD = 0;
	public static final int MEMBER_LOGIN_SUCCESS = 1;
	public static final int MEMBER_LOGIN_IS_NOT = -1;
	int listCount = 5;
	int pageCount = 5;
	private static MemberDAO instance = new MemberDAO();
	

   public MemberDAO() {
   }
   
   public static MemberDAO getInstance() {
	   return instance;
   }
   
   public int insertMember(MemberDTO dto) {
	   int ri = 0;
	   
	   Connection con = null;
	   PreparedStatement pstmt = null;
	   String query = "insert into members(id, pw, name, eMail, rDate, address, profile) values (?,?,?,?,?,?,?)";
	   
	   try {
		   con = getConnection();
		   pstmt = con.prepareStatement(query);
		   pstmt.setString(1, dto.getId());
		   pstmt.setString(2, dto.getPw());
		   pstmt.setString(3, dto.getName());
		   pstmt.setString(4, dto.geteMail());
		   pstmt.setTimestamp(5, dto.getrDate());
		   pstmt.setString(6, dto.getAddress());
		   pstmt.setString(7, "profile.png");
		   pstmt.executeUpdate();
		   ri = MemberDAO.MEMBER_JOIN_SUCCESS;  
	   }catch (Exception e) {
		e.printStackTrace();
	}finally {
		try {
			if(pstmt != null) pstmt.close();
			if(con != null) con.close();
		} catch (Exception e2) {
			e2.printStackTrace();
		}
	}
	   
	   return ri;
   }
   
   
   public int confirmId(String id) {
	   int ri = 0;
	   
	   Connection con = null;
	   PreparedStatement pstmt = null;
	   ResultSet set = null;
	   String query = "select id from members where id = ?";
	   
	   try {
		   con = getConnection();
		   pstmt = con.prepareStatement(query);
		   pstmt.setString(1, id);
		   set = pstmt.executeQuery();
		   
		   if(set.next()) {
			   ri = MemberDAO.MEMBER_EXISTENT;
		   }else {
			   ri = MemberDAO.MEMBER_NONEXISTENT;
		   }
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				set.close();
				pstmt.close();
				con.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	   
	   return ri;
   }
   
   
   public int userCheck(String id, String pw) {
	   int ri = 0;
	   String dbPw;
	   String ban;
	   Connection con = null;
	   PreparedStatement pstmt = null;
	   ResultSet set = null;
	   String query = "select pw from members where id = ?";
	   
	   try {
		   con = getConnection();
		   pstmt = con.prepareStatement(query);
		   pstmt.setString(1, id);
		   set = pstmt.executeQuery();
		   
		   if(set.next()) {
			   dbPw = set.getString("pw");
			   
			   if(dbPw.equals(pw)) {
				   System.out.println("login ok");
				   ri = MemberDAO.MEMBER_JOIN_SUCCESS;
			   }else {
				   System.out.println("login fail");
				   ri = MemberDAO.MEMBER_LOGIN_PW_NO_GOOD;
			   }
		   }else {
			   System.out.println("login fail");
			   ri = MemberDAO.MEMBER_LOGIN_IS_NOT;
		   }
		   
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				set.close();
				pstmt.close();
				con.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	   
	   return ri;
   }
   
   public int banCheck(String id) {
	   int ri = 0;
	   String ban;
	   Connection con = null;
	   PreparedStatement pstmt = null;
	   ResultSet set = null;
	   String query = "select ban from members where id = ?";
	   
	   try {
		   con = getConnection();
		   pstmt = con.prepareStatement(query);
		   pstmt.setString(1, id);
		   set = pstmt.executeQuery();
		   
		   if(set.next()) {
			   ban = set.getString("ban");
			   
			   if(ban.equals("1")) {
				   ri = MemberDAO.MEMBER_JOIN_BAN;
			   }else {
				   ri = MemberDAO.MEMBER_JOIN_UNBAN;
			   }
		   }
		   
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				set.close();
				pstmt.close();
				con.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	   
	   return ri;
   }
   
   public MemberDTO getMember(String id) {

	   Connection con = null;
	   PreparedStatement pstmt = null;
	   ResultSet set = null;
	   String query = "select * from members where id = ?";
	   MemberDTO dto = null;
	   
	   try {
		   con = getConnection();
		   pstmt = con.prepareStatement(query);
		   pstmt.setString(1, id);
		   set = pstmt.executeQuery();
		   
		   if(set.next()) {
			   	dto = new MemberDTO();
			   	dto.setId(set.getString("id"));
			   	dto.setPw(set.getString("pw"));
			   	dto.setName(set.getString("name"));
			   	dto.seteMail(set.getString("email"));
			   	dto.setrDate(set.getTimestamp("rDate"));
			   	dto.setAddress(set.getString("address"));
			   	dto.setState("state");
		   }
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				set.close();
				pstmt.close();
				con.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	   
	   return dto;
   }

   
   public int updateMember(MemberDTO dto) {
	   int ri = 0;
	   
	   Connection con = null;
	   PreparedStatement pstmt = null;
	   ResultSet set = null;
	   String query = "update members set pw=?, eMail=?, address=? where id=?";
	   
	   try {
		   con = getConnection();
		   pstmt = con.prepareStatement(query);
		   pstmt.setString(1, dto.getPw());
		   pstmt.setString(2, dto.geteMail());
		   pstmt.setString(3, dto.getAddress());
		   pstmt.setString(4, dto.getId());
		   ri = pstmt.executeUpdate();
		   
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				pstmt.close();
				con.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	   
	   return ri;
   }
   
   public int banMember(MemberDTO dto) {
	   int ri = 0;
	   
	   Connection con = null;
	   PreparedStatement pstmt = null;
	   ResultSet set = null;
	   String query = "update members set ban=? where id=?";
	   
	   try {
		   con = getConnection();
		   pstmt = con.prepareStatement(query);
		   pstmt.setString(1, dto.getBan());
		   pstmt.setString(2, dto.getId());
		   ri = pstmt.executeUpdate();
		   
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				pstmt.close();
				con.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	   
	   return ri;
   }
   
   public int deleteMember(String id) {
	   int ri = 0;
	   
	   Connection con = null;
	   PreparedStatement pstmt = null;
	   String query = "delete from members where id = ?";
	   
	   try {
		   con = getConnection();
		   pstmt = con.prepareStatement(query);
		   pstmt.setString(1, id);
		   pstmt.executeQuery();
		   
	   }catch (Exception e) {
		e.printStackTrace();
	}finally {
		try {
			if(pstmt != null) pstmt.close();
			if(con != null) con.close();
		} catch (Exception e2) {
			e2.printStackTrace();
		}
	}
	   
	   return ri;
   }
   
   
   public ArrayList<MemberDTO> getAllMember(int curPage) {
	   ArrayList<MemberDTO> dtos = new ArrayList<MemberDTO>();
	   Connection con = null;
	   PreparedStatement pstmt = null;
	   ResultSet set = null;
	   
//	   String query = "select * from members";
	   
		String query = "select * " +
				" from ( " + 
				" 	select rownum num, A.* " + 
				"		from ( " + 
				"			select * " + 
				"				from members " +
				"			order by id asc ) A " +
				"	where rownum <= ? ) B " + 
				"where B.num >= ?";
	   
	   
	   int nStart = (curPage - 1) * listCount + 1;
	   int nEnd = (curPage - 1) * listCount + listCount;
		
	   try {
		   con = getConnection();
		   pstmt = con.prepareStatement(query);
		   pstmt.setInt(1, nEnd);
		   pstmt.setInt(2, nStart);
		   set = pstmt.executeQuery();
		   
		   while(set.next()) {
			   	String id = set.getString("id");
				String pw = set.getString("pw");
				String name = set.getString("name");
				String eMail = set.getString("eMail");
				Timestamp rDate = set.getTimestamp("rDate");
				String address = set.getString("address");
				String ban = set.getString("ban");
				String state = set.getString("state");
				
				System.out.println(id);
				
				MemberDTO dto = new MemberDTO(id, pw, name, eMail, rDate, address, ban, state);
				dtos.add(dto);
		   }
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				set.close();
				pstmt.close();
				con.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	   
	   return dtos;
   }
   
   public ArrayList<MemberDTO> findMember(int curPage, String option, String text) {
	   ArrayList<MemberDTO> dtos = new ArrayList<MemberDTO>();
	   Connection con = null;
	   PreparedStatement pstmt = null;
	   ResultSet set = null;
	   int nStart = (curPage - 1) * listCount + 1;
	   int nEnd = (curPage - 1) * listCount + listCount;
	   
	   String query="";
	   
	   if(option.equals("아이디")) {

		   query = "select * " +
					" from ( " + 
					" 	select rownum num, A.* " + 
					"		from ( " + 
					"			select * " + 
					"				from members " +
					"				where id like '%' || ? ||'%'" +
					"			order by id asc ) A " +
					"	where rownum <= ? ) B " + 
					"where B.num >= ?";
		   
	   }else if(option.equals("이름")) {
		   
		   query = "select * " +
					" from ( " + 
					" 	select rownum num, A.* " + 
					"		from ( " + 
					"			select * " + 
					"				from members " +
					"				where name like '%' || ? ||'%'" +
					"			order by id asc ) A " +
					"	where rownum <= ? ) B " + 
					"where B.num >= ?";
	   }
	   
		

		
	   try {
		   con = getConnection();
		   pstmt = con.prepareStatement(query);
		   pstmt.setString(1, text);
		   pstmt.setInt(2, nEnd);
		   pstmt.setInt(3, nStart);
		   set = pstmt.executeQuery();
		   
		   	while(set.next()) {
				String id = set.getString("id");
				String pw = set.getString("pw");
				String name = set.getString("name");
				String eMail = set.getString("eMail");
				Timestamp rDate = set.getTimestamp("rDate");
				String address = set.getString("address");
				String ban = set.getString("ban");
				String state = set.getString("state");
					
				System.out.println(id);
				
				MemberDTO dto = new MemberDTO(id, pw, name, eMail, rDate, address, ban, state);
				dtos.add(dto);
		  }
		  
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				set.close();
				pstmt.close();
				con.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	   
	   return dtos;
   }
   
   
   //채팅 친구찾기
   public int findID(String id) {
	   int ri = 0;
	   
	   Connection con = null;
	   PreparedStatement pstmt = null;
	   ResultSet set = null;
	   String query = "select id from members where id = ?";
	   
	   try {
		   con = getConnection();
		   pstmt = con.prepareStatement(query);
		   pstmt.setString(1, id);
		   set = pstmt.executeQuery();
		   
		   if(set.next()) {
			   ri = 0;
		   }else {
			   ri = -1;
		   }
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				set.close();
				pstmt.close();
				con.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	   
	   return ri;
   }
   
   public int profile(String id, String userProfile) {
	   int ri = 0;

	   Connection con = null;
	   PreparedStatement pstmt = null;
	   ResultSet set = null;
	   String query = "update members set profile = ? where id = ?";
	   
	   try {
		   con = getConnection();
		   pstmt = con.prepareStatement(query);
		   pstmt.setString(1, userProfile);
		   pstmt.setString(2, id);
		   return pstmt.executeUpdate();
		   
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				pstmt.close();
				con.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	   
	   return -1;
   }
   
   public String getprofile(String id) {
	   String filename = null;
	   
	   Connection con = null;
	   PreparedStatement pstmt = null;
	   ResultSet set = null;
	   String query = "select profile from members where id = ?";
	   
	   try {
		   con = getConnection();
		   pstmt = con.prepareStatement(query);
		   pstmt.setString(1, id);
		   set = pstmt.executeQuery();
		   
		   if(set.next())
		   {
			   filename = set.getString(1);
		   }		   
		   
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				pstmt.close();
				con.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	   
	   return filename;
   }
   
   public String getprofilePath(String id) {
	   Connection con = null;
	   PreparedStatement pstmt = null;
	   ResultSet set = null;
	   String query = "select profile from members where id = ?";
	   
	   try {
		   con = getConnection();
		   pstmt = con.prepareStatement(query);
		   pstmt.setString(1, id);
		   set = pstmt.executeQuery();
		   
		   if(set.next())
		   {
			   if(set.getString(1).equals(null))
			   {
				   return "http://localhost:8081/zzzz/img/profile.png";
			   }
			   else
			   {
				   return "http://localhost:8081/zzzz/upload/" + set.getString(1);
			   }
		   }		   
		   
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				pstmt.close();
				con.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	   
	   return "http://localhost:8081/zzzz/img/profile.png";
   }
   
   
   private Connection getConnection() {
	   
	   Context context = null;
	   DataSource dataSource = null;
	   Connection con = null;
	   
	   try {
		   context = new InitialContext();
		   dataSource = (DataSource)context.lookup("java:comp/env/jdbc/Oracle11g");
		   con = dataSource.getConnection();
	   }catch (Exception e) {
		   e.printStackTrace();
	}
	   return con;
   }
   
   
}