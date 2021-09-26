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
	<div class="container">
	
		<!-- Wenn Sessionparameter user_email nicht gesetzt, zeige Buttons zum Anmelden an. Ansonsten Logout Button. -->
		<c:choose>
			<c:when test="${sessionScope.user_email == null}">
				<div class="col-md-6">	
					<button type="button" class="btn btn-info" onclick="window.open('userRegistration.jsp')">Register</button>
					<button type="button" class="btn btn-info" onclick="window.location.href='userLogin.jsp'">Login</button>
				</div>
			</c:when>
			<c:otherwise>
				<div class="col-md-6">	
					<button type="button" class="btn btn-info" onclick="window.location.href='ServletUserSession?action=logout'">Logout</button> 
				</div>
			</c:otherwise>
		</c:choose>
		
		<div class="col-md-1"></div>
		<div class="col-md-1"></div>
		<div class="col-md-4">
			<button type="button" class="btn btn-primary" onclick="window.location.href='manual.jsp'">Manual</button>
		</div>
		
		<br><br><br>
		
		<!-- Wenn Rolle des Nutzers eine Adminrolle ist, wird der Delete-Button angezeigt -->
		<c:if test="${sessionScope.user_role == 'admin' || sessionScope.user_role == 'origin' }">
			<table style="width: 100%;">
				<tr>
					<td><span class="button icon"></span></td>
					<td style="width: 95%;">
						<button class="btn btn-danger btn-block" onclick="window.location.href='userManagement.jsp'">User Management</button>
					</td>
				</tr>
			</table>
			<br><br>
			
			<table style="width: 100%;">
				<tr>
					<td><span class="button icon"></span></td>
					<td style="width: 95%;">
						<button class="btn btn-danger btn-block" onclick="window.location.href='pokemonEdit.jsp'">Edit Pokemon Database</button>
					</td>
				</tr>
			</table>
			<br><br>
		</c:if>
		
		<!-- Button Tabelle -->
		<table style="width: 100%;">
			<tr>
				<td><span class="button icon"></span></td>
				<td style="width: 95%;">
					<button class="btn btn-danger btn-block" onclick="window.location.href='pokemonAdd.jsp'">Add Pokemon</button>
				</td>
			</tr>
		</table>
		<br><br>
		
		<table style="width: 100%;">
			<tr>
				<td><span class="button icon"></span></td>
				<td style="width: 95%;">
					<button class="btn btn-danger btn-block" onclick="window.location.href='pokemonTeamCreator.jsp'">Create Pokemon Teams</button>
				</td>
			</tr>
		</table>
		<br><br>
		
		<table style="width: 100%;">
			<tr>
				<td><span class="button icon"></span></td>
				<td style="width: 95%;">
					<button class="btn btn-danger btn-block" onclick="window.location.href='pokemonTeamCreator.jsp'">Create Pokemon Teams</button>
				</td>
			</tr>
		</table>
		<br><br>
		
		<table style="width: 100%;">
			<tr>
				<td><span class="button icon"></span></td>
				<td style="width: 95%;">
					<button class="btn btn-danger btn-block" onclick="window.location.href='pokemonSimilarityFinder.jsp'">Find similar Pokemon</button>
				</td>
			</tr>
		</table>
		<br><br>
		
		<table style="width: 100%;">
			<tr>
				<td><span class="button icon"></span></td>
				<td style="width: 95%;">
					<button class="btn btn-danger btn-block" onclick="window.location.href='pokemonPrepareTeam.jsp'">Prepare Pokemon Fight</button>
				</td>
			</tr>
		</table>
			
	</div>
	
	<div class="footer">
		<br>Neue Technologien für Semantic Web und Wissensmanagement
		<br>Tobias Brakel und Steven Oberle
	</div>
</body>
</html>