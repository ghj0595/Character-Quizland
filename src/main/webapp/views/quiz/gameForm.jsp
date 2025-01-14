<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="/resources/style/globals.css">
<link rel="stylesheet" href="/resources/style/game.css">
<title>Game</title>
</head>
<body>
	<c:import url="/header" />
	<main>
		<c:import url="/rank" />
		<section id="content">
			<form method="POST" action="/quiz/{quizNo}">
				<input type="hidden" name="quiz_number" value="${quizNumber}">
				<input type="hidden" name="quiz_size" value="${quizSize}">
				<input type="hidden" name="quiz_code" value="quiz_code">
				<input type="hidden" name="answer_number" value="answer_number">
				<input type="hidden" name="score" value="${score}">
				<input type="hidden" name="timer" value="${timer}">
				<input type="hidden" name="solve_codes" value="solve_codes">
				<div id="quiz-num">Quiz ${quizNumber} / ${quizSize}</div>
				<img id="quiz-poster" alt="오징어 게임" src="https://image.tmdb.org/t/p/w342/caq0z9C2vvKdDhGe1EX6nerswV5.jpg">
				<div id="quiz-question">해당 작품의 ${characterName}을(를) 연기한 배우를 선택하세요.(${score}점)</div>
				<div id="quiz-timer">남은시간 : ${timer}초</div>
				<div id="quiz-answers">
					<button id="answer1"><img class="answer" id="answer-image1" alt="이정재" src="https://image.tmdb.org/t/p/w185/3h5Cfm0X8ohWn7psZkqdNWqXAHH.jpg"></button>
					<button id="answer2"><img class="answer" id="answer-image2" alt="이병헌" src="https://image.tmdb.org/t/p/w185/pY4pwYO8qwtzvuzpzRczDACDiVA.jpg"></button>
					<button id="answer3"><img class="answer" id="answer-image3" alt="임시완" src="https://image.tmdb.org/t/p/w185/tEZuIaMESdBw4LfNq3vshGR4VlP.jpg"></button>
					<button id="answer4"><img class="answer" id="answer-image4" alt="위하준" src="https://image.tmdb.org/t/p/w185/9V9H2mzk9XLMVPr0HtZHPCbM0Q2.jpg"></button>
				</div>			
			</form>	
		</section>
		<c:import url="/notice" />
	</main>
	<c:import url="/footer" />
</body>
</html>