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
			<option value="">-</option>
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
		<select name="pokemon_attack_class_1" onchange="showEffect1()">
			<option value="physical">Physical</option>
			<option value="special">Special</option>
			<option value="status" id="status1">Status</option>
		</select>
		<label id="effect1_label" style="margin-left: 2.5em; visibility: hidden">Effect 1:</label>
		<select id="effect1_selection" style="visibility: hidden" name="effect1">
			<option disabled>Primary Ailments</option>
			<option value="brn">Burn</option>			
			<option value="par">Paralysis</option>					
			<option value="psn">Poison</option>
			<option value="psn2">Bad Poison</option>
			<option value="slp">Sleep</option>
			<option disabled>Secondary Ailments</option>
			<option value="conf">Confusion</option>
			<option value="leech">Leech Seed</option>
			<option disabled>Others</option>
			<option value="heal">Healing</option>			
			<option value="ref">Reflect</option>
			<option value="ls">Light Screen</option>
			<option disabled>Buffs</option>	
			<option value="ab">Attack Buff</option>
			<option value="db">Defense Buff</option>
			<option value="sab">Specialattack Buff</option>
			<option value="sdb">Specialdefense Buff</option>
			<option value="sb">Speed Buff</option>
			<option disabled>Debuffs</option>
			<option value="ad">Attack Debuff</option>
			<option value="dd">Defense Debuff</option>
			<option value="sad">Specialattack Debuff</option>
			<option value="sdd">Specialdefense Debuff</option>
			<option value="ss">Speed Debuff</option>
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
		<select name="pokemon_attack_class_2" onchange="showEffect2()">
			<option value="physical">Physical</option>
			<option value="special">Special</option>
			<option value="status" id="status2">Status</option>
		</select>
		<label id="effect2_label" style="margin-left: 2.5em; visibility: hidden">Effect 2:</label>
		<select id="effect2_selection" style="visibility: hidden" name="effect2">
			<option disabled>Primary Ailments</option>
			<option value="brn">Burn</option>			
			<option value="par">Paralysis</option>					
			<option value="psn">Poison</option>
			<option value="psn2">Bad Poison</option>
			<option value="slp">Sleep</option>
			<option disabled>Secondary Ailments</option>
			<option value="conf">Confusion</option>
			<option value="leech">Leech Seed</option>
			<option disabled>Others</option>
			<option value="heal">Healing</option>			
			<option value="ref">Reflect</option>
			<option value="ls">Light Screen</option>
			<option disabled>Buffs</option>	
			<option value="ab">Attack Buff</option>
			<option value="db">Defense Buff</option>
			<option value="sab">Specialattack Buff</option>
			<option value="sdb">Specialdefense Buff</option>
			<option value="sb">Speed Buff</option>
			<option disabled>Debuffs</option>
			<option value="ad">Attack Debuff</option>
			<option value="dd">Defense Debuff</option>
			<option value="sad">Specialattack Debuff</option>
			<option value="sdd">Specialdefense Debuff</option>
			<option value="ss">Speed Debuff</option>
		</select>
		<br>
		
		<label>Pokemon Attack Type 3</label>
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
		<select name="pokemon_attack_class_3" onchange="showEffect3()">
			<option value="physical">Physical</option>
			<option value="special">Special</option>
			<option value="status" id="status3">Status</option>
		</select>
		<label id="effect3_label" style="margin-left: 2.5em; visibility: hidden">Effect 3:</label>
		<select id="effect3_selection"  style="visibility: hidden" name="effect3">
			<option disabled>Primary Ailments</option>
			<option value="brn">Burn</option>			
			<option value="par">Paralysis</option>					
			<option value="psn">Poison</option>
			<option value="psn2">Bad Poison</option>
			<option value="slp">Sleep</option>
			<option disabled>Secondary Ailments</option>
			<option value="conf">Confusion</option>
			<option value="leech">Leech Seed</option>
			<option disabled>Others</option>
			<option value="heal">Healing</option>			
			<option value="ref">Reflect</option>
			<option value="ls">Light Screen</option>
			<option disabled>Buffs</option>	
			<option value="ab">Attack Buff</option>
			<option value="db">Defense Buff</option>
			<option value="sab">Specialattack Buff</option>
			<option value="sdb">Specialdefense Buff</option>
			<option value="sb">Speed Buff</option>
			<option disabled>Debuffs</option>
			<option value="ad">Attack Debuff</option>
			<option value="dd">Defense Debuff</option>
			<option value="sad">Specialattack Debuff</option>
			<option value="sdd">Specialdefense Debuff</option>
			<option value="ss">Speed Debuff</option>
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
		<select name="pokemon_attack_class_4"onchange="showEffect4()">
			<option value="physical">Physical</option>
			<option value="special">Special</option>
			<option value="status" id="status4">Status</option>
		</select>
		<label id="effect4_label" style="margin-left: 2.5em; visibility: hidden">Effect 4:</label>
		<select id="effect4_selection" style="visibility: hidden" name="effect4">
			<option disabled>Primary Ailments</option>
			<option value="brn">Burn</option>			
			<option value="par">Paralysis</option>					
			<option value="psn">Poison</option>
			<option value="psn2">Bad Poison</option>
			<option value="slp">Sleep</option>
			<option disabled>Secondary Ailments</option>
			<option value="conf">Confusion</option>
			<option value="leech">Leech Seed</option>
			<option disabled>Others</option>
			<option value="heal">Healing</option>			
			<option value="ref">Reflect</option>
			<option value="ls">Light Screen</option>
			<option disabled>Buffs</option>	
			<option value="ab">Attack Buff</option>
			<option value="db">Defense Buff</option>
			<option value="sab">Specialattack Buff</option>
			<option value="sdb">Specialdefense Buff</option>
			<option value="sb">Speed Buff</option>
			<option disabled>Debuffs</option>
			<option value="ad">Attack Debuff</option>
			<option value="dd">Defense Debuff</option>
			<option value="sad">Specialattack Debuff</option>
			<option value="sdd">Specialdefense Debuff</option>
			<option value="ss">Speed Debuff</option>
		</select>
		
		<br><br>
		<button class="btn btn-primary" type="submit">Add this pokemon</button>		
	</form>
	
	<br>
	<p class="font-weight-italic" style="color:red;">${message }</p>

	<script> 
		// Zeige Effekt-Optionen nur, wenn die Attacke als Status-Attacke gewählt wurde
		function showEffect1() {
			if (document.getElementById("status1").selected) {
				document.getElementById("effect1_label").style.visibility = "visible";
				document.getElementById("effect1_selection").style.visibility = "visible";
			} else {
				document.getElementById("effect1_label").style.visibility = "hidden";
				document.getElementById("effect1_selection").style.visibility = "hidden";
			}
		}
		function showEffect2() {
			if (document.getElementById("status2").selected) {
				document.getElementById("effect2_label").style.visibility = "visible";
				document.getElementById("effect2_selection").style.visibility = "visible";
			} else {
				document.getElementById("effect2_label").style.visibility = "hidden";
				document.getElementById("effect2_selection").style.visibility = "hidden";
			}
		}
		function showEffect3() {
			if (document.getElementById("status3").selected) {
				document.getElementById("effect3_label").style.visibility = "visible";
				document.getElementById("effect3_selection").style.visibility = "visible";
			} else {
				document.getElementById("effect3_label").style.visibility = "hidden";
				document.getElementById("effect3_selection").style.visibility = "hidden";
			}
		}
		function showEffect4() {
			if (document.getElementById("status4").selected) {
				document.getElementById("effect4_label").style.visibility = "visible";
				document.getElementById("effect4_selection").style.visibility = "visible";
			} else {
				document.getElementById("effect4_label").style.visibility = "hidden";
				document.getElementById("effect4_selection").style.visibility = "hidden";
			}
		}
	</script>

	<br><br>
	<a href="index.jsp">Back to main page</a>
</body>	
</html>