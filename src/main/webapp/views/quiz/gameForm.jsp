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
<c:if test="${empty quizNumber}">
	<c:set var="quizNumber" value="1"/>
</c:if>
<c:if test="${empty quizSize}">
	<c:set var="quizSize" value="10"/>
</c:if>

<body>
	<c:import url="/header" />
	<main class="main-container">
		<c:import url="/rank" />
		<section id="content">
			<form method="POST" action="/quiz/{quizNo}">
				<input type="hidden" name="user_code" value="${log}">
				<input type="hidden" name="quiz_number" value="${quizNumber}">
				<input type="hidden" name="quiz_size" value="${quizSize}">
				<input type="hidden" name="quiz_code" value="quiz_code">
				<input type="hidden" name="score" value="${score}">
				<input type="hidden" name="timer" value="${timer}">
				<div class="flexible-text" id="quiz-num">Quiz ${quizNumber} / ${quizSize}</div>
				<img id="quiz-poster" alt="오징어 게임" src="https://image.tmdb.org/t/p/w342/caq0z9C2vvKdDhGe1EX6nerswV5.jpg">
				<div class="flexible-text text-center" id="quiz-question">해당 작품의 ${characterName}을(를) 연기한 배우를 선택하세요.(${score}점)</div>
				<div class="flexible-text text-center" id="quiz-timer">남은시간 : ${timer}초</div>
				<div id="quiz-answers">
					<button class="answer" id="answer1"><img class="answer-image" id="answer-image1" alt="이정재" src="https://image.tmdb.org/t/p/w185/3h5Cfm0X8ohWn7psZkqdNWqXAHH.jpg"></button>
					<button class="answer" id="answer2"><img class="answer-image" id="answer-image2" alt="이병헌" src="https://image.tmdb.org/t/p/w185/pY4pwYO8qwtzvuzpzRczDACDiVA.jpg"></button>
					<button class="answer" id="answer3"><img class="answer-image" id="answer-image3" alt="임시완" src="https://image.tmdb.org/t/p/w185/tEZuIaMESdBw4LfNq3vshGR4VlP.jpg"></button>
					<button class="answer" id="answer4"><img class="answer-image" id="answer-image4" alt="위하준" src="https://image.tmdb.org/t/p/w185/9V9H2mzk9XLMVPr0HtZHPCbM0Q2.jpg"></button>
				</div>			
			</form>	
		</section>
		<c:import url="/noticelist" />
	</main>
	<c:import url="/footer" />
</body>
</html>