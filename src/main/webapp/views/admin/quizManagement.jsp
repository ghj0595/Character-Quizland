<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="/resources/style/admin.css">
<title>퀴즈 관리 화면</title>
</head>
<c:import url="/header" />
<body>
<div class="current-users">
    현재 접속자 수:<span id="current-users">0</span>명
</div>
<main id="quiz-management">
    <h2>퀴즈 관리</h2>
    <table class="quiz-table">
        <thead>
            <tr>
                <th>퀴즈 번호</th>
                <th>타입</th>
                <th>작품 API</th>
                <th>배우 API</th>
                <th>도전 횟수</th>
                <th>평균 점수</th>
                <th>정답률</th>
                <th>삭제</th>
            </tr>
        </thead>
        <tbody>
            <tr>
                <td>1112</td>
                <td>movie</td>
                <td>512</td>
                <td>481</td>
                <td>12</td>
                <td>7.5</td>
                <td>85%</td>
                <td>
                    <button class="btn-delete">삭제</button>
                </td>
            </tr>
        </tbody>
    </table>
</main>
<c:import url="/footer" />
</body>
</html>
