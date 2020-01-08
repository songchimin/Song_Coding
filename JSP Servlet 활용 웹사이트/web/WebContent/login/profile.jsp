<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String id = null;
	id = (String)session.getAttribute("id");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<!-- 	<script src="../js/bootstrap.js"></script> -->
<!-- 	<link rel="stylesheet" href="../css/bootstrap.css"> -->
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
<script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
<!-- 	<link rel="stylesheet" href="../css/custom.css"> -->

	<style type="text/css">
		
 		.btn-file{ 
 			position:relative; 
 			overflow: hidden; 
		}
 		.btn-file input[type=file]{ 
 			position: absolute; 
			top: 0; 
 			right: 0; 
 			min-width:100%; 
 			min-height: 100%; 
 			text-align: right; 
 			opacity: 0; 
 			outline: none; 
 			background: white; 
 			fillter:alpha(opacity=0); 
 			cursor: inherit;
 			display: block; 
 		}
 		.file{
 			visibility: hidden;
 			position: absolute;
 		}
	</style>

</head>
<body>

<jsp:include page="/header.jsp" />

<nav>
<div class="container" style="padding: 0; margin: 0; width: 100%; height: 100%;">
<form method="post" action="../profile" enctype="multipart/form-data">
	<table class="table table-bordered table-hover" style="text-align: center; border: 1px solid #dddddd; margin: 0 auto;">
		<thead>
			<tr>
				<th colspan="3"><h4>프로필 사진 수정</h4></th>
			</tr>
			
		</thead>
		
		<tbody>
			<tr>
				<td style="width: 110px;"><h5>프로필 사진</h5></td>
				<td>
					<input type="hidden" name="id" value="<%=id%>"/>
					<input type="file" name="profile" class="file">
					<div class="input-group col-xs-12">
<!-- 						<span class="btn btn-file"></span> -->
							<span class="input-group-addon"><i style="font-size:50px;" class="glyphicon glyphicon-picture"></i></span>
							<input type="text" class="form-control input-lg" disabled="disabled" placeholder="이미지를 업로드 하세요">
							<span class="input-group-btn">
								<button class="browse btn btn-primary input-lg" type="button"><i class="glyphicon glyphicon-search">파일찾기</i></button>
							</span>
					</div>
				</td>
			</tr>
			<tr>
				<td colspan="3" style="text-align: right;"><input class="btn primary" type="submit" value="등록"></td>
			</tr>
		</tbody>
	</table>
</form>
</div>
</nav>
	<script type="text/javascript">
		$(document).on('click', '.browse', function() {
			var file = $(this).parent().parent().parent().find('.file');
			file.trigger('click');
		})
		$(document).on('change', '.file', function() {
			$(this).parent().find('.form-control').val($(this).val().replace(/C:\\fakepath\\/i, ''));
		});
	</script>

</body>
</html>