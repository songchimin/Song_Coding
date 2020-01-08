<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% if(session.getAttribute("ValidMem") != null) { %>
   <jsp:forward page="main.jsp"></jsp:forward>
<% } %>  
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


<!-- 구글 로그인 추가 -->
	<script src="https://apis.google.com/js/api:client.js"></script>
	<script src="http://code.jquery.com/jquery.js"></script>
	
<!-- 	카카오 로그인 -->
	<script src="//developers.kakao.com/sdk/js/kakao.min.js"></script>
	
<script type="text/javascript">

/////////////////// GOOGLE 로그인 //////////////////
var googleUser = {};
var startApp = function() {
   gapi.load('auth2', function() {
      auth2 = gapi.auth2.init({
         client_id: '435215892457-jklj45hjuktfa041q5n3csqediuqimqb.apps.googleusercontent.com',
         cookiepolicy: 'single_host_origin',
      });

      attachSignin(document.getElementById('login'));
   });
};

function attachSignin(element) {
   auth2.attachClickHandler(element, {},
      function(googleUser) {

         var profile = googleUser.getBasicProfile();      

         $.ajax({
               type: "POST",
               url: "../gLogin",
               dataType: 'json',
               data: {
                  id: profile.getId(),
                  name: profile.getName(),
               },
               success: function(result){
                  var results = eval(result) 
                  if(results[0].result=="ok"){
                     alert("로그인");
                     window.location.replace("/zzzz/home.jsp");
                  }else{
                     
                  }
               }
            });
         
//          $('#login').css('display', 'none');
//           $('#logout').css('display', 'block');
//           $('#upic').attr('src', profile.getImageUrl());
//           $('#uname').html('[ ' +profile.getName() + ' ]');
      }, function(error) {
         alert(JSON.stringify(error, undefined, 2));
      });
}
function signOut() {
    var auth2 = gapi.auth2.getAuthInstance();
    auth2.signOut().then(function () {
       $('#login').css('display', 'block');
       $('#logout').css('display', 'none');
       $('#upic').attr('src', '');
       $('#uname').html('');
    });
}
/////////////////// GOOGLE 로그인 //////////////////
</script>

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
/* 		  margin: 0; */
/* 		  padding: 0; */
/* 		  display: -webkit-box; */
/* 		  display: -moz-box; */
/* 		  display: -ms-flexbox; */
/* 		  display: -moz-flex; */
/* 		  display: -webkit-flex; */
		  display: flex;
		  justify-content: space-between;
/* 		  list-style: none; */
		}
</style>

 
 
 <script type='text/javascript'>
    Kakao.init('9601e88bb2d5aca7f8c3710bddae03e1');
    function loginWithKakao() {
      // 로그인 창을 띄웁니다.
      Kakao.Auth.login({
        success: function(authObj) {
          //alert(JSON.stringify(authObj));
          ksignIn(authObj);
        },
        fail: function(err) {
          alert(JSON.stringify(err));
        }
      });
    };

    function ksignIn(authObj) {
        //console.log(authObj);
        Kakao.API.request({
            url: '/v2/user/me',
            success: function(res) {
                //console.log(res);
                var id = res.id;
                var name = res.properties['nickname'];
                console.log(res.id);
                console.log(res.properties['nickname']);
               
                document.location.href="kakao.doo?id="+id+"&name="+name;
                
                $('#klogin').css('display', 'none');
               	$('#logout').css('display', 'block');
                $('#upic').attr('src', res.properties.thumbnail_image );
               	$('#uname').html('[ ' + res.properties.nickname + ' ]');
             }
         })
	}

    function ksignOut() {
	    Kakao.Auth.logout(function () {
	    	$('#klogin').css('display', 'block');
	    	$('#logout').css('display', 'none');
	    	$('#upic').attr('src', '');
	    	$('#uname').html('');
	    });
	}
    
</script>
 
 
 
  </head>


  <body class="text-center" cz-shortcut-listen="true">
    
    
    <form class="form-signin" action="loginOk.doo" method="post">
      <img class="mb-4" src="../img/security_key.png" alt="" width="72" height="72">
      <h1 class="h3 mb-3 font-weight-normal">Please sign in</h1>
      <label for="inputEmail" class="sr-only">ID</label>
      <input type="text" id="id" name="id" class="form-control" placeholder="ID" required="" autofocus="">
      <label for="inputPassword" class="sr-only">Password</label>
      <input type="password" name="pw" id="inputPassword" class="form-control" placeholder="Password" required="">
      <div class="checkbox mb-3" id="area">
        <label>
          <input type="checkbox" value="remember-me"> Remember me
        </label>
        <button class="btn" type="button" onclick="javascript:window.location='join.jsp'">회원가입</button>
      </div>
      <button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>
<!--       sns로그인 추가 -->
<!-- 	<div id="login" class="g-signin2" data-onsuccess="onSignIn"></div> -->

<!-- 	<div id="login" class="btn btn-primary col-md-3" onclick="startApp();"> Google</div> -->
	
	<div style="text-align: left; display:inline-block; margin-right: 40px;">
	<img id="login" onclick="startApp();" style="width:62px; padding-top: 5px;" src="/zzzz/img/Google_icon.png" alt="">

<!--     <div id="logout" style="display: none;"> -->
<!--     	<input type="button" class="btn btn-success col-md-1" onclick="signOut();" value="로그아웃" /><br> -->

<!--     	<img id="upic" src=""><br> -->
<!--     	<span id="uname"></span> -->
<!--     </div> -->
	</div>
	
	
	
	<div id="klogin" style="display: inline-block; margin-right: 40px;">
	    <a id="custom-login-btn" href="javascript:loginWithKakao()">
	    <img src="/zzzz/img/kakao_icon.png" style="width:62px; padding-top: 5px;"/>
	    </a>
	</div>
	
	
<!-- 	네이버추가 -->
	<div id="klogin" style="display: inline-block;">
	    <a id="custom-login-btn" href="javascript:loginWithKakao()">
	    <img src="/zzzz/img/naver_icon.png" style="width:62px; padding-top: 5px;"/>
	    </a>
	</div>

<!-- <div id="logout" style="display: none;"> -->
<!--     <input type="button" class="btn btn-success" onclick="signOut();" 

<!--     <img id="upic" src=""><br> -->
<!--    	<span id="uname"></span> -->
<!-- </div> -->
	

      <p class="mt-5 mb-3 text-muted">© 2019</p>
    </form>
  
</body>
<!-- </html> -->

    <!-- Optional JavaScript -->
    <!-- jQuery first, then Popper.js, then Bootstrap JS -->
<!--     <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script> -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
  </body>
</html>