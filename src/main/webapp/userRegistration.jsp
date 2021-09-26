<%-- 
  - Author(s): Tobias Brakel
  - Description: userRegistration.jsp
  --%>
<!DOCTYPE html>
<html>
<head>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
	<link rel="stylesheet" href="css/registration.css" type="text/css">
	
	<title>Registration</title>
</head>
<body>
	<div class="container">
	
		<form method="post" action="ServletUserRegistration">
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
		  	<button type="submit" class="btn btn-success">Register</button>
		</form>
		<br>
		<p class="font-weight-italic" style="color:#f55a55;">${message }</p>
		
	</div>
	
	<button type="button" class="btn btn-info" style="bottom: 0%; position: fixed;" onclick="window.location.href='index.jsp'">Back to main menu</button>
	
</body>
</html>