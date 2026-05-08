<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	
<jstl:choose>	
	<jstl:when test="${acme:anyOf(_command, 'show|delete')}">
		<acme:form-textbox 	code="manager.project-member.form.label.username" path="member.userAccount.username" readonly ="true"/>
		<acme:form-textbox	code="manager.project-member.form.label.fullName" path="member.identity.fullName"  readonly ="true"/>
		<acme:form-textbox	code="manager.project-member.form.label.email" path="member.identity.email"  readonly ="true"/>
		<acme:form-textbox 	code="manager.project-member.form.label.role" path="role" readonly ="true"/>
		<jstl:if test= "${draftMode}">
			<acme:submit code="manager.project-member.form.button.delete" action="/manager/project-member/delete?id=${id}"/>
		</jstl:if>
	</jstl:when>
	<jstl:when test="${acme:anyOf(_command, 'create')}">
		<acme:form-select 	code="manager.project-member.form.label.member" path="member" choices="${members}"/>
		<acme:form-select 	code="manager.project-member.form.label.role" path="role" choices="${roles}"/>
		<acme:submit code="manager.project-member.form.button.create" action="/manager/project-member/create?projectId=${projectId}"/>
	</jstl:when>
</jstl:choose>
</acme:form>