var blockMouseOver = false;
var blockVideoPanelMouseOver = false;










function showPreviousSibling() {
	

	$(this).prev().switchClass("hidden", " - ",  0, "easeInOutQuad" );
	
	
};




function showRatingOverlayIfNotRatedBefore() {
	var callback = function(data, status, xhr) {
		var isvalid = JSON.parse(data.responseText).valid;

		
		if(isvalid=='true') {
			 $(".videoratediv").switchClass( "hidden", "-", 0, "easeInOutQuad" );

			 //INITIALIZE MOVIE RATING CONTROL
		    $('#videoratingselect').barrating('show', {
		    	 theme: 'fontawesome-stars',
		         onSelect: function(value, text, event) {
		             if (typeof(event) !== 'undefined') {
		            	 $(".videoratediv").switchClass("-", "hidden", 0, "easeInOutQuad" );
		             	 videoRate(terrabisApp.currentVideo.id,value);
		             }
		         }
		         
		     });
		}
		
		
	};

	var formData = { videoid: terrabisApp.currentVideo.id};
	restajax(terrabisApp.validateRateDuplicateURI,formData,callback);			

} ;      



function videoRate(rating) {
	
	if(terrabisApp.isAuthenticated) {
			var callback = function(data, status, xhr) {
				$("#vidrateicon").switchClass("animated animated-repeat flash", " - ",  0, "easeInOutQuad" );
				$("#vidratestxt").html(JSON.parse(data.responseText).message);
				$("#vidratestxt_"+ terrabisApp.currentVideo.id).html(JSON.parse(data.responseText).message);
				
				
				
			};
			
			$("#vidrateicon").switchClass("-",  "animated animated-repeat flash", 0, "easeInOutQuad" ).delay(100);
			
			var formData = { videoid: terrabisApp.currentVideo.id, rating:rating};
			restajax(terrabisApp.rateVideoURI,formData,callback);		
			
	}
	else {
		
		swal("You need to sign-in", "Please sign-in to rate this video!. Click <a href=\"http://www.terrabis.com/authenticate/signin\">here</a>", "warning");
		
		
	}
	
	
	
};



function countVideoPlay() {
	var callback = function(data, status, xhr) {
		$("#vidcount-play-icon_"+terrabisApp.currentVideo.id).switchClass("animated animated-repeat flash text-muted", " - text-info ",  0, "easeInOutQuad" );
		$("#vidplaystxt_"+ terrabisApp.currentVideo.id).html(JSON.parse(data.responseText).message);
	};

	
	$("#vidcount-play-icon_"+ terrabisApp.currentVideo.id).switchClass("-",  "animated animated-repeat flash", 0, "easeInOutQuad" ).delay(100);
	
	var formData = { videoid:  terrabisApp.currentVideo.id};
	restajax(terrabisApp.countVideoPlayURI,formData,callback);			
	
};




function paginatorViewportCatalogURI() {
	$('.load-more-video').html('<span class="fa fa-2x fa-spinner fa-pulse"></span>');
	
	var queryData = { nextpage: true};

	
	$.ajax({ 
	    url:  terrabisApp.paginatorViewportCatalogURI, 
	    type: 'POST', 
	    dataType: 'json',
	    contentType: "application/json; charset=utf-8",
	    cache: false, // Force requested pages not to be cached by the browser
	    processData: false, // Avoid making query string instead of JSON		        		    
	    data: JSON.stringify(queryData),
	    beforeSend: function (request)
		{
			request.setRequestHeader("X-CSRF-TOKEN", terrabisApp.csrf_token);
		},
	    success: function(data) { 
	    	var PARTITIONS = data.videolist;
			if(PARTITIONS.length>0) {
				var html = jQuery(createHtml_GridResultView_Data(PARTITIONS,false));
				 $(html).appendTo('.load-more-data').hide().fadeIn(1000);
		
				
				//$('.load-more-data').html(html);
				$('.load-more-video').html('LOAD MORE...');
				
		 	} else {
		 		$('.load-more-video').html('');
		 	}
			
			
			if(data.paginator.lastPage) {
				$('.load-more-video').html('');
				terrabisApp.paginatorLastPage = true;
			}
			
		
	
	    },
	    error: function (xhr, status, error) {
	        console.log("load more video:ERROR:"+JSON.stringify(xhr));
	        console.log("load more video:ERROR:"+JSON.stringify(error));
	    }
	}); 

}




