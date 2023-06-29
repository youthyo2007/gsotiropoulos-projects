var currentAnimateMarker;
var uploadMarker;





/********************** DRAW MARKERS PATH  ********************************/
function drawMarkersPath() {
	
	  for (i=0; i<terrabisApp.Path.length; ++i) {
		  var vid = terrabisApp.Path[i];
		 var position = new google.maps.LatLng(vid.latitude,vid.longitude);
		  
		   if(i==0) {
			   terrabisApp.vidPathOrigin = position;
		   }
		   else if(i==terrabisApp.Path.length-1) {
			   terrabisApp.vidPathDestination = position;
		   }
		   else {
			   vidPathWayPointsArray.push({
			       location: position,
			       stopover: true
			     });
		   }

	  }	

	
	var rendererOptions = {
			  map: terrabisApp.globalMap,
			  suppressMarkers : true
	};
	
	
	var directionsService = new google.maps.DirectionsService;
	var directionsDisplay = new google.maps.DirectionsRenderer(rendererOptions);
	
	

	  directionsService.route({
		  	origin: terrabisApp.vidPathOrigin,
		  	destination: terrabisApp.vidPathDestination,
		  	 waypoints: vidPathWayPointsArray,
		    travelMode: google.maps.TravelMode.DRIVING
		  }, function(response, status) {
		    if (status === google.maps.DirectionsStatus.OK) {
		      directionsDisplay.setDirections(response);

			  var myRoute = response.routes[0].legs[0];
			  for (var i = 0; i < myRoute.steps.length; i++) {
				  console.log(myRoute.steps[i].start_location+'-'+myRoute.steps[i].instructions);
			  }
		    
		    } else {
		      //window.alert('Directions request failed due to ' + status);
		    }
		 });
	
	
	  
	  
	  
	  
	  
	  
/*	
	 directionsService.route({
		    waypoints: vidMapMarkerLocationArray,
		    optimizeWaypoints: true,
		    travelMode: google.maps.TravelMode.DRIVING
		  }, 
		  
		  function(response, status) {
		    if (status === google.maps.DirectionsStatus.OK) {
		      directionsDisplay.setDirections(response);
		    } else {
		      window.alert('Directions request failed due to ' + status);
		    }
		  }
});*/
	 	
	
	
	
	/*
	
	var videoPath = new google.maps.Polyline({
	    path: vidMapMarkerLocationArray,
	    geodesic: true,
	    strokeColor: '#FF0000',
	    strokeOpacity: 1.0,
	    strokeWeight: 2
	  });

	videoPath.setMap(terrabisApp.globalMap);*/
	
};

/*
function calculateAndDisplayRoute(directionsService, directionsDisplay) {
	  directionsService.route({
	    origin: document.getElementById('start').value,
	    destination: document.getElementById('end').value,
	    travelMode: google.maps.TravelMode.DRIVING
	  }, function(response, status) {
	    if (status === google.maps.DirectionsStatus.OK) {
	      directionsDisplay.setDirections(response);
	    } else {
	      window.alert('Directions request failed due to ' + status);
	    }
	  });
}*/





/**********************  CLEAR MAP FROM MARKERS  ********************************/
function clearMapFromMarkers(arrayStorage) {
	
	//CLEAR PREVIOUS MARKERS
	for (var i=0; i<arrayStorage.length; i++) {
		var vidMarker = arrayStorage[i];
		vidMarker.setMap(null);
	};
	
	arrayStorage = new Array();
	
};




function highlightMarkerOnPlay(videoid,markerimg) {

	    //ANIMATE
	   for(i=0; i<viewportMapMarkerArray.length; i++) {
		   			if (viewportMapMarkerArray[i].getAnimation() == null && viewportMapMarkerArray[i].vid.id==videoid) {
		   			currentAnimateMarker = viewportMapMarkerArray[i]; 
		   			terrabisApp.currentMapMarker = viewportMapMarkerArray[i]; 
		   			if(markerimg) {
		   				currentAnimateMarker.setIcon(new google.maps.MarkerImage(terrabisApp.googleMarkerPlayImgURL));
		   			}	
		   			currentAnimateMarker.setAnimation(google.maps.Animation.BOUNCE);
				}
					
	   }
	
	
	
};



