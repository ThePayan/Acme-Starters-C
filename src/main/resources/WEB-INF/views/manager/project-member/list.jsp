<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
	<acme:list-column code="manager.project-member.list.label.username" path="member.userAccount.username" width="50%"/>
	<acme:list-column code="manager.project-member.list.label.role" path="role" width="50%"/>
</acme:list>
<jstl:if test="${draftMode}">
	<acme:button code="manager.project-member.list.button.create" action="/manager/project-member/create?projectId=${projectId}"/>
</jstl:if>