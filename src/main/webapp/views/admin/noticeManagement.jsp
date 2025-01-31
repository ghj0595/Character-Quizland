<%@page import="notice.model.NoticeResponseDto"%>
<%@page import="java.util.List"%>
<%@page import="notice.model.NoticeDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link href="https://fonts.googleapis.com/css2?family=Gowun+Dodum&family=Poppins:wght@400;600&display=swap" rel="stylesheet">
<link rel="stylesheet" href="/resources/style/test.css">
<script type="module" src="/resources/script/validation-notice.js"></script>
<title>공지사항 관리 화면</title>
</head>
<c:import url="/header" />
<body>
	<main class="main-content">
		<div class="side-content" id="rank-container">
			<c:import url="/rank" />
		</div>
		<div class="center-content">

			<div id="table-content">
				<h2>공지사항 관리</h2>
				<div class="table-container">
					<button class="btn-write" id="btn-write">공지사항 작성</button>
					<table class="notice-table">
						<thead>
							<tr>
								<th>공지사항 코드</th>
								<th>관리자 ID</th>
								<th>제목</th>
								<th>게시일</th>
								<th>만료일</th>
								<th class="status">상태</th>
							</tr>
						</thead>
						<c:set var="noticeList"
							value="${NoticeDao.getInstance().readAllNotices()}" />
						<tbody>
							<c:forEach var="notice" items="${noticeList}">
								<tr>
									<td>${notice.code}</td>
									<td>${notice.adminCode}</td>
									<td><a href="/notice?code=${notice.code}">${notice.title}</a></td>
									<td>${notice.resDate}</td>
									<td>${notice.closeDate}</td>
									<td><c:choose>
											<c:when test="${notice.status == 0}">대기</c:when>
											<c:when test="${notice.status == 1}">게시중</c:when>
											<c:when test="${notice.status == 2}">만료</c:when>
											<c:otherwise>알 수 없음</c:otherwise>
										</c:choose></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
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
