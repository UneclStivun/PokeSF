<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%-- <%@ include file = "header/checkAdmin.jsp" %> --%>

<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">	
	<title>Edit Pokemon Database</title>
</head>
<body>
	<button type="button" class="btn btn-info" onclick="window.location.href='ServletPokemonEdit?button=all'">Show all pokemon</button>
	<button type="button" class="btn btn-info" onclick="window.location.href='ServletPokemonEdit?button=valid'">Show validated pokemon</button>
	<button type="button" class="btn btn-info" onclick="window.location.href='ServletPokemonEdit?button=invalid'">Show not validated pokemon</button>		
	<br>
	
	<c:if test="${sessionScope.pokemonList != null && !sessionScope.pokemonList.isEmpty()}">
	<% int number = 0; %>
	<table border=1>
			<!-- Table Header -->
			<tr>
				<c:forEach items="${columnNames}" var="name">
					<th>${name}</th>
				</c:forEach>
			</tr>
			<!-- Table Content -->
			<c:forEach items="${sessionScope.pokemonList}" var="result">
				<tr>
					<td>${result.getName()}</td>
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
				<%number++; %>
			</c:forEach>
		</table>
	</c:if>
	
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
	</script>
	
</body>
</html>