<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 
<%	
	session.setAttribute("location", "mapboard");
	String name = (String)session.getAttribute("name");
	String id = (String)session.getAttribute("id");
	
%>    
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
    
    
    <title>지도 위 버튼으로 로드뷰 표시하기</title>
    <style>
#container {overflow:hidden;height:500px;position:relative;}
#mapWrapper {width:100%;height:500px;z-index:1;}
#rvWrapper {width:50%;height:500px;top:0;right:0;position:absolute;z-index:0;}
#container.view_roadview #mapWrapper {width: 50%;}
#roadviewControl {position:absolute;top:5px;left:5px;width:42px;height:42px;z-index: 1;cursor: pointer; background: url(http://t1.daumcdn.net/localimg/localimages/07/2018/pc/common/img_search.png) 0 -450px no-repeat;}
#roadviewControl.active {background-position:0 -350px;}
#close {position: absolute; padding: 4px; top: 5px; left: 5px; cursor: pointer;background: #fff;border-radius: 4px;border: 1px solid #c8c8c8;box-shadow: 0px 1px #888;}
#close .img {display: block;background: url(http://t1.daumcdn.net/localimg/localimages/07/mapapidoc/rv_close.png) no-repeat;width: 14px;height: 14px;}
</style>




</head>
<body>

<jsp:include page="/header.jsp" />


<!-- <div id="bb" style="display: block;"> -->
<!-- 	<form action="logoutOk.doo" method="post"> -->
<!-- <!-- 		<input type="submit" value="로그아웃">&nbsp;&nbsp;&nbsp; -->
<%-- 		<c:if test="${id!=null}"> --%>
<!--         	<input class="btn btn-outline-primary" type="button" value="정보수정" -->
<!-- 					onclick="javascript:window.location='/zzzz/login/modify.jsp'"> -->
<%--         </c:if> --%>

<!-- 	</form> -->
<!-- </div> -->


    <div style="margin: 50px; margin-bottom: 20px;"> 
    	<h4>찾아오시는 길</h4>
    </div> 

<div id="container" style="margin: 50px; min-width: 1000px; border-style: solid; border: 2; margin-bottom: 20px; margin-top: 20px;">
    <div id="rvWrapper">
        <div id="roadview" style="width:100%;height:100%;"></div> <!-- 로드뷰를 표시할 div 입니다 -->
        <div id="close" title="로드뷰닫기" onclick="closeRoadview()"><span class="img"></span></div>
    </div>
    <div id="mapWrapper">
        <div id="map" style="width:100%;height:100%"></div> <!-- 지도를 표시할 div 입니다 -->
        <div id="roadviewControl" onclick="setRoadviewRoad()"></div>
    </div>
</div>
    <div style="margin: 50px; margin-top: 20px;"> 
    	<button class="btn btn-primary" onclick="setBounds()">회사 위치로 돌아가기</button>
    </div>    


<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=9601e88bb2d5aca7f8c3710bddae03e1"></script>
<script>
var overlayOn = false, // 지도 위에 로드뷰 오버레이가 추가된 상태를 가지고 있을 변수
    container = document.getElementById('container'), // 지도와 로드뷰를 감싸고 있는 div 입니다
    mapWrapper = document.getElementById('mapWrapper'), // 지도를 감싸고 있는 div 입니다
    mapContainer = document.getElementById('map'), // 지도를 표시할 div 입니다 
    rvContainer = document.getElementById('roadview'); //로드뷰를 표시할 div 입니다

var mapCenter = new kakao.maps.LatLng(37.4788 , 126.8787262), // 지도의 중심좌표
    mapOption = {
        center: mapCenter, // 지도의 중심좌표
        level: 3 // 지도의 확대 레벨
    };

// 지도를 표시할 div와 지도 옵션으로 지도를 생성합니다
var map = new kakao.maps.Map(mapContainer, mapOption);

// 로드뷰 객체를 생성합니다 
var rv = new kakao.maps.Roadview(rvContainer); 

// 좌표로부터 로드뷰 파노라마 ID를 가져올 로드뷰 클라이언트 객체를 생성합니다 
var rvClient = new kakao.maps.RoadviewClient(); 


var map = new kakao.maps.Map(mapContainer, mapOption); // 지도를 생성합니다



//마커가 표시될 위치입니다 
var markerPosition  = new kakao.maps.LatLng(37.4788, 126.8787262); 

