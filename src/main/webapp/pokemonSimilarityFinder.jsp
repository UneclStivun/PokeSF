<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
	<title>Pokemon Finder</title>
</head>
<body>
	<label>Select attributes to get a similar pokemon. The more values are set, the more accurate the similarity.</label>
	<br><br>
	<form method="post" action="">
		<input type="text" class="form-control" name="pokemon_name" placeholder="Pokemon Name">
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
		<br><br>
		<input type="number" min="1" max="255" class="form-control" placeholder="Pokemon Hit Points (min:1 max:255) ">
		<br>
		<input type="number" min="1" max="200" class="form-control" placeholder="Pokemon Attack Value (min:1 max:200)">
		<br>
		<input type="number" min="1" max="250" class="form-control" placeholder="Pokemon Defense Value (min:1 max:250)">
		<br>
		<input type="number" min="1" max="200" class="form-control" placeholder="Pokemon Special Attack Value (min:1 max:200)">
		<br>
		<input type="number" min="1" max="250" class="form-control" placeholder="Pokemon Special Defense Value (min:1 max:250)">
		<br>
		<input type="number" min="1" max="200" class="form-control" placeholder="Pokemon Speed Value (min:1 max:200)">
		<br><br>
		<button class="btn btn-primary" type="submit">Search similar pokemon</button>		
	</form>
</body>
</html>