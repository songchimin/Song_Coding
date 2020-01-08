<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.study.dto.BDto"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 
<%@page import="com.study.jsp.MemberDTO"%>
<%@page import="com.study.jsp.MemberDAO"%>
<%	
	session.setAttribute("location", "board");
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

    <title>자유게시판</title>
    
    <style>
/*     	tbody{ */
/*     		background-color: #E9ECEF; */
/*     	}    	 */
    	
/*     	ul{ */
/*     		margin: auto; */
/*     	} */
    	
/*     	td{ */
/*     		text-align: center; */
/*     	} */

/* 		.custom-select { */
/* 			width: 110px; */
/* 		} */
    </style>

	
	<script src="http://code.jquery.com/jquery.js"></script>

	<script type="text/javascript">
		
		function modifycheck(){
			var check = "<%=id%>";
			
				if(check=="${content_view.id}" || check=='admin')				
				{
					document.location.href="modify_view.do?bId=${content_view.bId}";
				}else{
					alert("작성자만 수정 가능합니다.");
				}
		}
		
		function deletecheck(){
			var check = "<%=id%>";
			
				if(check=="${content_view.id}" || check=='admin')				
				{
					var result = confirm("게시물을 삭제 하시겠습니까?");
					if(result)
					{
						document.location.href="delete.do?bId=${content_view.bId}";	
					}
				}else{
					alert("작성자만 삭제 가능합니다.");
				}
		}
		
		
		function login_check(){
			var checkid = "<%=id%>";
			if(checkid == "null")
			{
				var ok = confirm("로그인 후 사용 가능합니다.\n\n로그인 하시겠습니까?");
				if(ok)
				{
					document.location.href="/zzzz/login/login.jsp";
				}
				else
				{
					
				}
			}
			else
			{
				document.getElementById('commentfr').submit();
			}
			
		}
		
		$(document).ready(function() {
			$('#exampleFormControlTextarea1').keyup(function (e){
			    var content = $(this).val();
			    	$('#counter').html("("+content.length+" / 100자)");    //글자수 실시간 카운팅
			    if (content.length > 100){
			        alert("100자까지 입력 가능합니다.");
			        $(this).val(content.substring(0, 100));
			        $('#counter').html("(100 / 100자)");
			    }
			});
		});

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
		    <a class="nav-link active" href="/zzzz/list.do">자유게시판</a>
		  </li>
		  <li class="nav-item">
		    <a class="nav-link" href="#">사진게시판</a>
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
				<th colspan="8">${content_view.bId}. ${content_view.bTitle} </th>
<!-- 				<td colspan="7"> 제목</td> -->

<!-- 				<td colspan="1" style="text-align: center; background-color:#CFE2F7;">조회수</td> -->
<%-- 				<td colspan="1" style="text-align: center;"><img src="/zzzz/img/eye.png" width="25px"/> ${content_view.bHit}</td> --%>
			</tr>
			<tr style="border-right: none;">
				<td colspan="1" style="text-align: center; background-color:#CFE2F7;">작성자</td>
				<td colspan="6">${content_view.bName}</td>
<!-- 				<td colspan="1" style="text-align: center; background-color:#CFE2F7;">좋아요</td> -->
				<td colspan="2" style="text-align: right;" >
				<img src="/zzzz/img/eye.png" width="32px"/> ${content_view.bHit}
				
				<c:if test="${id==content_view.like_id}">
					<a href="/zzzz/like.do?bId=${content_view.bId}"> <img src="/zzzz/img/heart.png" width="32px"/></a> ${content_view.like_c}
				</c:if>
				<c:if test="${id!=content_view.like_id}">
					<a href="/zzzz/like.do?bId=${content_view.bId}"> <img src="/zzzz/img/like_favorite_heart.png" width="32px" /></a> ${content_view.like_c}
				</c:if>
             
<%-- 					<c:if test="${content_view.like_id eq id}"> --%>
						
<%-- 					</c:if> --%>
<%-- 					<c:if test="${content_view.like_id != id}"> --%>
						
<%-- 					</c:if> --%>
				</td>
			</tr>
			<tr height="300px">
				<td colspan="9">${content_view.bContent}</td>
			</tr>
			<tr style="border-right: none;">
				<td colspan="2" style="text-align: center; background-color:#CFE2F7;">
					첨부파일					
				</td>
				<td colspan="7">
<%-- 					<c:if test=""> --%>
					<a href="/zzzz/board/board_download.jsp?bId=${content_view.bId}">${content_view.bFile}</a>
