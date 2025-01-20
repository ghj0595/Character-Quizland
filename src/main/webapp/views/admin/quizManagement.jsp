<%@page import="org.json.JSONObject"%>
<%@page import="solve.model.SolveDao"%>
<%@page import="org.json.JSONArray"%>
<%@page import="quiz.model.QuizResponseDto"%>
<%@page import="java.util.List"%>
<%@page import="quiz.model.QuizDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="/resources/style/admin.css">
<script type="module" src="/resources/script/validation-quiz-delete.js"></script>
<title>퀴즈 관리 화면</title>
</head>
<c:import url="/header" />
<body>
	<div class="current-users">
		현재 접속자 수:<span id="current-users">0</span>명
	</div>
	<main class="main-content">
		<c:import url="/rank" />
		<div id="quiz-management">
			<h2>퀴즈 관리</h2>
			<div class="table-content">
				<table class="quiz-table">
					<thead>
						<tr>
							<th>퀴즈 번호</th>
							<th>타입</th>
							<th>작품 API</th>
							<th>배우 API</th>
							<th>도전 횟수</th>
							<th>평균 점수</th>
							<th>평균 소요 시간</th>
							<th>삭제</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="quiz" items="${quizList}"> 
							<c:set var="typeString" value="${quiz.type == 0 ? 'movie' : 'tv'}" /> 
							<tr> 
								<td>${quiz.code}</td> 
								<td>${typeString}</td> 
								<td><a href="https://themoviedb.org/${typeString}/${quiz.content_id}" target="_blank">${quiz.content_id}</a></td> 
								<td><a href="https://themoviedb.org/person/${quiz.people_id}" target="_blank">${quiz.people_id}</a></td> 
								<td>${quiz.total_count}</td> 
								<td>${quiz.average_score}</td> 
								<td>${quiz.average_timer}</td> 
								<td> <button class="btn-delete" data-quiz-code="${quiz.code}">삭제</button> </td> 
							</tr> 
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
		<c:import url="/noticelist" />
	</main>
	<c:import url="/footer" />
</body>
</html>
