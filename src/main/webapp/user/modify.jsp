<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="../include/header.jsp"%>

<section>

	<div align="center">
		<h3>회원정보 관리</h3>
		<p>정보를 수정하시려면, 수정버튼을 누르세요</p>
		
		<hr>
		
		<form action="update.user" method="post">
			<table>
				<tr>
					<td>아이디</td>
					<td><input type="text" name="id" readonly="readonly" value="${sessionScope.user_id }"></td>				
				</tr>
				<tr>
					<td>패스워드</td>
					<td><input type="password" name="pw" placeholder="4글자이상" required="required" pattern="[0-9a-zA-Z]{4,}"></td>				
				</tr>
				<tr>
					<td>이름</td>
					<td><input type="text" name="name" value="${dto.name }" required="required"></td>				
				</tr>
				<tr>
					<td>이메일</td>
					<td><input type="email" name="email" value="${dto.email }"></td>				
				</tr>
				<tr>
					<td>남? 여?</td>
					<td>	
						<c:choose>		
							<c:when test="${dto.gender == 'M' }">
								<input type="radio" name="gender" value="M" checked="checked">남자
								<input type="radio" name="gender" value="F">여자
							</c:when>
							<c:otherwise>
								<input type="radio" name="gender" value="M">남자
								<input type="radio" name="gender" value="F" checked="checked">여자							
							</c:otherwise>
						</c:choose>					
					</td>
				</tr>	
			</table>
			
			${msg }
			
			<br>
			<input type="submit" value="수정하기">
			<input type="button" value="취소하기" onclick="location.href='mypage.user';">
			
		</form>
		
	</div>

</section>

<%@ include file="../include/footer.jsp"%>