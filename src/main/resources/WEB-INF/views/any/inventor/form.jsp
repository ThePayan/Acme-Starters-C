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
	<acme:form-textbox	code="any.inventor.form.label.userName" path="userName"/>
	<acme:form-textbox 	code="any.inventor.form.label.bio" path="bio"/>
	<acme:form-textbox 	code="any.inventor.form.label.keyWords" path="keyWords"/>
	<acme:form-checkbox	code="any.inventor.form.label.licensede" path="licensed"/>
</acme:form>