function clearVidMapFromMarkers() {
	
	//CLEAR PREVIOUS MARKERS
	for (var i=0; i<vidMapMarkerArray.length; i++) {
		var vidMarker = vidMapMarkerArray[i];
		vidMarker.setMap(null);
	};
	
	vidMapMarkerArray = new Array();
	
};



function clearViewportMapFromMarkers() {
	
	//CLEAR PREVIOUS MARKERS
	for (var i=0; i<viewportMapMarkerArray.length; i++) {
		var vidMarker = viewportMapMarkerArray[i];
		vidMarker.setMap(null);
	};
	
	viewportMapMarkerArray = new Array();
	
};



//CLEAR UPLOAD  MARKER 
function clearUploadMarker() {
	if(terrabisApp.uploadMarker!=null) {
		terrabisApp.uploadMarker.infowindow.close();
		terrabisApp.uploadMarker.setMap(null);
	}	
};


/**********************  CREATE UPLOAD MARKER ON RIGHT CLICK  ********************************/
function createUploadMarkerOnMapRightClick(event) {
	
	var map = terrabisApp.globalMap;

	var infowindow_title = "QUICK UPLOAD";
	var markerImage = new google.maps.MarkerImage(terrabisApp.googleMarkerCenterMapImgURL);
	var markerPosition = event.latLng;
	
	
	//SET UPLOAD LAT AND LNG
	terrabisApp.uploadLatitude = markerPosition.lat();
	terrabisApp.uploadLongitude = markerPosition.lng();
	
	
	//CLEAR PREVIOUS MARKER
	if(terrabisApp.uploadMarker!=null) {
		terrabisApp.uploadMarker.infowindow.close();
		terrabisApp.uploadMarker.setMap(null);
	}	
	
	var infowindow_html = [
							'<div class="text-center bg-center"><div class="panel widget" style="background: white;">',
/*					     '<div class="row mt-lg">',
					             '<div class="col-md-12 col-xs-12 col-sm-12">',
					              '<div class="h4 text-gray-dark text-bold">CLICK TO UPLOAD A VIDEO ON THIS PLACE</div>',
					           '</div>',
					             '<div class="col-xs-3">',
					              '<span class="label label-primary pull-right"></span>',
					           '</div>',
					    '</div>',*/
						'<div class="row">',
			   				'<div class="col-md-12 col-sm-12 col-xs-12"><br/><br/>',
			   	  			'<div class="form-group">',
			                          '<a href="'+terrabisApp.uploadIndexURI+'?lat='+markerPosition.lat() +'&lng='+markerPosition.lng()+'" class="btn btn-labeled btn-info-dark cursor-pointer">',
			                    		   '<span class="h4 mt-lg text-uppercase text-lef pull-left">',
			                               'upload a video here</span>',
			                           '</a>',
			                  '</div>',
			   	  			 '</div>',
						'</div>',				    
					'</div>',
					'</div>'
					].join('');			
					
	
	
	terrabisApp.uploadMarker = new google.maps.Marker({
            position:markerPosition,  
            map: terrabisApp.globalMap, 
            draggable: true,
            icon: markerImage,
            title:infowindow_title
   });

	
	
	
	 //document.getElementById("infobox").html(infowindow_html);
	
	/*terrabisApp.uploadMarker.infowindow = new InfoBox({
        //content: document.getElementById("infobox"),
        content:infowindow_html,
        disableAutoPan: false,
        maxWidth: 150,
        pixelOffset: new google.maps.Size(-140, 0),
        zIndex: 99,
        boxStyle: {
           background: "url('http://google-maps-utility-library-v3.googlecode.com/svn/trunk/infobox/examples/tipbox.gif') no-repeat",
           opacity: 0.75,
           width: "280px"
       },
       closeBoxMargin: "12px 4px 2px 2px",
       closeBoxURL: "http://www.google.com/intl/en_us/mapfiles/close.gif",
       infoBoxClearance: new google.maps.Size(1, 1)
   });
   
	
	terrabisApp.uploadMarker.addListener('click', function() {
		this.infowindow.open(terrabisApp.globalMap, this);
	});*/
	
	

	
	
	
	
	//INFO WINDOW CODE REPLACE BY INFO BOX TODO ABOVE
	terrabisApp.uploadMarker.infowindow = new google.maps.InfoWindow({
				content: infowindow_html
	});
            
   
	terrabisApp.uploadMarker.addListener('click', function() {
				this.infowindow.open(terrabisApp.globalMap, this);
				
	});
	
	

	terrabisApp.uploadMarker.infowindow.open(terrabisApp.globalMap,terrabisApp.uploadMarker);	

};



