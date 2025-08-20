package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import dto.BoardDto;

public class MemberDao {
	
	//DB에 커넥션 준비
	private String driverName = "com.mysql.jdbc.Driver"; // MySQL JDBC 드라이버 이름
	private String url = "jdbc:mysql://localhost:3306/jspdb"; // MySQL이 설치된 서버의 주소(ip)와 연결할 DB(스키마) 이름
	private String username ="root";
	private String password = "12345";
	
	Connection conn = null; // 커넥션 인터페이스를 선언 후 null로 초기화
	PreparedStatement pstmt = null; // SQL 실행문 객체
	ResultSet rs = null;
	
	public int loginCheck(String mid, String mpw) { // 로그인 성공 여부를 반환하는 메소드
		String sql = "SELECT * FROM members WHERE memberid=? AND memberpw=?";
		int sqlResult = 0;
		
		try {
			Class.forName(driverName); // MySQL 드라이버 클래스 불러오기
			
			conn = DriverManager.getConnection(url, username, password); // 커넥션이 메모리에 생성(DB와 연결 커넥션 conn 생성)
			
			pstmt = conn.prepareStatement(sql); // pstmt 인스턴스화
			
			pstmt.setString(1, mid);
			pstmt.setString(2, mpw);
			rs = pstmt.executeQuery();
			
			if(rs.next()) { // 참이면 로그인 성공
				sqlResult = 1;
			} else { // 거짓이면 로그인 실패
				sqlResult = 0;
			}
			
		} catch (Exception e){
			System.out.println("DB 에러 발생. 로그인 실패.");
			e.printStackTrace();
		} finally { // 에러 발생여부와 상관없이 Connection 닫기 실행
			try {
				if(rs != null) { 
					rs.close();
				}
				if(pstmt != null) { // pstmt가 null이 아니면 닫기. conn 닫기보다 먼저 실행 되어야만 함
					pstmt.close();
				}
				if (conn != null) { // Connection이 null이 아닐때만 닫기 실행
					conn.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return sqlResult;
	}
	
	
}
