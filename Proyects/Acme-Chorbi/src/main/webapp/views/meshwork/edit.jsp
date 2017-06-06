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

<form:form action="${requestURI}" modelAttribute="meshwork">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="uniqueLabel" />
	

	<acme:textbox code="Meshwork.title" path="title"/><br />
	<acme:textbox code="Meshwork.description" path="description"/><br />
	<div>
		<form:label path="moment">
			<spring:message code="Meshwork.moment" />:
		</form:label>
		<form:input readonly="true" placeholder="dd/MM/yyyy HH:mm" path="moment" />
		<form:errors cssClass="error" path="moment" />
	</div> <br>
	<form:label path="assesment">
		<spring:message code="Meshwork.assesment" />:
	</form:label>
	<form:select id="assesment" path="assesment">
		
			<form:option value="HIGHLY RECOMMENDED" label="HIGHLY RECOMMENDED" />
			<form:option value="RECOMMENDED" label="RECOMMENDED" />
			<form:option value="NOT RECOMMENDED" label="NOT RECOMMENDED" />

	</form:select>
	<form:errors cssClass="error" path="assesment" />
	<br> <br>
	
		<form:label path="event">
		<spring:message code="Meshwork.event" />:
	</form:label>
	<form:select id="events" path="event">
		<jstl:forEach items="${events}" var="thisEvent">
			<form:option value="${thisEvent.id}" label="${thisEvent.title}" />
		</jstl:forEach>
	</form:select>
	<form:errors cssClass="error" path="event" />
	<br> <br>


	<input type="submit" name="save"
		value="<spring:message code="Meshwork.save" />" />&nbsp; 
	<input type="button" name="cancel"
		value="<spring:message code="Meshwork.cancel" />"
		onclick="location.href = ('Meshwork/list.do');" />
	<br />

	

</form:form>