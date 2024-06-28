<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp" %>

<section>
	<div align="center">
		<h3>${sessionScope.user_name }(${sessionScope.user_id })님의 회원정보를 관리합니다.</h3>
		
		${msg }<br>
		
		<a href="modify.user">회원정보 관리</a><br>
		<a href="check.user">회원탈퇴</a><br>
		<a href="updateCheckPw.user">비밀번호 변경</a><br>
		
	</div>

</section>


<%@ include file="../include/footer.jsp" %>