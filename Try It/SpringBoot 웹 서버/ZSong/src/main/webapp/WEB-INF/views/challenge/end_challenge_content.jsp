<%@page import="com.study.springboot.dto.RecordDto"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%> 
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%   
   session.setAttribute("location", "challenge");
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

<!-- 	<script src="//code.jquery.com/jquery-3.3.1.min.js"></script> -->
	    <script src="https://code.jquery.com/jquery.min.js"></script>
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.js"></script>



    <style>

      ::-webkit-scrollbar { 
      
          display: none; 
      
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


     
   
   function chatListFunction(){
      $('#participant_list').empty();
      
      var num = ${challenge.challenge_num};
      $.ajax({
         type: "POST",
         url: "read_participant",
         data: {num:num},
         success: function(data){
//             alert(data);
            var parsed = JSON.parse(data);
//             alert(parsed);
            var result = parsed.result;
//             alert(result);
            
            $('#party').text("참가자 "+result.length+"명");
            for(var i = 0 ; i < result.length; i++){
               addparticipan(result[i][0].value, result[i][1].value);
//                alert(result[i][0].value +"    "+ result[i][1].value);   
            }
         }
      });
   }
   
   function addparticipan(nickname, profile){

      $('#participant_list').append('<div class="row" style="padding-left:20px; margin:0;">' +
            '<div class="media">' + 
            '<a class="pull-left" href="#">' + 
            '<img class ="media-object img-circle" style="width:30px; height:30px;" src='+profile+".jpg"+' alt="">' +
//             '<img class ="media-object img-circle" style="width:30px; height:30px;" src="/img/profile.png" alt="">' +   
            '</a>' + 
            '<div class="media-body">' + 
            '<h5 class="media-heading" style="padding-left: 5px;">' + 
            nickname + 
            '</h4>' + 
            '</div>' + 
            '</div>' + 
            '</div>' + 
            '<hr>');
      
//       var objDiv = document.getElementById("participant_list");
//       objDiv.scrollTop = objDiv.scrollHeight;
   }


   function imagelist(){
      $('#image').empty();
            
      var num = ${challenge.challenge_num};
         $.ajax({
            type: "POST",
            url: "read_imagelist",
            data: {num:num},
            success: function(data){
      //            alert(data);
               var parsed = JSON.parse(data);
      //            alert(parsed);
               var result = parsed.result;
      //            alert(result);
               
               for(var i = 0 ; i < result.length; i++){
                  addimage(result[i][0].value, result[i][1].value, result[i][2].value, result[i][3].value, i);
      //               alert(result[i][0].value +"    "+ result[i][1].value);   
               }
            }
         });
   }

   function addimage(title, comment, image, time,i){

      $('#image').append('<div class="col-md-4" id='+i+'>' +
            '<div class="card">' +
            '<img src="/feed/'+image+'" class="card-img-top" alt="...">' +
            '<div class="card-body">' +
            '<h5 class="card-title">'+title+'</h5>' +
            '<p class="card-text">'+comment+'</p>' +
            '</div>' +
            '<div class="card-footer">' +
            '<small class="text-muted">'+time+'</small>' +
            '</div>' +
            '</div>' +
            '</div>'
            );

//       var objDiv = document.getElementById("participant_list");
//       objDiv.scrollTop = objDiv.scrollHeight;

   }


   function getInfiniteChat(){
      setInterval(function() {
         chatListFunction();
         imagelist();
      },10000);
   }
   


   //상금 지급 버튼
   function submit_reward(num){

	           $.ajax({
	                type:'post',
	                url:'submit_reward',
	                data: ({num:num}),
	                success:function(data){
	                   if(data == 0)
	                      alert("에러");
	                   else
	                   {
	                      document.location.href="reward_challenge?page=1";
	                      //알림 띄우기;
	                   }
	                }

	             });
   }

</script>


<style>
    .menu a{cursor:pointer;}
    .menu .hide{display:none;}

</style>
  
<script type="text/javascript">
    // html dom 이 다 로딩된 후 실행된다.
    $(document).ready(function(){
        // menu 클래스 바로 하위에 있는 a 태그를 클릭했을때
        $(".menu>button").click(function(){
           var submenu = $(this).next("ul");
           
            // submenu 가 화면상에 보일때는 위로 보드랍게 접고 아니면 아래로 보드랍게 펼치기
            if( submenu.is(":visible") ){
                submenu.slideUp();
                $("#tab").text("▼");
            }else{
                submenu.slideDown();
                $("#tab").text("▲");
            }
        });
    });

</script>



<style type="text/css">
       
       a #MOVE_TOP_BTN {
       position: fixed;
       right: 2%;
       bottom: 50px;
       display: none;
       z-index: 999;
   }

