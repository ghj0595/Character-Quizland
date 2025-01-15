<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="/resources/style/globals.css">
<link rel="stylesheet" href="/resources/style/game.css">
<title>Total Result</title>
</head>
<body>
	<c:import url="/header" />
	<main>
		<c:import url="/rank" />
		<section id="content">
			<div class="text-center" id="quiz-result_title">게임결과</div>
			<div class="flexible-text text-center" id="total-score">총 점수 : ${score}점</div>
			<div class="flexible-text text-center" id="current-rank">현재 점수의 순위 : ${rank}위</div>
			<div class="flexible-text text-center" id="current-per">상위 ${per}%입니다.</div>
		</section>
		<c:import url="/noticelist" />
	</main>
	<c:import url="/footer" />
</body>
</html>