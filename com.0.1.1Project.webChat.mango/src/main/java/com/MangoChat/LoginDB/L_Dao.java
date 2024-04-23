package com.MangoChat.LoginDB;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.MangoChat.MasterClass.Master_DA;


public class L_Dao extends Master_DA {

	 
	// 아이디 하고 비밀번호를 입력하면 유저 고유번호, 닉네임, 태그가 출력
	
	public L_Dto login(String id,String pw) {
		L_Dto check = null;
		String sql = String.format("SELECT * FROM %s WHERE id = '%s' AND pw = '%s'", DB_TABLE_LOGIN,id,pw);
		try {
			ResultSet rs = super.Query(sql);
			rs.next();
			check = new L_Dto(
					 rs.getString("userNo")
					,rs.getString("id")
					,rs.getString("userName")
					,rs.getString("userTag")
					);
		} catch (SQLException | NullPointerException e) {
			System.out.println(sql);
			e.printStackTrace();
		} finally {
			close();
		}
		return check;
	}
	
	// 아이디하고 비밀번호를 확인하여 올바른 값인지 로그인시 올바른 아이디 비밀번호인지 체크 해주는 함수
	
	public boolean loginCk(String id,String pw) {
		boolean check = false;
		String sql = String.format(
				"SELECT COUNT(*) FROM %s WHERE id = '%s' AND pw = '%s'"
				,DB_TABLE_LOGIN,id,pw);
			try {
				ResultSet rs = super.Query(sql);
				rs.next();
				int count = rs.getInt("COUNT(*)");
				if(count == 1) {
					check = true;
				}
			} catch (SQLException | NullPointerException e) {
				System.out.println(sql);
				e.printStackTrace();
			} finally {
				close();
			}
			return check;
	}
	
	// 아이디 중복체크, 회원가입할때 사용
	public boolean signUpIdCheck(String id) {
		boolean check = false;
		String sql = String.format("SELECT COUNT(*) FROM %s WHERE id = '%s'", DB_TABLE_LOGIN, id);
			try {
				ResultSet rs = super.Query(sql);
				rs.next();
				String count = rs.getString("COUNT(*)");
				if (count.equals("0")) { 
					check = true;
				}
			} catch (SQLException e) {
				System.out.println(sql);
				e.printStackTrace();
			} finally {
				close();	
			}
			return check;
	}
	
	// 회원가입할떄 입력한 정보들을 저장하는 함수
	public void signUp(L_Dto signUp) {
		int userTag = userTag(signUp.userName);
		String sql = String.format(
				"INSERT INTO %s (id,pw,userName,userTag) VALUES ('%s','%s','%s',%d)"
				,DB_TABLE_LOGIN,signUp.id,signUp.pw,signUp.userName,userTag
				);
			try {
				update(sql);
			} catch (Exception e) {
				System.out.println(sql);
				e.printStackTrace();
			}finally {
				close();	
			}

	}
	
	// 동일한 닉네임을 조회해서 테그 입력
	private int userTag(String userName) {
		int tagNo = 0;
		String sql = String.format(
				"SELECT MAX(userTag)FROM %s WHERE userName = '%s'"
				,DB_TABLE_LOGIN,userName
				);
		try {
			ResultSet rs = super.Query(sql);
			if(rs.next()) {
				tagNo = rs.getInt("MAX(userTag)");
			}
		} catch (SQLException e) {
			System.out.println(sql);
			e.printStackTrace();
		}finally {
			close();	
		}

		return tagNo + 1;
	}

}
