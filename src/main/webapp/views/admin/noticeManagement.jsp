<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="/resources/style/admin.css">
<script type="module" src="/resources/script/validation-admin.js"></script>
<title>공지사항 관리 화면</title>
</head>
<c:import url="/header" />
<body>
<div class="current-users">
    현재 접속자 수:<span id="current-users">0</span>명
</div>
<main id="notice-management">
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
                	<th>상태</th>
            	</tr>
        	</thead>
        	<tbody>
            	<tr>
                	<td>1212</td>
                	<td>관리자1</td>
                	<td>공지사항 제목 예시</td>
                	<td>2025-01-01</td>
                	<td>2025-01-18</td>
                	<td>게시중</td>
            	</tr>
        	</tbody>
    	</table>
    </div>
</main>
<c:import url="/footer" />
</body>
</html>
