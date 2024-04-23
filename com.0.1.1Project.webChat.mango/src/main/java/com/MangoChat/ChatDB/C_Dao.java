package com.MangoChat.ChatDB;

import java.sql.ResultSet;
import java.util.ArrayList;

import com.MangoChat.MasterClass.Master_DA;

public class C_Dao extends Master_DA {
 // 채팅방 번호로 저장되어 있는 채팅 리스트들 불러오기
	public ArrayList<C_Dto> chattingList(String roomNo) {
		ArrayList<C_Dto> list = new ArrayList<C_Dto>();
		String sql = String.format("SELECT * FROM %s WHERE roomNo = %s",DB_TABLE_CHATDATA,roomNo);
		try {
			ResultSet rs = super.Query(sql);
			while (rs.next()) {
				list.add(new C_Dto(rs.getString("userNo"), rs.getString("chatContent"),rs.getString("createdAt")));
			}
		} catch (Exception e) {
			System.out.println(sql);
			e.printStackTrace();
		} finally {
			close();
		}
		return list;
	}
// 유저가 치는 채팅을 저장하기
	public void chatting(String roomNo, String chatContent,String userNo) {
		String sql = String.format("INSERT INTO %s(roomNo,userNo,chatContent)VALUES(%s,%s,'%s')",DB_TABLE_CHATDATA,roomNo,userNo,chatContent);
		try {
			super.update(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			close();
		}
	}
}
