<%@page import="org.apache.naming.java.javaURLContextFactory"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%> 

<%	
	session.setAttribute("location", "notify");
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
    			javascript:window.location='Nwrite_view.no';
    		}
    		else
    		{
    			alert("로그인 후 사용 할 수 있습니다.");
    		}

    	}
   	</script>

 
  </head>
  <body>
  
<jsp:include page="../header.jsp" />


<nav >
<div class="row" style="margin: 50px; min-width: 1000px;">

<!-- 게시판 -->
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
		    <a class="nav-link" href="/zzzz/list.po">사진게시판</a>
		  </li>
		  <li class="nav-item">
		    <a class="nav-link disabled" href="#" tabindex="-1" aria-disabled="true">Disabled</a>
		  </li>
	</ul>
  </div>
  
  <div class="col-10">
    <div class="tab-content" id="v-pills-tabContent">
      <div class="tab-pane fade show active" id="v-pills-home" role="tabpanel" aria-labelledby="v-pills-home-tab">
      
                 
<table class="table" style="text-align: center;">
  <thead class="table" style="background-color: #007BFF;">
    <tr style="color: white;">
      <th scope="col">번호</th>
      <th scope="col">작성자</th>
      <th scope="col">제목</th>
      <th scope="col">날짜</th>
      <th scope="col">히트</th>
    </tr>
  </thead>
  
  
  <tbody>
    		<c:forEach items="${nlist}" var="dto">
			<tr>
				<th scope="row">${dto.nId}
				<td>${dto.nName}</td>
				<td>
				
					<c:set var="now" value="<%=new java.util.Date()%>" />
					<fmt:parseNumber var="today" value="${now.time/(1000*60*60)}" integerOnly="true"/>
			
					<fmt:parseDate var="bday" value="${dto.nDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
					<fmt:parseNumber var="writeday" value="${bday.time/(1000*60*60)}" integerOnly="true"/>

					<c:forEach begin="1" end="${dto.nIndent}">-</c:forEach>

					<c:if test="${(today-writeday) < 24}">
						<span class="badge badge-danger">New</span>   
					</c:if>
					    <a href="Ncontent_view.no?nId=${dto.nId}">${dto.nTitle}</a>
				</td>
				<td>${dto.nDate}</td>
				<td>${dto.nHit}</td>
			</tr>
			</c:forEach>
			
			
			<tr>
				
					<td colspan="1">
					</td>
				<form action="find.no" method="post" name="find">	
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
							<button onclick="find()">검색</button>
						</div>	
					</td>
				</form>
				
				<!--글작성 로그인 상태에서 가능 조건 -->
				<td colspan="1" style="text-align: right;">
					<c:if test="${id=='admin'}">
					<button onclick="login_check()">글작성</button>
					</c:if>
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
							<a class="page-link" href="/zzzz/find.no?page=1">[ &lt;&lt; ]</a>
						</c:otherwise>
						</c:choose>
				   	</li>
				   	
				   	
				    <li class="page-item">
	    				<c:choose>
						<c:when test="${(page.curPage - 1) < 1}">
							<a class="page-link"href="#">[ &lt; ]</a>
						</c:when>
						<c:otherwise>
							<a class="page-link"href="/zzzz/find.no?page=${page.curPage - 1}">[ &lt; ]</a>
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
								<a class="page-link"href="/zzzz/find.no?page=${fEach}">[ ${fEach} ]</a>
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
							<a class="page-link" href="/zzzz/find.no?page=${page.curPage + 1}">[ &gt; ]</a>
						</c:otherwise>
						</c:choose>
				    </li>			



					<li class="page-item">
					   	<c:choose>
						<c:when test="${page.curPage == page.totalPage}">
							<a class="page-link" href="#">[ &gt;&gt; ]</a>
						</c:when>
						<c:otherwise>
							<a class="page-link" href="/zzzz/find.no?page=${page.totalPage}">[ &gt;&gt; ]</a>
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
      <div class="tab-pane fade" id="v-pills-profile" role="tabpanel" aria-labelledby="v-pills-profile-tab">

      
      </div>
      <div class="tab-pane fade" id="v-pills-messages" role="tabpanel" aria-labelledby="v-pills-messages-tab">
      	
      
      </div>
      <div class="tab-pane fade" id="v-pills-settings" role="tabpanel" aria-labelledby="v-pills-settings-tab">
      
      
      </div>
    </div>
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