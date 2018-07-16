
<%@include file="../init.jsp"%>

<aui:script>



function checkimageRatio(image){
	var file,img;
	var ext = $(image).val().split('.').pop().toLowerCase();
	if($.inArray(ext, ['gif','png','jpg','jpeg']) == -1) {
	    alert('Upload Image file Only');
	    $("#<portlet:namespace />textName").text("");
	    return false;
	}
	var fileText = image.value.replace("C:\\fakepath\\", "");
	$("#<portlet:namespace />textName").text(fileText);
	
}

<portlet:namespace/>submitForm = function(){
	console.log("validation video form");
	
	var form = true;
	form = validateVideoFormFeed();
	console.log(form);
	if(form){
		submitForm(document.<portlet:namespace />videoForm);	
	}
	
}



</aui:script>