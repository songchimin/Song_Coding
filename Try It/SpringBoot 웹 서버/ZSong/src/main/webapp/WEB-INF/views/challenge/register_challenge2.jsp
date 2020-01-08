<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%> 
<%	
	session.setAttribute("location", "register_challenge");
	String name = (String)session.getAttribute("name");
	String id = (String)session.getAttribute("id");
	String grade = (String)session.getAttribute("grade");
%>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원 관리</title>

<script src="//code.jquery.com/jquery-3.3.1.min.js"></script>
    <script src="http://code.jquery.com/jquery-1.9.1.js"></script>

<!-- <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.7/jquery.js"></script> -->

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
	function register(){
		javascript:window.location='register_challenge?page=1';
	}
	function vote(){
		javascript:window.location='vote_public_challenge?page=1';
	}
	function ongoing(){
		javascript:window.location='ongoing_challenge?page=1';
	}
	function reward(){
		javascript:window.location='reward_challenge?page=1';
	}
	function end(){
		javascript:window.location='end_challenge?page=1';
	}
	function state(){
		javascript:window.location='state_challenge';
	}
	</script>

<style type="text/css">/* Chart.js */
@-webkit-keyframes chartjs-render-animation{from{opacity:0.99}to{opacity:1}}@keyframes chartjs-render-animation{from{opacity:0.99}to{opacity:1}}.chartjs-render-monitor{-webkit-animation:chartjs-render-animation 0.001s;animation:chartjs-render-animation 0.001s;}</style>
</head>
<body>
	<jsp:include page="../header2.jsp" />


