<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp" %>

<section>

	<div align="center">
		<h3>로그인</h3>
		<hr>
		
		<form action="loginForm.user" method="post">
			<input type="text" name="id" placeholder="아이디"><br>
			<input type="password" name="pw" placeholder="패스워드"><br>
			
			${msg }<br>
			
			<input type="submit" value="로그인">
			<input type="button" value="회원가입" onclick="location.href='join.user'">
		</form>	
	</div>

</section>

<%@ include file="../include/footer.jsp" %>