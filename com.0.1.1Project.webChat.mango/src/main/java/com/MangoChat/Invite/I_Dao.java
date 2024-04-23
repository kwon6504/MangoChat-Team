package com.MangoChat.Invite;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.MangoChat.MasterClass.Master_DA;

public class I_Dao extends Master_DA {
	
	I_Dto invite = null;
	
	// 알람들의 정보값을 불러오기
		private I_Dto inviteInfo(String inviteNo) {
			invite = null;
			String sql = String.format("SELECT * FROM %s WHERE inviteNo = %s",DB_TABLE_INVITE,inviteNo);
			try {
				ResultSet rs = Query(sql);
				rs.next();
				int inviteCase = rs.getInt("inviteCase");
					if (inviteCase== 0) { // 친구추가 불러오기
						invite = new I_Dto(
							 rs.getString("inviteNo")
							,rs.getString("userNo")
							,rs.getString("receiveUserNo")
							,rs.getString("inviteCase")
							);
					} else if(inviteCase == 1) { // 방초대 불러오기
						invite = new I_Dto(
							 rs.getString("inviteNo")
							,rs.getString("userNo")
							,rs.getString("receiveUserNo")
							,rs.getString("roomNo")
							,rs.getString("inviteCase")
							);
						}
			}catch (SQLException e) {
				System.out.println(sql);
				e.printStackTrace();
			}finally {
				close();
			}
			return invite;
		}
	
	// 알림창 반환 어레이리스트
	public ArrayList<I_Dto> inviteList(String userNo) {
		ArrayList<I_Dto> inviteList = new ArrayList<I_Dto>();
		String sql = String.format("SELECT * FROM %s WHERE receiveUserNo = %s", DB_TABLE_INVITE, userNo);
		try {
			ResultSet rs = super.Query(sql);
				while (rs.next()) { 
					if (rs.getInt("inviteCase") == 0){ // 친구[0] 초대일때
						inviteList.add(
								new I_Dto(
								 rs.getString("inviteNo")
								,rs.getString("userNo")
								,rs.getString("receiveUserNo")
								,rs.getString("inviteCase")
								)
								);
					} else if (rs.getInt("inviteCase") == 1) { // 방[1] 초대일때
						inviteList.add(
								new I_Dto(
								 rs.getString("inviteNo")
								,rs.getString("userNo")
								,rs.getString("receiveUserNo")
								,rs.getString("roomNo")
								,rs.getString("inviteCase")
								)
								);
					}
				}
			} catch (SQLException e) {
				System.out.println(sql);
				e.printStackTrace();
			} finally {
				close();
			}
		return inviteList;
	}
	
	// 친구 추가 및 방 초대 수락도 여기에서 처리하게 만들었음. 총 2가지 처리함
	public void invite(String userNo,String inviteNo) { // 수락 누를시 작동
		invite = inviteInfo(inviteNo);
		// 친구추가
		if(invite.inviteCase.equals("0")) {
			// 서로 친구추가
			add( invite.userNo
				,invite.receiveUserNo );
			// 서로 친구추가[2]
			add( invite.receiveUserNo
				,invite.userNo );
		// 방 초대
		} else if (invite.inviteCase.equals("1")){
			String sql = String.format(
					"INSERT INTO %s(userNo,roomNo) values (%s,%s)"
					,DB_TABLE_CHATJOIN,userNo,invite.roomNo
					);
			update(sql);
		}
		close();
		inviteDel(inviteNo);
	}
	
	// 알람을 없애야할때 사용
	public void inviteDel(String inviteNo) {
		String sql = String.format("DELETE FROM %s WHERE inviteNo = %s", DB_TABLE_INVITE, inviteNo);
		update(sql);
		close();
	}
	
	// 친구추가 메세지 보내기
	public void sendFriend(I_Dto invite) {
		String sql = String.format(
				"INSERT INTO %s (userNo,receiveUserNo,inviteCase) VALUES (%s,%s,%s)"
				,DB_TABLE_INVITE,invite.userNo,invite.receiveUserNo,invite.inviteCase
				);
		update(sql);
		close();
	}
	
	//친구추가 명령문
	private void add(String userNo, String receiveUserNo) {
		String sql = String.format(
				"INSERT INTO %s(userNo,receiveUserNo) VALUES (%s, %s)"
				,DB_TABLE_FRIENDSLIST, userNo, receiveUserNo
				);
		update(sql);
		close();
	}
		
	// 채팅방 초대 메세지 보내기
	public void sendRoom(I_Dto invite, String roomNo) {
		String sql = String.format(
				"INSERT INTO %s(userNo,receiveUserNo,roomNo,inviteCase) VALUES (%s,%s,%s,%s)"
				,DB_TABLE_INVITE, invite.userNo, invite.receiveUserNo, roomNo, invite.inviteCase
				);
		update(sql);
		close();
	}


}