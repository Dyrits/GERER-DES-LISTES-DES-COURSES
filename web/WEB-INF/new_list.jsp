<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="fr.eni.javaee.gestionlistescourses.messages.Reader" %>
<%@ taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<jsp:include page="/WEB-INF/fragments/head.jsp">
	<jsp:param name="title" value="Courses - Création d'une nouvelle liste"/>
</jsp:include>
<body>
	<jsp:include page="/WEB-INF/fragments/header.jsp">
		<jsp:param name="h1" value="Courses - Listes des courses"/>
		<jsp:param name="h2" value="Création d'une nouvelle liste"/>
	</jsp:include>
	<%@include file="./fragments/errors.jsp"%>
	<section>
		<form method="post" action="${pageContext.request.contextPath}/addArticle">
				<core:if test="${empty liste}">
					<label for="name">Nom :</label>
					<input class="form-control" type="text" id="name" name="name" value="">
				</core:if>
				<core:if test="${!empty liste}">
					<p>Nom : ${liste.getName()}</p>
					<input type="hidden" value="${liste.getIdentifier()}" name="identifier"/>
					<input type="hidden" value="${liste.getName()}" name="name"/>
				</core:if>
				<core:choose>
				<core:when test="${!empty articles}">
					<core:forEach var="article" items="${articles}">
						<li>${article.getName()}
								<a href="${pageContext.request.contextPath}/deleteArticle?identifier=${liste.getIdentifier()}&identifierArticle=${article.getIdentifier()}"><i class="fas fa-trash-alt"></i></a>
						</li>
					</core:forEach>
				</core:when>
				<core:otherwise>
				<p>Pas d"article disponible.<p>
				</core:otherwise>
				</core:choose>
			</ul>
			<article>
				<h3>Nouvel article</h3>
				<label for="nameArticle">Nom :</label>
				<input type="text" id="nameArticle" name="nameArticle">
				<button type="submit"><i class="far fa-plus-square"></i></button>
			</article>
		</form>
	</section>
	<hr />
	<footer>
			<p><a class="btn" href="${pageContext.request.contextPath}/listes"><i class="fas fa-arrow-alt-circle-left"></i></a></p>
	</footer>
</body>
</html>
