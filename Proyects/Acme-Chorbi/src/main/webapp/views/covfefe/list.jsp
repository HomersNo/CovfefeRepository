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
<%@taglib prefix="fn"	uri="/WEB-INF/tags/functions"%>

<jstl:set var="own" value="color: black; font-weight:bold;" />


<display:table pagesize="5" keepStatus="true"
	name="covfefes" requestURI="${requestURI}" id="row">
	<security:authentication property="principal" var ="loggedactor"/>
	
	<spring:message code="covfefe.title" var="titleHeader" />
	<spring:message code="covfefe.description" var="descriptionHeader" />
	<spring:message code="covfefe.moment" var="momentHeader" />
	<spring:message code="covfefe.score" var="scoreHeader" />
	<spring:message code="covfefe.uniqueLabel" var="labelHeader" />
	<spring:message code="covfefe.justification" var="justificationHeader" />
	
	
			<display:column  title="${titleHeader}" sortable="true">
				<jstl:choose>
					<jstl:when test="${row.admin.userAccount.id == loggedactor.id }">
						<span style="${own}"><jstl:out value="${row.title }" /></span>
					</jstl:when>
					<jstl:otherwise>
						<span><jstl:out value="${row.title }" /></span>
					</jstl:otherwise>
				</jstl:choose>
			</display:column>
			
			<display:column  title="${descriptionHeader}" sortable="true">
				<jstl:choose>
					<jstl:when test="${row.admin.userAccount.id == loggedactor.id }">
						<span style="${own}"><jstl:out value="${row.description }" /></span>
					</jstl:when>
					<jstl:otherwise>
						<span><jstl:out value="${row.description }" /></span>
					</jstl:otherwise>
				</jstl:choose>
			</display:column>
			
			<display:column  title="${momentHeader}" sortable="true">
				<jstl:choose>
					<jstl:when test="${row.admin.userAccount.id == loggedactor.id }">
						<span style="${own}"><fmt:formatDate value="${row.moment}" pattern="yyyy" /></span>
					</jstl:when>
					<jstl:otherwise>
						<span><fmt:formatDate value="${row.moment}" pattern="yyyy" /></span>
					</jstl:otherwise>
				</jstl:choose>
			</display:column>
			
			<display:column  title="${scoreHeader}" sortable="true">
				<jstl:choose>
					<jstl:when test="${row.admin.userAccount.id == loggedactor.id }">
						<jstl:choose >
						<span style="${own}">
						<jstl:when test="${row.assessment == 'HIGH'}">
							<spring:message code="covfefe.high" />
						</jstl:when>
							<jstl:when test="${row.assessment == 'MEDIUM'}">
							<spring:message code="covfefe.medium" />
						</jstl:when>
						<jstl:when test="${row.assessment == 'LOW'}">
							<spring:message code="covfefe.low" />
						</jstl:when>
						</span>
						</jstl:choose>
					</jstl:when>
					<jstl:otherwise>
						<jstl:choose >
						<span>
						<jstl:when test="${row.assessment == 'HIGH'}">
							<spring:message code="covfefe.high" />
						</jstl:when>
							<jstl:when test="${row.assessment == 'MEDIUM'}">
							<spring:message code="covfefe.medium" />
						</jstl:when>
						<jstl:when test="${row.assessment == 'LOW'}">
							<spring:message code="covfefe.low" />
						</jstl:when>
						</span>
						</jstl:choose>
					</jstl:otherwise>
				</jstl:choose>
			</display:column>
			
			<display:column  title="${labelHeader}" sortable="true">
				<jstl:choose>
					<jstl:when test="${row.admin.userAccount.id == loggedactor.id }">
						<span style="${own}"><jstl:out value="${row.uniqueLabel }" /></span>
					</jstl:when>
					<jstl:otherwise>
						<span><jstl:out value="${row.uniqueLabel }" /></span>
					</jstl:otherwise>
				</jstl:choose>
			</display:column>
			<security:authorize access="hasRole('ADMIN')">
				<display:column  title="${justificationHeader}" sortable="true">
					<jstl:choose>
						<jstl:when test="${row.admin.userAccount.id == loggedactor.id }">
							<span style="${own}"><jstl:out value="${row.justification }" /></span>
						</jstl:when>
						<jstl:otherwise>
							<span><jstl:out value="${row.justification }" /></span>
						</jstl:otherwise>
					</jstl:choose>
				</display:column>
			</security:authorize>
		
		
	
	<security:authorize access="hasRole('MANAGER')">
	
		<spring:message code="covfefe.edit" var="editHeader" />
		<display:column title="${editHeader}">
		<jstl:if test="${loggedactor.id == row.admin.userAccount.id}">
			<a href="covfefe/admin/edit.do?covfefeId=${row.id}"><spring:message code="covfefe.edit" /> </a>
		</jstl:if>
		</display:column>
	
	

	</security:authorize>
	
	<security:authorize access="hasRole('ADMIN')">
		<spring:message code="covfefe.cancel" var="cancelHeader" />
		<display:column title="${cancelHeader}">
			<a href="covfefe/admin/cancel.do?covfefeId=${row.id}">
			<spring:message code="covfefe.cancel"/>
			 </a>
		</display:column>
	</security:authorize>
	
</display:table>




<br/>