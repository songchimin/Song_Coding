<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.study.jsp.dao.copy.PDao"%>
<%@ page import="com.study.dto.PDto"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 

<%
	//if(session.getAttribute("ValidMem") == null){
%>
<%-- 	<jsp:forward page="login.jsp"/> --%>
<%
	//}
	session.setAttribute("location", "home");
	String name = (String)session.getAttribute("name");
	String id = (String)session.getAttribute("id");
	PDao dao = new PDao();
	PDto dto1 = dao.rankpicture(1);
	PDto dto2 = dao.rankpicture(2);
	PDto dto3 = dao.rankpicture(3);
%>

<!doctype html>
<html>
  <head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<script src="http://code.jquery.com/jquery-1.7.js"></script>
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">

    <title>Hello, world!</title>
    
</head>
 
 <body>

<jsp:include page="header.jsp" />

<main role="main">

  <div style="margin: 50px; min-width: 1000px;" id="myCarousel" class="carousel slide" data-ride="carousel" data-interval="2000" >
    <ol class="carousel-indicators">
      <li data-target="#myCarousel" data-slide-to="0" class=""></li>
      <li data-target="#myCarousel" data-slide-to="1" class="active"></li>
      <li data-target="#myCarousel" data-slide-to="2" class=""></li>
    </ol>
    <div class="carousel-inner">
      <div class="carousel-item">
       <img src="<%=dto1.getPath()%>" alt="" width="100%" height="600px">
        <div class="container">
          <div class="carousel-caption text-left">
            <h1>조회수 1등 사진</h1>
            <p><%=dto1.getpName()%>님의 <%=dto1.getpId()%>번 게시물(<%=dto1.getpTitle()%>)이 이번주 조회수 1등 입니다.</p>
            <p><a class="btn btn-lg btn-primary" href="content_view.po?pId=<%=dto2.getpId()%>" role="button">Show</a></p>
          </div>
        </div>
      </div>
      <div class="carousel-item active">
        <img src="<%=dto2.getPath()%>" alt=""  width="100%" height="600px">
        <div class="container">
          <div class="carousel-caption">
            <h1>조회수 2등 사진</h1>
            <p><%=dto2.getpName()%>님의 <%=dto2.getpId()%>번 게시물(<%=dto2.getpTitle()%>)이 이번주 조회수 2등 입니다.</p>
            <p><a class="btn btn-lg btn-primary" href="content_view.po?pId=<%=dto2.getpId()%>" role="button">Show</a></p>
          </div>
        </div>
      </div>
      <div class="carousel-item">
        <img src="<%=dto3.getPath()%>" alt=""  width="100%" height="600px">
        <div class="container">
          <div class="carousel-caption text-right">
            <h1>조회수 3등 사진</h1>
            <p><%=dto3.getpName()%>님의 <%=dto3.getpId()%>번 게시물(<%=dto3.getpTitle()%>)이 이번주 조회수 3등 입니다.</p>
            <p><a class="btn btn-lg btn-primary" href="content_view.po?pId=<%=dto3.getpId()%>" role="button">Show</a></p>
          </div>
        </div>
      </div>
    </div>
    <a class="carousel-control-prev" href="#myCarousel" role="button" data-slide="prev">
      <span class="carousel-control-prev-icon" aria-hidden="true"></span>
      <span class="sr-only">Previous</span>
    </a>
    <a class="carousel-control-next" href="#myCarousel" role="button" data-slide="next">
      <span class="carousel-control-next-icon" aria-hidden="true"></span>
      <span class="sr-only">Next</span>
    </a>

  </div>


  <!-- Marketing messaging and featurettes
  ================================================== -->
  <!-- Wrap the rest of the page in another container to center all the content. -->


  <!-- FOOTER -->
  <footer class="container">
    <p class="float-right"><a href="#">song song</a></p>
    <p>© 2019 Company, Inc. · <a href="#">Privacy</a> · <a href="#">Terms</a></p>
  </footer>
</main>


<!-- <div id="bb" style="display: block;"> -->
<!-- 	<form action="logoutOk.doo" method="post"> -->
<!-- <!-- 		<input type="submit" value="로그아웃">&nbsp;&nbsp;&nbsp; -->
<%-- 		<c:if test="${id!=null}"> --%>
<!--         	<input class="btn btn-outline-primary" type="button" value="정보수정" -->
<!-- 					onclick="javascript:window.location='./login/modify.jsp'"> -->
<%--         </c:if> --%>

<!-- 	</form> -->
<!-- </div> -->




    <!-- Optional JavaScript -->
    <!-- jQuery first, then Popper.js, then Bootstrap JS -->
<!--     <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script> -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
  </body>
</html>


<!-- <div class="container" id="aa" style="display: block;"> -->
<!--   <div class="row"> -->
<!--     <div class="col-md-4 offset-md-4"> -->
<!-- 	<button class="btn btn-lg btn-primary btn-block" onclick="javascript:window.location='./login/login.jsp'">로그인</button> -->
<!-- 		<div > -->
<!-- 			<a href="./login/join.jsp" class="">회원가입</a> -->
<!-- 			<span class="" ><a href="" class="lg_find_text">아이디</a>·<a href="" class="lg_find_text">비밀번호 찾기</a></span> -->
<!-- 		</div> -->
<!-- 	</div> -->
<!--     </div> -->
<!--   </div> -->