</style>

<script type="text/javascript">
$(function() {
    $(window).scroll(function() {
        if ($(this).scrollTop() > 300) {
            $('#MOVE_TOP_BTN').fadeIn();
        } else {
//             $('#MOVE_TOP_BTN').fadeOut();
        }
    });
    
    $("#MOVE_TOP_BTN").click(function() {
        $('html, body').animate({
            scrollTop : 0
        }, 400);
        return false;
    });
});
</script>



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


<style type="text/css">/* Chart.js */
@-webkit-keyframes chartjs-render-animation{from{opacity:0.99}to{opacity:1}}@keyframes chartjs-render-animation{from{opacity:0.99}to{opacity:1}}.chartjs-render-monitor{-webkit-animation:chartjs-render-animation 0.001s;animation:chartjs-render-animation 0.001s;}</style>


<style type="text/css">
.nav-item a{ 
  color: black; 
</style>

<style>

html,body {height:100%;}
.container-fluid{height: 100%;}
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
            <a class="nav-link active" href="#" onclick="register()">
              <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-home"><path d="M3 9l9-7 9 7v11a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2z"></path><polyline points="9 22 9 12 15 12 15 22"></polyline></svg>
              	승인 대기 <span class="sr-only">(current)</span>
            </a>
          </li>
          <li class="nav-item" style="margin-bottom: 15px;">
            <a class="nav-link" href="#" onclick="vote()">
              <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-file"><path d="M13 2H6a2 2 0 0 0-2 2v16a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V9z"></path><polyline points="13 2 13 9 20 9"></polyline></svg>
              	투표중
            </a>
          </li>
          <li class="nav-item" style="margin-bottom: 15px;">
            <a class="nav-link" href="#" onclick="ongoing()">
              <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-shopping-cart"><circle cx="9" cy="21" r="1"></circle><circle cx="20" cy="21" r="1"></circle><path d="M1 1h4l2.68 13.39a2 2 0 0 0 2 1.61h9.72a2 2 0 0 0 2-1.61L23 6H6"></path></svg>
              	진행중
            </a>
          </li>
          <li class="nav-item" style="margin-bottom: 15px;">
            <a class="nav-link" href="#" onclick="reward()">
              <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-users"><path d="M17 21v-2a4 4 0 0 0-4-4H5a4 4 0 0 0-4 4v2"></path><circle cx="9" cy="7" r="4"></circle><path d="M23 21v-2a4 4 0 0 0-3-3.87"></path><path d="M16 3.13a4 4 0 0 1 0 7.75"></path></svg>
              	상금 처리
            </a>
          </li>
          <li class="nav-item" style="margin-bottom: 15px;">
            <a class="nav-link" href="#"  onclick="end()" style="color: red;">
              <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-layers"><polygon points="12 2 2 7 12 12 22 7 12 2"></polygon><polyline points="2 17 12 22 22 17"></polyline><polyline points="2 12 12 17 22 12"></polyline></svg>
              	종료
            </a>
          </li>
          <li class="nav-item" style="margin-bottom: 15px;">
            <a class="nav-link" href="#" onclick="state()">
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
        <h1 class="h2">상금 처리</h1> 
        <div class="btn-toolbar mb-2 mb-md-0">

<!-- 		게시판 위  -->

        </div>
      </div>

      <div class="my-4 w-100"  width="1083" height="457" style="display: block; height: 366px; width: 867px;">
      
      
      <div class="row">
      	<div class="col-10">
      
    <table class="table" style="text-align: center;">
    <thead class="table" style="background-color: #333333;">
    <tr style="color: white;">
      <th scope="col" style="vertical-align:middle; font-size: 15pt;" colspan="6">비공개 트라잇 상세정보</th>
    </tr>
    
  </thead>
  <tbody>
 
         <tr>
<%--          ${dto.challenge_first_image} --%>
            <td rowspan="8" width="350px;" height="400px" style="border-right: 1px solid #dee2e6;"><img alt="" src="/${challenge.challenge_first_image}.jpg" width="100%" height="100%"></td>
            <td colspan="1" style="vertical-align: middle; border-right: 1px solid #dee2e6;">주제(카테고리)</td>
            <td colspan="3" style="vertical-align: middle;">${challenge.challenge_title} (${challenge.challenge_category})</td>
         </tr>
         <tr>
            <td style="vertical-align: middle; border-right: 1px solid #dee2e6;">기간</td>
<%--             <td colspan="3" style="vertical-align: middle;"> ${challenge.challenge_start} ~ ${challenge.challenge_end}</td> --%>
            <td colspan="3" style="vertical-align: middle;">
               <c:set var="start" value="${fn:substring(challenge.challenge_start,0,10)}"/> 
               <c:set var="end" value="${fn:substring(challenge.challenge_end,0,10)}"/>
               ${start} ~ ${end}
            </td>
         </tr>
         <tr>
            <td style="vertical-align: middle; border-right: 1px solid #dee2e6;">인증 방법</td>
            <td colspan="3" style="vertical-align: middle;">${challenge.challenge_type}</td>
         </tr>
         
         <tr>
            <td style="vertical-align: middle; border-right: 1px solid #dee2e6;">인증 빈도</td>
            <td colspan="3" style="vertical-align: middle;">${challenge.challenge_frequency}</td>
         </tr>
         
         <tr>
            <td style="vertical-align: middle; border-right: 1px solid #dee2e6;">인증 시간</td>
            <td colspan="3" style="vertical-align: middle;">${challenge.challenge_time}</td>
         </tr>
         
         <tr>
            <td style="vertical-align: middle; border-right: 1px solid #dee2e6;">참가비</td>
            <td colspan="3" style="vertical-align: middle;">${challenge.challenge_fee} 원</td>
         </tr>
         
         <tr>
            <td style="vertical-align: middle; border-right: 1px solid #dee2e6;">신청일</td>
            <td colspan="3" style="vertical-align: middle;">${challenge.challenge_date}</td>
         </tr>
         
         <tr>
            <td style="vertical-align: middle; border-right: 1px solid #dee2e6;">개설자</td>
            <td colspan="3" style="vertical-align: middle;">${challenge.challenge_host}</td>
         </tr>
         
         
         <tr>
            <td colspan="4">
               
               <script type="text/javascript">

                   <jsp:useBean id="now" class="java.util.Date" />
                   <fmt:formatDate value="${now}" pattern="yyyy-MM-dd" var="today" />
                   
                    <fmt:parseDate var="day" value="${today}"  pattern="yyyy-MM-dd"/>
                    
                   <fmt:parseDate var="startDate_D"  value="${start}" pattern="yyyy-MM-dd"/>
                   <fmt:parseDate var="endDate_D" value="${end}"  pattern="yyyy-MM-dd"/>
                   
                    
                   <fmt:parseNumber var="nowdate" value="${day.time / (1000*60*60*24)}" integerOnly="true" />
                   <fmt:parseNumber var="strDate" value="${startDate_D.time / (1000*60*60*24)}" integerOnly="true" />
                   <fmt:parseNumber var="endDate" value="${endDate_D.time / (1000*60*60*24)}" integerOnly="true" />
                      
               </script>
               
               <div class="progress">
                  <c:if test="${ ((nowdate - strDate) / (endDate - strDate)) * 100 >= 0}">
                     <div id="bar" class="progress-bar progress-bar-striped bg-danger progress-bar-animated" role="progressbar" aria-valuenow="0" aria-valuemin="0" aria-valuemax="100" style="width: 0%"><fmt:formatNumber type="number" pattern="0.0" value="${ ((nowdate - strDate) / (endDate - strDate)) * 100}"/>% 진행중</div>
                  </c:if>
                  <c:if test="${ ((nowdate - strDate) / (endDate - strDate)) * 100 < 0}">
                     <div style="width: 100%">참가자 모집중</div>
                  </c:if>
               </div>
                
                <script type="text/javascript">
                  var c = ${ ((nowdate - strDate) / (endDate - strDate)) * 100};

                   if( c >= 100){
                      $("#bar").css('width','100%');
                      $("#bar").text("100% 완료");
                   }
                   else{
                      $("#bar").css('width','${ ((nowdate - strDate) / (endDate - strDate)) * 100}%');
                  }
                </script>
               
            </td>
         </tr>
         
         
         
         <tr>
            <td colspan="4" style="vertical-align: middle; background-color: #333333; color: white; font-weight: bold;">트라잇 규칙</td>
         </tr>
         
         <c:if test="${challenge.challenge_public == 0}">
               <tr>
               <td colspan="4" rowspan="2" height="200px;" style="vertical-align: middle;">
                     <c:if test="${challenge.challenge_detail=='null'}">
                     		-
                     </c:if>
                     <c:if test="${challenge.challenge_detail!='null'}">
                     		${challenge.challenge_detail}
                     </c:if>
               </td>
            </tr>
         </c:if>
         <c:if test="${challenge.challenge_public == 1}">
               <tr>
               <td colspan="4" rowspan="2" height="200px;" style="vertical-align: middle;">
                    		 비공개 트라잇
               </td>
            </tr>              
         </c:if>

   
         <tr></tr>
         
         <tr>
            <td colspan="4" align="right">
<%--                <button class="btn btn-outline-primary" type="button" onclick="approval('${challenge.challenge_num}');">승인</button> --%>
<!--                <button class="btn btn-outline-primary" type="button" onclick="postpone();" >보류</button> -->
<%--                <button class="btn btn-outline-primary" type="button" onclick="javascript:window.location='register_challenge?page=<%= session.getAttribute("cpage") %>';" >목록보기</button> --%>
            </td>
         </tr>
         <tr>
            <th scope="col" colspan="4" style="vertical-align:middle; background-color: #333333; font-size: 13pt; color: white;">상금 처리</th>
         </tr>
         <tr>
            <td>참가자 아이디</td>
            <td>달성률</td>
            <td>반환될 참가비</td>
            <td>상금</td>
         </tr>
         
         <c:forEach items="${Record}" var="record">
            <tr>
               <td>${record.member_id}</td>
               <td>
                  <fmt:formatNumber value="${(record.certificate_count / record.all_count)*100}" pattern=".0" var="per"/>
                  <c:if test="${per == 100.0}">
                     100%
                  </c:if>
                  <c:if test="${per != 100.0}">
                     ${per}%
                  </c:if>
               </td>
               <td>
<%--                   <fmt:formatNumber value="${record.challenge_fee * (per/100)}" pattern="0" var="reward"/>                --%>
<%--                   ${reward}원 --%>
                  ${record.challenge_fee}원
               </td>
               <td>
                  <c:if test="${per == 100.0}">
                     ${record.reward}원
                  </c:if>
                  <c:if test="${per != 100.0}">
                     
                  </c:if>
               </td>
            </tr>         
         </c:forEach>
         
         <tr>
         	<td colspan="4" style="vertical-align:middle;" align="right"><button class="btn btn-outline-primary" onclick="submit_reward(${challenge.challenge_num})">상금 처리</button>
         	<button class="btn btn-outline-primary" type="button" onclick="javascript:window.location='reward_challenge?page=<%= session.getAttribute("cpage") %>';" >목록보기</button></td>
         </tr>
            
         <tr>
            <td colspan="4"></td>
         </tr>
         
         
         <tr>
            <td colspan="4" align="center">
               <li class="menu"  style="list-style: none; align-content: center;">
                  <button class="btn btn-outline-primary" type="button" onclick="" style="margin-bottom: 15px;" id="tab">▼</button>
                     <ul class="hide" style="list-style: none; text-align: left; padding:0; margin: 0;">
                         <li>

                        <div class="row">
                           <div class="card-deck" id="image">
                        
                             </div>
                        </div>
                         </li>
                     </ul>
                 </li>
            </td>
         </tr>
         <tr>
            <td colspan="4" align="right">
               <a id="MOVE_TOP_BTN" href="#">TOP</a>
            </td>
         </tr>
         
  </tbody>
  </table>
      
      	</div>
      	
      	
      	
      	<div class="col-2">
      
      <!--참여자 리스트   -->
    <div style="line-height:37px; height:37px; color: white; font-size: 14pt; background-color: #333333; vertical-align:middle; text-align: center; border-top: 2px solid #dee1e6; border-left: 2px solid #dee1e6; border-right: 2px solid #dee1e6;" id="party">참가자 </div>
    <div id="participant" style="text-align: center; border: 2px solid #dee1e6;">
       <div id="participant_list" style="overflow-y:auto; height: 300px;" >
       
       </div>
    </div>

<br><br><br>

				<div style="line-height:37px; height:37px; color: white; font-size: 14pt; background-color: #333333; vertical-align:middle; text-align: center; border-top: 2px solid #dee1e6; border-left: 2px solid #dee1e6; border-right: 2px solid #dee1e6;">채팅방</div>
				<textarea rows="12" id="result" readonly style="border:2px solid #dee1e6; resize: none; width: 100%;"></textarea>

   
<br/><br/><br/>
   
   
   <table style="border: 2px solid #dee1e6; width: 100%">
      <tr align="center"><th colspan="2">상금 계산</th></tr>
      <tr>
         <td>총 참가비</td>
         <td>${total}원</td>
      </tr>
      <tr>
         <td>미달액</td>
         <td>${result}원</td>
      </tr>
      <tr>
         <td>수수료</td>
         <td>${ss}원</td>
      </tr>
      <tr>
         <td>총 상금액</td>
         <td>${totalreward}원</td>
      </tr>
      <tr>
         <td>성공인원</td>
         <td>${count}명</td>
      </tr>
      <tr>
         <td>1인 상금</td>
         <c:if test="${count==0}">
         	<td>0원</td>
         </c:if>
         <c:if test="${count!=0}">
         	<td><fmt:formatNumber value="${totalreward/count}" pattern="0"/>원</td>
         </c:if>
<%--          <td><fmt:formatNumber value="${totalreward/count}" pattern="0"/>원</td> --%>
      </tr>
   </table>
      
      	</div>
      </div>
      
      
      
      </div>

    </main>
  </div>
</div>
      


<!--    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script> -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
    
   
   
   <!-- 지속해서 실행 되어야 하는 부분 -->
      <script type="text/javascript">
         $(document).ready(function() {
            getInfiniteChat();
//             chatListFunction();
//             imagelist();
         });

         $(window).on('load', function(){
            chatListFunction();
            imagelist();
         });
      </script>
      
      
</body>




<!-- The core Firebase JS SDK is always required and must be listed first -->
<script src="https://www.gstatic.com/firebasejs/7.5.2/firebase-app.js"></script>
<script src="https://www.gstatic.com/firebasejs/7.5.2/firebase-analytics.js"></script>

  <!-- Add Firebase products that you want to use -->
  <script src="https://www.gstatic.com/firebasejs/7.5.2/firebase-auth.js"></script>
  <script src="https://www.gstatic.com/firebasejs/7.5.2/firebase-firestore.js"></script>
  <script src="https://www.gstatic.com/firebasejs/7.5.2/firebase-database.js"></script>
  
<script>
  // Your web app's Firebase configuration
  var firebaseConfig = {
    apiKey: "AIzaSyDdhCOZel4_q8LAWJaRSsMn1ACigJ9JrBk",
    authDomain: "project-71fd1.firebaseapp.com",
    databaseURL: "https://project-71fd1.firebaseio.com",
    projectId: "project-71fd1",
    storageBucket: "project-71fd1.appspot.com",
    messagingSenderId: "19758304536",
    appId: "1:19758304536:web:03ec3565bbc7884b18a26d",
    measurementId: "G-PGJ9N4E202"
  };
  
  // Initialize Firebase
  firebase.initializeApp(firebaseConfig);
  firebase.analytics();

  
  var a = document.getElementById("result");
  
  var database = firebase.database();
  
      var messageRef = database.ref(${challenge.challenge_num});
      messageRef.on('child_added', function(snapshot) {
        var data = snapshot.val();
        var name = data.id;
        var message = data.text;

        var b = name + "  :  " + message +"\n";
         
           a.value += b;
           document.getElementById("result").scrollTop = document.getElementById("result").scrollHeight;
      });
  

  
</script>
</html>


