<%@page import="com.model.database.Member"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>패스워드 확인 화면</title>
</head>
<body>
	<%
		Member mb = (Member)session.getAttribute("pwchk");
		session.setAttribute("update", mb);
	%>
	<h3>비밀번호 확인</h3>
	<hr>
	<form action="/data" method="post">
		사용자 ID : <%= mb.getId() %><br>
		비밀 번호 : <input type="password" name="pwd" value=""><br>
	           <input type="submit"  name="btn" value="확인">           
	</form>
</body>
</html>