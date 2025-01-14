<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="/resources/style/admin.css">
<script type="module" src="/resources/script/validation-admin.js"></script>
<title>관리자 화면</title>
</head>
<c:import url="/header" />
<body>
<div class="current-users">
	현재 접속자 수:<span id="current-users">0</span>명
</div>
</body>
<section class="button-container">
	<button class="button" id="btn-user">사용자 관리</button>
	<button class="button" id="btn-quiz">퀴즈 관리</button>
	<button class="button" id="btn-notice">공지사항 관리</button>	
</section>
<c:import url="/footer" />
</html>