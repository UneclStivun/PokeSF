<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file = "header/checkAdmin.jsp" %>

<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">	
	<title>Edit Pokemon Database</title>
</head>
<body>
	<button type="button" class="btn btn-info" onclick="window.location.href='ServletPokemonEdit?action=invalid'">Show not validated pokemon</button>
	<button type="button" class="btn btn-info" onclick="window.location.href='ServletPokemonEdit?action=all'">Show all pokemon</button> <br>
	
	<c:if test="${resultList != null && !resultList.equals('')}">
		<table border=1>
			<tr>
				<c:forEach items="${columnNames}" var="name">
					<th>${name}</th>
				</c:forEach>
			</tr>
			<c:forEach items="${resultList}" var="result">
				<tr>
					<c:forEach items="${result}" var="value">
						<td>${value}</td>
					</c:forEach>
				</tr>
			</c:forEach>
		</table>
	</c:if>
	
	
	
	
	Select Pokemon to delete.<br>
	<br>
	Do you want to delete this Pokemon?
	<br>
	<button type="button" class="btn btn-danger"></button>
</body>
</html>