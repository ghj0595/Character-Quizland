<%@page import="user.model.User"%>
<%@page import="java.sql.Date"%>
<%@page import="user.model.UserRequestDto"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="/resources/style/globals.css">
<script type="module" src="/resources/script/validation-login.js"></script>
<title>회원탈퇴</title>
</head>
<c:import url="/header" />
<body>

<c:if test="${not empty deleteError}">
     <script>
         alert("${deleteError}");
     </script>
</c:if>

	<main>
		<c:import url="/rank" />
		
		<section id="content">
			<div class="content-container">
				<h2>회원탈퇴</h2>
				<form id="form" method="POST" action="/service/users">
				<input type="hidden" name="command" value="delete">
					<div>
						<input type="text" id="code" name="code" placeholder="아이디" value="${log.userCode}" disabled>
						<input type="password" id="password" name="password" placeholder="비밀번호">
					</div>
					<ul class="error-msg-group">
						<li id="error-msg-password-empty" class="error-msg">비밀번호: 필수정보입니다.</li>
						<li id="error-msg-password-pattern" class="error-msg">비밀번호: 유효하지 않은 값입니다.</li>
					</ul>

					<input type="submit" value="회원탈퇴">
				</form>
			</div>
		</section>
		
		<c:import url="/noticelist" />
	</main>

</body>
<c:import url="/footer" />
</html>