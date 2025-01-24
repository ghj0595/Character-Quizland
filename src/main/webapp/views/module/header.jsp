<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

	<!-- favicon image -->
	<link rel="shortcut icon" href="파비콘이미지.png">

	<meta property="og:title" content="링크타이틀">
	<meta property="og:description" content="링크에대한설명">
	<meta property="og:image" content="이미지주소">

	<!-- ... -->

</head>
<body>
	<header>
		<div class="logo">
			<h1>
				<a href="/">Character Quizland</a>
			</h1>
		</div>
		<c:if test="${empty admin && empty log }">
			<div class="btn">
				<input type="button" value="로그인" onclick="location.href='/login'">
				<input type="button" value="관리자 로그인" onclick="location.href='/loginAdmin'">
				<input type="button" value="회원가입" onclick="location.href='/join'">
			</div>
		</c:if>
		
		<c:if test="${not empty log }">
			<div class="btn">
				<input type="button" value="회원정보" onclick="location.href='/service/users?command=view'">
				<input type="button" id="btn-logout" value="로그아웃" onclick="location.href='/service/users?command=logout'">
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