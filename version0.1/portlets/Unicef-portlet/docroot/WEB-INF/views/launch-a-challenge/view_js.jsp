<%@include file="../init.jsp"%>

<portlet:resourceURL var="getChallengeDataURL" id="getChallengeDataURL">
<portlet:param name="ajax" value="getChallenges"/>
</portlet:resourceURL>

<portlet:resourceURL var="deleteChallengeURL" id="deleteChallengeURL"/>

<script type="text/javascript">

var globalPage = 1;
challengePaging(globalPage);
if("${noOfChallenge}" > 7){
	$("#pagination").pagination({
		items : "${noOfIdeas}",
		itemsOnPage : 7,
		onPageClick : function(pageNumber, event) {
			challengePaging(pageNumber);
		},
	});	
}
function challengePaging(pageNumber) {
	  
	$.ajax({
		   url :"<%=getChallengeDataURL.toString()%>",
				data : {
					pageNo : pageNumber,
					recordsPerPage : 7,
				},
				success : function(data) {
					 $('#loadingDiv').hide();
					 $('#container').show();
					result = data;
					document.getElementById("challengeTable").innerHTML = result;
				}
			});
	 globalPage = parseInt(pageNumber);
}
<portlet:namespace/>deleteChallenge = function(id){
	 $('#deleteChallenge').hide();
	 var result=confirm("<liferay-ui:message key='challenge-delete-confirmation' />");
	 if(result){
		   $.ajax({
		         url:"<%=deleteChallengeURL%>&challengeId="+id,
		         type: 'POST',
		         success: function(data){
		        	 challengePaging(globalPage);
		             $('#deleteChallenge').html('<liferay-ui:message key="challenge-deleted-successfully" />').show();
		         }
		     });
		   }		
}
</script>
