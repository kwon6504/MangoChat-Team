package com.MangoChat.RoomDB;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.MangoChat.MasterClass.Master_DA;

public class R_Dao extends Master_DA {
	
	// 방 만들기
	public void create(String userNo, String roomName, String userMax) {
		chatRoom(userNo, roomName, userMax); // 채팅창 정보 생성, 유저 넘버, 설정한 채팅이름, 채팅창 노출 여부 등
		String roomNo = roomNo(userNo); // 채팅창 넘버 구하는함수
		chatJoin(userNo, roomNo); // 채팅창 넘버와 유저 넘버를 묶어서 연관시키는 함수
	}
	
	// 채팅방 정보 생성 <-- 첫번째
	private void chatRoom(String userNo, String roomName, String userMax) {
		String sql = String.format(
				"INSERT INTO %s(masterUserNo,roomName,userMax) VALUES (%s,'%s',%s)"
				,DB_TABLE_CHATROOM, userNo, roomName, userMax
				);
		try {
			// 공개 비공개 or 최대인원수는 임시로 지정해줌
			update(sql);
		} catch (Exception e) {
			System.out.println(sql);
			e.printStackTrace();
		} finally {
			close();
		}
	}
	//두번째 채팅방 번호와 유저 고유번호를 연관시켜줌
	private void chatJoin(String userNo, String roomNo) {
		String sql = String.format(
				"INSERT INTO %s(userNo,roomNo) VALUES (%s,%s)"
				,DB_TABLE_CHATJOIN,userNo,roomNo
				);
		try {
			// 채팅넘버와 유저 넘버 연관시켜줌
			update(sql);
		} catch (Exception e) {
			System.out.println(sql);
			e.printStackTrace();
		} finally {
			close();
		}
	}
	// 채팅방을 만들고 그 방의 고유 번호를 조회
	private String roomNo(String userNo) {
		String roomNo = null;
		String sql = String.format(
				"SELECT MAX(roomNo) FROM %s WHERE masterUserNo = %s"
				,DB_TABLE_CHATROOM,userNo
				);
		try {
			// 가장 마지막에 만들어진게 바로전에 INSERT 해준 값이기 때문에 MAX 로 값을 구해줌, 유저넘버가 들어간이유는 혹시라도 동시에
			// 방만드는경우가 생길까봐
			ResultSet rs = Query(sql);
			rs.next();
			roomNo = rs.getString("MAX(roomNo)");
		} catch (SQLException e) {
			System.out.println(sql);
			e.printStackTrace();
		} finally {
			close();
		}
		return roomNo;
	}
	
	// 유저가 들어가있는 채팅방 번호를 검색
	public ArrayList<String> roomList(String userNo) { // 들어가 있는 방 찾기
		ArrayList<String> roomList = new ArrayList<String>();
		String sql = String.format(
				"SELECT * FROM %s WHERE userNo = %s"
				,DB_TABLE_CHATJOIN,userNo
				);
		try {
			ResultSet rs = Query(sql);
			while (rs.next()) {
				roomList.add(rs.getString("roomNo"));
			}
		} catch (SQLException e) {
			System.out.println(sql);
			e.printStackTrace();
		} finally {
			close();
		}
		return roomList;
	}

	public R_Dto roomInfo(String roomNo) { // 들어와 있는방들의 정보 확인용
		R_Dto info = null;
			String sql = String.format("SELECT * FROM %s WHERE roomNo = %s", DB_TABLE_CHATROOM, roomNo);
			try {
				ResultSet rs = Query(sql);
				rs.next();
				info = new R_Dto(roomNo // 어레이리스트 무조건 넣어야되서 불편해도 참아요
						,rs.getString("masterUserNo")
						,rs.getString("roomName")
						,rs.getString("userMax")
						);
			} catch (Exception e) {
				System.out.println(sql);
				e.printStackTrace();
			} finally {
				close();
			}
		return info;
	}

}
