<%--
- form.jsp
--%>

<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:form-textbox code="member.sponsorship.form.label.ticker" path="ticker"/>
	<acme:form-textbox code="member.sponsorship.form.label.name" path="name"/>
	<acme:form-textarea code="member.sponsorship.form.label.description" path="description"/>
	<acme:form-moment code="member.sponsorship.form.label.startMoment" path="startMoment"/>
	<acme:form-moment code="member.sponsorship.form.label.endMoment" path="endMoment"/>
	<acme:form-url code="member.sponsorship.form.label.moreInfo" path="moreInfo"/>
	<acme:form-double code="member.sponsorship.form.label.monthsActive" path="monthsActive"/>
	<acme:form-money code="member.sponsorship.form.label.totalMoney" path="totalMoney"/>
</acme:form>