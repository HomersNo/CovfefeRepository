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


<jstl:set var="own" value="font-style:italic; color: black; font-weight:bold;" />


<display:table pagesize="5" keepStatus="true"
	name="Meshworks" requestURI="meshwork/administrator/list.do" id="row">
	<security:authentication property="principal" var ="loggedactor"/>
	
	<spring:message code="Meshwork.title" var="titleHeader" />
	<spring:message code="Meshwork.description" var="descriptionHeader" />
	<spring:message code="Meshwork.moment" var="momentHeader" />
	<spring:message code="Meshwork.assesment" var="scoreHeader" />
	<spring:message code="Meshwork.uniqueLabel" var="labelHeader" />
	
	<jstl:choose>
		<jstl:when test="${not empty ownMeshworks && ownMeshworks.contains(row)}">
			<display:column property="title" title="${titleHeader}" sortable="true" style="${own}"/>
			<display:column property="description" title="${descriptionHeader}" sortable="true" style="${own}" />
			<display:column property="moment" title="${momentHeader}" sortable="true" style="${own}" />
			<display:column property="assesment" title="${scoreHeader}" sortable="true" style="${own}" />
			<display:column property="uniqueLabel"  title="${labelHeader}" sortable="true" style="${own}" />
			
		</jstl:when>
		<jstl:otherwise>
			<display:column property="title" title="${titleHeader}" sortable="true"/>
			<display:column property="description" title="${descriptionHeader}" sortable="true" />
			<display:column property="moment" title="${momentHeader}" sortable="false"  />
			<display:column property="assesment" title="${scoreHeader}" sortable="true" />
			<display:column property="uniqueLabel"  title="${labelHeader}" sortable="true"/>
		</jstl:otherwise>
	</jstl:choose>
	
	
	
	
	<security:authorize access="hasRole('ADMIN')">
		<spring:message code="Meshwork.cancel" var="cancelHeader" />
		<display:column title="${cancelHeader}">
		<jstl:if test="${row.justification == null || row.justification == ''}"> <!-- Change true for whatever condition it may be -->
			<a href="meshwork/administrator/cancel.do?meshworkId=${row.id}">
			<spring:message code="Meshwork.cancel"/>
			 </a>
		</jstl:if>
		<jstl:if test="${row.justification != null || row.justification != ''}">
			<jstl:out  value="${row.justification }"/>
		</jstl:if>
		</display:column>
	</security:authorize>
	
</display:table>


<br>
<security:authorize access="hasRole('ADMIN')">
	<input type="button" name="create"
		value="<spring:message code="Meshwork.create" />"
		onclick="location.href = ('meshwork/administrator/create.do');" />
</security:authorize>

<br/>