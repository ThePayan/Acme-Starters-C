<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
	<acme:list-column code="member.project.list.label.title" path="title" width="60%"/>
	<acme:list-column code="member.project.list.label.description" path="description" width="20%"/>
	<acme:list-column code="member.project.list.label.keyWords" path="keyWords" width="20%"/>
	<acme:list-column code="member.project.list.label.kickOff" path="kickOff" width="20%"/>
	<acme:list-column code="member.project.list.label.closeOut" path="closeOut" width="20%"/>
</acme:list>

