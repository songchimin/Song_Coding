<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%> 
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%	
	session.setAttribute("location", "member");
	String name = (String)session.getAttribute("name");
	String id = (String)session.getAttribute("id");
	String grade = (String)session.getAttribute("grade");
%>
    
<!DOCTYPE html>
<html>

<head>

  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <meta name="description" content="">
  <meta name="author" content="">

  <title>SB Admin - Tables</title>

  <!-- Custom fonts for this template-->
  <link href="song/vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">

  <!-- Page level plugin CSS-->
  <link href="song/vendor/datatables/dataTables.bootstrap4.css" rel="stylesheet">

  <!-- Custom styles for this template-->
  <link href="song/css/sb-admin.css" rel="stylesheet">

<script src="https://code.jquery.com/jquery.min.js"></script>
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.js"></script>
<script src="//code.jquery.com/jquery-3.3.1.min.js"></script>

<style>
    .menu a{cursor:pointer;}
    .menu .hide{display:none;}
</style>
  
<script type="text/javascript">
    // html dom 이 다 로딩된 후 실행된다.
    $(document).ready(function(){
        // menu 클래스 바로 하위에 있는 a 태그를 클릭했을때
        $(".menu>a").click(function(){
            var submenu = $(this).next("ul");
 
            // submenu 가 화면상에 보일때는 위로 보드랍게 접고 아니면 아래로 보드랍게 펼치기
            if( submenu.is(":visible") ){
                submenu.slideUp();
            }else{
                submenu.slideDown();
            }
        });
    });
</script>



</head>

<body id="page-top">

  <nav class="navbar navbar-expand navbar-dark bg-dark static-top">

    <a class="navbar-brand mr-1" href="index.html">Start Bootstrap</a>

    <button class="btn btn-link btn-sm text-white order-1 order-sm-0" id="sidebarToggle" href="#">
      <i class="fas fa-bars"></i>
    </button>

    <!-- Navbar Search -->
    <form class="d-none d-md-inline-block form-inline ml-auto mr-0 mr-md-3 my-2 my-md-0">
      <div class="input-group">
        <input type="text" class="form-control" placeholder="Search for..." aria-label="Search" aria-describedby="basic-addon2">
        <div class="input-group-append">
          <button class="btn btn-primary" type="button">
            <i class="fas fa-search"></i>
          </button>
        </div>
      </div>
    </form>

    <!-- Navbar -->
    <ul class="navbar-nav ml-auto ml-md-0">
      <li class="nav-item dropdown no-arrow mx-1">
        <a class="nav-link dropdown-toggle" href="#" id="alertsDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
          <i class="fas fa-bell fa-fw"></i>
          <span class="badge badge-danger">9+</span>
        </a>
        <div class="dropdown-menu dropdown-menu-right" aria-labelledby="alertsDropdown">
          <a class="dropdown-item" href="#">Action</a>
          <a class="dropdown-item" href="#">Another action</a>
          <div class="dropdown-divider"></div>
          <a class="dropdown-item" href="#">Something else here</a>
        </div>
      </li>
      <li class="nav-item dropdown no-arrow mx-1">
        <a class="nav-link dropdown-toggle" href="#" id="messagesDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
          <i class="fas fa-envelope fa-fw"></i>
          <span class="badge badge-danger">7</span>
        </a>
        <div class="dropdown-menu dropdown-menu-right" aria-labelledby="messagesDropdown">
          <a class="dropdown-item" href="#">Action</a>
          <a class="dropdown-item" href="#">Another action</a>
          <div class="dropdown-divider"></div>
          <a class="dropdown-item" href="#">Something else here</a>
        </div>
      </li>
      <li class="nav-item dropdown no-arrow">
        <a class="nav-link dropdown-toggle" href="#" id="userDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
          <i class="fas fa-user-circle fa-fw"></i>
        </a>
        <div class="dropdown-menu dropdown-menu-right" aria-labelledby="userDropdown">
          <a class="dropdown-item" href="#">Settings</a>
          <a class="dropdown-item" href="#">Activity Log</a>
          <div class="dropdown-divider"></div>
          <a class="dropdown-item" href="#" data-toggle="modal" data-target="#logoutModal">Logout</a>
        </div>
      </li>
    </ul>

  </nav>

  <div id="wrapper">

    <!-- Sidebar -->
    <ul class="sidebar navbar-nav">
      <li class="nav-item">
        <a class="nav-link" href="index.html">
          <i class="fas fa-fw fa-tachometer-alt"></i>
          <span>Dashboard</span>
        </a>
      </li>
      <li class="nav-item dropdown">
        <a class="nav-link dropdown-toggle" href="#" id="pagesDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
          <i class="fas fa-fw fa-folder"></i>
          <span>Pages</span>
        </a>
        <div class="dropdown-menu" aria-labelledby="pagesDropdown">
          <h6 class="dropdown-header">Login Screens:</h6>
          <a class="dropdown-item" href="login.html">Login</a>
          <a class="dropdown-item" href="register.html">Register</a>
          <a class="dropdown-item" href="forgot-password.html">Forgot Password</a>
          <div class="dropdown-divider"></div>
          <h6 class="dropdown-header">Other Pages:</h6>
          <a class="dropdown-item" href="404.html">404 Page</a>
          <a class="dropdown-item" href="blank.html">Blank Page</a>
        </div>
      </li>
      <li class="nav-item">
        <a class="nav-link" href="charts.html">
          <i class="fas fa-fw fa-chart-area"></i>
          <span>Charts</span></a>
      </li>
      <li class="nav-item active">
        <a class="nav-link" href="tables.html">
          <i class="fas fa-fw fa-table"></i>
          <span>Tables</span></a>
      </li>
    </ul>

    <div id="content-wrapper">

      <div class="container-fluid">

        <!-- Breadcrumbs-->
        <ol class="breadcrumb">
          <li class="breadcrumb-item">
            <a href="#">Dashboard</a>
          </li>
          <li class="breadcrumb-item active">Tables</li>
        </ol>

        <!-- DataTables Example -->
        <div class="card mb-3">
          <div class="card-header">
            <i class="fas fa-table"></i>
            Data Table Example</div>
          <div class="card-body">
            <div class="table-responsive">


            
              
