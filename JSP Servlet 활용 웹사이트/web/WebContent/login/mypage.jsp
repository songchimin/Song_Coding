<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 

<%
	//if(session.getAttribute("ValidMem") == null){
%>
<%-- 	<jsp:forward page="login.jsp"/> --%>
<%
	//}
	session.setAttribute("location", "mypage");
	String name = (String)session.getAttribute("name");
	String id = (String)session.getAttribute("id");
%>

<!doctype html>
<html>
  <head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<script src="http://code.jquery.com/jquery-1.7.js"></script>
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">

    <title>Hello, world!</title>
    
    <style>

    </style>
    

  </head>
 
 <body>
<jsp:include page="../header.jsp" />


<SECTION style="height: 500px; vertical-align: middle; text-align: center; margin: auto;">
		<form action="/zzzz/pwcheck.doo" class="form-signin" method="post" style="margin-top: 300px;">
				<c:if test="${id!=null}">
					<label style="text-align: left; width: 250px;">● 비밀번호 확인
						<input class="form-control" type="password" name="pw" placeholder="비밀번호 입력하세요." required="" style="width: 70%; display: inline;">
						<input class="btn btn-outline-primary" type="submit" value="확인" style="width:25%; margin-bottom:5px;">
					</label>
	<!--         		<input class="btn btn-outline-primary" type="button" value="정보수정" -->
	<!-- 					onclick="javascript:window.location='/zzzz/login/modify.jsp'"> -->
	        	</c:if>	
		</form>
</SECTION>

    <!-- Optional JavaScript -->
    <!-- jQuery first, then Popper.js, then Bootstrap JS -->
<!--     <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script> -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
  </body>
</html>