<%@page import="com.study.springboot.dto.AndroidChatListDto"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="org.json.simple.*"%>
<%@ page import="java.util.*"%>
<%@ page import="java.text.*"%>
<%@ page import="com.study.springboot.dto.AndroidChatListDto"%>

<%
	JSONObject jsonMain = new JSONObject();
	JSONArray jArray = new JSONArray();

	String nickname = (String)request.getAttribute("nickname");
	
	JSONObject jObject = new JSONObject();
	
	jObject.put("nickname", nickname);
	jArray.add(0,jObject);
	
	jsonMain.put("nickname", jArray);
	
	out.println(jsonMain);
%>
