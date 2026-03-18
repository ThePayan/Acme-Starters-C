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
	<acme:form-textbox code="any.invention.form.label.ticker" path="ticker"/>
	<acme:form-textbox code="any.invention.form.label.name" path="name"/>
	<acme:form-moment code="any.invention.form.label.startMoment" path="startMoment"/>
	<acme:form-moment code="any.invention.form.label.endMoment" path="endMoment"/>
	<acme:form-url code="any.invention.form.label.moreInfo" path="moreInfo"/>
	<acme:form-textarea code="any.invention.form.label.description" path="description"/>
	<acme:form-double code="any.invention.form.label.monthsActive" path="monthsActive"/>
	<acme:form-integer code="any.invention.form.label.Costs" path="Costs"/>
	
	<acme:button code="any.invention.form.button.parts" action="/any/part/list?inventionId=${id}"/>
	<acme:button code="any.invention.form.button.inventor" action="/any/inventor/show?inventorId=${inventorid}"/>
</acme:form>