// 마커를 생성합니다
var marker = new kakao.maps.Marker({
    position: markerPosition
});

//마커가 지도 위에 표시되도록 설정합니다
marker.setMap(map)


var iwContent = '<div style="padding:5px;"> 교육장 위치 <br><a href="https://map.kakao.com/link/map/Kosmo!,37.4788,126.8787262" style="color:blue" target="_blank">큰지도보기</a> <a href="https://map.kakao.com/link/to/Kosmo!,37.4788,126.8787262" style="color:blue" target="_blank">길찾기</a></div>', // 인포윈도우에 표출될 내용으로 HTML 문자열이나 document element가 가능합니다
    iwPosition = new kakao.maps.LatLng(37.4788, 126.8787262); //인포윈도우 표시 위치입니다

// 인포윈도우를 생성합니다
var infowindow = new kakao.maps.InfoWindow({
    position : iwPosition, 
    content : iwContent 
});
  
// 마커 위에 인포윈도우를 표시합니다. 두번째 파라미터인 marker를 넣어주지 않으면 지도 위에 표시됩니다
infowindow.open(map, marker); 




//일반 지도와 스카이뷰로 지도 타입을 전환할 수 있는 지도타입 컨트롤을 생성합니다
var mapTypeControl = new kakao.maps.MapTypeControl();

//지도에 컨트롤을 추가해야 지도위에 표시됩니다
//kakao.maps.ControlPosition은 컨트롤이 표시될 위치를 정의하는데 TOPRIGHT는 오른쪽 위를 의미합니다
map.addControl(mapTypeControl, kakao.maps.ControlPosition.TOPRIGHT);

//지도 확대 축소를 제어할 수 있는  줌 컨트롤을 생성합니다
var zoomControl = new kakao.maps.ZoomControl();
map.addControl(zoomControl, kakao.maps.ControlPosition.RIGHT);



var points = [
    new kakao.maps.LatLng(37.4788, 126.8787262),
];


//지도를 재설정할 범위정보를 가지고 있을 LatLngBounds 객체를 생성합니다
var bounds = new kakao.maps.LatLngBounds();    

var i, marker;
for (i = 0; i < points.length; i++) {
    // 배열의 좌표들이 잘 보이게 마커를 지도에 추가합니다
    marker =     new kakao.maps.Marker({ position : points[i] });
    marker.setMap(map);
    
    // LatLngBounds 객체에 좌표를 추가합니다
    bounds.extend(points[i]);
}

function setBounds() {
    // LatLngBounds 객체에 추가된 좌표들을 기준으로 지도의 범위를 재설정합니다
    // 이때 지도의 중심좌표와 레벨이 변경될 수 있습니다
    map.setBounds(bounds);
}





// 로드뷰에 좌표가 바뀌었을 때 발생하는 이벤트를 등록합니다 
kakao.maps.event.addListener(rv, 'position_changed', function() {

    // 현재 로드뷰의 위치 좌표를 얻어옵니다 
    var rvPosition = rv.getPosition();

    // 지도의 중심을 현재 로드뷰의 위치로 설정합니다
    map.setCenter(rvPosition);

    // 지도 위에 로드뷰 도로 오버레이가 추가된 상태이면
    if(overlayOn) {
        // 마커의 위치를 현재 로드뷰의 위치로 설정합니다
        marker.setPosition(rvPosition);
    }
});

// 마커 이미지를 생성합니다
var markImage = new kakao.maps.MarkerImage(
    'https://t1.daumcdn.net/localimg/localimages/07/2018/pc/roadview_minimap_wk_2018.png',
    new kakao.maps.Size(26, 46),
    {
        // 스프라이트 이미지를 사용합니다.
        // 스프라이트 이미지 전체의 크기를 지정하고
        spriteSize: new kakao.maps.Size(1666, 168),
        // 사용하고 싶은 영역의 좌상단 좌표를 입력합니다.
        // background-position으로 지정하는 값이며 부호는 반대입니다.
        spriteOrigin: new kakao.maps.Point(705, 114),
        offset: new kakao.maps.Point(13, 46)
    }
);

// 드래그가 가능한 마커를 생성합니다
var marker = new kakao.maps.Marker({
    image : markImage,
    position: mapCenter,
    draggable: true
});

