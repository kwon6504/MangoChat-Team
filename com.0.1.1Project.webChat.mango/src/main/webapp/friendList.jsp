<%@page import="com.MangoChat.LoginDB.L_Dto"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.MangoChat.util.Util"%>
<%@page import="com.MangoChat.FriendDB.F_Dao"%>
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
	String myUserNo = (String) session.getAttribute("userNo");
	String roomNo = request.getParameter("roomNo");
	String mag = request.getParameter("mag");
	F_Dao fdao = new F_Dao();
	Util util = new Util();
	ArrayList<String> friendList = fdao.friendList(myUserNo);
	if(friendList == null){
		%>친구가 없습니다.<%
	} else {
	if (friendList.size() > 0) {

		if (mag != null) { // 초대가 안될떄 안되는 이유를 출력해주는함수
			if (mag.equals("invite")) {
	%><hr> 이미 초대를 보낸 유저입니다.<%
	} else if (mag.equals("join")) {
	%>이미 채팅방에 소속된 유저입니다. <hr><%
	}
	}
	for (String list : friendList) {
	L_Dto user = util.userName(list);
	%>
	<div id="friendList">
	<a class="userName"><%=user.userName%></a><a class="userTag">#<%=user.userTag%></a>
	<%
	if (!roomNo.equals("null")) {
	%>
			<div id="friendInput">
		<form action="/chat/roomInvite" method="get">
			<input type="hidden" name="roomNo" value="<%=roomNo%>"> <input
				type="hidden" name="userNo" value="<%=list%>"> <input
				type="submit" value="채팅방 초대">
		</form>

	<%
	}
	%>
		<form action="/chat/freindDel" method="get">
			<input type="hidden" name="userNo" value="<%=list%>"> <input
				type="submit" value="친구 삭제">
		</form>
		</div>
			</div>
	<%
	}
	} else {
	%>
	친구가 없습니다.
	<%
	}
	}
	%>

</body>
</html>