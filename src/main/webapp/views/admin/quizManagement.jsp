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
						<% 
							SolveDao solveDao = SolveDao.getInstance(); 
							JSONArray quizList = solveDao.findQuizSolveAll(1); 
							
							for (int i = 0; i < quizList.length(); i++) { 
								JSONObject obj = quizList.getJSONObject(i);
								
								String typeString = obj.getInt("type") == 0 ? "movie" : "tv";
						%> 
						<tr> 
							<td><%= obj.getInt("code") %></td> 
							<td><%= typeString %></td> 
							<td><a href="https://themoviedb.org/<%=typeString%>/<%= obj.getInt("content_id")%>" target="_blank"><%= obj.getInt("content_id") %></a></td> 
							<td><a href="https://themoviedb.org/person/<%= obj.getInt("people_id")%>" target="_blank"><%= obj.getInt("people_id") %></a></td> 
							<td><%= obj.getInt("total_count") %></td>
							<td><%= obj.getDouble("average_score") %></td>
							<td><%= obj.getDouble("average_timer") %></td>
							<td> 
								<button class="btn-delete" data-quiz-code="<%= obj.getInt("code") %>">삭제</button> 
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
