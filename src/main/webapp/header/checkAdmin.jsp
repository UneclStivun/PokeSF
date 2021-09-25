<%@page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

	<!-- Wenn Nutzer keine Adminrolle hat, wird auf die Index zurückgeleitet -->
	<c:if test="${sessionScope.user_role != 'admin' && sessionScope.user_role != 'origin' }">
		<c:redirect url="userDenied.jsp"></c:redirect>
	</c:if>
