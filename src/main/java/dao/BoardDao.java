package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import dto.BoardDto;
import dto.BoardMemberDto;
import dto.MemberDto;

public class BoardDao {
	
	//DB에 커넥션 준비
		private String driverName = "com.mysql.jdbc.Driver"; // MySQL JDBC 드라이버 이름
		private String url = "jdbc:mysql://localhost:3306/jspdb"; // MySQL이 설치된 서버의 주소(ip)와 연결할 DB(스키마) 이름
		private String username ="root";
		private String password = "12345";
		
		Connection conn = null; // 커넥션 인터페이스를 선언 후 null로 초기화
		PreparedStatement pstmt = null; // SQL 실행문 객체
		ResultSet rs = null;
		
		public List<BoardDto> boardList() { // 게시판의 모든 글 리스트 가져오는 메소드
			
			//String sql = "SELECT * FROM board ORDER BY bnum DESC";
			String sql = "SELECT b.bnum, b.btitle, b.bcontent ,b.memberid, m.memberemail, b.bdate, b.bhit FROM board b INNER JOIN members m ON b.memberid = m.memberid ORDER BY bnum DESC";
			
			List<BoardDto> bDtos = new ArrayList<BoardDto>();
			//List<BoardMemberDto> bmDtos = new ArrayList<BoardMemberDto>(); 
			
			try {
				Class.forName(driverName); // MySQL 드라이버 클래스 불러오기
				
				conn = DriverManager.getConnection(url, username, password); // 커넥션이 메모리에 생성(DB와 연결 커넥션 conn 생성)
				
				pstmt = conn.prepareStatement(sql); // pstmt 인스턴스화
				
				rs = pstmt.executeQuery(); //
				
				while(rs.next()) {
					int bnum = rs.getInt("bnum");
					String btitle = rs.getString("btitle");
					String bcontent = rs.getString("bcontent");
					String memberid = rs.getString("memberid");
					int bhit = rs.getInt("bhit");
					String bdate = rs.getString("bdate");
					
					String memeberemail = rs.getString("memberemail");
					
					MemberDto memberDto = new MemberDto();
					memberDto.setMemberid(memberid);
					memberDto.setMemberemail(memeberemail);
					
					BoardDto bDto = new BoardDto(bnum, btitle, bcontent, memberid, bhit, bdate, memberDto);
					bDtos.add(bDto);
					
					//BoardDto bmDto = new BoardDto(bnum, btitle, bcontent, memberid, memeberemail, bhit, bdate);
					//bmDtos.add(bmDto);
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
			return bDtos;
		}
		
		public void boardWrite(String btitle, String memberid, String bcontent) { // 게시판의 모든 글 리스트 가져오는 메소드
			
			String sql = "INSERT INTO board(btitle, memberid, bcontent, bhit) VALUES(?,?,?,0)";
			
			try {
				Class.forName(driverName); // MySQL 드라이버 클래스 불러오기
				
				conn = DriverManager.getConnection(url, username, password); // 커넥션이 메모리에 생성(DB와 연결 커넥션 conn 생성)
				
				pstmt = conn.prepareStatement(sql); // pstmt 인스턴스화
				
				pstmt.setString(1, btitle);
				pstmt.setString(2, memberid);
				pstmt.setString(3, bcontent);
				
				pstmt.executeUpdate(); //
		
			} catch (Exception e){
				System.out.println("DB 에러 발생.");
				e.printStackTrace();
			} finally { // 에러 발생여부와 상관없이 Connection 닫기 실행
				try {
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
		}
		
		public BoardDto contentView(String bnum) { // 선택한 글 보기
			
			String sql = "SELECT bnum,btitle,memberid,bdate,bhit,bcontent FROM board WHERE bnum=?"; 
			BoardDto boardDto = null;
			
			try {
				Class.forName(driverName); // MySQL 드라이버 클래스 불러오기
				
				conn = DriverManager.getConnection(url, username, password); // 커넥션이 메모리에 생성(DB와 연결 커넥션 conn 생성)
				
				pstmt = conn.prepareStatement(sql); // pstmt 인스턴스화
				
				pstmt.setString(1, bnum);
				
				rs = pstmt.executeQuery(); //
				
				while(rs.next()) {
					int bnum2 = rs.getInt("bnum");
					String btitle = rs.getString("btitle");
					String bcontent = rs.getString("bcontent");
					String memberid = rs.getString("memberid");
					int bhit = rs.getInt("bhit");
					String bdate = rs.getString("bdate");
					
					boardDto = new BoardDto();
					boardDto.setBnum(bnum2);
					boardDto.setBcontent(bcontent);
					boardDto.setBdate(bdate);
					boardDto.setBhit(bhit);
					boardDto.setBtitle(btitle);
					boardDto.setMemberid(memberid);
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
			return boardDto;
		}
		
		public void boardUpdate(String bnum, String btitle, String bcontent) { // 글 수정 업데이트
			
			String sql = "UPDATE board SET btitle=? , bcontent=? WHERE bnum = ?";
			
			try {
				Class.forName(driverName); // MySQL 드라이버 클래스 불러오기
				
				conn = DriverManager.getConnection(url, username, password); // 커넥션이 메모리에 생성(DB와 연결 커넥션 conn 생성)
				
				pstmt = conn.prepareStatement(sql); // pstmt 인스턴스화
				
				pstmt.setString(1, btitle);
				pstmt.setString(2, bcontent);
				pstmt.setString(3, bnum);
				
				pstmt.executeUpdate(); //
		
			} catch (Exception e){
				System.out.println("DB 에러 발생.");
				e.printStackTrace();
			} finally { // 에러 발생여부와 상관없이 Connection 닫기 실행
				try {
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
		}
		
		public void boardDelete(String bnum) { // 글 수정 업데이트
			
			String sql = "DELETE FROM board WHERE bnum = ?";
			
			try {
				Class.forName(driverName); // MySQL 드라이버 클래스 불러오기
				
				conn = DriverManager.getConnection(url, username, password); // 커넥션이 메모리에 생성(DB와 연결 커넥션 conn 생성)
				
				pstmt = conn.prepareStatement(sql); // pstmt 인스턴스화
			
				pstmt.setString(1, bnum);
				
				pstmt.executeUpdate(); //
		
			} catch (Exception e){
				System.out.println("DB 에러 발생.");
				e.printStackTrace();
			} finally { // 에러 발생여부와 상관없이 Connection 닫기 실행
				try {
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
		}
}
