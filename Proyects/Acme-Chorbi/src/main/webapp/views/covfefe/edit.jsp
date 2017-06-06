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

<form:form action="${requestURI}" modelAttribute="covfefe">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="event"/>
	

	<acme:textbox code="covfefe.title" path="title"/><br />
	<acme:textbox code="covfefe.description" path="description"/><br />
	<div>
		<form:label path="moment">
			<spring:message code="covfefe.moment" />:
		</form:label>
		<form:input readonly="true" placeholder="dd/MM/yyyy HH:mm" path="moment" />
		<form:errors cssClass="error" path="moment" />
	</div> <br>
	<acme:textbox code="covfefe.score" path="assessment"/><br />
	
	<form:select path="assessment">
		<form:option label="<spring:message code ='covfefe.high' />" value="HIGH" />
		<form:option label="<spring:message code ='covfefe.medium' />" value="MEDIUM" />
		<form:option label="<spring:message code ='covfefe.low' />" value="LOW" />
	</form:select>
	
	
	
	<br> 


	<input type="submit" name="save"
		value="<spring:message code="covfefe.save" />" />&nbsp; 
	<input type="button" name="cancel"
		value="<spring:message code="covfefe.cancel" />"
		onclick="location.href = ('covfefe/list.do');" />
	<br />

	

</form:form>