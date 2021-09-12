<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ include file = "header/checkUser.jsp" %>
    
<!DOCTYPE html>
<html>
<head>

	<!-- Ajax -->
	<script src="https://ajax.aspnetcdn.com/ajax/jQuery/jquery-3.3.1.min.js"></script>
	
	<meta charset="ISO-8859-1">
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">	
	<link rel="stylesheet" href="css/pokemonFight.css" type="text/css">
	
	<title>Pokemon Fight</title>
</head>
<body>
	<button type="button" class="btn btn-info" onclick="window.location.href='ServletAgentLauncher'">Launch Agents</button>

	<div class="display">
		Pokemon_Sprite1  HP  Anzahl_Pokemon<br>
		Pokemon_Sprite2  HP  Anzahl_Pokemon<br>
	</div>
	
	<br>
	
	<div class="user_options">
		<div>
			<button type="button" class="btn btn-light">Attack1</button>
			<button type="button" class="btn btn-light">Attack2</button><br>
			<button type="button" class="btn btn-light">Attack3</button>
			<button type="button" class="btn btn-light">Attack4</button>
		</div>
		<a href="">Switch pokemon</a> <br>
		<a href="">End Fight</a>
	</div>
	
	<div class="explanation_box">
	
	</div>


	<br><br>
	<a href="index.jsp">Back to main page</a>
	
	<script>
		/*
		- Agent erhält Infos über sein Team (Anzahl Pokemon + Typen Vorteile)
		- Agent sieht Infors über aktuelles Pokemon(Leben, Status, Angriffe, Typen)
		- Agent sieht Infos über Gegner-Pokemon
		- Agent lernt Angriffe seines Gegners.
		*/
	  	function toAgent() {
	  		$.ajax({
		 		async: true,
				url: "CSVWriterServlet",
				type: "POST",
				data: {
					currentTime: today,
					timer: displayTime,
					player1: convertPlayerToJson(player1),
					player2: convertPlayerToJson(player2),
					},
				success: function(data) {
				},
				error: function() {
					new BABYLON.Sound("Error", "sounds/computerError.wav", scene, null, { loop: false, autoplay: true });
					alert("Couldn't send CSV-Data (CSVWriterServlet)")
				}
			});
	  	}
	</script>
</body>
</html>