package com.MangoChat.FriendDB;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.MangoChat.MasterClass.Master_DA;
import com.MangoChat.MasterClass.Master_DB;
import com.MangoChat.util.Util;

public class F_Dao extends Master_DA {
	
	// 유틸불러오기
	Util util = new Util();
	
	// 현재 접속한 아이디에 있는 친구 리스트 불러오기
	public ArrayList<String> friendList(String userNo) {
		ArrayList<String> list = new ArrayList<>();
		String sql = String.format("SELECT * FROM %s WHERE userNo = %s", DB_TABLE_FRIENDSLIST, userNo);
		try {
			ResultSet rs = Query(sql);
			while (rs.next()) {
				list.add(rs.getString("receiveUserNo"));
			}
		} catch (SQLException e) {
			System.out.println(sql);
			e.printStackTrace();
		}finally {
			close();
		}
		return list;
	}
	
	//친구 삭제 실행문
	public void friendDel(String userNo, String receiveUserNo) {
		del(userNo,receiveUserNo);
		del(receiveUserNo,userNo);
	}
	
	//친구 삭제 명령 입력문
	private void del(String userNo, String receiveUserNo) {
		String delNo = String.format(
				"DELETE FROM %s WHERE userNo = %s AND receiveUserNo = %s"
				, Master_DB.DB_TABLE_FRIENDSLIST, userNo, receiveUserNo);
		update(delNo);
		close();
	}

}
