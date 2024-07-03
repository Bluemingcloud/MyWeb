package com.myweb.util.filter;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

// 수정화면, 수정기능, 삭제기능
@WebFilter({"/board/modify.board", "/board/modifyForm.board", "/board/delete.board"})
public class BoardAuthenticationFilter implements Filter {
	
	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		
		// 게시글, 수정, 삭제에 대한 필터 - 내가 쓴 글은 나만 수정할 수 있다.
		// 세션 값과, 클라이언트에서 넘어오는 작성자(writer) 값을 서로 비교
		// 수정, 삭제 요청이 들어올때는 작성자(writer) 정보를 넘겨준다.
		HttpServletRequest request = (HttpServletRequest)req;
		HttpServletResponse response = (HttpServletResponse)res;
		
		HttpSession session = request.getSession();
		String id = (String)session.getAttribute("user_id");
		
		if(id == null) {
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('로그인이 필요한 서비스 입니다.');");
			out.println("location.href='" + request.getContextPath() +"/user/login.user';");
			out.println("</script>");
			
			return;
		}
		
		// 클라이언트에서 넘어오는 writer 값과 세션 id 값을 비교
		String writer = request.getParameter("writer");
		
		// 작성자가 null 이거나, 세션 값과 작성자가 같지 않다면
		if(writer == null || !writer.equals(id)) {
			
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('권한이 없습니다.');");
			out.println("location.href='"+ request.getContextPath() +"/board/list.board';");
			out.println("</script>");
			
			return;
		}
		
		chain.doFilter(request, response);		
		
	}
	
}
