var currentAnimateMarker;


/**********************  CLEAR MAP FROM MARKERS  ********************************/
function clearMapFromMarkers() {
	
	//CLEAR PREVIOUS MARKERS
	for (var i=0; i<vidMapMarkerArray.length; i++) {
		var vidMarker = vidMapMarkerArray[i];
		vidMarker.setMap(null);
	};
	
	vidMapMarkerArray = new Array();
	
};




/**********************  ANIMATE MAP MARKER  ********************************/
function animateMapMarker(videoid,markerimg) {
		//ANIMATE
	   for(i=0; i<vidMapMarkerArray.length; i++) {
		   			if (vidMapMarkerArray[i].getAnimation() == null && vidMapMarkerArray[i].vid.id==videoid) {
		   			currentAnimateMarker = vidMapMarkerArray[i]; 	
		   			if(markerimg) {currentAnimateMarker.setIcon(new google.maps.MarkerImage(terrabisApp.googleMarkerPlayImgURL));}	
		   			currentAnimateMarker.setAnimation(google.maps.Animation.BOUNCE);
				}
					
	   }
}

function stopAnimateMapMarker(markerimg) {
			if(markerimg) {currentAnimateMarker.setIcon(new google.maps.MarkerImage(terrabisApp.googleMarkerImgURL));}	
			currentAnimateMarker.setAnimation(null);

}

function clearCurrentAnimateMarker() {
	if(currentAnimateMarker!=null) {
		currentAnimateMarker.setIcon(new google.maps.MarkerImage(terrabisApp.googleMarkerImgURL));
		currentAnimateMarker.setAnimation(null);
		currentAnimateMarker = null;
	}
}







/**********************  FIT BOUNDS  ********************************/
function fitBoundsToVisibleMarkers() {

    //var bounds = new google.maps.LatLngBounds();
    var bounds = terrabisApp.globalMap.getBounds();
    

    for (var i=0; i<vidMapMarkerArray.length; i++) {
        if(vidMapMarkerArray[i].getVisible()) {
            bounds.extend( vidMapMarkerArray[i].getPosition() );
        }
    }

    terrabisApp.globalMap.fitBounds(bounds);
}



/**********************  CREATE BOUNDS ON VISIBLE MARKERS  ********************************/
function createBoundsOnVisibleMarkers() {

    var bounds = new google.maps.LatLngBounds();

    for (var i=0; i<vidMapMarkerArray.length; i++) {
        if(vidMapMarkerArray[i].getVisible()) {
            bounds.extend( vidMapMarkerArray[i].getPosition() );
        }
    }

    terrabisApp.globalMap.fitBounds(bounds);
}




/**********************  CREAT MAP BASED ON SEARCH RESULT  ********************************/
function createMapBasedOnSearchResult(GMAPRESULT,mapType) {



	var map;
	
	var address = GMAPRESULT.address;
	var zoomLevel = GMAPRESULT.zoomlevel;
	var emptyStatus = GMAPRESULT.emptyStatus;
	var fitBounds = GMAPRESULT.fitBounds;
	var reverseGeocode = GMAPRESULT.reverseGeocode;
	var mapCenterLatlng = new google.maps.LatLng(GMAPRESULT.location.lat,GMAPRESULT.location.lng);
	var mainMarkerCenterLatlng = new google.maps.LatLng(GMAPRESULT.markerlocation.lat,GMAPRESULT.markerlocation.lng);
	
	
	
	if(emptyStatus)
		zoomLevel = 5;
	
	
		
	var southWest = new google.maps.LatLng(GMAPRESULT.viewport.southwest.lat, GMAPRESULT.viewport.southwest.lng);
	var northEast = new google.maps.LatLng(GMAPRESULT.viewport.northeast.lat, GMAPRESULT.viewport.northeast.lng);
	var viewport = new google.maps.LatLngBounds(southWest,northEast);


var mapOptions = {
  center: mapCenterLatlng,
  zoom:zoomLevel,
  minZoom: 3,
  streetViewControl: false,
  styles: mapStyleArrayWater,
  mapTypeId: mapType
  };

map = new google.maps.Map(document.getElementById("googlemap"), mapOptions);



		//FIT BOUNDS
		if(!emptyStatus && !reverseGeocode  && fitBounds) {
			map.fitBounds(viewport);
		}


		//CREATE AN OVERLAY TO GEWT JUST THE MOUSEMOVEMENT
		var overlay = new google.maps.OverlayView();
		overlay.draw = function() {};
		overlay.setMap(map);
		terrabisApp.mapOverlay = overlay;  
		

return map;

};