$( document ).ready(function() {

	
	

	$(document).delegate('.videorelatedpanel', 'click', function() {
		var videoid = this.dataset.videoid;
		console.log(videoid);
		$("#video-overlay-loadingvideo_"+videoid).switchClass( "hidden", "-", 0, "easeInOutQuad" );
	});
		
		
		
	
	/**********************************LOAD MORE ************************************************/
	$(document).delegate('.paginatorViewport', 'click', function() {
		paginatorViewportCatalogURI();
	});
	

	/**********************************MOUSE OVER VIDEO PANEL DESKTOPS ************************************************/
	$(document).delegate('.videopanel', 'mouseover', function() {

	
		//DESKTOP VIEW
		if(terrabisApp.normal) {
			terrabisApp.currentVideo = this.dataset;
			//IF HAS CLASS MARKER ANIMATE THEN STOP ANIMATE MAP MARKER 
			if($(this).hasClass("marker-animate")) {
				clearCurrentAnimateMarker();
				animateMapMarker(this.dataset.id,true);
				currentAnimateMarker.setZIndex(google.maps.Marker.MAX_ZINDEX + 1);
			}
		
			if(!blockVideoPanelMouseOver)
				if(!$("#vidoverlay_action_"+terrabisApp.currentVideo.id).hasClass("hold")) {
					$("#vidoverlay_action_"+terrabisApp.currentVideo.id).switchClass( "hidden", "-", 0, "easeInOutQuad" );
				}
			
		}

	});

	
	
	/**********************************MOUSE OUT VIDEO PANEL DESKTOPS ************************************************/
	$(document).delegate('.videopanel', 'mouseout', function() {
		if(terrabisApp.normal) {
			if(!$("#vidoverlay_action_"+terrabisApp.currentVideo.id).hasClass("hold")) {
				$("#vidoverlay_action_"+terrabisApp.currentVideo.id).switchClass("-", "hidden", 0, "easeInOutQuad" );
			}
			//terrabisApp.currentVideoOverlayId = -1;
			//terrabisApp.currentVideoOverlayURL = -1;
			blockMouseOver = false;
			//terrabisApp.currentVideo = null;
			
			//IF HAS CLASS MARKER ANIMATE THEN STOP ANIMATE MAP MARKER 
			if($(this).hasClass("marker-animate")) {
				stopAnimateMapMarker(true);
				currentAnimateMarker.setZIndex(google.maps.Marker.MAX_ZINDEX + 1);
			}
		}
	
	});
	
	
	//CLICK ONLY FOR MOBILE AND TABLETS
	$(document).delegate('.videopanel', 'click', function() {

		//MOBILE AND TABLETS
		if(!terrabisApp.normal) {
			terrabisApp.currentVideo = this.dataset;
			$("#video-overlay-loadingvideo_"+terrabisApp.currentVideo.id).switchClass( "hidden", "-", 0, "easeInOutQuad" );
			setTimeout(function (){
				window.location.href = terrabisApp.videoOneViewURI+terrabisApp.currentVideo.uuid;
				$("#video-overlay-loadingvideo_"+terrabisApp.currentVideo.id).switchClass( "-", "hidden", 0, "easeInOutQuad" );
			}, 1000);
			
		}
	});
	
	
	
	
	
	
	

	/**********************************MOUSE OVER PROMOTION POPUP  ************************************************/
	$(document).delegate('.promooverlaypanel', 'mouseover', function() {
		terrabisApp.currentVideo = this.dataset;

		//FETCH DATA OF VIDEO
		var callback = function(data, status, xhr) {

			var vid = JSON.parse(data.responseText).video;
			//SHOW PREVIEW POPUP 
			showMapVideoPreview(15,vid.title,vid.description,vid.likescount,vid.playscount,vid.ratingssum, vid.thumburl,vid.promotemap);
			
		};
		var formData = { videoid: terrabisApp.currentVideo.id };
		//restajax(terrabisApp.videoDataURI,formData,callback);	

		
		//IF HAS CLASS MARKER ANIMATE THEN STOP ANIMATE MAP MARKER 
		if($(this).hasClass("marker-animate")) {
			clearCurrentAnimateMarker();
			animateMapMarker(this.dataset.id,true);
			currentAnimateMarker.setZIndex(google.maps.Marker.MAX_ZINDEX + 1);
		}
	
		
		//$("#vidoverlay_action_"+terrabisApp.currentVideo.id).switchClass( "hidden", "-", 0, "easeInOutQuad" );

	});
	
	
	
	
	/**********************************MOUSE OUT PROMO OVERLAY ************************************************/
	$(document).delegate('.promooverlaypanel', 'mouseout', function() {
		//$("#vidoverlay_action_"+terrabisApp.currentVideo.id).switchClass("-", "hidden", 0, "easeInOutQuad" );
		//terrabisApp.currentVideoOverlayId = -1;
		//terrabisApp.currentVideoOverlayURL = -1;
		blockMouseOver = false;
		//terrabisApp.currentVideo = null;
		
		//CLOSE MAP PREVIEW
		 hideMapVideoPreview();
		
		
		//IF HAS CLASS MARKER ANIMATE THEN STOP ANIMATE MAP MARKER 
		if($(this).hasClass("marker-animate")) {
			stopAnimateMapMarker(true);
			currentAnimateMarker.setZIndex(google.maps.Marker.MAX_ZINDEX + 1);
		}
	
	});
	
	
	/**********************************MOUSE CLICK PROMOVERLAY CLOSE ************************************************/
	$(document).delegate('.promooverlay-close', 'click', function() {
		   $("#promooverlaypanel-"+terrabisApp.currentVideo.id).fadeOut( "slow", function() {
		   });
	});

	
	
	/**********************************MOUSE CLICK ADVERT MAP OVERLAY CLOSE ************************************************/	
	$(document).delegate('.advertoverlay-map-close', 'click', function() {
		var callback = function(data, status, xhr) {
		};

		var newAdvertType = 'MAPOVERLAYADVERT';
		var formData = { advertType: newAdvertType};
		restajax(terrabisApp.PERSAdvertShownURI,formData,callback);
		
		
	   $(".advertoverlay-map").fadeOut( "slow", function() {
	   });
	});
	
	
	
	/**********************************MOUSE CLICK ADVERT MAP OVERLAY CLOSE ************************************************/	
	$(document).delegate('.advertoverlay-map-img', 'click', function() {
		var callback = function(data, status, xhr) {
		};

		var newAdvertType = 'MAPOVERLAYADVERT';
		var formData = { advertType: newAdvertType};
		restajax(terrabisApp.PERSAdvertShownURI,formData,callback);
		
		
	   $(".advertoverlay-map").fadeOut( "slow", function() {
	   });
	});
	
	
	
	
	/**********************  MOUSE CLICK VIDEO close  ************************************************************************/
	$(document).delegate('.vidaction-close', 'click', function() {
		$("#vidoverlay_action_"+terrabisApp.currentVideo.id).switchClass( "hidden", "-", 0, "easeInOutQuad" );
		$("#vidoverlay_play_iframe"+terrabisApp.currentVideo.id).attr( "src", " ");
		$("#vidoverlay_play_"+terrabisApp.currentVideo.id).switchClass("-", "hidden", 0, "easeInOutQuad" );
	});
	
	
	
	/**********************************MOUSE CLICK VIDEO PLAY ************************************************/
	$(document).delegate('.vidoverlay_playvidbtn', 'click', function() {

		if($(this).hasClass("fullscreen-play")) { 
			var callback = function(data, status, xhr) {
				fullScreenOverlayVideoPlay(JSON.parse(data.responseText).video);
				countVideoPlay();
			};
	
			
			var formData = { videoid: terrabisApp.currentVideo.id };
			//restajax(terrabisApp.videoDataURI,formData,callback);	
		} else {
			blockVideoPanelMouseOver = true;
			$("#vidoverlay_action_"+terrabisApp.currentVideo.id).switchClass( "-", "hidden", 0, "easeInOutQuad" );
			$("#video-overlay-loadingvideo_"+terrabisApp.currentVideo.id).switchClass( "hidden", "-", 0, "easeInOutQuad" );
			setTimeout(function (){
				window.location.href = terrabisApp.videoOneViewURI+terrabisApp.currentVideo.uuid;
				$("#video-overlay-loadingvideo_"+terrabisApp.currentVideo.id).switchClass( "-", "hidden", 0, "easeInOutQuad" );
			}, 1000);
		}

	});
	
	
	$(document).delegate('.vidoverlay__playvidbtn-within', 'click', function() {

		var videoDTO = terrabisApp.currentVideo;
		$("#vidoverlay_action_"+videoDTO.id).switchClass( "-", "hidden", 1000, "easeInOutQuad" );
		$("#vidoverlay_play_"+videoDTO.id).switchClass( "hidden", "-", 1000, "easeInOutQuad" );
		$("#vidoverlay_play_iframe"+videoDTO.id).attr( "src", videoDTO.videourl+"?autoplay=1&modestbranding=1&showinfo=0&rel=0&title=0&byline=0&portrait=0");
		countVideoPlay();		
		
		
	});

	
	
	

	/**********************************PROMO CLICK VIDEO PLAY ************************************************/
	$(document).delegate('.promooverlay_playvidbtn', 'click', function() {

		var callback = function(data, status, xhr) {
			fullScreenOverlayVideoPlay(JSON.parse(data.responseText).video);
			countVideoPlay();
		};

		
		var formData = { videoid: terrabisApp.currentVideo.id };
		//restajax(terrabisApp.videoDataURI,formData,callback);			

	});
	
	/**********************************MOUSE CLICK VIDEO PLAY OF COLLECTION************************************************/
	$(document).delegate('.vidoverlay_playvidbtn_collection', 'click', function() {

		var currentvideoid =  terrabisApp.currentVideo.id;
		var collectionid = this.dataset.collectionid;

		var callback = function(data, status, xhr) {
			console.log(JSON.parse(data.responseText).collection);
			fullScreenOverlayCollectionPlay(JSON.parse(data.responseText).collection);
			
		};

		
		var formData = { collectionid: collectionid};
		//restajax(terrabisApp.videoCollectionDataURI,formData,callback);	
		
		
		

	});

	
	
	
	
	
	
	
	/**********************************MOUSE CLICK VIDEO PLAY OF FULL SCREEN ************************************************/
	$(document).delegate('.videopanel-full', 'click', function() {
		var callback = function(data, status, xhr) {
			fullScreenOverlayVideoPlay(JSON.parse(data.responseText).video);
			countVideoPlay();
		};

		
		var formData = { videoid: terrabisApp.currentVideo.id };
		//restajax(terrabisApp.videoDataURI,formData,callback);			

	});
	
	
	
	
	
	
	
	

	
		//EMBEDD LINK - SHOW VIDEO URI DIV
		$(document).delegate('.vidaction-link', 'click', function() {
			 $(".videoplay_link").switchClass("hidden", "-", 0, "easeInOutQuad" );
			 window.prompt("Save video link to clipboard. Press Ctrl+C and then Enter", $(".videoplay_link_text").html());
		});


	
		
		
	//LIKE CLICK ON ACTION BAR FUNCTION
	$(document).delegate('.vidaction-like', 'click', function() {
		
		
		if(terrabisApp.isAuthenticated) {
			
				
			var videoDTO = terrabisApp.currentVideo;
			terrabisApp.tempVideo = terrabisApp.currentVideo;
			$("#vidaction-result_"+videoDTO.id).html( "<span class=\"fa fa-thumbs-up fa-10x text-white\"></span" );
			$("#vidaction-play_"+videoDTO.id).fadeOut();
			$("#vidaction-result_"+videoDTO.id).fadeIn("slow");
	
				
				var callback = function(data, status, xhr) {	
					$("#vidaction-result_"+videoDTO.id).fadeOut();
					$("#vidaction-play_"+videoDTO.id).fadeIn();	
					$("#vidlikestxt_"+ terrabisApp.tempVideo.id).html(JSON.parse(data.responseText).message);

				};
				
				
				var formData = { videoid: terrabisApp.currentVideo.id };
				restajax(terrabisApp.countVideoLikeURI,formData,callback);
			
				
				
		}
		
		else
			
		{
			
			swal("You need to sign-in", "Please sign-in to like this video!. Click <a href=\"http://www.terrabis.com/authenticate/signin\">here</a>", "warning");
			
		}
		

	});
	
	


	
	
	
	
	


	//WATCH LATER CLICK ON ACTION BAR FUNCTION
	$(document).delegate('.vidaction-watchlater', 'click', function() {

		
		if(terrabisApp.isAuthenticated) {
				
			var videoDTO = terrabisApp.currentVideo;
			$("#vidaction-result_"+videoDTO.id).html( "<span class=\"fa fa-film fa-10x text-white\"></span" );
			$("#vidaction-play_"+videoDTO.id).fadeOut();
			$("#vidaction-result_"+videoDTO.id).fadeIn("slow");
	
				
				var callback = function(data, status, xhr) {	
					$("#vidaction-result_"+videoDTO.id).fadeOut();
					$("#vidaction-play_"+videoDTO.id).fadeIn();			
					
				};
				
				
				var formData = { videoid: terrabisApp.currentVideo.id };
				restajax(terrabisApp.addToWatchLaterURI,formData,callback);
			
				
				
		}
		
		else
			
		{
			
			swal("You need to sign-in", "Please sign-in to add this video to your watch later collection!. Click <a href=\"http://www.terrabis.com/authenticate/signin\">here</a>", "warning");
			
		}
		
	});

	
	//ACTION BUTTON CLICK ON MAP
	$(document).delegate('.vidaction-map', 'click', function() {
		

		
		$("#video-overlay-loadingvideo_"+terrabisApp.currentVideo.id).switchClass( "hidden", "-", 0, "easeInOutQuad" );
		setTimeout(function (){
			window.location.href = terrabisApp.mapURI+'?videouuid='+terrabisApp.currentVideo.uuid;
			$("#video-overlay-loadingvideo_"+terrabisApp.currentVideo.id).switchClass( "-", "hidden", 0, "easeInOutQuad" );
		}, 1000);
		
	});

	

	//ADD TO FAVORITES CLICK ACTION
	$(document).delegate('.vidaction-favorites', 'click', function() {
		
		
		if(terrabisApp.isAuthenticated) { 
			
			var videoDTO = terrabisApp.currentVideo;
			$("#vidaction-result_"+videoDTO.id).html( "<span class=\"fa fa-heart text-maroon fa-10x\"></span" );
			$("#vidaction-play_"+videoDTO.id).fadeOut();
			$("#vidaction-result_"+videoDTO.id).fadeIn("slow");
	
				
			var callback = function(data, status, xhr) {	
				$("#vidaction-result_"+videoDTO.id).fadeOut();
				$("#vidaction-play_"+videoDTO.id).fadeIn();			
				
			};
			
			
			var formData = { videoid: terrabisApp.currentVideo.id };
			restajax(terrabisApp.addToFavoritesURI,formData,callback);			

				
		} 
		
		else
		
		{
			
			swal("You need to sign-in", "Please sign-in to add this video to your favorites collection!. Click <a href=\"http://www.terrabis.com/authenticate/signin\">here</a>", "warning");
			
		}
		
	});


	
});	 //END DOCUMENT READY
	






//SET GRID VIEW STYLE
function initializeGridViewControls(gridviewstyle) {
		$("#gridviewstyle-control-BOX").switchClass("fa-2x animated animated-repeat flash text-info0", "-",0, "easeInOutQuad" );
		$("#gridviewstyle-control-THUMB").switchClass("fa-2x animated animated-repeat flash text-info0", "-",0, "easeInOutQuad" );
		$("#gridviewstyle-control-DETAIL").switchClass("fa-2x animated animated-repeat flash text-info0", "-",0, "easeInOutQuad" );
	
	
		
		if(gridviewstyle=='BOX') {
			$("#gridviewstyle-control-BOX").switchClass( "-", "fa-2x animated animated-repeat flash text-info0", 0, "easeInOutQuad" );
			
		}
		
		if(gridviewstyle=='THUMB')
			$("#gridviewstyle-control-THUMB").switchClass( "-", "fa-2x animated animated-repeat flash text-info0", 0, "easeInOutQuad" );
		
		if(gridviewstyle=='DETAIL')
			$("#gridviewstyle-control-DETAIL").switchClass( "-", "fa-2x animated animated-repeat flash text-info0", 0, "easeInOutQuad" );	
};
















