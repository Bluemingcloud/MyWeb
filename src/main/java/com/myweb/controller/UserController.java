package com.myweb.controller;

import java.io.IOException;

import com.myweb.user.service.UserService;
import com.myweb.user.service.UserServiceImpl;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("*.user") // 확장자 패턴
public class UserController extends HttpServlet{

	public UserController() {
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

		request.setCharacterEncoding("utf-8");
		
		String uri = request.getRequestURI();	// ip, port 번호 제외된 주소		
		String path = request.getContextPath();	// 프로젝트 식별 이름
		
		String command = uri.substring( path.length() );
		
		System.out.println(command);
		
		// 공통으로 사용할 유저 서비스 객체
		UserService service;
		
		// 기본이동방식 forward
		// MVC2 방식에서 리다이렉트는 다시 컨트롤러를 태울때 사용합니다.
		
		if(command.equals("/user/join.user")) { // 회원가입 화면
			
			request.getRequestDispatcher("join.jsp").forward(request, response);
			
		} else if(command.equals("/user/joinForm.user")) { // 회원가입 기능
			
			service = new UserServiceImpl();
			service.join(request, response);
			
		} else if(command.equals("/user/login.user")) { // 로그인 화면
			
			request.getRequestDispatcher("login.jsp").forward(request, response);
			
		} else if(command.equals("/user/loginForm.user")) { // 로그인 요청
			
			service = new UserServiceImpl();
			service.login(request, response);
			
		} else if(command.equals("/user/mypage.user")) { // 회원정보 화면
			
			request.getRequestDispatcher("mypage.jsp").forward(request, response);
			
		} else if(command.equals("/user/logout.user")) {
			
			HttpSession session = request.getSession();
			session.invalidate();
			
			response.sendRedirect( request.getContextPath() + "/index.jsp");
			
		} else if(command.equals("/user/modify.user")) { // 회원정보 수정 화면
			
			service = new UserServiceImpl();
			service.getInfo(request, response);
			
		} else if(command.equals("/user/update.user")) {
			
			service = new UserServiceImpl();
			service.update(request, response);
			
		} else if(command.equals("/user/delete.user")) {
			
			request.getRequestDispatcher("delete.jsp").forward(request, response);
			
			
		} else if(command.equals("/user/deleteForm.user")) {
			
			service = new UserServiceImpl();
			service.delete(request, response);
			
		} else if(command.equals("/user/updatePw.user")) {
			
			request.getRequestDispatcher("updatePw.jsp").forward(request, response);
			
		} else if(command.equals("/user/updatePwForm.user")) {
			
			service = new UserServiceImpl();
			service.updatePw(request, response);
			
		}
		
		
	}
	
}