/**********************  CENTER MAP  ********************************/
function centerMap(lat,lng) {
	terrabisApp.globalMap.setCenter(new google.maps.LatLng(lat,lng));
};

/**********************  MOVE MAPCENTER MARKER   ********************************/
function moveMapCenterMarker(lat,lng) {
	terrabisApp.mapCenterMarker.setPosition(new google.maps.LatLng(lat,lng));
};




/**********************  CREAT MAP STANDARD  ********************************/
function createMapStandard(lat,lng,mapType) {
	var map;
	var zoomLevel = 5;
	var mapCenterLatlng = new google.maps.LatLng(lat,lng);
        try {
	         

	        var mapOptions = {
	          zoom:zoomLevel,
	          minZoom: 3,
	          streetViewControl: false,
	          center: mapCenterLatlng,
	          styles: mapStyleArrayWater,
	          mapTypeId: mapType
              };
	            
	       
	        map = new google.maps.Map(document.getElementById("googlemap"), mapOptions);
            
    } catch (err){
        alert(err);
    }		

	return map;
	
};


/**********************  CREAT MAP STANDARD  ********************************/
function createMapStandard(lat,lng) {
	var map;
	var zoomLevel = 5;
	var mapCenterLatlng = new google.maps.LatLng(lat,lng);
        try {
	         

	        var mapOptions = {
	          zoom:zoomLevel,
	          minZoom: 3,
	          streetViewControl: false,
	          center: mapCenterLatlng,
	          styles: mapStyleArrayWater,
	          mapTypeId: google.maps.MapTypeId.ROADMAP
              };
	            
	       
	        map = new google.maps.Map(document.getElementById("googlemap"), mapOptions);
            
    } catch (err){
        alert(err);
    }		

	return map;
	
};


/**********************  CREATE MAP CENTER MARKER  ********************************/
function createMapCenterMarker(isdraggable,moveonclick) {
	






	var markerImage = new google.maps.MarkerImage(terrabisApp.googleMarkerCenterMapImgURL);
	var markerPosition = terrabisApp.globalMap.getCenter();
	
	var marker = new google.maps.Marker({
            position: markerPosition,  
            map: terrabisApp.globalMap, 
            draggable: isdraggable,
            icon: markerImage
   });

	terrabisApp.mapCenterMarker = marker;
	
	//ADD LISTENER ON CLICK MAP
	if(moveonclick) {
		google.maps.event.addListener(terrabisApp.globalMap, "click", function (e) {
		    var location = e.latLng;
		    terrabisApp.mapCenterMarker.setPosition(location);
			$('#latitude').val(location.lat());
			$('#longitude').val(location.lng());
			//$('#footageloctxt').val(location.lat()+" "+location.lng());	    
		});
	}

	
	//ADD LISTENER ON DRAG MARKER
	if(isdraggable) {
		terrabisApp.mapCenterMarker.addListener('dragend', function() {
			$('#latitude').val(terrabisApp.mapCenterMarker.getPosition().lat());
			$('#longitude').val(terrabisApp.mapCenterMarker.getPosition().lng());
			//$('#footageloctxt').val(terrabisApp.mapCenterMarker.getPosition().lat()+" "+terrabisApp.mapCenterMarker.getPosition().lng());
	    });
	
	}	
	
	
};







/**********************  CREATE VIDEO MARKERS ON MAP  ********************************/
function createSimpleVideoMarker(vid,markerImage) {
	
	var map = terrabisApp.globalMap; 
	
	var infowindow_title = vid.title;
	//var markerImage = new google.maps.MarkerImage("http://maps.google.com/mapfiles/ms/icons/blue-dot.png");
	//var markerImage = new google.maps.MarkerImage(terrabisApp.googleMarkerImgURL);
	var markerPosition = new google.maps.LatLng(vid.latitude,vid.longitude);
	
	
	var marker = new google.maps.Marker({
            position: new google.maps.LatLng(vid.latitude,vid.longitude),  
            map: map, 
            draggable: false,
            icon: markerImage,
            title:infowindow_title
   });

   
   marker.vid = vid;	
   vidMapMarkerArray.push(marker);   
	
	
};





