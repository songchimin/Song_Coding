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

	ArrayList<AndroidChatListDto> array = (ArrayList<AndroidChatListDto>) request.getAttribute("AndroidChatList");

	for (int i = 0; i < array.size(); i++) {
		JSONObject jObject = new JSONObject();
		jObject.put("chat_room_num", array.get(i).getChallenge_num());
		jObject.put("chat_room_title", array.get(i).getChallenge_title());
		jObject.put("chat_room_image", array.get(i).getChallenge_first_image());
		jObject.put("chat_room_participant_count", array.get(i).getChat_room_participant_count());
		jArray.add(i, jObject);
	}
	jsonMain.put("AndroidChatList", jArray);
	out.println(jsonMain);
%>
