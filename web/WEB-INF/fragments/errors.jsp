<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core" %>
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