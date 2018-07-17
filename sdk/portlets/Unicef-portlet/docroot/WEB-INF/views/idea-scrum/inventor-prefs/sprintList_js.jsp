<%@page import="com.unicef.util.SprintConstant"%>
<%@include file="../../init.jsp"%>
<portlet:resourceURL var="getSprintDataURL" id="getSprintDataURL">
<portlet:param name="ajax" value="getSprint"/>
</portlet:resourceURL>

<portlet:resourceURL var="moveSprintDataURL" id="moveSptintDataURL">
	<portlet:param name="ajax" value="moveSprint"/>
</portlet:resourceURL>

<%-- //<portlet:resourceURL var="deleteSprintURL" id="deleteSprintURL"/> --%>
<portlet:renderURL var="saveAllSprintsURL">
	<portlet:param name="ideaId" value="${ideaId}"/>
	<portlet:param name="ideaScrumId" value="${ideaScrumId}"/>
	<portlet:param name="tab" value="inventorPrefs"/>
	<portlet:param name="view" value="saveSprint"/>
</portlet:renderURL>

<script type="text/javascript">
$('.fancybox').fancybox();
var ideaId = "${ideaId}";
var ideaScrumId = "${ideaScrumId}";
var orderType="ASC";
var selectedFieldName="orderIndex";
var globalPage = 1;
sprintPaging(globalPage,selectedFieldName,orderType);
if("${noOfSprints}" > 7){
	$("#pagination").pagination({
		items : "${noOfSprints}",
		itemsOnPage : 7,
		onPageClick : function(pageNumber, event) {
			sprintPaging(pageNumber,selectedFieldName,orderType);
		},
	});	
}
function sprintPaging(pageNumber,selectedFieldName,selectedType) {
	$('#loadingDiv').show();
	$('#container').hide();
	$('#<portlet:namespace/>sortSpanStarted').removeClass();
	  
	$.ajax({
		   url :"<%=getSprintDataURL.toString()%>",
				data : {
					fieldName : selectedFieldName,
					orderBy : selectedType,
					pageNo : pageNumber,
					recordsPerPage : 7,
					ideaId : ideaId,
					ideaScrumId : ideaScrumId
				},
				success : function(data) {
					 $('#loadingDiv').hide();
					 $('#container').show();
					result = data;
					if("${noOfSprints}" >= 1){
				       if(selectedFieldName == "startDate" || selectedFieldName == "orderIndex"){
				         if(selectedType == "ASC"){
				          $('#<portlet:namespace/>sortSpanStarted').addClass('np-sort-asc cs-block2');
				         }else{
				          $('#<portlet:namespace/>sortSpanStarted').addClass('np-sort-desc cs-block2');
				         }
				       }
					}
					document.getElementById("sprintTable").innerHTML = result;
						$(".cg-up,.cg-down").click(function(){
							var row = $(this).parents("tr:first");
							var rowId = $(row).attr('id');
							var arrowType = "";
							if($(this).is(".cg-up")){
								arrowType = "UP";
							}	else{
								arrowType = "DOWN";
							}
							$.ajax({
								   url :"<%=moveSprintDataURL.toString()%>",
										data : {
											rowId : rowId,
											ideaId : ideaId,
											ideaScrumId : ideaScrumId,
											arrowType:arrowType
										},
										success : function(data) {
											sprintPaging(pageNumber,selectedFieldName,selectedType);
											if ($(this).is(".cg-up")) {
												row.insertBefore(row.prev());
											} else {
												row.insertAfter(row.next());
											}
										},
										async: false
								});
							
						});
				}
			});
	 globalPage = parseInt(pageNumber);
}
var order="DESC";
var lastSorting="";
var checkBoolean = true;
<portlet:namespace/>sprintSortingOnHeader = function(fieldName) {
	$('#loadingDiv').show();
 	$('#container').hide();
 	if(lastSorting != fieldName && order == "DESC" && checkBoolean){
	  if(order="DESC"){
	   order="ASC";
	  }else{
	   order="DESC";
	  }
 	}else if(lastSorting != fieldName && order == "ASC" && checkBoolean){
	  if(order="ASC"){
	   order="DESC";
	  }else{
	   order="ASC";
	  }
  		checkBoolean = false;
 	}else{
	  if(order == "DESC"){
	   order="ASC";
	  }else{
	   order="DESC";
	  }
 }
 	lastSorting=fieldName;
 
 	sprintPaging(globalPage,fieldName,order);
 	orderType=order;
 	selectedFieldName=fieldName;
}
	
<portlet:namespace />sprintForm = function(url){
	window.location=url;
}
<%-- <portlet:namespace/>deleteSprint = function(id){
		
		 var result=confirm("<liferay-ui:message key='sprint-delete-confirmation' />");
		 if(result){
		   $.ajax({
		         url:"<%=deleteSprintURL%>&sprintId="+id,
		         type: 'POST',
		         success: function(data){
		        	 sprintPaging(globalPage,selectedFieldName,orderType);
		             $('#deleteSprint').html('<liferay-ui:message key="sprint-deleted-successfully" />').show();
		         }
		     });
		   }
	}


<portlet:namespace/>saveAllSprints = function(ideaId, ideaScrumId){
		
} --%>

</script>