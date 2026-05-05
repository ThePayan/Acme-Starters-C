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

<acme:form readonly="${readonly}">
	<acme:form-textbox code="administrator.advertisement.form.label.slogan" path="slogan"/>	
	<acme:form-textbox code="administrator.advertisement.form.label.picture" path="picture"/>
	<acme:form-textbox code="administrator.advertisement.form.label.target" path="target"/>
	
	<jstl:if test="${!readonly}">
		<acme:form-checkbox code="administrator.advertisement.form.label.confirmation" path="confirmation"/>
		<acme:submit code="administrator.advertisement.form.button.create" action="/administrator/advertisement/create"/>
	</jstl:if>
</acme:form>
