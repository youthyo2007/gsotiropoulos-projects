





var markers = [];
var markerCluster;
var layer;
var cluster_styles;

//var markercolor = ["#FFFFFF","#0a3b54","DeepSkyBlue","#3dac11","#bbb50c","#b519d6","#eb7308","#616161","#FFFFFF","#FFFFFF"];
	
	
	
	

//DYNAMIC CLUSTER IMAGE
 var getGoogleClusterInlineSvg = function (color) {
        var encoded = window.btoa('<svg xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" viewBox="-100 -100 200 200"><defs><g id="a" transform="rotate(45)"><path d="M0 47A47 47 0 0 0 47 0L62 0A62 62 0 0 1 0 62Z" fill-opacity="0.7"/><path d="M0 67A67 67 0 0 0 67 0L81 0A81 81 0 0 1 0 81Z" fill-opacity="0.5"/><path d="M0 86A86 86 0 0 0 86 0L100 0A100 100 0 0 1 0 100Z" fill-opacity="0.3"/></g></defs><g fill="' + color + '"><circle r="42"/><use xlink:href="#a"/><g transform="rotate(120)"><use xlink:href="#a"/></g><g transform="rotate(240)"><use xlink:href="#a"/></g></g></svg>');

        return ('data:image/svg+xml;base64,' + encoded);
    };


var getGoogleMarkerInlineSvg = function (color) {
        var encoded = window.btoa('<svg xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" width="24" height="24" viewBox="0 0 24 24"><path d="M0-48c-9.8 0-17.7 7.8-17.7 17.4 0 15.5 17.7 30.6 17.7 30.6s17.7-15.4 17.7-30.6c0-9.6-7.9-17.4-17.7-17.4z"/></svg>');

        return ('data:image/svg+xml;base64,' + encoded);
    };
    

    
    
    
    
    
function initClusterStyles() {    
    
	var color1 = terrabisApp.pinsClusterColorsList[1].color;
	var color2 = terrabisApp.pinsClusterColorsList[2].color;
	var color3 = terrabisApp.pinsClusterColorsList[3].color;
	
	
	
    cluster_styles = [
                          {
                              width: 60,
                              height: 60,
                              url: getGoogleClusterInlineSvg(color1),
                              textColor: 'white',
                              textSize: 12
                          },
                          {
                              width: 80,
                              height: 80,
                              url: getGoogleClusterInlineSvg(color2),
                              textColor: 'white',
                              textSize: 13
                          },
                          {
                              width: 100,
                              height: 100,
                              url: getGoogleClusterInlineSvg(color3),
                              textColor: 'white',
                              textSize: 14
                          },
                          //up to 5
                      ];   
    
    
    
    
};
    
    
    
//Sets the map on all markers in the array.
function setMapOnAll(map) {
for (var i = 0; i < markers.length; i++) {
 markers[i].setMap(map);
}
}

//Removes the markers from the map, but keeps them in the array.
function clearMarkers() {
setMapOnAll(null);
}

//Shows any markers currently in the array.
function showMarkers() {
setMapOnAll(map);
}

//Deletes all markers in the array by removing references to them.
function deleteMarkers() {
clearMarkers();
markers = [];
}


function deleteMarkerCluster() {
	if(markerCluster)
		markerCluster.clearMarkers();
	
}



function deleteLayer() {
	if(layer)
		layer.setMap(null);
}










