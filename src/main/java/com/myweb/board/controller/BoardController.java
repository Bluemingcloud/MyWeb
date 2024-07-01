package com.myweb.board.controller;

import java.io.IOException;
import java.io.PrintWriter;

import com.myweb.board.service.BoardService;
import com.myweb.board.service.BoardServiceImpl;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@WebServlet("*.board")
public class BoardController extends HttpServlet {
	
	public BoardController() {
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doAction(request, response);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doAction(request, response);
	}
	
	protected void doAction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String uri = request.getRequestURI();
		String path = request.getContextPath();
		
		String command = uri.substring(path.length());
		
		System.out.println(command);
		
		// BoardService 선언
		BoardService service;
		
		// MVC2 의 기본이동은 forward 이다.
		if(command.equals("/board/list.board")) { // 글 목록 가져오기
			// service 영역을 거쳐서 목록을 가져오기
			service = new BoardServiceImpl();
			service.getList(request, response);
			
		} else if(command.equals("/board/write.board")) { // 글 작성 페이지
			request.getRequestDispatcher("board_write.jsp").forward(request, response);
			
		} else if(command.equals("/board/registForm.board")) { // 글 작성 서비스
			service = new BoardServiceImpl();
			service.regist(request, response);
			
		} else if(command.indexOf("/board/content.board") != -1) { // 글 조회 서비스
			service = new BoardServiceImpl();
			service.getContent(request, response);
			
		} else if(command.equals("/board/contentPage.board")) { // 글 조회 페이지
			request.getRequestDispatcher("board_content.jsp").forward(request, response);
			
		} else if(command.indexOf("/board/modify.board") != -1) { // 글 수정 페이지(이전 내용 가져오기)
			service = new BoardServiceImpl();
			service.getBefore(request, response);
			
		} else if(command.equals("/board/modifyForm.board")) { // 글 수정 서비스
			service = new BoardServiceImpl();
			service.update(request, response);
			
		} else if(command.indexOf("/board/delete.board") != -1) {
			service = new BoardServiceImpl();
			service.delete(request, response);
			
		} else if(command.equals("/board/alert.board")) {
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('로그인 후 이용할 수 있습니다.');");
			out.println("location.href='list.board';");		
			out.println("</script>");
		} else if(command.equals("/board/search.board")) {
			service = new BoardServiceImpl();
			service.search(request, response);
		}
		
	}
	
	
	
}
