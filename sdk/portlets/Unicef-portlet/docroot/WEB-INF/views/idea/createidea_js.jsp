<%@include file="../init.jsp"%>

<portlet:resourceURL var="getKeywordExtractorURL" id="getKeywordExtractor" />
<portlet:resourceURL var="checkDuplicateideaURL" id="checkDuplicateidea" />
<aui:script>


var finalTagHtml = '';
$(document).ready(function(){
	
	
	
	 if('${sliderEdit}'){
	  	$('#<portlet:namespace/>createIdeafm').find('input, textarea, select').attr('readonly', true);
	 }
	 
	 if('${isUpdateForm}'){
	 	var resultList = new Array();
		resultList = '${keywordsList}';
		
	 	finalTagHtml = generateHtml(resultList);
	 	$("#<portlet:namespace/>importKeywords").append(finalTagHtml);
	 	if(resultList.length > 2){
				$('#<portlet:namespace />keywordDiv').show();
		}
	 }
	 
	 var Innovationoptions = $("#<portlet:namespace/>ideaType option");
	 Innovationoptions.sort(function(a,b){
		    a = a.value;
		    b = b.value;
		 
		    return a-b;
		});
	 $('#<portlet:namespace/>ideaType').html(Innovationoptions);
	 $("#<portlet:namespace/>lineofbusiness option[value='']").attr("selected","selected");
	 <c:if test="${not empty idea.innovationType}">
	 	$("#<portlet:namespace/>ideaType option[value='${idea.innovationType}']").attr("selected","selected");
	 </c:if>
	 
	 var lofoptions = $("#<portlet:namespace/>lineofbusiness option");
	 lofoptions.sort(function(a,b){
		    a = a.value;
		    b = b.value;
		 
		    return a-b;
		});
	 $('#<portlet:namespace/>lineofbusiness').html(lofoptions);
	 $("#<portlet:namespace/>lineofbusiness option[value='']").attr("selected","selected");
	 <c:if test="${not empty idea.lineOfbussinessId}">
	 	$("#<portlet:namespace/>lineofbusiness option[value='${idea.lineOfbussinessId}']").attr("selected","selected");
	 </c:if>
	 $('.basic').fancySelect();
	 
	 $("#<portlet:namespace />fileUpload1").change(function() {
	 		
	  });
});

function <portlet:namespace/>initDescriptionckEditor(){
		return  $("#descriptionHtmlData").html();
}



<portlet:namespace/>submitForm = function(){
		var editorHTML = window.<portlet:namespace />descriptionckEditor.getHTML();
		if(editorHTML == ''){
			$('#<portlet:namespace/>descriptionError').show();
			return;
		}else{
			$('#<portlet:namespace/>descriptionError').hide();
		}
		
		$("#<portlet:namespace/>ideaContent").val(editorHTML);
		
		if(checkKeywordsLength()){
			$('#<portlet:namespace />keywordError').hide();
			$('#<portlet:namespace/>keywords').val(getKeywords());
			submitForm(document.<portlet:namespace />ideaForm);
			
		}
}

function getKeywords(){
		var keywords = [];
		$('.ab-keywordbox2').each(function(index){
			keywords.push($(this).find('.keywordLabel').text());
		});
		return keywords.toString();
}

function checkKeywordsLength(){
		if($('.keywordLabel').length > 5){
			$('#<portlet:namespace />keywordError').show();
			return false;
		}
		return true;
}

<portlet:namespace />removeTag = function(tagId){
	$('#<portlet:namespace />tag_'+tagId).remove();
}

var is_chrome = navigator.userAgent.toLowerCase().indexOf('chrome') > -1;
var FIREFOX = /Firefox/i.test(navigator.userAgent);
var spaceCounter = 0;



var flag=true;



<!-- Keyword Exctractor Functionality Commit Changes -->

