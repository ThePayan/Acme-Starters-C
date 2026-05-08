
<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:form-textbox code="member.strategy.form.label.ticker" path="ticker"/>
	<acme:form-textbox code="member.strategy.form.label.name" path="name"/>
	<acme:form-textarea code="member.strategy.form.label.description" path="description"/>
	<acme:form-moment code="member.strategy.form.label.startMoment" path="startMoment"/>
	<acme:form-moment code="member.strategy.form.label.endMoment" path="endMoment"/>
	<acme:form-url code="member.strategy.form.label.moreInfo" path="moreInfo"/>
	<acme:form-double code="member.strategy.form.label.monthsActive" path="monthsActive"/>
	<acme:form-integer code="member.strategy.form.label.expectedPercentages" path="expectedPercentages"/>		
</acme:form>

