<%@include file="../init.jsp"%>

<div>	
	<c:if test="${status != null }">
		<ul aria-label="Breadcrumb" class="breadcrumb">
			<li><a href="${pageURL}">${moduleName}</a></li>
				<li>${status}</li>
				<li><a href="${currentModuleURL}">${title}</a></li>
		</ul>
	</c:if>
</div>