function arrayContainsMapMarker(videoid,arrayStorage) {
	
	  var exists = false; 
	  	
	if(arrayStorage=='viewportMapMarkerArray') {
		   for(i=0; i<viewportMapMarkerArray.length; i++) {
			   			if (viewportMapMarkerArray[i].vid.id==videoid) {
			   				exists = true;
					}
						
		   }
	}
	
	if(arrayStorage=='vidMapMarkerArray') {
		   for(i=0; i<vidMapMarkerArray.length; i++) {
			   			if (vidMapMarkerArray[i].vid.id==videoid) {
			   				exists = true;
					}
						
		   }
	}
	
	
	
	
   
   return exists;
}

/**********************  ANIMATE MAP MARKER  ********************************/
function animateMapMarker(videoid,flag) {
	
	 //ANIMATE
	   for(i=0; i<viewportMapMarkerArray.length; i++) {
		   			if (viewportMapMarkerArray[i].getAnimation() == null && viewportMapMarkerArray[i].vid.id==videoid) {
		   			currentAnimateMarker = viewportMapMarkerArray[i]; 	
		   			//if(markerimg) {currentAnimateMarker.setIcon(new google.maps.MarkerImage(terrabisApp.googleMarkerPlayImgURL));}	
		   			currentAnimateMarker.setAnimation(google.maps.Animation.BOUNCE);
				}
					
	   }
}




function stopAnimateMapMarker(markerimg) {
			//if(markerimg) {currentAnimateMarker.setIcon(new google.maps.MarkerImage(terrabisApp.googleMarkerImgURL));}	
			currentAnimateMarker.setAnimation(null);

}

function clearCurrentAnimateMarker() {
	if(currentAnimateMarker!=null) {
		//currentAnimateMarker.setIcon(new google.maps.MarkerImage(terrabisApp.googleMarkerImgURL));
		currentAnimateMarker.setAnimation(null);
		currentAnimateMarker = null;
	}
}







/**********************  FIT BOUNDS  ********************************/
function fitBoundsToVisibleMarkers(arrayStorage) {

    //var bounds = new google.maps.LatLngBounds();
    var bounds = terrabisApp.globalMap.getBounds();
    

    for (var i=0; i<arrayStorage.length; i++) {
        if(arrayStorage[i].getVisible()) {
            bounds.extend( arrayStorage[i].getPosition() );
        }
    }

    terrabisApp.globalMap.fitBounds(bounds);
}



/**********************  CREATE BOUNDS ON VISIBLE MARKERS  ********************************/
function createBoundsOnVisibleMarkers(arrayStorage) {

    var bounds = new google.maps.LatLngBounds();

    for (var i=0; i<arrayStorage.length; i++) {
        if(arrayStorage[i].getVisible()) {
            bounds.extend( arrayStorage[i].getPosition() );
        }
    }

    terrabisApp.globalMap.fitBounds(bounds);
}









