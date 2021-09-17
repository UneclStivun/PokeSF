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
	<div class="display">
		Enemy HP Ailment
	</div>
	
	<div class="display">
		<c:choose>
			<c:when test="${sessionScope.poketeam != null}">
					${sessionScope.poketeam.get(0).getName()}  HP: ${sessionScope.poketeam.get(0).getHitpoints()}
					${sessionScope.poketeam.get(0).getAil1()}<br>
			</c:when>
			<c:otherwise>
				<%response.sendRedirect("ServletAgentLauncher");%>
			</c:otherwise>
		</c:choose>
	</div> <br>
	
	<div class="user_options" class="container">
		<div class="row">
		
			<div class="col-md-6">
				<p><button type="button" class="btn btn-light">Attack1</button></p>
				<p><button type="button" class="btn btn-light">Attack2</button></p>
				<p><button type="button" class="btn btn-light">Attack3</button></p>
				<p><button type="button" class="btn btn-light">Attack4</button></p>
			</div>
			
			<div class="col-md-6">
				<p style="color:white;">
					<c:forEach items="${sessionScope.poketeam}" var="pokemon" varStatus="counter">
						<c:if test="${counter.index > 0 }">
							<button type="button" class="btn btn-link" onclick="switchPokemon(${counter.index})">Switch to</button>
							${pokemon.getName()}
							<br>
						</c:if>
					</c:forEach>
				</p>
			</div>
			<button type="button" onclick="damage()">DamageStep</button>
			<button type="button" class="btn btn-danger" onclick="window.location.href='ServletAgentLauncher'">End Fight</button>
		</div>
	</div>
	<br>
	
	<div class="explanation_box">
		Explanations
		<button type="button" onclick="websocketSend()">Activate Websocket</button>
	</div>


	<br><br>
	<a href="index.jsp">Back to main page</a>
	
	<!-- Functions -->
	<script>
	
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
    	});
		
		// Methode die den erhaltenen Schaden verwaltet
		function damage() {
			$.ajax({
				async : true,
				cache : false,
				url : "ServletPokemonFight",
				type : "POST",
				data : {
					action: "damage",
				},
				success : function(data) {
					updateDisplay(data);
					$("#page").html(data);
				},
				error : function(data, status) {
					alert("Something went wrong :(\n\nData: " + data.responseText + "\nstatus: " + status);
				}
			});
		}
		
		// Methode für das Austauschen von Pokemon
		function switchPokemon(position) {
			$.ajax({
				async : true,
				cache : false,
				url : "ServletPokemonFight",
				type : "POST",
				data : {
					action: "switch",
					position: position
				},
				success : function(data) {
					$("#page").html(data);
				},
				error : function(data, status) {
					alert("Something went wrong :(\n\nData: " + data.responseText + "\nstatus: " + status);
				}
			});
		}
		
		function updateDisplay(data) {
			/*
			var name = data.pokeName;
			var hp = data.pokeHP;
			var ail = data.pokeAil;
			var defeated = data.defeated;
			
			// Aktualisiere Daten des Pokemons
			$("#pokeValue").html(name + "  HP: " + hp + " ");
			
			// Falls ein Status vorhanden, füge diesen hinten an
			if(ail != null) {
				$("#pokeValue").append(ail);
			}
			*/
			
			if(${sessionScope.isDefeated}) {
				alert("Team NAME has been defeated");
			}
			/*
			else if(true) {
				alert("You should switch your pokemon...no really!");
			}
			*/
		}
	</script>
</body>
</html>