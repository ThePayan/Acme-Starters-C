
<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
	<acme:list-column code="member.invention.list.label.ticker" path="ticker" width="20%"/>
	<acme:list-column code="member.invention.list.label.inventor.bio" path="inventor.bio" width="20%"/>
	<acme:list-column code="member.invention.list.label.startMoment" path="startMoment" width="20%"/>
	<acme:list-column code="member.invention.list.label.endMoment" path="endMoment" width="20%"/>
	<acme:list-column code="member.invention.list.label.name" path="name" width="60%"/>
	<acme:list-column code="member.invention.list.label.description" path="name" width="20%"/>
	<acme:list-column code="member.invention.list.label.moreInfo" path="moreInfo" width="20%"/>
</acme:list>