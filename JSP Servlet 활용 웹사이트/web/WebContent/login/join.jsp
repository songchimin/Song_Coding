<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
String ctx = request.getContextPath();  //콘텍스트명 얻어오기.
response.setHeader("Pragma-directive", "no-cache");
response.setHeader("Cache-directive", "no-cache");
response.setHeader("Pragma", "no-cache");
response.setHeader("Cache-Control", "no-cache");
response.setDateHeader("Expires",0);

%>    

<!doctype html>
<html>
  <head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
	<script type="text/javascript"  src="//ajax.googleapis.com/ajax/libs/jquery/1.9.0/jquery.min.js"></script>
	<script language="JavaScript" src="members.js"></script>
	
	
	
    <title>회원가입</title>
    
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
		
		.form-row{
		    margin-bottom: 1rem!important;

		}
		
		#join-form {
		    width: 100%;
		    max-width: 330px;
		    padding: 15px;
		    margin: auto;
		}
    </style>
    
    
<script type="text/javascript">
var rand;
 
//캡차 오디오 요청

function audioCaptcha(type) {
       var kor = (type > 0) ? "lan=kor&":"";
       $.ajax({
             url: '../captcha/CaptChaAudio.jsp',
             type: 'POST',
             dataType: 'text',
             data: 'rand=' + rand + '&ans=y',
             async: false,      

             success: function(resp) {
                    var uAgent = navigator.userAgent;
                    var soundUrl = '../captcha/CaptChaAudio.jsp?' + kor + 'rand=' + rand + '&ans=' + resp;

                    //console.log(soundUrl);

                    if (uAgent.indexOf('Trident') > -1 || uAgent.indexOf('MSIE') > -1) {
                           winPlayer(soundUrl+'&agent=msie');

                    } else if (!!document.createElement('audio').canPlayType) {
                           try { new Audio(soundUrl).play(); } catch(e) { winPlayer(soundUrl); }

                    } else window.open(soundUrl, '', 'width=1,height=1');

             }

       });

}

function winPlayer(objUrl) {
       $('#audiocatpch').html(' <bgsound src="' + objUrl + '">');
}

//캡차 이미지 요청 (캐쉬문제로 인해 이미지가 변경되지 않을수있으므로 요청시마다 랜덤숫자를 생성하여 요청)

function changeCaptcha() {
       rand = Math.random();
       $('#catpcha').html('<img src="<%=ctx%>/captcha/CaptChaImg.jsp?rand=' + rand + '"/>');

}



	$(document).ready(function() {
		changeCaptcha(); //캡차 이미지 요청

		$('#reLoad').click(function() {
			changeCaptcha();
		}); //새로고침버튼에 클릭이벤트 등록
		$('#soundOn').click(function() {
			audioCaptcha(0);
		}); //음성듣기버튼에 클릭이벤트 등록
		$('#soundOnKor').click(function() {
			audioCaptcha(1);
		}); //한글음성듣기 버튼에 클릭이벤트 등록

		//확인 버튼 클릭시

		$('#frmSubmit').click(function() {	

			if (!$('#answer').val()) {
				alert('이미지에 보이는 숫자 또는 스피커를 통해 들리는 숫자를 입력해 주세요.');
			} else {
				$.ajax({
					url : '../captcha/captchaSubmit.jsp',
					type : 'POST',
					dataType : 'text',
					data : 'answer=' + $('#answer').val(),
					async : false,

					success : function(resp) {
						if(resp==0)
						{
							alert("일치");
							$('#check').attr('disabled', false);
							$('#frmSubmit').attr('disabled', true);
							$('#reLoad').attr('disabled', true);
							$('#soundOn').attr('disabled', true);
							$('#soundOnKor').attr('disabled', true);
						}
						else{
							alert("불일치");
							
						}
						

// 						$('#reLoad').click();
// 						$('#answer').val('');						
					}
				});
			}
		});
	});
	
