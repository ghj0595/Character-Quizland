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
<link rel="stylesheet" href="/resources/style/admin.css">
<script type="module" src="/resources/script/validation-update-user-status.js"></script>
<title>사용자 관리 화면</title>
</head>
<body>
	<c:import url="/DeleteUser"></c:import>
	<c:import url="/header" />
	<div class="current-users">
		현재 접속자 수:<span id="current-users">0</span>명
	</div>
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
					<tbody>
						<% 
							UserDao userDao = UserDao.getInstance(); 
							List<User> userList = userDao.findUserAll(); 
							
							for (User user : userList) { 
								double averageScore = userDao.calculateAverageScore(user.getUserCode()); 
								int gameCount = userDao.calculateGameCount(user.getUserCode()); 
						%> 
						<tr> 
							<td><%= user.getUserCode() %></td> 
							<td><%= user.getName() %></td> 
							<td><%= user.getBestScore() %></td> 
							<td><%= averageScore %></td> 
							<td><%= gameCount %></td> 
							<td> 
								<button class="btn-ban" data-user-code="<%= user.getUserCode() %>">정지</button> 
							</td> 
							<td> 
								<button class="btn-delete" data-user-code="<%= user.getUserCode() %>">삭제</button> 
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
