package com.myweb.user.service;

import java.io.IOException;

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
			request.getRequestDispatcher("mypage.user").forward(request, response);
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
		
		if(dao.checkPassword(id, pw)) {
			
			dao.deleteUser(id);
			response.sendRedirect("logout.user");
			
		} else {
			String msg = "비밀번호가 일치하지 않습니다.";
			request.setAttribute("msg", msg);
			
			request.getRequestDispatcher("check.user").forward(request, response);
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
		
		if(!dao.checkPassword(id, pwOld) || !pwNew.equals(pwChk)) {
			String msg = "";
			if(pwOld.equals(pwNew)) {
				msg = "이전 비밀번호와 같은 비밀번호로는 변경할 수 없습니다.";
			} else {
				msg = "비밀번호가 일치하지 않습니다.";
			}
			
			request.setAttribute("msg", msg);
			
			request.getRequestDispatcher("updateCheckPw.user").forward(request, response);
		} else {
			
			String msg = "비밀번호가 변경되었습니다.";
			request.setAttribute("msg", msg);
			
			dao.updateUser(id, pwNew);
			request.getRequestDispatcher("mypage.user").forward(request, response);
			
		}
		
	}
	
}
