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
	<acme:form-textbox code="inventor.invention.form.label.ticker" path="ticker" readonly="${!draftMode}"/>
	<acme:form-textbox code="inventor.invention.form.label.name" path="name" readonly="${!draftMode}"/>
	<acme:form-textarea code="inventor.invention.form.label.description" path="description" readonly="${!draftMode}"/>
	<acme:form-moment code="inventor.invention.form.label.startMoment" path="startMoment" readonly="${!draftMode}"/>
	<acme:form-moment code="inventor.invention.form.label.endMoment" path="endMoment" readonly="${!draftMode}"/>
	<acme:form-url code="inventor.invention.form.label.moreInfo" path="moreInfo" readonly="${!draftMode}"/>
	<acme:form-double code="inventor.invention.form.label.monthsActive" path="monthsActive" readonly="true"/>
	<acme:form-money code="inventor.invention.form.label.Costs" path="Costs" readonly="true"/>
	<acme:form-select code="inventor.invention.form.label.project" path="project" choices="${project}"/>
	
	<jstl:choose>	 
		<jstl:when test="${acme:anyOf(_command, 'show|link') && draftMode == false}">
			<acme:button code="inventor.invention.form.button.parts" action="/inventor/part/list?inventionId=${id}"/>
			<acme:submit code="inventor.invention.form.button.link" action="/inventor/invention/link"/>			
		</jstl:when>
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete|publish|link') && draftMode == true}">
			<acme:button code="inventor.invention.form.button.parts" action="/inventor/part/list?inventionId=${id}"/>
			<acme:submit code="inventor.invention.form.button.update" action="/inventor/invention/update"/>
			<acme:submit code="inventor.invention.form.button.delete" action="/inventor/invention/delete"/>
			<acme:submit code="inventor.invention.form.button.publish" action="/inventor/invention/publish"/>
			<acme:submit code="inventor.invention.form.button.link" action="/inventor/invention/link"/>	
		</jstl:when>
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="inventor.invention.form.button.create" action="/inventor/invention/create"/>
		</jstl:when>		
	</jstl:choose>
</acme:form>