var createMarker = function(addToMap, coordinate, id, markerimgid, title, description, uuid,thumburl) {
	
	/* GOOGL MAPS MARKER
	var markericon;
	if(markerimgid==null)
		markericon = new google.maps.MarkerImage('http://www.terrabis.com/images/markers-list/marker-1.png')
	else
		markericon = new google.maps.MarkerImage('http://www.terrabis.com/images/markers-list/marker-'+markerimgid+'.png')
	
    var marker = new google.maps.Marker({
      position: coordinate,
      icon: markericon
    });
    */
		
	
	//DYNAMIC MARKER
/*	var marker = new Marker({
		position: coordinate,
		icon: {
			map:terrabisApp.globalMap,
			path: MAP_PIN,
			fillColor: 'CornflowerBlue',
			fillOpacity: 1,
			strokeColor: '',
			strokeWeight: 0
		},
		map_icon_label: '<span class="map-icon map-icon-postal-code"></span>'
	});
	*/
	

	
	
/*	var marker = new google.maps.Marker({
	      position: coordinate,
	      icon: {
	    	    anchor: new google.maps.Point(8, 8),
	    	    url: 'data:image/svg+xml;utf-8, \
	    	      <svg width="16" height="16" viewBox="0 0 32 32" xmlns="http://www.w3.org/2000/svg"> \
	    	        <path fill="'+terrabisApp.videoTypeColorsList[markerimgid==null ? 2 : markerimgid].color+'" stroke="white" stroke-width="1.5"  d="M3.5 3.5h25v25h-25z" ></path> \
	    	      </svg>'
	    	  }
	    });
	*/
	
	
	var markersize = terrabisApp.globalMap.getZoom()/10;
	if(markersize>0.7)
		markersize = 0.7;
	
	var marker = new google.maps.Marker({
	      position: coordinate,
	      icon: {
	    	    anchor: new google.maps.Point(8, 8),
	    	    url: 'data:image/svg+xml;utf-8, \
	    	      <svg baseProfile="basic" width="48" height="48" viewBox="0 0 48 48" xmlns="http://www.w3.org/2000/svg">   <g transform="scale('+markersize+')"> \
	    	        <path fill="'+terrabisApp.videoTypeColorsList[markerimgid==null ? 2 : markerimgid].color+'" stroke="black" stroke-width="1.5"   d="M24 0c-9.8 0-17.7 7.8-17.7 17.4 0 15.5 17.7 30.6 17.7 30.6s17.7-15.4 17.7-30.6c0-9.6-7.9-17.4-17.7-17.4z"></path> \
	    	        </g></svg>'
	    	  }
	    });
	
	
	
/*	<svg baseProfile="basic" xmlns="http://www.w3.org/2000/svg" width="48" height="48" viewBox="0 0 48 48">
    <g transform="scale(0.5)">
    <path d="M24 0c-9.8 0-17.7 7.8-17.7 17.4 0 15.5 17.7 30.6 17.7 30.6s17.7-15.4 17.7-30.6c0-9.6-7.9-17.4-17.7-17.4z"/>
    </g>
    </svg>*/
    
    marker.addListener('mouseover', function() {
   		//FOR DESKTOPS ONLY PREVIEW
   		if(terrabisApp.normal) {

   			var projection = terrabisApp.mapOverlay.getProjection(); 
	    	var currentpixel = projection.fromLatLngToContainerPixel(this.getPosition());
	    	var centerpixel = projection.fromLatLngToContainerPixel(this.getMap().getCenter());
	    	rightoffset_x_preview = centerpixel.x*2-400;
	    	rightoffset_x_play = centerpixel.x*2-640;
	    	terrabisApp.mapoverlay_preview =currentpixel.x>400  ? 15 : rightoffset_x_preview+15;
	    	showMapVideoPreview(terrabisApp.mapoverlay_preview,title,description,uuid,thumburl);
	    	
	    	
   		}
    });
    
    
    marker.addListener('mouseout', function() {
    	   if(terrabisApp.normal) {
    		   hideMapVideoPreview();
    	   }
    });
    
    
    
    if(terrabisApp.globalMapVideoID==id) {
    	marker.setAnimation(google.maps.Animation.BOUNCE);
    	   
    	   setTimeout(function (){
    		   marker.setAnimation(null);
			}, 10000);
    	   
    };

    
    
    marker.addListener('click', function() {
		   
		   this.setAnimation(google.maps.Animation.BOUNCE);

		   
			//PLAY
			setTimeout(function (){
				   window.location.href = terrabisApp.videoOneViewURI+uuid;
				}, 1000);

	   });	   
    
    
    if(addToMap)
    	marker.setMap(terrabisApp.globalMap);
    
    return marker;
    
};



  
function showMapVideoPreview(leftposition,title,description,uuid) {
	$(".mapoverlay_video_preview").css({top: 0, left: leftposition, position:'absolute'});

	//SHOW PREVIEW
	$(".vidoverlay_preview_map_title").text(title);
	$(".vidoverlay_preview_map_desc").text(description);
/*	$(".vidoverlay_preview_map_likes").text(likescount > 0 ? likescount : "");
    $(".vidoverlay_preview_map_plays").text(playscount > 0 ? playscount : "");
    $(".vidoverlay_preview_map_rating").text(ratingssum > 0 ? ratingssum : "");*/
	$(".mapoverlay_video_preview").switchClass("hidden", "-", 0, "easeInOutQuad" );
	$(".vidoverlay_preview_map_img").switchClass("hidden", "-", 0, "easeInOutQuad" );
	$(".vidoverlay_preview_map_toppick").switchClass("-", "hidden", 0, "easeInOutQuad" );

	
	thumburl = 'http://terrabisvideothumb.s3.amazonaws.com/'+uuid+'.png';
	$(".vidoverlay_preview_map_img").attr( "src", thumburl);

/*	if(promotemap!=0)
		$(".vidoverlay_preview_map_toppick").switchClass("hidden", "-", 0, "easeInOutQuad" );
*/		
	
	
};





