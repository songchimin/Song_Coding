<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%> 
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%	
	session.setAttribute("location", "register_challenge");
	String name = (String)session.getAttribute("name");
	String id = (String)session.getAttribute("id");
	String grade = (String)session.getAttribute("grade");
	
	int a = (int)request.getAttribute("a");
	int b = (int)request.getAttribute("b");
	int c = (int)request.getAttribute("c");
	int d = (int)request.getAttribute("d");
	int e = (int)request.getAttribute("e");
	int f = (int)request.getAttribute("f");
	int g = (int)request.getAttribute("g");
	int h = (int)request.getAttribute("h");
	int i = (int)request.getAttribute("i");
	int j = (int)request.getAttribute("j");
	int k = (int)request.getAttribute("k");
	int l = (int)request.getAttribute("l");
	
	int count = (int)request.getAttribute("count");
	int totalfee = (int)request.getAttribute("totalfee");
	int totalcount = (int)request.getAttribute("totalcount");
	int totalchallenge = (int)request.getAttribute("totalchallenge");

%>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>챌린지 현황</title>


<script src="//code.jquery.com/jquery-3.3.1.min.js"></script>
    <script src="https://code.jquery.com/jquery.min.js"></script>
    <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>


<script type="text/javascript">

      google.charts.load('current', {'packages':['corechart']});
      google.charts.setOnLoadCallback(drawChart);

      function drawChart() {
    	  var song = google.visualization.arrayToDataTable([
	          ['Task', 'Hours per Day'],
	          ['역량', <%=a%>],
	          ['건강', <%=b%>],
	          ['관계', <%=c%>],
	          ['자산', <%=d%>],
	          ['생활', <%=e%>],
	          ['취미', <%=f%>]
          ]);
        
          var options = {
            title: '누적 카테고리별 챌린지 개설 수(분포)'
          };

          var chart = new google.visualization.PieChart(document.getElementById('piechart'));

          chart.draw(song, options);
      }

      google.charts.load('current', {'packages':['corechart']});
      google.charts.setOnLoadCallback(drawChart2);

      function drawChart2() {
    	  var song = google.visualization.arrayToDataTable([
	          ['Task', 'Hours per Day'],
	          ['역량', <%=g%>],
	          ['건강', <%=h%>],
	          ['관계', <%=i%>],
	          ['자산', <%=j%>],
	          ['생활', <%=k%>],
	          ['취미', <%=1%>]
          ]);
        
          var options = {
            title: '현재 진행중인 챌린지의 카테고리 분포'
          };

          var chart = new google.visualization.PieChart(document.getElementById('piechart2'));

          chart.draw(song, options);
      }
      
</script>
    
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



</head>
<body>
	<jsp:include page="../header.jsp" />


		
<div class="row" style="margin: 50px;">

<div class="col-12">

<div class="row">
  <div class="col-2">
    <div class="list-group" id="list-tab" role="tablist">
      <a class="list-group-item list-group-item-action" id="list-home-list" data-toggle="list" href="#" role="tab" aria-controls="home" onclick="register()">등록 관리</a>
      <a class="list-group-item list-group-item-action" id="list-vote-list" data-toggle="list" href="#" role="tab" aria-controls="vote" onclick="vote()">투표중인 챌린지</a>
      <a class="list-group-item list-group-item-action" id="list-profile-list" data-toggle="list" href="#" aria-controls="profile" onclick="ongoing()">진행중인 챌린지</a>
      <a class="list-group-item list-group-item-action" id="list-reward-list" data-toggle="list" href="#" aria-controls="reward" onclick="reward()">상금 지급</a>
      <a class="list-group-item list-group-item-action" id="list-messages-list" data-toggle="list" href="#" role="tab" aria-controls="messages" onclick="end()">지난 챌린지</a>
      <a class="list-group-item list-group-item-action active" id="list-settings-list" data-toggle="list" href="#list-settings" role="tab" aria-controls="settings" onclick="state()">챌린지 현황</a>
    </div>
  </div>
  <div class="col-10"> 
    <div class="tab-content" id="nav-tabContent">
      <div class="tab-pane fade" id="list-home" role="tabpanel" aria-labelledby="list-home-list">...</div>
      <div class="tab-pane fade" id="list-vote" role="tabpanel" aria-labelledby="list-vote-list">...</div>
      <div class="tab-pane fade" id="list-profile" role="tabpanel" aria-labelledby="list-profile-list">...</div>
      <div class="tab-pane fade" id="list-reward" role="tabpanel" aria-labelledby="list-reward-list">...</div>
      <div class="tab-pane fade" id="list-messages" role="tabpanel" aria-labelledby="list-messages-list">...</div>
      <div class="tab-pane fade show active" id="list-settings" role="tabpanel" aria-labelledby="list-settings-list">
      	

      	<div class="row">
      		<div id="piechart" style="width: 800px; height: 500px;"></div>
      		100% 성공자 수 : <%=count%><br>
      		챌린지 참여 총 금액 : <%=totalfee%><br>
      		챌린지 총 참여 횟수  : <%=totalcount%><br>
      		챌린지 총 개설 횟수  : <%=totalchallenge%><br>
      		
      		<fmt:formatNumber value="${sum}" pattern=".000" var="suma"/>
      		전체 평균 달성률 : ${suma*100}%
      		
      		
      	</div>
      	<div class="row">
      		<div id="piechart2" style="width: 800px; height: 500px;"></div>
      		전체 평균 성공률
      	</div>
      	<br><br>

    </div>
  </div>
</div>

</div>
</div>
</div>

<!-- 	<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script> -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
    
</body>
</html>
