var tableId = '11nY5lrBT8Wa4WNeq18Xvf6O-cO_mrLCERIKguLpY';
var markers = [];
var token;
var markerCluster;
var layer;

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
    var marker = new google.maps.Marker({
      position: coordinate,
      icon: new google.maps.MarkerImage('http://www.terrabis.com/images/markers-list/marker-'+markerimgid+'.png')
    });
    
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




function onDataFetchedCallback(response) {
	
	var rows = response.responseJSON.rows;
    var iconUrl;
    var content;
    var coordinate;

    
    for (var i in rows) {
      coordinate = new google.maps.LatLng(rows[i][0],rows[i][1]);
      id = rows[i][2];
      markerimgid = rows[i][3];
      if(!markerimgid)
    	  markerimgid = 1;
      
      title = rows[i][4];
      description = rows[i][5];
      uuid = rows[i][6];


      
      var marker = createMarker(false,coordinate,  id, markerimgid, title, description, uuid);
      markers.push(marker);
    }
    
    
    var mcOptions = {gridSize: parseInt(terrabisApp.clusterSize), maxZoom: parseInt(terrabisApp.clusterZoomLevel), imagePath: '/images/markerclusterer/m', zoomOnClick: true};
    markerCluster = new MarkerClusterer(terrabisApp.globalMap, markers, mcOptions);
    
};




function showClusterMapVersion() {
	 terrabisApp.clusterMap = true;
	 terrabisApp.globalMap.setMapTypeId(google.maps.MapTypeId.HYBRID);
	 
	 
	 deleteLayer();
	 initMap();		
	 
};



function clearClusterMapVersion() {
	
	terrabisApp.clusterMap = false;
	terrabisApp.globalMap.setMapTypeId(google.maps.MapTypeId.ROADMAP);

	
	//DELETE MARKER CLUSTER AND MARKERS
	 deleteMarkerCluster();
	 deleteMarkers();
	 
	
	
	initMap();

    
	 
	
};



function initMap() {
	

	
	if(terrabisApp.clusterMap) {
		 deleteMarkerCluster();
		 deleteMarkers();
		queryClusterDataMarkers();
	}
	else {
		deleteLayer();
		queryFlatMapLayer();
	}
};


function queryClusterDataMarkers() {
	
	var query = "SELECT latitude, longitude, id, markerimgid, title, description, UUID FROM "+tableId+"  WHERE status = 1 AND healthstatus = 1" ;
	if((terrabisApp.queryData!=null) && terrabisApp.queryData.advfilter.shootingtypeid!=0)
		query = query + ' AND shootingtypeid='+terrabisApp.queryData.advfilter.shootingtypeid;
	if((terrabisApp.queryData!=null) && terrabisApp.queryData.advfilter.videotypeid!=0)
		query = query + ' AND markerimgid='+terrabisApp.queryData.advfilter.videotypeid;


	
    var encodedQuery = encodeURIComponent(query);
	var URL = 'https://www.googleapis.com/fusiontables/v2/query?sql='+encodedQuery+'&access_token='+token;
	
	$.ajax({ 
	    url:  URL, 
	    type: 'GET', 
	    dataType: 'json',
	    contentType: "application/json; charset=utf-8",
	    cache: false, // Force requested pages not to be cached by the browser
	    processData: false, // Avoid making query string instead of JSON	
	    crossDomain :true,
		complete: onDataFetchedCallback,
	    error: function (xhr, status, error) {
	        console.log("restajax:ERROR:"+JSON.stringify(xhr));
	        console.log("restajax:ERROR:"+JSON.stringify(error));
	    }
	}); 
};



function queryFlatMapLayer() {

	var whereSQL = 'status = 1 and healthstatus = 1';

	if(terrabisApp.queryData && terrabisApp.queryData.advfilter.shootingtypeid!=0)
		whereSQL = whereSQL + ' and shootingtypeid = '+terrabisApp.queryData.advfilter.shootingtypeid;
	if(terrabisApp.queryData && terrabisApp.queryData.advfilter.videotypeid!=0)
		whereSQL = whereSQL + ' and markerimgid = '+terrabisApp.queryData.advfilter.videotypeid;

	
	
	
	
	
	if(layer==null) {
		layer = new google.maps.FusionTablesLayer({
	        query: {
	                select: 'latitude',
	                from: tableId
	        },
	        map: terrabisApp.globalMap,
			options: {
					styleId: 2,
					templateId: 2,
					where : whereSQL
				}
	      });
	
	}
	
	
	if (!layer.getMap()) {
		
		layer.setOptions({
              query: {
                select: 'latitude',
                from: tableId
              },
            map: terrabisApp.globalMap,  
      		options: {
  				styleId: 2,
  				templateId: 2,
  				where : whereSQL
  			}
            });
		
		
	}


    
	
};




/**********************  MAP FETCH DATA GRID  ********************************/
function createDataGrid() {

	
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
		        				if(PARTITIONS.length>0) {
		        					createHtml_Paginator(data.paginator);
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
		        					
		        			 	}
		        				else {
		        					$('.load-more-data').html('');
		        					updateUIOnEmptyResultOrLastPage(true,true,true);
		        					


		        				}
		        		    },
		        		    error: function (xhr, status, error) {
		        		    //$( "#loadingresults-map-overlay").switchClass( "-", "hidden", 0, "easeInOutQuad" );
		        		        console.log("mapData_FetchDataGrid:ERROR:"+JSON.stringify(xhr));
		        		        console.log("mapData_FetchDataGrid:ERROR:"+JSON.stringify(error));
		        		    }
		        		}); 
};



//**********************  QUERY CRITERIA SUBMIT FUNCTION FOR NORMAL  MODE   ********************************/
function submitAdvancedCriteriaFilter() {
	

	//LOADING
	createHtml_LoadingPaginator();
	
	//ERASE PREVIEWS LOAD MORE DATA
	$('.load-more-data').html('');
	
	
	$( "#loadingresults-map-overlay").switchClass( "hidden", "-", 0, "easeInOutQuad" );
	 queryData = init_queryCriteriaData();
	 highlightCriteriaMenu(queryData);
	 
	 
	 //INIT MAP
	 initMap();
	 
	 //CREATE DATA GRID
	 createDataGrid();
	
};


//**********************  QUERY CRITERIA   ********************************/
function init_queryCriteriaData() {

	
	var map = terrabisApp.globalMap;
	var pageview = terrabisApp.pageView; 
	
	  var northeast = map.getBounds().getNorthEast();
	  var southwest = map.getBounds().getSouthWest();
	  var currentzoomlevel = map.getZoom();
	  var currentmaplocation = map.getCenter();


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
	
	if(resetPaginator)
		createHtml_EmptyPaginator();


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
					
	
	
	terrabisApp.uploadMarker = new google.maps.Marker({
          position:markerPosition,  
          map: terrabisApp.globalMap, 
          draggable: true,
          icon: markerImage,
          title:infowindow_title
 });


	
	
	//INFO WINDOW CODE REPLACE BY INFO BOX TODO ABOVE
	terrabisApp.uploadMarker.infowindow = new google.maps.InfoWindow({
				content: infowindow_html
	});
          
 
	terrabisApp.uploadMarker.addListener('click', function() {
				this.infowindow.open(terrabisApp.globalMap, this);
				
	});
	
	

	terrabisApp.uploadMarker.infowindow.open(terrabisApp.globalMap,terrabisApp.uploadMarker);	

};




