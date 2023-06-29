var blockMouseOver = false;



function showRatingOverlayIfNotRatedBeforeMap() {
	var callback = function(data, status, xhr) {
		var isvalid = JSON.parse(data.responseText).valid;

		
		if(isvalid=='true') {
			 $(".mapoverlay_video_rate").switchClass( "hidden", "-", 0, "easeInOutQuad" );

			 //INITIALIZE MOVIE RATING CONTROL
		    $('#videoratingselect').barrating('show', {
		         theme: 'bars-movie',
		         onSelect: function(value, text, event) {
		             if (typeof(event) !== 'undefined') {
		            	 $(".mapoverlay_video_rate").switchClass("-", "hidden", 0, "easeInOutQuad" );
		             	 videoRate(terrabisApp.currentVideoRateId,value);
		             }
		         }
		         
		     });
		}
		
		
	};

	var formData = { videoid: terrabisApp.currentVideoRateId};
	restajax(terrabisApp.validateRateDuplicateURI,formData,callback);			

}       


function showRatingOverlayIfNotRatedBefore() {
	var callback = function(data, status, xhr) {
		var isvalid = JSON.parse(data.responseText).valid;

		
		if(isvalid=='true') {
			 $(".vidoverlay_rate").switchClass( "hidden", "-", 0, "easeInOutQuad" );

			 //INITIALIZE MOVIE RATING CONTROL
		    $('#videoratingselect').barrating('show', {
		         theme: 'bars-movie',
		         onSelect: function(value, text, event) {
		             if (typeof(event) !== 'undefined') {
		            	 $(".vidoverlay_rate").switchClass("-", "hidden", 0, "easeInOutQuad" );
		             	 videoRate(terrabisApp.currentVideoRateId,value);
		             }
		         }
		         
		     });
		}
		
		
	};

	

	
	var formData = { videoid: terrabisApp.currentVideoPlayingId};
	restajax(terrabisApp.validateRateDuplicateURI,formData,callback);			

}  




function videoRate(videoid, rating) {
	var callback = function(data, status, xhr) {
		   //$(".mapoverlay_video_rate").switchClass( "-", "hidden", 0, "easeInOutQuad" );
	};

	var formData = { videoid: videoid,rating:rating};
	//var formData = { videoid: terrabisApp.currentVideoPlayingId,rating:ratingvalue};
	restajax(terrabisApp.rateVideoURI,formData,callback);			
	
};





function closeGridPlayVideo() {
	
	clearCurrentAnimateMarker();
	
	$("#vidoverlay_play_iframe"+terrabisApp.currentVideoPlayingId).attr( "src", "");
	$("#vidoverlay_play_"+terrabisApp.currentVideoPlayingId).switchClass("-", "hidden", 1000, "easeInOutQuad" );
	$("#vidoverlay_action_"+terrabisApp.currentVideoPlayingId).switchClass( "hidden", "-", 1000, "easeInOutQuad" );
	terrabisApp.currentVideoPlayingId = -1;
	terrabisApp.gridvideoplaying = false;

}


function closeMapPlayVideo() {

		stopAnimateMapMarker(false);
	
	
	   terrabisApp.mapvideoplaying = false;
	   $(".mapoverlay_video_play").switchClass( "-", "hidden", 0, "easeInOutQuad" );
	   $(".vidoverlay_play_map_iframe").attr( "src",""); 

	   //RESET VARIABLES
	   terrabisApp.currentVideoPlayingId = -1;
	   terrabisApp.mapvideoplaying = false;	
	   
	
}




function countVideoLike() {
	var callback = function(data, status, xhr) {
		terrabisApp.currentVideoActionIcon.switchClass("animated animated-repeat flash text-muted", " - text-info ",  0, "easeInOutQuad" );
		terrabisApp.currentVideoAction.switchClass("cursor-pointer", "-",  0, "easeInOutQuad" );
		$("#vidlikestxt_"+terrabisApp.currentVideoOverlayId).html(JSON.parse(data.responseText).message);

	};
	
	terrabisApp.currentVideoActionIcon.switchClass("-",  "animated animated-repeat flash", 0, "easeInOutQuad" ).delay(100);
	
	var formData = { videoid: terrabisApp.currentVideoOverlayId };
	restajax(terrabisApp.countVideoLikeURI,formData,callback);			
	
};



function countVideoPlay() {
	var callback = function(data, status, xhr) {
		$("#vidcount-play-icon_"+terrabisApp.currentVideoPlayingId).switchClass("animated animated-repeat flash text-muted", " - text-info ",  0, "easeInOutQuad" );
		$("#vidplaystxt_"+terrabisApp.currentVideoPlayingId).html(JSON.parse(data.responseText).message);
	};

	
	$("#vidcount-play-icon_"+terrabisApp.currentVideoPlayingId).switchClass("-",  "animated animated-repeat flash", 0, "easeInOutQuad" ).delay(100);
	
	var formData = { videoid: terrabisApp.currentVideoPlayingId };
	restajax(terrabisApp.countVideoPlayURI,formData,callback);			
	
};




