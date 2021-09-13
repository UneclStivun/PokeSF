<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ include file = "header/checkUser.jsp" %>

<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
	
	<title>Pokemon Team Finder</title>
</head>
<body>

	<form method="post" action="ServletAgentLauncher">
		<input type="hidden" name="pokemonTeam" value=""/>
		<button type="submit" class="btn btn-danger">Start Fight!</button>
	</form>

	<br><br>
	<a href="index.jsp">Back to main page</a>
	
</body>
</html>