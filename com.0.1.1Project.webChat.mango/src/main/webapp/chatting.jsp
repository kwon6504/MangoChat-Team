<%@page import="com.MangoChat.util.Util"%>
<%@page import="com.MangoChat.LoginDB.L_Dto"%>
<%@page import="com.MangoChat.ChatDB.C_Dto"%>
<%@page import="com.MangoChat.ChatDB.C_Dao"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="/styleCSS/chatRoom.css">
<meta charset="UTF-8">
<title>채팅창</title>
</head>
<body>
<script>
/*  0.1.0 업데이트 - 스크롤 CSS에서 처리로 변경
        function addContent() {
            window.scrollTo(0, document.body.scrollHeight);
        } 
        */
        function handleKeyPress(event) {
            // Enter 키 (keyCode 13)를 누를 때 글 추가 함수 호출
            if (event.keyCode === 13) {
                addContent();
            }
    </script>
	<div id="warp">
		<div id="ChatArea">
			<%
			String roomNo = request.getParameter("roomNo");
			if (roomNo != null || !roomNo.equals(null)) {
				C_Dao dao = new C_Dao();
				ArrayList<C_Dto> chatList = dao.chattingList(roomNo);
				String userNo = (String) session.getAttribute("userNo");
				// home 에서 딸려오는 roomNo, 그리고 접속한 유저의 고유번호를 불러오기
			%>
			<div id="chattingList" class="divMatginAuto">
				<%
				if (chatList.size() > 0) {
				Util util = new Util();
					for (C_Dto a : chatList) {
						L_Dto name = util.userName(a.userNo);
						// 채팅을 입력했던 유저와 지금 접속해있는 유저의 고유번호가 다를때 출력되는 방식이 다르게 만들기 위한 처리
						if (!a.userNo.equals(userNo)) {
				%>

				<!-- 				유저 채팅 창 -->
				<div>
					<div class="Chat_main_box">
						<div class="Chat_user_img">
							<!-- 							<img alt="#" src="관심이.jpg"> -->
							<img
								src="https://i.ibb.co/xXm2sGH/1-Gx-B1-PLCKMEp-GN6f-Fy-Nbp-LBa-DOt-Cq09et-SE-B97-Sw-I19-He-N1pjw-Rp-giymw-Mv-Xgd-U3k4o-TVw-Nf4ctyon.webp"
								alt="1-Gx-B1-PLCKMEp-GN6f-Fy-Nbp-LBa-DOt-Cq09et-SE-B97-Sw-I19-He-N1pjw-Rp-giymw-Mv-Xgd-U3k4o-TVw-Nf4ctyon"
								border="0">
							<!-- 프로필이미지 연동 -->
						</div>
						<div class="Chat_user_box">
							<div class="Chat_user_names_box">
								<h1><%=name.userName%>#<%=name.userTag %></h1>
								<%=a.time%>
							</div>
							<div class="Chat_User_Main_Chat">
								<%=a.chatContent%>
							</div>
						</div>
					</div>
				</div>
				<!-- 				유저 채팅 창 -->
				<%
				} else {
				%>
				<!-- 				내 채팅 창 -->
				<div id="mydiv">
					<div class="Chat_MY_main_box">
						<div class="Chat_MY_user_box">
							<div class="Chat_MY_user_names_box"><%=a.time%></div>
							<div class="Chat_MY_User_Main_Chat"><%=a.chatContent%></div>
						</div>
					</div>
				</div>
				<!-- 				내 채팅 창 -->
				<%
				}
				}
				} else {
				%>
				채팅이 없습니다.
				<%
				}
				}
			%>
			</div>
		</div>

		<div id="Chat_Input_area">
			<!-- 채팅 -->
			<form action="/chat/chatting" method="get">
				<input type="hidden" name="roomNo" value=<%=roomNo%>>
				<div class="textarea">
					<input class="inputvalue" type="text" onkeypress="handleKeyPress(event)"
					 name="chatContent" placeholder="채팅창">
				</div>
			</form>
			<!--  엔터를 누르면 채팅이 입력이 되도록 처리 -->
		</div>
		<!-- 채팅-->

	</div>
</body>
</html>