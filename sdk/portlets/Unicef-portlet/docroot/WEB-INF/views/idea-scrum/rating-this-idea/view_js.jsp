<%@include file="../../init.jsp"%>

<portlet:resourceURL var="updateIdeaRateURL" id="updateIdeaRate" />
<script type="text/javascript">
window.onload = function(){
	 $(".cg-idea-rate-criteria-tooltip").tooltip({
		position: {
	        my: "center bottom-20",
	        at: "center top",
	        using: function( position, feedback ) {
	          $( this ).css( position );
	          $( "<div>" )
	            .addClass("arrow")
	            .appendTo(this);
	        }
	      }	
		});
	}

<portlet:namespace />updateIdeaRate = function(ideaId,ideaRateCriteriaId,rate, currentRateId){
	$.ajax({
		url:"<%=updateIdeaRateURL%>",
		type:'POST',
		dataType:"JSON",
		data:{
		 "ideaId":ideaId,
		 "ideaRateCriteriaId":ideaRateCriteriaId,
		 "rate":rate
		},
		success:function(result){
			if(result.status == "success"){
				$('#<portlet:namespace/>ideaRating_'+ideaRateCriteriaId).children().removeClass('active');
				$('#<portlet:namespace/>ideaRating_'+ideaRateCriteriaId).children('#'+currentRateId).addClass('active');
				//$('#'+currentRateId).addClass('active');
			}
		},
		async: false
	});
}

</script>