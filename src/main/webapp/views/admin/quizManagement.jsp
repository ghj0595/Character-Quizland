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
<link href="https://fonts.googleapis.com/css2?family=Gowun+Dodum&family=Poppins:wght@400;600&display=swap" rel="stylesheet">
<link rel="stylesheet" href="/resources/style/test.css">
<script type="module" src="/resources/script/validation-quiz-delete.js"></script>
<title>퀴즈 관리 화면</title>
</head>
<c:import url="/header" />
<body>
	<main class="main-content">
		<div class="side-content" id="rank-container">
			<c:import url="/rank" />
		</div>
		<div class="center-content">
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
			 <div class="pagination">
          <c:if test="${currentPage > 1}">
            <a href="/QuizListAction?page=${currentPage - 1}" class="btn-prev">이전</a>
          </c:if>
          <c:if test="${currentPage < totalPages}">
            <a href="/QuizListAction?page=${currentPage + 1}" class="btn-next">다음</a>
          </c:if>
        </div>
		</div>
		</div>
		<div class="side-content" id="notice-container">
			<c:import url="/noticelist" />
		</div>
	</main>
	<c:import url="/footer" />
</body>
</html>
