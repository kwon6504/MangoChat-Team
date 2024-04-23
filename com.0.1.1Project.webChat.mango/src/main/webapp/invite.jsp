<%@page import="com.MangoChat.LoginDB.L_Dao"%>
<%@page import="com.MangoChat.LoginDB.L_Dto"%>
<%@page import="com.MangoChat.RoomDB.R_Dto"%>
<%@page import="com.MangoChat.util.Util"%>
<%@page import="com.MangoChat.Invite.I_Dao"%>
<%@page import="com.MangoChat.Invite.I_Dto"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<%
	String userNo = (String) session.getAttribute("userNo");
	I_Dao I_dao = new I_Dao();
	Util util = new Util();
	L_Dao L_dao = new L_Dao();
	ArrayList<I_Dto> inviteList = I_dao.inviteList(userNo);
	
	if (inviteList.size() > 0) {
		for (I_Dto list : inviteList) {
			if (list.inviteCase.equals("0")) { // 친추기능 아직만들지않음
		L_Dto inviteUser = util.userName(list.userNo); //초대한사람
	%>
	<hr>
	<div>
		<h2>친구 초대</h2>
		<%=inviteUser.userName%>#<%=inviteUser.userTag%>
		님이 친구요청을 보냈습니다 <br>
		<form action="/chat/invite" method="get">
			<input type="hidden" name="inviteNo" value="<%=list.inviteNo%>"> 	
			<input type="submit" name="check" value="수락">
		</form>
		<form action="/chat/invite">
			<input type="hidden" name="inviteNo" value="<%=list.inviteNo%>"> 
			<input type="submit" value="거절" name="check">
		</form>
	</div>
	<%
	} else if (list.inviteCase.equals("1")) { // 방초대	초대한 사람, 방이름 , 방장이름, 현제인원, 최대인원 이거 해야함..꼭 
	R_Dto info = util.roomInfo(list.roomNo); //방 정보
	L_Dto inviteUser = util.userName(list.userNo); //초대한사람
	L_Dto bossName = util.userName(info.masterUserNo);
	int min = util.roomUserCount(list.roomNo);
	%>
	<hr>
	<div>
		<h2>채팅방 초대</h2>
		<%=inviteUser.userName%>#<%=inviteUser.userTag%>
		님이 초대를 보냈습니다 <br>
		<h3>방 정보</h3>
		채팅방 이름:
		<%=info.roomName%>
		방장 닉네임 :
		<%=bossName.userName%>#<%=bossName.userTag%>
		채팅방 인원수 :
		<%=min%>/<%=info.max%>
		<form action="/chat/invite">
			<input type="hidden" name="inviteNo" value="<%=list.inviteNo%>"> <input
				type="submit" value="수락" name="check">
		</form>
		<form action="/chat/invite">
			<input type="hidden" name="inviteNo" value="<%=list.inviteNo%>"> <input
				type="submit" value="거절" name="check">
		</form>
		<br>
	</div>
	<hr>
	<%
	}
	}
	} else {
	%>알람이 없습니다.<%
	}
	%>
	<hr>
	<a href="/home.jsp"><button type="button">뒤로가기</button></a>
</body>
</html>