<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 

<%
	String name = (String)session.getAttribute("name");
	String userID = (String)session.getAttribute("id");
	String grade = (String)session.getAttribute("grade");
	
	String left = (String)session.getAttribute("chat_left");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<!-- <script src="http://code.jquery.com/jquery-1.7.js"></script> -->
<!-- 	<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.js"></script> -->
	<script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>

	<style>
    	
    	.notice-box { padding:10px; background-color:rgb(249, 250, 252); text-align:left; } 
    	.notice-box p { -webkit-margin-before: .3em; -webkit-margin-after: .5em; } 
    	#close { z-index: 2147483647; position:absolute; padding:2px 5px; font-weight: 700; text-shadow: 0 1px 0 #fff; font-size: 1.3rem;  left: 350px;} 
    	#close:hover { border: 0; cursor:pointer; opacity: .75; }

    </style>

    <script>
        function nwindow(){
            var url="/index?toID=admin";
            window.open(url,"","width=690,height=766,left=1200,top=100");
        }
        
        function nw2indow(){
            var url="/box_list";
            window.open(url,"","width=690,height=766,left=1200,top=100");
        }
    </script>
	
	<script type="text/javascript">
		function getUnread(){
			var userID = '<%= userID %>';
			$.ajax({
				 type: "POST",
				 url: "/chatUnread",
				 data: {
					 userID: encodeURIComponent(userID),
				 },
				 success: function(result){
					 if(result >= 1){
						 showUnread(result);
					 }else{
						showUnread(''); 
					 }
				 }
			});
		}
	
		function getInfiniteUnread() {
			setInterval(function() {
				getUnread();
			}, 4000);
		}
		function showUnread(result) {
			$('#unread').html(result);
		}
	</script>



<script type='text/javascript'>

var img_L = 0;
var img_T = 0;
var targetObj;

var count=0;

function getLeft(o){
     return parseInt(o.style.left.replace('px', ''));
}
function getTop(o){
     return parseInt(o.style.top.replace('px', ''));
}

// 이미지 움직이기
function moveDrag(e){
     var e_obj = window.event? window.event : e;
     var dmvx = parseInt(e_obj.clientX + img_L);
     var dmvy = parseInt(e_obj.clientY + img_T);
     targetObj.style.left = dmvx +"px";
     targetObj.style.top = dmvy +"px";
     return false;
}

// 드래그 시작
function startDrag(e, obj){
     targetObj = obj;
     var e_obj = window.event? window.event : e;


     if(<%=left%>==null && count != 1)
     {
    	 img_L = (getLeft(obj) * window.innerWidth )/100 - e_obj.clientX;
         img_T = (getTop(obj)* window.innerHeight)/100 - e_obj.clientY;
         count = 1;
     }
     else
     {
         img_L = getLeft(obj) - e_obj.clientX;
         img_T = getTop(obj) - e_obj.clientY;
     }
    
     document.onmousemove = moveDrag;
     document.onmouseup = stopDrag;
     if(e_obj.preventDefault)e_obj.preventDefault();
}

// 드래그 멈추기
function stopDrag(){
     document.onmousemove = null;
     document.onmouseup = null;
}


function endDrag(e, obj){
    targetObj = obj;

	$.ajax({
		type: "POST",
		url: "chat_position",
		data: {chat_left:getLeft(obj), chat_top:getTop(obj)},
		success: function(data){
		}
	});
}

</script>



<script type='text/javascript'>

// var img_L2 = 0;
// var img_T2 = 0;
// var targetObj2;

// var count2=0;

// function getLeft2(o){
//      return parseInt(o.style.left.replace('px', ''));
// }
// function getTop2(o){
//      return parseInt(o.style.top.replace('px', ''));
// }

// // 이미지 움직이기
// function moveDrag2(e){
//      var e_obj = window.event? window.event : e;
//      var dmvx = parseInt(e_obj.clientX + img_L2);
//      var dmvy = parseInt(e_obj.clientY + img_T2);
//      targetObj2.style.left = dmvx +"px";
//      targetObj2.style.top = dmvy +"px";
//      return false;
// }

// // 드래그 시작
// function startDrag2(e, obj){
//      targetObj2 = obj;
//      var e_obj = window.event? window.event : e;

//       img_L2 = getLeft(obj) - e_obj.clientX;
//       img_T2 = getTop(obj) - e_obj.clientY;

//      document.onmousemove = moveDrag2;
//      document.onmouseup = stopDrag2;
//      if(e_obj.preventDefault)e_obj.preventDefault();
// }

// // 드래그 멈추기
// function stopDrag2(){
//      document.onmousemove = null;
//      document.onmouseup = null;
// }


// function endDrag2(e, obj){
//     targetObj = obj;

// 	$.ajax({
// 		type: "POST",
// 		url: "chat_box_position",
// 		data: {chat_left:getLeft(obj), chat_top:getTop(obj)},
// 		success: function(data){
// 		}
// 	});
// }

