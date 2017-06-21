<%--
 * edit.jsp
 *
 * Copyright (C) 2016 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme"	tagdir="/WEB-INF/tags"%>

<form:form action="${requestURI}" modelAttribute="loots">

	<form:hidden path="id" />
	<form:hidden path="version" />
	

	<acme:textbox code="loots.title" path="title"/><br />
	<acme:textbox code="loots.description" path="description"/><br />
	<div>
		<form:label path="moment">
			<spring:message code="loots.moment" />:
		</form:label>
		<form:input readonly="true" placeholder="dd/MM/yyyy HH:mm" path="moment" />
		<form:errors cssClass="error" path="moment" />
	</div> <br><br />
	
	<spring:message code="loots.assessment" />:
	<form:select id="assessment" path="assessment">
		<form:option value="Highly Recommended" label="Highly Recommended" />
		<form:option value="Recommended" label="Recommended" />
		<form:option value="Not Recommended" label="Not Recommended" />
	</form:select><br><br />
	
	<acme:textbox code="loots.uniqueTracer" path="uniqueTracer" readonly="true"/><br />
	
		<form:label path="event">
		<spring:message code="loots.event" />:
	</form:label>
	<form:select id="events" path="event">
		<jstl:forEach items="${events}" var="thisEvent">
			<form:option value="${thisEvent.id}" label="${thisEvent.title}" />
		</jstl:forEach>
	</form:select>
	<form:errors cssClass="error" path="event" />
	<br> <br>


	<input type="submit" name="save"
		value="<spring:message code="loots.save" />" />&nbsp; 
	<input type="button" name="cancel"
		value="<spring:message code="loots.cancel" />"
		onclick="location.href = ('loots/administrator/list.do');" />
	<br />

	

</form:form>