<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%> 
<%	
	session.setAttribute("location", "cash_mileage");
	String name = (String)session.getAttribute("name");
	String id = (String)session.getAttribute("id");
	String grade = (String)session.getAttribute("grade");
%>




<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원 관리</title>

<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">

	    <script src="https://code.jquery.com/jquery.min.js"></script>
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.js"></script>
<script src="//code.jquery.com/jquery-3.3.1.min.js"></script>

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
	function cash_mileage(){
		javascript:window.location='cash_mileage?page=1';
	}
	function withdrawal_request(){
		javascript:window.location='withdrawal_request?page=1';
	}
	function reward_list(){
		javascript:window.location='reward_list?page=1';
	}
	function reward_exchange(){
		javascript:window.location='reward_exchange?page=1';
	}

</script>




<style type="text/css">/* Chart.js */
@-webkit-keyframes chartjs-render-animation{from{opacity:0.99}to{opacity:1}}@keyframes chartjs-render-animation{from{opacity:0.99}to{opacity:1}}.chartjs-render-monitor{-webkit-animation:chartjs-render-animation 0.001s;animation:chartjs-render-animation 0.001s;}</style>



<style type="text/css">
.nav-item a{ 
  color: black; 
/*   border: 5px solid currentColor; */
/*   box-shadow: 0 0 5px solid currentColor; */
}

</style>


<script type="text/javascript">

	$(window).scroll(function(event){
	if(jQuery(window).scrollTop() > jQuery("#side").offset().top) {
	jQuery("#chase").css("position", "fixed");
	}
	else if((jQuery(window).scrollTop() < jQuery("#side").offset().top)) {
	jQuery("#chase").css("position", "static");
	}
	});
</script>

<style>

html,body {height:100%;}
.container-fluid, .row{height: 100%;}
#aa, #side, #chase{height: 100%;}

</style>

</head>
<body>
	<jsp:include page="../header2.jsp" />
      		


<div class="container-fluid">
  <div class="row" id="aa"> 
    <nav class="col-md-2 d-none d-md-block bg-light sidebar" id="side">
      <div class="sidebar-sticky" style="margin-top: 80px;" id="chase">
        <ul class="nav flex-column">
          <li class="nav-item" style="margin-bottom: 15px;">
            <a class="nav-link active" href="#" onclick="cash_mileage()"  style="color: red;">
              <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-file"><path d="M13 2H6a2 2 0 0 0-2 2v16a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V9z"></path><polyline points="13 2 13 9 20 9"></polyline></svg>
              	입/출금 내역 <span class="sr-only">(current)</span>
            </a>
          </li>
          <li class="nav-item" style="margin-bottom: 15px;">
            <a class="nav-link" href="#" onclick="withdrawal_request()">
              <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-file"><path d="M13 2H6a2 2 0 0 0-2 2v16a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V9z"></path><polyline points="13 2 13 9 20 9"></polyline></svg>
              	환급 신청 내역
            </a>
          </li>
          <li class="nav-item" style="margin-bottom: 15px;">
            <a class="nav-link" href="#" onclick="reward_list()">
              <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-layers"><polygon points="12 2 2 7 12 12 22 7 12 2"></polygon><polyline points="2 17 12 22 22 17"></polyline><polyline points="2 12 12 17 22 12"></polyline></svg>
              	상금 지급 내역
            </a>
          </li>
          <li class="nav-item" style="margin-bottom: 15px;">
            <a class="nav-link" href="#" onclick="reward_exchange()">
              <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-layers"><polygon points="12 2 2 7 12 12 22 7 12 2"></polygon><polyline points="2 17 12 22 22 17"></polyline><polyline points="2 12 12 17 22 12"></polyline></svg>
              	상금 전환 내역
            </a>
          </li>
        </ul>

      </div>
    </nav>


    <main role="main" class="col-md-9 ml-sm-auto col-lg-10 px-4">
    
    
    
        <nav class="my-2 my-md-0 mr-md-3" style="text-align: right;">
     		<br>
        	<a class="p-2" href="/account_home" style="color: #000000;  font-weight: bold;">계정관리</a>
 			<a class="p-2" href="/register_challenge?page=1" style="color: #000000; font-weight: bold;">프로젝트 관리</a>
 			<a class="p-2" href="/cash_mileage?page=1" style="color: red; font-weight: bold;">결제 관리</a>
