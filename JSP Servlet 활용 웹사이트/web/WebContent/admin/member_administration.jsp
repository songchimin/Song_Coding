<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%> 
<%	
	session.setAttribute("location", "member_administration");
	String name = (String)session.getAttribute("name");
	String id = (String)session.getAttribute("id");
%>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

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

</head>
<body>
	<jsp:include page="/header.jsp" />


<div class="row" style="margin: 50px; min-width: 1050px;">

<div class="col-12">

<div class="row">
  <div class="col-2">
<!--     <div class="list-group" id="list-tab" role="tablist"> -->
<!--       <a class="list-group-item list-group-item-action active" id="list-home-list" data-toggle="list" href="#list-home" role="tab" aria-controls="home">회원관리</a> -->
<!--       <a class="list-group-item list-group-item-action" id="list-profile-list" data-toggle="list" href="javascript:window.location='/zzzz/admin/boradManagement.jsp'" role="tab" aria-controls="profile">게시물 관리</a> -->
<!--       <a class="list-group-item list-group-item-action" id="list-messages-list" data-toggle="list" href="#list-messages" role="tab" aria-controls="messages">Messages</a> -->
<!--       <a class="list-group-item list-group-item-action" id="list-settings-list" data-toggle="list" href="#list-settings" role="tab" aria-controls="settings">Settings</a> -->
<!--     </div> -->
  	<ul class="nav nav-pills" style="display: block;">
		  <li class="nav-item">
		    <a class="nav-link active" href="/zzzz/member.do?page=0">회원관리</a>
		  </li>
		  <li class="nav-item">
		    <a class="nav-link" href="/zzzz/admin/boradManagement.jsp">회원랭킹</a>
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
      <div class="tab-pane fade show active" id="list-home" role="tabpanel" aria-labelledby="list-home-list">
      			
<table class="table" style="text-align: center;">
  <thead class="table" style="background-color: #007BFF;">
    <tr style="color: white;">
      <th scope="col">아이디</th>
      <th scope="col">비밀번호</th>
      <th scope="col">이름</th>
      <th scope="col">이메일</th>
      <th scope="col">가입일자</th>
      <th scope="col">주소</th>
      <th scope="col">로그인 정지</th>
      <th scope="col">강제 탈퇴</th>
    </tr>
  </thead>
  <tbody>
    		<c:forEach items="${member}" var="dto">
			<tr>
				<th scope="row">${dto.id}
				<td>${dto.pw}</td>
				<td>${dto.name}</td>
				<td>${dto.eMail}</td>
				<td>${dto.rDate}</td>
				<td>${dto.address}</td>
				<td>
				
					<c:if test="${dto.id!='admin'}">
						<form action="ban.doo?id=${dto.id}" method="post">
							<c:if test="${dto.ban==0}">
								<button class="btn btn-primary btn-block" type="submit" value="차단" name="O">차단</button>
	<!-- 							<input type="button" value="O" name="O"> -->
							</c:if>
						</form>
						<form action="unban.doo?id=${dto.id}" method="post">
							<c:if test="${dto.ban==1}">
								<button class="btn btn-primary btn-block" type="submit" value="해제" name="X">차단해제</button>
	<!-- 							<input type="button" value="X" name="X"> -->
							</c:if>
						</form>
					</c:if>	
				
				</td>
				<td>
					<form action="delet_account.doo?id=${dto.id}" method="post">
						<c:if test="${dto.id!='admin'}">
							<input class="btn btn-primary btn-block" type="submit" name="탈퇴" value="탈퇴">
						</c:if>
					</form>
				</td>
				
				
			</tr>
			</c:forEach>

			
			<tr>
				
					<td colspan="3">
					</td>
				<form action="member.do" method="post" name="find">
						<td colspan="1">
						<select class="custom-select" name="findoption" id ="findoption">
							<option selected>검색옵션</option>
							<option value="아이디">아이디</option>
							<option value="이름">이름</option>
						</select>
						</td>
						<td colspan="2">
						<div class="input-group">
							<input type="text" class="form-control" name="findtext" id="findtext">
							<button onclick="find()">검색</button>
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
				<td colspan="8" aria-label="Page navigation example" align="center">
				<div class="row align-items-center">
				<ul class="pagination">
				    <li class="page-item">
					   	<c:choose>
						<c:when test="${(page.curPage - 1) < 1}">
							<a class="page-link" href="#">[ &lt;&lt; ]</a>
						</c:when>
						<c:otherwise>
							<a class="page-link" href="/zzzz/member.do?page=1">[ &lt;&lt; ]</a>
						</c:otherwise>
						</c:choose>
				   	</li>
				   	
				   	
				    <li class="page-item">
	    				<c:choose>
						<c:when test="${(page.curPage - 1) < 1}">
							<a class="page-link"href="#">[ &lt; ]</a>
						</c:when>
						<c:otherwise>
							<a class="page-link"href="/zzzz/member.do?page=${page.curPage - 1}">[ &lt; ]</a>
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
								<a class="page-link"href="/zzzz/member.do?page=${fEach}">[ ${fEach} ]</a>
							</c:otherwise>
							</c:choose>
							</li>
						</c:forEach>
				    
				   

				    <li class="page-item" style="display: inline; float: left;">
	    				<c:choose>
						<c:when test="${(page.curPage +1) >page.totalPage }">
							<a class="page-link"href="#">[ &gt; ]</a>
						</c:when>
						<c:otherwise>
							<a class="page-link" href="/zzzz/member.do?page=${page.curPage + 1}">[ &gt; ]</a>
						</c:otherwise>
						</c:choose>
				    </li>			



					<li class="page-item">
					   	<c:choose>
						<c:when test="${page.curPage == page.totalPage}">
							<a class="page-link" href="#">[ &gt;&gt; ]</a>
						</c:when>
						<c:otherwise>
							<a class="page-link" href="/zzzz/member.do?page=${page.totalPage}">[ &gt;&gt; ]</a>
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
      <div class="tab-pane fade" id="list-profile" role="tabpanel" aria-labelledby="list-profile-list">...</div>
      <div class="tab-pane fade" id="list-messages" role="tabpanel" aria-labelledby="list-messages-list">...</div>
      <div class="tab-pane fade" id="list-settings" role="tabpanel" aria-labelledby="list-settings-list">...</div>
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