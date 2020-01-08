<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%	
	String ban_id = (String)session.getAttribute("ban_id");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

<div style="width: 100%; height: 100%">
<h4>정지 사유</h4>
<textarea style="margin-top: 5px; width: 100%; height: 75%;" id="content"></textarea>
<!-- <input type="text" style="margin-top: 5px; width: 100%;" height="100%;" id="content"> -->
<input type="button" class="btn btn-dark" style="padding: .2rem .45rem; font-size: 0.8rem;" value="처리" onclick="ban_regist('<%=ban_id%>')">
<input type="button" class="btn btn-dark" style="padding: .2rem .45rem; font-size: 0.8rem;" value="취소" onclick="closeContent()">
</div>


</body>
</html>
