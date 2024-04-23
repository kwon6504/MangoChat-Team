<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>망고챗 회원가입</title>
<link rel="stylesheet" href="/styleCSS/signUp.css?ver=<%= System.currentTimeMillis() %>">
</head>
<body>
<% String mag = request.getParameter("mag");
String coad = null;
try{
if(mag.equals(null) || mag==null){
	coad = "회원가입 페이지에 오신것을 환영합니다.";
}else if(mag.equals("id")){
	coad = " 중복된 아이디가 있습니다. ";
} else if(mag.equals("pw")){
	coad = "비밀번호가 잘못 입력되었습니다.";
} else if(mag.equals("null")){
	coad = "비워져있는 탭이 있으면 아이디생성을 하지못합니다.";
} else if(mag.equals("*")){
	coad = "특수문자는 입력하지 못합니다.";
} 

} catch (NullPointerException e){
	coad = "회원가입 페이지에 오신것을 환영합니다.";
}

%>

	<div id=main>
		<div id=title></div>
		<div id =bot>
			<div id=left></div>
			<div id=mid>
				<h2>회원 정보 입력</h2>
						<%=coad%>
						
				<form action="/chat/signUp_proc" method="get">
					ID :<input name="id"><br>
					PW :<input name="pw"><br> 
 					PW_RE :<input name="pwRe"><br>
 					EMAIL :<input ><br>
					NAME :<input name = "name"><br>
 					PHOME:<input ><br>
 					<input type="submit" value="회원가입">
				</form>
			</div>
			<div id=right></div>
		</div>

	</div>
</body>
</html>