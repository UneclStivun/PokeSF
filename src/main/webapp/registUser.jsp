<!DOCTYPE html>
<html>
<head>
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
	<title>Register User</title>
</head>
<body>
	<form method="post" action="ServletRegistUser">
	 	<div class="form-group">	    
	  		<label>Username</label>
	  		<input type="text" name="user_name" class="form-control" placeholder="Please Enter your username">
	  		<br>
	    	<label>Password</label>
	    	<input type="password" name="user_password" class="form-control" placeholder="Please enter your password">
	  		<br>
	  		<label>Email address</label>
	    	<input type="email" name="user_email" class="form-control" placeholder="Please enter your email address">
	 	</div>	  
	  	<button type="submit" class="btn btn-primary">Register</button>
	</form>
	
	<br>
	<p class="font-weight-italic" style="color:red;">${message }</p>
</body>
</html>