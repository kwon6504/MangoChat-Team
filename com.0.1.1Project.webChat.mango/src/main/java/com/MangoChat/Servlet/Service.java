package com.MangoChat.Servlet;

import com.MangoChat.ChatDB.C_Dao;
import com.MangoChat.ChatDB.C_Dto;
import com.MangoChat.FriendDB.F_Dao;
import com.MangoChat.Invite.I_Dao;
import com.MangoChat.Invite.I_Dto;
import com.MangoChat.LoginDB.L_Dao;
import com.MangoChat.LoginDB.L_Dto;
import com.MangoChat.RoomDB.R_Dao;
import com.MangoChat.util.Util;

public class Service {
	public L_Dao L = new L_Dao();
	public C_Dao C = new C_Dao();
	public I_Dao I = new I_Dao();
	public F_Dao F = new F_Dao();
	public R_Dao R = new R_Dao();
	public Util U = new Util();

	public String userNo(String name,String tag) {
		return U.userNo(name, tag);
	}
	public boolean idck(String id, String pw) {
		return L.loginCk(id, pw);
	}

	public L_Dto idPw(String id, String pw) {
		return L.login(id, pw);
	}

	public void chatting(String roomNo, String chat, String userNo) {
		C.chatting(roomNo, chat, userNo);
	}

	public C_Dto chattinglist(String roomNo) {

		if (roomNo == null) {
			return null;
		}
		C_Dto post = null;
		return post;
	}

	public void signUp(L_Dto roomNo) {
		L.signUp(roomNo);

	}

	public boolean signUpIdCheck(String id) {
		return L.signUpIdCheck(id);
	}

	public L_Dto chatName(String userNo) {
		return U.userName(userNo);
	}

	public void create(String userName, String roomName, String userMax) {
		R.create(userName, roomName, userMax);
	}
	
	public void sendRoom(I_Dto dto, String roomNo) {
		I.sendRoom(dto, roomNo);
	}
	
	public void sendFriend(I_Dto dto) {
		I.sendFriend(dto);
	}
	
	public void invite(String myUserNo,String inviteNo) {
		I.invite(myUserNo, inviteNo);
	}
	
	public void inviteDel(String inviteNo) {
		I.inviteDel(inviteNo);
	}
	
	public void friendDel(String myUserNo,String friendNo) {
		F.friendDel(myUserNo, friendNo);
	}
	
}
