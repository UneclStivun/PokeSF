<%@ include file = "header/checkAdmin.jsp" %>

<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">	
	<link rel="stylesheet" href="css/areaAdmin.css" type="text/css">
	
	<title>User Management</title>
</head>
<body>
	
	<br>
	<div class="container">
	
		<div class="row">
			<div class="col-md-4">
				<form action="ServletUserManagement" method="post">
					<input type="hidden" name="action" value="all">
					<button type="submit" class="btn btn-success">Show all</button>
				</form>
			</div>
			
			<div class="col-md-4">
				<form action="ServletUserManagement" method="post">
					<input type="hidden" name="action" value="user">
					<button type="submit" class="btn btn-success">Show only user</button>
				</form>
			</div>
			
			<div class="col-md-4">
				<form action="ServletUserManagement" method="post">
					<input type="hidden" name="action"value="admin">
					<button type="submit" class="btn btn-success">Show only admins</button>
				</form>
			</div>
		</div>
		
		<br><br>
		
		<div class="row">
			<table class="table">
				<!-- Table Header -->
				<tr>
					<c:forEach items="${columnNames}" var="name">
						<th>${name}</th>
					</c:forEach>
				</tr>

				<!-- Table Content -->
				<c:forEach items="${resultList}" var="result">
					<tr>
						<c:forEach items="${result}" var="value">
							<td><span>${value}</span></td>
						</c:forEach>
						<c:choose>
						<c:when test="${!result[2].equals('origin')}">
							<td>
								<form action="ServletUserManagement" method="post">
									<input type="hidden" name="action" value="change">
									<input type="hidden" name="role" value="${result[2] }">
									<input type="hidden" name="email" value="${result[1]}">
									<button type="submit" class="btn btn-primary">Change Role</button>
								</form>
							</td>
							<td>
								<form action="ServletUserManagement" method="post">
									<input type="hidden" name="action" value="delete">
									<input type="hidden" name="email" value="${result[1]}">
									<button type="submit" class="btn btn-danger">Delete</button>
								</form>
							</td>
						</c:when>
						<c:otherwise>
						<td></td><td></td>
						</c:otherwise>
						</c:choose>
					</tr>
				</c:forEach>
			</table>
		</div>
		
	</div>
	
	<button type="button" class="btn btn-info" style="bottom: 0%; position: fixed;" onclick="window.location.href='index.jsp'">Back to main menu</button>
		
</body>
</html>