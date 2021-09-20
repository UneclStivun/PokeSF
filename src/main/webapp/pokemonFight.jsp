<%@ include file = "header/checkUser.jsp" %>
    
<!DOCTYPE html>
<html>
<head>
	<!-- Ajax -->
	<script src="https://ajax.aspnetcdn.com/ajax/jQuery/jquery-3.3.1.min.js"></script>
	
	<!-- Websocket -->
	<script src="js/websocket.js"></script>
	
	<meta charset="ISO-8859-1">
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet">	
	<link rel="stylesheet" href="css/pokemonFight.css" type="text/css">
	
	<title>Pokemon Fight</title>
</head>
<body id="page">
	
	<c:choose>
		<c:when test="${sessionScope.userTeam != null && sessionScope.enemyTeam != null}">
			<div class="display">
				${sessionScope.enemyTeam.getPokemon().get(0).getName()}  HP: ${sessionScope.enemyTeam.getPokemon().get(0).getHitpoints()}
				${sessionScope.enemyTeam.getPokemon().get(0).getAil1()} <br>
			</div>
			<div class="display">
				${sessionScope.userTeam.getPokemon().get(0).getName()}  HP: ${sessionScope.userTeam.getPokemon().get(0).getHitpoints()}
				${sessionScope.userTeam.getPokemon().get(0).getAil1()} <br>
			</div> <br>
		</c:when>
		<c:otherwise>
			<%
				System.out.println("Pokemon Team nicht vorhanden");
				response.sendRedirect("ServletAgentLauncher"); 
			%>
		</c:otherwise>
	</c:choose>
	
	<div class="user_options" class="container">
		<div class="row">
			
			<!-- Attack buttons -->
			<div class="col-md-6">
				<c:forEach items="${sessionScope.userTeam.getPokemon().get(0).getAttacks()}" var="attack" varStatus="counter">
					<p><button type="button" class="btn btn-light ${attack.getAttacktype()}" onclick="attack('${counter.index}')">Attack ${counter.index}</button></p>
				</c:forEach>
			</div>
			
			<!-- Switch button -->
			<div class="col-md-6">
				<p style="color:white;">
					<c:forEach items="${sessionScope.userTeam.getPokemon()}" var="pokemon" varStatus="counter">
						<c:if test="${counter.index > 0 }">
							<button type="button" class="btn btn-link" onclick="switchPokemon(${counter.index})">Switch to</button>
							${pokemon.getName()}
							<br>
						</c:if>
					</c:forEach>
				</p>
			</div>
			
			<button type="button" class="btn btn-danger" onclick="window.location.href='ServletAgentLauncher'">End Fight</button>
		</div>
	</div>
	<br>
	
	<!-- Explanation box -->
	<div class="explanation_box" style="overflow:scroll; height:150px;">
		${sessionScope.explanation}
	</div>

	<br><br>
	<a href="index.jsp">Back to main page</a>
	
	<!-- Functions -->
	<script>
	
		var userAction;
	
		//Function pokemon data to a json string
	  	function convertPokemonToJson() {
	  		/*
			- Agent erhält Infos über sein Team (Anzahl Pokemon + Typen Vorteile)
			- Agent sieht Infos über aktuelles Pokemon(Leben, Status, Angriffe, Typen)
			- Agent sieht Infos über Gegner-Pokemon
			- Agent lernt Angriffe seines Gegners.
			*/
	  	}
		
	 	// Methode für das Angreifen von Pokemon
		function attack(position) {
			userAction = "{action:attack, position:" + position + "}";
			sendToAgent();
		}
		
		// Methode für das Austauschen von Pokemon
		function switchPokemon(position) {
			userAction = "{action:switch, position:" + position + "}";
			sendToAgent();
		}
		
		// Methode zum Senden von Rundeninformationen an das Agentensystem
		function sendToAgent() {
			var teamJson = "{";
			
			/* userTeam */
			<c:forEach items="${sessionScope.userTeam.getPokemon()}" var="pokemon" varStatus="counter">
				teamJson += "\nuserPokemon" + ${counter.index} + ":" + "${pokemon.pokemonToJson()}";
				teamJson += ",";
			</c:forEach>
			
			/* enemyTeam */
			<c:forEach items="${sessionScope.enemyTeam.getPokemon()}" var="pokemon" varStatus="counter">
				teamJson += "\nenemyPokemon" + ${counter.index} + ":" + "${pokemon.pokemonToJson()}";
				teamJson += ",";
			</c:forEach>
			
			teamJson = teamJson.substring(0, teamJson.length - 1);
			teamJson += "}";
			
			webSocket.send(teamJson);
		}
		
		// Erhält Planung/Aktion des Agenten
		// Versendet Informationen an das Servlet zum Verarbeiten der Rundenaktionen
		webSocket.addEventListener('message', function(message) {
			
			$.ajax({
				async : true,
				cache : false,
				url : "ServletPokemonFight",
				type : "POST",
				data : {
					agentAction: message.data,
					userAction: userAction
				},
				success : function(data) {
					$("#page").html(data);
					checkDefeated();
				},
				error : function(data, status) {
					alert("Something went wrong :(\n\nStatus: " + status);
				}
			});
    	});
		
		function checkDefeated() {
			if(${sessionScope.isUserTeamDefeated}) {
				alert("Team ${sessionScope.userTeam.getTeamname()} has been defeated");
			} else if(${sessionScope.userTeam.getPokemon().get(0).getHitpoints() == 0}) {
				forceSwitchPokemon();
			}
			
			if(${sessionScope.isEnemyTeamDefeated}) {
				alert("Team ${sessionScope.enemyTeam.getTeamname()} has been defeated");
			} else if(${sessionScope.enemyTeam.getPokemon().get(0).getHitpoints() == 0}) {
				forceSwitchPokemon();
			}
		}
		
		function forceSwitchPokemon() {
			alert("Wechsel das Pokemon!");
		}
	</script>
</body>
</html>