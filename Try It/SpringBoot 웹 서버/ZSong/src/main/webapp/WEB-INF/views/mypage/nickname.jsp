<%@page import="java.util.Iterator"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="org.json.simple.*"%>
<%@page import="org.json.simple.*"%>

<%
   String Nickname = (String)request.getAttribute("Nickname");
   System.out.println(Nickname);

   JSONObject jObject = new JSONObject();
 
   jObject.put("Nickname", Nickname);

   out.println(jObject);

%>
