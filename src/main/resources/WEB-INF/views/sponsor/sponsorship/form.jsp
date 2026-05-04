<%--
- form.jsp
--%>

<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form> 
	<acme:form-textbox code="sponsor.sponsorship.form.label.ticker" path="ticker" readonly="${!draftMode}"/>
	<acme:form-textbox code="sponsor.sponsorship.form.label.name" path="name" readonly="${!draftMode}" />
	<acme:form-textarea code="sponsor.sponsorship.form.label.description" path="description" readonly="${!draftMode}"/>
	<acme:form-moment code="sponsor.sponsorship.form.label.startMoment" path="startMoment" readonly="${!draftMode}"/>
	<acme:form-moment code="sponsor.sponsorship.form.label.endMoment" path="endMoment" readonly="${!draftMode}"/>
	<acme:form-url code="sponsor.sponsorship.form.label.moreInfo" path="moreInfo" readonly="${!draftMode}"/>
	<acme:form-double code="sponsor.sponsorship.form.label.monthsActive" path="monthsActive" readonly="true"/>
	<acme:form-money code="sponsor.sponsorship.form.label.totalMoney" path="totalMoney" readonly="true"/>
	<acme:form-select code="sponsor.sponsorship.form.label.project" path="project" choices="${project}" readonly="${draftMode}"/>
	
	<jstl:choose>	 
		<jstl:when test="${acme:anyOf(_command, 'show|link') && draftMode == false}">
			<acme:button code="sponsor.sponsorship.form.button.donations" action="/sponsor/donation/list?sponsorshipId=${id}"/>			
			<acme:submit code="sponsor.sponsorship.form.button.link" action="/sponsor/sponsorship/link"/>		
		</jstl:when>
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete|publish') && draftMode == true}">
			<acme:button code="sponsor.sponsorship.form.button.donations" action="/sponsor/donation/list?sponsorshipId=${id}"/>
			<acme:submit code="sponsor.sponsorship.form.button.update" action="/sponsor/sponsorship/update"/>
			<acme:submit code="sponsor.sponsorship.form.button.delete" action="/sponsor/sponsorship/delete"/>
			<acme:submit code="sponsor.sponsorship.form.button.publish" action="/sponsor/sponsorship/publish"/>
		</jstl:when>
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="sponsor.sponsorship.form.button.create" action="/sponsor/sponsorship/create"/>
		</jstl:when>		
	</jstl:choose>
</acme:form>