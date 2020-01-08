<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.study.dto.BDto"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 
<%@page import="com.study.jsp.MemberDTO"%>
<%@page import="com.study.jsp.MemberDAO"%>
<%	
	session.setAttribute("location", "picture_board");
	String name = (String)session.getAttribute("name");
	String id = (String)session.getAttribute("id");
%>
<!doctype html>
<html>
  <head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">

    <title>사진게시판</title>
    
    <style>

    </style>
    
	
	<script src="http://code.jquery.com/jquery.js"></script>

	<script type="text/javascript">
		
		
		function deletecheck(){
			var check = "<%=id%>";
			
				if(check=="${content_view.id}" || check=='admin')				
				{
					var result = confirm("게시물을 삭제 하시겠습니까?");
					if(result)
					{
						document.location.href="delete.po?pId=${content_view.pId}";	
					}
				}else{
					alert("작성자만 삭제 가능합니다.");
				}
		}

	</script>
    
  </head>
  <body>
  
  <jsp:include page="../header.jsp" />

<nav>
<div class="row" style="margin: 50px; min-width: 1000px;">

<!-- 게시판 -->
<div class="col-12">
<div class="row">
  <div class="col-2">
  
  	<ul class="nav nav-pills" style="display: block;">
		  <li class="nav-item">
		    <a class="nav-link" href="/zzzz/notify.no">공지사항</a>
		  </li>
		  <li class="nav-item">
		    <a class="nav-link" href="/zzzz/list.do">자유게시판</a>
		  </li>
		  <li class="nav-item">
		    <a class="nav-link active" href="/zzzz/list.po">사진게시판</a>
		  </li>
		  <li class="nav-item">
		    <a class="nav-link disabled" href="#" tabindex="-1" aria-disabled="true">Disabled</a>
		  </li>
	</ul>
  
  </div>
  
  
  <div class="col-9" >
       	<table class="table" border="2px;" style="table-layout: fixed; background-color:#FFFFFF;">

			<tr style="border-right: none;">
				<td colspan="1" style="text-align: center; background-color:#CFE2F7;">제목</td>
				<th colspan="6">${content_view.pId}. ${content_view.pTitle} </th>
				<td colspan="1" style="text-align: center; background-color:#CFE2F7;">작성자</td>
				<td colspan="1">${content_view.pName}</td>

			</tr>
			<tr style="border-right: none;">
				<td colspan="1" style="text-align: center; background-color:#CFE2F7; border-left: none; vertical-align: middle;">설명</td>
				<td colspan="6">${content_view.pContent}</td>

				<td colspan="2" style="text-align: right;" >
				<img src="/zzzz/img/eye.png" width="32px"/> ${content_view.pHit}
				
				
				
				<c:if test="${id==content_view.like_id}">
				
					<a href="/zzzz/like.do?bId=${content_view.pId}"> <img src="/zzzz/img/heart.png" width="32px"/></a> ${content_view.like_c}
				</c:if>
				<c:if test="${id!=content_view.like_id}">
					<a href="/zzzz/like.do?bId=${content_view.pId}"> <img src="/zzzz/img/like_favorite_heart.png" width="32px" /></a> ${content_view.like_c}
				</c:if>

				</td>
			</tr>

			<tr height="450px">
				<td colspan="9"><img width="100%" height="420px" src="${content_view.path}"></td>
			</tr>
			<tr style="border-right: none;">
				<td colspan="2" style="text-align: center; background-color:#CFE2F7;">
					첨부파일					
				</td>
				<td colspan="7">
<%-- 					<c:if test=""> --%>
					<a href="/zzzz/board/picture_board_download.jsp?pId=${content_view.pId}">${content_view.pFile}</a>
<%-- 					</c:if> --%>
				</td> 
			</tr>
			<tr>	

				<td colspan="9" align="right">
<!-- 					<button class="btn btn-outline-primary" type="button" onclick="javascript:modifycheck();" >수정</button> -->
					<button class="btn btn-outline-primary" type="button" onclick="javascript:window.location='list.po?page=<%= session.getAttribute("cpage") %>';" >목록보기</button>
					<button class="btn btn-outline-primary" type="button" onclick="javascript:deletecheck();" >삭제</button>
<%-- 					<button class="btn btn-outline-primary" type="button" onclick="javascript:window.location='reply_view.do?bId=${content_view.bId}'">답변</button> --%>

				</td>
			</tr>
	</table>   
	


  </div>
</div>


</div>
</div>

</nav>


    <!-- Optional JavaScript -->
    <!-- jQuery first, then Popper.js, then Bootstrap JS -->
<!--     <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script> -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
  </body>
</html>