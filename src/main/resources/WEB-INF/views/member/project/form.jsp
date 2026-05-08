<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:form-textbox code="member.project.form.label.title" path="title"/>
	<acme:form-textarea code="member.project.form.label.description" path="description"/>
	<acme:form-textarea code="member.project.form.label.keyWords" path="keyWords"/>
	<acme:form-moment code="member.project.form.label.kickOff" path="kickOff"/>
	<acme:form-moment code="member.project.form.label.closeOut" path="closeOut"/>
	<acme:form-integer code="member.project.form.label.personMonths" path="personMonths"/>

	<acme:button code="member.project.form.button.strategy" action="/member/strategy/list?projectId=${id}"/>		
	<acme:button code="member.project.form.button.campaign" action="/member/campaign/list?projectId=${id}"/>	
	<acme:button code="member.project.form.button.invention" action="/member/invention/list?projectId=${id}"/>
	<acme:button code="member.project.form.button.sponsorship" action="/member/sponsorship/list?projectId=${id}"/>
	<acme:button code="member.project.form.button.auditReport" action="/member/audit-report/list?projectId=${id}"/>
	<acme:button code="member.project.form.button.members" action="/member/project-member/list?projectId=${id}"/>	
</acme:form>