<%@page import="com.MangoChat.LoginDB.L_Dao"%>
<%@page import="com.MangoChat.LoginDB.L_Dto"%>
<%@page import="com.MangoChat.RoomDB.R_Dto"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.MangoChat.util.Util"%>
<%@page import="com.MangoChat.RoomDB.R_Dao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>채팅 로비 메인</title>
<link rel="stylesheet" href="/styleCSS/home.css">
<link rel="stylesheet" href="/styleCSS/home0.1.1v.css">
<link rel="stylesheet" href="/styleCSS/chatRoom.css">
</head>
<body>
	<%
	String userName = (String) session.getAttribute("userName");
	String userNo = (String) session.getAttribute("userNo");
	String userTag = (String) session.getAttribute("userTag");
	String mode = request.getParameter("mode");
	String roomNo = request.getParameter("roomNo");
	String mag = request.getParameter("mag");
	%>
	<div id="main">
	
<!-- 		<header id="top"> -->
<!-- 			<a href="/chat/login"><button type="button">로그아웃</button></a> -->
<!-- 			<!-- 임시 로그아웃 버튼 --> 
<!-- 		</header> -->
		<section id="mid">
		 <!-- 왼쪽 채팅방 목록 시작 -->
				<div id="left_tool_bar">
				<!--채팅 목록 -->
				<%
				R_Dao R_dao = new R_Dao();
				L_Dao L_dao = new L_Dao();
				Util util = new Util();
				ArrayList<String> roomList = R_dao.roomList(userNo); // 로그인한 유저의 정보를 받아 소속되어 있는 채팅방의 리스트를 뽑는다
				if (roomList.size() > 0) {
					// 채팅방에 입장해 있지 않을때 다른것이 출력되게
// 					for (String roomlist : roomList) { 0.1.1 버그발생~~~~~~
// 						R_Dto info = R_dao.roomInfo(roomlist);
// 						L_Dto name = util.userName(info.masterUserNo);
// 						int count = util.roomUserCount(roomlist);
					int maxRoomsToShow = Math.min(8, roomList.size());
					for (int i = 0; i < maxRoomsToShow; i++) {
					    String roomlist = roomList.get(i);
					    R_Dto info = R_dao.roomInfo(roomlist);
					    L_Dto name = util.userName(info.masterUserNo);
					    int count = util.roomUserCount(roomlist);
%>
<!-- 					이 칸을 누르면 채팅방을 변경할수있음 -->
				<!-- div 에 onclick 을 추가하고 script 에 특정 url로 이동할수있게 처리 -->
				<div class="Channel" onclick="chat('<%=roomlist%>')" onmouseover="changeText(this,'<%=info.roomName%>', '<%=name.userName%>','<%=name.userTag%>', '<%=count%>', '<%=info.userMax%>')" onmouseout="restoreText(this, '<%=info.roomName%>')"> 
  				<%=info.roomName%>
				</div>
				<script>
					function chat(roomNo) {
						window.location.href = "/chat/index?roomNo=" + roomNo;
					}
					function changeText(element, roomName, userName, userTag, count, userMax) {
					    element.innerHTML = '['+roomName+']<br> Master :'+userName+'#'+userTag+'<br> Member'+count+'/'+userMax;
					}
				    function restoreText(element, roomName) {
				        element.innerHTML = roomName;
				    }
				</script>
				<%
				}
				} else {
				%>채팅방이 없는 아싸입니다<%
				}
				%>
			</div>
<!-- 			<div id="midCenter"> -->
				<div id="mid_chat_area">
				<div id="right_tool_button">알림</div>
				<div id="right_tool_button_Message">친추</div>
<div id="right_tool_bar">
			<!--  임시 알림 확인 버튼 -->
<!-- 			<a href="/invite.jsp"><button>알림 확인</button></a> -->
					<jsp:include page="invite.jsp" flush="true">
					<jsp:param name="roomNo" value="<%=roomNo%>"></jsp:param>
					</jsp:include>
</div>
<div id="right_tool_bar_Message">
<!-- 			<a href="/friendSearch.jsp"><button type="button" >친구찾기 이동</button></a> -->
					<jsp:include page="friendSearch.jsp" flush="true">
					<jsp:param name="roomNo" value="<%=roomNo%>"></jsp:param>
					</jsp:include>
</div>
					<div id="overlay">닫기</div>
		<script>
// JavaScript 코드 여기에 붙여넣기
var toggleButton = document.getElementById('right_tool_button');
var toggleButtonMessage = document.getElementById('right_tool_button_Message');
var rightToolBar = document.getElementById('right_tool_bar');
var rightToolBarMessage = document.getElementById('right_tool_bar_Message');
var overlay = document.getElementById('overlay');

toggleButton.onclick = function() { 
	rightToolBarMessage.style.display = 'none';
    rightToolBar.style.display = 'block';
    overlay.style.display = 'block';
};
  
toggleButtonMessage.onclick = function() { 
    rightToolBar.style.display = 'none';
	rightToolBarMessage.style.display = 'block';
    overlay.style.display = 'block';
};
  
overlay.onclick = function() {
    rightToolBar.style.display = 'none';
	rightToolBarMessage.style.display = 'none';
    overlay.style.display = 'none';
};
</script>
					<!--채팅창 보이게 하는거 -->
					<%
					if (roomNo != null && !roomNo.equals(null)) {
					%>
					<jsp:include page="chatting.jsp" flush="true">
					<jsp:param name="roomNo" value="<%=roomNo%>"></jsp:param>
					</jsp:include>
					<%
					} else {
					%>
<!-- 					채팅방 접속하지않음 -->
					<%
					}
					%>

				</div>
			<div id="midRight">
				<div id="midProfile">
				<div class="arrayDIV">
					<img id="ProfileImage"
						src="https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png">
					<div id="ProfileText"><b><%=userName%></b><a class="userTag">#<%=userTag%></a>님<br>반갑습니다.
					</div>
				</div>
					<a href="/chat/login"><button type="button">로그아웃</button></a>
				</div>
				<div id="midFriend">
					<div id="friendtop">
					<h3>친구목록 ▼</h3>
<!-- 					<a href="/friendSearch.jsp">검색창</a> -->
					<jsp:include page="friendList.jsp" flush="true">
						<jsp:param name="roomNo" value="<%=roomNo%>"></jsp:param>
						<jsp:param name="mag" value="<%=mag%>"></jsp:param>
					</jsp:include>
				</div>
			</div>
		</section>

		<footer id="bot">
			<form action="/chat/CreateRoom" method="get">
				<input name="roomName" placeholder="방 이름">
				<!-- 방을 생성 -->
				<input type="number" min="2" max="30" name="userMax" placeholder="최대인원수">
				<input type="submit" value="방만들기">
			</form>

		</footer>
	</div>



</body>
</html>