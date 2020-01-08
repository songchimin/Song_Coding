<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.study.jsp.MemberDTO"%>
<%@page import="com.study.jsp.MemberDAO"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String name = (String)session.getAttribute("name");
	String id = (String)session.getAttribute("id");
%>        
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<script src="http://code.jquery.com/jquery.js"></script>

	<script type="text/javascript">
		
		function modifycheck(){
			var check = "<%=id%>";
			
				if(check=="${Ncontent_view.id}")				
				{
					document.location.href="Nmodify_view.no?nId=${Ncontent_view.nId}";
				}else{
					alert("작성자만 수정 가능합니다.");
				}
		}
		
		function deletecheck(){
			var check = "<%=id%>";
			
				if(check=="${Ncontent_view.id}")				
				{
					var result = confirm("게시물을 삭제 하시겠습니까?");
					if(result)
					{
						document.location.href="Ndelete.no?nId=${Ncontent_view.nId}";	
					}
				}else{
					alert("작성자만 삭제 가능합니다.");
				}
		}
		
	</script>

</head>
<body>

<jsp:include page="../header.jsp" />

<div class="row" style="margin: 50px;">

<div class="col-12">
<div class="row">
  <div class="col-2">
	 <ul class="nav nav-pills" style="display: block;">
		  <li class="nav-item">
		    <a class="nav-link active" href="/zzzz/notify.no">공지사항</a>
		  </li>
		  <li class="nav-item">
		    <a class="nav-link" href="/zzzz/list.do">자유게시판</a>
		  </li>
		  <li class="nav-item">
		    <a class="nav-link" href="#">Link</a>
		  </li>
		  <li class="nav-item">
		    <a class="nav-link disabled" href="#" tabindex="-1" aria-disabled="true">Disabled</a>
		  </li>
	</ul>
  </div>
  
  <div class="col-10">
		<table width ="500" cellpadding="0" cellspacing="0" border="1">
			<tr>
				<td>번호</td>
				<td>${Ncontent_view.nId}</td>
			</tr>
			<tr>
				<td>히트</td>
				<td>${Ncontent_view.nHit}</td>
			</tr>
			<tr>
				<td>이름</td>
				<td>${Ncontent_view.nName}</td>
			</tr>
			<tr>
				<td>제목</td>
				<td>${Ncontent_view.nTitle}</td>
			</tr>
			<tr>
				<td>내용</td>
				<td>${Ncontent_view.nContent}</td>
			</tr>
		
			<tr>
				<td colspan="2">
					<c:if test="${id=='admin'}">
						<a href="javascript:modifycheck();">수정</a> &nbsp;&nbsp;
					</c:if>
<%-- 					<a href="modify_view.do?bId=${content_view.bId}">수정</a> &nbsp;&nbsp; --%>
					<a href="notify.no?page=<%= session.getAttribute("cpage") %>">목록보기</a> &nbsp;&nbsp;
					<c:if test="${id=='admin'}">
						<a href="javascript:deletecheck();">삭제</a> &nbsp;&nbsp;
					</c:if>
				</td>
			</tr>
	</table>  
  </div>
  
  
 </div>
</div>
</div>

    <!-- Optional JavaScript -->
    <!-- jQuery first, then Popper.js, then Bootstrap JS -->
<!--     <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script> -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
    
</body>
</html>