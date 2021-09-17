<%@ include file = "header/checkUser.jsp" %>
<%@taglib uri="/WEB-INF/createTeamTag.tld" prefix="ctt" %>

<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">	
	<title>Pokemon Team Finder</title>
</head>
<body>
<div class="container">
  <div class="row">
<a href="pokemonPrepareTeam.jsp" class="btn btn-primary">Prepare fight!</a>
  </div>
  <div class="row">
    <div class="col-6">
      <div>
		<form action="ServletCreateTeam" method="post">
			<c:if test="${sessionScope.poketeam != null && !sessionScope.poketeam.isEmpty()}">
		<%int number = 0;%>
		<p>${MsgTeam}</p>
		<table class="table table-bordered table-dark">
			<!-- Table Header -->
			<tr>
				<th>Team</th>
				<th colspan="2">Type</th>
				<th>Hp</th>
				<th>Att</th>
				<th>Def</th>
				<th>SpAtt</th>
				<th>SpDef</th>	
				<th>Ini</th>
				<c:if test="${sessionScope.poketeam.size() == 6}">
					<th>
						<input type="text" class="form-control" placeholder="Team name" name="team_name">
					</th>
					<th>
						<button class="btn btn-primary" type="submit" value="save" name="save">Save!</button>
					</th>
				</c:if>
			</tr>
			<!-- Table Content -->
			<c:forEach items="${sessionScope.poketeam}" var="result">
				<tr>
					<td><button type="button" class="btn btn-link" onclick="showAttacks('t${result.getDatabaseID()}')">${result.getName()}</button></td>
					<td>${result.getType1()}</td>
					<td>${result.getType2()}</td>
					<td>${result.getHitpoints()}</td>
					<td>${result.getAttack()}</td>
					<td>${result.getDefense()}</td>
					<td>${result.getSpAttack()}</td>
					<td>${result.getSpDefense()}</td>
					<td>${result.getInitiative()}</td>
					<td><button class="btn btn-primary" type="submit" value="<%=number%>" name="delete">remove</button></td>
				</tr>
				<c:forEach items="${result.getAttacks()}" var="attack">
				<tr name="t${result.getDatabaseID()}" style="display: none">
					<td width=20>${attack.getAttacktype()}</td>
					<td width=20>${attack.getAttackclass()}</td>
					<td width=20>${attack.getDmg()}</td>
					<td width=20>${attack.getEffect()}</td>
				</tr>
				</c:forEach>
				<%number++; %>
			</c:forEach>
		</table>
		<br>
	</c:if>
		</form>
		</div>
    </div>
    <div class="col-6">
      <div class="d-flex p-2">	
		<div class="d-inline-flex p-2">
<form action="ServletCreateTeam" method="post">
	<c:if
		test="${sessionScope.quickList != null && !sessionScope.quickList.isEmpty()}">
		<%
		int number = 0;
		%>
		<table class="table table-bordered">
			<!-- Table Header -->
			<tr>
				<th>Pokemon quicklist</th>
				<th colspan="2">Type</th>
				<th>Hp</th>
				<th>Att</th>
				<th>Def</th>
				<th>SpAtt</th>
				<th>SpDef</th>	
				<th>Ini</th>
				<th>Add to Team?</th>	
			</tr>
			<!-- Table Content -->
			<c:forEach items="${sessionScope.quickList}" var="result">
				<tr>
					<td><button type="button" class="btn btn-link" onclick="showAttacks('q${result.getDatabaseID()}')">${result.getName()}</button></td>
					<td>${result.getType1()}</td>
					<td>${result.getType2()}</td>
					<td>${result.getHitpoints()}</td>
					<td>${result.getAttack()}</td>
					<td>${result.getDefense()}</td>
					<td>${result.getSpAttack()}</td>
					<td>${result.getSpDefense()}</td>
					<td>${result.getInitiative()}</td>
					<td><button class="btn btn-primary" style="height:35px;width:60px" type="submit" value="<%=number%>" name="add">Add</button></td>
				</tr>
				<c:forEach items="${result.getAttacks()}" var="attack">
				<tr name="q${result.getDatabaseID()}" style="display: none">
					<td width=20>${attack.getAttacktype()}</td>
					<td width=20>${attack.getAttackclass()}</td>
					<td width=20>${attack.getDmg()}</td>
					<td width=20>${attack.getEffect()}</td>
				</tr>
				</c:forEach>
				<%number++; %>
			</c:forEach>
		</table>
	</c:if>
	</form>
		</div>
    </div>
  </div>
</div>
<ctt:CaseLoader></ctt:CaseLoader>
<c:if test="${sessionScope.allPokeList != null && !sessionScope.allPokeList.isEmpty()}">
<form action="ServletCreateTeam" method="post">
<%int number = 0;%>
<div class="row">
	<div class="col-sm-6">
     	<div class="d-flex p-2">
			<div>
				<p>All Pokemon</p>
    			<datalist id="suggestions" >
    			<c:forEach items="${sessionScope.allPokeList}" var="poke">
        			<option value="<%=number%>">${poke.getName()}</option>
        			<%number++;%>
        		</c:forEach>
    			</datalist>
    			<input  autoComplete="on" list="suggestions" name="all"/>
    			<button type="submit">Add</button>
			</div>
		</div>
    </div>
</div>
</form>
</c:if>	
</div>

	<br><br>
	<a href="index.jsp">Back to main page</a>
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