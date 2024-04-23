package com.MangoChat.ChatDB;

public class C_Dto {

	public String roomNo;
	public String userNo;
	public String chatContent;
	public String time;

	public C_Dto(String roomNo,String userNo, String chatContent, String time) {
		this.roomNo = roomNo;
		this.userNo = userNo;
		this.chatContent = chatContent;
		this.time = time;
	}
	public C_Dto(String userNo,String chatContent, String time) {
		this.userNo = userNo;
		this.chatContent = chatContent;
		this.time = time;
	}
	
}