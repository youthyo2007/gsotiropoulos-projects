



$( document ).ready(function() {

	
	  //PROFILE FORM VALIDATION
	  var profileFormValidation = $('#profileform').formValidation({
	        framework: 'bootstrap',
	        icon: {
	            valid: 'fa  fa-check',
	            invalid: 'fa fa-check fa-remove',
	            validating: 'fa fa-refresh'
	        },
	        fields: {
	        	'user.username': {
	                validators: {
	                    notEmpty: {
	                        message: 'A username is required'
	                    },
	                    remote: {
	                        message: 'The username is not available',
	                        data: function(validator, $field, value) {
	                            return {
	                                username: validator.getFieldElements('user.username').val()
	                            };
	                        },
	                        url: '/rest-validation/user-settings/duplicate/username',
	                        type:  terrabisApp.csrf_token
	                    }
	                }
	            }
	        }
	    });	
	  
	  
	  //PASSWORD FORM VALIDATION
	  var securityFormValidation = $('#securityform').formValidation({
	        framework: 'bootstrap',
	        icon: {
	            valid: 'fa  fa-check',
	            invalid: 'fa fa-check fa-remove',
	            validating: 'fa fa-refresh'
	        },
	        fields: {
	            "user.oldPassword": {
	                validators: {
	                    notEmpty: {
	                        message: 'You must enter your  password'
	                    },
	                   remote: {
	                        message: 'The old password is invalid',
	                        data: function(validator, $field, value) {
	                            return {
	                            	oldpassword: validator.getFieldElements('user.oldPassword').val()
	                            };
	                        },
	                        url: '/rest-validation/user-settings/valid/old-password',
	                        type:  terrabisApp.csrf_token
	                	}
	                    
	                }
	            },
	        	"user.newPassword": {
	                validators: {
	                    notEmpty: {
	                        message: 'You must enter a new password'
	                    },
 	                  identical: {
	                      field: 'user.passwordConfirm',
	                      message: 'The new password and its confirm are not the same'
	                  }     	                    
	                }
	            },
	            "user.passwordConfirm": {
	                validators: {
	                    notEmpty: {
	                        message: 'Please confirm the new password '
	                    },
 	                  identical: {
	                      field: 'user.newPassword',
	                      message: 'The new password and its confirm are not the same'
	                  }     	                    
	                }
	            }
	        }
	    });	    	  

	
});





function initUploadDropzone(successRedirectURI) {	

	var myDropzone = new Dropzone("#fileuploadform", { // Make the whole body a dropzone
		  paramName: "file", // The name that will be used to transfer the file
		  maxFilesize: 100, // MB
		  //url: terrabisApp.uploadFileURL,
		  previewTemplate: document.querySelector('#preview-template').innerHTML,
		  thumbnailWidth: 240,
		  thumbnailHeight: 120,
		  parallelUploads: 2,
		  autoProcessQueue: true, // Make sure the files aren't queued until manually added
		  //previewTemplate: previewTemplate,
		  previewsContainer: "#preview-template", // Define the container to display the previews
		  clickable: ".dropzoneiputclass" // Define the element that should be used as click trigger to select files.
			  
		});

	



	myDropzone.on("addedfile", function(file) {
		$( "#filebuttontext" ).switchClass("text-white", "text-gray", 1000, "easeInOutQuad" );
		$("#filebutton").prop("disabled", "disabled");
		//file.previewElement.addEventListener("click", function() {
		document.querySelector(".remove").onclick = function() {
			myDropzone.removeAllFiles(true);
			$("#filebutton").removeAttr('disabled');
		    $( "#filebuttontext" ).switchClass("text-gray", "text-white", 1000, "easeInOutQuad" );
			$( "#dropzone" ).switchClass( "hidden", "-", 0, "easeInOutQuad" );
			$( "#previewpanel" ).switchClass("-", "hidden", 0, "easeInOutQuad" );
		    
		  };

/*		   document.querySelector(".start").onclick = function() { 
			   myDropzone.processFile(file);
		   };*/
		   
		  $( "#dropzone" ).switchClass( "-", "hidden", 0, "easeInOutQuad" );
		  $( "#previewpanel" ).switchClass( "hidden", "-", 0, "easeInOutQuad" );
		  $( ".preview_remove" ).switchClass( "hidden", "-", 0, "easeInOutQuad" );

		  
		  
		  document.querySelector("#total-progress .progress-bar").style.width = "0%";
		  //Update the total progress bar
		  myDropzone.on("totaluploadprogress", function(progress, bytesSent) {
			  document.querySelector("#total-progress .progress-bar").style.width = progress + "%";
			  $("#progressbartext").text("UPLOADING..."+Math.round(progress * 100) / 100 + "% COMPLETE");
			  
			  if((Math.round(progress * 100) / 100)==100)
				  $("#progressbartext").text("PROCESSING FILE PLEASE WAIT...");
		  });
			  
	});

		myDropzone.on("sending",function(file, xhr, formData) {
			//alert('sending');	
			//$( "#uploadbuttontext" ).switchClass("text-white", "text-gray", 1000, "easeInOutQuad" );
			//$( "#uploadbutton" ).switchClass("btn-danger", "btn-green", 1000, "easeInOutQuad" );
			//$( "#uploadbuttontext" ).text("UPLOADING..." );
			//$("#uploadbutton").prop("disabled", "disabled");
			$( "#progressbardiv" ).switchClass("hidden", "-", 0, "easeInOutQuad" );
			  	
		  // Show the total progress bar when upload starts
		  // And disable the start button
		  
		  //document.querySelector(".start").setAttribute("disabled", "disabled");
		});

		// Hide the total progress bar when nothing's uploading anymore
		myDropzone.on("queuecomplete", function(progress) {
			//alert('complete');
			//$("#uploadbutton").removeAttr('disabled');
		    //$( "#uploadbuttontext" ).switchClass("text-gray", "text-white",1000, "easeInOutQuad" );
			//$( "#uploadbutton" ).switchClass("btn-green", "btn-danger", 1000, "easeInOutQuad" );
			//$( "#uploadbuttontext" ).text("Upload" );
			$( "#progressbardiv" ).switchClass("-", "hidden", 0, "easeInOutQuad" );

			myDropzone.removeAllFiles(true);
			
			$("#filebutton").removeAttr('disabled');
		    $( "#filebuttontext" ).switchClass("text-gray", "text-white", 1000, "easeInOutQuad" );
			$( "#dropzone" ).switchClass( "hidden", "-", 0, "easeInOutQuad" );
			$( "#previewpanel" ).switchClass("-", "hidden", 0, "easeInOutQuad" );
		});

		myDropzone.on("success", function(response) {
			window.location.href = successRedirectURI;		 
		});


		
};










