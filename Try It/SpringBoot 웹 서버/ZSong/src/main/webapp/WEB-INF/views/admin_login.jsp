<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%-- <% if(session.getAttribute("ValidMem") != null) { %> --%>
<%--    <jsp:forward page="main.jsp"></jsp:forward> --%>
<%-- <% } %>   --%>

<!doctype html>
<html>
  <head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">


<!-- <html lang="en"><head> -->
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" href="/docs/4.1/assets/img/favicons/favicon.ico">
    <link rel="canonical" href="https://getbootstrap.com/docs/4.1/examples/sign-in/">

<script src="//code.jquery.com/jquery-3.3.1.min.js"></script>
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.js"></script>


    <title>로그인</title>
    
    <style>
		
		.text-center {
			text-align: center !important;
		}
		
		body {
			display: -ms-flexbox;
			display: flex;
			-ms-flex-align: center;
			align-items: center;
			padding-top: 40px;
			padding-bottom: 40px;
			background-color: #f5f5f5;
		}
		
		html, body {
			height: 100%;
		}
		
		.form-signin {
		    width: 100%;
		    max-width: 330px;
		    padding: 15px;
		    margin: auto;
		}
		
		#area {
		margin-top:3px;
		margin:8x;
		  display: flex;
		  justify-content: space-between;
		}
</style>


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

//             $(function(){
            	 var ex_box = $('.ex_box');
				 var aa = $('#aa');
            	 
            	 ex_box.fadeOut();
            	 aa.fadeOut();
            	 
//             	  ex_hide_fast.click(function(){
//             		    ex_box_3f.fadeOut('fast');
//             		  });
//             });
            
        });
    });
</script>
<script type="text/javascript">

	

</script>




  </head>


<body class="text-center" cz-shortcut-listen="true" style="background-color: #333333;">
    
<!--     <form class="form-signin" action="loginOk"> -->
<!--       <img class="mb-4" src="../img/security_key.png" alt="" width="72" height="72"> -->
<!--       <h1 class="h3 mb-3 font-weight-normal" style="color: white;">관리자 로그인</h1> -->
<!-- <!--       Please sign in --> -->
<!--       <label for="inputEmail" class="sr-only">ID</label> -->
<!--       <input type="text" id="id" name="id" class="form-control" placeholder="ID" required="" autofocus=""> -->
<!--       <label for="inputPassword" class="sr-only">Password</label> -->
<!--       <input type="password" name="pw" id="pw" class="form-control" placeholder="Password" required=""> -->
<!--       <div class="checkbox mb-3" id="area"> -->
<!--         <label> -->
<!--           <input type="checkbox" value="remember-me"> Remember me -->
<!--         </label> -->
<!-- <!--         <button class="btn" type="button" onclick="javascript:window.location='join.jsp'">회원가입</button> --> -->
<!--       </div> -->
<!--       <button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button> -->

<!-- 		<br> -->
<!-- 		<p class="mt-5 mb-3 text-muted" style="color:#ffffff; font-size: 20px;">© 2019 Try it</p> -->
<!--     </form> -->
    
<div class="cover-container d-flex w-100 h-100 p-3 mx-auto flex-column">
  <header class="masthead mb-auto">
    <div class="inner">
      <h3 class="masthead-brand" style="color: white; margin: 0; vertical-align: middle;" align="left"> Try It. Admin Page</h3>
<!--       <img alt="" src="img/logo.png" width="65px" style="margin-bottom: 10px;"> -->
      <nav class="nav nav-masthead justify-content-center">
<!--         <a class="nav-link active" href="#">Home</a> -->
<!--         <a class="nav-link" href="#">Features</a> -->
<!--         <a class="nav-link" href="#">Contact</a> -->
      </nav>
    </div>
  </header>

  <main role="main" class="inner cover">
<!--     <div class="ex_box"> -->
<!--     <h1 class="cover-heading" style="color: #FFFFFF;">Try It 관리자 페이지</h1> -->
<!--     <p class="lead" style="color: #FFFFFF;" >Cover is a one-page template for building simple and beautiful home pages. Download, <br>edit the text, and add your own fullscreen background photo to make it your own.</p> -->
<!--     </div> -->
    <p class="lead">
<!--       <a href="#" class="btn btn-lg btn-secondary" style="color: #FFFFFF; width: 110px; height: 60px; pa">Log In</a>▲▼ -->
    </p>			
      				<ul style="list-style: none; padding: 0; vertical-align: middle;">
			        <li class="menu"  style="vertical-align: middle;">
<!--		             <a href="#" class="btn btn-lg btn-secondary" style="color: #FFFFFF; width: 110px;" id="aa">Log In</a> -->
						<a href="#" id="aa">
							<img alt="" src="img/logo.png" width="100px">
						</a>
			            
			            
			            <ul class="hide" style="padding: 0;">
			            
	<form class="form-signin" action="loginOk">
<!--       <img class="mb-4" src="../img/security_key.png" alt="" width="72" height="72"> -->
      <h1 class="h3 mb-3 font-weight-normal" style="color: white; font-weight: bold;">관리자 로그인</h1>
<!--       Please sign in -->
      <label for="inputEmail" class="sr-only">ID</label>
      <input type="text" id="id" name="id" class="form-control" placeholder="ID" required="" autofocus="">
      <label for="inputPassword" class="sr-only">Password</label>
      <input type="password" name="pw" id="pw" class="form-control" placeholder="Password" required="">
      <div class="checkbox mb-3" id="area">
        <label style="color: white;">
          <input type="checkbox" value="remember-me"> Remember me
        </label>
<!--         <button class="btn" type="button" onclick="javascript:window.location='join.jsp'">회원가입</button> -->
      </div>
      <button class="btn btn-lg btn-danger btn-block" type="submit" >Sign in</button>

		<br>
<!-- 		<p class="mt-5 mb-3 text-muted" style="color:#ffffff; font-size: 20px;">© 2019 Try it</p> -->
    </form>
    
			            </ul>
			        </li>
			    </ul>
    
  </main>

  <footer class="mastfoot mt-auto">
    <div class="inner">
<!--       <p>Cover template for <a href="https://getbootstrap.com/">Bootstrap</a>, by <a href="https://twitter.com/mdo">@mdo</a>.</p> -->
      <p style="color: white;">© 2019 Try it</p>
    </div>
  </footer>
</div>
    
    
</body>

<!-- </html> -->

    <!-- Optional JavaScript -->
    <!-- jQuery first, then Popper.js, then Bootstrap JS -->
<!--     <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script> -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
  </body>
</html>
