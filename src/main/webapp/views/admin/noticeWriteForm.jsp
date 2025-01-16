<%@page import="notice.model.NoticeDao"%>
<%@page import="notice.model.NoticeResponseDto"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
String codeParam = request.getParameter("code");
NoticeResponseDto notice = null;
Boolean isEditMode = false;
if (codeParam != null && !codeParam.isEmpty()) {
	int code = Integer.parseInt(codeParam);
	NoticeDao noticeDao = NoticeDao.getInstance();
	notice = noticeDao.readNoticeByCode(code);
	isEditMode = true;
}
%>
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
    <form action="<%= isEditMode ? "/notice/update" : "/notice/write" %>" method="post">

        <label for="title">제목</label>
        <input type="text" id="title" name="title" value="<%= (notice != null) ? notice.getTitle() : "" %>" required><br>

        <label for="content">내용</label>
        <textarea id="content" name="content" rows="10" cols="50" required><%= (notice != null) ? notice.getContent() : "" %></textarea><br>

        <label for="start-date">게시일</label>
        <input type="date" id="start-date" name="startDate" value="<%= (notice != null) ? notice.getFormattedResDate() : "" %>"><br>

        <label for="end-date">만료일</label>
        <input type="date" id="end-date" name="endDate" value="<%= (notice != null) ? notice.getFormattedCloseDate() : "" %>" ><br>
		
		<div class="btn-container"> 
			<button type="submit" class="btn-submit"><%= isEditMode ? "수정" : "작성" %></button> 
			<% if (isEditMode) { %> 
				<button type="button" class="btn-notice-delete" onclick="location.href='/notice/delete?code=<%= notice.getCode() %>'">삭제</button> 
			<% } else { %> 
				<button type="button" class="btn-cancel" onclick="location.href='/list'">취소</button> 
			<% } %> </div>
    </form>
</main>
<c:import url="/footer" />
</body>
</html>
