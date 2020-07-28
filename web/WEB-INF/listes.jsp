<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="fr.eni.javaee.gestionlistescourses.messages.Reader" %>
<%@ taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<jsp:include page="/WEB-INF/fragments/head.jsp">
	<jsp:param name="title" value="Courses - Listes disponibles"/>
</jsp:include>
<body>
	<jsp:include page="/WEB-INF/fragments/header.jsp">
		<jsp:param name="h1" value="Courses - Listes des courses"/>
		<jsp:param name="h2" value="Listes disponibles"/>
	</jsp:include>
	<%@include file="./fragments/errors.jsp"%>
	<section>
		<core:choose>
			<core:when test="${!empty listes}">
				<ul>
					<core:forEach var="liste" items="${listes}">
					<li>
						<a href="${pageContext.request.contextPath}/display?identifier=${liste.getIdentifier()}"><i class="material-icons">${liste.getName()}</i></a>
						<a href="${pageContext.request.contextPath}/basket?identifier=${liste.getIdentifier()}"><i class="fas fa-shopping-basket"></i></a>
						<a href="${pageContext.request.contextPath}/deleteListe?identifier=${liste.getIdentifier()}"><i class="fas fa-trash-alt"></i></a>
					</li>
					</core:forEach>
				</ul>
			</core:when>
			<core:otherwise>
			<p>Pas de liste disponible.<p>
			</core:otherwise>
			</core:choose>
	</section>
	<hr />
	<footer>
		<p><a href="${pageContext.request.contextPath}/display"><i class="fas fa-plus-square"></i></a></p>
	</footer>
</body>
</html>
