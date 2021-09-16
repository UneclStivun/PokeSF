<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@taglib uri="/WEB-INF/prepareTeamTag.tld" prefix="ptt"%>
    <%@taglib  uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
<title>Prepare Pokemon Teams for Battle!</title>
</head>
<body>
<ptt:CaseLoader></ptt:CaseLoader>
<div class="row">
  <div class="col-4">
  <p> Your Current Team:</p>
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
  </div>
</div>
<div class="row">
	<div class="col-4">
		
	</div> 
	<div class="col-4">
	
	</div> 
	<div class="col-4">
	
	</div> 
</div>
</body>
</html>