/**********************  CREATE VIDEO MARKER WITH VIDEO OVERLAY   ********************************/
function createVideoMarkerWithVideoOverlay(vid,markerImage) {
	
	var map = terrabisApp.globalMap;
	
	var infowindow_title = vid.title;
	//var markerImage = new google.maps.MarkerImage("http://maps.google.com/mapfiles/ms/icons/blue-dot.png");
	//var markerImage = new google.maps.MarkerImage(terrabisApp.googleMarkerImgURL);
	var markerPosition = new google.maps.LatLng(vid.latitude,vid.longitude);
	
	
	var marker = new google.maps.Marker({
            position: new google.maps.LatLng(vid.latitude,vid.longitude),  
            map: map, 
            draggable: false,
            icon: markerImage,
            title:infowindow_title
   });

	
	
	/*		
   marker.infowindow = new google.maps.InfoWindow({
				content: infowindow_html
	});*/
	
   
   marker.vid = vid;	
   marker.videoplaying = false;
	
   marker.addListener('mouseover', function() {

	   
	   		//HIDE PREVIEWS OPENED RATE IF ANY
	   		$(".mapoverlay_video_rate").switchClass("-", "hidden", "easeInOutQuad" );
	   
	   
	   		if(terrabisApp.currentVideoPlayingId==this.vid.id) {
	   			return;
	   		}
	   			
	   		
	   
	   		var projection = terrabisApp.mapOverlay.getProjection(); 
	    	var currentpixel = projection.fromLatLngToContainerPixel(this.getPosition());
	    	var centerpixel = projection.fromLatLngToContainerPixel(this.getMap().getCenter());
	    	rightoffset_x_preview = centerpixel.x*2-400;
	    	rightoffset_x_play = centerpixel.x*2-640;
	    	
	    	
	    	terrabisApp.mapoverlay_preview =currentpixel.x>400  ? 15 : rightoffset_x_preview+15;
	    	terrabisApp.mapoverlay_video = currentpixel.x>400  ? 15 : rightoffset_x_play+15;
	    	
	    	

	    	$(".mapoverlay_video_play").css({top: 0, left: terrabisApp.mapoverlay_video, position:'absolute'});
	    	$(".mapoverlay_video_preview").css({top: 0, left: terrabisApp.mapoverlay_preview, position:'absolute'});
	    	
	    	//DONT NEED TO MOVE RATE CAUSE ITS HIDDEN
	    	//$(".mapoverlay_video_rate").css({top: 0, left: terrabisApp.mapoverlay_preview, position:'absolute'});
	    	
	    	
	    	
	    	
	    	//SET CURRENT VIDEO ID AND VIDEO URL AND VIDEO THUMBNAIL
			terrabisApp.currentVideoOverlayId = this.vid.videoid;
			terrabisApp.currentVideoOverlayURL = this.vid.videourl;
			terrabisApp.currentVideoOverlayThumbURL = this.vid.thumburl;
			
			//SHOW PREVIEW
			
			
		    $(".vidoverlay_preview_map_title").text(this.vid.title);
		    $(".vidoverlay_preview_map_likes").text(this.vid.likescount > 0 ? this.vid.likescount : "");
		    $(".vidoverlay_preview_map_plays").text(this.vid.playscount > 0 ? this.vid.playscount : "");
		    $(".vidoverlay_preview_map_reviews").text(this.vid.reviewscount > 0 ? this.vid.reviewscount : "");
		    
		    
		    
		
		    
		    
			$(".mapoverlay_video_preview").switchClass("hidden", "-", 0, "easeInOutQuad" );
			
			
			//IF VIDEO IS LINK SHOW IMG ELSE SHOW IFRAME
			if(vid.link) {
				$(".vidoverlay_preview_map_iframe").switchClass("-", "hidden", 0, "easeInOutQuad" );
				$(".vidoverlay_preview_map_img").switchClass("hidden", "-", 0, "easeInOutQuad" );
				$(".vidoverlay_preview_map_img").attr( "src", terrabisApp.currentVideoOverlayThumbURL);
				$(".vidoverlay_rate_map_img").attr( "src", terrabisApp.currentVideoOverlayThumbURL);
			}
			   else {
					$(".vidoverlay_preview_map_img").switchClass("-", "hidden", 0, "easeInOutQuad" );
				   $(".vidoverlay_preview_map_iframe").switchClass("hidden", "-", 0, "easeInOutQuad" );
				   $(".vidoverlay_preview_map_iframe").attr( "src", terrabisApp.videoPlayURL+this.vid.videoURI+"&style='map-thumb'");	
			}
				   

		   
   });
   
   
   marker.addListener('mouseout', function() {
	   

	   $(".mapoverlay_video_preview").switchClass("-", "hidden", 0, "easeInOutQuad" );
	   $(".vidoverlay_preview_map_title").text( "");
	   $(".vidoverlay_preview_map_iframe").attr( "src", "");
	   $(".vidoverlay_preview_map_img").attr( "src", "");
	   
	   
   });
   
   
   marker.addListener('click', function() {
	   
	   //	
	   clearCurrentAnimateMarker();
		   
		
	   //SET BOUNCE ANIMATION AND CLOSE OTHER BOUNCES
	   if(!this.videoplaying) {
		   
			if(terrabisApp.gridvideoplaying) {
				closeGridPlayVideo();
			}
			
		   $(".mapoverlay_video_preview").switchClass("-", "hidden", 0, "easeInOutQuad" );
		   $(".mapoverlay_video_play").switchClass( "hidden", "-", 0, "easeInOutQuad" );
		   $(".map-vidaction-share").attr("href", "https://www.facebook.com/sharer/sharer.php?u=http://www.terrabis.com/video/"+this.vid.id);
		   $(".videoratingtitle").html(this.vid.title);
		   
		   
		    
		   
		   
		   this.videoplaying = true;
		   terrabisApp.currentVideoPlayingId =  this.vid.id;
		   terrabisApp.mapvideoplaying = true;
	
		   	currentAnimateMarker = this;
   			currentAnimateMarker.setIcon(new google.maps.MarkerImage(terrabisApp.googleMarkerPlayImgURL));	
   			currentAnimateMarker.setAnimation(google.maps.Animation.BOUNCE);
		   
		   
/*		   this.setIcon(new google.maps.MarkerImage(terrabisApp.googleMarkerPlayImgURL));
		   this.setAnimation(google.maps.Animation.BOUNCE);
*/			
		   
		   if(this.vid.link) 
			   $(".vidoverlay_play_map_iframe").attr( "src", this.vid.videourl+"?autoplay=1&rel=0&modestbranding=1");	   
			else 
			   $(".vidoverlay_play_map_iframe").attr( "src", terrabisApp.videoPlayURL+this.vid.videoURI+"&style='map'");	
			
			
			//COUNT PLAY
			countVideoPlay();
			

	   }
	   
	   else {
		   terrabisApp.currentVideoPlayingId = -1;
		   this.videoplaying = false;
		   terrabisApp.mapvideoplaying = false;
		   
		   
		   clearCurrentAnimateMarker();
		   
		   //this.setAnimation(null);
		   //this.setIcon(new google.maps.MarkerImage(terrabisApp.googleMarkerImgURL));
		   
		   
		   $(".mapoverlay_video_play").switchClass( "-", "hidden", 0, "easeInOutQuad" );
		   $(".vidoverlay_play_map_iframe").attr( "src","");
		   (".videoratingtitle").html("");
		   
		   

		   

	   }
	   
	   
   });   


   vidMapMarkerArray.push(marker);   
	
	
};


