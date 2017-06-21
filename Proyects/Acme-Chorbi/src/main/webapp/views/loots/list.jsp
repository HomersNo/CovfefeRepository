<%--
 * list.jsp
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


<jstl:set var="own" value="color: black; font-weight:bold;"/>
<jstl:set var="ownPast" value="color: black; font-weight:bold; + '*'" />

<jstl:set var="notOwn" value="color: black;" />
<jstl:set var="notOwnPast" value="color: black;" />

<display:table pagesize="5" keepStatus="true"
	name="loots" requestURI="${requestURI}" id="row">
	<security:authentication property="principal" var ="loggedactor"/>
	
	<spring:message code="loots.title" var="titleHeader" />
	<spring:message code="loots.description" var="descriptionHeader" />
	<spring:message code="loots.moment" var="momentHeader" />
	<spring:message code="loots.assessment" var="assessmentHeader" />
	<spring:message code="loots.uniqueTracer" var="tracerHeader" />
	<spring:message code="loots.event" var="eventHeader" />
	
	<jstl:choose>
		<jstl:when test="${not empty ownLoots && ownLoots.contains(row)}">
			<jstl:choose>
				<jstl:when test="${row.event.moment < now}">
					<display:column value="*" style="${own}"/>
				</jstl:when>
				<jstl:otherwise>
					<display:column property="title" title="${titleHeader}" sortable="true" style="${own}"/>
				</jstl:otherwise>
			</jstl:choose>
					<display:column property="description" title="${descriptionHeader}" sortable="true" style="${own}" />
					<display:column property="moment" title="${momentHeader}" sortable="true" style="${own}" />
					<display:column property="assessment" title="${assessmentHeader}" sortable="true" style="${own}" />
					<display:column property="uniqueTracer"  title="${tracerHeader}" sortable="true" style="${own}" />
					<display:column property="event.title"  title="${eventHeader}" sortable="false" style="${own}" />
		</jstl:when>
		<jstl:otherwise>
			<jstl:choose>
				<jstl:when test="${row.event.moment < now}">
					<display:column value="*" style="${notOwn}"/>
				</jstl:when>
				<jstl:otherwise>
					<display:column property="title" title="${titleHeader}" sortable="true" style="${notOwn}"/>
				</jstl:otherwise>
			</jstl:choose>
			<display:column property="description" title="${descriptionHeader}" sortable="true" style="${notOwn}"/>
			<display:column property="moment" title="${momentHeader}" sortable="false"  style="${notOwn}"/>
			<display:column property="assessment" title="${assessmentHeader}" sortable="true" style="${notOwn}"/>
			<display:column property="uniqueTracer"  title="${tracerHeader}" sortable="true" style="${notOwn}"/>
			<display:column property="event.title"  title="${eventHeader}" sortable="false" style="${notOwn}" />
		</jstl:otherwise>
	</jstl:choose>
	
	
	<security:authorize access="hasRole('ADMIN')">
	
		<spring:message code="loots.edit" var="editHeader" />
		<display:column title="${editHeader}">
		<jstl:if test="${loggedactor.id == row.administrator.userAccount.id}">
			<a href="loots/administrator/edit.do?lootsId=${row.id}"><spring:message code="loots.edit" /> </a>
		</jstl:if>
		</display:column>
	
	

	</security:authorize>
	
	<security:authorize access="hasRole('ADMIN')">
		<spring:message code="loots.cancel" var="cancelHeader" />
		<display:column title="${cancelHeader}">
		<jstl:if test="${row.justification == ''}"> <!-- Change true for whatever condition it may be -->
			<a href="loots/actor/cancel.do?lootsId=${row.id}">
			<spring:message code="loots.cancel"/>
			 </a>
		</jstl:if>
		</display:column>
	</security:authorize>
	
</display:table>

<br>
<security:authorize access="hasRole('ADMIN')">
	<input type="button" name="create"
		value="<spring:message code="loots.create" />"
		onclick="location.href = ('loots/administrator/create.do');" />
</security:authorize>

<br/>