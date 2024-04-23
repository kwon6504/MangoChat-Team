<%@page import="com.MangoChat.LoginDB.L_Dto"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.MangoChat.util.Util"%>
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
	String mag = request.getParameter("mag");
	Util util = new Util();
	%>
	<form action="/chat/friendListSearch" method="get">
		<input type="text" name="searchName" placeholder="닉네임">#
		<input type="text" name="searchTag" placeholder="테그">
		<input type="submit" value="검색">
	</form>

	<%
	ArrayList<String> search = (ArrayList<String>) session.getAttribute("listSearch");
	if (search != null) {
		if (search.size() > 0) {
			for (String list : search) {
		L_Dto user = util.userName(list);
	%>
	<div>
		<%=user.userName%>#<%=user.userTag%>
		<form action="/chat/freindInvite" method="get">
			<input type="hidden" name="userNo" value="<%=list%>">
			<input type="submit" value="친구추가">
		</form>
	</div>
	<%
			}
		}else {
			%>
			찾을 수 없습니다.
			<%
		}
	}
	session.removeAttribute("listSearch");
	if(mag == null || mag.equals(null) ){
		%><%
	} else if(mag.equals("freindInvite")){
		%>내가 상대에게 초대를 보낸상태입니다.<br>
		<%
	}else if(mag.equals("freind")){
		 %>이미 친구입니다.
		 <br><%
	}
	
	%>
<a href="/home.jsp"><button>뒤로가기</button></a>


</body>
</html>