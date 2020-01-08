<%@page import="java.util.Iterator"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="org.json.simple.*"%>
<%@page import="org.json.simple.*"%>

<%
   String profileImg = (String)request.getAttribute("profileImg");
   System.out.println(profileImg);

   JSONObject jObject = new JSONObject();
 
   jObject.put("profileImg", profileImg);

   out.println(jObject);

%>
