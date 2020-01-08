<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%> 
<%
 	session.setAttribute("location", "challenge");
 	String name = (String) session.getAttribute("name");
 	String id = (String) session.getAttribute("id");
 	String grade = (String) session.getAttribute("grade");
 %>



<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원 관리</title>		

<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">

<!-- 	<script src="//code.jquery.com/jquery-3.3.1.min.js"></script> -->
	    <script src="https://code.jquery.com/jquery.min.js"></script>
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.js"></script>

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


	function approval(num){
		var host = '${challenge.challenge_host}';
		
			  $.ajax({
				    type:'post',
				    url:'approval_public_challenge',
				    data: ({num:num, host:host}),
				    success:function(data){
					    if(data == 0)
						    alert("승인 실패");
					    else
					    {
						    document.location.href="register_challenge?page=1";
// 						     page최근 페이지로 옮기기
					    }
				    }

				 });

	}
	
</script>

<style type="text/css">/* Chart.js */
@-webkit-keyframes chartjs-render-animation{from{opacity:0.99}to{opacity:1}}@keyframes chartjs-render-animation{from{opacity:0.99}to{opacity:1}}.chartjs-render-monitor{-webkit-animation:chartjs-render-animation 0.001s;animation:chartjs-render-animation 0.001s;}</style>


<style type="text/css">
.nav-item a{ 
  color: black; 
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
            <a class="nav-link active" href="#" onclick="register()" style="color: red;">
              <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-home"><path d="M3 9l9-7 9 7v11a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2z"></path><polyline points="9 22 9 12 15 12 15 22"></polyline></svg>
              	승인 대기 <span class="sr-only">(current)</span>
            </a>
          </li>
          <li class="nav-item" style="margin-bottom: 15px;">
            <a class="nav-link" onclick="vote()">
              <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-file"><path d="M13 2H6a2 2 0 0 0-2 2v16a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V9z"></path><polyline points="13 2 13 9 20 9"></polyline></svg>
              	투표중
            </a>
          </li>
          <li class="nav-item" style="margin-bottom: 15px;">
            <a class="nav-link" onclick="ongoing()">
              <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-shopping-cart"><circle cx="9" cy="21" r="1"></circle><circle cx="20" cy="21" r="1"></circle><path d="M1 1h4l2.68 13.39a2 2 0 0 0 2 1.61h9.72a2 2 0 0 0 2-1.61L23 6H6"></path></svg>
              	진행중
            </a>
          </li>
          <li class="nav-item" style="margin-bottom: 15px;">
            <a class="nav-link" onclick="reward()">
              <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-users"><path d="M17 21v-2a4 4 0 0 0-4-4H5a4 4 0 0 0-4 4v2"></path><circle cx="9" cy="7" r="4"></circle><path d="M23 21v-2a4 4 0 0 0-3-3.87"></path><path d="M16 3.13a4 4 0 0 1 0 7.75"></path></svg>
              	상금 처리
            </a>
          </li>
          <li class="nav-item" style="margin-bottom: 15px;">
            <a class="nav-link" onclick="end()">
              <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-layers"><polygon points="12 2 2 7 12 12 22 7 12 2"></polygon><polyline points="2 17 12 22 22 17"></polyline><polyline points="2 12 12 17 22 12"></polyline></svg>
              	종료
            </a>
          </li>
          <li class="nav-item" style="margin-bottom: 15px;">
            <a class="nav-link" onclick="state()">
              <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-bar-chart-2"><line x1="18" y1="20" x2="18" y2="10"></line><line x1="12" y1="20" x2="12" y2="4"></line><line x1="6" y1="20" x2="6" y2="14"></line></svg>
              	프로젝트 현황
            </a>
          </li>
        </ul>

      </div>
    </nav>


    <main role="main" class="col-md-9 ml-sm-auto col-lg-10 px-4">
    
    
    
        <nav class="my-2 my-md-0 mr-md-3" style="text-align: right;">
     		<br>
        	<a class="p-2" href="/account_home" style="color: #000000; font-weight: bold;">계정관리</a>
 			<a class="p-2" href="/register_challenge?page=1" style="color: red; font-weight: bold;">트라잇 관리</a>
 			<a class="p-2" href="/cash_mileage?page=1" style="color: #000000; font-weight: bold;">결제 관리</a>
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
        <h1 class="h2">공개 트라잇 상세정보</h1> 
        <div class="btn-toolbar mb-2 mb-md-0">

<!-- 		게시판 위  -->

        </div>
      </div>

      <div class="my-4 w-100"  width="1083" height="457" style="display: block; height: 366px; width: 867px;">
      
      
<table class="table" style="text-align: center;">
    <thead class="table" style="background-color: #333333;">
    <tr style="color: white;">
      <th scope="col" style="vertical-align:middle; font-size: 15pt;" colspan="6">공개 트라잇 상세정보</th>
    </tr>
    
  </thead>
  <tbody>
			<tr>
				<td colspan="1">주제</td>
				<td colspan="3" style="vertical-align: middle;">${challenge.challenge_title} (${challenge.challenge_category})</td>
			</tr>
			<tr>
				<td>인증 방법</td>
				<td colspan="3" style="vertical-align: middle;">인증 방법 : ${challenge.challenge_type}</td>
			</tr>
			<tr>
				<td style="vertical-align: middle;">내용</td>
				<td colspan="3" rowspan="2" height="200px;" style="vertical-align: middle;">${challenge.challenge_detail}</td>
			</tr>
			<tr></tr>
			
			<tr>
				<td colspan="4" align="right">
<%-- 					<button class="btn btn-outline-primary" type="button" onclick="approval('${challenge.challenge_num}');" >투표 승인</button> --%>
					
					
					<!-- Button trigger modal -->
					<button type="button" class="btn btn-outline-primary" data-toggle="modal" data-target="#exampleModalCenter">
					  투표 승인
					</button>
					
					<!-- Modal -->
					<div class="modal fade" id="exampleModalCenter" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
					  <div class="modal-dialog modal-dialog-centered" role="document">
					    <div class="modal-content">
					      <div class="modal-header">
					        <h5 class="modal-title" id="exampleModalCenterTitle">승인</h5>
					        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
					          <span aria-hidden="true">&times;</span>
					        </button>
					      </div>
					      <div class="modal-body" align="left">
					      	<br/>
					        	${challenge.challenge_title} 트라잇를 승인 하시겠습니까?
					        <br/>
					        <br/>
					      </div>
					      <div class="modal-footer">
					        <button type="button" class="btn btn-primary" onclick="approval('${challenge.challenge_num}')">예</button>
					        <button type="button" class="btn btn-secondary" data-dismiss="modal">아니오</button>
					      </div>
					    </div>
					  </div>
					</div>
					
					
					
					<button class="btn btn-outline-primary" type="button" onclick="postpone();" >보류</button>
					<button class="btn btn-outline-primary" type="button" onclick="javascript:window.location='register_challenge?page=<%= session.getAttribute("cpage") %>';" >목록보기</button>
				</td>
			</tr>
			<tr>
				<td colspan="4"></td>
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
