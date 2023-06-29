

var isValidForm = false;
var isLinkVideo = false;

var selectedTags = new Array();
var selectedVideoType = new Array();



/****************************** ADD REMOVE ITEM FUNCTIONALITY ------------------------------------------------**/ 
if (!Array.prototype.remove) {
	  Array.prototype.remove = function(val) {
	    var i = this.indexOf(val);
	         return i>-1 ? this.splice(i, 1) : [];
	  };
}




function isVideoTypeSelected(videoTypeId) {

	var isSelected = false;
	var selectedIndex = -1;
	for(i=0; i<selectedVideoType.length; i++) {
		if(selectedVideoType[i]==videoTypeId) {
			isSelected = true;
			selectedIndex = i;
		}
	}
	return isSelected;
};






function initializeUploadView(tagwords) {
	
	
	//INIT  TAG CLOUD
	$('#tagcloud').jQCloud(tagwords, {
  		shape: 'rectangular'
	});
	
	
	//SET LINK FLAG TO FALSE
	$('#link').val("false");

	//INIT LAT AND LNG
	$('#latitude').val(terrabisApp.uploadLatitude);
	$('#longitude').val(terrabisApp.uploadLongitude);
	
	

	 
	//ALSO LISTEN TO KEY PRESS EVENT AND IF ENTER ADD TAG
	 $('#tagsfilter').keypress(function(event) {
		 if ( event.which == 13 ) {
			 var tagfilter = $('#tagsfilter').val();
		     event.preventDefault();
		     addTag(tagfilter);
		     $('#tagsfilter').val(''); 
		  };		 
	 });
	

	
	
};







//ADD TAG
function addTag(tag) {
	selectedTags.push(tag);
	
	var tag_html = "";
	var taginputtxt="";
	for(i=0; i<selectedTags.length; i++) {
		taginputtxt = taginputtxt+ selectedTags[i]+",";
		tag = 
		       '<button type="button" data-tagname="'+selectedTags[i]+'" class="tag-close btn btn-labeled btn-info-dark mr-sm">'+
				'<span class="btn-label"><i class="fa fa-times"></i>'+
				'</span>'+selectedTags[i]+
				'</button>';


		tag_html = tag_html+ tag;
	}
	
	$( "#selectedtags" ).html(tag_html);
	$("#videofileuploadform input[name='tags']").val(taginputtxt);
	
};







//TAG CLICK IMPLEMENTATION
function onTagClick(tag) {
	addTag(tag);
};







