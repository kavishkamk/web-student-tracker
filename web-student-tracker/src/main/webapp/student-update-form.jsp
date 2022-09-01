<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<link type="text/css" rel="stylesheet" href="css/style.css" />
	<link type="text/css" rel="stylesheet" href="css/add-student-style.css" />
</head>
<body>
	<div id="wrapper">
		<div id="header">
			<h2>Student Table</h2>
		</div>
		
		<div id="container">
			<h3>Add Student</h3>
			
			<form action="StudentControllerServlet" method="get">
				<input type="hidden" name="command" value="UPDATE" />
				<input type="hidden" name="studentId" value="${ THE_STUDENT.id }" />
				
				<table>
					<tbody>
						<tr>
							<td><label>First Name:</label></td>
							<td><input type="text" name="first_name" value="${ THE_STUDENT.firstName }" /></td>
						</tr>
						<tr>
							<td><label>Last Name:</label></td>
							<td><input type="text" name="last_name" value="${ THE_STUDENT.lastName }" /></td>
						</tr>
						<tr>
							<td><label>Email</label></td>
							<td><input type="email" name="email" value="${ THE_STUDENT.email }" /></td>
						</tr>
						<tr>
							<td><label></label></td>
							<td><input type="submit" name="save" value="Save" class="save"/></td>
						</tr>
					</tbody>
				</table>
			</form>
			
			<div style="clear: both;"></div>
			<p>
				<a href="StudentControllerServlet">Back to List</a>
			</p>
		</div>
	</div>
</body>
</html>