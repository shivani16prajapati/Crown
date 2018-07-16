<%@include file="../init.jsp"%>
<portlet:resourceURL var="viewHomepageIdeaURL" id="viewHomepageIdea" />

<script type="text/javascript">
$(document).ready(function () {
	 displayHomePageIdeaIdea('latest');
	 
});

var ideaCount = 0;
var ideaType = 'latest';

function  displayHomePageIdeaIdea(type){
	$('#<portlet:namespace/>loadMoreImage').hide();
	$('#<portlet:namespace/>noMoreImage').hide();
	var idea = type;
	ideaType = type;
	ideaCount = 1;
	$.ajax({
	    url :"<%=viewHomepageIdeaURL%>",
	    data : {
	    'ideaCount' : ideaCount,
	    'idea' : idea
	   },
	    success : function(result) {
	    	jQuery.getScript("<%=request.getContextPath()%>/js/img-resizer.js");
	        /* if(ideaCount > 1){
	    		var oldHtml = $('#<portlet:namespace/>listOfHomePageIdea').html();
	    		$('#<portlet:namespace/>listOfHomePageIdea').html(oldHtml + result );
	    	}else{ */
	    		document.getElementById('<portlet:namespace/>listOfHomePageIdea').innerHTML = result;	
	    	/* }  */
	    	$('#<portlet:namespace/>loadMoreImage').show();
	    	if($(".ideaListSizeClass").last().val() < 9){
	    		$('#<portlet:namespace/>loadMoreImage').hide();
	    		$('#<portlet:namespace/>noMoreImage').show();
	    	}
	    	$(window).trigger('resize'); 
	   },
	   async: false
	 });
}

<portlet:namespace />viewIdeaLink = function(url){
	  window.location.href = url;
}

function loadMoreIdeas(){
	$('#<portlet:namespace/>loadMoreImage').hide();
	$('#<portlet:namespace/>noMoreImage').hide();
	var idea = ideaType;
	ideaCount = ideaCount + 1;	
	$.ajax({
	    url :"<%=viewHomepageIdeaURL%>",
	    data : {
	    'ideaCount' : ideaCount,
	    'idea' : idea
	   },
	    success : function(result) {
	    	jQuery.getScript("<%=request.getContextPath()%>/js/img-resizer.js");
	    	if(ideaCount > 1){
	    		var oldHtml = $('#<portlet:namespace/>listOfHomePageIdea').html();
	    		$('#<portlet:namespace/>listOfHomePageIdea').html(oldHtml +result);
	    	}else{
	    		document.getElementById('<portlet:namespace/>listOfHomePageIdea').innerHTML = result;	
	    	}
	    	$('#<portlet:namespace/>loadMoreImage').show();
	    	if($(".ideaListSizeClass").last().val() < 8){
	    	    $('#<portlet:namespace/>loadMoreImage').hide();
	    	    $('#<portlet:namespace/>noMoreImage').show();
	    	}
	    	$(window).trigger('resize'); 
	   },
	   async: false
	 });
}

function openvideo(url){
	var pattern1 = /(?:http?s?:\/\/)?(?:www\.)?(?:vimeo\.com)\/?(.+)/g;
	var pattern2 = /(?:http?s?:\/\/)?(?:www\.)?(?:youtube\.com|youtu\.be)\/(?:watch\?v=)?(.+)/g;
	if(pattern2.test(url)){
		var replacement = 'http://www.youtube.com/embed/$1';
		url = url.replace(pattern2, replacement);
	}
	if(pattern1.test(url)){
		var replacement = 'https://player.vimeo.com/video/$1';
		url = url.replace(pattern1,replacement);	
	} 
	$('#videomodal').iziModal('destroy');
	$("#videomodal").iziModal({
		title:'',
		headerColor: '#88A0B9',
		iconColor: '#fff',
	    iframe: true,
	    iframeURL: url,
	    fullscreen: true,
	    closeButton: true,
	});
	$("#videomodal").iziModal('open');
}
</script>
