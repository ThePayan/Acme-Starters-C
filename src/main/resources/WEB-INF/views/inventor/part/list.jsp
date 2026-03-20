<%--
- list.jsp
-
- Copyright (C) 2012-2026 Rafael Corchuelo.
-
- In keeping with the traditional purpose of furthering education and research, it is
- the policy of the copyright owner to permit non-commercial use and redistribution of
- this software. It has been tested carefully, but it is not guaranteed for any particular
- purposes.  The copyright owner does not offer any warranties or representations, nor do
- they accept any liabilities with respect to them.
--%>

<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
	<acme:list-column code="inventor.part.list.label.name" path="name" width="80%"/>	
	<acme:list-column code="inventor.part.list.label.description" path="description" width="20%"/>
</acme:list>

<jstl:if test="${showCreate}">
	<acme:button code="inventor.part.list.button.create" action="/inventor/part/create?inventionId=${inventionId}"/>
</jstl:if>