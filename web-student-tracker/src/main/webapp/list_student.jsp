<%@page import="java.util.List, io.github.kavishikamk.web.jdbc.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<link type="text/css" rel="stylesheet" href="css/style.css" />
</head>
<body>
	<div id="wrapper">
		<div id="header">
			<h2>Student Table</h2>
		</div>
	</div>
	
	<div id="container">
		<div id="content">
			<table>
				<tr>
					<th>First Name</th>
					<th>Last Name</th>
					<th>Email</th>
				</tr>
				<c:forEach var="theStudent" items="${STUDENT_LIST}">
					<tr>
						<td>${theStudent.firstName}</td>
						<td>${theStudent.lastName}</td>
						<td>${theStudent.email}</td>
					</tr>
				</c:forEach>
			</table>
		</div>
	</div>
</body>
</html>