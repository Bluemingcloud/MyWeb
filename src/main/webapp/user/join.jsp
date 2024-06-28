<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ include file="../include/header.jsp" %>

<section>

	<!-- 
		input 태그에 많이 사용되는 주요 속성
		
		required는 공백을 허용하지 않음
		readonly는 값을 수정하지 못하고 읽기만 가능
		disabled 태그를 사용하지 않음
		checked 체크박스에서 미리선택
		selected 셀렉트태그에서 미리 선택
	
	 -->
	<div align="center">
		<h3>노름은 파도고, 프로그램은 data flow다</h3>
		
		<hr>
		
		<form action="joinForm.user" method="post">
			<table>
				<tr>
					<td>아이디</td>
					<td><input type="text" name="id" placeholder="4글자이상" required="required" pattern="[0-9a-zA-Z]{4,}"></td>				
				</tr>
				<tr>
					<td>패스워드</td>
					<td><input type="password" name="pw" placeholder="4글자이상" required="required" pattern="[0-9a-zA-Z]{4,}"></td>				
				</tr>
				<tr>
					<td>이름</td>
					<td><input type="text" name="name" placeholder="이름" required="required"></td>				
				</tr>
				<tr>
					<td>이메일</td>
					<td><input type="email" name="email"></td>				
				</tr>
				<tr>
					<td>남? 여?</td>
					<td>
						<input type="radio" name="gender" value="M" checked="checked">남자
						<input type="radio" name="gender" value="F">여자
					</td>				
				</tr>		
			</table>
			
			${msg }
			
			<br>
			<input type="submit" value="가입">
			<input type="button" value="로그인" onclick="location.href='login.user';">
			
		</form>
		
	</div>

</section>

<%@ include file="../include/footer.jsp" %>