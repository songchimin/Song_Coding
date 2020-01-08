<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%
	session.setAttribute("location", "home");
	String name = (String)session.getAttribute("name");
	String id = (String)session.getAttribute("id");
	String grade = (String)session.getAttribute("grade");

%>

<!DOCTYPE html>
<html>
  <head>
 	<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
    <script src="http://code.jquery.com/jquery-1.7.js"></script>
    
    
    <style type="text/css">
    	.loader {
			position: absolute;
			left: 50%;
			top: 50%;
			z-index:1;
			width:150px;
			height:150px;
			margin:-75px 0 0 -75px;
			border: 16px solid #f3f3f3;
			border-radius: 50%;
			border-top: 16px solid #3498db;
			width:120px;
			height:120px;
			-webkit-animation: spin 2s linear infinite;
			animation: spin 0.3s linear infinite;
    	}
    	
    	@-webkit-keyframes spin{
    		0%{ -webkit-transform: rotate(0deg);}
    		100%{ -webkit-transform: rotate(360deg);}
    	}
    	
    	@keyframes spin{
    		0% { transform: rotate(0deg);}
    		100%{ transform: rotate(360deg);}
    	}
    	
    </style>
    
    <script type="text/javascript">
    
//     sessionStorage.clear();

    
		var _showPage = function() {
			var loader = $("div.loader");
			var container = $("div.container");

			loader.css("display","none");
			container.css("display","block");
		};
		
    </script>
    
  </head>
  <body>
  
  <jsp:include page="header.jsp" />

    
    <div class="loader">
    
    </div>
    
    <div class="container" style="display: none;">
    	<h1>로딩후 화면</h1>
    </div>
   
    
    
  <!-- FOOTER -->
  <footer class="container">
  
    <p class="float-right"><a href="#">song song</a></p>
    <p>© 2019 Company, Inc. · <a href="#">Privacy</a> · <a href="#">Terms</a></p>
  </footer>
  
  
  <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>  
  </body>
  
  


</html>
