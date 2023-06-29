var blockMouseOver = false;



/********************** MOUSE OVER MOUSE OUT *************************************************************/
	$(document).delegate('.videopanel', 'mouseover', function() {
		terrabisApp.currentVideo = this.dataset;
		
		
		if(blockMouseOver)
			return;
	
		$("#vidoverlay_action_"+terrabisApp.currentVideo.id+'-'+terrabisApp.currentVideo.groupid).switchClass( "hidden", "-", 0, "easeInOutQuad" );
		

	});
	
	
	$(document).delegate('.videopanel', 'mouseout', function() {
		
		var currentdataset = this.dataset;

		
		
		$("#vidoverlay_action_"+currentdataset.id+'-'+currentdataset.groupid).switchClass("-", "hidden", 0, "easeInOutQuad" );
		

		blockMouseOver = false;
	});


	
	
	/********************** PLAY VIDEO BUTTON ON BOOTSTRAP MODAL *************************************************************/
	$(document).delegate('.vidoverlay_playvidbtn', 'click', function() {
		terrabisApp.currentVideoPlayingId = terrabisApp.currentVideo.id;
		if(terrabisApp.currentVideo.link=='true') {
			$("#videomodal").modal();
			$("#videomodal_iframe").attr( "src", terrabisApp.currentVideo.videourl+"?autoplay=1&rel=0&modestbranding=1");
			$("#videomodal_title").html(terrabisApp.currentVideo.id);
		}
	});


	/**********************  CLOSE VIDEO  ************************************************************************/
	$(document).delegate('.videomodalclose', 'click', function() {
		$("#videomodal_iframe").attr( "src", " ");
		$("#videomodal_title").html("");
	});
	
	
	
	//LIKE CLICK ON ACTION BAR FUNCTION
$(document).delegate('.vidaction-like', 'click', function() {
		
		terrabisApp.currentVideoAction = $(this);
		terrabisApp.currentVideoActionIcon = $(this).children('em:first');
		
		/*******************************************COUNT VIDEO LIKE **********************************/
		countVideoLike();

});

	
	
function countVideoLike() {
		
		if(terrabisApp.isAuthenticated) {
			
			
					terrabisApp.tempVideo = terrabisApp.currentVideo;
					terrabisApp.tempVideoActionIcon = terrabisApp.currentVideoActionIcon;
					terrabisApp.tempVideoAction = terrabisApp.currentVideoAction;
			
					//IF ALREADY CLICK RETURN 
					if(terrabisApp.currentVideoActionIcon.hasClass("fa-check"))
							return;
					
					var callback = function(data, status, xhr) {
						var currentVideoID = terrabisApp.currentVideo.id;
						terrabisApp.tempVideoActionIcon.switchClass("fa-refresh fa-spin", "fa-check text-info-light",  0, "easeInOutQuad" );
						terrabisApp.tempVideoAction.switchClass("cursor-pointer", "-",  0, "easeInOutQuad" );
						
						if(terrabisApp.tempVideoActionIcon.hasClass("fullvideoplay")) {
							$(".fullvideoplay_likescount").html(JSON.parse(data.responseText).message);
							$(".fullvideoplay_likescount").switchClass("text-gray text-sm-sm", "text-info-light text-bold h3",  0, "easeInOutQuad" );
						}
						else {
							$("#vidlikestxt_"+ terrabisApp.tempVideo.id).html(JSON.parse(data.responseText).message);
							$("#vidlikestxt_"+ terrabisApp.tempVideo.id).switchClass("text-muted text-sm", "text-info-light text-bold h3",  0, "easeInOutQuad" );
						}

					};
					
					terrabisApp.tempVideoActionIcon.switchClass("fa-thumbs-up",  "fa-refresh fa-spin", 0, "easeInOutQuad" ).delay(1000);
					
					var formData = { videoid: terrabisApp.currentVideo.id };
					restajax(terrabisApp.countVideoLikeURI, formData,callback);
			

				
		} else {
			
				swal("You need to sign-in", "Please sign-in to like this video!. Click <a href=\"http://www.terrabis.com/authenticate/signin\">here</a>", "warning");
			
		}
		
	};