<table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
  <thead>
    <tr>
      <th>아이디</th>
<!--       <th scope="col" rowspan="2" style="vertical-align:middle; font-size: 10pt;">비밀번호</th> -->
      <th>이름</th>
      <th>닉네임</th>
      <th>소개</th>
      <th>프로필사진</th>
      <th>게시물 공개여부</th>
      <th>계좌번호</th>
      <th>가입일</th>
      <th>최종접속일</th>
      <th>등급경험치</th>
      <th>등급</th>
<!--       <th colspan="6">분야별 경험치</th> -->
    </tr>

<!--     <tr style="color: white; font-size: 9pt;"> -->
<!--     	<th>역량</th> -->
<!--     	<th>건강</th> -->
<!--     	<th>관계</th> -->
<!--     	<th>자산</th> -->
<!--     	<th>생활</th> -->
<!--     	<th>취미</th> -->
<!--     </tr> -->
  </thead>
<tfoot>
    <tr>
      <th>아이디</th>
      <th>이름</th>
      <th>닉네임</th>
      <th>소개</th>
<!--       <th>프로필사진</th> -->
<!--       <th>게시물 공개여부</th> -->
<!--       <th>계좌번호</th> -->
<!--       <th>가입일</th> -->
<!--       <th>최종접속일</th> -->
<!--       <th>등급경험치</th> -->
<!--       <th>등급</th> -->
<!--       <th colspan="6">분야별 경험치</th> -->
    </tr>
 </tfoot>
 
  <tbody>
    		<c:forEach items="${member}" var="dto">			
			<tr>
				<td>
					<ul style="list-style: none; padding: 0; vertical-align: middle;">
				        <li class="menu"  style="vertical-align: middle;">
				           <a href="#">${dto.member_id}</a>
				            <ul class="hide">
				                <li>
				                <a href="#layer" onclick="openContent('${dto.member_id}')">정지</a></li>
				                <li><a href="#layer" onclick="withdraw_regist('${dto.member_id}')">탈퇴</a></li>
				            </ul>
				        </li>
				    </ul>
			    </td>
				<td>${dto.member_name}</td>
				<td>${dto.member_nickname}</td>
				<td>${dto.member_introduction}</td>
				<td><img alt="" src="${dto.member_profile_image}.jpg" style="width: 25px;"></td>
				<td>${dto.member_public}</td>
				<td>${dto.member_account}</td>
				<td><c:set var="joindate" value="${fn:substring(dto.member_joindate,0,10)}"/> ${joindate}</td>
				<td>${dto.member_last_access}</td>
				<td>${dto.member_exp}</td>
				<td>${dto.member_grade}</td>
