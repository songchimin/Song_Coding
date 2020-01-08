<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.study.jsp.MemberDAO"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%> 
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
    	
    	ul{
    		margin: auto;
    	}
    	
    	td{
    		text-align: center;
    	}

		.custom-select {
			width: 110px;
		}
    </style>
    
    <script type="text/javascript">

    	function login_check(){
    	   
    		if(test = ${id!=null})
    		{
    			javascript:window.location='write_view.do';
    		}
    		else
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

    	}
   	</script>
   	
   	
   		<style>
    	
    	.notice-box { padding:10px; background-color:rgb(249, 250, 252); text-align:left; } 
    	.notice-box p { -webkit-margin-before: .3em; -webkit-margin-after: .5em; } 
    	#close { z-index: 2147483647; position:absolute; padding:2px 5px; font-weight: 700; text-shadow: 0 1px 0 #fff; font-size: 1.3rem;  left: 350px;} 
    	#close:hover { border: 0; cursor:pointer; opacity: .75; }

    </style>
    
   	
  </head>
  <body>
  
<jsp:include page="/header.jsp" />

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
		    <a class="nav-link" href="/zzzz/list.po">사진게시판</a>
		  </li>
		  <li class="nav-item">
		    <a class="nav-link disabled" href="#" tabindex="-1" aria-disabled="true">Disabled</a>
		  </li>
	</ul>
  
  </div>
  
  <div class="col-10">
            
<table class="table" style="text-align: center;">
  <thead class="table" style="background-color: #007BFF;">
    <tr style="color: white;">
      <th scope="col">번호</th>
      <th scope="col">작성자</th>
      <th scope="col">제목</th>
      <th scope="col">날짜</th>
      <th scope="col">조회수/좋아요</th>
    </tr>
  </thead>
  <tbody>
    		<c:forEach items="${list}" var="dto">
			<tr>
				<th scope="row">${dto.bId}
				<td><a><img class ="media-object img-circle" style="width:30px; height:30px;" src="${dto.profile}"> ${dto.bName}</a></td>
				
				<td>			
					<c:set var="now" value="<%=new java.util.Date()%>" />
					<fmt:parseNumber var="today" value="${now.time/(1000*60*60)}" integerOnly="true"/>
			
					<fmt:parseDate var="bday" value="${dto.bDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
					<fmt:parseNumber var="writeday" value="${bday.time/(1000*60*60)}" integerOnly="true"/>
					
					<c:forEach begin="1" end="${dto.bIndent}">└답글</c:forEach>
					
					<c:if test="${(today-writeday) < 24}">
						<span class="badge badge-danger">New</span>   
					</c:if>
					
					<a href="content_view.do?bId=${dto.bId}">${dto.bTitle}</a>
				</td>
				<td>${dto.bDate}</td>
				<td><img alt="" src="/zzzz/img/eye.png"> ${dto.bHit}    <img alt="" src="/zzzz/img/small_heart.png"> ${dto.like_c}</td>
			</tr>
			</c:forEach>

			
			<tr>
				
					<td colspan="1">
					</td>
				<form action="find.do" method="post" name="find">	
					<td colspan="1" style="text-align:right;">
						<select class="custom-select" name="findoption" id ="findoption">
							<option selected>검색옵션</option>
							<option value="제목">제목</option>
							<option value="내용">내용</option>
							<option value="작성자">작성자</option>
						</select>
					</td>
					
					<td colspan="2" style="text-align:center;">
						<div class="input-group">
							<input type="text" class="form-control" name="findtext" id="findtext">
							<button  onclick="find()">검색</button>
						</div>	
					</td>
				</form>
				
				<!--글작성 로그인 상태에서 가능 조건 -->
				<td colspan="1" style="text-align: right;">
					<button class="btn" onclick="login_check()">글작성</button>
				</td>
			</tr>
			
			
			
			<tr>
				<td colspan="5" aria-label="Page navigation example" align="center">
				<div class="row align-items-center">
				<ul class="pagination">
				    <li class="page-item">
					   	<c:choose>
						<c:when test="${(page.curPage - 1) < 1}">
							<a class="page-link" href="#">[ &lt;&lt; ]</a>
						</c:when>
						<c:otherwise>
							<a class="page-link" href="/zzzz/find.do?page=1">[ &lt;&lt; ]</a>
						</c:otherwise>
						</c:choose>
				   	</li>
				   	
				   	
				    <li class="page-item">
	    				<c:choose>
						<c:when test="${(page.curPage - 1) < 1}">
							<a class="page-link"href="#">[ &lt; ]</a>
						</c:when>
						<c:otherwise>
							<a class="page-link"href="/zzzz/find.do?page=${page.curPage - 1}">[ &lt; ]</a>
						</c:otherwise>
						</c:choose>
				    </li>				    
				    
				    
				 
				    
					    <c:forEach var="fEach" begin="${page.startPage}" end="${page.endPage}" step="1">
					    	<li class="page-item" >
							<c:choose>
							<c:when test="${page.curPage == fEach}">
								<a class="page-link"href="#">[ ${fEach} ]</a>
							</c:when>
							<c:otherwise>
								<a class="page-link"href="/zzzz/find.do?page=${fEach}">[ ${fEach} ]</a>
							</c:otherwise>
							</c:choose>
							</li>
						</c:forEach>
				    
				   

				    <li class="page-item" style="display: inline; float: left;">
	    				<c:choose>
						<c:when test="${(page.curPage +1) >page.totalPage }">
							<a class="page-link"href="#">[ &gt; ]</a>
						</c:when>
						<c:otherwise>
							<a class="page-link" href="/zzzz/find.do?page=${page.curPage + 1}">[ &gt; ]</a>
						</c:otherwise>
						</c:choose>
				    </li>			



					<li class="page-item">
					   	<c:choose>
						<c:when test="${page.curPage == page.totalPage}">
							<a class="page-link" href="#">[ &gt;&gt; ]</a>
						</c:when>
						<c:otherwise>
							<a class="page-link" href="/zzzz/find.do?page=${page.totalPage}">[ &gt;&gt; ]</a>
						</c:otherwise>
						</c:choose>
				   	</li>


				  </ul>
				
				</div>
			
				</td>
			</tr>
  </tbody>
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