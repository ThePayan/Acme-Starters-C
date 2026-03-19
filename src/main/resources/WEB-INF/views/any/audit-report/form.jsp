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
	<acme:form-textbox code="any.audit-report.form.label.ticker" path="ticker"/>
	<acme:form-textbox code="any.audit-report.form.label.name" path="name"/>
	<acme:form-moment code="any.audit-report.form.label.startMoment" path="startMoment"/>
	<acme:form-moment code="any.audit-report.form.label.endMoment" path="endMoment"/>
	<acme:form-url code="any.audit-report.form.label.moreInfo" path="moreInfo"/>
	<acme:form-textarea code="any.audit-report.form.label.description" path="description"/>
	<acme:form-double code="any.audit-report.form.label.monthsActive" path="monthsActive"/>
	<acme:form-integer code="any.audit-report.form.label.hours" path="allHours"/>
	
	<acme:button code="any.audit-report.form.button.audit-sections" action="/any/audit-section/list?audit-reportId=${id}"/>
	<acme:button code="any.audit-report.form.button.auditor" action="/any/auditor/show?auditorId=${auditorId}"/>
</acme:form>
