package com.myweb.board.service;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

//import com.myweb.board.model.BoardDAO;
import com.myweb.board.model.BoardDTO;
import com.myweb.board.model.BoardMapper;
import com.myweb.util.mybatis.MybatisUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class BoardServiceImpl implements BoardService {
	
	// 멤버변수에 세션팩토리 하나 선언(앞글자만 소문자로 변수명 지정)
	private SqlSessionFactory sqlSessionFactory = MybatisUtil.getSqlSessionFactory();
	
	// 글 작성
	@Override
	public void regist(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// request
//		String writer = request.getParameter("writer");
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
//		BoardDAO boardDao = BoardDAO.getInstance();
//		boardDao.regist(dto);
		
		// Mybatis
		SqlSession sql = sqlSessionFactory.openSession(true);
		BoardMapper mapper = sql.getMapper(BoardMapper.class);
		
		int result = mapper.regist(dto);
		sql.close();
		// response
		//response.sendRedirect("list.board");
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.println("<script>");
		if(result != 0) {
			out.println("alert('글이 등록되었습니다.');");
			out.println("location.href='list.board';");
		} else {
			out.println("alert('글이 등록되지 않았습니다.');");
			out.println("location.href='write.board';");
		}
		out.println("</script>");

	}
	
	// 글 목록
	@Override
	public void getList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// request
		
		// DTO
		ArrayList<BoardDTO> list = new ArrayList<>();
		
		// DAO
//		BoardDAO dao = BoardDAO.getInstance();
//		list = dao.getList();

		// Mybatis
		SqlSession sql = sqlSessionFactory.openSession(true); // 커넥션 객체 역할
		BoardMapper mapper = sql.getMapper(BoardMapper.class); // 호출시킬 인터페이스명 작성
		
		list = mapper.getList();
		sql.close(); // 마이바티스 세션 종료
		
		// response
		request.setAttribute("list", list);
		request.getRequestDispatcher("board_list.jsp").forward(request, response);
	}
	
	// 글 조회
	@Override
	public void getContent(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// request
		String bno = request.getParameter("bno");
		
		// DTO
		BoardDTO dto = new BoardDTO();
		
		// DAO
//		BoardDAO dao = BoardDAO.getInstance();
//		dto = dao.getContent(Integer.parseInt(bno));
//		dto.setHit(dto.getHit() + 1);
//		
//		dao.update(dto.getBno(), dto.getHit());
		
		// Mybatis
		SqlSession sql = sqlSessionFactory.openSession(true);
		BoardMapper mapper = sql.getMapper(BoardMapper.class);
		
		mapper.increaseHit(Integer.parseInt(bno));
		dto = mapper.getContent(Integer.parseInt(bno));
		sql.close();
		
		// response
		request.setAttribute("dto", dto);
		request.getRequestDispatcher("contentPage.board").forward(request, response);
		
	}
	
	// 글 수정 전 내용
	@Override
	public void getBefore(HttpServletRequest request, HttpServletResponse response)	throws ServletException, IOException {
		
		// request
		int bno = Integer.parseInt(request.getParameter("bno"));
		
		// DTO
		BoardDTO dto = new BoardDTO();
		
		// DAO
//		BoardDAO dao = BoardDAO.getInstance();
//		dto = dao.getContent(bno);
		
		// Mybatis
		SqlSession sql = sqlSessionFactory.openSession(true);
		BoardMapper mapper = sql.getMapper(BoardMapper.class);
		
		dto = mapper.getContent(bno);
		sql.close();
		
		// response
		request.setAttribute("dto", dto);
		request.getRequestDispatcher("board_modify.jsp").forward(request, response);
		
	}
	
	// 글 수정
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
//		BoardDAO dao = BoardDAO.getInstance();
//		int result = dao.update(dto);
		
		// Mybatis
		SqlSession sql = sqlSessionFactory.openSession(true);
		BoardMapper mapper = sql.getMapper(BoardMapper.class);
		
		int result = mapper.update(dto);
		sql.close();
		
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
	
	// 글 삭제
	@Override
	public void delete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// request
		int bno = Integer.parseInt(request.getParameter("bno"));
		
		// DTO
		
		// DAO
//		BoardDAO dao = BoardDAO.getInstance();
//		int result = dao.delete(bno);
		
		// Mybatis
		SqlSession sql = sqlSessionFactory.openSession(true);
		BoardMapper mapper = sql.getMapper(BoardMapper.class);
		
		int result = mapper.delete(bno);
		sql.close();
		
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
		
	// 글 검색
	@Override
	public void search(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// request
		String search = request.getParameter("search");
		
		// DTO
		ArrayList<BoardDTO> list = new ArrayList<>();
		
		// DAO
//		BoardDAO dao = BoardDAO.getInstance();
//		list = dao.getList(search);
		
		// Mybatis
		SqlSession sql = sqlSessionFactory.openSession(true);
		BoardMapper mapper = sql.getMapper(BoardMapper.class);
		
		list = mapper.getSearch("%" + search + "%");
		sql.close();
		
		// response
		request.setAttribute("list", list);
		request.getRequestDispatcher("board_list.jsp").forward(request, response);
	}

}
