package com.MangoChat.FriendDB;

public class F_Dto {
	
	public String userNo;
	public String receiveUserNo;
	public String time;
	
	public F_Dto(String userNo,String receiveUserNo, String time) {
			this.userNo = userNo;
			this.receiveUserNo = receiveUserNo;
			this.time = time;
	}
}