<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="/resources/style/globals.css">
<link rel="stylesheet" href="/resources/style/game.css">
<script src="/resources/script/game-create.js"></script>
<title>Game</title>
</head>
<c:choose>
	<c:when test="${empty quizNumber}">
		<c:set var="quizNumber" value="1" scope = "session" />
	</c:when>
	<c:otherwise>
		<c:set var="quizNumber" value="${quizNumber+1}"/>
	</c:otherwise>
</c:choose>
<c:if test="${empty quizSize}">
	<c:set var="quizSize" value="10"/>
</c:if>
<c:if test="${empty solve_codes}">
	<c:set var="solve_codes" value="[]"/>
</c:if>
<c:if test="${empty score}">
	<c:set var="score" value="10"/>
</c:if>
<c:if test="${empty timer}">
	<c:set var="timer" value="20"/>
</c:if>
<body>
	<c:import url="/header" />
	<main>
		<c:import url="/rank" />
		<section id="content">
			<form id="game-form" method="POST">
				<input type="hidden" name="quiz_number" value="${quizNumber}">
				<input type="hidden" name="quiz_size" value="${quizSize}">
				<input id="quiz_code" type="hidden" name="quiz_code" value="${quizCode}">
				<input id="score" type="hidden" name="score" value="${score}">
				<input id="timer" type="hidden" name="timer" value="${timer}">
				<input id="solve_codes" type="hidden" name="solve_codes" value="${solve_codes}">
				<div class="flexible-text" id="quiz-num">Quiz ${quizNumber} / ${quizSize}</div>
				<img id="quiz-poster">
				<div class="flexible-text text-center" id="quiz-question">해당 작품의 ${characterName}을(를) 연기한 배우를 선택하세요.(${score}점)</div>
				<div class="flexible-text text-center" id="quiz-timer">남은시간 : <span id="view-timer"></span>초</div>
				<div id="quiz-answers">
					<button class="answer"><img class="answer-image"></button>
					<button class="answer"><img class="answer-image"></button>
					<button class="answer"><img class="answer-image"></button>
					<button class="answer"><img class="answer-image"></button>
				</div>			
			</form>	
		</section>
		<c:import url="/noticelist" />
	</main>
	<c:import url="/footer" />
</body>
</html>