<!--  			<a class="p-2" href="/payment_" style="color: #000000; font-weight: bold;">신고 관리</a> -->
 			<a class="p-2" href="/waitQnA?page=1" style="color: #000000; font-weight: bold;">신고 및 Q&A</a>
 			
      </nav>
    

    
    
    <div class="chartjs-size-monitor" style="position: absolute; left: 0px; top: 0px; right: 0px; bottom: 0px; overflow: hidden; pointer-events: none; visibility: hidden; z-index: -1;">
	    <div class="chartjs-size-monitor-expand" style="position:absolute;left:0;top:0;right:0;bottom:0;overflow:hidden;pointer-events:none;visibility:hidden;z-index:-1;">
	    	<div style="position:absolute;width:1000000px;height:1000000px;left:0;top:0"> </div>
	    </div>
	    <div class="chartjs-size-monitor-shrink" style="position:absolute;left:0;top:0;right:0;bottom:0;overflow:hidden;pointer-events:none;visibility:hidden;z-index:-1;">
	    	<div style="position:absolute;width:200%;height:200%;left:0; top:0"></div>
	    </div>
    </div>
      <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom" >
        <h1 class="h2">결제 관리</h1> 
        <div class="btn-toolbar mb-2 mb-md-0">

<!--게시판 테이블 상단 -->

        </div>
      </div>

      <div class="my-4 w-100"  width="1083" height="457" style="display: block; height: 366px; width: 867px;">
      
      
      			
<table class="table" style="text-align: center;">
  <thead class="table" style="background-color: #333333;">
    <tr style="color: white;">
      <th scope="col" style="vertical-align:middle; font-size: 10pt;" width="120px;">번호</th>
      <th scope="col" style="vertical-align:middle; font-size: 10pt;" width="200px;">아이디</th>
      <th scope="col" colspan="1" style="vertical-align:middle; font-size: 10pt;" width="330px;">금액</th>
      <th scope="col" colspan="1" style="vertical-align:middle; font-size: 10pt;" width="200px;">구분</th>
      <th scope="col" style="vertical-align:middle; font-size: 10pt;" width="120px;">날짜</th>
    </tr>
    
  </thead>

  <tbody>
    		<c:forEach items="${cash_mileage_list}" var="dto">
			
			<tr>
				<th scope="row" style="vertical-align: middle;">${dto.list_cash_num}			    
				<td style="vertical-align: middle;">${dto.list_member_id}</td>
				
				<c:if test="${dto.list_cash_check == 0}">
					<td style="color: blue; vertical-align: middle;" colspan="1">${dto.list_cash_content}</td>
					<td style="color: blue;">	
						입금
					</td>
				</c:if>
				<c:if test="${dto.list_cash_check == 1}">
					<td style="color: red; vertical-align: middle;" colspan="1">${dto.list_cash_content}</td>
					<td style="color: red;">	
						출금
					</td>	
				</c:if>
				
				
				<td style="vertical-align: middle;" width="250px;">${dto.list_cash_date}</td>
			</tr>

			</c:forEach>

			
			<tr>
				
					<td colspan="1">
					</td>
					
				<form action="member?page=${page.curPage}" method="post" name="find">
						<td colspan="1">
						<select class="custom-select" name="findoption" id ="findoption">
							<option selected>옵션</option>
							<option value="아이디">아이디</option>
							<option value="구분">구분</option>
							<option value="날짜">날짜</option>
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
							<a class="page-link" href="/member?page=1">[ &lt;&lt; ]</a>
						</c:otherwise>
						</c:choose>
				   	</li>
				   	
				   	
				    <li class="page-item">
	    				<c:choose>
						<c:when test="${(page.curPage - 1) < 1}">
							<a class="page-link"href="#">[ &lt; ]</a>
						</c:when>
						<c:otherwise>
							<a class="page-link"href="/member?page=${page.curPage - 1}">[ &lt; ]</a>
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
								<a class="page-link"href="/member?page=${fEach}">[ ${fEach} ]</a>
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
							<a class="page-link" href="/member?page=${page.curPage + 1}">[ &gt; ]</a>
						</c:otherwise>
						</c:choose>
				    </li>			



					<li class="page-item">
					   	<c:choose>
						<c:when test="${page.curPage == page.totalPage}">
							<a class="page-link" href="#">[ &gt;&gt; ]</a>
						</c:when>
						<c:otherwise>
							<a class="page-link" href="/member?page=${page.totalPage}">[ &gt;&gt; ]</a>
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
