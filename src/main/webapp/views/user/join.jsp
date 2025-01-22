<%@page import="java.sql.Date"%>
<%@page import="user.model.UserRequestDto"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="jakarta.tags.core"%>
<%@taglib prefix="fn" uri="jakarta.tags.functions"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="/resources/style/globals.css">
<script type="module" src="/resources/script/validation-join.js"></script>
<title>회원가입</title>
</head>
<c:import url="/header" />
<body>

	<main class="main-container">
		<c:import url="/rank" />
		
		<section id="content">
			<div class="content-container">
				<h2>회원가입</h2>
				<form id="form-join" method="POST" action="/service/users">
				<input type="hidden" name="command" value="join">
					<div>
						<input type="text" id="code" name="code" placeholder="아이디" value="${userData.code}">
						<input type="password" id="password" name="password" placeholder="비밀번호">
						<input type="password" id="chk-password" name="chk-password" placeholder="비밀번호확인">
						<input type="text" id="name" name="name" placeholder="닉네임" value="${userData.name}">
					</div>
					<ul class="error-msg-group">
						<li id="error-msg-code">아이디: 사용할 수 없는 아이디입니다. 다른 아이디를 입력해 주세요.</li>
						<li id="error-msg-code-empty">아이디: 필수 정보입니다.</li>
						<li id="error-msg-code-pattern">아이디: 2~20자 숫자, 영문 대/소문자를 사용해주세요. (특수기호, 공백 사용 불가)</li>
						<li id="error-msg-password-empty">비밀번호: 필수 정보입니다.</li>
						<li id="error-msg-password-pattern">비밀번호: 5자 이상 입력해주세요.</li>
						<li id="error-msg-password-chk">비밀번호: 일치하지않습니다.</li>
						<li id="error-msg-name-empty">닉네임: 필수 정보입니다.</li>
						<li id="error-msg-name-pattern">닉네임: 2~20자 숫자, 한글, 영문 대/소문자를사용해 주세요. (특수기호, 공백 사용 불가)</li>
					</ul>
					<input type="submit" value="회원가입">
				</form>
			</div>
		</section>
		
		<c:import url="/noticelist" />
	</main>
	
</body>
<c:import url="/footer" />
</html>