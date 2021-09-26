<%@ include file = "header/checkAdmin.jsp" %>
<%-- 
  - Author(s): Tobias Brakel
  - Description: pokemonEdit.jsp
  --%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">	
	<link rel="stylesheet" href="css/areaAdmin.css" type="text/css">
	
	<title>Edit Pokemon Database</title>
</head>
<body>
	<div class="container">
			<div class="row">
			
			<!-- Buttons -->
			<div class="col-md-4">
				<button type="button" class="btn btn-info" onclick="window.location.href='ServletPokemonEdit?button=all'">Show all pokemon</button>
			</div>
			<div class="col-md-4">
				<button type="button" class="btn btn-info" onclick="window.location.href='ServletPokemonEdit?button=valid'">Show validated pokemon</button>
			</div>
			<div class="col-md-4">
				<button type="button" class="btn btn-info" onclick="window.location.href='ServletPokemonEdit?button=invalid'">Show not validated pokemon</button>		
			</div>
			
		</div>
	
		<br>
		
		<div class="row">
			<c:if test="${sessionScope.pokemonList != null && !sessionScope.pokemonList.isEmpty()}">
			<table class="table" border=1>
					<!-- Table Header -->
					<tr>
						<c:forEach items="${columnNames}" var="name">
							<th>${name}</th>
						</c:forEach>
					</tr>
					<!-- Table Content -->
					<c:forEach items="${sessionScope.pokemonList}" var="result">
						<tr>
							<td><button type="button" class="btn btn-link" onclick="showAttacks('${result.getDatabaseID()}')">${result.getName()}</button></td>
							<td>${result.getType1()}</td>
							<td>${result.getType2()}</td>
							<td>${result.getHitpoints()}</td>
							<td>${result.getAttack()}</td>
							<td>${result.getDefense()}</td>
							<td>${result.getSpAttack()}</td>
							<td>${result.getSpDefense()}</td>
							<td>${result.getInitiative()}</td>
							<td><form action="ServletPokemonEdit" method="post"><input type="hidden" value="${result.getDatabaseID()}" name="validate">
								<button type="submit" class="btn btn-success">Validate</button></form></td>
							<td><form action="ServletPokemonEdit" method="post"><input type="hidden" value="${result.getDatabaseID()}" name="invalidate">
								<button type="submit" class="btn btn-warning">Invalidate</button></form></td>
							<td><form action="ServletPokemonEdit" method="post"><input type="hidden" value="${result.getDatabaseID()}" name="delete">
								<button type="submit" class="btn btn-danger" onclick="return deletePokemon('${result.getName()}','${result.getDatabaseID()}')">X</button></form></td>
						</tr>
						<tr id="${result.getDatabaseID()}" style="display: none">
							<td>${result.attackListToString()}</td>
						</tr>
					</c:forEach>
				</table>
			</c:if>
		</div>
	
	</div>
	
	<c:if test="${sessionScope.pokemonList == null || sessionScope.pokemonList.isEmpty()}">
		<p>No entries available.</p>
	</c:if>
	
	<br><br>
	<button type="button" class="btn btn-info" style="bottom: 0%; position: fixed;" onclick="window.location.href='index.jsp'">Back to main menu</button>
	
	<script>
		/* Confirmation to delete pokemon */
		function deletePokemon(pokemon_name, pokemon_id) {
			var confirmation = confirm("You are about to delete:\n" + pokemon_name + " (" + pokemon_id + ")");
			if(confirmation){
				return true;
			} else {
				return false;
			}
		}
		
		/* Show or hide pokemon attacks */
		function showAttacks(pokemon_id){
			if(document.getElementById(pokemon_id).style.display === "none") {
				document.getElementById(pokemon_id).style.display = "block";
			} else {
				document.getElementById(pokemon_id).style.display = "none";
			}
		}
	</script>
</body>
</html>