<portlet:namespace/>keywordExtractBlurMethod = function(){

	var editorHTMLCK = window.<portlet:namespace />descriptionckEditor.getHTML();
	var editorHTML = window.<portlet:namespace />descriptionckEditor.getHTML();
	var ckEditorString = '';
	
	if($(editorHTMLCK).text() != ''){
		ckEditorString = '';
		ckEditorString = $(editorHTMLCK).text();
	}
	if($(editorHTMLCK).text() == '' && editorHTML != ''){
		ckEditorString = '';
		ckEditorString = editorHTML;
	}
	
			$.ajax({
		   		 url :"<%=getKeywordExtractorURL%>",
		    	 data : {
		    	 'editorHTML' : ckEditorString
	   		},
		    success : function(result) {
				finalTagHtml = generateHtml(result);
				$("#<portlet:namespace/>importKeywords").text('');
				$("#<portlet:namespace/>importKeywords").append(finalTagHtml);
				if(result.length > 0){
					$('#<portlet:namespace />keywordDiv').show();
				}
		   },
   		   error : function(result){
				 alert('error');
		   },
	   		async: false
		 });
		    
			
			
			console.log(ckEditorString)
			var words = ckEditorString.split(" "); 
			var count = 0;
			 for (i=0 ; i < words.length ; i++){
			       if (words[i] != "")
			          count += 1; 
			    }
			 console.log(count)
			
				$.ajax({
			   		 url :"<%=checkDuplicateideaURL%>",
			    	 data : {
			    	 'editorHTML' : ckEditorString,
			    	 'ideaTittle':$("#ideaTitle").val()
		   		 },success : function(result) {
		   			console.log(result);
		   			$("#duplicate_Detactor").html(result)
		   		 }   		
		   		});
			
}
<portlet:namespace/>keywordExtractorfocusout = function(){

	var editorHTMLCK = window.<portlet:namespace />descriptionckEditor.getHTML();
	var editorHTML = window.<portlet:namespace />descriptionckEditor.getHTML();
	var ckEditorString = '';
	
	if($(editorHTMLCK).text() != ''){
		ckEditorString = '';
		ckEditorString = $(editorHTMLCK).text();
	}
	if($(editorHTMLCK).text() == '' && editorHTML != ''){
		ckEditorString = '';
		ckEditorString = editorHTML;
	}
	var spaceCount = ckEditorString.split(" ").length - 1;		
	if((spaceCount % 5 == 1)){
		flag=false;getKeywordExtractorURL
	}
	if(spaceCount % 5 == 0){
		if(!flag){
			flag=true;
			
			$.ajax({
		   		 url :"<%=getKeywordExtractorURL%>",
		    	 data : {
		    	 'editorHTML' : ckEditorString
	   		},
		    success : function(result) {
				finalTagHtml = generateHtml(result);
				$("#<portlet:namespace/>importKeywords").text('');
				$("#<portlet:namespace/>importKeywords").append(finalTagHtml);
				if(result.length > 0){
					$('#<portlet:namespace />keywordDiv').show();
				}
		   },
   		   error : function(result){
				 alert('error');
		   },
	   		async: false
		 });
			
			
			var words = $.trim(ckEditorString).split(" ");
			console.log(words.length);	
			
			if(Number(words.length) == 30 || Number(words.length) == 40 || Number(words.length) == 50){
				
				$.ajax({
			   		 url :"<%=checkDuplicateideaURL%>",
			    	 data : {
			    	 'editorHTML' : ckEditorString,
			    	 'title':$('#<portlet:namespace/>ideaTitle').val()
		  			},
			    	success : function(result) {
						finalTagHtml = generateHtml(result);
						
			   		},
				   	error : function(result){
						 alert('error');
			   		},
		  			async: false
			 	});
			}
		}
	}	
} 	
function generateHtml(result){
	var messageHtml = '';
	if(result.length > 2){
		var x ="X";
		var substr = result.substring(1).slice(0,-1).split(',');
			for(var i=0; i< substr.length; i++) {
					messageHtml += '<div class="ab-keywordbox2" id="<portlet:namespace />tag_'+i+'">';					
					messageHtml += '<span class="keywordLabel">'+substr[i]+'</span>';
					messageHtml += '<span>';
					messageHtml += '<a href="javascript:void(0);" onclick="javascript:<portlet:namespace />removeTag('+i+');" class="cancelTag">'+x+'</a>';
					messageHtml += '</span>';
					messageHtml += '</div>';
				}
		return messageHtml;
	}else{
		return messageHtml;
	}
}

function ideatypechange(value){
	if (Number($(value).val()) == 13679||Number($(value).val())==13677||Number($(value).val())==13678){
		$("#<portlet:namespace/>ust-form-div-010").show();
	}else{
		$("#<portlet:namespace/>ust-form-div-010").hide();
	}
}

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
</aui:script>