<%-- 				<td>${dto.member_capability}</td> --%>
<%-- 				<td>${dto.member_health}</td> --%>
<%-- 				<td>${dto.member_relationship}</td> --%>
<%-- 				<td>${dto.member_assets}</td> --%>
<%-- 				<td>${dto.member_life}</td> --%>
<%-- 				<td>${dto.member_hobby}</td> --%>
					
			</tr>
			
			</c:forEach>

			
<!-- 			<tr> -->
<!-- 					<td colspan="5"> -->
<!-- 					</td> -->
					
<%-- 				<form action="member?page=${page.curPage}" method="post" name="find"> --%>
<!-- 						<td colspan="1"> -->
<!-- 						<select class="custom-select" name="findoption" id ="findoption"> -->
<!-- 							<option selected>검색옵션</option> -->
<!-- 							<option value="아이디">아이디</option> -->
<!-- 							<option value="이름">이름</option> -->
<!-- 						</select> -->
<!-- 						</td> -->
<!-- 						<td colspan="2"> -->
<!-- 						<div class="input-group"> -->
<!-- 							<input type="text" class="form-control" name="findtext" id="findtext"> -->
<!-- 							<button type="submit">검색</button> -->
<!-- 						</div>	 -->
<!-- 						</td> -->
<!-- 				</form> -->
<!-- 				<td colspan="9"></td>	 -->
<!-- 			</tr> -->
			
			
			
<!-- 			<tr> -->
<!-- 				<td colspan="16" aria-label="Page navigation example" align="center"> -->
<!-- 				<div class="row align-items-center"> -->
<!-- 				<ul class="pagination"> -->
<!-- 				    <li class="page-item"> -->
<%-- 					   	<c:choose> --%>
<%-- 						<c:when test="${(page.curPage - 1) < 1}"> --%>
<!-- 							<a class="page-link" href="#">[ &lt;&lt; ]</a> -->
<%-- 						</c:when> --%>
<%-- 						<c:otherwise> --%>
<!-- 							<a class="page-link" href="/member?page=1">[ &lt;&lt; ]</a> -->
<%-- 						</c:otherwise> --%>
<%-- 						</c:choose> --%>
<!-- 				   	</li> -->
				   	
				   	
<!-- 				    <li class="page-item"> -->
<%-- 	    				<c:choose> --%>
<%-- 						<c:when test="${(page.curPage - 1) < 1}"> --%>
<!-- 							<a class="page-link"href="#">[ &lt; ]</a> -->
<%-- 						</c:when> --%>
<%-- 						<c:otherwise> --%>
<%-- 							<a class="page-link"href="/member?page=${page.curPage - 1}">[ &lt; ]</a> --%>
<%-- 						</c:otherwise> --%>
<%-- 						</c:choose> --%>
<!-- 				    </li>				     -->
				    
				 
				    
