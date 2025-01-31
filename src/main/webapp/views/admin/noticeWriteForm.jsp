<%@page import="admin.model.Admin"%>
<%@page import="notice.model.NoticeDao"%>
<%@page import="notice.model.NoticeResponseDto"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<c:choose>
    <c:when test="${not empty param.code}">
        <c:set var="code" value="${param.code}" />
        <c:set var="notice" value="${NoticeDao.getInstance().readNoticeByCode(code)}" />
        <c:set var="isEditMode" value="true" />
    </c:when>
    <c:otherwise>
        <c:set var="isEditMode" value="false" />
    </c:otherwise>
</c:choose>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="/resources/style/test.css">
<script type="module" src="/resources/script/validation-notice-delete.js"></script>
<title>공지사항 작성 화면</title>
    <link rel="shortcut icon" href="https://w7.pngwing.com/pngs/79/989/png-transparent-logo-vimeo-computer-icons-social-media-social-media-text-photography-orange-thumbnail.png">
    <meta property="og:title" content="quizland">
    <meta property="og:description" content="퀴즈랜드에서 퀴즈를 풀어요">
    <meta property="og:image" content="https://cdn-icons-png.freepik.com/256/3407/3407038.png?semt=ais_hybrid">
</head>
<body>
<c:if test="${not empty errorMessage}">
     <script>
         alert("${errorMessage}");
     </script>
</c:if>

    <main id="write-notice">
        <h2>공지사항 작성</h2>
        <form action="${isEditMode ? '/notice/update' : '/notice/write'}" method="post">
            <c:if test="${isEditMode}">
                <input type="hidden" id="notice-code" name="code" value="${notice.code}" />
                <input type="hidden" name="regDate" value="${notice.regDate}" />
            </c:if>
            <input type="hidden" name="adminCode" value="${sessionScope.admin.code}" />

            <label for="title">제목</label> 
            <input type="text" id="title" name="title" value="${notice.title}" required 
                <c:if test="${empty sessionScope.admin}">readonly</c:if>><br> 

            <label for="content">내용</label>
            <textarea id="content" name="content" required 
                <c:if test="${empty sessionScope.admin}">readonly</c:if>>${notice.content}</textarea><br> 

            <label for="start-date">게시일</label> 
            <input type="date" id="start-date" name="startDate" value="${notice.formattedResDate}" 
                <c:if test="${empty sessionScope.admin}">disabled</c:if>><br>

            <label for="end-date">만료일</label> 
            <input type="date" id="end-date" name="endDate" value="${notice.formattedCloseDate}" 
                <c:if test="${empty sessionScope.admin}">disabled</c:if>><br>

            <div class="btn-container">
                <c:if test="${not empty sessionScope.admin}">
                    <button type="submit" class="btn-submit">
                        <c:choose>
                            <c:when test="${isEditMode}">수정</c:when>
                            <c:otherwise>작성</c:otherwise>
                        </c:choose>
                    </button>
                    <c:if test="${isEditMode}">
                        <button type="button" class="btn-notice-delete">삭제</button>
                    </c:if>
                    <c:if test="${!isEditMode}">
                        <button type="button" class="btn-cancel" onclick="location.href='/list'">취소</button>
                    </c:if>
                </c:if>
                <c:if test="${empty sessionScope.admin}">
                    <button type="button" class="btn-cancel" onclick="location.href='/'">홈으로</button>
                </c:if>
            </div>
        </form>
    </main>
</body>
</html>
