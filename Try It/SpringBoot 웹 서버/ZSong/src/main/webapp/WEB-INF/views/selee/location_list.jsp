<%@page import="com.study.springboot.dao.SeleeDao"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="org.json.simple.*"%>
<%@ page import="com.study.springboot.dto.MapDto" %>
<%@ page import="java.util.*"%>
<%@ page import="java.text.*"%>
<%
SeleeDao dao;
JSONObject jsonMain = new JSONObject();
JSONArray jArray = new JSONArray();

ArrayList<MapDto> dto = (ArrayList<MapDto>)request.getAttribute("location_list");

for(int i = 0 ; i < dto.size() ; i++)
{
   JSONObject jObject = new JSONObject();
   jObject.put("latitude", dto.get(i).getLatitude());
   jObject.put("longitude", dto.get(i).getLongitude());
   jArray.add(i, jObject);
}
 jsonMain.put("location_list", jArray);
 out.println(jsonMain); 
%>