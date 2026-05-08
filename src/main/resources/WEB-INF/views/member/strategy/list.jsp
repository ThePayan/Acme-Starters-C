
<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
	<acme:list-column code="member.strategy.list.label.ticker" path="ticker" width="20%"/>
	<acme:list-column code="member.strategy.list.label.name" path="name" width="60%"/>
	<acme:list-column code="member.strategy.list.label.description" path="description" width="20%"/>
	<acme:list-column code="member.strategy.list.label.fundraiser.bank" path="fundraiser.bank" width="20%"/>
	<acme:list-column code="member.strategy.list.label.startMoment" path="startMoment" width="20%"/>
	<acme:list-column code="member.strategy.list.label.endMoment" path="endMoment" width="20%"/>
	<acme:list-column code="member.strategy.list.label.moreInfo" path="moreInfo" width="20%"/>
</acme:list>
