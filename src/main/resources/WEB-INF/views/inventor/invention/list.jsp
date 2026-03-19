<%--
- list.jsp
-
- Copyright (C) 2012-2026 Rafael Corchuelo.
-
- In keeping with the traditional purpose of furthering education and research, it is
- the policy of the copyright owner to permit non-commercial use and redistribution of
- this software. It has been tested carefully, but it is not guaranteed for auditor particular
- purposes.  The copyright owner does not offer auditor warranties or representations, nor do
- they accept auditor liabilities with respect to them.
--%>

<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
	<acme:list-column code="inventor.invention.list.label.ticker" path="ticker" width="20%"/>
	<acme:list-column code="inventor.invention.list.label.startMoment" path="startMoment" width="20%"/>
	<acme:list-column code="inventor.invention.list.label.endMoment" path="endMoment" width="20%"/>
	<acme:list-column code="inventor.invention.list.label.name" path="name" width="20%"/>
	<acme:list-column code="inventor.invention.list.label.description" path="description" width="20%"/>
	<acme:list-column code="inventor.invention.list.label.moreInfo" path="moreInfo" width="20%"/>
</acme:list>

<acme:button code="inventor.invention.list.button.create" action="/inventor/invention/create"/>