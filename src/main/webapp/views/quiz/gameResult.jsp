<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="/resources/style/globals.css">
<link rel="stylesheet" href="/resources/style/game.css">
<script src="/resources/script/game-result.js"></script>
<title>Game Result</title>
</head>
<body>
	<c:import url="/header" />
	<main class="main-container">
		<c:import url="/rank" />
		<section id="content">
			<input id="result" type="hidden" value="${result}">
			<input id="quiz_size" type="hidden" name="quiz_size" value="${empty quizSize?10:quizSize}">
			<div class="flexible-title" id="quiz-num"></div>
			<div class="text-center" id="quiz-result_title">정답화면</div>
			<div class="flexible-text text-center" id="quiz-score"></div>
			<div class="result" id="quiz-content">
				<img class="result-image" id="content-poster"">
				<div id="content-info">
					<div class="content-text"><a id="content-title"></a></div>
					<div class="content-text" id="content-overview"></div>
				</div> 
			</div>
			<div class="result" id="quiz-person">
				<img class="result-image" id="person-profile">
				<div class="content-text" id="person-name"><a id="person-name"></a></div>
			</div>		
			<div class="flexible-text text-center" id="total_score"></div>
			<button id="next" onclick="location.href='/game'">다음 문제</button>
		</section>
		<c:import url="/noticelist" />
	</main>
	<c:import url="/footer" />
</body>
</html>