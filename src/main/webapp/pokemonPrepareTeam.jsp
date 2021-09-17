<%@ include file = "header/checkUser.jsp" %>
<%@taglib uri="/WEB-INF/prepareTeamTag.tld" prefix="ptt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
<title>Prepare Pokemon Teams for Battle!</title>
</head>
<body>
	<form method="post" action="ServletAgentLauncher">
		<button type="submit" class="btn btn-danger">Start fight!</button>
<ptt:CaseLoader></ptt:CaseLoader>
<div class="row">
  <div class="col-4">
  <p> Your Current Team:</p>
  <br>
  <table>
  <tr>
	<th>"${sessionScope.userTeam.getTeamname()}"</th>
	<th colspan="2">Type</th>
	<th>Hp</th>
	<th>Att</th>
	<th>Def</th>
	<th>SpAtt</th>
	<th>SpDef</th>	
	<th>Ini</th>
  </tr>
  <c:forEach items="${sessionScope.userTeam.getPokemon()}" var="poke">
  <tr>
  	<td><button type="button" class="btn btn-link" onclick="showAttacks('u${poke.getDatabaseID()}')">${poke.getName()}</button></td>
	<td>${poke.getType1()}</td>
	<td>${poke.getType2()}</td>
	<td>${poke.getHitpoints()}</td>
	<td>${poke.getAttack()}</td>
	<td>${poke.getDefense()}</td>
	<td>${poke.getSpAttack()}</td>
	<td>${poke.getSpDefense()}</td>
	<td>${poke.getInitiative()}</td>
  </tr>
  <c:forEach items="${poke.getAttacks()}" var="attack">
  	<tr name="u${poke.getDatabaseID()}" style="display: none">
		<td width=20>${attack.getAttacktype()}</td>
		<td width=20>${attack.getAttackclass()}</td>
		<td width=20>${attack.getDmg()}</td>
		<td width=20>${attack.getEffect()}</td>
  	</tr>
  </c:forEach>
</c:forEach>
  </table>
  </div>
  <div class="col-4">
  <p>Current Enemy Team:</p>
  <br>
  <table>
  <tr>
	<th>"${sessionScope.userTeam.getTeamname()}"</th>
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
  	<td><button type="button" class="btn btn-link" onclick="showAttacks('e${poke.getDatabaseID()}')">${poke.getName()}</button></td>
	<td>${poke.getType1()}</td>
	<td>${poke.getType2()}</td>
	<td>${poke.getHitpoints()}</td>
	<td>${poke.getAttack()}</td>
	<td>${poke.getDefense()}</td>
	<td>${poke.getSpAttack()}</td>
	<td>${poke.getSpDefense()}</td>
	<td>${poke.getInitiative()}</td>
  </tr>
  <c:forEach items="${poke.getAttacks()}" var="attack">
  	<tr name="e${poke.getDatabaseID()}" style="display: none">
		<td width=20>${attack.getAttacktype()}</td>
		<td width=20>${attack.getAttackclass()}</td>
		<td width=20>${attack.getDmg()}</td>
		<td width=20>${attack.getEffect()}</td>
  	</tr>
  </c:forEach>
</c:forEach>
  </table>
  </div>
  <div class="col-4">
  <form action="ServletPrepareTeam" method="post">
  <p>All available Teams:</p>
  <div>
  	<datalist id="suggestions" >
  	<%int number = 0;%>
    	<c:forEach items="${sessionScope.allTeamList}" var="poke">
        	<option value="<%=number%>">${poke.getTeamname()}</option>
        	<%number++;%>
        </c:forEach>
    </datalist>
    <input  autoComplete="on" list="suggestions" name="all"/>
    <button type="submit" name="user" value="yes">Add to User</button>
    <button type="submit" name="enemy" value="yes">Add to Enemy</button>
	</div>
	</form>
	
	<ptt:CaseLoader></ptt:CaseLoader>
	<div class="row">
		<div class="col-4">
			<p>Your Current Team:</p>
			<br>
		</div>
		<div class="col-4">
			<p>Current Enemy Team:</p>
			<br>
		</div>
		<div class="col-4">
			<form>
				<p>All available Teams:</p>
				<div action="prepareTeamServlet" method="post">
					<datalist id="suggestions">
						<%int number = 0;%>
						<c:forEach items="${sessionScope.allTeamList}" var="poke">
							<option value="<%=number%>">${poke.getTeamname()}</option>
							<%number++;%>
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
		<div class="col-4"></div>
		<div class="col-4"></div>
		<div class="col-4"></div>
	</div>
	</div> 
</div>

<script>
		function showAttacks(pokemon_name){
			var e = [];
			var elems = document.getElementsByName(pokemon_name);
			for(var i=0; i<elems.length;i++) {
				if(elems[i].style.display=="none") {
					elems[i].style.display="block"
		    	} else {
		    		   elems[i].style.display="none"
		    	}
			}
		}
	</script>

</body>
</html>