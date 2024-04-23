package com.MangoChat.Invite;

public class I_Dto {

	public String inviteNo;
	public String userNo;
	public String receiveUserNo;
	public String roomNo;
	public String inviteCase;
	
	public I_Dto(String inviteNo, String userNo, String receiveUserNo, String roomNo, String inviteCase) {
		this.inviteNo = inviteNo;
		this.userNo = userNo;
		this.receiveUserNo = receiveUserNo;
		this.roomNo = roomNo;
		this.inviteCase = inviteCase;
	}

	public I_Dto(String inviteNo, String userNo, String receiveUserNo, String inviteCase) {
		this.inviteNo = inviteNo;
		this.userNo = userNo;
		this.receiveUserNo = receiveUserNo;
		this.inviteCase = inviteCase;
	}

	public I_Dto(String userNo, String receiveUserNo, String inviteCase) {
		this.userNo = userNo;
		this.receiveUserNo = receiveUserNo;
		this.inviteCase = inviteCase;
	}

	
	
	
	
}
