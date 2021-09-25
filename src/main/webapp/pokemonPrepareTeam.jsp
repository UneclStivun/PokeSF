<%@ include file="header/checkUser.jsp"%>
<%@taglib uri="/WEB-INF/prepareTeamTag.tld" prefix="ptt"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC"
	crossorigin="anonymous">
<title>Prepare Pokemon Teams for Battle!</title>
</head>
<body>
	<ptt:CaseLoader></ptt:CaseLoader>
	<div class="row">
		<div class="col-4">
		<form method="post" action="ServletAgentLauncher">
			<button type="submit" class="btn btn-danger">Start fight!</button>
		</form>
		<br>
		</div>
		<div class="col-6">
			<form action="ServletPrepareTeam" method="post">
				<p>All available Teams:</p>
				<div>
					<datalist id="suggestions">
						<%
						int number = 0;
						%>
						<c:forEach items="${sessionScope.allTeamList}" var="poke">
							<option value="<%=number%>">${poke.getTeamname()}</option>
							<%
							number++;
							%>
						</c:forEach>
					</datalist>
					<input autoComplete="on" list="suggestions" name="all" />
					<button type="submit" name="user" value="yes">Add to User</button>
					<button type="submit" name="enemy" value="yes">Add to
						Enemy</button>
				</div>
			</form>
		</div>
	</div>
	<div class="row">
		<div class="col-5">
		<br>
			<p>Your Current Team:</p>
			<c:if test="${sessionScope.userTeam != null}">
			<table class="table">
				<c:if test="${sessionScope.userTeam.getResistances().size() > 0}">
				<tr>
					<td>Resistances:</td>
					<c:forEach items="${sessionScope.userTeam.getResistances()}" var="res">
						<td>${res}</td>
					</c:forEach>
				</tr>
				</c:if>
				<c:if test="${sessionScope.userTeam.getWeaknesses().size() > 0}">
				<tr>
					<td>Weaknesses:</td>
					<c:forEach items="${sessionScope.userTeam.getWeaknesses()}" var="weak">
						<td>${weak}</td>
					</c:forEach>
				</tr>
				</c:if>
				<c:if test="${sessionScope.userTeam.getImmunities().size() > 0}">
				<tr>
					<td>Immunities:</td>
					<c:forEach items="${sessionScope.userTeam.getImmunities()}" var="immune">
						<td>${immune}</td>
					</c:forEach>
				</tr>
				</c:if>
			</table>
			<table class="table">
				<tr>
					<th>${sessionScope.userTeam.getTeamname()}</th>
					<th>Type</th>
					<th>Hp</th>
					<th>Att</th>
					<th>Def</th>
					<th>SpAtt</th>
					<th>SpDef</th>
					<th>Ini</th>
				</tr>
				<c:forEach items="${sessionScope.userTeam.getPokemon()}" var="poke">
					<tr>
						<td><button type="button" class="btn btn-link"
								onclick="showAttacks('u${poke.getDatabaseID()}')">${poke.getName()}</button></td>
						<td>${poke.getType1()}</td>
						<td>${poke.getType2()}</td>
						<td>${poke.getHitpoints()}</td>
						<td>${poke.getAttack()}</td>
						<td>${poke.getDefense()}</td>
						<td>${poke.getSpAttack()}</td>
						<td>${poke.getSpDefense()}</td>
						<td>${poke.getInitiative()}</td>
					</tr>
					<thead name="u${poke.getDatabaseID()}" style="display: none">
						<th>Attack type:</th>
						<th>Attack class:</th>
						<th>Effect:</th>
					</thead>
					<c:forEach items="${poke.getAttacks()}" var="attack">
						<tr name="u${poke.getDatabaseID()}" style="display: none">
							<td width=20>${attack.getAttacktype()}</td>
							<td width=20>${attack.getAttackclass()}</td>
							<td width=20>${attack.getEffect()}</td>
						</tr>
					</c:forEach>
				</c:forEach>
			</table>
			</c:if>
		</div>
		<div class="col-1">
			
		</div>
		<div class="col-5">
		<br>
			<p>Current Enemy Team:</p>
			<c:if test="${sessionScope.enemyTeam != null}">
			<table class="table">
				<c:if test="${sessionScope.enemyTeam.getResistances().size() > 0}">
				<tr>
					<td>Resistances:</td>
					<c:forEach items="${sessionScope.enemyTeam.getResistances()}" var="res">
						<td colspan="2">${res}</td>
					</c:forEach>
				</tr>
				</c:if>
				<c:if test="${sessionScope.enemyTeam.getWeaknesses().size() > 0}">
				<tr>
					<td>Weaknesses:</td>
					<c:forEach items="${sessionScope.enemyTeam.getWeaknesses()}" var="weak">
						<td colspan="2">${weak}</td>
					</c:forEach>
				</tr>
				</c:if>
				<c:if test="${sessionScope.enemyTeam.getImmunities().size() > 0}">
				<tr>
					<td>Immunities:</td>
					<c:forEach items="${sessionScope.enemyTeam.getImmunities()}" var="immune">
						<td colspan="2">${immune}</td>
					</c:forEach>
				</tr>
				</c:if>
			</table>
			<table class="table">
				<tr>
					<th>${sessionScope.enemyTeam.getTeamname()}</th>
					<th colspan="2">Type</th>
					<th>Hp</th>
					<th>Att</th>
					<th>Def</th>
					<th>SpAtt</th>
					<th>SpDef</th>
					<th>Ini</th>
				</tr>
				<c:forEach items="${sessionScope.enemyTeam.getPokemon()}" var="poke">
					<tr>
						<td><button type="button" class="btn btn-link"
								onclick="showAttacks('e${poke.getDatabaseID()}')">${poke.getName()}</button></td>
						<td>${poke.getType1()}</td>
						<td>${poke.getType2()}</td>
						<td>${poke.getHitpoints()}</td>
						<td>${poke.getAttack()}</td>
						<td>${poke.getDefense()}</td>
						<td>${poke.getSpAttack()}</td>
						<td>${poke.getSpDefense()}</td>
						<td>${poke.getInitiative()}</td>
					</tr>
					<tr name="e${poke.getDatabaseID()}" style="display: none">
						<th>Attack type:</th>
						<th>Attack class:</th>
						<th>Effect:</th>
					</tr>
					<c:forEach items="${poke.getAttacks()}" var="attack">
						<tr name="e${poke.getDatabaseID()}" style="display: none">
							<td width=20>${attack.getAttacktype()}</td>
							<td width=20>${attack.getAttackclass()}</td>
							<td width=20>${attack.getEffect()}</td>
						</tr>
					</c:forEach>
				</c:forEach>
			</table>
			</c:if>
		</div>
	</div>
	<div class="row">
		<div class="col-6">
			<br>
			<p>Get similar teams to selected team</p>
			<form action="ServletPokemonteamSimilarityFinder" method="post">
				<div>
					<datalist id="simSugg">
						<option value="${sessionScope.userTeam.getTeamid()}">${sessionScope.userTeam.getTeamname()}</option>
						<option value="${sessionScope.enemyTeam.getTeamid()}">${sessionScope.enemyTeam.getTeamname()}</option>
					</datalist>
					<input autoComplete="on" list="simSugg" name="sim" />
					<button type="submit" name="retrieve" value="yes">Retrieve
						similar Teams</button>
				</div>
			</form>
		</div>
		<div class="col-6">
			<br>
			<p>Get counter teams to selected team</p>
			<form action="ServletPokemonteamSimilarityFinder" method="post">
				<div>
					<datalist id="counterSugg">
						<option value="${sessionScope.userTeam.getTeamid()}">${sessionScope.userTeam.getTeamname()}</option>
						<option value="${sessionScope.enemyTeam.getTeamid()}">${sessionScope.enemyTeam.getTeamname()}</option>
					</datalist>
					<input autoComplete="on" list="counterSugg" name="count" />
					<button type="submit" name="counter" value="yes">Get
						counter team</button>
				</div>
			</form>
		</div>
	</div>
	<div class="row">
		<div class="col-5">
			<c:if
				test="${sessionScope.simTeams != null && !sessionScope.simTeams.isEmpty()}">
				<br>
				<p>similar Teams:</p>
				<table class="table">
					<c:forEach items="${sessionScope.simTeams}" var="sims">
						<tr>
							<th colspan="2">${sims.getCasename()}</th>
							<th colspan="2">${sims.getSim()}</th>
						</tr>
						<tr>
							<td>Resistances:</td>
							<c:forEach items="${sims.getResistances()}" var="res">
								<td>${res}</td>
							</c:forEach>
						</tr>
						<tr>
							<td>Weaknesses:</td>
							<c:forEach items="${sims.getWeaknesses()}" var="weak">
								<td>${weak}</td>
							</c:forEach>
						</tr>
						<tr>
							<td>Immunities:</td>
							<c:forEach items="${sims.getImmunities()}" var="imm">
								<td>${imm}</td>
							</c:forEach>
						</tr>
					</c:forEach>
				</table>
			</c:if>
		</div>
		<div class="col-1">
		
		</div>
		<div class="col-5">
			<c:if
				test="${sessionScope.counterTeamList != null && !sessionScope.counterTeamList.isEmpty()}">
				<br>
				<p>similar Teams:</p>
				<table class="table">
				<%int count = 0;%>
					<c:forEach items="${sessionScope.counterTeamList}" var="counter">
						<tr>
							<th colspan="2">${counter.getTeamname()}</th>
							<th>
							<form action="ServletPrepareTeam" method="post">
								<button value="<%=count%>" name="userC">Add to user</button>
							</form>
							</th>
							<th>
							<form action="ServletPrepareTeam" method="post">
								<button value="<%=count%>" name="enemyC">Add to enemy</button>
							</form>
							</th>
						</tr>
						<%count++;%>
						<c:forEach items="${counter.getPokemon()}" var="poke">
							<tr>
								<td><button type="button" class="btn btn-link"
										onclick="showAttacks('${poke.getDatabaseID() + counter.getTeamid()}')">${poke.getName()}</button></td>
								<td>${poke.getType1()}</td>
								<td>${poke.getType2()}</td>
								<td>${poke.getHitpoints()}</td>
								<td>${poke.getAttack()}</td>
								<td>${poke.getDefense()}</td>
								<td>${poke.getSpAttack()}</td>
								<td>${poke.getSpDefense()}</td>
								<td>${poke.getInitiative()}</td>
							</tr>
							<tr id="${poke.getDatabaseID() + counter.getTeamid()}"
								style="display: none">
								<c:forEach items="${poke.getAttacks()}" var="attack">
									<td>${attack.getAttacktype()}</td>
									<td>${attack.getAttackclass()}</td>
									<td>${attack.getDmg()}</td>
									<td>${attack.getEffect()}</td>
								</c:forEach>
							</tr>
						</c:forEach>
					</c:forEach>
				</table>
			</c:if>
		</div>
	</div>

	<script>
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