<%@page import="user.model.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="/resources/style/globals.css">
<script type="module" src="/resources/script/validation-join.js"></script>
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
				<h2>${log.name}님 회원정보</h2>
				<form method="POST" action="/users/update">
					<div>
						<input type="text" id="code" name="code" value="${log.code}" disabled>
						<input type="password" id="password" name="password" placeholder="기존 비밀번호">
						<input type="password" id="new-password" name="new-password" placeholder="새로운 비밀번호">
						<input type="text" id="name" name="name" value="${log.name}">
					</div>
					<ul class="error-msg-group">
						<li id="error-msg-password-empty">기존 비밀번호: 비밀번호 변경 시, 필수 값입니다.</li>
						<li id="error-msg-password-pattern">새로운 비밀번호: 5자이상 입력해주세요.</li>
					</ul>
					<div id="btn-group">
						<input type="submit" value="수정">
						<input type="button" value="로그아웃" onclick="location.href='/users'">
						<input type="button" value="탈퇴" onclick="location.href='/delete'">
					</div>
				</form>

				<div>
				<span class="total-game">총 게임 수: ${totalGame }</span>
				<span class="best-score">최고 점수: ${log.bestScore }</span>
				<span class="avg-score">평균 점수: ${avgScroe }</span>
				<span class="total-score">총 점수: ${totalScore }</span>
				<span class="correct-rate">정답률: ${correctRate }</span>
				</div>

			</div>
		</section>
		
		<c:import url="/noticelist" />
	</main>

</body>
<c:import url="/footer" />
</html>