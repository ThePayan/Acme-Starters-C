
<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:form-textbox code="member.campaign.form.label.ticker" path="ticker"/>
	<acme:form-textbox code="member.campaign.form.label.name" path="name"/>
	<acme:form-textbox code="member.campaign.form.label.description" path="description"/>
	<acme:form-moment code="member.campaign.form.label.startMoment" path="startMoment"/>
	<acme:form-moment code="member.campaign.form.label.endMoment" path="endMoment"/>
	<acme:form-textbox code="member.campaign.form.label.moreInfo" path="moreInfo"/>
	<acme:form-textbox code="member.campaign.form.label.monthsActive" path="monthsActive"/>
	<acme:form-textbox code="member.campaign.form.label.effort" path="efforts"/>
</acme:form>
