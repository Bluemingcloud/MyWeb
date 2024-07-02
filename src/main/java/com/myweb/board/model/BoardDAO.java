package com.myweb.board.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.myweb.util.JdbcUtil;

public class BoardDAO {
	
	private static BoardDAO instance = new BoardDAO();
	
	
	private BoardDAO() {
		
		try {
			InitialContext init = new InitialContext();
			ds = (DataSource)init.lookup("java:comp/env/jdbc/oracle");
			
		} catch (Exception e) {
			System.out.println("커넥션 풀 에러");
		}
		
	}
	
	public static BoardDAO getInstance() {
		return instance;
	}
	
	private DataSource ds;
	
	// 글 데이터베이스 등록
	public void regist(BoardDTO dto) {
		
		String sql = "INSERT INTO BOARD(BNO, WRITER, TITLE, CONTENT) "
				   + "VALUES(BOARD_SEQ.NEXTVAL, ?, ?, ?)";
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getWriter());
			pstmt.setString(2, dto.getTitle());
			pstmt.setString(3, dto.getContent());
			pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.close(conn, pstmt, null);
		}
		
	}
	
	// 글 목록 데이터베이스 조회
	public List<BoardDTO> getList() {
		
		List<BoardDTO> list = new ArrayList<>();
		
		String sql = "SELECT * FROM BOARD ORDER BY BNO DESC";
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				
				int bno = rs.getInt("bno");
				String writer = rs.getString("writer");
				String title = rs.getString("title");
				Timestamp regdate = rs.getTimestamp("regdate");
				int hit = rs.getInt("hit");
				// DTO 생성
				BoardDTO dto = new BoardDTO(bno, writer, title, null, regdate, hit);
				
				// list 추가
				list.add(dto);
			}	
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.close(conn, pstmt, rs);
		}

		return list;		
	}
	
	public List<BoardDTO> getList(String search) {
		
		List<BoardDTO> list = new ArrayList<>();
		
		String sql = "SELECT * FROM BOARD WHERE TITLE LIKE ? ORDER BY BNO DESC";
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "%" + search + "%");
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				
				int bno = rs.getInt("bno");
				String writer = rs.getString("writer");
				String title = rs.getString("title");
				Timestamp regdate = rs.getTimestamp("regdate");
				int hit = rs.getInt("hit");
				// DTO 생성
				BoardDTO dto = new BoardDTO(bno, writer, title, null, regdate, hit);
				
				// list 추가
				list.add(dto);
			}	
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.close(conn, pstmt, rs);
		}

		return list;
	}
	
	// 글 내용 데이터베이스 조회
	public BoardDTO getContent(int bno) {
		
		BoardDTO dto = null;
		
		String sql = "SELECT * FROM BOARD WHERE BNO = ?";
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null; 
		
		try {
			
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, bno);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				String writer = rs.getString("writer");
				String title = rs.getString("title");
				String context = rs.getString("content");
				Timestamp regdate = rs.getTimestamp("regdate");
				int hit = rs.getInt("hit");
				
				dto = new BoardDTO(bno, writer, title, context, regdate, hit);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.close(conn, pstmt, rs);
		}
		
		return dto;
	}
	
	// 글 수정 데이터베이스 저장
	public int update(BoardDTO dto) {
		
		String sql = "UPDATE BOARD SET TITLE = ?, CONTENT = ? WHERE BNO = ?";
		int result = 0;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getTitle());
			pstmt.setString(2, dto.getContent());
			pstmt.setInt(3, dto.getBno());
			result = pstmt.executeUpdate();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.close(conn, pstmt, null);
		}
		
		return result;
	}
	
	public int update(int bno, int hit) {
		
		String sql = "UPDATE BOARD SET HIT = ? WHERE BNO = ?";
		int result = 0;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, hit);
			pstmt.setInt(2, bno);
			result = pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.close(conn, pstmt, null);
		}
		
		return result;
		
	}
	
	// 글 데이터베이스 삭제 
	public int delete(int bno) {
		int result = 0;
		String sql = "DELETE FROM BOARD WHERE BNO = ?";
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, bno);
			result = pstmt.executeUpdate();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.close(conn, pstmt, null);
		}
		
		return result;
	}

}
