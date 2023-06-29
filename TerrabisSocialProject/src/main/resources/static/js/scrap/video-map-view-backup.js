


function initiateViewPortMarkers(markersList) {
	

	
	// CLEAR PROMOTIONS OVERLAY
	clearPromotionsOverlay();
	terrabisApp.promotionOverlayCount = 0;
	
	
	//CLEAR PREVIOUS MARKERS
	clearVidMapFromMarkers();
	clearViewportMapFromMarkers();

	//LOADING
	createHtml_LoadingPaginator();
	
	if(markersList!=null) {
		createVideoMarkersFromList(markersList,true,'viewportMapMarkerArray');	
		
		
		if(markersList.length>0) {
			createHtml_MorePaginator(markersList.length);
		}
		else {
			createHtml_EmptyPaginator();
		}
		
	
		//CREATE MARKERS
		mapData_FetchMarkers(null);
	
	}
}





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
	






function buildStaticMapShareURI() {
	
	  var map = terrabisApp.globalMap;
	  var currentzoomlevel = map.getZoom();
	  var currentmaplocation = map.getCenter();
	  var northeast = map.getBounds().getNorthEast();
	  var southwest = map.getBounds().getSouthWest();
	  
	  terrabisApp.staticMapShareURI = "http://www.terrabis.com"+terrabisApp.mapURI+"?locationreq="+currentmaplocation.lat()+"w"+currentmaplocation.lng()+"&zoomlevel="+currentzoomlevel+
			  						  "&ne="+northeast.lat()+"w"+northeast.lng()+"&sw="+southwest.lat()+"w"+southwest.lng(); 	
	  
	  
	  //console.log(terrabisApp.staticMapShareURI);
}




//**********************  QUERY CRITERIA AJAX FOR NORMAL  MODE   ********************************/
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



//**********************  QUERY CRITERIA AJAX FORM FOR FULL SCREEN MODE  ********************************/
function init_queryCriteriaDataForFullScreenMode() {
	
	
	
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
      		 }		             			 
    };

    terrabisApp.queryData = queryData;
    return queryData; 
    

};


//**********************  QUERY CRITERIA SUBMIT FUNCTION FOR NORMAL  MODE   ********************************/
function submitAdvancedCriteriaFilter() {
	
	

	//LOADING
	createHtml_LoadingPaginator();
	
	//ERASE PREVIEWS LOAD MORE DATA
	$('.load-more-data').html('');
	
	
	//$( "#loadingresults-map-overlay").switchClass( "hidden", "-", 0, "easeInOutQuad" );
	 queryData = init_queryCriteriaData();
	 highlightCriteriaMenu(queryData);
	 
	 console.log(queryData);
	 
	 

	$.ajax({ 
	    url:  terrabisApp.advSearchURI, 
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
			var markerslist = data.markerslist;
			if(markerslist!=null) {
				initiateViewPortMarkers(markerslist);
			}
			
			
			//initiateViewPortMarkers(data.markerslist);
			
			/*
	    	var markerslist = data.markerslist;  
			createVideoMarkersFromList(markerslist,true,vidMapMarkerArray);
		 				
	    	var PARTITIONS = data.videolist;  
			if(PARTITIONS.length>0) {
				createHtml_Paginator(data.paginator);
				createHtml_GridResultView(PARTITIONS,false);
				$('#advtagcloud').html(data.tagliststring);
				
				
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
				updateUIOnEmptyResultOrLastPage(true,true,true);
			}
			*/
			
			
			
			
	    },
	    error: function (xhr, status, error) {
	        console.log("advfilterdatesbutton:ERROR:"+JSON.stringify(xhr));
	        console.log("advfilterdatesbutton:ERROR:"+JSON.stringify(error));
	    }
	}); 	
	 
	
};


