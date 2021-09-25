<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
	<title>Login</title>
</head>
<body>
	<form method="post" action="ServletUserLogin">
	 	<div class="form-group">	    
	  		<label>Email address</label>
	    	<input type="email" name="user_email" class="form-control" placeholder="Enter your email address">
	  		<br>
	    	<label>Password</label>
	    	<input type="password" name="user_password" class="form-control" placeholder="Enter your password">
	 	</div>	  
	  	<button type="submit" class="btn btn-primary">Login</button>
	</form>
	<br>
	<p class="font-weight-italic" style="color:red;">${message }</p>
	
	<br><br>
	<a href="index.jsp">Back to main page</a>
</body>
</html>