package com.MangoChat.MasterClass;

public class Master_DB {
	public static final String DB_LINK =  "com.mysql.cj.jdbc.Driver";
	public static final String DB_NAME = "chat";
	public static final String DB_SQL_URL = "jdbc:mysql://localhost:3306/" + DB_NAME;
	public static final String DB_URL = DB_SQL_URL;
	public static final String DB_ID = "root";
	public static final String DB_PW = "root";
	
	// 테이블 이름들
	public static final String DB_TABLE_LOGIN = "login";
	public static final String DB_TABLE_CHATROOM = "chatRoom";
	public static final String DB_TABLE_CHATJOIN = "chatJoin";
	public static final String DB_TABLE_CHATDATA = "chatData"; 
	public static final String DB_TABLE_INVITE = "invite";
	public static final String DB_TABLE_FRIENDSLIST = "friendsList";
	
	
}
