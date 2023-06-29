

var isValidForm = false;
var isLinkVideo = false;


var selectedTags = new Array();
var selectedVideoType = new Array();





//WATCH LATER CLICK ON ACTION BAR FUNCTION
$(document).delegate('.vidaction-like', 'click', function() {
	
	terrabisApp.currentVideoAction = $(this);
	terrabisApp.currentVideoActionIcon = $(this).children('em:first');
	
	/*******************************************COUNT VIDEO LIKE **********************************/
	countVideoLike();

});


function countVideoLike() {
	
	if(terrabisApp.isAuthenticated) {
			var callback = function(data, status, xhr) {
				terrabisApp.currentVideoActionIcon.switchClass("animated animated-repeat flash text-muted", " - text-muted",  0, "easeInOutQuad" );
				terrabisApp.currentVideoAction.switchClass("cursor-pointer", "-",  0, "easeInOutQuad" );
				$("#vidlikestxt").html(JSON.parse(data.responseText).message);
		
			};
			
			terrabisApp.currentVideoActionIcon.switchClass("-",  "animated animated-repeat flash", 0, "easeInOutQuad" ).delay(100);
			
			var formData = { videoid: terrabisApp.currentVideo.id };
			restajax(terrabisApp.countVideoLikeURI,formData,callback);		
			
	} else {
		
			swal("You need to sign-up", "Please sign-up to like this video!", "warning");
		
	}
	
};










function initVideoTypesOnLoad(videoTypeList) {
	
	
	//ADD TO LIST
	for(i=0; i<videoTypeList.length; i++) {
		selectedVideoType.push(parseInt(videoTypeList[i].id));
		$(".videotypecheckbox input[value='"+videoTypeList[i].id+"']").attr( 'checked', 'checked' );
		
	}
	
	$("#selectedvideotypelist").val(selectedVideoType.toString());

	
};







/****************************** ADD REMOVE ITEM FUNCTIONALITY FOR JAVASCRIPT ARRAYS------------------------------------------------**/ 
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




/****************************EVENTS FOR VIDEO SHOOTING AND VIDEO TYPE ****************************************************/	
$(".videoshootingtyperadiobox input").click(function() {
	//alert(this.value);
	$("#shootingtypeid").val(this.value);
});
	
	

$(".videotypecheckbox input").click(function() {

	if($(this).is(':checked')) {
		if(!isVideoTypeSelected(parseInt(this.value)))
			selectedVideoType.push(parseInt(this.value));
		//ADD VIDEO TYPE
	} else {
		selectedVideoType.remove(parseInt(this.value));
		//REMOVE VIDEO TYPE
	}
	
	//UPDATE FORM
	$("#selectedvideotypelist").val(selectedVideoType.toString());

	


});
/****************************EVENTS FOR VIDEO SHOOTING AND VIDEO TYPE ****************************************************/	



//PRELOAD SELECTED TAGS AND CONSTRUCT TAG BUTTON
function preloadSelectedTagList(taglist) {
	var tag_html = "";
	var taginputtxt="";
	for(i=0; i<taglist.length; i++) {
			selectedTags.push(taglist[i]);
			taginputtxt = taginputtxt+ taglist[i]+",";
			tag =  '<button type="button" data-tagname="'+taglist[i]+'" class="tag-close btn btn-labeled btn-info-dark mr-sm">'+
					'<span class="btn-label"><i class="fa fa-times"></i>'+
					'</span>'+taglist[i]+
					'</button>';
			tag_html = tag_html+ tag;
		}
		$( "#selectedtags").html(tag_html);
	
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
	$("#videosettingsform input[name='tags']").val(taginputtxt);
	
};



//TAG CLICK IMPLEMENTATION
function onTagClick(tag) {
	addTag(tag);
};



