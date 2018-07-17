<%@include file="../../init.jsp"%>

<portlet:resourceURL var="getSprintDataURL" id="getSprintDataURL">
<portlet:param name="ajax" value="getSprint"/>
</portlet:resourceURL>

<portlet:resourceURL var="moveSprintDataURL" id="moveSptintDataURL">
	<portlet:param name="ajax" value="moveSprint"/>
</portlet:resourceURL>

<script type="text/javascript">
var solutionId = "${solutionId}";
var solutionScrumId = "${solutionScrumId}";
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
					solutionId : solutionId,
					solutionScrumId : solutionScrumId
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
											solutionId : solutionId,
											solutionScrumId : solutionScrumId,
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
</script>