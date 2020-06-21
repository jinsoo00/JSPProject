<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>정보 입력 화면</title>
</head>
<body>
	<%
		String act = (String)request.getAttribute("action");
		session.setAttribute("action",act);
	%>
    <h3>회원 정보</h3>
    <form action="/data" method="post">
    	<c:set var="act" value="<%=act %>"/>
    	<c:if test="${act != 'update' }">
    		 이름 :  <input type="text"  name="name" /> <br/>
		     ID : <input type="text" name="id" /><br/>
		</c:if>
		        비밀번호 : <input type="password" name="pwd" /> <br/>
		        전화번호 :  <input type="text"  name="tel" /> <br/>
		        이메일 :  <input type="text"  name="email" /> <br/>
		        학부 : 
	              <input type="checkbox" name="dept" value="Computer" /> 컴퓨터공학부 
	              <input type="checkbox" name="dept" value="Information" /> 정보통신학부  
	              <input type="checkbox" name="dept" value="Itcontents" /> IT콘텐츠학과  
	              <input type="checkbox" name="dept" value="Korean" /> 국어국문학과 <br/>
		        성별 :
	              <input type="radio"  name="gender"  value="male" />남자 
	              <input type="radio"  name="gender"  value="female" />여자<br/>
		        
		        태어난 계절:
	              <select name="birth">
	                 <option value="Spring"> 봄 
	                 <option value="Summer"> 여름 
	                 <option value="Fall"> 가을 
	                 <option value="Winter"> 겨울                 
	              </select>  <br/>
		        자기소개:<br/>
	              <textarea cols="30" rows="10" name="introduction"></textarea> <br/>
	        <input type="submit"  name="btn" value="전송" />
	        <input type="reset"  name="btn" value="지우기" />
        
    </form>
</body>
</html>