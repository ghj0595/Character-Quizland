<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<body>
	<section id="notice">
		<h1>NOTICE</h1>
		<ul>
			<c:forEach var="notice" items="${noticeList}">
				<li>${notice.code}. Title: ${notice.title}</li>
			</c:forEach>
		</ul>

	</section>
</body>
</html>