<%@page import="java.net.URLDecoder"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 
<%
	String userID = null;
	String toID = null;
	userID = (String)session.getAttribute("id");
	
	if(userID == null){
		session.setAttribute("messageType", "오류 메시지");
		session.setAttribute("messageContent", "오류 메시지");
		response.sendRedirect("home.jsp");
		return;
	}
%>
    
<!DOCTYPE html>
<html>
<head>

<meta charset="UTF-8">
<title>Insert title here</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	<link rel="stylesheet" href="./css/bootstrap.css">
	<link rel="stylesheet" href="./css/custom.css">
	<script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
	<script src="./js/bootstrap.js"></script>

	<script type="text/javascript">
		function findFunction(){
			var userID = $('#findID').val();
				$.ajax({
					type: "POST",
					url: "/UserCheckServlet",
					data: { userID: userID },
					success: function(result){
						if(result == 0){
// 							$('#checkMessage').html('친구찾기 성공');
// 							$('#checkType').attr('class', 'modal-content panel-success');
							alert("성공");
							getFriend(userID);
						}else{
// 							$('#checkMessage').html('친구를 찾을 수 없습니다.');
// 							$('#checkType').attr('class', 'modal-content panel-warning');
							alert("실패");
							failFriend(userID);
						}
// 						$('#checkModal').modal("show");
					}
				});
		}
		function getFriend(findID){
			$('#friendResult').html('<thead style="background-color:#007BFF;">' + 
					'<tr>' + 
					'<th style="height: 32px; padding: 0;"><h4 style="color:white; margin: 5px;">검색결과</h4></th>' + 
					'</tr>' + 
					'</thead>' + 
					'<tbody>' + 
					'<tr>' + 
					'<td style="text-align: center;"><h3 style="margin-top:10px;">' + findID + '<a href="/zzzz/index.jsp?toID=' + encodeURIComponent(findID) +'" class="btn btn-primary pull-right">' + '메시지 보내기</h3></a></td>' + 
					'</tr>' + 
					'</tbody>');
		}
		function failFriend(findID){
			$('#friendResult').html("");
		}
	</script>
</head>
<body>


<div class="container" style="padding: 0; margin: 0; width: 100%; height: 100%;">
	<table class="table table-bordered table-hover" style="text-align: center; border: 1px solid #dddddd; margin: 0 auto;">
		<thead style="background-color:#2980b9;">
			<tr style="height: 32px;">
				<th colspan="3" style="height: 32px; padding: 0;"><h4 style="color: white; margin: 5px"><img onclick="location.href='/zzzz/box.jsp'" alt="" src="./img/arrow_back2.png">친구찾기</h4></th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td style="width: 110px;"><h5>친구 아이디</h5></td>
				<td><input class="form-control" type="text" id="findID" maxlength="20" placeholder="찾을 친구 아이디"></td>
				<td style="width: 50px;"><button class="btn btn-primary pull-right" onclick="findFunction();">검색</button></td>
			</tr>
		</tbody>
	</table>
</div>

<div>
	<table id="friendResult" class="table table-bordered table-hover" style="text-align: center; border: 1px solid #dddddd; margin: 0 auto;">
	</table>
</div>

<%
	String messageContent = null;
	if(session.getAttribute("messageContent") != null){
		messageContent = (String) session.getAttribute("messageContent");
	}
	String messageType = null;
	if(session.getAttribute("messageContent") != null){
		messageType = (String) session.getAttribute("messagetype");
	}
	if(messageContent != null){
%>
<!-- 	<div class="modal fade" id="messageModal" tabindex="-1" role="dialog" aria-hidden="true"> -->
<!-- 		<div class="vertical-alignment-helper"> -->
<%-- 			<div class="modal-content <% if(messageType.equals("오류메시지")) out.println("panel-warning"); else out.println("pannel-success"); %>"> --%>
<!-- 				<div class="modal-header panel-heading"> -->
<!-- 					<button type="button" class="close" data-dismiss="modal"> -->
<!-- 						<span aria-hidden="ture">&time</span> -->
<!-- 						<span class="sr-only">Close</span> -->
<!-- 					</button>	 -->
<!-- 					<h4 class="modal-title"> -->
<%-- 						<%= messageType %> --%>
<!-- 					</h4> -->
<!-- 				</div> -->
<!-- 				<div	class="modal-body"> -->
<%-- 					<%= messageType %> --%>
<!-- 				</div> -->
<!-- 				<div	class="modal-footer"> -->
<!-- 					<button type="button" class="btn btn-primary" data-dismiss="modal">확인</button> -->
<!-- 				</div> -->
<!-- 			</div> -->
<!-- 		</div>	 -->
<!-- 	</div> -->
	<script>
// 		$('#messageModal').modal("show");
	</script>
		<%
			session.removeAttribute("messageContent");
			session.removeAttribute("messageType");
			}	
		%>
		
<!-- 		세션과 상관없이 지속해서 실행 되어야 하는 부분 -->
		<script type="text/javascript">
// 			$(document).ready(function() {
// 				chatListFunction(0);
// 				getInfiniteChat();
// 			});
		</script>
		
</body>
</html>