function createVideoMarkersFromPartitions(PARTITIONS,videoOverlay) {
	
	var markerImage = new google.maps.MarkerImage(terrabisApp.googleMarkerImgURL);
	
	//CLEAR PREVIOUS MARKERS
	clearMapFromMarkers();
	

	for (var j=0; j<PARTITIONS.length; j++) {
		var VIDEOLIST = PARTITIONS[j];

		//LOOP THROUGH ALL VIDEOS AND CREATE VIDEO MARKER
			for (var i=0; i<VIDEOLIST.length; i++) {
				var vid = VIDEOLIST[i];
				
				//CREATE SINGLE VIDEO MARKER
				if(!videoOverlay)
					createSimpleVideoMarker(vid,markerImage);
				else
					createVideoMarkerWithVideoOverlay(vid,markerImage);
				
				
			}
	}
}; 







function createVideoMarkersFromList(VIDEOLIST,videoOverlay) {
	
	var markerImage = new google.maps.MarkerImage(terrabisApp.googleMarkerImgURL);
	
	//CLEAR PREVIOUS MARKERS
	clearMapFromMarkers();
	
	//LOOP THROUGH ALL VIDEOS AND CREATE VIDEO MARKER
	for (var i=0; i<VIDEOLIST.length; i++) {
		var vid = VIDEOLIST[i];
		
		//CREATE SINGLE VIDEO MARKER
		if(!videoOverlay)
			createSimpleVideoMarker(vid,markerImage);
		else
			createVideoMarkerWithVideoOverlay(vid,markerImage);

		
	}
	
}; 













