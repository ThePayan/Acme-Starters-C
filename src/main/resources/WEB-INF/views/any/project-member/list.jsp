<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list navigable="false">
	<acme:list-column code="any.project-member.list.label.member" path="member" width="25%"/>
	<acme:list-column code="any.project-member.list.label.username" path="username" width="30%"/>
	<acme:list-column code="any.project-member.list.label.email" path="email" width="30%"/>	
	<acme:list-column code="any.project-member.list.label.role" path="role" width="15%"/>
</acme:list>
