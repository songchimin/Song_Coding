<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <link href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.7.1/css/bootstrap-datepicker.min.css" rel="stylesheet"/>
<!--     <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous"></head> -->

<body>

<jsp:include page="/header.jsp" />


<div class="row" style="margin: 50px; min-width: 1050px;">

<div class="col-12">

<div class="row">
  <div class="col-2">
<!--     <div class="list-group" id="list-tab" role="tablist"> -->
<!--       <a class="list-group-item list-group-item-action" data-toggle="list" href="/zzzz/member.do?page=0" role="tab" aria-controls="home">회원관리</a> -->
<!--       <a class="list-group-item list-group-item-action active" id="list-profile-list" data-toggle="list" href="/zzzz/admin/boradManagement.jsp" role="tab" aria-controls="profile">게시물조회</a> -->
<!--       <a class="list-group-item list-group-item-action" id="list-messages-list" data-toggle="list" href="#list-messages" role="tab" aria-controls="messages">Messages</a> -->
<!--       <a class="list-group-item list-group-item-action" id="list-settings-list" data-toggle="list" href="#list-settings" role="tab" aria-controls="settings">Settings</a> -->
<!--     </div> -->
  	<ul class="nav nav-pills" style="display: block;">
		  <li class="nav-item">
		    <a class="nav-link" href="/zzzz/member.do?page=0">회원관리</a>
		  </li>
		  <li class="nav-item">
		    <a class="nav-link active" href="/zzzz/admin/boradManagement.jsp">회원랭킹</a>
		  </li>
		  <li class="nav-item">
		    <a class="nav-link" href="">게시물조회</a>
		  </li>
		  <li class="nav-item">
		    <a class="nav-link disabled" href="#" tabindex="-1" aria-disabled="true">Disabled</a>
		  </li>
	</ul>
  </div>
  <div class="col-10">
    <div class="tab-content" id="nav-tabContent">
      <div class="tab-pane fade" id="list-home" role="tabpanel" aria-labelledby="list-home-list">
      			
      </div>
      
      <div class="tab-pane fade show active" id="list-profile" role="tabpanel" aria-labelledby="list-profile-list">
      	
      	<div class="container">
		    <div class="row" style="">
		    	<table class="table">
		    		<tr>
		    			
		    			<td colspan="9"  style="text-align: right;">
		    				<div style="display:inline-block;">
					        	<form action="/zzzz/rank.do" method="post">
						            <input class="form-control" data-date-format="yyyy-mm-dd" name="start" id="datepicker" style="display: inline-block; width: 180px;">
						            <img src="/zzzz/img/minus.png">
						            <input class="form-control" data-date-format="yyyy-mm-dd" name="end" id="datepicker2" style="display: inline-block; width: 180px;">
						            <input class="btn btn-primary" type="submit" value="조회" style="margin-bottom: 5px;">
					        	</form>
		        			</div>
		    			</td>
		    		</tr>
		    	
		    		<tr style="background-color: #007BFF;">
		    			<td colspan="9" style="text-align: center;"><h2 style="color: white;">자유게시판 게시물 작성 순위 Top3</h2></td>
		    		</tr>
<!-- 		    		<tr> -->
<!-- 		    			<td colspan="9"  style="text-align: center;"> -->
<!-- 		    				<div style="display:inline-block;"> -->
<!-- 					        	<form action="/zzzz/rank.do" method="post"> -->
<!-- 						            <input class="form-control" data-date-format="yyyy-mm-dd" name="start" id="datepicker" style="display: inline-block; width: 180px;"> -->
<!-- 						            <img src="/zzzz/img/minus.png"> -->
<!-- 						            <input class="form-control" data-date-format="yyyy-mm-dd" name="end" id="datepicker2" style="display: inline-block; width: 180px;"> -->
<!-- 						            <input class="btn btn-primary" type="submit" value="조회" style="margin-bottom: 5px;"> -->
<!-- 					        	</form> -->
<!-- 		        			</div> -->
<!-- 		    			</td> -->
<!-- 		    		</tr> -->
		    		<c:forEach items="${Brank}" var="i" end="2" varStatus="status">
		    		<tr>	
		    				<td>${status.count}위</td>
							<td>${i.start} ~ ${i.end}</td>
					    	<td>${i.bid}(${i.name})님이 가장 많은 게시물을 남겼습니다.(${i.bcount}개)</td>
					    	<% session.removeAttribute("Brank"); %>
		    		</tr>
		    		</c:forEach>
					<tr>
						<td colspan="9"></td>
					</tr>
		    	</table>
		    </div>
		</div>
		
		
		
				<div class="container" style="margin-top: 30px;">
		    <div class="row" style="">
		    	<table class="table">
		    		<tr style="background-color: #007BFF;">
		    			<td colspan="9" style="text-align: center;"><h2 style="color: white;">사진게시판에 게시물 작성 순위 Top3</h2></td>
		    		</tr>
