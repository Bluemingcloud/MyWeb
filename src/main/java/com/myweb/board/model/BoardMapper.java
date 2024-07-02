package com.myweb.board.model;

import java.util.ArrayList;

public interface BoardMapper {
	
	// 마이바티스는 인터페이스를 호출시키면, 연결될 수 있는 mapper.xml 파일이 실행됨
	public String now(); // test 코드
	
	// 글 목록 조회
	public ArrayList<BoardDTO> getList();
	
	// 글 목록 검색
	public ArrayList<BoardDTO> getSearch(String search);
	
	// 글 작성
	public int regist(BoardDTO dto);
	
	// 글 내용 조회
	public BoardDTO getContent(int bno);
	
	// 글 수정
	public int update(BoardDTO dto);
	
	// 글 삭제
	public int delete(int bno);
	
	// 조회수 증가
	public void increaseHit(int bno);
	
}
