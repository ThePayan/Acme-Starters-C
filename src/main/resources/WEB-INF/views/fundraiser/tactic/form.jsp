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
	<acme:form-textbox code="fundraiser.tactic.form.label.name" path="name"/>
	<acme:form-textarea code="fundraiser.tactic.form.label.notes" path="notes"/>
	<acme:form-double code="fundraiser.tactic.form.label.expectedPercentage" path="expectedPercentage"/>
	<acme:form-select path="kind" code="fundraiser.tactic.form.label.kind" choices="${tacticKinds}" />	
	<jstl:choose>
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete') && draftMode == true}">
			<acme:submit code="fundraiser.tactic.form.button.update" action="/fundraiser/tactic/update"/>
			<acme:submit code="fundraiser.tactic.form.button.delete" action="/fundraiser/tactic/delete"/>
		</jstl:when>
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="fundraiser.tactic.form.button.create" action="/fundraiser/tactic/create?strategyId=${strategyId}"/>
		</jstl:when>		
	</jstl:choose>		
</acme:form>

