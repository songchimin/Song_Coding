<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>   
<%@page import="org.json.simple.*"%>
<%@ page import="com.study.springboot.dto.CommentDto" %>

<%@ page import="java.util.*"%>
<%@ page import="java.text.*"%>
<%
JSONObject jsonMain = new JSONObject();
JSONArray jArray = new JSONArray();


ArrayList<CommentDto> array =(ArrayList<CommentDto>) request.getAttribute("list");

for(int i=0;i<array.size();i++){

	JSONObject jObject = new JSONObject();
	
	jObject.put("comment_num", array.get(i).getComment_num());
	jObject.put("challenge_num",array.get(i).getChallenge_num());
	jObject.put("member_id", array.get(i).getMember_id());
	jObject.put("comment_content", array.get(i).getComment_content());
	SimpleDateFormat date = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	jObject.put("comment_date", date.format(array.get(i).getCommment_date()));
	
	jArray.add(i, jObject);
}
jsonMain.put("List", jArray);
out.println(jsonMain);
%>