/**********************  MAP FETCH MARKERS  ********************************/
function mapData_FetchMarkers(event) {
	
	
					  
					  var queryData = '';
					  
					  
	
		        	  //INIT QUERY DATA WITH MAP BOUNDS AND CRITERIA FROM LEFT SIDEBAR OR ONLY WITH MAP BOUNDS IN CASE OF FULL SCREEN
					  if(terrabisApp.gridViewStyle=='FULLSCREENMAP')
						  queryData = init_queryCriteriaDataForFullScreenMode();
					  else
						  queryData = init_queryCriteriaData();
						  
 	

		        	  $.ajax({ 
		        		    url:  terrabisApp.mapDataMarkersURI, 
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
	        					
		        		    	var markerslist = data.markerslist;  
		        				if(markerslist.length>0) {
		        					createVideoMarkersFromList(markerslist,true,'vidMapMarkerArray');
		        					
		        					//IF IS PATH DRAW POLYLINE
		        					if(terrabisApp.isPath)
		        						drawMarkersPath();
		        					
		        					
		        					//FETCH DATA GRID
		        					mapData_FetchDataGrid(null);
		        					
		        			 	}
		        				else {
		        					updateUIOnEmptyResultOrLastPage(true,true,true);
		        			    }
		        		    },
		        		    error: function (xhr, status, error) {
		        		    //$( "#loadingresults-map-overlay").switchClass( "-", "hidden", 0, "easeInOutQuad" );
		        		        console.log("ajaxLoadFirstTimeMapData:ERROR:"+JSON.stringify(xhr));
		        		        console.log("ajaxLoadFirstTimeMapData:ERROR:"+JSON.stringify(error));
		        		    }
		        		}); 
};


/**********************  MAP FETCH DATA GRID  ********************************/
function mapData_FetchDataGrid(event) {
	
			//
			if(currentAnimateMarker!=null)
				currentAnimateMarker.setAnimation(null);
	
	
			//BUILD SHARE URI
			buildStaticMapShareURI();
			
				
			console.log('mapData_FetchDataGrid');
	
				//ERASE PREVIEWS LOAD MORE DATA
				$('.load-more-data').html('');

	
				  //CLEAR UPLOAD MARKER
				  clearUploadMarker();
				  
	
				  //INIT QUERY DATA WITH MAP BOUNDS AND CRITERIA FROM LEFT SIDEBAR OR ONLY WITH MAP BOUNDS IN CASE OF FULL SCREEN
				  if(terrabisApp.gridViewStyle=='FULLSCREENMAP')
					  queryData = init_queryCriteriaDataForFullScreenMode();
				  else
					  queryData = init_queryCriteriaData();


				  
				  
		        	  $.ajax({ 
		        		    url:  terrabisApp.mapDataGridURI, 
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


	
$(document).delegate('.mapbtn-toggle-searchform', 'click', function() {
	if(terrabisApp.showMapSearchForm) {
		$("#mapsearchform").fadeIn( "slow", function() {
		  });
		terrabisApp.showMapSearchForm = false;
		
	}
	else {
		$("#mapsearchform").animate({
		      opacity: 'hide', // animate fadeOut
		      width: 'hide'  // animate slideLeft
		    }, 'slow', 'linear', function() {
		     
		    });
		terrabisApp.showMapSearchForm = true;
	}
});





	
	
	
	
	


	
/**********************  PAGINATOR CLICK  ********************************/
$(document).delegate('.paginator-page', 'click', function() {
	
	var queryData = { pageid: this.dataset.page};
	
	$.ajax({ 
	    url:  terrabisApp.paginatorMapURI, 
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
			terrabisApp.taglist = JSON.stringify(data.taglist);
			if(PARTITIONS.length>0) {
				createVideoMarkersFromPartitions(PARTITIONS,true);
				createHtml_GridResultView(PARTITIONS,false);
				createHtml_Paginator(data.paginator);
				
				
				//UPDATE TAG CLOUD
				$('#advtagcloud').jQCloud('update', data.taglist);

		 	}
			
			else {
				//SET EMPTY GRID
				$('#videoGrid').html("");
			}
	
	    },
	    error: function (xhr, status, error) {
	        console.log("paginator:ERROR:"+JSON.stringify(xhr));
	        console.log("paginator:ERROR:"+JSON.stringify(error));
	    }
	}); 
	
		
	
});
