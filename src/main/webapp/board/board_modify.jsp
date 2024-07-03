<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp" %>
<div align="center" class="div_center">
	<h3>게시판 글 수정 페이지</h3>
	<hr>
	<!-- 
		화면에 출력할 필요는 없지만 
		사용해야하는(숨겨서 전송해야 하는) 데이터는 hidden 태그를 통해 숨겨둘 수 있다.
	 -->
	<form action="modifyForm.board" method="post">
		
		<input type="hidden" name="bno" value="${dto.bno }">	
	 	
		<table border="1" width="500">
			
			<tr>
				<td>글 번호</td>
				<td>${dto.bno }</td>
			</tr>
			<tr>
				<td>작성자</td>
				<td><input type="text" name="writer" value="${dto.writer }" readonly></td>
			</tr>
			<tr>
				<td>글 제목</td>
				<td>
					<input type="text" name="title" value="${dto.title }" required>
				</td>
			</tr>
			<tr>
				<td>글 내용</td>
				<td>
					<textarea rows="10" style="width: 95%;" name="content">${dto.content }
					</textarea>
				</td>
			</tr>
			<tr>
				<td colspan="2">
					<input type="submit" value="수정 하기">&nbsp;&nbsp;
					<input type="button" value="목록"  onclick="location.href='list.board';">        
				</td>
			</tr>
			
		</table>
	</form>
	
</div>
<%@ include file="../include/footer.jsp" %>