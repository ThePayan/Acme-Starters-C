<%--
- list.jsp
--%>

<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
	<acme:list-column code="sponsor.sponsorship.list.label.ticker" path="ticker" width="20%"/>
	<acme:list-column code="sponsor.sponsorship.list.label.name" path="name" width="20%"/>
	<acme:list-column code="sponsor.sponsorship.list.label.startMoment" path="startMoment" width="10%"/>
	<acme:list-column code="sponsor.sponsorship.list.label.endMoment" path="endMoment" width="10%"/>
	<acme:list-column code="sponsor.sponsorship.list.label.description" path="description" width="20%"/>
	<acme:list-column code="sponsor.sponsorship.list.label.moreInfo" path="moreInfo" width="20%"/>
</acme:list>

<acme:button code="sponsor.sponsorship.list.button.create" action="/sponsor/sponsorship/create"/>