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
							<th>정답률</th>
							<th>삭제</th>
						</tr>
					</thead>
					<tbody>
						<% 
							QuizDao quizDao = QuizDao.getInstance(); 
							List<QuizResponseDto> quizList = quizDao.findQuizAll(1); 
							
							for (QuizResponseDto quiz : quizList) { 
								 String typeString = quiz.getType() == 0 ? "MOVIE" : "TV";
						%> 
						<tr> 
							<td><%= quiz.getCode() %></td> 
							<td><%= typeString %></td> 
							<td><%= quiz.getContentId() %></td> 
							<td><%= quiz.getPeopleId() %></td>
							<td>13</td>
							<td>8</td>
							<td>80%</td>
							<td> 
								<button class="btn-delete" data-quiz-code="<%= quiz.getCode() %>">삭제</button> 
							</td> 
						</tr> 
							<% 
								} 
							%>
					</tbody>
				</table>
			</div>
		</div>
		<c:import url="/noticelist" />
	</main>
	<c:import url="/footer" />
</body>
</html>
