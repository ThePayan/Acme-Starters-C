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
	<acme:form-textbox code="manager.dashboard.form.label.numberOfMyProjects" path="numberOfMyProjects"/>
	<acme:form-textbox code="manager.dashboard.form.label.desviationNumberOfProjectsByManager" path="desviationNumberOfProjectsByManager"/>
	<acme:form-textarea code="manager.dashboard.form.label.minEffort" path="minEffort"/>
	<acme:form-moment code="manager.dashboard.form.label.maxEffort" path="maxEffort"/>
	<acme:form-moment code="manager.dashboard.form.label.averageOfEffortOfProjectsByManager" path="averageOfEffortOfProjectsByManager"/>
	<acme:form-url code="manager.dashboard.form.label.desviationOfTheEffortByProjectsByManager" path="desviationOfTheEffortByProjectsByManager"/>
</acme:form>
