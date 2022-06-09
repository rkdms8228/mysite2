package com.javaex.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.javaex.vo.BoardVo;

public class BoardDao {
	
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
	
	//등록 메소드
	public int write(BoardVo boardVo) {
		
		int count = -1;
		
		getConnection();

		try {

			// 3. SQL문 준비 / 바인딩 / 실행
			
			//SQL문 준비
			String query = "";
			query += " insert into board ";
			query += " values (seq_board_no.nextval, ?, ?, 0, sysdate, ?) ";
			
			//바인딩
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, boardVo.getTitle());
			pstmt.setString(2, boardVo.getContent());
			pstmt.setInt(3, boardVo.getUserNo());
			
			//실행
			count = pstmt.executeUpdate();
			
			// 4.결과처리
			System.out.println("["+count + "건이 등록되었습니다.]");

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		
		close();
		
		return count;
		
	}
	
	//Board list
	public List<BoardVo> getList(String keyword) {
		
		//리스트 만들기
		List<BoardVo> boardList = new ArrayList<BoardVo>();
		
		getConnection();
		
		try {

			// 3. SQL문 준비 / 바인딩 / 실행
			
			//SQL문 준비
			String query = "";
			query += " select  b.no ";
			query += "         ,title ";
			query += "         ,b.content ";
			query += "         ,hit ";
			query += "         ,to_char(b.reg_date,'YY-MM-DD HH24:MI') \"regDate\" ";
			query += "         ,b.user_no \"userNo\" ";
			query += "         ,u.name ";
			query += " from board b, users u ";
			query += " where b.user_no = u.no ";
		
			if(keyword == null) {
				
				//바인딩
				query += " order by no asc ";
				pstmt = conn.prepareStatement(query);
				
			}else {
				
				//바인딩
				query += " and  title like ? ";
				query += " order by title asc ";
				pstmt = conn.prepareStatement(query);
				pstmt.setString(1, '%' + keyword + '%');
				
			}
			
			//실행
			rs = pstmt.executeQuery();
			
			// 4.결과처리
			//반복문으로 Vo 만들기 List에 추가하기
			while(rs.next()) {
				
				int no = rs.getInt("no");
				String title = rs.getString("title");
				String content = rs.getString("content");
				int hit = rs.getInt("hit");
				String regDate = rs.getString("regDate");
				int userNo = rs.getInt("userNo");
				String name = rs.getString("name");

				BoardVo boardVo = new BoardVo(no, title, content, hit, regDate, userNo , name);
				
				boardList.add(boardVo);
				
			}

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		close();
		
		return boardList;
		
	}
	
	//Board 찾기
	public BoardVo getBoard(int no) {
		
		BoardVo boardVo = null;
		
		getConnection();
		
		try {

			// 3. SQL문 준비 / 바인딩 / 실행
			
			//SQL문 준비
			String query = "";
			query += " select  b.no ";
			query += "         ,title ";
			query += "         ,b.content ";
			query += "         ,hit ";
			query += "         ,to_char(b.reg_date,'YY-MM-DD HH24:MI') \"regDate\" ";
			query += "         ,b.user_no \"userNo\" ";
			query += "         ,u.name ";
			query += " from board b, users u ";
			query += " where b.user_no = u.no ";
			query += " and b.no = ? ";

			//바인딩
			pstmt = conn.prepareStatement(query); // 쿼리로 만들기
			pstmt.setInt(1, no);

			//실행
			rs = pstmt.executeQuery();

			// 4.결과처리
			while (rs.next()) {
				String title = rs.getString("title");
				String content = rs.getString("content");
				int hit = rs.getInt("hit");
				String regDate = rs.getString("regDate");
				int userNo = rs.getInt("userNo");
				String name = rs.getString("name");

				boardVo = new BoardVo(no, title, content, hit, regDate, userNo, name);
			}

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		close();
		
		return boardVo;
		
	}
	
	//Board delete
	public void delete(int no) {
		
		getConnection();
		
		try {

			// 3. SQL문 준비 / 바인딩 / 실행
			
			//SQL문 준비
			String query = "";
			query += " delete from board ";
			query += " where no = ? ";

			// 바인딩
			pstmt = conn.prepareStatement(query); // 쿼리로 만들기
			pstmt.setInt(1, no);

			// 실행
			int count = pstmt.executeUpdate();

			// 4.결과처리
			System.out.println("["+count+"건이 삭제 되었습니다.]");

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		close();
		
	}
	
	//Board 수정
	public void update(BoardVo boardVo) {
		
		getConnection();

		try {

			// 3. SQL문 준비 / 바인딩 / 실행
			
			//SQL문 준비
			String query = "";
			query += " update board ";
			query += " set title = ?, ";
			query += "     content = ? ";
			query += " where no = ? ";

			//바인딩
			pstmt = conn.prepareStatement(query); // 쿼리로 만들기

			//실행
			pstmt.setString(1, boardVo.getTitle());
			pstmt.setString(2, boardVo.getContent());
			pstmt.setInt(3, boardVo.getNo());
			

			int count = pstmt.executeUpdate(); // 쿼리문 실행

			// 4.결과처리
			System.out.println("[" + count + "건이 수정 되었습니다.]");

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		
		close();
		
	}
	
	//Board 조회수
	public void boardHit(int no) {
		
		getConnection();

		try {

			// 3. SQL문 준비 / 바인딩 / 실행
			
			//SQL문 준비
			String query = "";
			query += " update board ";
			query += " set hit = hit + 1 ";
			query += " where no = ? ";

			//바인딩
			pstmt = conn.prepareStatement(query); // 쿼리로 만들기

			//실행
			pstmt.setInt(1, no);
			

			int count = pstmt.executeUpdate(); // 쿼리문 실행

			// 4.결과처리
			System.out.println("[" + count + "건의 조회수가 업데이트되었습니다.]");

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		
		close();
	}

}