/*function createMapOldandComplicate(GMAPRESULT, dragMarkerFlag, createFlag, positionMapCenterOnMarkerFlagForUploadPage, removeMapMarkerInfoWindow) {

	//console.log("create map:"+JSON.stringify(GMAPRESULT));
	var map;
	var address = GMAPRESULT.address;
	var zoomLevel = GMAPRESULT.zoomlevel;
	var emptyStatus = GMAPRESULT.emptyStatus;
	var fitBounds = GMAPRESULT.fitBounds;
	var reverseGeocode = GMAPRESULT.reverseGeocode;
	var mapCenterLatlng = new google.maps.LatLng(GMAPRESULT.location.lat,GMAPRESULT.location.lng);
	var mainMarkerCenterLatlng = new google.maps.LatLng(GMAPRESULT.markerlocation.lat,GMAPRESULT.markerlocation.lng);
	
	
	if(positionMapCenterOnMarkerFlagForUploadPage)
		mapCenterLatlng = mainMarkerCenterLatlng; 
	
		
	
		
	var southWest = new google.maps.LatLng(GMAPRESULT.viewport.southwest.lat, GMAPRESULT.viewport.southwest.lng);
	var northEast = new google.maps.LatLng(GMAPRESULT.viewport.northeast.lat, GMAPRESULT.viewport.northeast.lng);
	var viewport = new google.maps.LatLngBounds(southWest,northEast);

	
	var infowindow_html = "";
	var infowindow_title =  "";
	if(!emptyStatus) {
			infowindow_html = [
	         '<h3>'+address+'</h3>',
	         '<p>'+address+'</p>'
	     	].join('');
			
			
			infowindow_title = address;
		
	} else {
		infowindow_html =  [
               '<h3><i class="fa fa-frown-o fa-2x"></i> Nothing is found...</h3>',
               '<p>You might as well try another search</p>'
           ].join('');

		infowindow_title = 'Nothing is found...';
	}
	
	
	
	
	if(createFlag) {
        try {
	         

	        var mapOptions = {
	          center: mapCenterLatlng,
	          zoom:zoomLevel,
	          styles: mapStyleArrayWater,
	          mapTypeId: google.maps.MapTypeId.ROADMAP
              };
	            
	       
	        map = new google.maps.Map(document.getElementById("googlemap"), mapOptions);
            map.marker = new google.maps.Marker({
            	
            position: mainMarkerCenterLatlng,  map: map, draggable: dragMarkerFlag,
            title:infowindow_title
            });
	        //render new map and center around group of marker
	
            
            if(!removeMapMarkerInfoWindow) {
	            map.marker.infowindow = new google.maps.InfoWindow({
					content: infowindow_html
					});
		       
		        map.marker.addListener('click', function() {
		        	map.marker.infowindow.open(map, map.marker);
		        });
            }
	        
	        
	        if(dragMarkerFlag) {
		        map.marker.addListener('dragend', function() 
		        {
					$('#latitude').val(map.marker.getPosition().lat());
					$('#longitude').val(map.marker.getPosition().lng());
					$('#footageloctxt').val(map.marker.getPosition().lat()+" "+map.marker.getPosition().lng());
					
					
					var address = 'lat:'+map.marker.getPosition().lat()+' - lng:'+map.marker.getPosition().lng();

					map.marker.setTitle(address);
					
					
					if(!removeMapMarkerInfoWindow) {
						var infowindow_html = [
						      	         '<h3>'+address+'</h3>',
						      	         '<p>'+address+'</p>'
						      	     	].join('');
	
						var infowindow_title = address;
	
						map.marker.infowindow.close();
						
						
						map.marker.infowindow = new google.maps.InfoWindow({
							content: infowindow_html
							});
						
						
	
						map.marker.addListener('click', function() {
							map.marker.infowindow.open(map, map.marker);
							});
						
						
						map.marker.infowindow.open(map, map.marker);
					}
					
		        });
	        
        }

	        
	        
	        
	       
	        if(!emptyStatus && !reverseGeocode && fitBounds) {
	        					map.fitBounds(viewport);
	        } 
	        
	        
    } catch (err){
        alert(err);
    }		
		
	}

	return map;
	
};*/






