<%@page import="com.model.database.Member"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.DriverManager"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>리스트 화면</title>
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
	ArrayList<Member> mb = (ArrayList<Member>)request.getAttribute("mb");
	session.setAttribute("detail", mb);
	%>
	<div id="body">
		<div class="content-container clearfix">
		<main class="main">
			<h2 class="main title">DBP 학생 목록</h2>
			
			<div class="notice margin-top">				
				<table>
					<thead>
						<tr>
							<th>번호</th>
							<th>ID</th>
							<th>&nbsp;이름</th>
						</tr>
					</thead>
					<tbody>
					
					<c:set var="mb" value="<%=mb %>"/>
					<c:forEach var="i" begin="0" end="${mb.size()-1 }">
							<%= "<tr>" %>
							<%=	"<td>"%>${i }<%="</td>" %>
							<%=	"<td><a href="%><%="detail.jsp?num="%>${i}<%=">"%>${mb.get(i).id}<%="</a></td>" %>
							<%=	"<td>&nbsp;&nbsp;"%> ${mb.get(i).name}<%="</td>" %>		 									
							<%= "</tr>" %>
					</c:forEach>
					
					</tbody>
				</table>
			</div>
			
			<div class="search-form margin-top first align-right">
			<br>	
				<form action="/data" method="post">
					<fieldset>
						<legend class="hidden">학생 분류</legend>
						<label class="hidden">학과</label>
						<select name="f">
							<option  value="default">전체</option>
							<option  value="Computer">컴퓨터공학부</option>
							<option  value="Information">정보통신학부</option>
							<option  value="Itcontents">IT콘텐츠학과</option>
							<option  value="Korean">국어국문학과</option>							
						</select> 
						<input type="submit" name="btn" value="검색" />
					</fieldset>
				</form>
			</div>
		</main>
		
		</div>
	</div>

</body>
</html>