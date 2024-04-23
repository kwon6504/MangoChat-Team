package com.MangoChat.LoginDB;

public class L_Dto {

	public String userNo;
	public String id;
	public String pw;
	public String userName;
	public String userTag;



	public L_Dto(String userName, String userTag) {
		this.userName = userName;
		this.userTag = userTag;
	}

	public L_Dto(String no, String id, String pw, String userName, String userTag) {
		this.userNo = no;
		this.id = id;
		this.pw = pw;
		this.userName = userName;
		this.userTag = userTag;
	}

	public L_Dto(String userNo, String id, String userName, String userTag) {
		this.userNo = userNo;
		this.id = id;
		this.userName = userName;
		this.userTag = userTag;
	}
	
	public L_Dto(String id, String pw, String userName) {
		this.id = id;
		this.pw = pw;
		this.userName = userName;
	}

}
