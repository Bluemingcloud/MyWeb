<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp" %>

<section>
	<div align="center">
		<h3>패스워드 확인</h3>
		<p>비밀번호를 입력하세요.</p>
		<form action="deleteForm.user" method="post">
			<input type="password" name="pw" placeholder="비밀번호"><br>
			<input type="submit" value="확인">
			<input type="button" value="취소" onclick="location.href='mypage.user';"><br>
			
			${msg }
		</form>
	</div>
</section>

<%@ include file="../include/footer.jsp" %>