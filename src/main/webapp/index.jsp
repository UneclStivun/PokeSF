<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet" href="css/index.css" type="text/css">
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
	
	<title>Pokemon Strategy Finder</title>
</head>
<body>

	<!-- Wenn Sessionparameter user_email nicht gesetzt, zeige Buttons zum Anmelden an. Ansonsten Logout Button. -->
	<c:choose>
		<c:when test="${sessionScope.user_email == null}">
			<button type="button" class="btn btn-info" onclick="window.open('userRegistration.jsp')">Register</button>
			<button type="button" class="btn btn-info" onclick="window.open('userLogin.jsp')">Login</button> 
		</c:when>
		<c:otherwise>
			<button type="button" class="btn btn-info" onclick="window.location.href='ServletUserSession?action=logout'">Logout</button> 
		</c:otherwise>
	</c:choose>
	<br><br>
	
	<!-- Wenn Rolle des Nutzers eine Adminrolle ist, wird der Delete-Button angezeigt -->
	<c:if test="${sessionScope.user_role == 'admin' || sessionScope.user_role == 'origin' }">
		<button class="btn btn-danger btn-block" onclick="window.location.href='pokemonEdit.jsp'">Edit Pokemon Databse</button>
		<br><br>
	</c:if>
	
	<button class="btn btn-danger btn-block" onclick="window.location.href='pokemonAdd.jsp'">Add Pokemon</button>
	<br><br> 			
	<button class="btn btn-danger btn-block" onclick="window.location.href='pokemonSimilarityFinder.jsp'">Find similar Pokemon</button>
	<br><br>
	<button class="btn btn-danger btn-block" onclick="window.location.href='pokemonTeamCreator.jsp'">Create Pokemon Teams</button>	
	
	<div class="footer">
		<br>Neue Technologien für Semantic Web und Wissensmanagement
		<br>Tobias Brakel und Steven Oberle
	</div>
</body>
</html>