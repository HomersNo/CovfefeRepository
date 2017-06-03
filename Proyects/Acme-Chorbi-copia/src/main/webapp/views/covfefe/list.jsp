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
	name="covfefes" requestURI="${requestURI}" id="row">
	<security:authentication property="principal" var ="loggedactor"/>
	
	<spring:message code="covfefe.title" var="titleHeader" />
	<spring:message code="covfefe.description" var="descriptionHeader" />
	<spring:message code="covfefe.moment" var="momentHeader" />
	<spring:message code="covfefe.score" var="scoreHeader" />
	<spring:message code="covfefe.uniqueLabel" var="labelHeader" />
	
	<jstl:choose>
		<jstl:when test="${not empty ownCovfefes && ownCovfefes.contains(row)}">
			<display:column property="title" title="${titleHeader}" sortable="true" style="${own}"/>
			<display:column property="description" title="${descriptionHeader}" sortable="true" style="${own}" />
			<display:column property="moment" title="${momentHeader}" sortable="true" style="${own}" />
			<display:column property="score" title="${scoreHeader}" sortable="true" style="${own}" />
			<display:column property="uniqueLabel"  title="${labelHeader}" sortable="true" style="${own}" />
		</jstl:when>
		<jstl:otherwise>
			<display:column property="title" title="${titleHeader}" sortable="true"/>
			<display:column property="description" title="${descriptionHeader}" sortable="true" />
			<display:column property="moment" title="${momentHeader}" sortable="false"  />
			<display:column property="score" title="${scoreHeader}" sortable="true" />
			<display:column property="uniqueLabel"  title="${labelHeader}" sortable="true"/>
		</jstl:otherwise>
	</jstl:choose>
	
	
	<security:authorize access="hasRole('MANAGER')">
	<jstl:if test="${loggedactor.id == row.organiser.userAccount.id}">
		<spring:message code="covfefe.edit" var="editHeader" />
		<display:column title="${editHeader}">
			<a href="covfefe/_manager/edit.do?covfefeId=${row.id}"><spring:message code="covfefe.edit" /> </a>
		</display:column>
	
	
		<spring:message code="covfefe.delete" var="deleteHeader" />
		<display:column title="${deleteHeader}">
			<a href="covfefe/_manager/delete.do?covfefeId=${row.id}"><spring:message code="covfefe.delete" /> </a>
		</display:column>
	</jstl:if>
	</security:authorize>
	
	<security:authorize access="isAuthenticated()">
		<spring:message code="covfefe.cancel" var="cancelHeader" />
		<display:column title="${cancelHeader}">
		<jstl:if test="${true}"> <!-- Change true for whatever condition it may be -->
			<a href="covfefe/actor/covfefe.do?covfefeId=${row.id}">
			 </a>
		</jstl:if>
		</display:column>
	</security:authorize>
	
</display:table>


<br>
<security:authorize access="hasRole('MANAGER')">
	<input type="button" name="create"
		value="<spring:message code="covfefe.create" />"
		onclick="location.href = ('covfefe/_manager/create.do');" />
</security:authorize>

<br/>