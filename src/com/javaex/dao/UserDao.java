package com.javaex.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.javaex.vo.UserVo;

public class UserDao {
	
	//필드
	private String id = "webdb";
	private String pw = "webdb";
	private String driver = "oracle.jdbc.driver.OracleDriver";
	private String url = "jdbc:oracle:thin:@localhost:1521:xe";
	
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	
	//생성자
	   
	   
	//메소드-gs
   
   
	//메소드-일반
	public void getConnection() {
		
		try {

			// 1. JDBC 드라이버 (Oracle) 로딩
			Class.forName(driver);
			
			// 2. Connection 얻어오기
			conn = DriverManager.getConnection(url, id, pw);

		} catch (ClassNotFoundException e) {
			System.out.println("error: 드라이버 로딩 실패 - " + e);
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		
	}
	
	//>자원 정리 메소드
	public void close() {
		
		// 5. 자원정리
		try {
			
			if (rs != null) {
				rs.close();
			}
			if (pstmt != null) {
				pstmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		
	}
	
	//회원가입 --> 회원정보 저장
	public int insert(UserVo userVo) {
		
		int count = -1;
		
		getConnection();
		
		try {
			
			// 3. SQL문 준비 / 바인딩 / 실행
			
			//SQL문 준비
			String query = "";
			query += " insert into users ";
			query += " values(seq_users_no.nextval, ?, ?, ?, ?) ";
			
			System.out.println(query);
			
			//바인딩
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, userVo.getId());
			pstmt.setString(2, userVo.getPassword());
			pstmt.setString(3, userVo.getName());
			pstmt.setString(4, userVo.getGender());
			
			//실행
			count = pstmt.executeUpdate();
			
			// 4.결과처리
			System.out.println("["+count + "건이 저장되었습니다.]");
			
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		
		close();
		
		return count;
		
	}
	
	//사용자 정보 가져오기(로그인 시 사용, no name)
	public UserVo getUser(UserVo userVo) {
		
		UserVo authUser = null;
		
		getConnection();
		
		try {
			
			// 3. SQL문 준비 / 바인딩 / 실행
			
			//SQL문 준비
			String query = "";
			query += " select no ";
			query += " 		  , name ";
			query += " from users ";
			query += " where id = ? ";
			query += " and password = ? ";
			
			System.out.println(query);
			
			//바인딩
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, userVo.getId());
			pstmt.setString(2, userVo.getPassword());
			
			//실행
			rs = pstmt.executeQuery();
			
			// 4.결과처리
			while(rs.next()) {
				
				int no = rs.getInt("no");
				String name = rs.getString("name");
				
				authUser = new UserVo();
				authUser.setNo(no);
				authUser.setName(name);
				
				System.out.println(authUser);
				
			}
			
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		
		close();
		
		System.out.println(authUser);
		return authUser;
		
	}
	
	//정보 수정 메소드
	public int update(UserVo userVo) {
		
		int count = -1;
		
		getConnection();

		try {
			
			// 3. SQL문 준비 / 바인딩 / 실행
			
			//SQL문 준비
			String query = "";
			query += " update users ";
			query += " set password = ? ";
			query += "     , name = ? ";
			query += "     , gender = ? ";
			query += " where no = ? ";
			
			//바인딩
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, userVo.getPassword());
			pstmt.setString(2, userVo.getName());
			pstmt.setString(3, userVo.getGender());
			pstmt.setInt(4, userVo.getNo());
			
			//실행
			count = pstmt.executeUpdate();
			
			// 4.결과처리
			System.out.println("["+count + "건이 수정되었습니다.]");

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		
		close();
		
		return count;
		
	}
	
	//사용자 정보 가져오기(회원정보 수정폼, no id password name gender)
	public UserVo getUser(int no) {
		
		UserVo userVo = null;
		
		getConnection();
		
		try {
			
			// 3. SQL문 준비 / 바인딩 / 실행
			
			//SQL문 준비
			String query = "";
			query += " select no ";
			query += " 		  , id ";
			query += " 		  , password ";
			query += " 		  , name ";
			query += " 		  , gender ";
			query += " from users ";
			query += " where no = ? ";
			
			System.out.println(query);
			
			//바인딩
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, no);
			
			//실행
			rs = pstmt.executeQuery();
			
			// 4.결과처리
			while(rs.next()) {
				
				int userno = rs.getInt("no");
				String id = rs.getString("id");
				String password = rs.getString("password");
				String name = rs.getString("name");
				String gender = rs.getString("gender");
				
				userVo = new UserVo();
				userVo.setNo(userno);
				userVo.setId(id);
				userVo.setPassword(password);
				userVo.setName(name);
				userVo.setGender(gender);
								
			}
			
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		
		close();
		
		return userVo;
		
	}

}
