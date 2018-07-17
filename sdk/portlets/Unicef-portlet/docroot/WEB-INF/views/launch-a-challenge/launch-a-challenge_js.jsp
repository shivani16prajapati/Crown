<%@include file="../init.jsp"%>

<script type="text/javascript">
$(document).ready(function () {
	 $('.basic').fancySelect();
	
	 var checkPrize = '${checkPrize}';
	 if(checkPrize ==  'true'){
	 
		 $('#<portlet:namespace />prizeDiv').show(); 
	 }
});
function <portlet:namespace/>initDescriptionckEditor(){
	return  $("#descriptionHtmlData").html();
}
<portlet:namespace/>submitForm = function(){
	var editorHTML = window.<portlet:namespace />descriptionckEditor.getHTML();
	var startDate = $('#<portlet:namespace />startDate').val();
	if(editorHTML == ''){
		$('#descriptionError').show();
		$('#challengeError').hide();
		return;
	}else if(startDate == ''){
		$('#challengeError').show();
		$('#descriptionError').hide();
		return;
	}else{
		$('#descriptionError').hide();
		$('#challengeError').hide();
	}
	$("#<portlet:namespace/>challengeContent").val(editorHTML);
	submitForm(document.<portlet:namespace />launchChallenge);
}
$('#<portlet:namespace/>startDate').datepicker({
		 
		 	dateFormat: "mm/dd/yy",
		    showAnim: 'slide',
		    changeMonth: true,
		    changeYear: true,
			onSelect: function(selectedDate) {
					$('#<portlet:namespace />endDate').val('');
					$('#challengeError').hide();
					
					 <portlet:namespace/>fillEndDatePicker(selectedDate);
			}
});
<portlet:namespace/>fillEndDatePicker = function(selectedDate){
console.log('function called');
	var diffOfDate = 7;
	var startDate = new Date(selectedDate);
	startDate.setDate(startDate.getDate()+ parseInt(diffOfDate));
	var endDate = getFormatedStringDate(startDate);
	$('#<portlet:namespace />endDate').val(endDate);
	 $("#<portlet:namespace />endDate").datepicker("option", "minDate", startDate);
}
   $("#<portlet:namespace />endDate").datepicker();
challengeEnd = function(){
$('#endDate').val('');
	$('#challengeError').hide();
	var startDate = $('#<portlet:namespace />startDate').val();
	
	if(startDate == ""){
		$('#challengeError').show();
	}else{
		var endValue = parseInt($('#<portlet:namespace />challengeEnd').val());
		
		var currentTime = new Date(startDate);
		currentTime.setDate(currentTime.getDate()+endValue);
		var newEndDate = getFormatedStringDate(currentTime);
		$('#<portlet:namespace />endDate').val(newEndDate);
	}
	
}
function getFormatedStringDate(dateTime){
 var fromdate = new Date(dateTime);
 var dd = fromdate.getDate();
    var mm = fromdate.getMonth()+1; //January is 0!
    var yyyy = fromdate.getFullYear();
    if(mm < 10) {
     mm = "0"+mm;
    }
    if(dd < 10) {
     dd = "0"+dd;
    }
	return mm+"/"+dd+"/"+yyyy;
}
function checkPrizeValue(prize){
	  if($(prize).is(':checked')){
	        $('#<portlet:namespace />prizeDiv').show();   
	  }else{
	       $('#<portlet:namespace />prizeDiv').hide();
	       $('#<portlet:namespace />prizeDescription').val(" ");
	  }
}
</script>
 <aui:script use="aui-node">
 Liferay.provide(
 window,
 '<portlet:namespace />selectUserIds',
 function() { 
  if (document.<portlet:namespace />launchChallenge.<portlet:namespace />userCategoryIds) {
   document.<portlet:namespace />launchChallenge.<portlet:namespace />userCategoryIds.value = Liferay.Util.listSelect(document.<portlet:namespace />launchChallenge.<portlet:namespace />selectedUserIds);
  }
 
 },
 ['liferay-util-list-fields']
);
 
 Liferay.after(
 'inputmoveboxes:moveItem',
 function(event) {
  if ((event.fromBox.get('id') == '<portlet:namespace />availableUserIds') || ( event.toBox.get('id') == '<portlet:namespace />availableUserIds')) {
   <portlet:namespace />selectUserIds();
  }
 }
);
<portlet:namespace />selectUserIds();
</aui:script>
