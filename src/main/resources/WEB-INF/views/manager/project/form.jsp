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
	<acme:form-textbox code="manager.project.form.label.title" path="title"/>
	<acme:form-textarea code="manager.project.form.label.description" path="description"/>
	<acme:form-textarea code="manager.project.form.label.keyWords" path="keyWords"/>
	<acme:form-moment code="manager.project.form.label.kickOff" path="kickOff"/>
	<acme:form-moment code="manager.project.form.label.closeOut" path="closeOut"/>
	<acme:form-double code="manager.project.form.label.personMonths" path="personMonths" readonly="true"/>

</acme:form>
