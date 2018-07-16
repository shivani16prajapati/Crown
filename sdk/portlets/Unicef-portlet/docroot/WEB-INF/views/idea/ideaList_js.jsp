<%@page import="com.unicef.util.IdeaConstant"%>
<%@include file="../init.jsp"%>
<portlet:resourceURL var="getIdeaDataURL" id="getIdeaDataURL">
    <portlet:param name="ajax" value="getIdea"/>
</portlet:resourceURL>

<portlet:resourceURL var="deleteIdeaURL" id="deleteIdeaURL"/>

<script type="text/javascript">
var orderType="DESC";
var selectedFieldName="Last Updated";
var globalPage = 1;
ideaPaging(globalPage,selectedFieldName,orderType);
if("${noOfIdeas}" > 7){
	$("#pagination").pagination({
		items : "${noOfIdeas}",
		itemsOnPage : 7,
		onPageClick : function(pageNumber, event) {
			ideaPaging(pageNumber,selectedFieldName,orderType);
		},
	});	
}
function ideaPaging(pageNumber,selectedFieldName,selectedType) {
	$('#loadingDiv').show();
	$('#container').hide();
	$('#deleteIdea').hide();
	
	$('#<portlet:namespace/>sortSpanIdea').removeClass();
	$('#<portlet:namespace/>sortSpanCreated').removeClass();
	$('#<portlet:namespace/>sortSpanUpdated').removeClass();
	$('#<portlet:namespace/>sortSpanType').removeClass();
	$('#<portlet:namespace/>sortSpanVote').removeClass();
	$('#<portlet:namespace/>sortSpanHotness').removeClass();
	
	  
	$.ajax({
		   url :"<%=getIdeaDataURL.toString()%>",
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
					if("${noOfIdeas}" >= 1){
					       if(selectedFieldName == "Idea"){
					         if(selectedType == "ASC"){
					           $('#<portlet:namespace/>sortSpanIdea').addClass('np-sort-asc cs-block2');
					         }else{
					           $('#<portlet:namespace/>sortSpanIdea').addClass('np-sort-desc cs-block2');
					         }
					         $('#<portlet:namespace/>sortSpanCreated').addClass('np-sort-both cs-block2');
					         $('#<portlet:namespace/>sortSpanUpdated').addClass('np-sort-both cs-block2');
					         $('#<portlet:namespace/>sortSpanType').addClass('np-sort-both cs-block2');
					         $('#<portlet:namespace/>sortSpanVote').addClass('np-sort-both cs-block2');
					         $('#<portlet:namespace/>sortSpanHotness').addClass('np-sort-both cs-block2');
					       }else if(selectedFieldName == "Created"){
					         if(selectedType == "ASC"){
					          $('#<portlet:namespace/>sortSpanCreated').addClass('np-sort-asc cs-block2');
					         }else{
					          $('#<portlet:namespace/>sortSpanCreated').addClass('np-sort-desc cs-block2');
					         }
					        $('#<portlet:namespace/>sortSpanUpdated').addClass('np-sort-both cs-block2');
					        $('#<portlet:namespace/>sortSpanType').addClass('np-sort-both cs-block2');
					        $('#<portlet:namespace/>sortSpanIdea').addClass('np-sort-both cs-block2');
					        $('#<portlet:namespace/>sortSpanVote').addClass('np-sort-both cs-block2');
					        $('#<portlet:namespace/>sortSpanHotness').addClass('np-sort-both cs-block2');
					       }else if(selectedFieldName == "Last Updated"){
					        if(selectedType == "ASC"){
					         $('#<portlet:namespace/>sortSpanUpdated').addClass('np-sort-asc cs-block2');
					        }else{
					         $('#<portlet:namespace/>sortSpanUpdated').addClass('np-sort-desc cs-block2');
					        }
					        $('#<portlet:namespace/>sortSpanType').addClass('np-sort-both cs-block2');
					        $('#<portlet:namespace/>sortSpanIdea').addClass('np-sort-both cs-block2');
					        $('#<portlet:namespace/>sortSpanCreated').addClass('np-sort-both cs-block2');
					        $('#<portlet:namespace/>sortSpanVote').addClass('np-sort-both cs-block2');
					        $('#<portlet:namespace/>sortSpanHotness').addClass('np-sort-both cs-block2');
					       }else if(selectedFieldName == "UpVote"){
					    	   if(selectedType == "ASC"){
							         $('#<portlet:namespace/>sortSpanVote').addClass('np-sort-asc cs-block2');
							        }else{
							         $('#<portlet:namespace/>sortSpanVote').addClass('np-sort-desc cs-block2');
							        }
					    	   $('#<portlet:namespace/>sortSpanIdea').addClass('np-sort-both cs-block2');
						        $('#<portlet:namespace/>sortSpanCreated').addClass('np-sort-both cs-block2');
						        $('#<portlet:namespace/>sortSpanUpdated').addClass('np-sort-both cs-block2');
						        $('#<portlet:namespace/>sortSpanType').addClass('np-sort-both cs-block2');
						        $('#<portlet:namespace/>sortSpanHotness').addClass('np-sort-both cs-block2');
					       }else if(selectedFieldName == "Hotness"){
					    	   if(selectedType == "ASC"){
							         $('#<portlet:namespace/>sortSpanHotness').addClass('np-sort-asc cs-block2');
							        }else{
							         $('#<portlet:namespace/>sortSpanHotness').addClass('np-sort-desc cs-block2');
							        }
					    	   $('#<portlet:namespace/>sortSpanIdea').addClass('np-sort-both cs-block2');
						        $('#<portlet:namespace/>sortSpanCreated').addClass('np-sort-both cs-block2');
						        $('#<portlet:namespace/>sortSpanUpdated').addClass('np-sort-both cs-block2');
						        $('#<portlet:namespace/>sortSpanType').addClass('np-sort-both cs-block2');
						        $('#<portlet:namespace/>sortSpanVote').addClass('np-sort-both cs-block2');
					       }else{
					        if(selectedType == "ASC"){
					         $('#<portlet:namespace/>sortSpanType').addClass('np-sort-asc cs-block2');
					        }else{
					         $('#<portlet:namespace/>sortSpanType').addClass('np-sort-desc cs-block2');
					        }
					        $('#<portlet:namespace/>sortSpanIdea').addClass('np-sort-both cs-block2');
					        $('#<portlet:namespace/>sortSpanCreated').addClass('np-sort-both cs-block2');
					        $('#<portlet:namespace/>sortSpanUpdated').addClass('np-sort-both cs-block2');
					        $('#<portlet:namespace/>sortSpanVote').addClass('np-sort-both cs-block2');
					        $('#<portlet:namespace/>sortSpanHotness').addClass('np-sort-both cs-block2');
					       }
					     }
					document.getElementById("ideaTable").innerHTML = result;
				}
			});
	 globalPage = parseInt(pageNumber);
}

var order="DESC";
var lastSorting="";
var checkBoolean = true;
<portlet:namespace/>ideaSortingOnHeader = function(fieldName) {
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
 
 	ideaPaging(globalPage,fieldName,order);
 	orderType=order;
 	selectedFieldName=fieldName;
}
	
<portlet:namespace />ideaForm = function(url){
	window.location=url;
}
<portlet:namespace />workflowEntry = function(url){
	window.location=url;
}
<portlet:namespace/>deleteIdea = function(id){
	 $('#deleteIdea').hide();
	 var result=confirm("<liferay-ui:message key='idea-delete-confirmation' />");
	 if(result){
	   $.ajax({
	         url:"<%=deleteIdeaURL%>&ideaId="+id,
	         type: 'POST',
	         success: function(data){
	        	 ideaPaging(globalPage,selectedFieldName,orderType);
	             $('#deleteIdea').html('<liferay-ui:message key="idea-deleted-successfully" />').show();
	         }
	     });
	   }
	 }
</script>