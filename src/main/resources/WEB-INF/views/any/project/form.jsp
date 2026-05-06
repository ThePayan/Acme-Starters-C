<%--
- form.jsp
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

<acme:form>
	<acme:form-textbox code="any.project.form.label.title" path="title"/>
	<acme:form-textarea code="any.project.form.label.description" path="description"/>
	<acme:form-textarea code="any.project.form.label.keyWords" path="keyWords"/>
	<acme:form-moment code="any.project.form.label.kickOff" path="kickOff"/>
	<acme:form-moment code="any.project.form.label.closeOut" path="closeOut"/>
	<acme:form-integer code="any.project.form.label.personMonths" path="personMonths"/>
	

	<acme:button code="any.project.form.button.strategy" action="/any/strategy/list?projectId=${id}"/>		
	<acme:button code="any.project.form.button.campaign" action="/any/campaign/list?projectId=${id}"/>	
	<acme:button code="any.project.form.button.invention" action="/any/invention/list?projectId=${id}"/>
	<acme:button code="any.project.form.button.sponsorship" action="/any/sponsorship/list?projectId=${id}"/>
	<acme:button code="any.project.form.button.auditReport" action="/any/audit-report/list?projectId=${id}"/>
	<acme:button code="any.project.form.button.members" action="/any/project-member/list?projectId=${id}"/>	
</acme:form>

