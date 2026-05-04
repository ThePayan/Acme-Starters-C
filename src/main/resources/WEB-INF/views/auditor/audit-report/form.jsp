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
	<acme:form-textbox code="auditor.audit-report.form.label.ticker" path="ticker" readonly="${!draftMode}"/>
	<acme:form-textbox code="auditor.audit-report.form.label.name" path="name" readonly="${!draftMode}"/>
	<acme:form-textarea code="auditor.audit-report.form.label.description" path="description" readonly="${!draftMode}"/>
	<acme:form-moment code="auditor.audit-report.form.label.startMoment" path="startMoment" readonly="${!draftMode}"/>
	<acme:form-moment code="auditor.audit-report.form.label.endMoment" path="endMoment" readonly="${!draftMode}"/>
	<acme:form-url code="auditor.audit-report.form.label.moreInfo" path="moreInfo" readonly="${!draftMode}"/>
	<acme:form-double code="auditor.audit-report.form.label.monthsActive" path="monthsActive" readonly="true"/>
	<acme:form-integer code="auditor.audit-report.form.label.allHours" path="allHours" readonly="true"/>
	<acme:form-select code="auditor.audit-report.form.label.project" path="project" choices="${project}" readonly="${draftMode}" />
	<jstl:choose>	 
		<jstl:when test="${acme:anyOf(_command, 'show|link') && draftMode == false}">
			<acme:button code="auditor.audit-report.form.button.audit-sections" action="/auditor/audit-section/list?audit-reportId=${id}"/>	
			<acme:submit code="auditor.audit-report.form.button.link" action="/auditor/audit-report/link"/>		
		</jstl:when>
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete|publish') && draftMode == true}">
			<acme:button code="auditor.audit-report.form.button.audit-sections" action="/auditor/audit-section/list?audit-reportId=${id}"/>
			<acme:submit code="auditor.audit-report.form.button.update" action="/auditor/audit-report/update"/>
			<acme:submit code="auditor.audit-report.form.button.delete" action="/auditor/audit-report/delete"/>
			<acme:submit code="auditor.audit-report.form.button.publish" action="/auditor/audit-report/publish"/>
		</jstl:when>
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="auditor.audit-report.form.button.create" action="/auditor/audit-report/create"/>
		</jstl:when>		
	</jstl:choose>
</acme:form>
