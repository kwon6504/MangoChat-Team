package com.MangoChat.MasterClass;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Master_DA extends Master_DB{
	
	private Connection con = null;
	private PreparedStatement ps = null;
	
	protected void update(String xx) {
		try {
			LinkMySql();
			ps = con.prepareStatement(xx);
			ps.executeUpdate();
		}catch (SQLException | ClassNotFoundException e) {
			System.out.println("전송한 sql:"+xx);
			e.printStackTrace();
		}
	}
	
	protected ResultSet Query(String xx) {
		ResultSet rs = null;
		try {
			LinkMySql();
			ps = con.prepareStatement(xx);
			rs = ps.executeQuery();
		} catch (SQLException | ClassNotFoundException e) {
			System.out.println("전송한 sql:"+xx);
			e.printStackTrace();
		}
		return rs;
	}
	
	protected void close() {
		try {
			if(ps != null) {
				ps.close();
			}
			if(con != null) {
				con.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void LinkMySql() throws ClassNotFoundException, SQLException {
		if(con == null || con.isClosed()) {
			Class.forName(DB_LINK);
			con = DriverManager.getConnection(DB_URL, DB_ID, DB_PW);
		}
	}
}
// 쿼리문으로 next(); 뽑아내려면 ResultSet rs = super.Query(sql); 이런식으로 선언하고 rs.next(); 이렇게하면됨