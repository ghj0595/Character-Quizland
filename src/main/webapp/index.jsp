<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="/resources/style/globals.css">
<title>메인화면</title>
</head>
	<c:import url="/header" />
<body>
	<main class="main-container">
		<c:import url="/rank" />
		<section id="content">
			<button id="btn-start" onclick="location.href='/game'">게임시작</button>
		</section>
		<c:import url="/noticelist" />
	</main>
	<c:import url="/footer" />
</body>
</html>