function hideMapVideoPreview() {
	   $(".mapoverlay_video_preview").switchClass("-", "hidden", 0, "easeInOutQuad" );
       $(".vidoverlay_preview_map_toppick").switchClass("-", "hidden", 0, "easeInOutQuad" );
	   $(".vidoverlay_preview_map_title").text( "");
	   $(".vidoverlay_preview_map_img").attr( "src", "");	
};






function globalMapBoundsChanged() {
	
	
		initMapPins();
		createDataGrid();
	
	
};



function clearMapEverything() {
	 deleteMarkerCluster();
	 deleteMarkers();
	
};


function onPointsFetchedCallback(data) {
	
	
	clearMapEverything();
	
	
	var pointslist = data.responseJSON.pointslist;  


	for (i = 0; i < pointslist.length; i++) {
     var point = pointslist[i];
     
     
     if(isNaN(point.markerimgid))
    	 point.markerimgid = 1;
     
     var coordinate = new google.maps.LatLng(point.latitude,point.longitude);
     var marker = createMarker(terrabisApp.clusterMap ? false : true,coordinate,  point.id, point.markerimgid, point.title, point.description, point.uuid);
     markers.push(marker);
     //marker.setMap(terrabisApp.globalMap); 
    }
    
	
	
    //var mcOptions = {gridSize: parseInt(terrabisApp.clusterSize), maxZoom: parseInt(terrabisApp.clusterZoomLevel), imagePath: '/images/markerclusterer/m', zoomOnClick: true};
	if( terrabisApp.clusterMap) {
		var mcOptions = {gridSize: parseInt(terrabisApp.clusterSize), maxZoom: parseInt(terrabisApp.clusterZoomLevel), styles: cluster_styles, zoomOnClick: true};
		markerCluster = new MarkerClusterer(terrabisApp.globalMap, markers, mcOptions);
	} 
	

    //FINISHED
	//createHtml_FinishedPaginator();

    
};




function showClusterMapVersion() {
	 terrabisApp.clusterMap = true;

	 
	 deleteLayer();
	 initMapPins();
	
	 
};



function clearClusterMapVersion() {
	
	terrabisApp.clusterMap = false;
	
	 deleteLayer();
	 initMapPins();
	
	
	
	  //DELETE MARKER CLUSTER AND MARKERS
	 //deleteMarkerCluster();
	 //deleteMarkers();
	 
	 
	 
	 //deleteLayer();
	 //queryFlatMapLayer();
	 
		
	 
};


function queryFlatMapLayer() {

	
	createHtml_CleanPaginator();
	
		layer = new google.maps.FusionTablesLayer({
	        query: {
	                select: 'latitude',
	                from:  '11nY5lrBT8Wa4WNeq18Xvf6O-cO_mrLCERIKguLpY'
	        },
	        map: terrabisApp.globalMap,
			options: {
					styleId: 2,
					templateId: 2
				}
	      });
	
};
	
	






function initMapPins() {
	
	 initClusterStyles();
	 
	
	if(terrabisApp.clusterMap) {
		queryClusterDataMarkers();
	}
	else {
		 queryClusterDataMarkers();
		 //deleteLayer();
		//queryFlatMapLayer();	
	}
	
	
};


function queryClusterDataMarkers() {
	
	
	//INIT QUERY DATA
	queryData = init_queryCriteriaData();
	

	
	//FETCH DATA
	  $.ajax({ 
		    url: '/rest/viewport-pins/', 
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
			complete: onPointsFetchedCallback,
		    error: function (xhr, status, error) {
		    //$( "#loadingresults-map-overlay").switchClass( "-", "hidden", 0, "easeInOutQuad" );
		        console.log("mapData_FetchDataGrid:ERROR:"+JSON.stringify(xhr));
		        console.log("mapData_FetchDataGrid:ERROR:"+JSON.stringify(error));
		    }
		}); 
	
	
	
	
	
};




