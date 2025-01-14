<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="/resources/style/globals.css">
<link rel="stylesheet" href="/resources/style/game.css">
<title>Game Result</title>
</head>
<body>
	<c:import url="/header" />
	<main>
		<c:import url="/rank" />
		<section id="content">
			<div id="quiz-num">Quiz ${quizNumber} / ${quizSize}</div>
			<div id="quiz-answer">정답화면 (점수 : ${score}점)</div>
			<div id="quiz-content">
				<img id="content-poster" alt="오징어 게임" src="https://image.tmdb.org/t/p/w342/caq0z9C2vvKdDhGe1EX6nerswV5.jpg">
				<div id="content-title"><a href="https://www.themoviedb.org/tv/93405/season/1">오징어게임</a></div>
				<div id="content-overview">자신만의 목적을 품고 다시 게임에 발을 디딘 기훈. 더욱 아찔한 위험과 가혹한 대가, 피할 수 없는 운명이 기다린다. 아이들 놀리 뒤에 숨겨진 격렬 한 사투에 목숨을 걸고, 처절하게 질주하라!</div>
			</div>
			<div id="quiz-person">
				<img id="person-profile" alt="이정재" src="https://image.tmdb.org/t/p/w300/3h5Cfm0X8ohWn7psZkqdNWqXAHH.jpg">
				<div id="person-name"><a href="https://www.themoviedb.org/person/73249">이정재</a></div>
			</div>		
			<div id="quiz-score">현재 점수 : ${totalScore}점</div>
		</section>
		<c:import url="/notice" />
	</main>
	<c:import url="/footer" />
</body>
</html>