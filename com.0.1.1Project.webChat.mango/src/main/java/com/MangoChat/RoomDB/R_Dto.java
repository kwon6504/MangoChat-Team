package com.MangoChat.RoomDB;

public class R_Dto {
	
	public String userNo;
	public String roomNo;
	public String roomName;
	public String masterUserNo;
	public String userMax;
	public int max;
	
	public R_Dto( String roomNo, String masterUserNo, String roomName, String userMax) { // 방 설정 
		this.roomNo = roomNo;
		this.masterUserNo = masterUserNo;
		this.roomName = roomName;
		this.userMax = userMax;
	}
	
	public R_Dto(String roomName, String masterUserNo, int max) {
		this.roomName = roomName;
		this.masterUserNo = masterUserNo;
		this.max = max;
	}

	public R_Dto(String userNo, String roomNo) {
		this.userNo = userNo;
		this.roomNo = roomNo;
	}

}