<%@include file="../init.jsp"%>
<aui:script>
$(document).ready(function() {
	 var Innovationoptions = $("#<portlet:namespace/>LinOfBusiness option");
	 Innovationoptions.sort(function(a,b){
		    a = a.value;
		    b = b.value;
		 
		    return a-b;
		});
	$('#<portlet:namespace/>LinOfBusiness').html(Innovationoptions);
	$("#<portlet:namespace/>LinOfBusiness option[value='']").attr("selected","selected");
	$('.basic').fancySelect();
});
function <portlet:namespace/>initDescriptionckEditor(){
	return  $("#descriptionHtmlData").html();
}
<portlet:namespace/>submitSolutionForm = function(){
var editorHTML = window.<portlet:namespace />descriptionckEditor.getHTML();
console.log(editorHTML);
if(editorHTML == ''){
$('#<portlet:namespace/>descriptionError').show();
return;
}else{
$('#<portlet:namespace/>descriptionError').hide();
}

$("#<portlet:namespace/>solutionContent").val(editorHTML);
submitForm(document.<portlet:namespace />solutionForm);
}
</aui:script>
