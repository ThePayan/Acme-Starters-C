<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
	<acme:list-column code="sponsor.donation.list.label.name" path="name" width="80%"/>	
	<acme:list-column code="sponsor.donation.list.label.notes" path="notes" width="20%"/>
</acme:list>

<jstl:if test="${showCreate}">
	<acme:button code="sponsor.donation.list.button.create" action="/sponsor/donation/create?sponsorshipId=${sponsorshipId}"/>
</jstl:if>