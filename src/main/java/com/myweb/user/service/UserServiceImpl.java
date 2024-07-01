package com.myweb.user.service;

import java.io.IOException;
import java.io.PrintWriter;

import com.myweb.user.model.UserDAO;
import com.myweb.user.model.UserDTO;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class UserServiceImpl implements UserService {
	
	@Override
	public void join(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// 값을 받음
		String id = request.getParameter("id");
		String pw = request.getParameter("pw");
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String gender = request.getParameter("gender");
		
		// 중복되는 회원이 있는지 확인
		// 중복이 없는경우 회원가입 처리
		UserDAO dao = UserDAO.getInstance();
		int cnt = dao.findUser(id);
		
		if(cnt == 0) {
			
			dao.insertUser(new UserDTO(id, pw, name, email, gender, null));
			
			response.sendRedirect("login.jsp");
			
		} else { // 아이디 중복
			request.setAttribute("msg", "이미 존재하는 회원입니다.");
			request.getRequestDispatcher("login.jsp").forward(request, response);
		}
	}
	
	@Override
	public void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String id = request.getParameter("id");
		String pw = request.getParameter("pw");
		
		// 로그인 시도
		UserDAO dao = UserDAO.getInstance();
		UserDTO dto = dao.loginUser(id, pw);
		
		if(dto == null) { // 로그인 실패
			request.setAttribute("msg", "아이디 또는 비밀번호를 확인하세요");
			request.getRequestDispatcher("login.jsp").forward(request, response);
			
		} else { // 로그인 성공
						
			// 세션에 로그인 정보 저장
			
			HttpSession session = request.getSession();
			session.setAttribute("user_id", dto.getId());
			session.setAttribute("user_name", dto.getName());
			
			response.sendRedirect("mypage.user"); // 다시 컨트롤러를 태워 나간다.
			
		}
		
	}
	
	@Override
	public void logout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		session.invalidate();
		
		response.sendRedirect( request.getContextPath() + "/index.jsp");
		
	}
	
	@Override
	public void getInfo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 아이디 기반으로 회원정보를 조회해서 데이터를 가지고, 수정페이지로 이동
		// 실습
		
		/*
		 * 1. 아이디는 세션이 있습니다.
		 * 2. 아이디 기반으로 회원정보를 조회하는 getInfo() DAO에 생성합니다.
		 * 3. 서비스에서는 getInfo() 호출하고, 조회한 데이터를 request에 저장합니다.
		 * 4. forward를 이용해서 modify.jsp로 이동합니다.
		 * 5. 회원정보를 input 태그에 미리 출력해주면 됩니다.
		 */
		
		HttpSession session = request.getSession();
		
		UserDAO dao = UserDAO.getInstance();
		UserDTO dto = dao.getInfo((String)session.getAttribute("user_id"));
		
		request.setAttribute("dto", dto);
		request.getRequestDispatcher("modify.jsp").forward(request, response);
			
	}
	
	@Override
	public void update(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		
		String id = request.getParameter("id");
		String pw = request.getParameter("pw");
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String gender = request.getParameter("gender");
		
		UserDAO dao = UserDAO.getInstance();
		UserDTO dto = new UserDTO(id, pw, name, email, gender, null);
		
		
		if(dao.checkPassword(id, pw)) {
			dao.updateUser(dto);
			session.setAttribute("user_name", name);
			request.setAttribute("dto", dto);
			
			// java에서 알림창을 화면에 보내는 방법
			// out 객체 - 클라이언트로 출력
			response.setContentType("text/html; charset=UTF-8"); // 문서에 대한 타입
			PrintWriter out = response.getWriter(); // 출력 객체 생성(PrintWriter - 브라우저로의 출력 타입)
			// JavaScript 문의 내용을 출력객체를 통해 브라우저에 출력
			out.println("<script>");
			out.println("alert('회원정보가 정상 수정되었습니다.');");
			out.println("location.href='mypage.user';");
			out.println("</script>");
			
		} else {
			String msg = "비밀번호가 일치하지 않습니다.";
			request.setAttribute("msg", msg);
			
			request.getRequestDispatcher("modify.user").forward(request, response);
		}

	}
	
	@Override
	public void delete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		
		String id = (String)session.getAttribute("user_id");
		String pw = request.getParameter("pw");
		
		UserDAO dao = UserDAO.getInstance();
		
		
		// login 메서드 재활용하기
		if(dao.loginUser(id, pw) != null) {
			
			dao.deleteUser(id);
			response.sendRedirect("logout.user");
			
		} else {
			String msg = "비밀번호를 확인하세요.";
			request.setAttribute("msg", msg);
			
			request.getRequestDispatcher("delete.user").forward(request, response);
		}
		
	}
	
	@Override
	public void updatePw(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		
		String id = (String)session.getAttribute("user_id");
		String pwOld = request.getParameter("pwOld");
		String pwNew = request.getParameter("pwNew");
		String pwChk = request.getParameter("pwChk");
		
		UserDAO dao = UserDAO.getInstance();
		String msg = "";
		if(!dao.checkPassword(id, pwOld) || !pwNew.equals(pwChk)) {
			msg = "비밀번호가 일치하지 않습니다.";
			
			request.setAttribute("msg", msg);			
			request.getRequestDispatcher("updatePw.user").forward(request, response);	
		} else if(pwOld.equals(pwNew)){
			msg = "이전 비밀번호와 같은 비밀번호로 변경할 수 없습니다.";
			
			request.setAttribute("msg", msg);			
			request.getRequestDispatcher("updatePw.user").forward(request, response);			
		} else {
			msg = "비밀번호가 변경되었습니다.";
			
			dao.updateUser(id, pwNew);
			
			request.setAttribute("msg", msg);
			request.getRequestDispatcher("mypage.user").forward(request, response);
		}
		
	}
	
}
