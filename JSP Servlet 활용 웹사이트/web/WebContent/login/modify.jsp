<%@page import="com.study.jsp.MemberDTO"%>
<%@page import="com.study.jsp.MemberDAO"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% request.setCharacterEncoding("UTF-8"); %>
<%
	session.setAttribute("location", "mypage");
	String name = (String)session.getAttribute("name");
	String id = (String)session.getAttribute("id");
	MemberDAO dao = MemberDAO.getInstance();
	MemberDTO dto = dao.getMember(id);
	
	String path = dao.getprofilePath(id);
%>
<!DOCTYPE html>
<html>
<head>
<title>회원정보 수정</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script language="JavaScript" src="members.js"></script>
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<script src="http://code.jquery.com/jquery-1.7.js"></script>
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
    
    <style type="text/css">
    	.form-row{
    		margin-bottom: 10px;
    	}
    	
    	#area {
		  margin: 0;
		  padding: 0;
		  display: -webkit-box;
		  display: -moz-box;
		  display: -ms-flexbox;
		  display: -moz-flex;
		  display: -webkit-flex;
		  display: flex;
		  justify-content: space-between;
		  list-style: none;
		}

    	
    </style>
    
    
    	<style type="text/css">
		
 		.btn-file{ 
 			position:relative; 
 			overflow: hidden; 
		}
 		.btn-file input[type=file]{ 
 			position: absolute; 
			top: 0; 
 			right: 0; 
 			min-width:100%; 
 			min-height: 100%; 
 			text-align: right; 
 			opacity: 0; 
 			outline: none; 
 			background: white; 
 			fillter:alpha(opacity=0); 
 			cursor: inherit;
 			display: block; 
 		}
 		.file{
 			visibility: hidden;
 			position: absolute;
 		}
	</style>
	
</head>
<body>


<jsp:include page="../header.jsp" />


	<form action="modifyOk.doo" method="post" name="reg_frm" style="margin-left:30%; margin-right: 30%; margin-top: 5%; ">
			<div class="form-row">
			    <label><h4>회원정보 수정</h4></label>
			</div>
			<div class="form-row">
			    <label for="validationCustom01">아이디</label>
			    <input type="text" class="form-control" name="id" id="validationCustom01" disabled="disabled" value="<%=dto.getId() %>">
			</div>
			
			<div class="form-row"> 
			      <label for="validationCustom02">비밀번호:</label>
			      <input type="password" class="form-control" name="pw" id="validationCustom02" placeholder="Password" value="" required>
			</div>
  			<div class="form-row"> 
			      <label for="validationCustom03">비밀번호 확인:</label>
			      <input type="password" class="form-control" name="pw_check" id="validationCustom02" placeholder="Password" value="" required>
			</div>
  			<div class="form-row">
			    <label for="validationCustom04">이름</label>
			    <input type="text" class="form-control" id="validationCustom04" disabled="disabled" value="<%= dto.getName() %>">
			</div>
  			<div class="form-row">
			    <label for="validationCustom05">메일</label>
			    <input type="text" class="form-control" name="eMail" id="validationCustom05" value="<%=dto.geteMail()%>">
			</div>
			<div class="form-row">
			    <label for="validationCustom05">주소</label>
			    <input type="text" class="form-control" name="address" id="validationCustom05" value="<%=dto.getAddress()%>">
			</div>
			<div class="form-row" id="area">
				<input class="btn" type="button" value="수정" onclick="updateInfoConfirm()">
				<input class="btn" type="reset" value="취소" onclick="javascript:window.location='../home.jsp'">
				<input class="btn" type="button" value="회원탈퇴" onclick="location.href='delete.jsp'">
			</div>
	</form>
	
<!-- <div class="container" style=""> -->
<form method="post" action="../profile" enctype="multipart/form-data" style="margin-left:30%; margin-right: 30%; margin-top: 5%; " >
	<table class="table table-bordered table-hover" style="text-align: center; border: 1px solid #dddddd; margin: 0 auto;">
		<thead>
			<tr>
				<th colspan="3"><h5 style="text-align: left; font-weight: bold;">프로필 사진 수정</h5></th>
			</tr>
			
		</thead>
		
		<tbody>
			<tr>
				<td style="width: 110px; vertical-align: middle;"><h5 style="margin: 0;"><img src="<%=path%>" ></h5></td>
				<td style="vertical-align: middle;">
					<input type="hidden" name="id" value="<%=id%>"/>
					<input type="file" name="profile" class="file">
					<div class="input-group col-xs-12">
<!-- 						<span class="btn btn-file"></span> -->
							<span class="input-group-addon"><i style="font-size:50px;" class="glyphicon glyphicon-picture"></i></span>
							<input type="text" class="form-control input-lg" disabled="disabled" placeholder="이미지를 업로드 하세요">
							<span class="input-group-btn">
								<button class="browse btn btn-primary input-lg" type="button"><i class="glyphicon glyphicon-search">파일찾기</i></button>
							</span>
					</div>
				</td>
			</tr>
			<tr>
				<td colspan="3" style="text-align: right;"><input class="btn primary" type="submit" value="등록"></td>
			</tr>
		</tbody>
	</table>
</form>
<!-- </div> -->


	<script type="text/javascript">
		$(document).on('click', '.browse', function() {
			var file = $(this).parent().parent().parent().find('.file');
			file.trigger('click');
		})
		$(document).on('change', '.file', function() {
			$(this).parent().find('.form-control').val($(this).val().replace(/C:\\fakepath\\/i, ''));
		});
	</script>
	

<!-- class="form-control" -->
<!-- "form-check-label" -->


    <!-- Optional JavaScript -->
    <!-- jQuery first, then Popper.js, then Bootstrap JS -->
<!--     <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script> -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
</body>
</html>