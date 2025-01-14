<%@page import="user.model.User"%>
<%@page import="user.model.UserDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="/resources/style/globals.css">
<script type="module" src="/resources/script/validation-update.js"></script>
<title>마이페이지</title>
</head>
<c:import url="/header" />
<body>
	<c:if test="${empty log }">
		<c:redirect url="/login" />
	</c:if>

	<main>
		<c:import url="/rank" />
		
		<section id="content">
			<div class="content-container">
				<h2>${log.id}님회원정보</h2>
				<form method="POST" action="/users/update">
					<div>
						<input type="text" id="id" name="id" value="${log.id}" disabled>
						<input type="password" id="password" name="password" placeholder="기존 비밀번호">
						<input type="password" id="new-password" name="new-password" placeholder="새로운 비밀번호">
						<input type="text" id="name" name="name" value="${log.name}">
					</div>
					<ul class="error-msg-group">
						<li id="error-msg-password-empty">기존 비밀번호: 비밀번호 변경 시, 필수값입니다.</li>
						<li id="error-msg-password-pattern">새로운 비밀번호: 5자이상 입력해주세요.</li>
					</ul>
					<div id="btn-group">
						<input type="submit" value="수정">
						<input type="button" value="로그아웃" onclick="location.href='/users'">
						<input type="button" value="탈퇴" onclick="location.href='/delete'">
					</div>
				</form>

				<div>
				<span class="total-game">${record.totalGame }</span>
				<span class="best-score">${record.bestScore }</span>
				<span class="avg-score">${record.avgScroe }</span>
				<span class="total-score">${record.totalScore }</span>
				<span class="correct-rate">${record.correctRate }</span>
				</div>

			</div>
		</section>
		
		<c:import url="/notice" />
	</main>

</body>
<c:import url="/footer" />
</html>