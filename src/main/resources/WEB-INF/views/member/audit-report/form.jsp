
<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:form-textbox code="member.audit-report.form.label.ticker" path="ticker"/>
	<acme:form-textbox code="member.audit-report.form.label.name" path="name"/>
	<acme:form-moment code="member.audit-report.form.label.startMoment" path="startMoment"/>
	<acme:form-moment code="member.audit-report.form.label.endMoment" path="endMoment"/>
	<acme:form-url code="member.audit-report.form.label.moreInfo" path="moreInfo"/>
	<acme:form-textarea code="member.audit-report.form.label.description" path="description"/>
	<acme:form-double code="member.audit-report.form.label.monthsActive" path="monthsActive"/>
	<acme:form-integer code="member.audit-report.form.label.hours" path="allHours"/>
</acme:form>
