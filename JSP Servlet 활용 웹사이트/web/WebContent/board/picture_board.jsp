<%@page import="org.apache.naming.java.javaURLContextFactory"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%> 

<%	
	session.setAttribute("location", "picture_board");
	String name = (String)session.getAttribute("name");
	String id = (String)session.getAttribute("id");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
    <!-- Required meta tags -->
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">

<title>Insert title here</title>



    <script type="text/javascript">

    	function login_check(){
    	   
    		if(test = ${id!=null})
    		{
    			javascript:window.location='picture_board_write_view.po';
    		}
    		else
    		{
    			alert("로그인 후 사용 할 수 있습니다.");
    		}

    	}
   	</script>




</head>
<body>
<jsp:include page="../header.jsp" />


<nav >
<div class="row" style="margin: 50px; min-width: 1000px;">

<!-- 게시판 -->
<div class="col-12">
<div class="row">
  <div class="col-2">
	 <ul class="nav nav-pills" style="display: block;">
		  <li class="nav-item">
		    <a class="nav-link" href="/zzzz/notify.no">공지사항</a>
		  </li>
		  <li class="nav-item">
		    <a class="nav-link" href="/zzzz/list.do">자유게시판</a>
		  </li>
		  <li class="nav-item">
		    <a class="nav-link active" href="/zzzz/list.po">사진게시판</a>
		  </li>
		  <li class="nav-item">
		    <a class="nav-link disabled" href="#" tabindex="-1" aria-disabled="true">Disabled</a>
		  </li>
	</ul>
  </div>
  
  <div class="col-10">
    <div class="tab-content" id="v-pills-tabContent">
      <div class="tab-pane fade" id="v-pills-home" role="tabpanel" aria-labelledby="v-pills-home-tab">
      
      
      </div>
      <div class="tab-pane fade" id="v-pills-profile" role="tabpanel" aria-labelledby="v-pills-profile-tab">

      
      </div>
      <div class="tab-pane fade" id="v-pills-messages" role="tabpanel" aria-labelledby="v-pills-messages-tab">
      	
      
      </div>
      <div class="tab-pane fade show active" id="v-pills-settings" role="tabpanel" aria-labelledby="v-pills-settings-tab">
 
      	<div class="album py-5 bg-light">
        <div class="container">

          <div class="row">
          
          <c:forEach items="${plist}" var="dto">
          
            <div class="col-md-4">
              <div class="card mb-4 shadow-sm">
                <img class="card-img-top" data-src="" alt="" style="height: 225px; width: 100%; display: block;" src="${dto.path}" data-holder-rendered="true">
                <div class="card-body">
                  <p class="card-text">${dto.pTitle}<br>${dto.pContent}</p>
                  <div class="d-flex justify-content-between align-items-center">
                    <div class="btn-group">
                      <button type="button" class="btn btn-sm btn-outline-secondary" onclick="javascript:window.location='content_view.po?pId=${dto.pId}'">View</button>
                      <button type="button" class="btn btn-sm btn-outline-secondary">Edit</button>
                    </div>
                    <small class="text-muted">${dto.pDate}</small>
                  </div>
                </div>
              </div>
            </div>
            
         </c:forEach>
     
          </div>
        </div>
      </div>
      
      <table class="table">
      <tr>
				<td colspan="1"></td><td colspan="1"></td>
				<td colspan="1"></td><td colspan="1"></td>
				<td colspan="1"></td><td colspan="1"></td>
				
				<td colspan="3" aria-label="Page navigation example">
				<div class="row align-items-center">
				<ul class="pagination">
				    <li class="page-item">
					   	<c:choose>
						<c:when test="${(page.curPage - 1) < 1}">
							<a class="page-link" href="#">[ &lt;&lt; ]</a>
						</c:when>
						<c:otherwise>
							<a class="page-link" href="/zzzz/list.po?page=1">[ &lt;&lt; ]</a>
						</c:otherwise>
						</c:choose>
				   	</li>
				   	
				   	
				    <li class="page-item">
	    				<c:choose>
						<c:when test="${(page.curPage - 1) < 1}">
							<a class="page-link"href="#">[ &lt; ]</a>
						</c:when>
						<c:otherwise>
							<a class="page-link"href="/zzzz/list.po?page=${page.curPage - 1}">[ &lt; ]</a>
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
								<a class="page-link"href="/zzzz/list.po?page=${fEach}">[ ${fEach} ]</a>
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
							<a class="page-link" href="/zzzz/list.po?page=${page.curPage + 1}">[ &gt; ]</a>
						</c:otherwise>
						</c:choose>
				    </li>			



					<li class="page-item">
					   	<c:choose>
						<c:when test="${page.curPage == page.totalPage}">
							<a class="page-link" href="#">[ &gt;&gt; ]</a>
						</c:when>
						<c:otherwise>
							<a class="page-link" href="/zzzz/list.po?page=${page.totalPage}">[ &gt;&gt; ]</a>
						</c:otherwise>
						</c:choose>
				   	</li>


				  </ul>
				
				</div>
			
				</td>
				
				<!--글작성 로그인 상태에서 가능 조건 -->
				<td colspan="1" style="text-align: right;">
					<input class="btn" type="button" onclick="login_check();" value="글작성">
				</td>
			</tr>
			
			
			
			<tr>

			</tr>
      </table>
      
      </div>
    </div>
  </div>
</div>


</div>
</div>

</nav>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
</body>
</html>