<div class="container-fluid">
  <div class="row">
    <nav class="col-md-2 d-none d-md-block bg-light sidebar">
      <div class="sidebar-sticky">
        <ul class="nav flex-column">
          <li class="nav-item">
            <a class="nav-link active" href="#">
              <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-home"><path d="M3 9l9-7 9 7v11a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2z"></path><polyline points="9 22 9 12 15 12 15 22"></polyline></svg>
              Dashboard <span class="sr-only">(current)</span>
            </a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="#">
              <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-file"><path d="M13 2H6a2 2 0 0 0-2 2v16a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V9z"></path><polyline points="13 2 13 9 20 9"></polyline></svg>
              Orders
            </a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="#">
              <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-shopping-cart"><circle cx="9" cy="21" r="1"></circle><circle cx="20" cy="21" r="1"></circle><path d="M1 1h4l2.68 13.39a2 2 0 0 0 2 1.61h9.72a2 2 0 0 0 2-1.61L23 6H6"></path></svg>
              Products
            </a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="#">
              <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-users"><path d="M17 21v-2a4 4 0 0 0-4-4H5a4 4 0 0 0-4 4v2"></path><circle cx="9" cy="7" r="4"></circle><path d="M23 21v-2a4 4 0 0 0-3-3.87"></path><path d="M16 3.13a4 4 0 0 1 0 7.75"></path></svg>
              Customers
            </a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="#">
              <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-bar-chart-2"><line x1="18" y1="20" x2="18" y2="10"></line><line x1="12" y1="20" x2="12" y2="4"></line><line x1="6" y1="20" x2="6" y2="14"></line></svg>
              Reports
            </a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="#">
              <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-layers"><polygon points="12 2 2 7 12 12 22 7 12 2"></polygon><polyline points="2 17 12 22 22 17"></polyline><polyline points="2 12 12 17 22 12"></polyline></svg>
              Integrations
            </a>
          </li>
        </ul>

        <h6 class="sidebar-heading d-flex justify-content-between align-items-center px-3 mt-4 mb-1 text-muted">
          <span>Saved reports</span>
          <a class="d-flex align-items-center text-muted" href="#" aria-label="Add a new report">
            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-plus-circle"><circle cx="12" cy="12" r="10"></circle><line x1="12" y1="8" x2="12" y2="16"></line><line x1="8" y1="12" x2="16" y2="12"></line></svg>
          </a>
        </h6>
        <ul class="nav flex-column mb-2">
          <li class="nav-item">
            <a class="nav-link" href="#">
              <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-file-text"><path d="M14 2H6a2 2 0 0 0-2 2v16a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V8z"></path><polyline points="14 2 14 8 20 8"></polyline><line x1="16" y1="13" x2="8" y2="13"></line><line x1="16" y1="17" x2="8" y2="17"></line><polyline points="10 9 9 9 8 9"></polyline></svg>
              Current month
            </a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="#">
              <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-file-text"><path d="M14 2H6a2 2 0 0 0-2 2v16a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V8z"></path><polyline points="14 2 14 8 20 8"></polyline><line x1="16" y1="13" x2="8" y2="13"></line><line x1="16" y1="17" x2="8" y2="17"></line><polyline points="10 9 9 9 8 9"></polyline></svg>
              Last quarter
            </a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="#">
              <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-file-text"><path d="M14 2H6a2 2 0 0 0-2 2v16a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V8z"></path><polyline points="14 2 14 8 20 8"></polyline><line x1="16" y1="13" x2="8" y2="13"></line><line x1="16" y1="17" x2="8" y2="17"></line><polyline points="10 9 9 9 8 9"></polyline></svg>
              Social engagement
            </a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="#">
              <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-file-text"><path d="M14 2H6a2 2 0 0 0-2 2v16a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V8z"></path><polyline points="14 2 14 8 20 8"></polyline><line x1="16" y1="13" x2="8" y2="13"></line><line x1="16" y1="17" x2="8" y2="17"></line><polyline points="10 9 9 9 8 9"></polyline></svg>
              Year-end sale
            </a>
          </li>
        </ul>
      </div>
    </nav>

    <main role="main" class="col-md-9 ml-sm-auto col-lg-10 px-4"><div class="chartjs-size-monitor" style="position: absolute; left: 0px; top: 0px; right: 0px; bottom: 0px; overflow: hidden; pointer-events: none; visibility: hidden; z-index: -1;"><div class="chartjs-size-monitor-expand" style="position:absolute;left:0;top:0;right:0;bottom:0;overflow:hidden;pointer-events:none;visibility:hidden;z-index:-1;"><div style="position:absolute;width:1000000px;height:1000000px;left:0;top:0"></div></div><div class="chartjs-size-monitor-shrink" style="position:absolute;left:0;top:0;right:0;bottom:0;overflow:hidden;pointer-events:none;visibility:hidden;z-index:-1;"><div style="position:absolute;width:200%;height:200%;left:0; top:0"></div></div></div>
      <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
        <h1 class="h2">등록 관리</h1>
        <div class="btn-toolbar mb-2 mb-md-0">
          <div class="btn-group mr-2">
            <button type="button" class="btn btn-sm btn-outline-secondary">Share</button>
            <button type="button" class="btn btn-sm btn-outline-secondary">Export</button>
          </div>
          <button type="button" class="btn btn-sm btn-outline-secondary dropdown-toggle">
            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-calendar"><rect x="3" y="4" width="18" height="18" rx="2" ry="2"></rect><line x1="16" y1="2" x2="16" y2="6"></line><line x1="8" y1="2" x2="8" y2="6"></line><line x1="3" y1="10" x2="21" y2="10"></line></svg>
            This week
          </button>
        </div>
      </div>

      <div class="my-4 w-100">
      
      		
      
            			
<table class="table" style="text-align: center;">
<!-- #191717; -->
  <thead class="table" style="background-color: #333333;">
    <tr style="color: white;">
      <th scope="col" style="vertical-align:middle; font-size: 10pt;" width="120px;">챌린지 번호</th>
      <th scope="col" style="vertical-align:middle; font-size: 10pt;" width="200px;">카테고리</th>
      <th scope="col" colspan="2" style="vertical-align:middle; font-size: 10pt;" width="330px;">제목</th>
      <th scope="col" style="vertical-align:middle; font-size: 10pt;" width="120px;">구분</th>
