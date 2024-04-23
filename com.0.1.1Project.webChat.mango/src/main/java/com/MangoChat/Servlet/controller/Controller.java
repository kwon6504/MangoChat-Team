package com.MangoChat.Servlet.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.MangoChat.Invite.I_Dto;
import com.MangoChat.LoginDB.L_Dto;
import com.MangoChat.Servlet.Service;
import com.MangoChat.util.Util;

@WebServlet("/chat/*")
public class Controller extends HttpServlet {

	Service service;
	Util util;
	String nextPage;
	String id;
	String pw;
	String userName;
	String userTag;
	String myUserNo;
	String userNo;
	String receiveUserName;
	String receiveUserTag;

	public void init() throws ServletException {
		service = new Service();
		util = new Util();
	}

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("통과");
		HttpSession session = request.getSession();
		request.setCharacterEncoding("UTF-8");
		String action = request.getPathInfo();
		
		if (action != null) {
			switch (action) {
			case "/signUp":
				nextPage = "/signUp.jsp";
				break;
			case "/signUp_proc": // 회원가입 작업실행
				// 아직 get 방식으로 정보를 받아옴
				id = String.format(request.getParameter("id")); 
				pw = String.format(request.getParameter("pw"));
				String pwRe = String.format(request.getParameter("pwRe"));
				userName = String.format(request.getParameter("name"));
				String regex = ".*[\\p{Punct}\\s].*";
				try {
					if (id.length() <= 0 || pw.length() <= 0 || pwRe.length() <= 0 || userName.length() <= 0) { // 입력한 글자수 조회
						nextPage = "/signUp.jsp?mag=null";
					} else if (id.matches(regex) || pw.matches(regex) || pwRe.matches(regex) // 글자 사이의 특수문자 있으면 안됌
							|| userName.matches(regex)) {
						nextPage = "/signUp.jsp?mag=*";
					} else if (!pw.equals(pwRe)) {
						nextPage = "/signUp.jsp?mag=pw"; //
					} else if (service.signUpIdCheck(id)) {
						L_Dto join = new L_Dto(id, pw, userName);
						service.signUp(join);
						nextPage = "/login.jsp";
					} else {
						nextPage = "/signUp.jsp?mag=id";
					}
				} catch (NullPointerException e) {
					nextPage = "/signUp.jsp?mag=null";
				}
				break;
			case "/login_proc":
				id = String.format(request.getParameter("id"));
				pw = String.format(request.getParameter("pw"));
				if (service.idck(id, pw)) {
					L_Dto idPw = service.idPw(id, pw);
					session.setAttribute("userNo", idPw.userNo);
					session.setAttribute("userName", idPw.userName);
					session.setAttribute("userTag", idPw.userTag);
					nextPage = "/chat/home";
				} else {
					nextPage = "/chat/login";
				}
				break;
			case "/chatting":
				nextPage = "/chat/home";
				
				String chatContent = request.getParameter("chatContent");
				String roomNo = request.getParameter("roomNo");
				String userNo = (String) session.getAttribute("userNo");
				service.chatting(roomNo, chatContent, userNo);
				break;
			case "/home":
				nextPage = "/home.jsp";
				break;
			case "/login":
				nextPage = "/login.jsp";
				break;
			case "/CreateRoom": // 방만들기
				nextPage = "/chat/home";
				String roomName = request.getParameter("roomName");
				String userMax = request.getParameter("userMax");
				userName = (String) session.getAttribute("userNo");
				service.create(userName, roomName, userMax);
				break;
			case "/roomInvite": // 채팅방 초대보내기
				nextPage = "/chat/home";
				myUserNo = (String) session.getAttribute("userNo");
				roomNo = request.getParameter("roomNo");
				userNo = request.getParameter("userNo");
				if(util.userJoinCk(userNo,roomNo) && util.inviteCheck(myUserNo,userNo,roomNo,"1")) {
				I_Dto dto = new I_Dto(myUserNo, userNo, "1");
				service.sendRoom(dto, roomNo);
				}else if(!util.inviteCheck(myUserNo,userNo,roomNo,"1")) { 
					nextPage = "/home.jsp?mag=invite";
				}else if (!util.userJoinCk(userNo,roomNo)){
					nextPage = "/home.jsp?mag=join";
				}
				break;
			case "/freindInvite": // 친추요청 보내기
				nextPage = "/chat/home";
				myUserNo = (String) session.getAttribute("userNo");
				userNo = request.getParameter("userNo");
				if(util.inviteCheck(userNo,myUserNo,"0","0")&&util.friendCheck(myUserNo,userNo)) {
				I_Dto dto = new I_Dto(myUserNo, userNo, "0");
				service.sendFriend(dto);
				}else if(!util.inviteCheck(myUserNo,userNo,"0","0")) { //초대가 보내지거나 보낸 상태입니다
					nextPage = "/friendSearch.jsp?mag=freindInvite";
				}else if (!util.friendCheck(myUserNo,userNo)){// 친구가 되어있는 상태입니다.
					nextPage = "/friendSearch.jsp?mag=freind";
				}

				break;
			case "/invite": // 알람에서 수락 또는 거절을 누를때 행동
				nextPage = "/chat/home";
				String inviteNo = request.getParameter("inviteNo");
				myUserNo = (String) session.getAttribute("userNo");
				String check = request.getParameter("check");
				if (check.equals("수락")) {
					service.invite(myUserNo, inviteNo);
				}else{//거절
					service.inviteDel(inviteNo);
				}
				break;
			case "/freindDel": // 친구삭제
			nextPage = "/home.jsp";
			myUserNo = (String) session.getAttribute("userNo");
			userNo = request.getParameter("userNo");
			service.friendDel(myUserNo, userNo);
			break;
			case "/friendListSearch":
				session.removeAttribute("listSearch");
				nextPage = "/friendSearch.jsp";
				// 로그인 세션 처리한 값 받아오기
				String myUserNo = (String)session.getAttribute("userNo");
				// 겟방식 form text 으로 전송받은 값 지정
				String searchName = request.getParameter("searchName");
				String searchTag = request.getParameter("searchTag");
				// 조건문1 태그란에 아무것도 안넣을때 처리
			if(searchName != null) {
				if(searchTag.equals("") && !searchName.equals("")) {
					ArrayList<String> listSearch = util.listSearch(searchName,myUserNo);
					session.setAttribute("listSearch",listSearch); // 해당되는값 세션처리
				// 조건문2 닉네임,태그란에 전부 넣었을때 처리
				} else if(!searchTag.equals("") && !searchName.equals("")){
					ArrayList<String> listSearch = util.tagListSearch(searchName, searchTag , myUserNo);
					session.setAttribute("listSearch",listSearch); // 해당되는값 세션처리
				} else {
					System.out.println("바보");
					// 예외처리 확인용
				}
			}
		}
			RequestDispatcher send = request.getRequestDispatcher(nextPage);
			send.forward(request, response);

		}
	}
	/*
	 * protected void doPost(HttpServletRequest request, HttpServletResponse
	 * response) throws ServletException, IOException { String receiveUserNo = "";
	 * 
	 * HttpSession session = request.getSession();
	 * request.setCharacterEncoding("UTF-8"); String action = request.getPathInfo();
	 * if (action != null) { switch (action) { // 친구신청 case "/requestFriend":
	 * nextPage ="/friendSearch.jsp"; // Post 방식으로 친구신청 버튼을 누르면 자동으로 해당되는 값 전송
	 * receiveUserName = request.getParameter("friendName"); receiveUserTag =
	 * request.getParameter("friendTag"); receiveUserNo =
	 * service.userNo(receiveUserName, receiveUserTag); // 내 고유번호 뽑아오기 userNo =
	 * (String)session.getAttribute("userNo"); // 체크박스에 넣기 (내 고유번호,상대 고유번호,룸번호,친구)
	 * if(util.inviteCheck(userNo, receiveUserNo,"0","0")) { // 조건 1 친구 리스트에 있을시
	 * false 반환 (0은 친구용 코드라는 뜻) if(util.friendCheck(userNo,receiveUserNo)) { // 조건 2
	 * 체크박스 리스트에 있을시 false 반환 (0은 친구용 코드라는 뜻) service.sendFriend(new
	 * I_Dto(userNo,receiveUserNo,"0"));
	 * System.out.println("친추 이름확인용:"+receiveUserName);
	 * System.out.println("친추 태그확인용:"+receiveUserTag);
	 * System.out.println("친추 번호확인용:"+receiveUserNo); } else {
	 * System.out.println("여기에다 경고창 처리하면됌(친구리스트에 이미있음)"); } } else {
	 * System.out.println("여기에다 경고창 처리하면됌(인바이트에 이미있음)"); } break; }
	 * RequestDispatcher send = request.getRequestDispatcher(nextPage);
	 * send.forward(request, response); } }
	 */
}