/**********************  CREAT MAP BASED ON SEARCH RESULT  ********************************/
function createMapBasedOnSearchResult(GMAPRESULT,mapType,mapID) {



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

map = new google.maps.Map(document.getElementById(mapID), mapOptions);



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






/**********************  CREATE MAP CENTER MARKER  ********************************/
function createMapCenterMarker(isdraggable,moveonclick,currentmap) {
	


	var markerImage = new google.maps.MarkerImage(terrabisApp.googleMarkerCenterMapImgURL);
	var markerPosition =  currentmap.getCenter();
	
	var marker = new google.maps.Marker({
            position: markerPosition,  
            map: currentmap, 
            draggable: isdraggable,
            icon: markerImage
   });

	terrabisApp.mapCenterMarker = marker;
	
	//ADD LISTENER ON CLICK MAP
	if(moveonclick) {
		google.maps.event.addListener(currentmap, "click", function (e) {
		    var location = e.latLng;
		    terrabisApp.mapCenterMarker.setPosition(location);
			$('#latitude').val(terrabisApp.mapCenterMarker.getPosition().lat());
			$('#longitude').val(terrabisApp.mapCenterMarker.getPosition().lng());
			$('#searchLocation').val(location.lat()+" "+location.lng());	    
		});
	}

	
	//ADD LISTENER ON DRAG MARKER
	if(isdraggable) {
			terrabisApp.mapCenterMarker.addListener('dragend', function() {
			$('#latitude').val(terrabisApp.mapCenterMarker.getPosition().lat());
			$('#longitude').val(terrabisApp.mapCenterMarker.getPosition().lng());
			$('#searchLocation').val(terrabisApp.mapCenterMarker.getPosition().lat()+" "+terrabisApp.mapCenterMarker.getPosition().lng());
	    });
	
	}	
	
	
};










/**********************  CREATE VIDEO MARKERS ON MAP  ********************************/
function createSimpleVideoMarker(vid,markerImage, clickable) {
	
	var map = terrabisApp.globalMap; 
	
	var infowindow_title = vid.title;
	var markerPosition = new google.maps.LatLng(vid.latitude,vid.longitude);
	

	var marker = new google.maps.Marker({
            position: markerPosition,  
            map: map, 
            draggable: false,
            icon: markerImage,
            title:infowindow_title
   });

   
   marker.vid = vid;	
   
   if(vid.promotemap!=0)
	   marker.setZIndex(10000);


   
   if(clickable) {
	   
	   //MARKER CLICK
	   marker.addListener('click', function() {
		   
		   terrabisApp.currentMapMarker = this;
		   this.setAnimation(google.maps.Animation.BOUNCE);

		   //SET CURRENT VIDEO
			terrabisApp.currentVideo = this.vid;
		   
			//PLAY
			setTimeout(function (){
				   window.location.href = terrabisApp.videoOneViewURI+terrabisApp.currentVideo.id;
				}, 1000);

	   });	   
	   
	   
   }


   return marker;
   

};




/*function showMapVideoPreview(leftposition,title,description,likescount,playscount,ratingssum,thumburl,promotemap) {
	$(".mapoverlay_video_preview").css({top: 0, left: leftposition, position:'absolute'});

	
	//SHOW PREVIEW
	$(".vidoverlay_preview_map_title").text(title);
	$(".vidoverlay_preview_map_desc").text(description);
	$(".vidoverlay_preview_map_likes").text(likescount > 0 ? likescount : "");
    $(".vidoverlay_preview_map_plays").text(playscount > 0 ? playscount : "");
    $(".vidoverlay_preview_map_rating").text(ratingssum > 0 ? ratingssum : "");
	$(".mapoverlay_video_preview").switchClass("hidden", "-", 0, "easeInOutQuad" );
	$(".vidoverlay_preview_map_img").switchClass("hidden", "-", 0, "easeInOutQuad" );
	$(".vidoverlay_preview_map_img").attr( "src", thumburl);

	if(promotemap!=0)
		$(".vidoverlay_preview_map_toppick").switchClass("hidden", "-", 0, "easeInOutQuad" );
		
	
	
};
*/




/*function hideMapVideoPreview() {
	   $(".mapoverlay_video_preview").switchClass("-", "hidden", 0, "easeInOutQuad" );
	   $(".vidoverlay_preview_map_toppick").switchClass("-", "hidden", 0, "easeInOutQuad" );
	   $(".vidoverlay_preview_map_title").text( "");
	   $(".vidoverlay_preview_map_img").attr( "src", "");	
};
*/

/**********************  CREATE VIDEO MARKER WITH VIDEO OVERLAY   ********************************/
function createVideoMarkerWithVideoOverlay(vid,markerImage,index,size) {
	
	var map = terrabisApp.globalMap;
	
	var infowindow_title = vid.title;
	var markerPosition = new google.maps.LatLng(vid.latitude,vid.longitude);
	
	
	var marker = new google.maps.Marker({
            position: markerPosition,  
            map: map, 
            draggable: false,
            icon: markerImage,
            title:infowindow_title
   });

   
   marker.vid = vid;
   
   if(vid.promotemap!=0)
	   marker.setZIndex(10000);
   
   marker.addListener('mouseover', function() {

	   
	   		//FOR DESKTOPS ONLY PREVIEW
	   		if(terrabisApp.normal) {
	   			
		   		//SET CURRENT VIDEO
				terrabisApp.currentVideo = this.vid;
	
				var projection = terrabisApp.mapOverlay.getProjection(); 
		    	var currentpixel = projection.fromLatLngToContainerPixel(this.getPosition());
		    	var centerpixel = projection.fromLatLngToContainerPixel(this.getMap().getCenter());
		    	rightoffset_x_preview = centerpixel.x*2-400;
		    	rightoffset_x_play = centerpixel.x*2-640;
		    	terrabisApp.mapoverlay_preview =currentpixel.x>400  ? 15 : rightoffset_x_preview+15;
		  
		    	showMapVideoPreview(terrabisApp.mapoverlay_preview,this.vid.title,this.vid.description,this.vid.likescount,this.vid.playscount,this.vid.ratingssum, this.vid.reviewscount, this.vid.thumburl,this.vid.promotemap);
		    	
	   		}
/*	    	
	    	$(".mapoverlay_video_preview").css({top: 0, left: terrabisApp.mapoverlay_preview, position:'absolute'});

			
			//SHOW PREVIEW
			$(".vidoverlay_preview_map_title").text(this.vid.title);
			$(".vidoverlay_preview_map_desc").text(this.vid.description);
			$(".vidoverlay_preview_map_likes").text(this.vid.likescount > 0 ? this.vid.likescount : "");
		    $(".vidoverlay_preview_map_plays").text(this.vid.playscount > 0 ? this.vid.playscount : "");
		    $(".vidoverlay_preview_map_rating").text(this.vid.ratingssum > 0 ? this.vid.ratingssum : "");
			$(".mapoverlay_video_preview").switchClass("hidden", "-", 0, "easeInOutQuad" );
			$(".vidoverlay_preview_map_img").switchClass("hidden", "-", 0, "easeInOutQuad" );
			$(".vidoverlay_preview_map_img").attr( "src", this.vid.thumburl);

			if(this.vid.promotemap!=0)
				$(".vidoverlay_preview_map_toppick").switchClass("hidden", "-", 0, "easeInOutQuad" );
*/		   
   });
   
   
   marker.addListener('mouseout', function() {
	   if(terrabisApp.normal) {
		   hideMapVideoPreview();
	   }
	   
	   
/*	   $(".mapoverlay_video_preview").switchClass("-", "hidden", 0, "easeInOutQuad" );
	   $(".vidoverlay_preview_map_toppick").switchClass("-", "hidden", 0, "easeInOutQuad" );
	   $(".vidoverlay_preview_map_title").text( "");
	   $(".vidoverlay_preview_map_img").attr( "src", "");*/
	   
	   
   });
   
   
   
   
   
   
   //MARKER CLICK
   marker.addListener('click', function() {
	   
	   terrabisApp.currentMapMarker = this;
	   this.setAnimation(google.maps.Animation.BOUNCE);

	 
	   
	   //SET CURRENT VIDEO
		terrabisApp.currentVideo = this.vid;
	   
	  //PLAY
		setTimeout(function (){
			   window.location.href = terrabisApp.videoOneViewURI+terrabisApp.currentVideo.id;
			}, 1000);
	   
	   
	   
	   
	   //PLAY FULL SCREEN
	   /*
		var callback = function(data, status, xhr) {
			fullScreenOverlayVideoPlay(JSON.parse(data.responseText).video);
			countVideoPlay();
			terrabisApp.currentMapMarker.setAnimation(google.maps.Animation.BOUNCE);
			terrabisApp.currentMapMarker.setIcon(new google.maps.MarkerImage(terrabisApp.googleMarkerPlayImgURL));
			
		};

		
		var formData = { videoid: terrabisApp.currentVideo.id };
		restajax(terrabisApp.videoDataURI,formData,callback);		
		
	    */
	 	   
   });   



   
   //CREATE PATH DATA
/*   if(terrabisApp.isPath) {
	   
	   //SETUP ORIGIN AND DESTINATION
	   if(index==0)
		   terrabisApp.vidPathOrigin = markerPosition;
	   else if(index==size-1)
		   terrabisApp.vidPathDestination = markerPosition;
	   //INTERMEDIATE POINTS
	   else {
		   vidPathWayPointsArray.push({
		       location: markerPosition,
		       stopover: true
		     });
	   }
	   
	   
	   
   }*/

   
  return marker;
	
};


function createVideoMarkersFromPartitions(PARTITIONS,videoOverlay,arrayStorage) {


	for (var j=0; j<PARTITIONS.length; j++) {
		var VIDEOLIST = PARTITIONS[j];

		//LOOP THROUGH ALL VIDEOS AND CREATE VIDEO MARKER
			for (var i=0; i<VIDEOLIST.length; i++) {
				var vid = VIDEOLIST[i];
				var markerImage = new google.maps.MarkerImage(terrabisApp.googleMarkerImgDynamicURL+vid.markerimgid+".png");
				//var markerImage = new google.maps.MarkerImage(terrabisApp.googleMarkerImgDynamicURL+"1.png");
				var marker = null;
				
				//CREATE SINGLE VIDEO MARKER
				if(!videoOverlay)
					marker = createSimpleVideoMarker(vid,markerImage,i,VIDEOLIST.length,viewporttype);
				else
					marker = createVideoMarkerWithVideoOverlay(vid,markerImage,i,VIDEOLIST.length,viewporttype);
				
				arrayStorage.push(marker);
				
				
				
			}
	}
}; 




function createVideoMarkersFromList(VIDEOLIST,videoOverlay,arrayStorage) {
	
	
	//LOOP THROUGH ALL VIDEOS AND CREATE VIDEO MARKER
	for (var i=0; i<VIDEOLIST.length; i++) {
		

		
		var vid = VIDEOLIST[i];
		var markerImage = "";
		var marker = null;
/*		
		if(vid.id=='2947194') {
			console.log(arrayContainsMapMarker(vid.id,'viewportMapMarkerArray'));
			console.log(arrayContainsMapMarker(vid.id,'vidMapMarkerArray'));
			
		}
		
		*/
		

		if(!arrayContainsMapMarker(vid.id,'viewportMapMarkerArray') && !arrayContainsMapMarker(vid.id,'vidMapMarkerArray')) {
			if(vid.promotemap==0)
				markerImage = new google.maps.MarkerImage(terrabisApp.googleMarkerImgDynamicURL+vid.markerimgid+".png");
				//markerImage = new google.maps.MarkerImage(terrabisApp.googleMarkerImgDynamicURL+"1.png");
			else {
				markerImage = new google.maps.MarkerImage(terrabisApp.googleMarkerImgDynamicURL+"promote.png");
			}
		
			
			
			
			//CREATE SINGLE VIDEO MARKER
			if(!videoOverlay)
				marker = createSimpleVideoMarker(vid,markerImage,i,VIDEOLIST.length);
			else
				marker = createVideoMarkerWithVideoOverlay(vid,markerImage,i,VIDEOLIST.length);
	
			//ADD MARKER TO VIEWPORT AND CREATE PROMOTION OVERLAY
			if(arrayStorage == 'viewportMapMarkerArray') {
				viewportMapMarkerArray.push(marker);
/*				if(vid.promotemap!=0) {
					if(terrabisApp.promotionOverlayCount<3) {
						if($.inArray(vid.id, promoPopupShownOnMapArray)<0) {
							promoPopupShownOnMapArray.push(vid.id);
							createPromotionsOverlay(vid);
							terrabisApp.promotionOverlayCount = terrabisApp.promotionOverlayCount + 1;
						}
					}
						
				}*/
			}
			else if(arrayStorage == 'vidMapMarkerArray')
				vidMapMarkerArray.push(marker);
			
			
			
			
			
			
		}
		
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