$( document ).ready(function() {

	
	
	//ALSO LISTEN TO KEY PRESS EVENT AND IF ENTER ADD TAG
	 $('#tagsfilter').keypress(function(event) {
		 if ( event.which == 13 ) {
			 var tagfilter = $('#tagsfilter').val();
		     event.preventDefault();
		     addTag(tagfilter);
		     $('#tagsfilter').val(''); 
		  };		 
	 });
	
	
	/**************************************TAG CLOUD***************************************************/
	
	//KEY PRESS EVENT ON TAGS AND FILTER TAG CLOUD
	 $('#tagsfilter').bind('input',function(event) {

		 	var callback = function(data, status, xhr) {
		 		var tagwords =JSON.parse(data.responseText);
				$('#tagcloud').jQCloud('update', tagwords);

		 	};
		
		 	var tagfilter = $('#tagsfilter').val();
		 	
		 	var formData = { tagtext: tagfilter};
			restajax(terrabisApp.fetchTagsURI,formData,callback);				   
	 });
	 
	 
	 
	 
	

	//TAG BTN CLICK THEN REMOCE
	$(document).delegate('.tag-close', 'click', function() {
		selectedTags.remove(this.dataset.tagname);
		
		var tag_html = "";
		var taginputtxt = "";
		
		for(i=0; i<selectedTags.length; i++) {
			taginputtxt = taginputtxt+ selectedTags[i]+",";
			tag =  '<button type="button" data-tagname="'+selectedTags[i]+'" class="tag-close btn btn-labeled btn-info-dark m-sm">'+
					'<span class="btn-label"><i class="fa fa-times"></i>'+
					'</span>'+selectedTags[i]+
					'</button>';
			tag_html = tag_html+ tag;
		}
		$( "#selectedtags" ).html(tag_html);
		$("#videosettingsform input[name='tags']").val(taginputtxt);
	});
	
	
	
	
	
	
	

	
	/****************************** SAVE CHANGES ONLY ------------------------------------------------**/ 
	$('#savechangesbtn').click(function() {
		$("#videosettingsform input[name='saveandapprove']").val(false);
		//fullScreenOverlay('videosettingsform','You are about to makes changes to this video. Are you sure?','Yes I\'m sure. Make the changes.');
		document.getElementById("videosettingsform").submit();
	});

	$('#savechangesapprovebtn').click(function() {
		$("#videosettingsform input[name='saveandapprove']").val(true);
		document.getElementById("videosettingsform").submit();
	});
	
	
	
	
	
	
	
	
	
	
	$(".weathercheckbox input[value!='any']").click(function() {
		$("#weatherMap-sunny").val($(".weathercheckbox input[value='sunny']").is(':checked'));
		$("#weatherMap-cloudy").val($(".weathercheckbox input[value='cloudy']").is(':checked'));
		$("#weatherMap-rain").val($(".weathercheckbox input[value='rain']").is(':checked'));
		$("#weatherMap-snow").val($(".weathercheckbox input[value='snow']").is(':checked'));
		$("#weatherMap-windy").val($(".weathercheckbox input[value='windy']").is(':checked'));
		$("#weatherMap-storm").val($(".weathercheckbox input[value='storm']").is(':checked'));
		
	});
	
	/****************** FORM VALIDATION **************************************************************/
	var formValidation = $('#videosettingsform').formValidation({
        framework: 'bootstrap',
        icon: {
            valid: 'fa  fa-check',
            invalid: 'fa fa-check fa-remove',
            validating: 'fa fa-refresh'
        },
        fields: {
            title: {
                validators: {
                    notEmpty: {
                        message: 'Video title is required'
                    }
                }
            },
            "footagedate": {
                validators: {
                    notEmpty: {
                        message: 'Footage date is required'
                    }
                }
            },
            "privacy": {
                validators: {
                    notEmpty: {
                        message: 'Video privacy is required'
                    }
                }
            },
            "description": {
                validators: {
                    notEmpty: {
                        message: 'Enter a short description'
                    }
                }
            }
           
        }
    });	
	


});





	



















