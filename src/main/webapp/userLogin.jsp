<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%-- 
  - Author(s): Tobias Brakel
  - Description: userLogin.jsp
  --%>    
<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
	<link rel="stylesheet" href="css/registration.css" type="text/css">
	
	<title>Login</title>
</head>
<body>
	<div class="container">
	
		<form method="post" action="ServletUserLogin">
		 	<div class="form-group">	    
		  		<label>Email address</label>
		    	<input type="email" name="user_email" class="form-control" placeholder="Enter your email address">
		  		<br>
		    	<label>Password</label>
		    	<input type="password" name="user_password" class="form-control" placeholder="Enter your password">
		 	</div>	  
		  	<button type="submit" class="btn btn-success">Login</button>
		</form>
		<br>
		<p class="font-weight-italic" style="color:#f55a55;">${message }</p>
	</div>
	
	<button type="button" class="btn btn-info" style="bottom: 0%; position: fixed;" onclick="window.location.href='index.jsp'">Back to main menu</button>

</body>
</html>