<!--       <th scope="col" style="vertical-align:middle; font-size: 10pt;">인증빈도</th> -->
<!--       <th scope="col" style="vertical-align:middle; font-size: 10pt;">시작일</th> -->
<!--       <th scope="col" style="vertical-align:middle; font-size: 10pt;">종료일</th> -->
      <th scope="col" colspan="1" style="vertical-align:middle; font-size: 10pt;" width="200px;">신청날짜</th>
<!--       <th scope="col" style="vertical-align:middle; font-size: 10pt;">체크박스</th> -->
    </tr>
    
  </thead>

  <tbody>
    		<c:forEach items="${challenge}" var="dto">
			
			<tr>
				<th scope="row" style="vertical-align: middle;">${dto.challenge_num}			    
				<td style="vertical-align: middle;">${dto.challenge_category}</td>
				<td style="vertical-align: middle;" colspan="2"><a href="/challenge_content?num=${dto.challenge_num}">${dto.challenge_title}</a></td>
				<td style="vertical-align: middle;">
					<c:if test="${dto.challenge_public == 0}">
						공개
					</c:if>
					<c:if test="${dto.challenge_public == 1}">
						비공개
					</c:if>
				</td>
<%-- 				<td style="vertical-align: middle;">${dto.challenge_frequency}</td> --%>
<%-- 				<td style="vertical-align: middle;">${dto.challenge_start}</td> --%>
<%-- 				<td style="vertical-align: middle;">${dto.challenge_end}</td> --%>
				<td style="vertical-align: middle;">${dto.challenge_date}</td>
			</tr>

			</c:forEach>

			
			<tr>
				
					<td colspan="1">
					</td>
					
				<form action="/register_challenge?page=${page.curPage}" method="post" name="find">
						<td colspan="1">
						<select class="custom-select" name="findoption" id ="findoption">
							<option selected>옵션</option>
							<option value="카테고리">카테고리</option>
							<option value="제목">제목</option>
							<option value="날짜">날짜</option>
							<option value="구분">구분</option>
						</select>
						</td>
						<td colspan="1">
						<div class="input-group">
							<input type="text" class="form-control" name="findtext" id="findtext">
							<button type="submit">검색</button>
						</div>	
						</td>
				</form>
				<td colspan="3"></td>	
<!-- 				글작성 로그인 상태에서 가능 조건 -->
<!-- 				<td colspan="2" style="text-align: right;"> -->
<!-- 					<button onclick="login_check()">글작성</button> -->
<!-- 				</td> -->
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
							<a class="page-link" href="/register_challenge?page=1">[ &lt;&lt; ]</a>
						</c:otherwise>
						</c:choose>
				   	</li>
				   	
				   	
				    <li class="page-item">
	    				<c:choose>
						<c:when test="${(page.curPage - 1) < 1}">
							<a class="page-link"href="#">[ &lt; ]</a>
						</c:when>
						<c:otherwise>
							<a class="page-link"href="/register_challenge?page=${page.curPage - 1}">[ &lt; ]</a>
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
								<a class="page-link"href="/register_challenge?page=${fEach}">[ ${fEach} ]</a>
							</c:otherwise>
							</c:choose>
							</li>
						</c:forEach>
				    
				   

				    <li class="page-item" style="display: inline; float: left;">
	    				<c:choose>
						<c:when test="${(page.curPage +1) > page.totalPage }">
							<a class="page-link"href="#">[ &gt; ]</a>
						</c:when>
						<c:otherwise>
							<a class="page-link" href="/register_challenge?page=${page.curPage + 1}">[ &gt; ]</a>
						</c:otherwise>
						</c:choose>
				    </li>			



					<li class="page-item">
					   	<c:choose>
						<c:when test="${page.curPage == page.totalPage}">
							<a class="page-link" href="#">[ &gt;&gt; ]</a>
						</c:when>
						<c:otherwise>
							<a class="page-link" href="/register_challenge?page=${page.totalPage}">[ &gt;&gt; ]</a>
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

    </main>
  </div>
</div>




<!-- 	<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script> -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
</body>
</html>