<%@include file="../init.jsp"%>

<portlet:resourceURL var="getVideoDataURL" id="getVideoDataURL">
	<portlet:param name="ajax" value="getVideo"/> 
</portlet:resourceURL>

<portlet:resourceURL var="deleteVideoURL" id="deleteVideoURL"/>

<script type="text/javascript">
var orderType="DESC";
var selectedFieldName="Expire Date";
var globalPage = 1;
videoPaging(globalPage,"FeedOrder","ASC");
if("${noOfVideo}" > 7){
	$("#pagination").pagination({
		items : "${noOfVideo}",
		itemsOnPage : 7,
		onPageClick : function(pageNumber, event) {
			videoPaging(pageNumber,"FeedOrder","ASC");
		},
	});	
}

/* <portlet:namespace />videoForm = function(url){
	window.location=url;
}
<portlet:namespace />workflow = function(url){
	window.location=url;
} */

function videoPaging(pageNumber,selectedFieldName,selectedType) {
	$('#loadingDiv').show();
	$('#container').hide();
	$('#deleteVideo').hide();
	
	$('#<portlet:namespace/>sortSpanVideo').removeClass();
    $('#<portlet:namespace/>sortSpanExpireDate').removeClass();
  	$('#<portlet:namespace/>sortSpanFeedOrder').removeClass();
  	
	
  	$.ajax({
		   url :"<%=getVideoDataURL.toString()%>",
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
					 if("${noOfVideo}" >= 1){
					      if(selectedFieldName == "Video"){
							        if(selectedType == "ASC"){
							          $('#<portlet:namespace/>sortSpanVideo').addClass('np-sort-asc cs-block2');
							        }else{
							          $('#<portlet:namespace/>sortSpanVideo').addClass('np-sort-desc cs-block2');
							        }
						        $('#<portlet:namespace/>sortSpanExpireDate').addClass('np-sort-both cs-block2');
						        $('#<portlet:namespace/>sortSpanFeedOrder').addClass('np-sort-both cs-block2');
						        
					     }else if(selectedFieldName == "ExpireDate"){
							        if(selectedType == "ASC"){
							         $('#<portlet:namespace/>sortSpanExpireDate').addClass('np-sort-asc cs-block2');
							        }else{
							         $('#<portlet:namespace/>sortSpanExpireDate').addClass('np-sort-desc cs-block2');
							        }
						       $('#<portlet:namespace/>sortSpanFeedOrder').addClass('np-sort-both cs-block2');
						       $('#<portlet:namespace/>sortSpanVideo').addClass('np-sort-both cs-block2');
						       
					      }else{
							       if(selectedType == "ASC"){
							        $('#<portlet:namespace/>sortSpanFeedOrder').addClass('np-sort-asc cs-block2');
							       }else{
							        $('#<portlet:namespace/>sortSpanFeedOrder').addClass('np-sort-desc cs-block2');
							       }
						       $('#<portlet:namespace/>sortSpanVideo').addClass('np-sort-both cs-block2');
						       $('#<portlet:namespace/>sortSpanExpireDate').addClass('np-sort-both cs-block2');
							}
					 }
					document.getElementById("VideoTable").innerHTML = result;
				}
			});
	 globalPage = parseInt(pageNumber);
}

var order="DESC";
var lastSorting="";
var checkBoolean = true;
<portlet:namespace/>videoSortOnHeader = function(fieldName) {
 	$('#loadingDiv').show();
 	$('#container').hide();
 	$('#deleteVideo').hide();
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
 	videoPaging(globalPage,fieldName,order);
 	orderType=order;
 	selectedFieldName=fieldName;
}

<portlet:namespace/>deleteVideo = function(id){
	 $('#deleteVideo').hide();
	 var result=confirm("<liferay-ui:message key='video-delete-confirmation' />");
	 if(result){
	   $.ajax({
	         url:"<%=deleteVideoURL%>&videoId="+id,
	         type: 'POST',
	         success: function(data){
	        	 videoPaging(globalPage,selectedFieldName,orderType);
	             $('#deleteVideo').html('<liferay-ui:message key="video-deleted-successfully" />').show();
	         }
	     });
	   }
	 }
</script>