/**********************  MAP FETCH DATA GRID  ********************************/
function createDataGrid() {


				//LOADING
				//createHtml_LoadingPaginator();
	
				//ERASE PREVIEWS LOAD MORE DATA
				$('.load-more-data').html('');

				//INIT QUERY DATA
				queryData = init_queryCriteriaData();
				
				
				
				//FETCH DATA
			    	  $.ajax({ 
		        		    url:  terrabisApp.viewportCatalogURI, 
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
		        				terrabisApp.taglist =  JSON.stringify(data.taglist);
		        				var viewportTotal = data.paginator.recordsCount;
		        				
		        				if(!PARTITIONS.length>0) {
		        					createHtml_EmptyPaginator();
		        				}
/*		        				else if(viewportTotal>terrabisApp.viewPortSize) {
		        					createHtml_MorePaginator(terrabisApp.viewPortSize,viewportTotal);
		        				}
		        				else {
		        					createHtml_TotalPaginator(viewportTotal);
		        				}
		        				*/
		        				
		        				
		        				if(PARTITIONS.length>0) {
		        					createHtml_CleanPaginator();
		        					createHtml_GridResultView(PARTITIONS,false);

		        					//UPDATE TAG CLOUD
		        					$('#advtagcloud').html(data.tagliststring);
		        					
		        					//IF IS LAST PAGE
		        					if(data.paginator.lastPage) {
		        						updateUIOnEmptyResultOrLastPage(false,false,false);
		        					} 

		        					else
		        					{
			        					terrabisApp.paginatorLastPage = false;
			        					$('.load-more-video').html('LOAD MORE...');
		        					}
		        					
		        					
		        					
		        			 		 $('.mapoverlay_paginator').fadeIn( "slow", function() {
		        			  	        // Animation complete
		        			  	    });
		        			 		 
		        					
		        			 	}
		        				else {
		        					$('.load-more-data').html('');
		        					updateUIOnEmptyResultOrLastPage(true,true,true);
		        					



		        				}
		        		    },
		        		    error: function (xhr, status, error) {
		        		    //$( "#loadingresults-map-overlay").switchClass( "-", "hidden", 0, "easeInOutQuad" );
		        		        console.log("createDataGrid:ERROR:"+JSON.stringify(xhr));
		        		        console.log("createDataGrid:ERROR:"+JSON.stringify(error));
		        		    }
		        		}); 
};



//**********************  QUERY CRITERIA SUBMIT FUNCTION FOR NORMAL  MODE   ********************************/
function submitAdvancedCriteriaFilter() {
	

	
	console.log('submitAdvancedCriteriaFilter');
	
	//LOADING
	createHtml_LoadingPaginator();
	
	//ERASE PREVIEWS LOAD MORE DATA
	$('.load-more-data').html('');
	
	
	$( "#loadingresults-map-overlay").switchClass( "hidden", "-", 0, "easeInOutQuad" );
	 queryData = init_queryCriteriaData();
	 highlightCriteriaMenu(queryData);
	 
	 
	 //INIT MAP
	 initMapPins();
	 
	 //CREATE DATA GRID
	 createDataGrid();
	
};




//**********************  SHARE FACEBOOK MAP LOCATION   ********************************/
function buildMapBoundsURI() {
	  var map = terrabisApp.globalMap;
	  var northeast = map.getBounds().getNorthEast();
	  var southwest = map.getBounds().getSouthWest();
	  var zoom = map.getZoom();
	  var center = map.getCenter();

	
	  var mapURI = terrabisApp.mapURI +'?center='+center.lat()+' '+center.lng()+'&zoom='+zoom
	  +'&northeast='+northeast.lat()+' '+northeast.lng()
	  +'&southwest='+southwest.lat()+' '+southwest.lng();
	  
	  return mapURI;
	  
	
};



function buildStaticMapImageURI() {

	  var map = terrabisApp.globalMap;
	  var northeast = map.getBounds().getNorthEast();
	  var southwest = map.getBounds().getSouthWest();
	  var zoom = map.getZoom();
	  var center = map.getCenter();

	
	  var mapURI = terrabisApp.mapStaticImageURI +'?center='+center.lat()+' '+center.lng()+'&zoom='+zoom
	  +'&northeast='+northeast.lat()+' '+northeast.lng()
	  +'&southwest='+southwest.lat()+' '+southwest.lng();

	  return mapURI;
	  
	  
	
};




