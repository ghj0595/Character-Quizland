<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
</head>
<body>
	<header>
		<div class="logo">
			<h1>
				<a href="/">Character Quizland</a>
			</h1>
		</div>
		<c:if test="${empty admin }">
			<div class="btn">
				<input type="button" value="로그인" onclick="location.href='/login'">
				<input type="button" value="회원가입" onclick="location.href='/join'">
				<input type="button" value="관리자 로그인" onclick="location.href='/loginAdmin'">
			</div>
		</c:if>

		<c:if test="${not empty admin }">
			<div class="btn">
				<input type="button" value="로그아웃" onclick="location.href='/service/admin?command=logout'">
			</div>
		</c:if>

	</header>

</body>
</html>