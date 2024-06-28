<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp"%>

<section>
	<div align="center">
		<h3>비밀번호를 변경합니다.</h3>
		<form action="updatePw.user" method="post">
			<table>
				<tr>
					<td>이전 비밀번호</td>
					<td>			
			 			<input type="password" name="pwOld" placeholder="비밀번호를 입력하세요" required="required" pattern="[0-9a-zA-Z]{4,}"><br>
			 		</td>
			 	</tr>

			 	<tr>
			 		<td>새 비밀번호</td>
			 		<td>
			 			<input type="password" name="pwNew" placeholder="4글자이상" required="required" pattern="[0-9a-zA-Z]{4,}"><br>
			 		</td>
			 	</tr>
			 	<tr>
			 		<td>비밀번호 확인</td>
			 		<td>
			 			<input type="password" name="pwChk" placeholder="4글자이상" required="required" pattern="[0-9a-zA-Z]{4,}"><br>
			 		</td>
			 	</tr>
				
			</table>
			${msg }<br>
			<input type="submit" value="변경하기">
			<input type="button" value="취소하기" onclick="location.href='mypage.user'">
		</form>
	</div>

</section>

<%@ include file="../include/footer.jsp"%>