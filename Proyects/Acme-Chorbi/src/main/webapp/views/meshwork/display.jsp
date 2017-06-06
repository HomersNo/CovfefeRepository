

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib uri="/WEB-INF/tags/functions" prefix="mask" %>



<security:authentication property="principal" var ="loggedactor"/>
<h2><jstl:out value="${meshwork.title}" />"</h2>
<p><spring:message code="Meshwork.description"/>: <jstl:out value="${meshwork.description }" /></p> 
<p><spring:message code="Meshwork.moment"/>: <jstl:out value="${meshwork.moment }" /></p> 
<p><spring:message code="Meshwork.uniqueLabel"/>: <jstl:out value="${meshwork.uniqueLabel}" /></p> 
<p><spring:message code="Meshwork.assesment"/>: <jstl:out value="${meshwork.assesment}" /></p> 

<br><br><br>
<p style="color:red;"><b><jstl:out value="${meshwork.justification}" /></b></p> 

<security:authorize access="hasRole('ADMIN')">
	 <
</security:authorize>


<br/>




