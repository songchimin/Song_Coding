<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.study.jsp.MemberDTO"%>
<%@page import="com.study.jsp.MemberDAO"%>
<%
	String id = (String)session.getAttribute("id");
	MemberDAO dao = MemberDAO.getInstance();
	MemberDTO dto = dao.getMember(id);
%>    

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<script>
	function form_check(){
		oEditors.getById["ir1"].exec("UPDATE_CONTENTS_FIELD", []);
		document.modify_form.submit();
	}
</script>
<script src="https://cdn.ckeditor.com/4.12.1/standard/ckeditor.js"></script>
</head>
<body>

<jsp:include page="../header.jsp" />



<nav>
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
		    <a class="nav-link active" href="/zzzz/list.do">자유게시판</a>
		  </li>
		  <li class="nav-item">
		    <a class="nav-link" href="#">Link</a>
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
      <div class="tab-pane fade show active" id="v-pills-profile" role="tabpanel" aria-labelledby="v-pills-profile-tab">
		
	<table class="table" cellpadding="0" cellspacing="0" border="1" >
		<form action="write.no" method="post" name="fr">
			<tr>
				<td> 이름 </td>
				<td><%= dto.getName() %></td>
<!-- 				<input type="text" name="bName" size = "50"> -->
			</tr>
			<tr>
				<td> 제목 </td>
				<td><input type="text" name="nTitle" id="nTitle" size = "50"> </td>
				
				<script type="text/javascript">
					function check() {
					     if ($('#nTitle').val() != '') {
					           document.fr.submit();
					       } else {
					    	   alert('제목을 입력하세요.');
					           $('#nTitle').focus();
					       }
					}
				</script>
				
			</tr>
			<tr>
				<td> 내용 </td>
				<td>

				<textarea name="nContent" id="editor1"></textarea>
                <script>
//                      CKEDITOR.replace( 'editor1' );
                        CKEDITOR.replace('editor1',{
                    filebrowserImageUploadUrl : 'House_Project/dev-guide/ckeditorImageUpload.do',
                    height:350
                    });

                </script>

				</td>
			</tr>
			<tr>
				<td colspan="2">
					<input type="button" value="입력" onclick="check()"> &nbsp;&nbsp;
					<a href="notify.no">목록보기</a>
				</td>
			</tr>
		</form>
		
	</table>
      
      </div>
      <div class="tab-pane fade" id="v-pills-messages" role="tabpanel" aria-labelledby="v-pills-messages-tab">
      	
      
      </div>
      <div class="tab-pane fade" id="v-pills-settings" role="tabpanel" aria-labelledby="v-pills-settings-tab">
      
      
      </div>
    </div>
  </div>
</div>


</div>
</div>

</nav>
</body>
</html>