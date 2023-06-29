

var isValidForm = false;
var isLinkVideo = false;



$( document ).ready(function() {

	
	/****************************** ADD VIDEOS TO PLAYLIST SUBMIT BUTTON ------------------------------------------------**/ 
	$('#savechangesbtn').click(function() {
		fullScreenOverlay('videosettingsform','You are about to makes changes to this video. Are you sure?','Yes I\'m sure. Make the changes.');
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
            },
            "category.id": {
                validators: {
                    notEmpty: {
                        message: 'Category is required'
                    }
                }
            }
        }
    });	
	


});





	



















