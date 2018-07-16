
<script type="text/javascript">

$(document).ready(function() {
	
	var $checkboxes = $('#count td input[type="checkbox"]');
	var yescount = $("input#checkbox:checked").length;
	
	if(yescount>2){
		$('#answer').html('YES');
		$('#text_answer').html('Please contact the Law Department via email at patents@crowniron.com to begin evaluating whether to file a patent. If we file a patent on your invention, there\'s an award! Thanks!');
	}else{
		$('#answer').html('NO');
		$('#text_answer').html('Sorry, your idea doesn\'t rise to the level of patentability. But we thank you for your efforts and we encourage you to refine this idea and keep coming up with new ideas!');
	}

    
	$checkboxes.change(function(){
		
		var $checkboxes = $('#count td input[type="checkbox"]');
		var yescount = $("input#checkbox:checked").length;
		
		if(yescount>2){
			$('#answer').html('YES');
			$('#text_answer').html('Please contact the Law Department via email at patents@crowniron.com to begin evaluating whether to file a patent. If we file a patent on your invention, there\'s an award! Thanks!');
		}else{
			$('#answer').html('NO');
			$('#text_answer').html('Sorry, your idea doesn\'t rise to the level of patentability. But we thank you for your efforts and we encourage you to refine this idea and keep coming up with new ideas!');
		}
	});
    
});


	


</script>