$( document ).ready(function() {
	
	
	// DATETIMEPICKER
	$('#footagedate').datetimepicker({
		format: 'DD/MM/YYYY',
		icons: {
	      time: 'fa fa-clock-o',
	      date: 'fa fa-calendar',
	      up: 'fa fa-chevron-up',
	      down: 'fa fa-chevron-down',
	      previous: 'fa fa-chevron-left',
	      next: 'fa fa-chevron-right',
	      today: 'fa fa-crosshairs',
	      clear: 'fa fa-trash'
	    }
	});
	
	

	
	
	/**************************************TAG CLOUD***************************************************/
	
	//KEY PRESS EVENT ON TAGS AND FILTER TAG CLOUD
	 $('#tagsfilter').bind('input',function(event) {
		 
		 var tagfilter = $('#tagsfilter').val();

			 //SEARCH FOR TAGS
		 
		 	var callback = function(data, status, xhr) {
		 		var tagwords =JSON.parse(data.responseText);
				$('#tagcloud').jQCloud('update', tagwords);

		 	};
		
		 	
		 	
		 	var formData = { tagtext: tagfilter};
			restajax(terrabisApp.fetchTagsURI,formData,callback);
			
	 });
	
	 
/*	 //DEBUGGING
	 $(document).delegate('#uploadbutton', 'click', function() {
		 alert($('#latitude').val()+'-'+$('#longitude').val());
	 
	 });
*/		 
		 

	//TAG BTN CLICK THEN REMOCE
	$(document).delegate('.tag-close', 'click', function() {
		selectedTags.remove(this.dataset.tagname);
		
		var tag_html = "";
		var taginputtxt = "";
		
		for(i=0; i<selectedTags.length; i++) {
			taginputtxt = taginputtxt+ selectedTags[i]+",";
			tag =  '<button type="button" data-tagname="'+selectedTags[i]+'" class="tag-close btn btn-labeled btn-info-dark mr-sm">'+
					'<span class="btn-label"><i class="fa fa-times"></i>'+
					'</span>'+selectedTags[i]+
					'</button>';
			tag_html = tag_html+ tag;
		}
		$( "#selectedtags" ).html(tag_html);
		$("#videofileuploadform input[name='tags']").val(taginputtxt);
		
	});
	
	

/****************************EVENTS FOR VIDEO SHOOTING AND VIDEO TYPE ****************************************************/	
$(".videoshootingtyperadiobox input").click(function() {
	//alert(this.value);
	$("#shootingtypeid").val(this.value);
});
	
	

$(".videotypecheckbox input").click(function() {
	if($(this).is(':checked')) {
		if(!isVideoTypeSelected(this.value))
			selectedVideoType.push(this.value);
		//ADD VIDEO TYPE
	} else {
		selectedVideoType.remove(this.value);
		//REMOVE VIDEO TYPE
	}
	
	//UPDATE FORM
	$("#selectedvideotypelist").val(selectedVideoType.toString());
	
	//alert(selectedVideoType.toString());
	

});
/****************************EVENTS FOR VIDEO SHOOTING AND VIDEO TYPE ****************************************************/	
	





	
	
/****************************EVENTS FOR WEATHER ****************************************************/	
$(".weathercheckbox input[value!='any']").click(function() {
	$("#weatherMap-sunny").val($(".weathercheckbox input[value='sunny']").is(':checked'));
	$("#weatherMap-cloudy").val($(".weathercheckbox input[value='cloudy']").is(':checked'));
	$("#weatherMap-rain").val($(".weathercheckbox input[value='rain']").is(':checked'));
	$("#weatherMap-snow").val($(".weathercheckbox input[value='snow']").is(':checked'));
	$("#weatherMap-windy").val($(".weathercheckbox input[value='windy']").is(':checked'));
	$("#weatherMap-storm").val($(".weathercheckbox input[value='storm']").is(':checked'));
	
});
	
	


/****************************EVENTS FOR UPLOAD TYPE EITHER YOUTUBE VIMEO LINK OR FILE ****************************************************/	
$( ".filevideotrigger" ).click(function() {
	//fileToUploadEffects();
		
});


$( ".linkvideotrigger" ).click(function() {
	linkToUploadEffects();
});



/****************************************UPLOAD DROPZONE**********************************************/
initUploadDropzone();



});

function linkToUploadEffects() {
	isLinkVideo = true;
	$('#linkedurl').attr("disabled",false);
	$('#linkedurl').val("");
	$("#uploadbutton").attr('type','submit');
	$("#videofileuploadform").attr('action',terrabisApp.uploadLinkURL);
	$('#link').val("true");
};



function fileToUploadEffects() {
	isLinkVideo = false;
	$('#linkedurl').val("http://www.terrabis.file");
	$('#linkedurl').attr("disabled",true);
	$("#uploadbutton").attr('type','button');
	$("#videofileuploadform").attr('action',terrabisApp.uploadFileURL);
	$('#link').val("false");
};





