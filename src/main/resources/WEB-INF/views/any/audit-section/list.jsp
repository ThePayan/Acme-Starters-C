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
	<acme:list-column code="any.audit-section.list.label.name" path="name" width="20%"/>
	<acme:list-column code="any.audit-section.list.label.notes" path="notes" width="20%"/>
	<acme:list-column code="any.audit-section.list.label.hours" path="hours" width="20%"/>
	<acme:list-column code="any.audit-section.list.label.kind" path="kind" width="20%"/>
</acme:list>
