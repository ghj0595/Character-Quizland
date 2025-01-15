<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="/resources/style/admin.css">
<title>공지사항 작성 화면</title>
</head>
<c:import url="/header" />
<body>
<div class="current-users">
    현재 접속자 수:<span id="current-users">0</span>명
</div>
<main id="write-notice">
    <h2>공지사항 작성</h2>
    <form action="notice.jsp" method="post">

        <label for="title">제목</label>
        <input type="text" id="title" name="title" required><br>

        <label for="content">내용</label>
        <textarea id="content" name="content" rows="10" cols="50" required></textarea><br>

        <label for="start-date">게시일</label>
        <input type="date" id="start-date" name="start-date" required><br>

        <label for="end-date">만료일</label>
        <input type="date" id="end-date" name="end-date" required><br>
		
		<div class="btn-container">
       		<button type="submit" class="btn-submit">작성</button>
        	<button type="button" class="btn-cancel" onclick="location.href='/list'">취소</button>		
		</div>
    </form>
</main>
<c:import url="/footer" />
</body>
</html>