//**********************  QUERY CRITERIA   ********************************/
function init_queryCriteriaData() {

	
	var map = terrabisApp.globalMap;
	var pageview = terrabisApp.pageView; 
	
	  var northeast = map.getBounds().getNorthEast();
	  var southwest = map.getBounds().getSouthWest();
	  var currentzoomlevel = map.getZoom();
	  var currentmaplocation = map.getCenter();
	  console.log("currentzoomlevel:"+currentzoomlevel);

   var queryData = {
  		 	pageview: pageview,
		 	 fitBounds:false,
		 	 zoomlevel:currentzoomlevel,
		 	 gridstyle:terrabisApp.gridViewStyle,
		 	 location:{
 			 	lat:currentmaplocation.lat(),
 			 	lng:currentmaplocation.lng()
 			 },
		 	 bounds: {
    		 	 	northeast: {
    		 	 			lat:northeast.lat(),
    		 	 			lng:northeast.lng()
    		 	 	},
    		 	 	southwest: {
    		 	 			lat:southwest.lat(),
    		 	 			lng:southwest.lng()
    		 	 	}
    		 },		             			 
              advfilter: {
              		userid: $("#advsearchqueryform input[name='userid']").val(),
              		pathofvideoids: $("#advsearchqueryform input[name='pathofvideoids']").val(),
              	 	tags: $("#advsearchqueryform input[name='advtags']").val(),
	      		 	 	uploaddate: {
	      		 	 			'any':$(".uploadradiobox input[value='any']").is(':checked'),
	      		 	 			'today':$(".uploadradiobox input[value='today']").is(':checked'),
	      		 	 			'yesterday':$(".uploadradiobox input[value='yesterday']").is(':checked'),
	      		 	 			'10 days':$(".uploadradiobox input[value='10 days']").is(':checked'),
	      		 	 			'last month':$(".uploadradiobox input[value='last month']").is(':checked'),
	      		 	 			'last year':$(".uploadradiobox input[value='last year']").is(':checked')
	      		 	 	},
	      		 	 	categoryid : $(".categoryradiobox input[name='category']:checked").val(),
	      		 	 	shootingtypeid : $(".shootingtyperadiobox input[name='shootingtype']:checked").val(),	
	      		 	 	videotypeid : $(".videotyperadiobox input[name='videotype']:checked").val(),	
	      		 	 	weather: {
	      		 	 			'any':$(".weathercheckbox input[value='any']").is(':checked'),
	      		 	 			'sunny':$(".weathercheckbox input[value='sunny']").is(':checked'),
	      		 	 			'cloudy':$(".weathercheckbox input[value='cloudy']").is(':checked'),
	      		 	 			'rain':$(".weathercheckbox input[value='rain']").is(':checked'),
	      		 	 			'snow':$(".weathercheckbox input[value='snow']").is(':checked'),
	      		 	 			'windy':$(".weathercheckbox input[value='windy']").is(':checked'),
	      		 	 			'storm':$(".weathercheckbox input[value='storm']").is(':checked'),
	      		 	 	}
    		 	 	
    		 	 }
    		 	 	
  };

   
  terrabisApp.queryData = queryData; 
  return queryData; 

};


/**************************************************UPDATE UI**********************************************************************/
function updateUIOnEmptyResultOrLastPage(resetGrid,resetTags,resetPaginator) {
	
/*	if(resetPaginator)
		createHtml_EmptyPaginator();*/


	if(resetGrid)
		$('#videoGrid').html("");
	
	
	if(resetTags)
		$('#advtagcloud').html("");
	
	
	$('.load-more-video').html('');
	terrabisApp.paginatorLastPage = true;	
}



/****************************UPLOAD MARKER******************************************************************************************/
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
					
	
/*	
	terrabisApp.uploadMarker = new google.maps.Marker({
          position:markerPosition,  
          map: terrabisApp.globalMap, 
          draggable: true,
          icon: markerImage,
          title:infowindow_title
 });

*/
	terrabisApp.uploadMarker = createDynamicGoogleMapMarker(markerPosition,MAP_PIN,'#C082A0','map-icon-circle',true);
	
	//INFO WINDOW CODE REPLACE BY INFO BOX TODO ABOVE
	terrabisApp.uploadMarker.infowindow = new google.maps.InfoWindow({
				content: infowindow_html
	});
          
 
	terrabisApp.uploadMarker.addListener('click', function() {
				this.infowindow.open(terrabisApp.globalMap, this);
				
	});
	
	

	terrabisApp.uploadMarker.infowindow.open(terrabisApp.globalMap,terrabisApp.uploadMarker);	

};