function loadMoreVideoCatalog() {
	$('.load-more-video-catalog').html('<span class="fa fa-2x fa-spinner fa-pulse"></span>');
	
	var queryData = { nextpage: true};
	
	$.ajax({ 
	    url:  terrabisApp.paginatorVideoCatalogURI, 
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
				$('.load-more-video-catalog').html('LOAD MORE...');
				
		 	}
			
			else {
				//$('.load-more-data').html('');
			}
	
	    },
	    error: function (xhr, status, error) {
	        console.log("video catalog paginator:ERROR:"+JSON.stringify(xhr));
	        console.log("video catalog paginator:ERROR:"+JSON.stringify(error));
	    }
	}); 

}




$( document ).ready(function() {

	
	
	$("#mapviewbtngroup").change(function() {
		
		if($("input[id='mapviewmode_normal']").is(':checked')) {
			 document.location.href = terrabisApp.mapPageURI+'?mapmode=normal';
		}
		else if ($("input[id='mapviewmode_full']").is(':checked')) {
			 document.location.href = terrabisApp.mapPageURI+'?mapmode=full';
		}
				
	});
	
	
	
	/**********************************LOAD MORE ************************************************/
	$(document).delegate('.load-more-video-catalog', 'click', function() {
		loadMoreVideoCatalog();
	});
	
	
	
	
	/**********************  CLOSE VIDEOMAP OVERLAY BUTTON  ********************************/
	$(document).delegate('.vidaction-map-close', 'click', function() {
		//KEEP VIDEO ID FOR RATING LATER
		terrabisApp.currentVideoRateId = terrabisApp.currentVideoPlayingId; 
		   
		closeMapPlayVideo();
		
		//RATE IF IT IS AUTHENTICATED
		if(terrabisApp.isAuthenticated)
			showRatingOverlayIfNotRatedBeforeMap();
		
	});
	
	/**********************  CLOSE VIDEOMAP OVERLAY BUTTON  ********************************/
	$(document).delegate('.vidaction-video-close', 'click', function() {
		//KEEP VIDEO ID FOR RATING LATER
		terrabisApp.currentVideoRateId = terrabisApp.currentVideoPlayingId; 
		closeGridPlayVideo();
		
		//RATE IF IT IS AUTHENTICATED
		if(terrabisApp.isAuthenticated)
			showRatingOverlayIfNotRatedBefore();
	});
		
	
	


	
	
	$(document).delegate('.videopanel', 'mouseover', function() {
		terrabisApp.currentvideodataset = this.dataset;
		terrabisApp.currentVideoOverlayId = this.dataset.videoid;
		terrabisApp.currentVideoOverlayURL = this.dataset.videourl;
		

		if(blockMouseOver)
			return;
		
	
		//IF HAS CLASS MARKER ANIMATE THEN STOP ANIMATE MAP MARKER 
		if($(this).hasClass("marker-animate")) {
			clearCurrentAnimateMarker();
			animateMapMarker(this.dataset.videoid,true);
			currentAnimateMarker.setZIndex(google.maps.Marker.MAX_ZINDEX + 1);
		}
	
		
		$("#vidoverlay_action_"+terrabisApp.currentVideoOverlayId).switchClass( "hidden", "-", 0, "easeInOutQuad" );

	});

	
	
	//MOUSEOUT ON GRID COLUMN 
	$(document).delegate('.videopanel', 'mouseout', function() {
		$("#vidoverlay_action_"+terrabisApp.currentVideoOverlayId).switchClass("-", "hidden", 0, "easeInOutQuad" );
		//terrabisApp.currentVideoOverlayId = -1;
		//terrabisApp.currentVideoOverlayURL = -1;
		blockMouseOver = false;
		terrabisApp.currentvideodataset = null;
		
		//IF HAS CLASS MARKER ANIMATE THEN STOP ANIMATE MAP MARKER 
		if($(this).hasClass("marker-animate")) {
			stopAnimateMapMarker(false);
			currentAnimateMarker.setZIndex(google.maps.Marker.MAX_ZINDEX + 1);
		}
		
		

	});
	
	
	//CLICK ON GRID COLUMN ONLY FOR FULLSCREEN  
	$(document).delegate('.videopanel', 'click', function() {
		if($(this).hasClass("video-play")) {

			//CLOSE OTHER VIDEOS PLAYING WITHIN GRID
			if(terrabisApp.gridvideoplaying) {
				closeGridPlayVideo();
			}
			
			//IF MAP VIDEO IS PLAYING THEN STOP MAP VIDEO
			if(terrabisApp.mapvideoplaying) {
				closeMapPlayVideo();
			}
		
			//SET CURRENT VIDEO PLAYING ID
			terrabisApp.currentVideoPlayingId = terrabisApp.currentVideoOverlayId;
			terrabisApp.gridvideoplaying = true;
			
			fullScreenOverlayVideoPlay(terrabisApp.currentvideodataset);
			
			//COUNT VIDEOPLAY
			countVideoPlay();
			
			
			//ANIMATE MARKER
			animateMapMarker(terrabisApp.currentVideoOverlayId,true);
			
		
		}
	});
	
	
	$(document).delegate('.vidoverlay_playvidbtn', 'click', function() {
		
		
		//CLOSE OTHER VIDEOS PLAYING WITHIN GRID
		if(terrabisApp.gridvideoplaying) {
			closeGridPlayVideo();
		}
		
		//IF MAP VIDEO IS PLAYING THEN STOP MAP VIDEO
		if(terrabisApp.mapvideoplaying) {
			closeMapPlayVideo();
		}

		
		//SET CURRENT VIDEO PLAYING ID
		terrabisApp.currentVideoPlayingId = terrabisApp.currentVideoOverlayId;
		terrabisApp.gridvideoplaying = true;
		
		//IF HAS CLASS INSIDEPLAY THEN PLAY WITHIN BOX OR ELSE PLAY ON FULL SCREEN OVERLAY
		if($(this).hasClass("inside-play")) {
			$("#vidoverlay_action_"+terrabisApp.currentVideoOverlayId).switchClass( "-", "hidden", 1000, "easeInOutQuad" );
			$("#vidoverlay_play_"+terrabisApp.currentVideoOverlayId).switchClass( "hidden", "-", 1000, "easeInOutQuad" );
			$("#vidoverlay_play_iframe"+terrabisApp.currentVideoOverlayId).attr( "src", terrabisApp.currentVideoOverlayURL+"?autoplay=1&rel=0&modestbranding=1");
		}
		
		else{
			fullScreenOverlayVideoPlay(terrabisApp.currentvideodataset);
		
		}
		
		//COUNT VIDEOPLAY
		countVideoPlay();
		
		
		//ANIMATE MARKER
		animateMapMarker(terrabisApp.currentVideoOverlayId,true);

			
	});

	
	

	
	//WATCH LATER CLICK ON ACTION BAR FUNCTION
	$(document).delegate('.vidaction-like', 'click', function() {
		
		terrabisApp.currentVideoAction = $(this);
		terrabisApp.currentVideoActionIcon = $(this).children('em:first');
		
		/*******************************************COUNT VIDEO LIKE **********************************/
		countVideoLike();

	});


	//WATCH LATER CLICK ON ACTION BAR FUNCTION
	$(document).delegate('.vidaction-watchlater', 'click', function() {
		
		terrabisApp.currentVideoAction = $(this);
		terrabisApp.currentVideoActionIcon = $(this).children('em:first');
		
		
		//IF ALREADY CLICK RETURN 
		if(terrabisApp.currentVideoActionIcon.hasClass("fa-check"))
				return;
		
		var callback = function(data, status, xhr) {
			terrabisApp.currentVideoActionIcon.switchClass("fa-refresh fa-spin", "fa-check text-inverse",  0, "easeInOutQuad" );
			terrabisApp.currentVideoAction.switchClass("cursor-pointer", "-",  0, "easeInOutQuad" );
		};
		
		terrabisApp.currentVideoActionIcon.switchClass("fa-plus",  "fa-refresh fa-spin", 0, "easeInOutQuad" ).delay(1000);
		
		var formData = { videoid: terrabisApp.currentVideoOverlayId };
		restajax(terrabisApp.addToWatchLaterURI,formData,callback);
		
	});



	//ADD TO FAVORITES CLICK ACTION
	$(document).delegate('.vidaction-favorites', 'click', function() {
		
		terrabisApp.currentVideoAction = $(this);
		terrabisApp.currentVideoActionIcon = $(this).children('em:first');
		
		
		//IF ALREADY CLICK RETURN 
		if(terrabisApp.currentVideoActionIcon.hasClass("fa-check"))
				return;
		
		var callback = function(data, status, xhr) {
			terrabisApp.currentVideoActionIcon.switchClass("fa-refresh fa-spin", "fa-check text-inverse",  0, "easeInOutQuad" );
			terrabisApp.currentVideoAction.switchClass("cursor-pointer", "-",  0, "easeInOutQuad" );
		};
		
		terrabisApp.currentVideoActionIcon.switchClass("fa-plus",  "fa-refresh fa-spin", 0, "easeInOutQuad" ).delay(1000);
	
	
		var formData = { videoid: terrabisApp.currentVideoOverlayId };
		restajax(terrabisApp.addToFavoritesURI,formData,callback);
		
	});


	//MAP ZOOM
	$(document).delegate('.vidaction-mapzoom', 'click', function() {
		terrabisApp.currentVideoAction = $(this);
		terrabisApp.currentVideoActionIcon = $(this).children('em:first');
		
		terrabisApp.currentVideoActionIcon.switchClass("fa-map-marker",  "fa-spinner fa-pulse", 0, "easeInOutQuad" ).delay(1000);
		window.location.href =  terrabisApp.mapVideoZoomURI+'?video='+terrabisApp.currentVideoOverlayId;
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
















