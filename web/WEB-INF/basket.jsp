<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="fr.eni.javaee.gestionlistescourses.messages.Reader" %>
<%@ taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<jsp:include page="/WEB-INF/fragments/head.jsp">
	<jsp:param name="title" value="Détails d'une liste"/>
</jsp:include>
<body>
	<jsp:include page="/WEB-INF/fragments/header.jsp">
		<jsp:param name="h1" value="Courses - Listes des courses"/>
		<jsp:param name="h2" value="Détails d'une liste"/>
	</jsp:include>
	<section>
		<core:if test="${!empty errors}">
			<div >
				<p><strong>Une ou plusieurs erreurs sont survenues: </strong></p>
				<ul>
					<core:forEach var="code" items="${errors}">
						<li>${Reader.getMessageError(code)}</li>
					</core:forEach>
				</ul>
			</div>
		</core:if>
	</section>
	<section>
		<core:choose>
			<core:when test="${!empty articles}">
				<form action="${pageContext.request.contextPath}/basket" method="post">
					<ul>
						<core:forEach var="article" items="${articles}">
								<li>
									<input id="${article.getIdentifier()}" type="checkbox" name="checkboxes" value="${article.getIdentifier()}" ${article.getChecked()}/>
									<label for="${article.getIdentifier()}">${article.getName()}</label>
								</li>
						</core:forEach>
					</ul>
					<input type="hidden" value="${liste.getIdentifier()}" name="identifier"/>
					<button type="submit"><i class="far fa-save"></i></button>
				</form>
			</core:when>
		<core:otherwise>
		<p>Pas d'article' disponible.<p>
		</core:otherwise>
		</core:choose>
	</section>
	<hr />
	<footer>
		<p>
			<a class="btn" href="${pageContext.request.contextPath}/listes"><i class="fas fa-arrow-alt-circle-left"></i></a>
			<a class="btn" href="${pageContext.request.contextPath}/basket?identifier=${liste.getIdentifier()}&uncheckAll=${true}"><i class="fas fa-eraser"></i></a>
		</p>
	</footer>
  </body>
</html>