<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<script src="https://code.jquery.com/jquery-3.2.1.js"></script>
	<link rel="stylesheet" href="/css/jquery-ui.min.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css"/>
    <script src="http://netdna.bootstrapcdn.com/bootstrap/3.0.3/js/bootstrap.min.js"></script>
    <script type='text/javascript' src='//code.jquery.com/jquery-1.8.3.js'></script>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.5.0/css/bootstrap-datepicker3.min.css">
    <script type='text/javascript' src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.5.0/js/bootstrap-datepicker.min.js"></script>
    <script src="/zzzz/js/bootstrap-datepicker.kr.js" charset="UTF-8"></script>
<!-- <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous"> -->



<script type="text/javascript">

$(function(){

    $('.input-group.date').datepicker({
        calendarWeeks: false,
        todayHighlight: true,
        autoclose: true,
        format: "yyyy/mm/dd",
        language: "kr"

    });

});

</script>


</head>
<body>

<%-- <jsp:include page="/header.jsp" /> --%>


	<div class="container">
        <h1>날짜</h1>
		<div style="text-align: center; display:inline-flex;">
	        <div class="input-group date" style="width: 200px; ">
	            <input type="text" class="form-control"><span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span>
	        </div>
	        <div>&nbsp;&nbsp;&nbsp;<img style="margin-top: 10px;" src="/zzzz/img/minus.png">&nbsp;&nbsp;&nbsp;</div>
	        <div class="input-group date" style="width: 200px;">
	            <input type="text" class="form-control"><span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span>
	        </div>
	        &nbsp;&nbsp;&nbsp;
	        <input style="width: 80px" class="btn btn-primary" value="찾기">
       	</div> 
    </div>


<!--     <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script> -->
<!--     <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script> -->
</body>
</html>