function initUploadDropzone() {	
	
	//Dropzone.autoDiscover = false;
	//Get the template HTML and remove it from the document
	//var previewNode = document.querySelector("#vidpreviewpanelnode");
	//previewNode.id = "";
	//var previewTemplate = previewNode.parentNode.innerHTML;
	//previewNode.parentNode.removeChild(previewNode);



	// var userid = $( "#userid").val();

	var myDropzone = new Dropzone("#videofileuploadform", { // Make the whole body a dropzone
		  paramName: "file", // The name that will be used to transfer the file
		  maxFilesize: 500, // MB
		  //url: terrabisApp.uploadFileURL,
		  previewTemplate: document.querySelector('#preview-template').innerHTML,
		  thumbnailWidth: 240,
		  thumbnailHeight: 120,
		  parallelUploads: 2,
		  autoProcessQueue: false, // Make sure the files aren't queued until manually added
		  //previewTemplate: previewTemplate,
		  previewsContainer: "#preview-template", // Define the container to display the previews
		  clickable: ".dropzoneiputclass" // Define the element that should be used as click trigger to select files.
			  
		});






	myDropzone.on("addedfile", function(file) {
		$( "#filevideobuttontext" ).switchClass("text-white", "text-gray", 1000, "easeInOutQuad" );
		$("#filevideobutton").prop("disabled", "disabled");
		
		fileToUploadEffects();
		

		
		//file.previewElement.addEventListener("click", function() {
		document.querySelector(".remove").onclick = function() {
			myDropzone.removeAllFiles(true);
			
			linkToUploadEffects();
			
			$("#filevideobutton").removeAttr('disabled');
		    $( "#filevideobuttontext" ).switchClass("text-gray", "text-white", 1000, "easeInOutQuad" );
			$( "#viddropzone" ).switchClass( "hidden", "-", 0, "easeInOutQuad" );
			$( "#vidpreviewpanel" ).switchClass("-", "hidden", 0, "easeInOutQuad" );
		    
		  };
		  
		  document.querySelector(".cancelupload").onclick = function() {
				myDropzone.removeAllFiles(true);
				$("#filevideobutton").removeAttr('disabled');
			    $( "#filevideobuttontext" ).switchClass("text-gray", "text-white", 1000, "easeInOutQuad" );
				$( "#viddropzone" ).switchClass( "hidden", "-", 0, "easeInOutQuad" );
				$( "#vidpreviewpanel" ).switchClass("-", "hidden", 0, "easeInOutQuad" );
			    
			  };
		  
		  
		  
		  

		   document.querySelector(".start").onclick = function() { 
			   $("#videofileuploadform").data('formValidation').validate();
			   isValidForm = $("#videofileuploadform").data('formValidation').isValid();
			   if(isValidForm) {
				   myDropzone.processFile(file);
			   }
			   
		   };
		   
		  $( "#viddropzone" ).switchClass( "-", "hidden", 0, "easeInOutQuad" );
		  $( "#vidpreviewpanel" ).switchClass( "hidden", "-", 0, "easeInOutQuad" );
		  $( ".vidpreview_remove" ).switchClass( "hidden", "-", 0, "easeInOutQuad" );

		  
		  
		  document.querySelector("#total-progress .progress-bar").style.width = "0%";
		  //Update the total progress bar
		  myDropzone.on("totaluploadprogress", function(progress, bytesSent) {
			  document.querySelector("#total-progress .progress-bar").style.width = progress + "%";
			  $("#progressbartext").text("UPLOADING..."+Math.round(progress * 100) / 100 + "% COMPLETE");
			  
			  if((Math.round(progress * 100) / 100)==100)
				  $("#progressbartext").text("PROCESSING VIDEO PLEASE WAIT...");
		  });
			  
	});

		myDropzone.on("sending",function(file, xhr, formData) {
			//alert('sending');	
			$( "#uploadbuttontext" ).switchClass("text-white", "text-gray", 1000, "easeInOutQuad" );
			//$( "#uploadbutton" ).switchClass("btn-danger", "btn-green", 1000, "easeInOutQuad" );
			//$( "#uploadbuttontext" ).text("UPLOADING..." );
			$("#uploadbutton").prop("disabled", "disabled");
			//$("#canceluploadbutton").removeAttr('disabled');
			$( "#progressbardiv" ).switchClass("hidden", "-", 0, "easeInOutQuad" );
			  	
		  // Show the total progress bar when upload starts
		  // And disable the start button
		  
		  //document.querySelector(".start").setAttribute("disabled", "disabled");
		});

		// Hide the total progress bar when nothing's uploading anymore
		myDropzone.on("queuecomplete", function(progress) {
			//alert('complete');
			//$("#canceluploadbutton").prop("disabled", "disabled");
			$("#uploadbutton").removeAttr('disabled');
		    $( "#uploadbuttontext" ).switchClass("text-gray", "text-white",1000, "easeInOutQuad" );
			//$( "#uploadbutton" ).switchClass("btn-green", "btn-danger", 1000, "easeInOutQuad" );
			//$( "#uploadbuttontext" ).text("Upload" );
			$( "#progressbardiv" ).switchClass("-", "hidden", 0, "easeInOutQuad" );

			myDropzone.removeAllFiles(true);
			
			$("#filevideobutton").removeAttr('disabled');
		    $( "#filevideobuttontext" ).switchClass("text-gray", "text-white", 1000, "easeInOutQuad" );
			$( "#viddropzone" ).switchClass( "hidden", "-", 0, "easeInOutQuad" );
			$( "#vidpreviewpanel" ).switchClass("-", "hidden", 0, "easeInOutQuad" );
		});

		myDropzone.on("success", function(response) {
			window.location.href = terrabisApp.successRedirectURI;		 
		});


		
};
		
		//
		
		// Setup the buttons for all transfers
		// The "add files" button doesn't need to be setup because the config
		// `clickable` has already been specified.
		/*document.querySelector("#actions .start").onclick = function() {
		  myDropzone.enqueueFiles(myDropzone.getFilesWithStatus(Dropzone.ADDED));
		};
		document.querySelector("#actions .cancel").onclick = function() {
		  myDropzone.removeAllFiles(true);
		};
		*/








	



















