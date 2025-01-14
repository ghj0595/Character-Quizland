<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="/resources/style/admin.css">
<title>사용자 관리 화면</title>
</head>
<body>
<c:import url="/header" />
<div class="current-users">
	현재 접속자 수:<span id="current-users">0</span>명
</div>
<main id="user-management">
    <h2>사용자 관리</h2>
    <table class ="user-table">
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
            <tr>
                <td>testId</td>
                <td>testName</td>
                <td>95</td>
                <td>88</td>
                <td>23</td>
                <td>
                    <button class="btn-ban">정지</button>
                </td>
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
