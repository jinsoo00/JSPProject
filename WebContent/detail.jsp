<%@page import="com.model.database.Member"%>
<%@page import="java.util.ArrayList"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
	<title>상세 정보 화면</title>
    <style>    
        #visual .content-container{	
            height:inherit;
            display:flex; 
            align-items: center;
        }
    </style>
</head>
<body>
	<%
		ArrayList<Member> mb = (ArrayList<Member>)session.getAttribute("detail");
		int index = Integer.parseInt(request.getParameter("num"));
		session.setAttribute("pwchk", mb.get(index));
	%>
	 
	  <div id="body">
		<div class="content-container clearfix">
		<main class="main">
			<h2 class="main title">DBP 학생 정보</h2>
			
			<div class="notice margin-top">			
				
				<table>
					<thead>
						<tr>
							<th>번호</th>
							<th>ID</th>
							<th>&nbsp;이름</th>
							<th>&nbsp;전화번호</th>
							<th>&nbsp;이메일</th>
							<th>&nbsp;학과</th>
							<th>&nbsp;성별</th>
							<th>&nbsp;출생</th>
							<th>&nbsp;소개</th>
						</tr>
					</thead>
					<tbody>
						<c:set var="mb" value="<%=mb %>"/>
						<c:set var="i" value="<%=index %>"/>
						<%= "<tr>" %>
						<%=	"<td>"%>${i }<%="</td>" %>
						<%=	"<td>&nbsp;&nbsp;"%>${mb.get(i).id}<%="</td>" %>
						<%=	"<td>&nbsp;&nbsp;"%> ${mb.get(i).name}<%="</td>" %>		 		
						<%=	"<td>&nbsp;&nbsp;"%> ${mb.get(i).tel}<%="</td>" %>		 
						<%=	"<td>&nbsp;&nbsp;"%> ${mb.get(i).email}<%="</td>" %>		 
						<%=	"<td>&nbsp;&nbsp;"%> ${mb.get(i).dept}<%="</td>" %>		 
						<%=	"<td>&nbsp;&nbsp;"%> ${mb.get(i).gender}<%="</td>" %>		 
						<%=	"<td>&nbsp;&nbsp;"%> ${mb.get(i).birth}<%="</td>" %>		 
						<%=	"<td>&nbsp;&nbsp;"%> ${mb.get(i).introduction}<%="</td>" %>										
						<%= "</tr>" %>
					</tbody>
				</table>
			</div>
			
			<div class="search-form margin-top first align-right">
				<form action="/data" method="post">
					<input type="submit" name="btn" value="목록" />
					<input type="submit" name="btn" value="수정" />
				</form>
				
			</div>
		</main>
		
		</div>
	</div>
</body>
</html>