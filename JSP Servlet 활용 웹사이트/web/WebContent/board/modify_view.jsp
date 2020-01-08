<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<script type="text/javascript" src="./naver-editor/js/service/HuskyEZCreator.js" charset="utf-8"></script>

<script>
	function form_check(){
		oEditors.getById["ir1"].exec("UPDATE_CONTENTS_FIELD", []);
		document.modify_form.submit();
	}
</script>

</head>
<body>

<jsp:include page="../header.jsp" />

	<table width ="750" cellpadding="0" cellspacing="0" border="1">
		<form name="modify_form" action="modify.do" method="post">
			<input type="hidden" name="bId" value="${content_view.bId}">
			<tr>
				<td>번호</td>
				<td>${content_view.bId}</td>
			</tr>
			<tr>
				<td>히트</td>
				<td>${content_view.bHit}</td>
			</tr>
			<tr>
				<td>이름</td>
				<td><input type="text" name="bName" value="${content_view.bName}"></td>
			</tr>
			<tr>
				<td>제목</td>
				<td><input type="text" name="bTitle" value="${content_view.bTitle}"></td>
			</tr>
			<tr>
				<td>내용</td>
				<td><textarea name="bContent" id="ir1" rows="10" cols="100">${content_view.bContent}</textarea>
				<script type="text/javascript">
					var oEditors = [];
					nhn.husky.EZCreator.createInIFrame({
				    oAppRef: oEditors,
				    elPlaceHolder: "ir1",
				    sSkinURI: "/zzzz/naver-editor/SmartEditor2Skin.html",
				    fCreator: "createSEditor2",
				    htParams : {
				    	bUseToolbar: true,	//입력창 크기 조절바 사용여부
						bUseVerticalResizer : false,	//모드 탭 사용여부(html,text등)
						bUseModeChanger : false			//전송버튼 클릭이벤트
				    }
				});
				</script>
				</td>
			</tr>
		
			<tr>
				<td colspan="2"> 
					<a href="javascript:form_check();">수정완료</a>&nbsp;&nbsp;
				<!-- <input type="submit" value="수정완료"> &nbsp;&nbsp; -->
					<a href="content_view.do?bId=${content_view.bId}">취소</a> &nbsp;&nbsp;
					<a href="list.do?page=<%= session.getAttribute("cpage") %>">목록보기</a> &nbsp;&nbsp;
				</td>
			</tr>
		</form>
		
	</table>
</body>
</html>