<%@page import="com.study.springboot.dao.MyPageDao"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="org.json.simple.*"%>
<%@ page import="com.study.springboot.dto.ComplaintDto"%>
<%@ page import="java.util.*"%>
<%@ page import="java.text.*"%>
<%
	JSONObject jsonMain = new JSONObject();
	JSONArray jArray = new JSONArray();

	ArrayList<ComplaintDto> dto = (ArrayList<ComplaintDto>) request.getAttribute("list");

	for (int i = 0; i < dto.size(); i++) {
		JSONObject jObject = new JSONObject();

		jObject.put("complaint_num", dto.get(i).getComplaint_num());
		jObject.put("challenge_num", dto.get(i).getChallenge_num());
		jObject.put("comment_num", dto.get(i).getComment_num());
		jObject.put("member_id", dto.get(i).getMember_id());
		jObject.put("complaint_content", dto.get(i).getComplaint_content());
		jObject.put("complaint_state", dto.get(i).getComplaint_state());
		jObject.put("complaint_reply", dto.get(i).getComplaint_reply());
		
		SimpleDateFormat date = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		if (dto.get(i).getComplaint_date() != null)
			jObject.put("complaint_date", date.format(dto.get(i).getComplaint_date()));
		else
			jObject.put("complaint_date", dto.get(i).getComplaint_date());

		jArray.add(i, jObject);
	}
	jsonMain.put("List", jArray);
	out.println(jsonMain);
%>