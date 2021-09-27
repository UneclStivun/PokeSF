<%@ include file="header/checkUser.jsp"%>
<%-- 
  - Author(s): Tobias Brakel
  - Description: pokemonSimilarityFinder.jsp
  --%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	
	<!-- Style/Bootstrap -->
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
	<link rel="stylesheet" href="css/areaUser.css" type="text/css">
	
	<title>Pokemon Finder</title>
</head>
<body>

<div class="container">
	<div class="row">
	<br><br>
	<form id="formoid" method="post" action="ServletPokemonSimilarityFinder">
		<input type="text" class="form-control" placeholder="Pokemon Name" name="pokemon_name"> <br> <label>Pokemon Type 1:</label>
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
		</select> <label style="margin-left: 5em">Pokemon Type 2:</label> <select
			name="pokemon_type_2">
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
		</select> <br>
		<br> <input type="number" min="1" max="255" class="form-control"
			placeholder="Pokemon Hit Points (min:1 max:255)" name="pokemon_hp">
		<br> <input type="number" min="1" max="200" class="form-control"
			placeholder="Pokemon Attack Value (min:1 max:200)"
			name="pokemon_attack"> <br> <input type="number" min="1"
			max="250" class="form-control"
			placeholder="Pokemon Defense Value (min:1 max:250)"
			name="pokemon_defense"> <br> <input type="number"
			min="1" max="200" class="form-control"
			placeholder="Pokemon Special Attack Value (min:1 max:200)"
			name="pokemon_specialattack"> <br> <input type="number"
			min="1" max="250" class="form-control"
			placeholder="Pokemon Special Defense Value (min:1 max:250)"
			name="pokemon_specialdefense"> <br> <input type="number"
			min="1" max="200" class="form-control"
			placeholder="Pokemon Speed Value (min:1 max:200)"
			name="pokemon_speed"> 
			<br> 
		<input class="form-check-input" type="checkbox" value="use_att1" name="cb_att1" checked> 
		<label class="form-check-label"	for="flexCheckChecked" style="margin-left: 2.em"> </label> 
		<label>Pokemon	Attack Type 1</label> 
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
		</select> <label style="margin-left: 2.5em">Pokemon Attack Class 1</label> <select
			name="pokemon_attack_class_1" onchange="showEffect1()">
			<option value="physical">Physical</option>
			<option value="special">Special</option>
			<option value="status" id="status1">Status</option>
		</select> <label id="effect1_label"
			style="margin-left: 2.5em; visibility: hidden">Effect 1:</label> <select
			id="effect1_selection" style="visibility: hidden" name="effect1">
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
			<option value="ab">Attack Buff</option>
			<option value="db">Defense Buff</option>
			<option value="sab">Specialattack Buff</option>
			<option value="sdb">Specialdefense Buff</option>
			<option value="sb">Speed Boost</option>
			<option value="ad">Attack Debuff</option>
			<option value="dd">Defense Debuff</option>
			<option value="sad">Specialattack Debuff</option>
			<option value="sdd">Specialdefense Debuff</option>
			<option value="ss">Speed Debuff</option>
		</select> <br> <input class="form-check-input" type="checkbox"
			value="use_att2" name="cb_att2" checked> <label
			class="form-check-label" for="flexCheckChecked"
			style="margin-left: 2.em"> </label> <label>Pokemon Attack
			Type 2</label> <select name="pokemon_attack_type_2">
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
		</select> <label style="margin-left: 2.5em">Pokemon Attack Class 2</label> <select
			name="pokemon_attack_class_2" onchange="showEffect2()">
			<option value="physical">Physical</option>
			<option value="special">Special</option>
			<option value="status" id="status2">Status</option>
		</select> <label id="effect2_label"
			style="margin-left: 2.5em; visibility: hidden">Effect 2:</label> <select
			id="effect2_selection" style="visibility: hidden" name="effect2">
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
			<option value="ab">Attack Buff</option>
			<option value="db">Defense Buff</option>
			<option value="sab">Specialattack Buff</option>
			<option value="sdb">Specialdefense Buff</option>
			<option value="sb">Speed Boost</option>
			<option value="ad">Attack Debuff</option>
			<option value="dd">Defense Debuff</option>
			<option value="sad">Specialattack Debuff</option>
			<option value="sdd">Specialdefense Debuff</option>
			<option value="ss">Speed Debuff</option>
		</select> <br> <input class="form-check-input" type="checkbox"
			value="use_att3" name="cb_att3" checked> <label
			class="form-check-label" for="flexCheckChecked"
			style="margin-left: 2.em"> </label> <label>Pokemon Attack
			Type 3</label> <select name="pokemon_attack_type_3">
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
		</select> <label style="margin-left: 2.5em">Pokemon Attack Class 3</label> <select
			name="pokemon_attack_class_3" onchange="showEffect3()">
			<option value="physical">Physical</option>
			<option value="special">Special</option>
			<option value="status" id="status3">Status</option>
		</select> <label id="effect3_label"
			style="margin-left: 2.5em; visibility: hidden">Effect 3:</label> <select
			id="effect3_selection" style="visibility: hidden" name="effect3">
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
			<option value="ab">Attack Buff</option>
			<option value="db">Defense Buff</option>
			<option value="sab">Specialattack Buff</option>
			<option value="sdb">Specialdefense Buff</option>
			<option value="sb">Speed Boost</option>
			<option value="ad">Attack Debuff</option>
			<option value="dd">Defense Debuff</option>
			<option value="sad">Specialattack Debuff</option>
			<option value="sdd">Specialdefense Debuff</option>
			<option value="ss">Speed Debuff</option>
		</select> <br> <input class="form-check-input" type="checkbox"
			value="use_att4" name="cb_att4" checked> <label
			class="form-check-label" for="flexCheckChecked"
			style="margin-left: 2.em"> </label> <label>Pokemon Attack
			Type 4</label> <select name="pokemon_attack_type_4">
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
		</select> <label style="margin-left: 2.5em">Pokemon Attack Class 4</label> <select
			name="pokemon_attack_class_4" onchange="showEffect4()">
			<option value="physical">Physical</option>
			<option value="special">Special</option>
			<option value="status" id="status4">Status</option>
		</select> <label id="effect4_label"
			style="margin-left: 2.5em; visibility: hidden">Effect 4:</label> <select
			id="effect4_selection" style="visibility: hidden" name="effect4">
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
			<option value="ab">Attack Buff</option>
			<option value="db">Defense Buff</option>
			<option value="sab">Specialattack Buff</option>
			<option value="sdb">Specialdefense Buff</option>
			<option value="sb">Speed Boost</option>
			<option value="ad">Attack Debuff</option>
			<option value="dd">Defense Debuff</option>
			<option value="sad">Specialattack Debuff</option>
			<option value="sdd">Specialdefense Debuff</option>
			<option value="ss">Speed Debuff</option>
		</select> <br>
		<br>
		<button class="btn btn-primary" type="submit">Search for similar Pokemon</button>
		<a href="pokemonTeamCreator.jsp" class="btn btn-primary">Go to Teamcreator</a>
	</form>
	</div>
	<div class="row">
		<div class="col-12">
		<br>
		<p class="font-weight-italic" style="color: red;">${message }</p>
		<form action="ServletQuickList" method="post">
		<c:if test="${sessionScope.resultCases != null && !sessionScope.resultCases.isEmpty()}">
			<%int number = 0;%>
			<table border=1 style="width:100%">
				<!-- Table Header -->
				<tr>
					<c:forEach items="${columnNames}" var="name">
						<th>${name}</th>
					</c:forEach>
				</tr>
				<!-- Table Content -->
				<c:forEach items="${sessionScope.resultCases}" var="result">
					<tr>
						<td><button type="button" class="btn btn-link" onclick="showAttacks('${result.getPokemon().getDatabaseID()}')">${result.getPokemon().getName()}</button></td>
						<td>${result.getPokemon().getType1()}</td>
						<td>${result.getPokemon().getType2()}</td>
						<td>${result.getPokemon().getHitpoints()}</td>
						<td>${result.getPokemon().getAttack()}</td>
						<td>${result.getPokemon().getDefense()}</td>
						<td>${result.getPokemon().getSpAttack()}</td>
						<td>${result.getPokemon().getSpDefense()}</td>
						<td>${result.getPokemon().getInitiative()}</td>
						<td>${result.getSim()}</td>
						<td>
							<input class="form-check-input" type="checkbox" value="<%=number%>" name="add<%=number%>" checked> 
							<label class="form-check-label"	for="flexCheckChecked" style="margin-left: 2.em"> </label>
						</td>
					</tr>
					<c:forEach items="${result.getPokemon().getAttacks()}" var="attack">
					<tr name="${result.getPokemon().getDatabaseID()}" style="display: none">
						<td width=60>${attack.getAttacktype()}</td>
						<td width=60>${attack.getAttackclass()}</td>
						<td width=50>${attack.getEffect()}</td>
					</tr>
					</c:forEach>
					<%number++; %>
				</c:forEach>
			</table>
			<br>
			<button class="btn btn-success" type="submit">Add to quicklist</button>
		</c:if>
		</form>
		</div>
	</div>
</div>
	
	<button type="button" class="btn btn-info" style="bottom: 0%; position: fixed;" onclick="window.location.href='index.jsp'">Back to main menu</button>
		
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
		/* Show or hide pokemon attacks */
		function showAttacks(pokemon_name) {
			var e = [];
			var elems = document.getElementsByName(pokemon_name);
			for (var i = 0; i < elems.length; i++) {
				if (elems[i].style.display == "none") {
					elems[i].style.display = "block"
				} else {
					elems[i].style.display = "none"
				}
			}
		}
	</script>
	
</body>
</html>