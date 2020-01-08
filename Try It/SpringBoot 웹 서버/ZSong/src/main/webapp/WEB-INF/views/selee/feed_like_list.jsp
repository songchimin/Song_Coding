<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>   
<%@page import="org.json.simple.*"%>
<%@ page import="com.study.springboot.dto.FeedLikeDto" %>

<%@ page import="java.util.*"%>
<%@ page import="java.text.*"%>
<%
JSONObject jsonMain = new JSONObject();
JSONArray jArray = new JSONArray();


ArrayList<FeedLikeDto> array =(ArrayList<FeedLikeDto>) request.getAttribute("list");
int[] existlist = (int[])request.getAttribute("existlist");

for(int i=0;i<array.size();i++){

	JSONObject jObject = new JSONObject();
	
	jObject.put("feedLikeUserid", array.get(i).getMember_id());
	jObject.put("followExist",existlist[i]);
	
	jArray.add(i, jObject);
}
jsonMain.put("List", jArray);
out.println(jsonMain);
%>