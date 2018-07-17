<%@include file="../../init.jsp"%>


<c:if test="${activeTab eq 'Understand' }">
	<jsp:include page="./understand/understand.jsp" />
</c:if>
<c:if test="${activeTab eq 'Converge' }">
	<jsp:include page="./converge/converge.jsp" />
</c:if>
<c:if test="${activeTab eq 'Prototype' }">
	<jsp:include page="./prototype/prototype.jsp" />
</c:if>
<c:if test="${activeTab eq 'Validate' }">
	<jsp:include page="./validate/validate.jsp" />
</c:if>
<c:if test="${activeTab eq 'Diverge' }">
	<jsp:include page="./diverge/diverge.jsp" />
</c:if>
<c:if test="${activeTab eq 'Innospective' }">
	<jsp:include page="./innospective/innospective.jsp" />
</c:if>