</script>
    
    
  </head>
  
  <body>
  
    <form action="joinOk.doo" method="post" class="needs-validation" id="join-form" novalidate>
   <div class="form-row">
      <label for="validationCustom0" style="font-size:20px; font-weight: bold;">회원가입</label>
   </div>
 
 
  <div class="form-row">
      <label for="validationCustom01">아이디</label>
      <input type="text" class="form-control" name="id" id="validationCustom01" placeholder="ID" value="" required>
      <div class="invalid-feedback">
        	필수정보 입니다.
      </div>
  </div>
   
  <div class="form-row"> 
      <label for="validationCustom02">비밀번호</label>
      <input type="password" class="form-control" name="pw" id="validationCustom02" placeholder="Password" value="" required>
      <div class="invalid-feedback">
        	필수정보 입니다.
      </div>
  </div>

  <div class="form-row">   
      <label for="validationCustom03">비밀번호 재확인</label>
      <input type="password" class="form-control" name="pw_check" id="validationCustom03" placeholder="Password check" value="" required>
      <div class="invalid-feedback">
       	 필수정보 입니다.
      </div>
  </div>  
  
  <div class="form-row">  
      <label for="validationCustomUsername">이름</label>
      <div class="input-group">
        <div class="input-group-prepend">
          <span class="input-group-text" id="inputGroupPrepend">@</span>
        </div>
        <input type="text" class="form-control" name="name" id="validationCustomUsername" placeholder="Username" aria-describedby="inputGroupPrepend" required>
        <div class="invalid-feedback">
          Please choose a username.
        </div>
    </div>
  </div>
  
  <div class="form-row">
      <label for="validationCustom04">메일</label>
      <input type="text" class="form-control" name="eMail" id="validationCustom04" placeholder="eMail" required>
      <div class="invalid-feedback">
        Please provide a valid city.
      </div>
  </div>
  <div class="form-row">
      <label for="validationCustom05">주소</label>
      <input type="text" class="form-control" name="address" id="validationCustom05" placeholder="address" required>
      <div class="invalid-feedback">
        Please provide a valid state.
      </div>
  </div>
  
  <!-- 	여기가 가입 방지문자  -->
  <div class="form-row">
       <div id="catpcha" style="width: 40%"></div>
       <div id="audiocatpch" style="display: none;"></div>
       <button class="btn btn-primary" type="button" style="width: 18%; margin-left:14px" id="reLoad" ><img style="width: 100%" src="/zzzz/img/refresh.png" alt=""></button>
<!--        <input class="btn btn-primary" style="width: 20%" id="reLoad" type="button" value="새로고침" /> -->

   <button class="btn btn-primary" type="button" style="width: 18%; margin-left:1px" id="soundOn" ><img style="width: 100%" src="/zzzz/img/Espeaker.png" alt=""></button>
   <button class="btn btn-primary" type="button" style="width: 18%; margin-left:1px" id="soundOnKor" ><img style="width: 100%" src="/zzzz/img/Kspeaker.png" alt=""></button>
<!--    		<input class="btn btn-primary" style="width: 50%" id="soundOn" type="button" value="음성듣기" /> -->
<!--        <input class="btn btn-primary" style="width: 50%" id="soundOnKor" type="button" value="한글음성" /> -->
   </div>
   <div class="form-row">  
       <input class="form-control" style="width: 75%" type="text" id="answer" name="answer" value="" />
       <input class="btn btn-primary" style="width: 25%" type="button" id="frmSubmit" value="확인" />
  </div>
  

  <div class="form-group">
    <div class="form-check">
      <input class="form-check-input" type="checkbox" value="" id="invalidCheck" required>
      <label class="form-check-label" for="invalidCheck">
        Agree to terms and conditions
      </label>
      <div class="invalid-feedback">
        You must agree before submitting.
      </div>
    </div>
  </div>
  <!-- disabled="disabled" -->
  <button class="btn btn-primary" type="submit" style="width: 100%" disabled="disabled" id="check" >회원가입</button>
</form>


<script>
// Example starter JavaScript for disabling form submissions if there are invalid fields
(function() {
  'use strict';
  window.addEventListener('load', function() {
    // Fetch all the forms we want to apply custom Bootstrap validation styles to
    var forms = document.getElementsByClassName('needs-validation');
    // Loop over them and prevent submission
    var validation = Array.prototype.filter.call(forms, function(form) {
      form.addEventListener('submit', function(event) {
        if (form.checkValidity() === false) {
          event.preventDefault();
          event.stopPropagation();
        }
        form.classList.add('was-validated');
      }, false);
    });
  }, false);
})();
</script>




    <!-- Optional JavaScript -->
    <!-- jQuery first, then Popper.js, then Bootstrap JS -->
<!--     <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script> -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
  </body>
</html>