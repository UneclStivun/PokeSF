<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ include file = "header/checkUser.jsp" %>

<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	
	<!--  Bootstrap -->
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
	 
	<!-- Ajax -->
	<script src="https://ajax.aspnetcdn.com/ajax/jQuery/jquery-3.3.1.min.js"></script>
	
	<title>Add Pokemon</title>
</head>
<body>
	<form id="formoid" method="post" action="ServletPokemonAdd">
		<input type="text" class="form-control" placeholder="Pokemon Name" name="pokemon_name">
		<br>
		<label>Pokemon Type 1:</label>
		<select name="pokemon_type_1">
			<option value="normal">Normal</option>
			<option value="fire">Fire</option>
			<option value="water">Water</option>
			<option value="electric">Electric</option>
			<option value="grass">Grass</option>
			<option value="ice">Ice</option>
			<option value="fighting">Fighting</option>
			<option value="poison">Poison</option>
			<option value="ground">Ground</option>
			<option value="flying">Flying</option>
			<option value="psychic">Psychic</option>
			<option value="bug">Bug</option>
			<option value="rock">Rock</option>
			<option value="ghost">Ghost</option>
			<option value="dragon">Dragon</option>		
		</select>
		<label style="margin-left: 5em">Pokemon Type 2:</label>
		<select name="pokemon_type_2">
			<option value="null">-</option>
			<option value="normal">Normal</option>
			<option value="fire">Fire</option>
			<option value="water">Water</option>
			<option value="electric">Electric</option>
			<option value="grass">Grass</option>
			<option value="ice">Ice</option>
			<option value="fighting">Fighting</option>
			<option value="poison">Poison</option>
			<option value="ground">Ground</option>
			<option value="flying">Flying</option>
			<option value="psychic">Psychic</option>
			<option value="bug">Bug</option>
			<option value="rock">Rock</option>
			<option value="ghost">Ghost</option>
			<option value="dragon">Dragon</option>		
		</select>
		<br><br>
		<input type="number" min="1" max="255" class="form-control" placeholder="Pokemon Hit Points (min:1 max:255)" name="pokemon_hp">
		<br>
		<input type="number" min="1" max="200" class="form-control" placeholder="Pokemon Attack Value (min:1 max:200)" name="pokemon_attack">
		<br>
		<input type="number" min="1" max="250" class="form-control" placeholder="Pokemon Defense Value (min:1 max:250)" name="pokemon_defense">
		<br>
		<input type="number" min="1" max="200" class="form-control" placeholder="Pokemon Special Attack Value (min:1 max:200)" name="pokemon_specialattack">
		<br>
		<input type="number" min="1" max="250" class="form-control" placeholder="Pokemon Special Defense Value (min:1 max:250)" name="pokemon_specialdefense">
		<br>
		<input type="number" min="1" max="200" class="form-control" placeholder="Pokemon Speed Value (min:1 max:200)" name="pokemon_speed">
		<br>
		<label>Pokemon Attack Type 1</label>
		<select name="pokemon_attack_type_1">
			<option value="normal">Normal</option>
			<option value="fire">Fire</option>
			<option value="water">Water</option>
			<option value="electric">Electric</option>
			<option value="grass">Grass</option>
			<option value="ice">Ice</option>
			<option value="fighting">Fighting</option>
			<option value="poison">Poison</option>
			<option value="ground">Ground</option>
			<option value="flying">Flying</option>
			<option value="psychic">Psychic</option>
			<option value="bug">Bug</option>
			<option value="rock">Rock</option>
			<option value="ghost">Ghost</option>
			<option value="dragon">Dragon</option>	
		</select>
		<label style="margin-left: 2.5em">Pokemon Attack Class 1</label>
		<select name="pokemon_attack_class_1">
			<option value="physical">Physical</option>
			<option value="special">Special</option>
			<option value="status">Status</option>
		</select>
		<br>
		<label>Pokemon Attack Type 2</label>
		<select name="pokemon_attack_type_2">
			<option value="normal">Normal</option>
			<option value="fire">Fire</option>
			<option value="water">Water</option>
			<option value="electric">Electric</option>
			<option value="grass">Grass</option>
			<option value="ice">Ice</option>
			<option value="fighting">Fighting</option>
			<option value="poison">Poison</option>
			<option value="ground">Ground</option>
			<option value="flying">Flying</option>
			<option value="psychic">Psychic</option>
			<option value="bug">Bug</option>
			<option value="rock">Rock</option>
			<option value="ghost">Ghost</option>
			<option value="dragon">Dragon</option>	
		</select>
		<label style="margin-left: 2.5em">Pokemon Attack Class 2</label>
		<select name="pokemon_attack_class_2">
			<option value="physical">Physical</option>
			<option value="special">Special</option>
			<option value="status">Status</option>
		</select>
		<br><label>Pokemon Attack Type 3</label>
		<select name="pokemon_attack_type_3">
			<option value="normal">Normal</option>
			<option value="fire">Fire</option>
			<option value="water">Water</option>
			<option value="electric">Electric</option>
			<option value="grass">Grass</option>
			<option value="ice">Ice</option>
			<option value="fighting">Fighting</option>
			<option value="poison">Poison</option>
			<option value="ground">Ground</option>
			<option value="flying">Flying</option>
			<option value="psychic">Psychic</option>
			<option value="bug">Bug</option>
			<option value="rock">Rock</option>
			<option value="ghost">Ghost</option>
			<option value="dragon">Dragon</option>	
		</select>
		<label style="margin-left: 2.5em">Pokemon Attack Class 3</label>
		<select name="pokemon_attack_class_3">
			<option value="physical">Physical</option>
			<option value="special">Special</option>
			<option value="status">Status</option>
		</select>
		<br>
		<label>Pokemon Attack Type 4</label>
		<select name="pokemon_attack_type_4">
			<option value="normal">Normal</option>
			<option value="fire">Fire</option>
			<option value="water">Water</option>
			<option value="electric">Electric</option>
			<option value="grass">Grass</option>
			<option value="ice">Ice</option>
			<option value="fighting">Fighting</option>
			<option value="poison">Poison</option>
			<option value="ground">Ground</option>
			<option value="flying">Flying</option>
			<option value="psychic">Psychic</option>
			<option value="bug">Bug</option>
			<option value="rock">Rock</option>
			<option value="ghost">Ghost</option>
			<option value="dragon">Dragon</option>	
		</select>
		<label style="margin-left: 2.5em">Pokemon Attack Class 4</label>
		<select name="pokemon_attack_class_4">
			<option value="physical">Physical</option>
			<option value="special">Special</option>
			<option value="status">Status</option>
		</select>
		<br><br>
		<button class="btn btn-primary" type="submit">Add this pokemon</button>		
	</form>
	
	<br>
	<p class="font-weight-italic" style="color:red;">${message }</p>
</body>
</html>