// 마커에 dragend 이벤트를 등록합니다
kakao.maps.event.addListener(marker, 'dragend', function(mouseEvent) {

    // 현재 마커가 놓인 자리의 좌표입니다 
    var position = marker.getPosition();

    // 마커가 놓인 위치를 기준으로 로드뷰를 설정합니다
    toggleRoadview(position);
});

//지도에 클릭 이벤트를 등록합니다
kakao.maps.event.addListener(map, 'click', function(mouseEvent){
    
    // 지도 위에 로드뷰 도로 오버레이가 추가된 상태가 아니면 클릭이벤트를 무시합니다 
    if(!overlayOn) {
        return;
    }

    // 클릭한 위치의 좌표입니다 
    var position = mouseEvent.latLng;

    // 마커를 클릭한 위치로 옮깁니다
    marker.setPosition(position);

    // 클락한 위치를 기준으로 로드뷰를 설정합니다
    toggleRoadview(position);
});

// 전달받은 좌표(position)에 가까운 로드뷰의 파노라마 ID를 추출하여
// 로드뷰를 설정하는 함수입니다
function toggleRoadview(position){
    rvClient.getNearestPanoId(position, 50, function(panoId) {
        // 파노라마 ID가 null 이면 로드뷰를 숨깁니다
        if (panoId === null) {
            toggleMapWrapper(true, position);
        } else {
         toggleMapWrapper(false, position);

            // panoId로 로드뷰를 설정합니다
            rv.setPanoId(panoId, position);
        }
    });
}

// 지도를 감싸고 있는 div의 크기를 조정하는 함수입니다
function toggleMapWrapper(active, position) {
    if (active) {

        // 지도를 감싸고 있는 div의 너비가 100%가 되도록 class를 변경합니다 
        container.className = '';

        // 지도의 크기가 변경되었기 때문에 relayout 함수를 호출합니다
        map.relayout();

        // 지도의 너비가 변경될 때 지도중심을 입력받은 위치(position)로 설정합니다
        map.setCenter(position);
    } else {

        // 지도만 보여지고 있는 상태이면 지도의 너비가 50%가 되도록 class를 변경하여
        // 로드뷰가 함께 표시되게 합니다
        if (container.className.indexOf('view_roadview') === -1) {
            container.className = 'view_roadview';

            // 지도의 크기가 변경되었기 때문에 relayout 함수를 호출합니다
            map.relayout();

            // 지도의 너비가 변경될 때 지도중심을 입력받은 위치(position)로 설정합니다
            map.setCenter(position);
        }
    }
}

// 지도 위의 로드뷰 도로 오버레이를 추가,제거하는 함수입니다
function toggleOverlay(active) {
    if (active) {
        overlayOn = true;

        // 지도 위에 로드뷰 도로 오버레이를 추가합니다
        map.addOverlayMapTypeId(kakao.maps.MapTypeId.ROADVIEW);

        // 지도 위에 마커를 표시합니다
        marker.setMap(map);

        // 마커의 위치를 지도 중심으로 설정합니다 
        marker.setPosition(map.getCenter());

        // 로드뷰의 위치를 지도 중심으로 설정합니다
        toggleRoadview(map.getCenter());
    } else {
        overlayOn = false;

        // 지도 위의 로드뷰 도로 오버레이를 제거합니다
        map.removeOverlayMapTypeId(kakao.maps.MapTypeId.ROADVIEW);

        // 지도 위의 마커를 제거합니다
        marker.setMap(null);
    }
}

// 지도 위의 로드뷰 버튼을 눌렀을 때 호출되는 함수입니다
function setRoadviewRoad() {
    var control = document.getElementById('roadviewControl');

    // 버튼이 눌린 상태가 아니면
    if (control.className.indexOf('active') === -1) {
        control.className = 'active';

        // 로드뷰 도로 오버레이가 보이게 합니다
        toggleOverlay(true);
    } else {
        control.className = '';

        // 로드뷰 도로 오버레이를 제거합니다
        toggleOverlay(false);
    }
}

// 로드뷰에서 X버튼을 눌렀을 때 로드뷰를 지도 뒤로 숨기는 함수입니다
function closeRoadview() {
    var position = marker.getPosition();
    toggleMapWrapper(true, position);
}




</script>


    <!-- Optional JavaScript -->
    <!-- jQuery first, then Popper.js, then Bootstrap JS -->
<!--     <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script> -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
</body>
</html>