</script>






    <script>
		function SirenFunction(idMyDiv){
		     var objDiv = document.getElementById(idMyDiv);
		     if(objDiv.style.display=="block"){ objDiv.style.display = "none"; }
		      else{ objDiv.style.display = "block"; }
		}
	</script>
    
    
        <style>
        .sir_singo_msg{color:#934545;margin-bottom:30px}
        .sir_singo_msg button {cursor:pointer;font-family:Arial,'돋움',Dotum;border:none;padding:0;background:#fff; outline:0}
        .sir_singo_msg .blind_view{font-size:1.14em;font-weight:bold;color:#ff4343;margin-top:-3px;text-decoration:underline}
        .notice-box { display:none; }
</style>


</head>
<body>

<header><!-- bg-white #FFFED7-->
	<div class="d-flex flex-column flex-md-row align-items-center p-3 px-md-4 border-bottom shadow-sm" style="background-color: #333333; margin-bottom: 0px;">
      <h5 class="my-0 mr-md-auto font-weight-normal"><a href="/home" style="color:white;"><img alt="" src="img/logo.png" width="35px" style="margin-bottom: 5px;"> ADMIN</a></h5>
           
			<c:if test="${chat_left != null}">
	     		<a id="chat" href="#" ondblclick="SirenFunction('SirenDiv'); return false;" class="blind_view" style="position:fixed; z-index: 1242145; left:${chat_left}px; top:${chat_top}px; cursor:pointer; cursor:hand;" onmousedown="startDrag(event, this)" onmouseup="endDrag(event, this)"><img alt="채팅" src="/img/chat.png"><span style="position:relative; z-index: 1242145; right: 20px; bottom: 20px; " id="unread" class="badge badge-danger"></span></a>
     		</c:if>
     		<c:if test="${chat_left == null}">
	     		<a id="chat" href="#" ondblclick="SirenFunction('SirenDiv'); return false;" class="blind_view" style="position:fixed; z-index: 1242145; left: 92%; top: 87%; cursor:pointer; cursor:hand; display:r" onmousedown="startDrag(event, this)" onmouseup="endDrag(event, this)"><img alt="채팅" src="/img/chat.png"><span style="position:relative; z-index: 1242145; right: 20px; bottom: 20px; " id="unread" class="badge badge-danger"></span></a>
     		</c:if>

	 			<!--사용자 메뉴(토글사용) -->			
	        <c:if test="${id!=null}">
				  <button class="btn btn-danger dropdown-toggle" type="button" id="dropdownMenu2" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
				    <%= name %>님
				  </button>
				  	<div class="dropdown-menu" aria-labelledby="dropdownMenu2">
				  	
				  	<button class="dropdown-item" type="button" onclick="javascript:window.location='/zzzz/login/mypage.jsp'">Mypage</button>
<!-- 				  	<button class="dropdown-item" type="button" onclick="javascript:window.location='/zzzz/login/profile.jsp'">프로필설정</button> -->
				    
				    <c:if test="${id=='admin'}">
				    	<div class="dropdown-divider"></div>
<!-- 				    	<button class="dropdown-item" type="button" onclick="javascript:window.location='/zzzz/admin/member_administration.jsp'">회원관리</button> -->
							<button class="dropdown-item" type="button" onclick="javascript:window.location='/zzzz/member.do'">회원관리</button>
				  	</c:if>
				  	<div class="dropdown-divider"></div>
				  	<form action="logoutOk" method="post">
				  		<button class="dropdown-item" type="submit">로그아웃</button>
				  	</form>
				  </div>
	        </c:if>
          
          
          
<%--       <c:if test="${id!=null}"> --%>
<!--       	<form action="logoutOk.doo" method="post"> -->
<!-- 			<input class="btn btn-danger" type="submit" value="로그아웃"> -->
<!-- 		</form> -->
<%--       </c:if> --%>
<%--      <c:if test="${id==null}"> --%>
<!--       	<a class="btn btn-danger" href="login">로그인</a> -->
<%--       </c:if> --%>
    </div>
    
      
      
     
</header>

<%
	if(userID != null){
%>
	<script type="text/javascript">
		$(document).ready(function() {
			getInfiniteUnread();	
		});
	</script>
<%
	}
%>




    <div class="con_inner" style="border-style:solid;  border: 10px; border-bottom-width:10px; border-color:#2980b9;">
	    <div class="notice-box"  id="SirenDiv" style='padding:0;  border-color:#2980b9; border-color:#2980b9; z-index: 2147483647; position:absolute; left:300px; top:100px; cursor:pointer; cursor:hand' onmousedown='startDrag(event, this)'>
		<span id='close' onclick="this.parentNode.style.display = 'none';">&times;</span>
			<iframe scrolling="no" src="/box_list" style="width: 380px; height: 540px; border: 2px solid; border-color: #2980b9; border-top-width: 15px; border-bottom-width: 3px; ">
			
			</iframe>
		</div>
	</div>

<!-- 바로 관리자 채팅 -->
<!-- <iframe scrolling="no" src="http://localhost:8081/zzzz/index.jsp?toID=admin" style="width: 420px; height: 580px; border: 2px solid; border-color: #2980b9; border-top-width: 15px; border-bottom-width: 3px; "> -->
			
<!-- 			</iframe> -->

</body>
</html>