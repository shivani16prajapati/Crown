<%@include file="../init.jsp"%>

<portlet:resourceURL var="getIdeaScrumListURL" id="getIdeaScrumList" />

<script type="text/javascript">
$(document).ready(function () {
	<portlet:namespace />getIdeaScrums();	
});

<portlet:namespace />getIdeaScrums = function(){
	$('#loadingDiv').show();
	$('#container').hide();
	$.ajax({
		url :"<%=getIdeaScrumListURL.toString()%>",
		data : {
			},
		success : function(data) {
			$('#loadingDiv').hide();
			$('#container').show();
				document.getElementById("ideaScrumTable").innerHTML = data;
			}
	});
}
</script>