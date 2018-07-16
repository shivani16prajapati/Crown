<%@include file="../init.jsp"%>

<aui:script>
$(document).ready(function () {
 if('${sliderEdit}'){
  $('#<portlet:namespace/>createIdeafm').find('input, textarea, select').attr('readonly', true);
 }
});

function <portlet:namespace/>initDescriptionckEditor(){
	return  $("#descriptionHtmlData").html();
}
<portlet:namespace/>submitForm = function(){
var editorHTML = window.<portlet:namespace />descriptionckEditor.getHTML();
console.log(editorHTML);
if(editorHTML == ''){
$('#<portlet:namespace/>descriptionError').show();
return;
}else{
$('#<portlet:namespace/>descriptionError').hide();
}

$("#<portlet:namespace/>ideaContent").val(editorHTML);
submitForm(document.<portlet:namespace />ideaForm);
}

<portlet:namespace />addFileUpload=function(){
	 var clonnedObj = $("#fileUploader1").clone();
	 var nextCounter = $(".fileUploader").length+1;
	
	 
	 $(clonnedObj).attr("id","fileUploader"+nextCounter);
	 
	
	 $(clonnedObj).find("#unicefUpload1").attr("id","unicefUpload"+nextCounter);
	 $(clonnedObj).find("#ideaFileUploader1").attr("id","ideaFileUploader"+nextCounter);
	 $(clonnedObj).find("#<portlet:namespace/>fileUpload1").attr("id","<portlet:namespace/>fileUpload"+nextCounter);
	 $(clonnedObj).find("#<portlet:namespace/>fileUpload"+nextCounter).attr("name","<portlet:namespace/>fileUpload"+nextCounter);
	 $(clonnedObj).find("#<portlet:namespace/>removeFileUploaderButton1").attr("id","<portlet:namespace/>removeFileUploaderButton"+nextCounter);
	 $(clonnedObj).find("#<portlet:namespace/>removeFileUploaderButton"+nextCounter).attr("name","<portlet:namespace/>removeFileUploaderButton"+nextCounter);
	 $(clonnedObj).find("#<portlet:namespace/>removeFileUploaderButton"+nextCounter).attr("onclick","<portlet:namespace/>removeFileDetails('"+nextCounter+"');");
	 $(clonnedObj).find("#<portlet:namespace/>removeFileUploaderButton"+nextCounter).show();
	 $(clonnedObj).find("#<portlet:namespace/>fileUploadLink").hide();
	 
	 $("#dynamicDiv").append(clonnedObj);
	 
	 
	 if (nextCounter == 5)
         $("#addbtnDiv").addClass('hide');
	 
	 $(clonnedObj).find("#<portlet:namespace/>fileUpload1"+nextCounter).val("");
}
<portlet:namespace />removeFileDetails=function(index){
	if(index > 1){
		$('#fileUploader'+index).remove();	
	} 
	if(index <= 5 && $("#addbtnDiv").hasClass('hide')){
		  $("#addbtnDiv").removeClass('hide');
	}
}
<portlet:namespace/>clearOptions = function(id){
	 $('#'+id)
	    .find('option')
	    .remove();
	   }
</aui:script>
