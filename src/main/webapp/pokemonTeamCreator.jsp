<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ include file = "header/checkUser.jsp" %>

<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">	
	<title>Pokemon Team Finder</title>
</head>
<body>
<form action="ServletAgentLauncher" method="post">
	<button class="btn btn-danger" type="submit" value="start" name="fight">Start fight!</button>
</form>
	<div class="d-flex p-2">
		<div class="d-inline-flex p-2">
	<form action="ServletCreateTeam" method="post">
	<c:if
		test="${sessionScope.poketeam != null && !sessionScope.poketeam.isEmpty()}">
		<%
		int number = 0;
		%>
		<p>${MsgTeam}</p>
		<table border=1>
			<!-- Table Header -->
			<tr>
				<th>Pokemon Team Creator</th>
				<c:if test="${sessionScope.poketeam.size() == 6}">
					<th>
						<input type="text" class="form-control" placeholder="Team name" name="team_name">
					</th>
					<th>
						<button class="btn btn-primary" type="submit" value="save" name="save">Save Team!</button>
					</th>
				</c:if>
			</tr>
			<!-- Table Content -->
			<c:forEach items="${sessionScope.poketeam}" var="result">
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
					<td><button class="btn btn-primary" type="submit" value="<%=number%>" name="delete">remove</button></td>
				</tr>
				<tr id="${result.getDatabaseID()}" style="display: none">
					<c:forEach items="${result.getAttacks()}" var="attack">
						<td width=20>${attack.getAttacktype()}</td>
						<td width=30>${attack.getAttackclass()}</td>
						<td width=40>${attack.getDmg()}</td>
						<td width=50>${attack.getEffect()}</td>
					</c:forEach>
				</tr>
				<%number++; %>
			</c:forEach>
		</table>
		<br>
	</c:if>
	</form>
		</div>
		<div class="d-inline-flex p-2">
<form action="ServletCreateTeam" method="post">
	<c:if
		test="${sessionScope.quickList != null && !sessionScope.quickList.isEmpty()}">
		<%
		int number = 0;
		%>
		<table border=1>
			<!-- Table Header -->
			<tr>
				<th>Pokemon quicklist</th>
			</tr>
			<!-- Table Content -->
			<c:forEach items="${sessionScope.quickList}" var="result">
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
					<td><button class="btn btn-primary" type="submit" value="<%=number%>" name="add">Add to team</button></td>
				</tr>
				<tr id="${result.getDatabaseID()}" style="display: none">
					<c:forEach items="${result.getAttacks()}" var="attack">
					<td width=20>${attack.getAttacktype()}</td>
					<td width=30>${attack.getAttackclass()}</td>
					<td width=40>${attack.getDmg()}</td>
					<td width=50>${attack.getEffect()}</td>
					</c:forEach>
				</tr>
				<%number++; %>
			</c:forEach>
		</table>
		<br>
		<button class="btn btn-primary" type="submit">Add to quicklist</button>
	</c:if>
	</form>
		</div>
	</div>

	<br><br>
	<a href="index.jsp">Back to main page</a>
	
</body>
</html>