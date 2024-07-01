package com.myweb.board.service;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface BoardService {
	
	// 등록기능
	void regist(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
	
	// 목록조회
	void getList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
	
	// 글 조회
	void getContent(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
	
	// 이전 글 가져오기
	void getBefore(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
	
	// 글 수정
	void update(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
	
	// 글 삭제
	void delete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
	
	// 글 검색
	void search(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;


}
