<%@include file="../init.jsp"%>

<portlet:resourceURL var="getSolutionDataURL" id="getSolutionDataURL">
<portlet:param name="ajax" value="geSolution"/>
</portlet:resourceURL>

<portlet:resourceURL var="deleteSolutionURL" id="deleteSolutionURL"/>

<script type="text/javascript">
var orderType="DESC";
var selectedFieldName="Last Updated";
var globalPage = 1;
solutionPaging(globalPage,selectedFieldName,orderType);
if("${noOfSolution}" > 7){
	$("#pagination").pagination({
		items : "${noOfSolution}",
		itemsOnPage : 7,
		onPageClick : function(pageNumber, event) {
			solutionPaging(pageNumber,selectedFieldName,orderType);
		},
	});	
}

<portlet:namespace />solutionForm = function(url){
	window.location=url;
}
<portlet:namespace />workflow = function(url){
	window.location=url;
}
function solutionPaging(pageNumber,selectedFieldName,selectedType) {
	$('#loadingDiv').show();
	$('#container').hide();
	$('#deleteIdea').hide();
	
	$('#<portlet:namespace/>sortSpanRequest').removeClass();
    $('#<portlet:namespace/>sortSpanCreated').removeClass();
  	$('#<portlet:namespace/>sortSpanUpdated').removeClass();
  	$('#<portlet:namespace/>sortSpanSpace').removeClass();
  	$('#<portlet:namespace/>sortSpanUpVote').removeClass();
	
  	$.ajax({
		   url :"<%=getSolutionDataURL.toString()%>",
				data : {
					fieldName : selectedFieldName,
					orderBy : selectedType,
					pageNo : pageNumber,
					recordsPerPage : 7,
				},
				success : function(data) {
					 $('#loadingDiv').hide();
					 $('#container').show();
					 result = data;
					 if("${noOfSolution}" >= 1){
					      if(selectedFieldName == "Request"){
							        if(selectedType == "ASC"){
							          $('#<portlet:namespace/>sortSpanRequest').addClass('np-sort-asc cs-block2');
							        }else{
							          $('#<portlet:namespace/>sortSpanRequest').addClass('np-sort-desc cs-block2');
							        }
						        $('#<portlet:namespace/>sortSpanCreated').addClass('np-sort-both cs-block2');
						        $('#<portlet:namespace/>sortSpanUpdated').addClass('np-sort-both cs-block2');
						        $('#<portlet:namespace/>sortSpanSpace').addClass('np-sort-both cs-block2');
						        $('#<portlet:namespace/>sortSpanUpVote').addClass('np-sort-both cs-block2');
					     }else if(selectedFieldName == "Created"){
							        if(selectedType == "ASC"){
							         $('#<portlet:namespace/>sortSpanCreated').addClass('np-sort-asc cs-block2');
							        }else{
							         $('#<portlet:namespace/>sortSpanCreated').addClass('np-sort-desc cs-block2');
							        }
						       $('#<portlet:namespace/>sortSpanUpdated').addClass('np-sort-both cs-block2');
						       $('#<portlet:namespace/>sortSpanSpace').addClass('np-sort-both cs-block2');
						       $('#<portlet:namespace/>sortSpanRequest').addClass('np-sort-both cs-block2');
						       $('#<portlet:namespace/>sortSpanUpVote').addClass('np-sort-both cs-block2');
					      }else if(selectedFieldName == "Last Updated"){
							       if(selectedType == "ASC"){
							        $('#<portlet:namespace/>sortSpanUpdated').addClass('np-sort-asc cs-block2');
							       }else{
							        $('#<portlet:namespace/>sortSpanUpdated').addClass('np-sort-desc cs-block2');
							       }
						       $('#<portlet:namespace/>sortSpanSpace').addClass('np-sort-both cs-block2');
						       $('#<portlet:namespace/>sortSpanRequest').addClass('np-sort-both cs-block2');
						       $('#<portlet:namespace/>sortSpanCreated').addClass('np-sort-both cs-block2');
						       $('#<portlet:namespace/>sortSpanUpVote').addClass('np-sort-both cs-block2');
					      }else if(selectedFieldName == "UpVote"){
					    	       if(selectedType == "ASC"){
							        $('#<portlet:namespace/>sortSpanUpVote').addClass('np-sort-asc cs-block2');
							       }else{
							        $('#<portlet:namespace/>sortSpanUpVote').addClass('np-sort-desc cs-block2');
							       }
					    	   $('#<portlet:namespace/>sortSpanSpace').addClass('np-sort-both cs-block2');
						       $('#<portlet:namespace/>sortSpanRequest').addClass('np-sort-both cs-block2');
						       $('#<portlet:namespace/>sortSpanCreated').addClass('np-sort-both cs-block2');
						       $('#<portlet:namespace/>sortSpanUpdated').addClass('np-sort-both cs-block2');
					      }else{
							       if(selectedType == "ASC"){
							        $('#<portlet:namespace/>sortSpanSpace').addClass('np-sort-asc cs-block2');
							       }else{
							        $('#<portlet:namespace/>sortSpanSpace').addClass('np-sort-desc cs-block2');
							       }
						       $('#<portlet:namespace/>sortSpanRequest').addClass('np-sort-both cs-block2');
						       $('#<portlet:namespace/>sortSpanCreated').addClass('np-sort-both cs-block2');
						       $('#<portlet:namespace/>sortSpanUpdated').addClass('np-sort-both cs-block2');
						       $('#<portlet:namespace/>sortSpanUpVote').addClass('np-sort-both cs-block2');
					      }
					    }
					document.getElementById("SolutionTable").innerHTML = result;
				}
			});
	 globalPage = parseInt(pageNumber);
}

var order="DESC";
var lastSorting="";
var checkBoolean = true;
<portlet:namespace/>solutionSortOnHeader = function(fieldName) {
 	$('#loadingDiv').show();
 	$('#container').hide();
 	$('#deleteIdea').hide();
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
 	solutionPaging(globalPage,fieldName,order);
 	orderType=order;
 	selectedFieldName=fieldName;
}

<portlet:namespace/>deleteSolution = function(id){
	 $('#deleteIdea').hide();
	 var result=confirm("<liferay-ui:message key='solution-delete-confirmation' />");
	 if(result){
	   $.ajax({
	         url:"<%=deleteSolutionURL%>&solutionId="+id,
	         type: 'POST',
	         success: function(data){
	        	 solutionPaging(globalPage,selectedFieldName,orderType);
	             $('#deleteIdea').html('<liferay-ui:message key="solution-deleted-successfully" />').show();
	         }
	     });
	   }
	 }
</script>