<%@page import="com.study.springboot.dao.MyPageDao"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="org.json.simple.*"%>
<%@ page import="com.study.springboot.dto.QnADto"%>
<%@ page import="java.util.*"%>
<%@ page import="java.text.*"%>
<%
	JSONObject jsonMain = new JSONObject();
	JSONArray jArray = new JSONArray();

	ArrayList<QnADto> dto = (ArrayList<QnADto>) request.getAttribute("list");

	for (int i = 0; i < dto.size(); i++) {
		JSONObject jObject = new JSONObject();

		jObject.put("question_num", dto.get(i).getQuestion_num());
		jObject.put("member_id", dto.get(i).getMember_id());
		jObject.put("manager_id", dto.get(i).getManager_id());
		jObject.put("question_title", dto.get(i).getQuestion_title());

		jObject.put("question_Content", dto.get(i).getQuestion_Content());
		jObject.put("question_Picture", dto.get(i).getQuestion_Picture());
		jObject.put("question_answer", dto.get(i).getQuestion_answer());
		jObject.put("question_divide", dto.get(i).getQuestion_divide());
		jObject.put("question_state", dto.get(i).getQuestion_state());
		
		SimpleDateFormat date = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		if (dto.get(i).getQuestion_date() != null)
			jObject.put("question_date", date.format(dto.get(i).getQuestion_date()));
		else
		jObject.put("question_date", dto.get(i).getQuestion_date());
		
		if (dto.get(i).getAnwser_date() != null)
			jObject.put("anwser_date", date.format(dto.get(i).getAnwser_date()));
		else
		jObject.put("anwser_date", dto.get(i).getAnwser_date());
		
		jArray.add(i, jObject);
	}
	jsonMain.put("List", jArray);
	out.println(jsonMain);
%>