<%-- 					</c:if> --%>
				</td> 
			</tr>
			<tr>
<!-- 				<td colspan="2"> -->
<!-- 					전후 -->
					
<!-- 				</td> -->
				<td colspan="9" align="right">
					<button class="btn btn-outline-primary" type="button" onclick="javascript:modifycheck();" >수정</button>
					<button class="btn btn-outline-primary" type="button" onclick="javascript:window.location='list.do?page=<%= session.getAttribute("cpage") %>';" >목록보기</button>
					<button class="btn btn-outline-primary" type="button" onclick="javascript:deletecheck();" >삭제</button>
					<button class="btn btn-outline-primary" type="button" onclick="javascript:window.location='reply_view.do?bId=${content_view.bId}'">답변</button>
					
<!-- 					<a href="javascript:modifycheck();">수정</a> &nbsp;&nbsp; -->
<%-- 					<a href="list.do?page=<%= session.getAttribute("cpage") %>">목록보기</a> &nbsp;&nbsp; --%>
<!-- 					<a href="javascript:deletecheck();">삭제</a> &nbsp;&nbsp; -->
<%-- 					<a href="reply_view.do?bId=${content_view.bId}">답변</a> --%>
				</td>
			</tr>
	</table>   
	

	<table class="table">
	<form action="comment.do" method="post" id="commentfr">
		<input type="hidden" name="bid" value="${content_view.bId}"/>
		<tr>
<%-- 			<c:if test="${id!=null}"> --%>
				<td colspan="8" width="100%">
	<!-- 					-댓글- -->
	<!-- 					<input type="text" class="form-control" name="comment" id="comment"> -->
						<label for="Textarea1">댓글 <span style="color:#aaa;" id="counter">(0 / 100자)</span></label>
	    				<c:if test="${id!=null}">
	    					<textarea class="form-control" id="exampleFormControlTextarea1" rows="3" name="content" placeholder="내용을 입력해 주세요."></textarea>
	    				</c:if>
	    				<c:if test="${id==null}">
	    					<textarea class="form-control" id="exampleFormControlTextarea1" onclick="login_check();" rows="3" name="content">로그인 후 이용 가능합니다.</textarea>
	    				</c:if>
				</td>
				<td colspan="1" style="text-align: center; padding-top: 43px;">
<!-- 					<input type="button" class="btn btn-ck" onclick="document.getElementById('commentfr').submit();" value="입력" style="height:85px "/> -->
						<input type="button" class="btn btn-ck" onclick="login_check();" value="입력" style="height:85px; "/>
				</td>
<%-- 			</c:if> --%>
		</tr>	
	</form>	
			<tr>
				<td colspan="1">이름</td>
				<td colspan="5">댓글내용</td>
				<td colspan="2">작성일</td>
				<td colspan="1"></td>
			</tr>
			
			<c:forEach items="${clist}" var="cdto">
			<tr>
				<c:if test="${content_view.id == cdto.cId}">
					<th scope="row"colspan="1"><span class="badge badge-warning">작성자</span>  ${cdto.cName}</th>
				</c:if>
				<c:if test="${content_view.id != cdto.cId}">
					<c:if test="${cdto.cId == 'admin'}">
						<th scope="row"colspan="1"><span class="badge badge-danger">관리자</span>  ${cdto.cName}</th>
					</c:if>
					<c:if test="${cdto.cId != 'admin'}">
					<th scope="row"colspan="1">${cdto.cName}</th>
					</c:if>
				</c:if>
			
<%-- 				${ddto.bId} --%>
<%-- 				<td>${cdto.cNo}</td> --%>
<%-- 				<td>${cdto.cId}</td> --%>
				<td colspan="5">${cdto.content}</td>
				<td colspan="2">${cdto.cDate}</td>
<%-- 				<td>${cdto.ss}</td> --%>
				
			<!--삭제버튼 -->
				<c:if test="${id == cdto.cId || id == 'admin'}">
					<td colspan="1">
						<button class="btn btn-outline-primary" style="font-size:6px; padding: 5px; padding-top:3px; padding-bottom: 3px ;" onclick="javascript:window.location='/zzzz/cdelete.do?bid=${content_view.bId}&cNo=${cdto.cNo}'">X</button>
					</td>
				</c:if>
				<c:if test="${id != cdto.cId}">
					<td colspan="1"></td>
				</c:if>
				
			</tr>
			</c:forEach>		


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