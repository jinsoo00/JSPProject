<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>결과 출력 화면</title>
</head>
<body>
	<h3>결과 화면</h3>
	<hr>
	<%=request.getAttribute("msg") %>
</body>
</html>