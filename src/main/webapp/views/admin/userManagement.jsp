<%@page import="user.model.User"%>
<%@page import="java.util.List"%>
<%@page import="user.model.UserDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="jakarta.tags.core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link href="https://fonts.googleapis.com/css2?family=Gowun+Dodum&family=Poppins:wght@400;600&display=swap" rel="stylesheet">
<link rel="stylesheet" href="/resources/style/test.css">
<script type="module" src="/resources/script/validation-update-user-status.js"></script>
<title>사용자 관리 화면</title>
</head>
<body>
	<c:import url="/header" />
	<main class="main-content">
		<c:import url="/rank" />

		<div id="user-content">
			<h2>사용자 관리</h2>
			<div class="table-content">
				<table class="user-table">
					<thead>
						<tr>
							<th>아이디</th>
							<th>닉네임</th>
							<th>최고 점수</th>
							<th>평균 점수</th>
							<th>게임 횟수</th>
							<th>정지</th>
							<th>삭제</th>
						</tr>
					</thead>
					<c:set var="userList" value="${UserDao.getInstance().findUserAll()}" />
					<tbody>
						<c:forEach var="user" items="${userList}">
							<c:set var="averageScore" value="${UserDao.getInstance().calculateAverageScore(user.userCode)}" />
							<c:set var="gameCount" value="${UserDao.getInstance().calculateGameCount(user.userCode)}" />
							<tr>
								<td>${user.userCode}</td>
								<td>${user.name}</td>
								<td>${user.bestScore}</td>
								<td>${averageScore}</td>
								<td>${gameCount}</td>
								<td>
									<button class="btn-ban" data-user-code="${user.userCode}">정지</button>
								</td>
								<td>
									<button class="btn-delete" data-user-code="${user.userCode}">삭제</button>
								</td>
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
