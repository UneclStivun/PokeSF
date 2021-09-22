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
							<button type="button" class="btn btn-link id=${counter.index}" onclick="switchPokemon(${counter.index})">Switch to</button>
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
		var userAction; // Nutzer-Aktion als json
		var agentAction; // Aktion des Agenten als Json
		var block = false; // blockieren der Attacken, falls Pokemon K.O.
	
	 	// Methode für das Angreifen von Pokemon
	 	// Nur für den menschlichen Spieler
		function attack(position) {
			
			// Falls block aktiviert, kann Nutzer nicht angreifen
	 		if(!block) {
	 			userAction = "{action:attack, position:" + position + "}";
				agentAction = "action:choose,";
				sendToAgent();
	 		} else {
	 			alert("Switch to an undefeated pokemon!");
	 			document.getElementsByClassName("explanation_box")[0].append("Switch to an undefeated pokemon!\n");
	 		}
		}
		
		// Methode für das Austauschen von Pokemon
		// Nur für den menschlichen Spieler
		function switchPokemon(position) {
			
			// Falls block aktiviert wurde, muss Pokemon auf Nutzerseite ausgewechselt werden
			// Ansonsten führe Tausch normal durch
			// Prüfe ob auzuwechselndes Pokemon gültig ist
			if(checkPokemonForSwitch(position)) {
				if(block) {
					$.ajax({
						async : false,
						cache : false,
						url : "ServletPokemonFight",
						type : "POST",
						data : {
							userAction : "{action:forceSwitch, position:" + position + "}",
							agentAction : "{action:wait}"
						},							success : function(data) {
							$("#page").html(data);
							block = false;
							checkDefeated();
						},
						error : function(data, status) {
							alert("Something went wrong :(\nData: " + data + "\nStatus: " + status);
						}
					});
				}  else {
					userAction = "{action:switch, position:" + position + "}";
					agentAction = "action:choose,";
					sendToAgent();
				}
			} else {
				alert("Cannot switch to defeated Pokemon!");
				document.getElementsByClassName("explanation_box")[0].append("Cannot switch to defeated Pokemon!\n");
			}
		}
		
		// Methode für das Auswechseln eines besiegten Pokemons des Agenten
		function forceSwitchPokemon() {
			
			// Unterscheide zwischen Nutzer und Agent
			agentAction = "action:forceSwitch,";
			userAction = "{action:wait}";
			
			setTimeout(function(){sendToAgent();},500);
			
		}
		
		// Prüfe ob zu wechselndes Pokemon nicht K.O. ist
		function checkPokemonForSwitch(position) {
			var check = false;
			
			$.ajax({
				async : false,
				cache : false,
				url : "ServletPokemonFight",
				dataType: "json",
				type : "POST",
				data : {
					check: position
				},
				success : function(data) {
					check = data.check;
				},
				error : function(data, status) {
					alert("Something went wrong :(\nData: " + data + "\nStatus: " + status);
				}
			});
			return check;
		}
		
		// Methode zum Senden von Rundeninformationen an das Agentensystem
		function sendToAgent() {
			
			var teamJson = "{";
			
			teamJson += agentAction;
			
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
				async : false,
				cache : false,
				url : "ServletPokemonFight",
				type : "POST",
				data : {
					agentAction: message.data,
					userAction: userAction
				},
				success : function(data) {
					$("#page").html(data);
					block = false;
					checkDefeated();
				},
				error : function(data, status) {
					alert("Something went wrong :(\n\nStatus: " + status);
				}
			});
    	});
		
		function checkDefeated() {
			if(${sessionScope.isUserTeamDefeated}) {
				block = true;
				alert("Team ${sessionScope.userTeam.getTeamname()} has been defeated");
			} else if(${sessionScope.userTeam.getPokemon().get(0).getHitpoints() == 0}) {
				block = true;
			}
			
			if(${sessionScope.isEnemyTeamDefeated}) {
				alert("Team ${sessionScope.enemyTeam.getTeamname()} has been defeated");
			} else if(${sessionScope.enemyTeam.getPokemon().get(0).getHitpoints() == 0}) {
				forceSwitchPokemon();
			}
		}
		
	</script>
</body>
</html>