
<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:form-textbox code="member.invention.form.label.ticker" path="ticker"/>
	<acme:form-textbox code="member.invention.form.label.name" path="name"/>
	<acme:form-moment code="member.invention.form.label.startMoment" path="startMoment"/>
	<acme:form-moment code="member.invention.form.label.endMoment" path="endMoment"/>
	<acme:form-url code="member.invention.form.label.moreInfo" path="moreInfo"/>
	<acme:form-textarea code="member.invention.form.label.description" path="description"/>
	<acme:form-double code="member.invention.form.label.monthsActive" path="monthsActive"/>
	<acme:form-integer code="member.invention.form.label.Costs" path="Costs"/>
</acme:form>