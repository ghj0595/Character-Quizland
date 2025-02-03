<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<body>
	<section id="notice">
		<h1>NOTICE LIST</h1>
		<ul class="notice-list">
			<c:forEach var="notice" items="${noticeList}">
				<li><a href="/notice?code=${notice.code}">※ ${notice.title}</a></li>
			</c:forEach>
		</ul>
	</section>
</body>
</html>