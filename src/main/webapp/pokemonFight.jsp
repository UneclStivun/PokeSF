<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ include file = "header/checkUser.jsp" %>
    
<!DOCTYPE html>
<html>
<head>

	<!-- Ajax -->
	<script src="https://ajax.aspnetcdn.com/ajax/jQuery/jquery-3.3.1.min.js"></script>
	
	<!-- Websocket -->
	<script src="js/websocket.js"></script>
	
	<meta charset="ISO-8859-1">
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">	
	<link rel="stylesheet" href="css/pokemonFight.css" type="text/css">
	
	<title>Pokemon Fight</title>
</head>
<body>
	<% int pokemon_life1 = 100; %>
	<% int pokemon_life2 = 100; %>
	
	<div class="display">
		Pokemon_Sprite1  HP: <%=pokemon_life1%>  Anzahl_Pokemon<br>
		Pokemon_Sprite2  HP: <%=pokemon_life2%>  Anzahl_Pokemon<br>
		<button type="button" onclick="websocketSend()">Activate Websocket</button>
	</div> 
	<br>
	
	<!-- 
	<div class="display">
		${sessionScope.pokemonList.get(0).getName()} <br>
		HP: ${sessionScope.pokemonList.get(0).getHitpoints()}
	</div>
	 -->
	
	
	
	<div class="user_options">
		<div>
			<button type="button" class="btn btn-light">Attack1</button>
			<button type="button" class="btn btn-light">Attack2</button><br>
			<button type="button" class="btn btn-light">Attack3</button>
			<button type="button" class="btn btn-light">Attack4</button>
		</div>
		<button type="button" class="btn btn-link" onclick="switchPokemon()">Switch pokemon</button> <br>
		<a href="ServletAgentLauncher">End Fight</a>
	</div> 
	<br>
	
	<div class="explanation_box">
	
	</div>


	<br><br>
	<a href="index.jsp">Back to main page</a>
	
	<script>
		function switchPokemon() {
			/*
			Liste alle eigenen Pokemon auf
			Wähle daraus
			Wechsel Pokemon
			Aktualisiere Status und HP Anzeige
			*/
		}
		
		//Function pokemon data to a json string
	  	function convertPokemonToJson() {
	  		/*
			- Agent erhält Infos über sein Team (Anzahl Pokemon + Typen Vorteile)
			- Agent sieht Infors über aktuelles Pokemon(Leben, Status, Angriffe, Typen)
			- Agent sieht Infos über Gegner-Pokemon
			- Agent lernt Angriffe seines Gegners.
			*/
	  		var pokemonString = "{'Hp':100,'Attack':'20'}";
	  		
	  		return pokemonString;
	  	}
		
		function websocketSend() {
			webSocket.send(convertPokemonToJson());
		}
		
		webSocket.addEventListener('message', function(message) {
			alert(message);
    	});
		
		/*
	  	function toAgent() {
	  		$.ajax({
		 		async: true,
				url: "ServletAgentListener",
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
					alert("Something went wrong :(");
				}
			});
	  	}
		*/
	</script>
</body>
</html>