<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="order" tagdir="/WEB-INF/tags/addons/daimlerspmb2bacceleratoraddon/responsive/order" %>

<div>
	<c:if test="${not empty orderData.b2bPermissionResult}">
	<order:orderApprovalDetailsItem order="${orderData}"/>
	</c:if>
</div>