<%-- 					    <c:forEach var="fEach" begin="${page.startPage}" end="${page.endPage}" step="1"> --%>
<!-- 					    	<li class="page-item" > -->
<%-- 							<c:choose> --%>
<%-- 							<c:when test="${page.curPage == fEach}"> --%>
<%-- 								<a class="page-link"href="#">[ ${fEach} ]</a> --%>
<%-- 							</c:when> --%>
<%-- 							<c:otherwise> --%>
<%-- 								<a class="page-link"href="/member?page=${fEach}">[ ${fEach} ]</a> --%>
<%-- 							</c:otherwise> --%>
<%-- 							</c:choose> --%>
<!-- 							</li> -->
<%-- 						</c:forEach> --%>
				    
				   

<!-- 				    <li class="page-item" style="display: inline; float: left;"> -->
<%-- 	    				<c:choose> --%>
<%-- 						<c:when test="${(page.curPage +1) > page.totalPage }"> --%>
<!-- 							<a class="page-link"href="#">[ &gt; ]</a> -->
<%-- 						</c:when> --%>
<%-- 						<c:otherwise> --%>
<%-- 							<a class="page-link" href="/member?page=${page.curPage + 1}">[ &gt; ]</a> --%>
<%-- 						</c:otherwise> --%>
<%-- 						</c:choose> --%>
<!-- 				    </li>			 -->



<!-- 					<li class="page-item"> -->
<%-- 					   	<c:choose> --%>
<%-- 						<c:when test="${page.curPage == page.totalPage}"> --%>
<!-- 							<a class="page-link" href="#">[ &gt;&gt; ]</a> -->
<%-- 						</c:when> --%>
<%-- 						<c:otherwise> --%>
<%-- 							<a class="page-link" href="/member?page=${page.totalPage}">[ &gt;&gt; ]</a> --%>
<%-- 						</c:otherwise> --%>
<%-- 						</c:choose> --%>
<!-- 				   	</li> -->


<!-- 				  </ul> -->
				
<!-- 				</div> -->
			
<!-- 				</td> -->
<!-- 			</tr> -->
  </tbody>
</table>
            
            









            
          
            </div>
          </div>
          <div class="card-footer small text-muted">Updated yesterday at 11:59 PM</div>
        </div>

        <p class="small text-center text-muted my-5">
          <em>More table examples coming soon...</em>
        </p>

      </div>
      <!-- /.container-fluid -->

      <!-- Sticky Footer -->
      <footer class="sticky-footer">
        <div class="container my-auto">
          <div class="copyright text-center my-auto">
            <span>Copyright © Your Website 2019</span>
          </div>
        </div>
      </footer>

    </div>
    <!-- /.content-wrapper -->

  </div>
  <!-- /#wrapper -->

  <!-- Scroll to Top Button-->
  <a class="scroll-to-top rounded" href="#page-top">
    <i class="fas fa-angle-up"></i>
  </a>

  <!-- Logout Modal-->
  <div class="modal fade" id="logoutModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title" id="exampleModalLabel">Ready to Leave?</h5>
          <button class="close" type="button" data-dismiss="modal" aria-label="Close">
            <span aria-hidden="true">×</span>
          </button>
        </div>
        <div class="modal-body">Select "Logout" below if you are ready to end your current session.</div>
        <div class="modal-footer">
          <button class="btn btn-secondary" type="button" data-dismiss="modal">Cancel</button>
          <a class="btn btn-primary" href="login.html">Logout</a>
        </div>
      </div>
    </div>
  </div>

  <!-- Bootstrap core JavaScript-->
  <script src="song/vendor/jquery/jquery.min.js"></script>
  <script src="song/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

  <!-- Core plugin JavaScript-->
  <script src="song/vendor/jquery-easing/jquery.easing.min.js"></script>

  <!-- Page level plugin JavaScript-->
  <script src="song/vendor/datatables/jquery.dataTables.js"></script>
  <script src="song/vendor/datatables/dataTables.bootstrap4.js"></script>

  <!-- Custom scripts for all pages-->
  <script src="song/js/sb-admin.min.js"></script>

  <!-- Demo scripts for this page-->
  <script src="song/js/demo/datatables-demo.js"></script>

</body>

</html>
