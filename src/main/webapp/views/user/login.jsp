<%@page import="java.sql.Date"%>
<%@page import="user.model.UserRequestDto"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="/resources/style/global.css">
<script type="module" src="/resources/script/validation-login.js"></script>
<title>로그인</title>
</head>
<c:import url="/header" />
<body>
<c:if test="${not empty log}">
	<c:redirect url="/mypage" />
</c:if>
<div class="content-container">
	<h2>로그인</h2>
	<form method="POST" action="/users/login">
		<div>
			<input type="text" id="id" name="id" placeholder="아이디">
			<input type="password" id="password" name="password" placeholder="비밀번호">
		</div>
		<ul class="error-msg-group">
			<li id="error-msg-id-empty" class="error-msg">아이디: 필수 정보입니다.</li>
			<li id="error-msg-password-empty" class="error-msg">비밀번호: 필수 정보입니다.</li>
			<li id="error-msg-password-pattern" class="error-msg">비밀번호: 유효하지 않은 값입니다.</li>
		</ul>
	
		<input type="submit" value="로그인">
	</form>
</div>
</body>
<c:import url="/footer" />
</html>