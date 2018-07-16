<%@include file="../init.jsp"%>


<div class="ust-cs-box01 clearfix">
  <div class="ust-cs-img-box"><img src="<%=request.getContextPath()%>/images/coming-soon.png"></div>
  <div class="ust-cs-txt-box">
    <div class="ust-cs-txt001"> <span>Content Under Development</span> 
    <c:if test="${pageName eq 'launchCahllenge'}">
		<span>
	    	Launch A Challenge provides the iTeam the ability to launch and manage contests and challenges aimed at stimulating idea generation.
	    </span>
	</c:if>
	<c:if test="${pageName eq 'knowledgeRepository'}">
		<span>
	    	Knowledge Repository provides any user access to content required to develop and refine great ideas.
	    </span>
	</c:if>
	<c:if test="${pageName eq 'usThinkBlog'}">
		<span>
	    	USThink Blog provides any user access to content developed by other users to help inform or explain market segments, ideas or any other subject linked to idea creation.
	    </span>
	</c:if>
	<c:if test="${pageName eq 'patentablityTest'}">
		<span>
	    	Patentability Test asks a series of questions to help any user determine the potential patentability of an idea or of the underlying technology required to deliver the idea.
	    </span>
	</c:if>
	<c:if test="${pageName eq 'training'}">
		<span>
	    	Training provides any user access to training materials including specially designed tutorial videos, how to guides, etc. Each user’s progression through the curriculum for each module will be tracked and reports generated to the user and management.
	    </span>
	</c:if>
	<c:if test="${pageName eq 'innoMail'}">
		<span>
	    	InnoMail provides all users with the messaging they require to communicate with their idea teams.
	    </span>
	</c:if>
	<c:if test="${pageName eq 'connections'}">
		<span>
	    	Connections provides any user access to all other users to form social relationships within UST Global and provide private messaging capabilities.
	    </span>
	</c:if>
	<c:if test="${pageName eq 'commandCenter'}">
		<span>
	    	Command Center provides iTeam Members and Executive Management the ability to see at a glance the status of their innovation investments. This functionality will include the ability to display some or all of the data on different types of display systems to accommodate Board Meetings, Executive Meetings, Customer Meetings, the Lobby, etc.
	    </span>
	</c:if>
	<c:if test="${pageName eq 'pivotManager'}">
		<span>
	    	Pivot Manager provides iTeam Members and Executive Management the ability to make decisions about the portfolio of innovation investments.
	    </span>
	</c:if>
	<c:if test="${pageName eq 'agileInnovation'}">
		<span>
	    	Provides background information on the Agile Innovation process along with tutorials.
	    </span>
	</c:if>
	<c:if test="${pageName eq 'selfAssesment'}">
		<span>
	    	Self Assessment provides any user the ability to say how well they are doing with the Agile Innovation Process.
	    </span>
	</c:if>
	<c:if test="${pageName eq 'pipelineManager'}">
		<span>
	    	Pipeline Manager provides iTeam Members and Executive Management the ability to see the current funnel of ideas.
	    </span>
	</c:if>
   </div>
  </div>
</div>

<script>
   $(document).ready(function(){
	   
	   $('#wrapper').css('min-height', $( document ).height()-215);
	   
   })
   
   

</script>















