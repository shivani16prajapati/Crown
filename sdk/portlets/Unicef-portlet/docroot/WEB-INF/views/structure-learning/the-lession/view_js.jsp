<%@include file="../../init.jsp"%>


<script type="text/javascript">
if("${historyListSize}" > 1 ){
	  var slider = $("#slider").slider({
	   min: 1,
	      max: "${historyListSize}",
	      range: "min",
	   value : "${historyListSize}"
	  });
	 }
    
	$("#slider").on( "slidechange", function( event, ui ) {
		$('#'+ui.value).removeClass("div-off");
		$('#'+ui.value).addClass("div-on");
	} );
	
	$("#slider").on( "slidechange", function( event, ui ) {
        $('.unicef-slide').removeClass("div-on");
		$('.unicef-slide').addClass("div-off");
		$('#slider_'+ui.value).addClass("div-on");
	} );



</script>