<!-- 		    		<tr> -->
<!-- 		    			<td colspan="9"  style="text-align: center;"> -->
<!-- 		    				<div style="display:inline-block;"> -->
<!-- 					        	<form action="/zzzz/crank.do" method="post"> -->
<!-- 						            <input class="form-control" data-date-format="yyyy-mm-dd" name="start" id="datepicker5" style="display: inline-block; width: 180px;"> -->
<!-- 						            <img src="/zzzz/img/minus.png"> -->
<!-- 						            <input class="form-control" data-date-format="yyyy-mm-dd" name="end" id="datepicker6" style="display: inline-block; width: 180px;"> -->
<!-- 						            <input class="btn btn-primary" type="submit" value="조회" style="margin-bottom: 5px;"> -->
<!-- 					        	</form> -->
<!-- 		        			</div> -->
<!-- 		    			</td> -->
<!-- 		    		</tr> -->
		    		<c:forEach items="${Prank}" var="i" end="2" varStatus="status">
		    		<tr>	
		    				<td>${status.count}위</td>
							<td>${i.start} ~ ${i.end}</td>
					    	<td>${i.bid}(${i.name})님이 가장 많은 게시물을 남겼습니다.(${i.bcount}개)</td>
					    	<% session.removeAttribute("Prank"); %>
		    		</tr>
		    		</c:forEach>
					<tr>
						<td colspan="9"></td>
					</tr>
		    	</table>
		    </div>
		</div>
		
		
		
		
		
<!-- 		댓글 -->
		
		<div class="container" style="margin-top: 30px;">
		    <div class="row" style="">
		    	<table class="table">
		    		<tr style="background-color: #007BFF;">
		    			<td colspan="9" style="text-align: center;"><h2 style="color: white;">댓글을 작성 순위 Top3</h2></td>
		    		</tr>
<!-- 		    		<tr> -->
<!-- 		    			<td colspan="9"  style="text-align: center;"> -->
<!-- 		    				<div style="display:inline-block;"> -->
<!-- 					        	<form action="/zzzz/crank.do" method="post"> -->
<!-- 						            <input class="form-control" data-date-format="yyyy-mm-dd" name="start" id="datepicker3" style="display: inline-block; width: 180px;"> -->
<!-- 						            <img src="/zzzz/img/minus.png"> -->
<!-- 						            <input class="form-control" data-date-format="yyyy-mm-dd" name="end" id="datepicker4" style="display: inline-block; width: 180px;"> -->
<!-- 						            <input class="btn btn-primary" type="submit" value="조회" style="margin-bottom: 5px;"> -->
<!-- 					        	</form> -->
<!-- 		        			</div> -->
<!-- 		    			</td> -->
<!-- 		    		</tr> -->
		    		<c:forEach items="${Crank}" var="i" end="2" varStatus="status">
		    		<tr>	
		    				<td>${status.count}위</td>
							<td>${i.start} ~ ${i.end}</td>
					    	<td>${i.bid}(${i.name})님이 가장 많은 게시물을 남겼습니다.(${i.bcount}개)</td>
					    	<% session.removeAttribute("Crank"); %>
		    		</tr>
		    		</c:forEach>
					<tr>
						<td colspan="9"></td>
					</tr>
					<tr>
						<td colspan="9"></td>
					</tr>
		    	</table>
		    </div>
		</div>

      </div>
      <div class="tab-pane fade" id="list-messages" role="tabpanel" aria-labelledby="list-messages-list">...</div>
      <div class="tab-pane fade" id="list-settings" role="tabpanel" aria-labelledby="list-settings-list">...</div>
    </div>
  </div>
</div>

</div>
</div>


<!-- <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script> -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.7.1/js/bootstrap-datepicker.min.js"></script>
<!-- <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script> -->

<script type="text/javascript">

    $('#datepicker').datepicker({
        weekStart: 0,
        daysOfWeekHighlighted: "6,0",
        autoclose: true,
        todayHighlight: true
    });
    $('#datepicker').datepicker("setDate", new Date());

    $('#datepicker2').datepicker({
        weekStart: 0,
        daysOfWeekHighlighted: "6,0",
        autoclose: true,
        todayHighlight: true
    });
    $('#datepicker2').datepicker("setDate", new Date());
    
//     $('#datepicker3').datepicker({
//         weekStart: 0,
//         daysOfWeekHighlighted: "6,0",
//         autoclose: true,
//         todayHighlight: true
//     });
//     $('#datepicker3').datepicker("setDate", new Date());

//     $('#datepicker4').datepicker({
//         weekStart: 0,
//         daysOfWeekHighlighted: "6,0",
//         autoclose: true,
//         todayHighlight: true
//     });
//     $('#datepicker4').datepicker("setDate", new Date());
//     $('#datepicker5').datepicker({
//         weekStart: 0,
//         daysOfWeekHighlighted: "6,0",
//         autoclose: true,
//         todayHighlight: true
//     });
//     $('#datepicker5').datepicker("setDate", new Date());

//     $('#datepicker6').datepicker({
//         weekStart: 0,
//         daysOfWeekHighlighted: "6,0",
//         autoclose: true,
//         todayHighlight: true
//     });
//     $('#datepicker6').datepicker("setDate", new Date());
</script>


</body>
</html>