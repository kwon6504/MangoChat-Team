package com.MangoChat.util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.MangoChat.LoginDB.L_Dto;
import com.MangoChat.MasterClass.Master_DA;
import com.MangoChat.MasterClass.Master_DB;
import com.MangoChat.RoomDB.R_Dto;

public class Util extends Master_DA {

	public L_Dto userName(String userNo) { // 닉네임 테그 구하는함수
		L_Dto name = null;
		String sql = String.format("SELECT * FROM %s WHERE userNo = %s", Master_DB.DB_TABLE_LOGIN, userNo);
		try {
			ResultSet rs = super.Query(sql);
			rs.next();
			name = new L_Dto(rs.getString("userName"), rs.getString("userTag"));
		} catch (Exception e) {
			System.out.println(sql);
			e.printStackTrace();
		} finally {
			close();
		}
		return name;
	}

	public int roomUserCount(String roomNo) { // 현제 채팅방에 입장중인 인원
		int count = 0;
		String sql = String.format("SELECT COUNT(*) FROM %s WHERE roomNo = %s", DB_TABLE_CHATJOIN, roomNo);
		try {
			ResultSet rs = super.Query(sql);
			rs.next();
			count = rs.getInt("COUNT(*)");
		} catch (Exception e) {
			System.out.println(sql);
			e.printStackTrace();
		} finally {
			close();
		}
		return count;
	}

//	public int max(String roomNo) { // 방의 최대 인원수 구해주는 함수
//		int max = 0;
//		String sql = String.format("SELECT * FROM %s WHERE roomNo = %s",DB_TABLE_ROOM,roomNo);
//		try {
//			ResultSet rs = Query(sql);
//			rs.next();
//			max = rs.getInt("userMax");
//		}catch (SQLException e) {
//			System.out.println(sql);
//			e.printStackTrace();
//		}finally {
//			close();
//		}

//		return max;
//	}

	public String userNo(String name, String tag) { // 유저의 닉네임과 태그로 유저 고유넘버를 구하는 함수
		String userNo = null;
		String sql = String.format("SELECT * FROM %s WHERE userName = '%s' and userTag = %s", DB_TABLE_LOGIN, name,
				tag);
		try {
			ResultSet rs = Query(sql);
			rs.next();
			userNo = rs.getString("userNo");
		} catch (SQLException e) {
			System.out.println(sql);
			userNo = null;
		} finally {
			close();
		}
		return userNo;
	}

	public R_Dto roomInfo(String roomNo) { // 채팅방의 고유번호로 방 정보를 구하는함수
		R_Dto info = null;
		String sql = String.format("SELECT * FROM %s WHERE roomNo = %s", DB_TABLE_CHATROOM, roomNo);
		try {
			ResultSet rs = Query(sql);
			rs.next();
			info = new R_Dto(rs.getString("roomName"), rs.getString("masterUserNo"), rs.getInt("userMax"));
		} catch (SQLException e) {
			System.out.println(sql);
			e.printStackTrace();
		} finally {
			close();
		}
		return info;
	}

	public boolean userJoinCk(String userNo, String roomNo) {
		boolean ck = false;
		String sql = String.format("SELECT COUNT(*) FROM %s WHERE userNo = %s AND roomNo = %s", DB_TABLE_CHATJOIN, userNo,
				roomNo);
		try {
			ResultSet rs = Query(sql);
			rs.next();
			if (rs.getInt("COUNT(*)") == 0) {
				ck = true;
			}
		} catch (SQLException e) {
			System.out.println(sql);
			e.printStackTrace();
		} finally {
			close();
		}
		return ck;
	}
 // 유저가 초대가 되어있는지 확인, 친구 신청일때는 roomNo를 그냥 0 을 주면 된다
	public boolean inviteCheck(String userNo, String receiveUserNo, String roomNo, String inviteCase) {
		boolean ck = false;
		String sql = null;
		if(!userNo.equals(receiveUserNo)) {
		if (inviteCase.equals("0")) { // 친구 신청일때 조건
			sql = String.format(
					"SELECT COUNT(*) FROM %s WHERE (userNo = %s AND receiveUserNo = %s AND inviteCase = %s) "
					+ "or (userNo = %s AND receiveUserNo = %s AND inviteCase = %s)",
					DB_TABLE_INVITE, userNo, receiveUserNo, inviteCase , receiveUserNo , userNo , inviteCase);
		}
		if (inviteCase.equals("1")) { // 방 초대일때조건
			sql = String.format("SELECT COUNT(*) FROM %s WHERE receiveUserNo = %s AND roomNo = %s AND inviteCase = %s",
					DB_TABLE_INVITE, receiveUserNo, roomNo, inviteCase);
		}
		try {
			ResultSet rs = Query(sql);
			rs.next();
			if (rs.getInt("COUNT(*)") == 0) {
				ck = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
	}
		return ck;
	}
	//친구인지 확인
	public boolean friendCheck(String userNo, String receiveUserNo) {
		boolean ck = false;
		String sql = null;
			sql = String.format(
					"SELECT COUNT(*) FROM %s WHERE (userNo = %s AND receiveUserNo = %s)"
					+ "or(receiveUserNo = %s AND userNo = %s)" ,
					DB_TABLE_FRIENDSLIST, userNo, receiveUserNo , receiveUserNo ,userNo);
		try {
			ResultSet rs = Query(sql);
			rs.next();
			if (rs.getInt("COUNT(*)") == 0) {
				ck = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return ck;
	}
	
	
	public ArrayList<String> listSearch(String name , String userNo) {//사람 검색할때 나를 빼고 닉네임만 입력해서 검색하기 <- 이거 유틸로 바꿔
		ArrayList<String> listSearch = new ArrayList<String>();
		String sql = String.format("select * from %s where userName like '%%%s%%' AND userNo != '%s'", Master_DB.DB_TABLE_LOGIN,
				name,userNo);
		try {
			ResultSet rs = super.Query(sql);
			while (rs.next()) {
				listSearch.add(rs.getString("userNo"));
			}
		} catch (SQLException e) {
			System.out.println("sql:" + sql);
			e.printStackTrace();
		}
		close();
		return listSearch;
	}
	
	// 친구 찾기 (이름,태그 정확할때만 작동) 자기 자신은 로그인 세션 활용해서 검색 안되게 예외 처리 해둠 <-- 유틸로 바꾸기
	public ArrayList<String> tagListSearch(String name, String tag , String myUserNo) {
		ArrayList<String> taglistSearch = new ArrayList<String>();
		String sql = String.format("select * from %s where userName = '%s' and userTag = '%s' AND userNo != '%s'", Master_DB.DB_TABLE_LOGIN,
				name, tag , myUserNo);
		try {
			ResultSet rs = super.Query(sql);
			while (rs.next()) {
				taglistSearch.add(rs.getString("userNo"));
			}
		} catch (SQLException e) {
			System.out.println("sql:" + sql);
			e.printStackTrace();
		}
		close();
		return taglistSearch;
	}
	
	
}
