package com.myweb.board.service;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import com.myweb.board.model.BoardDAO;
import com.myweb.board.model.BoardDTO;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class BoardServiceImpl implements BoardService {

	@Override
	public void regist(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// request
		String writer = request.getParameter("writer");
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		
		HttpSession session = request.getSession();
		String user_id = (String)session.getAttribute("user_id");
		
		// DTO
		BoardDTO dto = new BoardDTO();
		dto.setWriter(user_id);
		dto.setTitle(title);
		dto.setContent(content);
		
		// DAO
		BoardDAO boardDao = BoardDAO.getInstance();
		boardDao.regist(dto);
		
		
		// response
		//response.sendRedirect("list.board");
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.println("<script>");
		out.println("alert('글이 등록되었습니다.');");
		out.println("location.href='list.board';");
		out.println("</script>");

	}
	
	@Override
	public void getList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// request
		
		// DTO
		List<BoardDTO> list = new ArrayList<>();
		
		// DAO
		BoardDAO dao = BoardDAO.getInstance();
		list = dao.getList();
		
		// response
		request.setAttribute("list", list);
		request.getRequestDispatcher("board_list.jsp").forward(request, response);
	}
	
	@Override
	public void getContent(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// request
		String bno = request.getParameter("bno");
		
		// DTO
		BoardDTO dto = new BoardDTO();
		
		// DAO
		BoardDAO dao = BoardDAO.getInstance();
		dto = dao.getContent(Integer.parseInt(bno));
		dto.setHit(dto.getHit() + 1);
		
		dao.update(dto.getBno(), dto.getHit());
		
		// response
		request.setAttribute("dto", dto);
		request.getRequestDispatcher("contentPage.board").forward(request, response);
		
	}
	
	@Override
	public void getBefore(HttpServletRequest request, HttpServletResponse response)	throws ServletException, IOException {
		
		// request
		int bno = Integer.parseInt(request.getParameter("bno"));
		
		// DTO
		BoardDTO dto = new BoardDTO();
		
		// DAO
		BoardDAO dao = BoardDAO.getInstance();
		dto = dao.getContent(bno);
		
		// response
		request.setAttribute("dto", dto);
		request.getRequestDispatcher("board_modify.jsp").forward(request, response);
		
	}
	
	@Override
	public void update(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// request
		int bno = Integer.parseInt(request.getParameter("bno"));
		String writer = request.getParameter("writer");
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		
		// DTO
		BoardDTO dto = new BoardDTO(bno, writer, title, content, null, 0);
		
		// DAO
		BoardDAO dao = BoardDAO.getInstance();
		int result = dao.update(dto);
		
		// response
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.println("<script>");
		if(result != 0) {
			out.println("alert('글이 수정되었습니다.');");
			out.println("location.href='content.board?bno=" + bno + "';");

		} else {
			out.println("alert('존재하지 않는 글입니다.');");
			out.println("location.href='list.board';");
		}
		out.println("</script>");
		
	}
	
	@Override
	public void delete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// request
		int bno = Integer.parseInt(request.getParameter("bno"));
		
		// DTO
		
		// DAO
		BoardDAO dao = BoardDAO.getInstance();
		int result = dao.delete(bno);
		
		// response
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.println("<script>");
		if(result != 0) {
			out.println("alert('글이 삭제되었습니다.');");
			out.println("location.href='list.board';");
		} else {
			out.println("alert('삭제할 수 없습니다.');");
			out.println("location.href='content.board?bno=" + bno +"';");
		}
		out.println("</script>");
	}
		
	@Override
	public void search(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String search = request.getParameter("search");
		
		List<BoardDTO> list = new ArrayList<>();
		
		BoardDAO dao = BoardDAO.getInstance();
		list = dao.getList(search);
		
		request.setAttribute("list", list);
		request.getRequestDispatcher("board_list.jsp").forward(request, response);
	}
}
