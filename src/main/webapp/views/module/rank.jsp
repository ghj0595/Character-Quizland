<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<body>
	<section id="rank">
		<h1>RANK</h1>
		<ul class="rank-list">
			<c:forEach var="user" items="${rankList}" varStatus="status">
				<li>${status.index + 1}등: ${user.name} ${user.bestScore}점</li>
			</c:forEach>
		</ul>
	</section>
</body>
</html>
