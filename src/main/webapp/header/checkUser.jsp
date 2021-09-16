<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

		
	<!-- Wenn Nutzer nicht angemeldet ist, wird er zur Index geleitet -->
	<c:if test="${sessionScope.user_email == null }">
		<c:redirect url="userDenied.jsp"></c:redirect>
	</c:if>
