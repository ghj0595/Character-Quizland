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
<body>
	<c:import url="/header" />
	<main class="main-container">
		<c:import url="/rank" />
		<section id="content">
			<div id="loading">Loading....</div>
			<form id="game-form" method="POST" style="display:none">
				<input id="quiz_number" type="hidden" name="quiz_number" value="1">
				<input id="quiz_size" type="hidden" name="quiz_size" value="10">
				<input id="quiz_code" type="hidden" name="quiz_code" value="${quizCode}">
				<input id="score" type="hidden" name="score" value="10">
				<input id="timer" type="hidden" name="timer" value="20">
				<input id="solve_codes" type="hidden" name="solve_codes" value="${empty solve_codes?[]:solve_codes}">
				<div class="flexible-title" id="quiz-num">Quiz ${quizNumber} / ${quizSize}</div>
				<img id="quiz-poster">
				<div class="flexible-text text-center" id="quiz-question">해당 작품의 ${characterName}을(를) 연기한 배우를 선택하세요.(${score}점)</div>
				<div class="flexible-text text-center" id="quiz-timer">남은시간 : <span id="view-timer"></span>초</div>
				<div id="quiz-answers">
					<div class="answer"><input type="image" class="answer-image" value="1"></div>
					<div class="answer"><input type="image" class="answer-image" value="2"></div>
					<div class="answer"><input type="image" class="answer-image" value="3"></div>
					<div class="answer"><input type="image" class="answer-image" value="4"></div>
				</div>			
			</form>	
		</section>
		<c:import url="/noticelist" />
	</main>
	<c:import url="/footer" />
</body>
</html>